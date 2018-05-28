package cn.wt.poidemo.entity;

import java.sql.Date;
import java.util.List;

public class DOC_INFO_T {
	private int ID;
	private String DOC_NAME;
	private String DOC_AUTHOR;
	private Date DOC_CREATETIME;
	private List<PARA_INFO_T> para_INFO_Ts;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getDOC_NAME() {
		return DOC_NAME;
	}
	public void setDOC_NAME(String dOC_NAME) {
		DOC_NAME = dOC_NAME;
	}
	public String getDOC_AUTHOR() {
		return DOC_AUTHOR;
	}
	public void setDOC_AUTHOR(String dOC_AUTHOR) {
		DOC_AUTHOR = dOC_AUTHOR;
	}
	public Date getDOC_CREATETIME() {
		return DOC_CREATETIME;
	}
	public void setDOC_CREATETIME(Date dOC_CREATETIME) {
		DOC_CREATETIME = dOC_CREATETIME;
	}
	public List<PARA_INFO_T> getPara_INFO_Ts() {
		return para_INFO_Ts;
	}
	public void setPara_INFO_Ts(List<PARA_INFO_T> para_INFO_Ts) {
		this.para_INFO_Ts = para_INFO_Ts;
	}
	@Override
	public String toString() {
		return "DOC_INFO_T [ID=" + ID + ", DOC_NAME=" + DOC_NAME + ", DOC_AUTHOR=" + DOC_AUTHOR + ", DOC_CREATETIME="
				+ DOC_CREATETIME + ", para_INFO_Ts=" + para_INFO_Ts + "]";
	}
	
}
