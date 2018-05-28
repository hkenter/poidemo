package cn.wt.poidemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFStyles;

/**
 * Hello world!
 *
 */
public class App {
	/**
	 * word整体样式
	 */
	// private static CTStyles wordStyles = null;
	private static List<XWPFParagraph> wordParagraphs = null;
	private static XWPFStyles wordStyles = null;
	private static XWPFDocument template = null;

	/**
	 * Word整体样式
	 */
	static {
		try {
			// 读取模板文档
			template = new XWPFDocument(
					new FileInputStream("C:\\Users\\Administrator\\Desktop\\poidemo\\广核无目录.docx"));
			// 获得模板文档的整体样式
			// wordStyles = template.getStyle();
			wordParagraphs = template.getParagraphs();
			wordStyles = template.getStyles();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		Set<String> paraNum = new HashSet<String>();;
		wordParagraphs.forEach((s) -> {
			String style = s.getStyle();
			Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
			if (s.getStyleID() != null) {
				if (pattern.matcher(style).matches()) {
					paraNum.add(style);
				}
			}
		});
		paraNum.forEach((n) -> System.out.println(n));
		wordParagraphs.forEach((s) -> {
			String text = s.getParagraphText();
			String style = s.getStyle();
			paraNum.forEach((n) -> {
				if (n.equals(style)) {
					System.out.println("" + text.split("\t")[0]);
					//if (text.split("\t").length > 1)
					//System.out.println("页数：" + text.split("\t")[1]);
				}
			});
			if (s.getStyleID() == null) {
				//System.out.println(text);
			}
		});
		
		System.out.println("========================================");
	}
}
