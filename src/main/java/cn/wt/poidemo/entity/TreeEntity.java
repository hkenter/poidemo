package cn.wt.poidemo.entity;

import java.io.Serializable;
import java.util.List;

public class TreeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public String id;// 节点id

	public String parentId;// 节点父id

	public String iconCls = "folder";// 节点样式，默认即可

	public Boolean leaf = true;// 是否为叶子节点，true表示是叶子节点，false表示不是叶子节点

	public Boolean expanded = true; // 是否展开，默认true,展开

	public List<TreeEntity> children;// 孩子节点

	public TreeEntity() {

	}

	public TreeEntity(String id, String parentId) {
		this.id = id;
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public List<TreeEntity> getChildren() {
		return children;
	}

	public void setChildren(List<TreeEntity> children) {
		this.children = children;
	}
}
