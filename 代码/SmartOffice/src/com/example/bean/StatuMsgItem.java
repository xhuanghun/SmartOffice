package com.example.bean;

public class StatuMsgItem {

	private int SkinIconId;
	private String TitleName;
	private String TitleItemName;
	
	private String block1_1;
	private String block1_2;
	private String block1_3;
	
	private String block2_1;
	private String block2_2;
	private String block2_3;
	
	
	public int getSkinIconId() {
		return SkinIconId;
	}
	public void setSkinIconId(int skinIconId) {
		SkinIconId = skinIconId;
	}
	public String getTitleName() {
		return TitleName;
	}
	public void setTitleName(String titleName) {
		TitleName = titleName;
	}
	public String getTitleItemName() {
		return TitleItemName;
	}
	public void setTitleItemName(String titleItemName) {
		TitleItemName = titleItemName;
	}
	public String getBlock1_1() {
		return block1_1;
	}
	public void setBlock1_1(String block1_1) {
		this.block1_1 = block1_1;
	}
	public String getBlock1_2() {
		return block1_2;
	}
	public void setBlock1_2(String block1_2) {
		this.block1_2 = block1_2;
	}
	public String getBlock1_3() {
		return block1_3;
	}
	public void setBlock1_3(String block1_3) {
		this.block1_3 = block1_3;
	}
	public String getBlock2_1() {
		return block2_1;
	}
	public void setBlock2_1(String block2_1) {
		this.block2_1 = block2_1;
	}
	public String getBlock2_2() {
		return block2_2;
	}
	public void setBlock2_2(String block2_2) {
		this.block2_2 = block2_2;
	}
	public String getBlock2_3() {
		return block2_3;
	}
	public void setBlock2_3(String block2_3) {
		this.block2_3 = block2_3;
	}
	public StatuMsgItem(int skinIconId, String titleName, String titleItemName,
			String block1_1, String block1_2, String block1_3, String block2_1,
			String block2_2, String block2_3) {
		super();
		SkinIconId = skinIconId;
		TitleName = titleName;
		TitleItemName = titleItemName;
		this.block1_1 = block1_1;
		this.block1_2 = block1_2;
		this.block1_3 = block1_3;
		this.block2_1 = block2_1;
		this.block2_2 = block2_2;
		this.block2_3 = block2_3;
	}
	
	
	
}
