package com.anbang.qipai.doudizhu.web.vo;

import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestInfoDbo;
import com.dml.puke.pai.PukePai;

public class GameInfoVO {
	private int beishu;
	private List<PukePai> dipaiList;

	public GameInfoVO(GameLatestInfoDbo info) {
		beishu = info.getBeishu();
		dipaiList = info.getDipaiList();
	}

	public GameInfoVO() {

	}

	public int getBeishu() {
		return beishu;
	}

	public void setBeishu(int beishu) {
		this.beishu = beishu;
	}

	public List<PukePai> getDipaiList() {
		return dipaiList;
	}

	public void setDipaiList(List<PukePai> dipaiList) {
		this.dipaiList = dipaiList;
	}

}
