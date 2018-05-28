package cn.wt.poidemo.entity;

public class PARA_INFO_T extends TreeEntity  {
	private static final long serialVersionUID = 1L;
	private int ID;
	private int DOC_ID;
	private String PARA_STYLE_ID;
	private String PARA_VAL;
	private String PARA_TITLE;
	private String PARA_TEXT;
	private int PARA_STEP;
	private boolean isSensitivewords = false;;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getDOC_ID() {
		return DOC_ID;
	}
	public void setDOC_ID(int dOC_ID) {
		DOC_ID = dOC_ID;
	}
	public String getPARA_STYLE_ID() {
		return PARA_STYLE_ID;
	}
	public void setPARA_STYLE_ID(String pARA_STYLE_ID) {
		PARA_STYLE_ID = pARA_STYLE_ID;
	}
	public String getPARA_VAL() {
		return PARA_VAL;
	}
	public void setPARA_VAL(String pARA_VAL) {
		PARA_VAL = pARA_VAL;
	}
	public String getPARA_TITLE() {
		return PARA_TITLE;
	}
	public void setPARA_TITLE(String pARA_TITLE) {
		PARA_TITLE = pARA_TITLE;
	}
	public String getPARA_TEXT() {
		return PARA_TEXT;
	}
	public void setPARA_TEXT(String pARA_TEXT) {
		PARA_TEXT = pARA_TEXT;
	}
	public int getPARA_STEP() {
		return PARA_STEP;
	}
	public void setPARA_STEP(int pARA_STEP) {
		PARA_STEP = pARA_STEP;
	}
	public boolean isSensitivewords() {
		return isSensitivewords;
	}
	public void setSensitivewords(boolean isSensitivewords) {
		this.isSensitivewords = isSensitivewords;
	}
	@Override
	public String toString() {
		return "PARA_INFO_T [ID=" + ID + ", DOC_ID=" + DOC_ID + ", PARA_STYLE_ID=" + PARA_STYLE_ID + ", PARA_VAL="
				+ PARA_VAL + ", PARA_TITLE=" + PARA_TITLE + ", PARA_TEXT=" + PARA_TEXT + ", PARA_STEP=" + PARA_STEP
				+ "]";
	}
	
}
