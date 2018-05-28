package cn.wt.poidemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFStyles;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.pool.DruidPooledPreparedStatement;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;

import cn.wt.poidemo.entity.DOC_INFO_T;
import cn.wt.poidemo.entity.PARA_INFO_T;
import cn.wt.poidemo.util.ConfigUtil;
import cn.wt.poidemo.util.DataSourceUtil;
import cn.wt.poidemo.util.SensitiveWordUtil;
import cn.wt.poidemo.util.TreeUtil;

public class Tester {
	private static List<XWPFParagraph> wordParagraphs = null;
	private static XWPFStyles wordStyles = null;
	private static XWPFDocument template = null;
	private static FileInputStream fis = null;
	private static DruidPooledConnection dpc = null;
	private static DruidPooledPreparedStatement ps_doc = null;
	private static DruidPooledPreparedStatement ps_para = null;
	private static String sql_doc = "insert into doc_info_t (DOC_NAME,DOC_AUTHOR,DOC_CREATETIME) values (?,?,?)";
	private static String sql_para = "insert into para_info_t (DOC_ID,PARA_STYLE_ID,PARA_VAL,PARA_TITLE,PARA_TEXT,PARA_STEP) values (?,?,?,?,?,?)";
	private static Integer id = null; // 自增长主键
	private static int s_num = 0;
	
