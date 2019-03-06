package com.anbang.qipai.doudizhu.cqrs.q.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.q.dao.GameFinishVoteDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.JuResultDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.PukeGameDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameFinishVoteDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.doudizhu.plan.dao.PlayerInfoDao;

@Service
public class PukeGameQueryService {

	@Autowired
	private PukeGameDboDao pukeGameDboDao;

	@Autowired
	private PlayerInfoDao playerInfoDao;

	@Autowired
	private GameFinishVoteDboDao gameFinishVoteDboDao;

	@Autowired
	private JuResultDboDao juResultDboDao;

	public PukeGameDbo findPukeGameDboById(String gameId) {
		return pukeGameDboDao.findById(gameId);
	}

	public void newPukeGame(PukeGameValueObject pukeGame) {

	}

	public void joinGame(PukeGameValueObject pukeGame) {

	}

	public void leaveGame(PukeGameValueObject pukeGame) {

	}

	public void backToGame(String playerId, PukeGameValueObject pukeGameValueObject) {

	}

	public void finish(PukeGameValueObject pukeGameValueObject) {

	}

	public void voteToFinish(PukeGameValueObject pukeGameValueObject) {

	}

	public GameFinishVoteDbo findGameFinishVoteDbo(String gameId) {
		return gameFinishVoteDboDao.findByGameId(gameId);
	}

}
