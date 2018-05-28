package cn.wt.poidemo.dao;

import cn.wt.poidemo.entity.DOC_INFO_T;

public interface IDocDao {
	/**
	 * save一份文档的解析内容（文档属性）
	 * @param doc
	 * @return
	 */
	public int  saveDoc(DOC_INFO_T doc);
}
