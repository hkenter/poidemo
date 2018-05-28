package cn.wt.poidemo.dao;

import cn.wt.poidemo.entity.PARA_INFO_T;

public interface IParaDao {
	/**
	 * save一份文档的解析内容（段落）
	 * @param para
	 * @return
	 */
	public int savePara(PARA_INFO_T para);
}
