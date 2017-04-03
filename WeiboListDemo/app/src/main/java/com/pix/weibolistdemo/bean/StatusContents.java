package com.pix.weibolistdemo.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StatusContents  implements Serializable {

	private static final long serialVersionUID = 2115103214814709009L;

	private List<StatusContent> statuses;

	private Long selectedGroupId;

	private int total_number;

	private long length;
	
	public StatusContents() {
		statuses = new ArrayList<StatusContent>();
	}

	public StatusContents(List<StatusContent> statuses) {
		this.statuses = statuses;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<StatusContent> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<StatusContent> statuses) {
		this.statuses = statuses;
	}

	public Long getSelectedGroupId() {
		return selectedGroupId;
	}

	public void setSelectedGroupId(Long selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}

	public int getTotal_number() {
		return total_number;
	}

	public void setTotal_number(int total_number) {
		this.total_number = total_number;
	}

	public long getLength() {
		return length;
	}

}
