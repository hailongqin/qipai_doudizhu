package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.GameInfo;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;
import com.dml.puke.pai.PukePai;

public class GameLatestInfoDbo {
	private String id;
	private int beishu;
	private List<PukePai> dipaiList;
	private List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos;

	public GameLatestInfoDbo() {

	}

	public GameLatestInfoDbo(PukeGameValueObject pukeGame, Map<String, PlayerQiangdizhuState> playerQiangdizhuMap,
			GameInfo gameInfo) {
		id = pukeGame.getId();
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
