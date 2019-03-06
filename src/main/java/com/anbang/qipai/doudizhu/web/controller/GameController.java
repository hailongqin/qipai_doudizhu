package com.anbang.qipai.doudizhu.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.ReadyForGameResult;
import com.anbang.qipai.doudizhu.cqrs.c.service.GameCmdService;
import com.anbang.qipai.doudizhu.cqrs.c.service.PlayerAuthService;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameFinishVoteDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.doudizhu.cqrs.q.service.PukeGameQueryService;
import com.anbang.qipai.doudizhu.cqrs.q.service.PukePlayQueryService;
import com.anbang.qipai.doudizhu.plan.bean.MemberGoldBalance;
import com.anbang.qipai.doudizhu.plan.bean.PlayerInfo;
import com.anbang.qipai.doudizhu.plan.service.MemberGoldBalanceService;
import com.anbang.qipai.doudizhu.plan.service.PlayerInfoService;
import com.anbang.qipai.doudizhu.utils.CommonVoUtil;
import com.anbang.qipai.doudizhu.web.vo.CommonVO;
import com.anbang.qipai.doudizhu.web.vo.GameFinishVoteVO;
import com.anbang.qipai.doudizhu.web.vo.GameVO;
import com.anbang.qipai.doudizhu.websocket.GamePlayWsNotifier;
import com.anbang.qipai.doudizhu.websocket.QueryScope;
import com.dml.mpgame.game.Canceled;
import com.dml.mpgame.game.CrowdLimitsException;
import com.dml.mpgame.game.Finished;
import com.dml.mpgame.game.GameNotFoundException;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.fpmpv.VoteNotPassWhenWaitingNextPan;
import com.dml.mpgame.game.extend.vote.FinishedByVote;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.player.GamePlayerOnlineState;
import com.dml.mpgame.game.watch.Watcher;

@RestController
@RequestMapping("/game")
public class GameController {

	@Autowired
	private PlayerAuthService playerAuthService;

	@Autowired
	private GameCmdService gameCmdService;

	@Autowired
	private PukeGameQueryService pukeGameQueryService;

	@Autowired
	private PukePlayQueryService pukePlayQueryService;

	@Autowired
	private GamePlayWsNotifier wsNotifier;

	@Autowired
	private MemberGoldBalanceService memberGoldBalanceService;

	@Autowired
	private PlayerInfoService playerInfoService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 新一局游戏
	 */
	@RequestMapping(value = "/newgame")
	@ResponseBody
	public CommonVO newgame(String playerId, int panshu, int renshu, boolean qxp) {
		CommonVO vo = new CommonVO();
		String newGameId = UUID.randomUUID().toString();
		PukeGameValueObject pukeGameValueObject = gameCmdService.newPukeGame(newGameId, playerId, panshu, renshu, qxp);
		pukeGameQueryService.newPukeGame(pukeGameValueObject);
		String token = playerAuthService.newSessionForPlayer(playerId);
		Map data = new HashMap();
		data.put("gameId", newGameId);
		data.put("token", token);
		vo.setData(data);
		return vo;
	}

	/**
	 * 新一局游戏,游戏未开始时离开就是退出
	 */
	@RequestMapping(value = "/newgame_leave_quit")
	@ResponseBody
	public CommonVO newgame_leave_quit(String playerId, int panshu, int renshu, boolean qxp) {
		CommonVO vo = new CommonVO();
		String newGameId = UUID.randomUUID().toString();
		PukeGameValueObject pukeGameValueObject = gameCmdService.newPukeGameLeaveAndQuit(newGameId, playerId, panshu,
				renshu, qxp);
		pukeGameQueryService.newPukeGame(pukeGameValueObject);
		String token = playerAuthService.newSessionForPlayer(playerId);
		Map data = new HashMap();
		data.put("gameId", newGameId);
		data.put("token", token);
		vo.setData(data);
		return vo;
	}

