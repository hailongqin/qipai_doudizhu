package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.GameInfo;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;
import com.dml.puke.pai.PukePai;

public class GameInfoDbo {
	private String id;
	private String gameId;
	private int panNo;
	private int actionNo;
	private long actionTime;
	private int beishu;
	private List<PukePai> dipaiList;
	private List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos;

	public GameInfoDbo() {

	}

	public GameInfoDbo(PukeGameValueObject pukeGame, Map<String, PlayerQiangdizhuState> playerQiangdizhuMap,
			GameInfo gameInfo, int actionNo) {
		gameId = pukeGame.getId();
		panNo = pukeGame.getPanNo();
		this.actionNo = actionNo;
		actionTime = gameInfo.getActionTime();
		beishu = gameInfo.getBeishu();
		dipaiList = gameInfo.getDipaiList();
		playerQiangdizhuInfos = new ArrayList<>();
		for (String playerId : playerQiangdizhuMap.keySet()) {
			PlayerQiangdizhuInfoDbo player = new PlayerQiangdizhuInfoDbo();
			player.setPlayerId(playerId);
			player.setState(playerQiangdizhuMap.get(playerId));
			playerQiangdizhuInfos.add(player);
		}
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

	public int getActionNo() {
		return actionNo;
	}

	public void setActionNo(int actionNo) {
		this.actionNo = actionNo;
	}

	public long getActionTime() {
		return actionTime;
	}

	public void setActionTime(long actionTime) {
		this.actionTime = actionTime;
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
