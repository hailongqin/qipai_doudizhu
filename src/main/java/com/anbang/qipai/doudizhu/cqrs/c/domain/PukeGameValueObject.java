package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.HashMap;
import java.util.Map;

import com.dml.doudizhu.ju.JuResult;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGameValueObject;

public class PukeGameValueObject extends FixedPlayersMultipanAndVotetofinishGameValueObject {

	private int panshu;
	private int renshu;
	private boolean qxp;// 去小牌
	private int difen;
	private Map<String, Integer> playerTotalScoreMap = new HashMap<>();
	private JuResult juResult;

	public PukeGameValueObject(PukeGame pukeGame) {
		super(pukeGame);
		panshu = pukeGame.getPanshu();
		renshu = pukeGame.getRenshu();
		qxp = pukeGame.isQxp();
		difen = pukeGame.getDifen();
		playerTotalScoreMap.putAll(pukeGame.getPlayerTotalScoreMap());
		if (pukeGame.getJu() != null) {
			juResult = pukeGame.getJu().getJuResult();
		}
	}

	public int getDifen() {
		return difen;
	}

	public void setDifen(int difen) {
		this.difen = difen;
	}

	public JuResult getJuResult() {
		return juResult;
	}

	public void setJuResult(JuResult juResult) {
		this.juResult = juResult;
	}

	public int getPanshu() {
		return panshu;
	}

	public void setPanshu(int panshu) {
		this.panshu = panshu;
	}

	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}

	public boolean isQxp() {
		return qxp;
	}

	public void setQxp(boolean qxp) {
		this.qxp = qxp;
	}

	public Map<String, Integer> getPlayerTotalScoreMap() {
		return playerTotalScoreMap;
	}

	public void setPlayerTotalScoreMap(Map<String, Integer> playerTotalScoreMap) {
		this.playerTotalScoreMap = playerTotalScoreMap;
	}

}
