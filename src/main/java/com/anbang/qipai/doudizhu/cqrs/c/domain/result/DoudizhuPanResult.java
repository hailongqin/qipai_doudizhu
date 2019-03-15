package com.anbang.qipai.doudizhu.cqrs.c.domain.result;

import java.util.List;

import com.dml.doudizhu.pan.PanResult;

public class DoudizhuPanResult extends PanResult {

	private boolean dizhuying;

	private List<DoudizhuPanPlayerResult> panPlayerResultList;

	public List<DoudizhuPanPlayerResult> getPanPlayerResultList() {
		return panPlayerResultList;
	}

	public void setPanPlayerResultList(List<DoudizhuPanPlayerResult> panPlayerResultList) {
		this.panPlayerResultList = panPlayerResultList;
	}

	public boolean isDizhuying() {
		return dizhuying;
	}

	public void setDizhuying(boolean dizhuying) {
		this.dizhuying = dizhuying;
	}

}