	/**
	 * 加入游戏
	 */
	@RequestMapping(value = "/joingame")
	@ResponseBody
	public CommonVO joingame(String playerId, String gameId) {
		CommonVO vo = new CommonVO();
		PukeGameValueObject pukeGameValueObject;
		try {
			pukeGameValueObject = gameCmdService.joinGame(playerId, gameId);
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg(e.getClass().toString());
			return vo;
		}
		pukeGameQueryService.joinGame(pukeGameValueObject);
		// 通知其他玩家
		for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
			if (!otherPlayerId.equals(playerId)) {
				wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(pukeGameValueObject.getState(),
						pukeGameValueObject.findPlayerState(otherPlayerId)));
			}
		}
		String token = playerAuthService.newSessionForPlayer(playerId);
		Map data = new HashMap();
		data.put("token", token);
		vo.setData(data);
		return vo;
	}

	/**
	 * 加入观战
	 */
	@RequestMapping(value = "/joinwatch")
	@ResponseBody
	public CommonVO joinWatch(String playerId, String gameId) {
		PukeGameValueObject pukeGameValueObject;
		String nickName = "";
		String headimgurl = "";

		// 加入观战
		try {
			PlayerInfo playerInfo = playerInfoService.findPlayerInfoById(playerId);
			nickName = playerInfo.getNickname();
			headimgurl = playerInfo.getHeadimgurl();
			pukeGameValueObject = gameCmdService.joinWatch(playerId, nickName, headimgurl, gameId);
		} catch (CrowdLimitsException e) {
			return CommonVoUtil.error("too many watchers");
		} catch (Exception e) {
			logger.error("joinWatch:" + JSON.toJSONString(e));
			return CommonVoUtil.error(e.getClass().toString());
		}

		// 通知游戏玩家
		for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
			wsNotifier.notifyWatchInfo(otherPlayerId, "input", playerId, nickName, headimgurl);
		}
		// 通知其他观战者
		Map<String, Watcher> map = gameCmdService.getwatch(gameId);
		if (!CollectionUtils.isEmpty(map)) {
			for (Watcher list : map.values()) {
				if (!list.getId().equals(playerId)) {
					wsNotifier.notifyWatchInfo(list.getId(), "input", playerId, nickName, headimgurl);
				}
			}
		}

		// 返回查询token
		String token = playerAuthService.newSessionForPlayer(playerId);

		Watcher watcher = new Watcher();
		watcher.setId(playerId);
		watcher.setHeadimgurl(headimgurl);
		watcher.setNickName(nickName);
		watcher.setState("join");
		watcher.setJoinTime(System.currentTimeMillis());

		Map data = new HashMap();
		data.put("token", token);
		return CommonVoUtil.success(data, "join watch success");
	}

	/**
	 * 离开观战
	 */
	@RequestMapping(value = "/leavewatch")
	@ResponseBody
	public CommonVO leaveWatch(String token, String gameId) {
		String playerId = playerAuthService.getPlayerIdByToken(token);
		if (playerId == null) {
			return CommonVoUtil.error("invalid token");
		}
		PukeGameValueObject pukeGameValueObject;
		String nickName = "";
		String headimgurl = "";

		try {
			nickName = playerInfoService.findPlayerInfoById(playerId).getNickname();
			pukeGameValueObject = gameCmdService.leaveWatch(playerId, gameId);
		} catch (Exception e) {
			logger.error("leavewatch():" + gameId + JSON.toJSONString(e));
			return CommonVoUtil.error(e.getClass().toString());
		}

		// 通知游戏玩家
		for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
			wsNotifier.notifyWatchInfo(otherPlayerId, "leave", playerId, nickName, headimgurl);
		}
		// 通知观战者
		Map<String, Watcher> map = gameCmdService.getwatch(gameId);
		if (!CollectionUtils.isEmpty(map)) {
			for (Watcher list : map.values()) {
				if (!list.getId().equals(playerId)) {
					wsNotifier.notifyWatchInfo(list.getId(), "input", playerId, nickName, headimgurl);
				}
			}
		}

		Watcher watcher = new Watcher();
		watcher.setId(playerId);
		watcher.setHeadimgurl(headimgurl);
		watcher.setNickName(nickName);
		watcher.setState("leave");

		return CommonVoUtil.success("leave success");
	}

	/**
	 * 查询正在观战的玩家
	 */
	@RequestMapping(value = "/queryWatch")
	@ResponseBody
	public CommonVO queryWatch(String gameId) {
		Map<String, Watcher> map = gameCmdService.getwatch(gameId);
		if (CollectionUtils.isEmpty(map)) {
			return CommonVoUtil.success("queryWatch success");
		}
		return CommonVoUtil.success(map.values(), "queryWatch success");
	}

	/**
	 * 观战者看到的信息
	 */
	@RequestMapping(value = "/watchinginfo")
	@ResponseBody
	public CommonVO watchingInfo(String gameId) {
		CommonVO vo = new CommonVO();
		PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
		GameVO gameVO = new GameVO();
		Map data = new HashMap();
		data.put("game", gameVO);
		vo.setData(data);
		return vo;
	}

	/**
	 * 挂起（手机按黑的时候调用）
	 */
	@RequestMapping(value = "/hangup")
	@ResponseBody
	public CommonVO hangup(String token) {
		CommonVO vo = new CommonVO();
		String playerId = playerAuthService.getPlayerIdByToken(token);
		if (playerId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		PukeGameValueObject pukeGameValueObject;
		String flag = "query";
		try {
			pukeGameValueObject = gameCmdService.leaveGameByHangup(playerId);
			if (pukeGameValueObject == null) {
				vo.setSuccess(true);
				return vo;
			}
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg(e.getClass().getName());
			return vo;
		}
		pukeGameQueryService.leaveGame(pukeGameValueObject);
		// 断开玩家的socket
		wsNotifier.closeSessionForPlayer(playerId);
		String gameId = pukeGameValueObject.getId();
		JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
		// 记录战绩
		if (juResultDbo != null) {
			PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);

		}
		if (pukeGameValueObject.getState().name().equals(FinishedByVote.name)
				|| pukeGameValueObject.getState().name().equals(Canceled.name)) {

		} else {

		}
		// 通知其他玩家
		for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
			if (!otherPlayerId.equals(playerId)) {
				List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(),
						pukeGameValueObject.findPlayerState(otherPlayerId));
				scopes.remove(QueryScope.panResult);
				if (pukeGameValueObject.getState().name().equals(VoteNotPassWhenPlaying.name)
						|| pukeGameValueObject.getState().name().equals(VoteNotPassWhenWaitingNextPan.name)) {
					scopes.remove(QueryScope.gameFinishVote);
				}
				wsNotifier.notifyToQuery(otherPlayerId, scopes);
			}
		}

		hintWatcher(gameId, flag);
		return vo;
	}

	/**
	 * 离开游戏(非退出,还会回来的)
	 */
	@RequestMapping(value = "/leavegame")
	@ResponseBody
	public CommonVO leavegame(String token) {
		CommonVO vo = new CommonVO();
		String playerId = playerAuthService.getPlayerIdByToken(token);
		if (playerId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		PukeGameValueObject pukeGameValueObject;
		String endFlag = "query";
		try {
			pukeGameValueObject = gameCmdService.leaveGame(playerId);
			if (pukeGameValueObject == null) {
				vo.setSuccess(true);
				return vo;
			}
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg(e.getClass().getName());
			return vo;
		}
		pukeGameQueryService.leaveGame(pukeGameValueObject);
		// 断开玩家的socket
		wsNotifier.closeSessionForPlayer(playerId);
		String gameId = pukeGameValueObject.getId();
		JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
		// 记录战绩
		if (juResultDbo != null) {
			PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);

		}
		if (pukeGameValueObject.getState().name().equals(FinishedByVote.name)
				|| pukeGameValueObject.getState().name().equals(Canceled.name)) {

		} else if (pukeGameValueObject.getState().name().equals(Finished.name)) {

		} else {

		}
		// 通知其他玩家
		for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
			if (!otherPlayerId.equals(playerId)) {
				List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(),
						pukeGameValueObject.findPlayerState(otherPlayerId));
				if (!pukeGameValueObject.getState().name().equals(Finished.name)) {
					scopes.remove(QueryScope.panResult);
				}
				if (pukeGameValueObject.getState().name().equals(VoteNotPassWhenPlaying.name)
						|| pukeGameValueObject.getState().name().equals(VoteNotPassWhenWaitingNextPan.name)) {
					scopes.remove(QueryScope.gameFinishVote);
				}
				wsNotifier.notifyToQuery(otherPlayerId, scopes);
			}
		}

		hintWatcher(gameId, endFlag);
		return vo;
	}

	/**
	 * 返回游戏
	 */
	@RequestMapping(value = "/backtogame")
	@ResponseBody
	public CommonVO backtogame(String playerId, String gameId) {
		// 是观战返回新token
		Map<String, Watcher> map = gameCmdService.getwatch(gameId);
		if (!CollectionUtils.isEmpty(map) && map.containsKey(playerId)) {
			List<String> playerIds = new ArrayList<>();
			playerIds.add(playerId);

			Map data = new HashMap();
			String token = playerAuthService.newSessionForPlayer(playerId);
			data.put("token", token);
			return CommonVoUtil.success(data, "backtogame success");
		}

		CommonVO vo = new CommonVO();
		Map data = new HashMap();
		vo.setData(data);
		PukeGameValueObject pukeGameValueObject;
		try {
			pukeGameValueObject = gameCmdService.backToGame(playerId, gameId);
		} catch (Exception e) {
			// 如果找不到game，看下是否是已经结束(正常结束和被投票)的game
			if (e instanceof GameNotFoundException) {
				PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);

			}
			vo.setSuccess(false);
			vo.setMsg(e.getClass().toString());
			return vo;
		}

		pukeGameQueryService.backToGame(playerId, pukeGameValueObject);

		// 通知其他人
		for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
			if (!otherPlayerId.equals(playerId)) {
				List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(),
						pukeGameValueObject.findPlayerState(otherPlayerId));
				scopes.remove(QueryScope.panResult);
				if (pukeGameValueObject.getState().name().equals(VoteNotPassWhenPlaying.name)
						|| pukeGameValueObject.getState().name().equals(VoteNotPassWhenWaitingNextPan.name)) {
					scopes.remove(QueryScope.gameFinishVote);
				}
				wsNotifier.notifyToQuery(otherPlayerId, scopes);
			}
		}
		String token = playerAuthService.newSessionForPlayer(playerId);
		data.put("token", token);
		return vo;

	}

	/**
	 * 游戏的所有信息,不包含局
	 * 
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value = "/info")
	@ResponseBody
	public CommonVO info(String gameId) {
		CommonVO vo = new CommonVO();
		PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
		GameVO gameVO = new GameVO();
		Map data = new HashMap();
		data.put("game", gameVO);
		vo.setData(data);
		return vo;
	}

	/**
	 * 最开始的准备,不适用下一盘的准备
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/ready")
	@ResponseBody
	public CommonVO ready(String token) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap();
		vo.setData(data);
		String playerId = playerAuthService.getPlayerIdByToken(token);
		if (playerId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}

		ReadyForGameResult readyForGameResult;
		try {
			readyForGameResult = gameCmdService.readyForGame(playerId, System.currentTimeMillis());
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg(e.getClass().getName());
			return vo;
		}

		try {
			pukePlayQueryService.readyForGame(readyForGameResult);// TODO 一起点准备的时候可能有同步问题.要靠框架解决
		} catch (Throwable e) {
			vo.setSuccess(false);
			vo.setMsg(e.getMessage());
			return vo;
		}
		// 通知其他人
		for (String otherPlayerId : readyForGameResult.getPukeGame().allPlayerIds()) {
			if (!otherPlayerId.equals(playerId)) {
				wsNotifier.notifyToQuery(otherPlayerId,
						QueryScope.scopesForState(readyForGameResult.getPukeGame().getState(),
								readyForGameResult.getPukeGame().findPlayerState(otherPlayerId)));
			}
		}

		List<QueryScope> queryScopes = new ArrayList<>();
		queryScopes.add(QueryScope.gameInfo);
		if (readyForGameResult.getPukeGame().getState().name().equals(Playing.name)) {
			queryScopes.add(QueryScope.panForMe);
		}
		data.put("queryScopes", queryScopes);
		return vo;
	}

	/**
	 * 最开始的取消准备,不适用下一盘的准备
	 *
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/cancelready")
	@ResponseBody
	public CommonVO cancelReady(String token) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap();
		vo.setData(data);
		String playerId = playerAuthService.getPlayerIdByToken(token);
		if (playerId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}

		ReadyForGameResult readyForGameResult;
		try {
			readyForGameResult = gameCmdService.cancelReadyForGame(playerId, System.currentTimeMillis());
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg(e.getClass().getName());
			return vo;
		}

		try {
			pukePlayQueryService.readyForGame(readyForGameResult);// TODO 一起点准备的时候可能有同步问题.要靠框架解决
		} catch (Throwable e) {
			vo.setSuccess(false);
			vo.setMsg(e.getMessage());
			return vo;
		}
		// 通知其他人
		for (String otherPlayerId : readyForGameResult.getPukeGame().allPlayerIds()) {
			if (!otherPlayerId.equals(playerId)) {
				wsNotifier.notifyToQuery(otherPlayerId,
						QueryScope.scopesForState(readyForGameResult.getPukeGame().getState(),
								readyForGameResult.getPukeGame().findPlayerState(otherPlayerId)));

			}
		}

		List<QueryScope> queryScopes = new ArrayList<>();
		queryScopes.add(QueryScope.gameInfo);
		if (readyForGameResult.getPukeGame().getState().name().equals(Playing.name)) {
			queryScopes.add(QueryScope.panForMe);
		}
		data.put("queryScopes", queryScopes);
		return vo;
	}

	@RequestMapping(value = "/finish")
	@ResponseBody
	public CommonVO finish(String token) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap();
		vo.setData(data);
		String playerId = playerAuthService.getPlayerIdByToken(token);
		if (playerId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}

		PukeGameValueObject pukeGameValueObject;
		String endFlag = "query";
		try {
			pukeGameValueObject = gameCmdService.finish(playerId, System.currentTimeMillis());
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg(e.getClass().getName());
			return vo;
		}
		pukeGameQueryService.finish(pukeGameValueObject);
		String gameId = pukeGameValueObject.getId();
		JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
		// 记录战绩
		if (juResultDbo != null) {
			PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);

		}

		if (pukeGameValueObject.getState().name().equals(FinishedByVote.name)
				|| pukeGameValueObject.getState().name().equals(Canceled.name)) {

			data.put("queryScope", QueryScope.gameInfo);
		} else {
			// 游戏没结束有两种可能：一种是发起了投票。还有一种是游戏没开始，解散发起人又不是房主，那就自己走人。
			if (pukeGameValueObject.allPlayerIds().contains(playerId)) {
				data.put("queryScope", QueryScope.gameFinishVote);
			} else {
				data.put("queryScope", null);

			}
		}

		// 通知其他人来查询
		for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
			if (!otherPlayerId.equals(playerId)) {
				GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
				if (onlineState.equals(GamePlayerOnlineState.online)) {
					List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(),
							pukeGameValueObject.findPlayerState(otherPlayerId));
					scopes.remove(QueryScope.panResult);
					wsNotifier.notifyToQuery(otherPlayerId, scopes);
				}
			}
		}

		hintWatcher(gameId, endFlag);
		return vo;
	}

	@RequestMapping(value = "/vote_to_finish")
	@ResponseBody
	public CommonVO votetofinish(String token, boolean yes) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap();
		vo.setData(data);
		String playerId = playerAuthService.getPlayerIdByToken(token);
		if (playerId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}

		PukeGameValueObject pukeGameValueObject;
		String endFlag = "query";
		try {
			pukeGameValueObject = gameCmdService.voteToFinish(playerId, yes);
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg(e.getClass().getName());
			return vo;
		}
		String gameId = pukeGameValueObject.getId();
		pukeGameQueryService.voteToFinish(pukeGameValueObject);
		JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
		// 记录战绩
		if (juResultDbo != null) {
			PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);

		}
		if (pukeGameValueObject.getState().name().equals(FinishedByVote.name)
				|| pukeGameValueObject.getState().name().equals(Canceled.name)) {

		}
		data.put("queryScope", QueryScope.gameFinishVote);
		// 通知其他人来查询投票情况
		for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
			if (!otherPlayerId.equals(playerId)) {
				GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
				if (onlineState.equals(GamePlayerOnlineState.online)) {
					List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(),
							pukeGameValueObject.findPlayerState(otherPlayerId));
					scopes.remove(QueryScope.panResult);
					wsNotifier.notifyToQuery(otherPlayerId, scopes);
				}
			}
		}

		hintWatcher(gameId, endFlag);
		return vo;
	}

	/**
	 * 投票倒计时结束弃权
	 */
	@RequestMapping(value = "/timeover_to_waiver")
	@ResponseBody
	public CommonVO timeoverToWaiver(String token) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap();
		vo.setData(data);
		String playerId = playerAuthService.getPlayerIdByToken(token);
		if (playerId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}

		PukeGameValueObject pukeGameValueObject;
		String endFlag = "query";
		try {
			pukeGameValueObject = gameCmdService.voteToFinishByTimeOver(playerId, System.currentTimeMillis());
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg(e.getClass().getName());
			return vo;
		}
		String gameId = pukeGameValueObject.getId();
		pukeGameQueryService.voteToFinish(pukeGameValueObject);
		JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
		// 记录战绩
		if (juResultDbo != null) {
			PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);

		}
		if (pukeGameValueObject.getState().name().equals(FinishedByVote.name)
				|| pukeGameValueObject.getState().name().equals(Canceled.name)) {

		}
		data.put("queryScope", QueryScope.gameFinishVote);
		// 通知其他人来查询投票情况
		for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
			if (!otherPlayerId.equals(playerId)) {
				GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
				if (onlineState.equals(GamePlayerOnlineState.online)) {
					List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(),
							pukeGameValueObject.findPlayerState(otherPlayerId));
					scopes.remove(QueryScope.panResult);
					wsNotifier.notifyToQuery(otherPlayerId, scopes);
				}
			}
		}

		hintWatcher(gameId, endFlag);
		return vo;
	}

	@RequestMapping(value = "/finish_vote_info")
	@ResponseBody
	public CommonVO finishvoteinfo(String gameId) {

		CommonVO vo = new CommonVO();
		GameFinishVoteDbo gameFinishVoteDbo = pukeGameQueryService.findGameFinishVoteDbo(gameId);
		Map data = new HashMap();
		data.put("vote", new GameFinishVoteVO(gameFinishVoteDbo.getVote()));
		vo.setData(data);
		return vo;

	}

	@RequestMapping(value = "/wisecrack")
	@ResponseBody
	public CommonVO wisecrack(String token, String gameId, String ordinal) {
		CommonVO vo = new CommonVO();
		String playerId = playerAuthService.getPlayerIdByToken(token);
		if (playerId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
		if (!ordinal.contains("qiaopihuafy")) {
			// 通知其他人
			vo.setSuccess(true);
			return vo;
		}
		MemberGoldBalance account = memberGoldBalanceService.findByMemberId(playerId);
		if (account.getBalanceAfter() > 10) {
			// 通知其他人
			vo.setSuccess(true);
			return vo;
		}
		vo.setSuccess(false);
		vo.setMsg("InsufficientBalanceException");
		return vo;
	}

	@RequestMapping(value = "/playback")
	@ResponseBody
	public CommonVO playback(String gameId, int panNo) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap();
		vo.setData(data);

		return vo;
	}

	@RequestMapping(value = "/speak")
	@ResponseBody
	public CommonVO speak(String token, String gameId, String wordId) {
		CommonVO vo = new CommonVO();
		String playerId = playerAuthService.getPlayerIdByToken(token);
		if (playerId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);

		vo.setSuccess(true);
		return vo;
	}

	/**
	 * 通知观战者
	 */
	private void hintWatcher(String gameId, String flag) {
		Map<String, Object> map = gameCmdService.getwatch(gameId);

	}
}