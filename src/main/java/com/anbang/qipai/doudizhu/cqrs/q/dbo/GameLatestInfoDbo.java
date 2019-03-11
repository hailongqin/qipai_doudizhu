package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.c.domain.GameInfo;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.dml.puke.pai.PukePai;

public class GameLatestInfoDbo {
	private String gameId;
	private int beishu;
	private List<PukePai> dipaiList;

	public GameLatestInfoDbo() {

	}

	public GameLatestInfoDbo(PukeGameValueObject pukeGame, GameInfo gameInfo) {
		gameId = pukeGame.getId();
		beishu = gameInfo.getBeishu();
		dipaiList = gameInfo.getDipaiList();
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
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
