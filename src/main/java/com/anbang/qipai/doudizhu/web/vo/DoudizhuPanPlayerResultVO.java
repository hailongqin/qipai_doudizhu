package com.anbang.qipai.doudizhu.web.vo;

import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.DoudizhuPanPlayerResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGamePlayerDbo;
import com.dml.doudizhu.player.DoudizhuPlayerValueObject;

public class DoudizhuPanPlayerResultVO {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private DoudizhuPlayerShoupaiVO allShoupai;
	private int difen;
	private int beishu;
	private int score;// 一盘结算分
	private int totalScore;// 总分

	public DoudizhuPanPlayerResultVO() {

	}

	public DoudizhuPanPlayerResultVO(PukeGamePlayerDbo playerDbo, DoudizhuPanPlayerResultDbo panPlayerResult,
			DoudizhuPlayerValueObject playerValueObject) {
		playerId = playerDbo.getPlayerId();
		nickname = playerDbo.getNickname();
		headimgurl = playerDbo.getHeadimgurl();
		List<List<Integer>> shoupaiIdListForSortList = playerValueObject.getShoupaiIdListForSortList();
		if (shoupaiIdListForSortList == null || shoupaiIdListForSortList.isEmpty()) {
			allShoupai = new DoudizhuPlayerShoupaiVO(playerValueObject.getAllShoupai(),
					playerValueObject.getTotalShoupai(), null);
		} else {
			allShoupai = new DoudizhuPlayerShoupaiVO(playerValueObject.getAllShoupai(),
					playerValueObject.getTotalShoupai(), shoupaiIdListForSortList.get(0));
		}
		difen = panPlayerResult.getPlayerResult().getDifen();
		beishu = panPlayerResult.getPlayerResult().getBeishu().getValue();
		score = panPlayerResult.getPlayerResult().getScore();
		totalScore = panPlayerResult.getPlayerResult().getTotalScore();
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

	public DoudizhuPlayerShoupaiVO getAllShoupai() {
		return allShoupai;
	}

	public void setAllShoupai(DoudizhuPlayerShoupaiVO allShoupai) {
		this.allShoupai = allShoupai;
	}

	public int getDifen() {
		return difen;
	}

	public void setDifen(int difen) {
		this.difen = difen;
	}

	public int getBeishu() {
		return beishu;
	}

	public void setBeishu(int beishu) {
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
