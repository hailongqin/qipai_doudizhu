package com.anbang.qipai.doudizhu.cqrs.c.domain.result;

import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.GameInfo;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;
import com.dml.doudizhu.pan.PanActionFrame;

public class PukeActionResult {
	private PukeGameValueObject pukeGame;
	private GameInfo gameInfo;
	private PanActionFrame panActionFrame;
	private DoudizhuPanResult panResult;
	private DoudizhuJuResult juResult;
	private Map<String, PlayerQiangdizhuState> playerQiangdizhuMap;

	public PukeGameValueObject getPukeGame() {
		return pukeGame;
	}

	public void setPukeGame(PukeGameValueObject pukeGame) {
		this.pukeGame = pukeGame;
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}

	public void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}

	public PanActionFrame getPanActionFrame() {
		return panActionFrame;
	}

	public void setPanActionFrame(PanActionFrame panActionFrame) {
		this.panActionFrame = panActionFrame;
	}

	public DoudizhuPanResult getPanResult() {
		return panResult;
	}

	public void setPanResult(DoudizhuPanResult panResult) {
		this.panResult = panResult;
	}

	public DoudizhuJuResult getJuResult() {
		return juResult;
	}

	public void setJuResult(DoudizhuJuResult juResult) {
		this.juResult = juResult;
	}

	public Map<String, PlayerQiangdizhuState> getPlayerQiangdizhuMap() {
		return playerQiangdizhuMap;
	}

	public void setPlayerQiangdizhuMap(Map<String, PlayerQiangdizhuState> playerQiangdizhuMap) {
		this.playerQiangdizhuMap = playerQiangdizhuMap;
	}

}
