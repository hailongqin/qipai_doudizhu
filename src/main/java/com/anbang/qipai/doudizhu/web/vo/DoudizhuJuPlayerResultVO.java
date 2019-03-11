package com.anbang.qipai.doudizhu.web.vo;

import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuJuPlayerResult;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGamePlayerDbo;

public class DoudizhuJuPlayerResultVO {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private int yingCount;
	private int fanchuntianCount;
	private int chuntianCount;
	private int maxBeishu;
	private int totalScore;

	public DoudizhuJuPlayerResultVO(PukeGamePlayerDbo playerDbo) {
		playerId = playerDbo.getPlayerId();
		nickname = playerDbo.getNickname();
		headimgurl = playerDbo.getHeadimgurl();
		yingCount = 0;
		fanchuntianCount = 0;
		chuntianCount = 0;
		maxBeishu = 0;
		totalScore = 0;
	}

	public DoudizhuJuPlayerResultVO(DoudizhuJuPlayerResult juPlayerResult, PukeGamePlayerDbo playerDbo) {
		playerId = playerDbo.getPlayerId();
		nickname = playerDbo.getNickname();
		headimgurl = playerDbo.getHeadimgurl();
		yingCount = juPlayerResult.getYingCount();
		fanchuntianCount = juPlayerResult.getFanchuntianCount();
		chuntianCount = juPlayerResult.getChuntianCount();
		maxBeishu = juPlayerResult.getMaxBeishu();
		totalScore = juPlayerResult.getTotalScore();
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public int getYingCount() {
		return yingCount;
	}

	public void setYingCount(int yingCount) {
		this.yingCount = yingCount;
	}

	public int getFanchuntianCount() {
		return fanchuntianCount;
	}

	public void setFanchuntianCount(int fanchuntianCount) {
		this.fanchuntianCount = fanchuntianCount;
	}

	public int getChuntianCount() {
		return chuntianCount;
	}

	public void setChuntianCount(int chuntianCount) {
		this.chuntianCount = chuntianCount;
	}

	public int getMaxBeishu() {
		return maxBeishu;
	}

	public void setMaxBeishu(int maxBeishu) {
		this.maxBeishu = maxBeishu;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

}
