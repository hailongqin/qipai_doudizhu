package com.anbang.qipai.doudizhu.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.DoudizhuPanPlayerResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;
import com.dml.doudizhu.player.DoudizhuPlayerValueObject;

public class PanResultVO {

	private List<DoudizhuPanPlayerResultVO> playerResultList;

	private int panNo;

	private long finishTime;

	private PanActionFrameVO lastPanActionFrame;

	public PanResultVO() {

	}

	public PanResultVO(PanResultDbo panResultDbo, PukeGameDbo pukeGameDbo) {
		List<DoudizhuPanPlayerResultDbo> list = panResultDbo.getPlayerResultList();
		List<DoudizhuPlayerValueObject> players = panResultDbo.getPanActionFrame().getPanAfterAction()
				.getDoudizhuPlayerList();
		if (list != null) {
			playerResultList = new ArrayList<>(list.size());
			list.forEach((panPlayerResult) -> {
				DoudizhuPlayerValueObject doudizhuPlayer = null;
				for (DoudizhuPlayerValueObject player : players) {
					if (player.getId().equals(panPlayerResult.getPlayerId())) {
						doudizhuPlayer = player;
						break;
					}
				}
				playerResultList.add(new DoudizhuPanPlayerResultVO(
						pukeGameDbo.findPlayer(panPlayerResult.getPlayerId()), panPlayerResult, doudizhuPlayer));
			});
		}
		panNo = panResultDbo.getPanNo();
		finishTime = panResultDbo.getFinishTime();
		lastPanActionFrame = new PanActionFrameVO(panResultDbo.getPanActionFrame());
	}

	public List<DoudizhuPanPlayerResultVO> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<DoudizhuPanPlayerResultVO> playerResultList) {
		this.playerResultList = playerResultList;
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

}
