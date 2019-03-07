package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;

public class QiangdizhuInfoDbo {
	private String id;
	private String gameId;
	private int panNo;
	private List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos;

	public QiangdizhuInfoDbo() {

	}

	public QiangdizhuInfoDbo(PukeGameValueObject pukeGame, Map<String, PlayerQiangdizhuState> playerQiangdizhuMap) {
		gameId = pukeGame.getId();
		panNo = pukeGame.getPanNo();
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

	public List<PlayerQiangdizhuInfoDbo> getPlayerQiangdizhuInfos() {
		return playerQiangdizhuInfos;
	}

	public void setPlayerQiangdizhuInfos(List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos) {
		this.playerQiangdizhuInfos = playerQiangdizhuInfos;
	}

}
