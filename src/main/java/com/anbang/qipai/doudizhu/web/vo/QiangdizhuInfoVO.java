package com.anbang.qipai.doudizhu.web.vo;

import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.PlayerQiangdizhuInfoDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.QiangdizhuInfoDbo;

public class QiangdizhuInfoVO {
	private String gameId;
	private int panNo;
	private List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos;

	public QiangdizhuInfoVO() {

	}

	public QiangdizhuInfoVO(QiangdizhuInfoDbo info) {
		gameId = info.getGameId();
		panNo = info.getPanNo();
		playerQiangdizhuInfos = info.getPlayerQiangdizhuInfos();
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public int getPanNo() {
		return panNo;
	}

	public void setPanNo(int panNo) {
		this.panNo = panNo;
	}

	public List<PlayerQiangdizhuInfoDbo> getPlayerQiangdizhuInfos() {
		return playerQiangdizhuInfos;
	}

	public void setPlayerQiangdizhuInfos(List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos) {
		this.playerQiangdizhuInfos = playerQiangdizhuInfos;
	}
}
