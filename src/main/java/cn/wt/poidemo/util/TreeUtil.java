package cn.wt.poidemo.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

import cn.wt.poidemo.entity.PARA_INFO_T;
import cn.wt.poidemo.entity.TreeEntity;

public class TreeUtil {
	private static String style_id = null;
	private static int step = 0;
	private static int i = 1;

	/**
	 * 分析para_INFO_Ts内的step与styleId,将treeid与treeparentid写入
	 * @param para_INFO_Ts
	 * @return
	 */
	public static List<PARA_INFO_T> makeTree(List<PARA_INFO_T> para_INFO_Ts) {
		List<PARA_INFO_T> paras_nosort = new ArrayList<PARA_INFO_T>(para_INFO_Ts);
		para_INFO_Ts.sort((para1, para2) -> para1.getPARA_STYLE_ID().compareTo(para2.getPARA_STYLE_ID()));
		List<PARA_INFO_T> list = new ArrayList<PARA_INFO_T>();
		String rootStyleId = para_INFO_Ts.get(0).getPARA_STYLE_ID();
		
		para_INFO_Ts.forEach(paraInfo -> {
			//System.out.println(paraInfo.getPARA_TITLE());
			style_id = paraInfo.getPARA_STYLE_ID();
			step = paraInfo.getPARA_STEP();
			
			if (paraInfo.getPARA_STYLE_ID().equals(rootStyleId)) {
				paraInfo.setId(String.valueOf(paraInfo.getPARA_STEP()));
				list.add(paraInfo);
			}
			/**
			 * 原则上只处理直系儿子,孙子等无视
			 */
			for (int j = step;j < paras_nosort.size();j++) {
				PARA_INFO_T para_nosort = paras_nosort.get(j);
				if (Integer.valueOf(para_nosort.getPARA_STYLE_ID()) == Integer.valueOf(style_id) + 1) { // 存在直系子节点
					paras_nosort.get(j).setId(String.valueOf(para_nosort.getPARA_STEP()));
					paras_nosort.get(j).setParentId(String.valueOf(paraInfo.getPARA_STEP()));
					list.add(paras_nosort.get(j));
				} else if (Integer.valueOf(para_nosort.getPARA_STYLE_ID()) == Integer.valueOf(style_id)) {
					// 遇见同级节点 do nothing 可以打断了
					break;
				} else if (Integer.valueOf(para_nosort.getPARA_STYLE_ID()) < Integer.valueOf(style_id)) {
					// 遇见父节点 do nothing 可以打断了
					break;
				} else if (Integer.valueOf(para_nosort.getPARA_STYLE_ID()) > Integer.valueOf(style_id) + 1) {
					// 遇见子节点的儿孙 do nothing 还可能存在儿子，继续遍历
				}
			}
			i++;
		});
		i = 1;
		list.forEach(a -> {
			System.out.println(a.getPARA_TITLE() + "tree:id=" + a.getId() + ",parentId=" + a.getParentId());
		});
		return list;
	}

	/**
	 * 格式化list为树形list
	 * 
	 * @param list
	 * @param falg
	 *            true 表示全部展开，其他 表示不展开
	 * @return
	 */
	public static <T extends TreeEntity> List<T> formatTree(List<T> list, Boolean flag) {
		List<T> nodeList = new ArrayList<T>();
		for (T node1 : list) {
			boolean mark = false;
			for (T node2 : list) {
				if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
					node2.setLeaf(false);
					mark = true;
					if (node2.getChildren() == null) {
						node2.setChildren(new ArrayList<TreeEntity>());
					}
					node2.getChildren().add(node1);
					if (flag) {
						// 默认已经全部展开
					} else {
						node2.setExpanded(false);
					}
					break;
				}
			}
			if (!mark) {
				nodeList.add(node1);
				if (flag) {
					// 默认已经全部展开
				} else {
					node1.setExpanded(false);
				}
			}
		}
		return nodeList;
	}

	public static void main(String[] args) {
		List<PARA_INFO_T> list = new ArrayList<PARA_INFO_T>();
		PARA_INFO_T root1 = new PARA_INFO_T();
		root1.setId("1");
		PARA_INFO_T child1 = new PARA_INFO_T();
		child1.setId("11");
		child1.setParentId("1");
		PARA_INFO_T child11 = new PARA_INFO_T();
		child11.setId("111");
		child11.setParentId("11");
		PARA_INFO_T root2 = new PARA_INFO_T();
		root2.setId("2");
		PARA_INFO_T child2 = new PARA_INFO_T();
		child2.setId("21");
		child2.setParentId("2");
		list.add(root1);
		list.add(child1);
		list.add(child11);
		list.add(root2);
		list.add(child2);
		List<PARA_INFO_T> treelist = formatTree(list, false);
		String json = JSONArray.toJSONString(treelist);
		System.out.println(json);
	}
}
