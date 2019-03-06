package com.anbang.qipai.doudizhu.cqrs.q.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.doudizhu.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.ReadyForGameResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.doudizhu.cqrs.q.dao.GameLatestPanActionFrameDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.JuResultDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.PanActionFrameDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.PanResultDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.PukeGameDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PanActionFrameDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.doudizhu.plan.dao.PlayerInfoDao;
import com.dml.doudizhu.pan.PanActionFrame;

@Service
public class PukePlayQueryService {

	@Autowired
	private PukeGameDboDao pukeGameDboDao;

	@Autowired
	private PlayerInfoDao playerInfoDao;

	@Autowired
	private PanResultDboDao panResultDboDao;

	@Autowired
	private JuResultDboDao juResultDboDao;

	@Autowired
	private PanActionFrameDboDao panActionFrameDboDao;

	@Autowired
	private GameLatestPanActionFrameDboDao gameLatestPanActionFrameDboDao;

	public PanActionFrame findAndFilterCurrentPanValueObjectForPlayer(String gameId, String playerId) throws Exception {
		PukeGameDbo pukeGameDbo = pukeGameDboDao.findById(gameId);

		return null;
	}

	public void readyForGame(ReadyForGameResult readyForGameResult) throws Throwable {

	}

	public void action(PukeActionResult pukeActionResult) throws Throwable {

	}

	public void readyToNextPan(ReadyToNextPanResult readyToNextPanResult) throws Throwable {

	}

	public PanResultDbo findPanResultDbo(String gameId, int panNo) {
		return panResultDboDao.findByGameIdAndPanNo(gameId, panNo);
	}

	public JuResultDbo findJuResultDbo(String gameId) {
		return juResultDboDao.findByGameId(gameId);
	}

	public List<PanActionFrameDbo> findPanActionFrameDboForBackPlay(String gameId, int panNo) {
		return panActionFrameDboDao.findByGameIdAndPanNo(gameId, panNo);
	}
}
