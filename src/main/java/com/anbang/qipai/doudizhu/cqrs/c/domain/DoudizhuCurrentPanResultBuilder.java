package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.listener.ChuntainAndFanchuntianOpportunityDetector;
import com.anbang.qipai.doudizhu.cqrs.c.domain.listener.ZhadanDaActionStatisticsListener;
import com.anbang.qipai.doudizhu.cqrs.c.domain.qiangdizhu.QiangdizhuDizhuDeterminer;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanPlayerResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanResult;
import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.CurrentPanResultBuilder;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.pan.PanResult;
import com.dml.doudizhu.pan.PanValueObject;
import com.dml.doudizhu.player.DoudizhuPlayer;
import com.dml.doudizhu.preparedapai.dipai.SanzhangDipaiDeterminer;

public class DoudizhuCurrentPanResultBuilder implements CurrentPanResultBuilder {

	private int renshu;
	private int difen;

	@Override
	public PanResult buildCurrentPanResult(Ju ju, long panFinishTime) {
		DoudizhuPanResult latestFinishedPanResult = (DoudizhuPanResult) ju.findLatestFinishedPanResult();
		Map<String, Integer> playerTotalScoreMap = new HashMap<>();
		if (latestFinishedPanResult != null) {
			for (DoudizhuPanPlayerResult panPlayerResult : latestFinishedPanResult.getPanPlayerResultList()) {
				playerTotalScoreMap.put(panPlayerResult.getPlayerId(), panPlayerResult.getTotalScore());
			}
		}
		Pan currentPan = ju.getCurrentPan();
		String dizhu = currentPan.getDizhuPlayerId();
		boolean dizhuying = false;
		DoudizhuPlayer dizhuPlayer = currentPan.findDizhu();
		if (dizhuPlayer.getAllShoupai().size() == 0) {
			dizhuying = true;
		}

		ChuntainAndFanchuntianOpportunityDetector chuntainAndFanchuntianOpportunityDetector = ju
				.getActionStatisticsListenerManager().findDaListener(ChuntainAndFanchuntianOpportunityDetector.class);
		ZhadanDaActionStatisticsListener zhadanDaActionStatisticsListener = ju.getActionStatisticsListenerManager()
				.findDaListener(ZhadanDaActionStatisticsListener.class);
		QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = (QiangdizhuDizhuDeterminer) ju.getDizhuDeterminer();
		SanzhangDipaiDeterminer sanzhangDipaiDeterminer = (SanzhangDipaiDeterminer) ju.getDipaiDeterminer();
		DoudizhuBeishu beishu = new DoudizhuBeishu();
		beishu.setRenshu(renshu);
		beishu.setQiangdizhuCount(qiangdizhuDizhuDeterminer.getQiangdizhuCount());
		beishu.setDipaiHasDuiA(sanzhangDipaiDeterminer.dipaiHasDuiA());
		beishu.setDipaiHasDuier(sanzhangDipaiDeterminer.dipaiHasDuier());
		beishu.setDipaiTonghua(sanzhangDipaiDeterminer.dipaiIsTonghua());
		beishu.setDipaiShunzi(sanzhangDipaiDeterminer.dipaiIsShunzi());
		beishu.setDipaiTongdianshu(sanzhangDipaiDeterminer.dipaiIsTongdianshu());
		beishu.setDipaiXiaoyuShi(sanzhangDipaiDeterminer.dipaiXiaoyushi());
		beishu.setDipaihasXiaowang(sanzhangDipaiDeterminer.dipaiHasXiaowang());
		beishu.setDipaihasDawang(sanzhangDipaiDeterminer.dipaiHasDawang());
		beishu.setDachuZhadanCount(zhadanDaActionStatisticsListener.getDachuZhadanCount());
		beishu.setChuntian(chuntainAndFanchuntianOpportunityDetector.isChuntian());
		beishu.setFanchuntian(chuntainAndFanchuntianOpportunityDetector.isFanChuntian());
		beishu.calculate();

		Map<String, Integer> playerZhadanCountMap = zhadanDaActionStatisticsListener.getPlayerZhadanCountMap();
		List<DoudizhuPanPlayerResult> panPlayerResultList = new ArrayList<>();
		if (dizhuying) {// 地主赢
			for (DoudizhuPlayer player : currentPan.getDoudizhuPlayerIdPlayerMap().values()) {
				DoudizhuPanPlayerResult playerResult = new DoudizhuPanPlayerResult();
				if (dizhu.equals(player.getId())) {
					playerResult.setPlayerId(player.getId());
					playerResult.setYing(true);
					playerResult.setDizhu(true);
					playerResult.setDifen(difen);
					playerResult.setBeishu(beishu);
					playerResult.setScore((renshu - 1) * difen * beishu.getValue());
					Integer count = playerZhadanCountMap.get(player.getId());
					if (count == null) {
						count = 0;
					}
					playerResult.setZhadanCount(count);
				} else {
					playerResult.setPlayerId(player.getId());
					playerResult.setDifen(difen);
					playerResult.setBeishu(beishu);
					playerResult.setScore(-1 * difen * beishu.getValue());
					Integer count = playerZhadanCountMap.get(player.getId());
					if (count == null) {
						count = 0;
					}
					playerResult.setZhadanCount(count);
				}
				panPlayerResultList.add(playerResult);
			}
		} else {// 农民赢
			for (DoudizhuPlayer player : currentPan.getDoudizhuPlayerIdPlayerMap().values()) {
				DoudizhuPanPlayerResult playerResult = new DoudizhuPanPlayerResult();
				if (dizhu.equals(player.getId())) {
					playerResult.setPlayerId(player.getId());
					playerResult.setDizhu(true);
					playerResult.setDifen(difen);
					playerResult.setBeishu(beishu);
					playerResult.setScore(-(renshu - 1) * difen * beishu.getValue());
					Integer count = playerZhadanCountMap.get(player.getId());
					if (count == null) {
						count = 0;
					}
					playerResult.setZhadanCount(count);
				} else {
					playerResult.setPlayerId(player.getId());
					playerResult.setYing(true);
					playerResult.setDifen(difen);
					playerResult.setBeishu(beishu);
					playerResult.setScore(difen * beishu.getValue());
					Integer count = playerZhadanCountMap.get(player.getId());
					if (count == null) {
						count = 0;
					}
					playerResult.setZhadanCount(count);
				}
				panPlayerResultList.add(playerResult);
			}
		}
		panPlayerResultList.forEach((playerResult) -> {
			// 计算累计总分
			if (latestFinishedPanResult != null) {
				playerResult
						.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
			} else {
				playerResult.setTotalScore(playerResult.getScore());
			}
		});
		DoudizhuPanResult panResult = new DoudizhuPanResult();
		panResult.setYingjiaPlayerId(currentPan.getYingjiaPlayerId());
		panResult.setDizhuying(dizhuying);
		panResult.setPan(new PanValueObject(currentPan));
		panResult.setPanPlayerResultList(panPlayerResultList);
		panResult.setPanFinishTime(panFinishTime);
		return panResult;
	}

	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}

	public int getDifen() {
		return difen;
	}

	public void setDifen(int difen) {
		this.difen = difen;
	}

}
