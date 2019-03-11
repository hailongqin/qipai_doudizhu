package com.anbang.qipai.doudizhu.cqrs.c.service;

import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.ReadyForGameResult;
import com.dml.mpgame.game.GameValueObject;

public interface GameCmdService {

	PukeGameValueObject newPukeGame(String gameId, String playerId, Integer panshu, Integer renshu, Boolean qxp,
			Integer difen);

	PukeGameValueObject newPukeGameLeaveAndQuit(String gameId, String playerId, Integer panshu, Integer renshu,
			Boolean qxp, Integer difen);

	PukeGameValueObject joinGame(String playerId, String gameId) throws Exception;

	PukeGameValueObject leaveGame(String playerId) throws Exception;

	PukeGameValueObject leaveGameByHangup(String playerId) throws Exception;

	PukeGameValueObject leaveGameByOffline(String playerId) throws Exception;

	PukeGameValueObject backToGame(String playerId, String gameId) throws Exception;

	ReadyForGameResult readyForGame(String playerId, Long currentTime) throws Exception;

	ReadyForGameResult cancelReadyForGame(String playerId, Long currentTime) throws Exception;

	PukeGameValueObject finish(String playerId, Long currentTime) throws Exception;

	PukeGameValueObject voteToFinish(String playerId, Boolean yes) throws Exception;

	PukeGameValueObject voteToFinishByTimeOver(String playerId, Long currentTime) throws Exception;

	GameValueObject finishGameImmediately(String gameId) throws Exception;

	void bindPlayer(String playerId, String gameId);
}
