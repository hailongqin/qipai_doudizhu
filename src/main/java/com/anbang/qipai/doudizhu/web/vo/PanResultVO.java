package com.anbang.qipai.doudizhu.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.DoudizhuPanPlayerResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;

public class PanResultVO {

	private List<DoudizhuPanPlayerResultVO> playerResultList;

	private boolean dizhuying;

	private int panNo;

	private long finishTime;

	private PanActionFrameVO lastPanActionFrame;

	private GameInfoVO gameInfoVO;

	public PanResultVO() {

	}

	public PanResultVO(PanResultDbo panResultDbo, PukeGameDbo pukeGameDbo) {
		List<DoudizhuPanPlayerResultDbo> list = panResultDbo.getPlayerResultList();
		playerResultList = new ArrayList<>();
		if (list != null) {
			list.forEach((panPlayerResult) -> {
				playerResultList.add(new DoudizhuPanPlayerResultVO(
						pukeGameDbo.findPlayer(panPlayerResult.getPlayerId()), panPlayerResult));
			});
		}
		dizhuying = panResultDbo.isDizhuying();
		panNo = panResultDbo.getPanNo();
		finishTime = panResultDbo.getFinishTime();
		lastPanActionFrame = new PanActionFrameVO(panResultDbo.getPanActionFrame());
		gameInfoVO = new GameInfoVO(panResultDbo.getGameLatestInfoDbo());
	}

	public List<DoudizhuPanPlayerResultVO> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<DoudizhuPanPlayerResultVO> playerResultList) {
		this.playerResultList = playerResultList;
	}

	public boolean isDizhuying() {
		return dizhuying;
	}

	public void setDizhuying(boolean dizhuying) {
		this.dizhuying = dizhuying;
	}

	public int getPanNo() {
		return panNo;
	}

	public void setPanNo(int panNo) {
		this.panNo = panNo;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public PanActionFrameVO getLastPanActionFrame() {
		return lastPanActionFrame;
	}

	public void setLastPanActionFrame(PanActionFrameVO lastPanActionFrame) {
		this.lastPanActionFrame = lastPanActionFrame;
	}

	public GameInfoVO getGameInfoVO() {
		return gameInfoVO;
	}

	public void setGameInfoVO(GameInfoVO gameInfoVO) {
		this.gameInfoVO = gameInfoVO;
	}

}
