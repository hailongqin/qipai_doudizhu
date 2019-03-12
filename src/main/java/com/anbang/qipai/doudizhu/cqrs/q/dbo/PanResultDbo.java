package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanPlayerResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanResult;
import com.dml.doudizhu.pan.PanActionFrame;

public class PanResultDbo {
	private String id;
	private String gameId;
	private int panNo;
	private List<DoudizhuPanPlayerResultDbo> playerResultList;
	private long finishTime;
	private PanActionFrame panActionFrame;
	private GameLatestInfoDbo gameLatestInfoDbo;

	public PanResultDbo() {

	}

	public PanResultDbo(String gameId, DoudizhuPanResult panResult) {
		this.gameId = gameId;
		panNo = panResult.getPan().getNo();
		playerResultList = new ArrayList<>();
		for (DoudizhuPanPlayerResult playerResult : panResult.getPanPlayerResultList()) {
			DoudizhuPanPlayerResultDbo dbo = new DoudizhuPanPlayerResultDbo();
			dbo.setPlayerId(playerResult.getPlayerId());
			dbo.setPlayerResult(playerResult);
			dbo.setPlayer(panResult.findPlayer(playerResult.getPlayerId()));
			playerResultList.add(dbo);
		}
		finishTime = panResult.getPanFinishTime();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<DoudizhuPanPlayerResultDbo> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<DoudizhuPanPlayerResultDbo> playerResultList) {
		this.playerResultList = playerResultList;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public PanActionFrame getPanActionFrame() {
		return panActionFrame;
	}

	public void setPanActionFrame(PanActionFrame panActionFrame) {
		this.panActionFrame = panActionFrame;
	}

	public GameLatestInfoDbo getGameLatestInfoDbo() {
		return gameLatestInfoDbo;
	}

	public void setGameLatestInfoDbo(GameLatestInfoDbo gameLatestInfoDbo) {
		this.gameLatestInfoDbo = gameLatestInfoDbo;
	}

}