	/**
	 * Word整体样式
	 */
	static {
		try {
			// 读取模板文档
			fis = new FileInputStream("C:\\Users\\Administrator\\Desktop\\poidemo\\大数据框架.docx");
			template = new XWPFDocument(fis);
			// 获取段落
			wordParagraphs = template.getParagraphs();
			// 获取doc样式
			wordStyles = template.getStyles();
			// init dbconn
			DataSourceUtil dbd = new DataSourceUtil();
			dbd.initDruidDataSourceFactory();
			dpc = dbd.getDruidDataSource().getConnection();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, Throwable {
		// 获取文档属性
		CoreProperties props = template.getProperties().getCoreProperties();

		DOC_INFO_T doc_INFO_T = new DOC_INFO_T();
		doc_INFO_T.setDOC_AUTHOR(props.getCreator());
		doc_INFO_T.setDOC_NAME(props.getTitle());
		doc_INFO_T.setDOC_CREATETIME(new Date(System.currentTimeMillis()));
		List<PARA_INFO_T> para_INFO_Ts = new ArrayList<PARA_INFO_T>();

		int j = 0; // 计数
		boolean isParaDone = false; // 是否解析完标题下所有内容
		PARA_INFO_T para_INFO_T = new PARA_INFO_T();
		StringBuffer paraText = new StringBuffer();
		for (XWPFParagraph para : wordParagraphs) {
			ArrayList<String> al = new ArrayList<String>();
			if (null == wordStyles.getStyle(para.getStyle())) {
				/**
				 * 无样式正文
				 */
				isParaDone = false;
				//System.out.println(para.getParagraphText());
				paraText.append(para.getParagraphText());
			} else {
				String val = wordStyles.getStyle(para.getStyle()).getCTStyle().getName().getVal();
				al.add(val);
				switch (val) {
				/**
				 * 有样式标题（只能解析有样式的标题）
				 */
				case "heading 1":case "heading 2":case "heading 3":case "heading 4":case "heading 5":
				case "样式1":case "样式2":case "样式3":case "样式4":case "样式5":
				case "List Paragraph":
					if (!StringUtils.isEmpty(paraText.toString()) && j > 0) {
						isParaDone = true;
					}
					if (isParaDone) {
						para_INFO_T.setPARA_TEXT(paraText.toString());
						para_INFO_Ts.add(para_INFO_T);
						para_INFO_T = new PARA_INFO_T();
						paraText = new StringBuffer();
					}
					al.add(para.getParagraphText());
					j++;
					//System.out.println(j++ + ":" + al);
					para_INFO_T.setPARA_STYLE_ID(para.getStyle());
					para_INFO_T.setPARA_VAL(val);
					para_INFO_T.setPARA_TITLE(para.getParagraphText());
					para_INFO_T.setPARA_STEP(j);
					break;
				/**
				 * 有样式正文
				 */
				case "Normal Indent":
					isParaDone = false;
					//System.out.println(para.getParagraphText());
					paraText.append(para.getParagraphText());
					break;
				/**
				 * 目录
				 * (目录存在未更新风险,并且目前未找到操作更新方法)
				 */
				case "toc 1":case "toc 2":case "toc 3":case "toc 4":case "toc 5":
					//do nothing
					break;
				default:
					System.out.println("****************************");
					System.out.println(val);
					System.out.println(para.getParagraphText());
					System.out.println("****************************");
					break;
				}
			}
		}
		para_INFO_T.setPARA_TEXT(paraText.toString());
		para_INFO_Ts.add(para_INFO_T);
		doc_INFO_T.setPara_INFO_Ts(para_INFO_Ts);
		
		//初始化敏感词库
		System.out.println("\nBegin check sensitive words\n");
		
		Set<String> sensitiveWordSet = ConfigUtil.getSensitivewords();;
        SensitiveWordUtil.init(sensitiveWordSet);
        para_INFO_Ts.forEach(para -> {
        	//是否含有关键字
            boolean result = SensitiveWordUtil.contains(para.getPARA_TEXT());
            if (result) {
            	s_num++;
            	para.setSensitivewords(true);
            	//获取语句中的敏感词
                Set<String> set = SensitiveWordUtil.getSensitiveWord(para.getPARA_TEXT());
                System.out.println("本段中包含敏感词的个数为：" + set.size() + "。包含：" + set);
                //替换语句中的敏感词
                String filterStr = SensitiveWordUtil.replaceSensitiveWord(para.getPARA_TEXT(), "[*敏感词*]");
                System.out.println(filterStr);
            }
        });
        System.out.println("文档包含敏感词的个数总计：" + s_num + "。");
        
        System.out.println("\nDone!\n");
        
		/*
		 * 构建树形结构
		 */
        System.out.println("\nBegin make json tree\n");
		
        List<PARA_INFO_T> paras = TreeUtil.makeTree(para_INFO_Ts);
		List<PARA_INFO_T> parasTreelist = TreeUtil.formatTree(paras, false);
		String json = JSONArray.toJSONString(parasTreelist);
		System.out.println(json);
		
		System.out.println("\nDone!\n");
		
		// 插入文档及段落信息
		//insertDB(doc_INFO_T, para_INFO_Ts);

	}

	@SuppressWarnings("unused")
	private static void insertDB(DOC_INFO_T doc_INFO_T, List<PARA_INFO_T> para_INFO_Ts) throws SQLException {
		dpc.setAutoCommit(false); // 开启事务
		ps_doc = (DruidPooledPreparedStatement) dpc.prepareStatement(sql_doc, DruidPooledPreparedStatement.RETURN_GENERATED_KEYS);
		ps_doc.setString(1, doc_INFO_T.getDOC_NAME());
		ps_doc.setString(2, doc_INFO_T.getDOC_AUTHOR());
		ps_doc.setDate(3, doc_INFO_T.getDOC_CREATETIME());
		ps_doc.executeUpdate();
		ResultSet rs = ps_doc.getGeneratedKeys();
		if (rs.next()) {
			id = rs.getInt(1);// 自增长主键
		}
		ps_para = (DruidPooledPreparedStatement) dpc.prepareStatement(sql_para);
		para_INFO_Ts.forEach(paraInfo -> {
			try {
				ps_para.setInt(1, id);
				ps_para.setString(2, paraInfo.getPARA_STYLE_ID());
				ps_para.setString(3, paraInfo.getPARA_VAL());
				ps_para.setString(4, paraInfo.getPARA_TITLE());
				ps_para.setString(5, paraInfo.getPARA_TEXT());
				ps_para.setInt(6, paraInfo.getPARA_STEP());
				ps_para.addBatch();
				ps_para.executeBatch();
				// 提交事务
				dpc.commit();
			} catch (SQLException e) {
				try {
					e.printStackTrace();
					dpc.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		try {
			if (ps_doc != null)
				ps_doc.close();
			if (ps_para != null)
				ps_para.close();
			if (dpc != null)
				dpc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
