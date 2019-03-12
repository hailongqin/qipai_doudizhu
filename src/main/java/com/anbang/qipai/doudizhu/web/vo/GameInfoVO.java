package com.anbang.qipai.doudizhu.web.vo;

import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestInfoDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PlayerQiangdizhuInfoDbo;
import com.dml.puke.pai.PukePai;

public class GameInfoVO {
	private int beishu;
	private List<PukePai> dipaiList;
	private List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos;

	public GameInfoVO() {

	}

	public GameInfoVO(GameLatestInfoDbo gameLatestInfoDbo) {
		beishu = gameLatestInfoDbo.getBeishu();
		dipaiList = gameLatestInfoDbo.getDipaiList();
		playerQiangdizhuInfos = gameLatestInfoDbo.getPlayerQiangdizhuInfos();
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

	public List<PlayerQiangdizhuInfoDbo> getPlayerQiangdizhuInfos() {
		return playerQiangdizhuInfos;
	}

	public void setPlayerQiangdizhuInfos(List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos) {
		this.playerQiangdizhuInfos = playerQiangdizhuInfos;
	}

}
