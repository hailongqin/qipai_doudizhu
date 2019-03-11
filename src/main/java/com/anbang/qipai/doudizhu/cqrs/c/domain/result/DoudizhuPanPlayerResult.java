package com.anbang.qipai.doudizhu.cqrs.c.domain.result;

import com.anbang.qipai.doudizhu.cqrs.c.domain.DoudizhuBeishu;

public class DoudizhuPanPlayerResult {
	private String playerId;
	private boolean ying;
	private int difen;
	private DoudizhuBeishu beishu;
	private int score;// 一盘结算分
	private int totalScore;// 总分

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public boolean isYing() {
		return ying;
	}

	public void setYing(boolean ying) {
		this.ying = ying;
	}

	public int getDifen() {
		return difen;
	}

	public void setDifen(int difen) {
		this.difen = difen;
	}

	public DoudizhuBeishu getBeishu() {
		return beishu;
	}

	public void setBeishu(DoudizhuBeishu beishu) {
		this.beishu = beishu;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

}
