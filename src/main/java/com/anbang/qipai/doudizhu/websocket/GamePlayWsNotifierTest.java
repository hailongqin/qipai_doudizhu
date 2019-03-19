package com.anbang.qipai.doudizhu.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;

public class GamePlayWsNotifierTest {

	private Map<String, WebSocketSession> idSessionMap = new ConcurrentHashMap<>();

	private Map<String, Long> sessionIdActivetimeMap = new ConcurrentHashMap<>();

	private Map<String, String> sessionIdPlayerIdMap = new ConcurrentHashMap<>();

	private Map<String, Set<String>> playerIdSessionIdsMap = new ConcurrentHashMap<>();

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private Gson gson = new Gson();

	public WebSocketSession removeSession(String id) {
		WebSocketSession removedSession = idSessionMap.remove(id);
		sessionIdActivetimeMap.remove(id);
		if (removedSession != null) {
			String removedPlayerId = sessionIdPlayerIdMap.remove(id);
			if (removedPlayerId != null) {
				Set<String> currentSessionIdSetForPlayer = playerIdSessionIdsMap.get(removedPlayerId);
				if (currentSessionIdSetForPlayer != null) {
					currentSessionIdSetForPlayer.remove(id);
					if (currentSessionIdSetForPlayer.isEmpty()) {
						playerIdSessionIdsMap.remove(removedPlayerId);
					}
				}
			}
		}
		return removedSession;
	}

	public void addSession(WebSocketSession session) {
		idSessionMap.put(session.getId(), session);
		sessionIdActivetimeMap.put(session.getId(), System.currentTimeMillis());
	}

	public void bindPlayer(String sessionId, String playerId) {
		Set<String> sessionAlreadyExistsIdSet = playerIdSessionIdsMap.get(playerId);
		sessionIdPlayerIdMap.put(sessionId, playerId);
		if (sessionAlreadyExistsIdSet == null) {
			sessionAlreadyExistsIdSet = new HashSet<>();
		}
		sessionAlreadyExistsIdSet.add(sessionId);
		playerIdSessionIdsMap.put(playerId, sessionAlreadyExistsIdSet);
		updateSession(sessionId);
	}

	public void updateSession(String id) {
		sessionIdActivetimeMap.put(id, System.currentTimeMillis());
	}

	public String findPlayerIdBySessionId(String sessionId) {
		return sessionIdPlayerIdMap.get(sessionId);
	}

	public void notifyToQuery(String playerId, List<QueryScope> scopes) {
		executorService.submit(() -> {
			for (QueryScope scope : scopes) {
				Set<String> sessionIdSet = playerIdSessionIdsMap.get(playerId);
				if (sessionIdSet == null) {
					return;
				}
				CommonMO mo = new CommonMO();
				mo.setMsg("query");
				Map data = new HashMap();
				data.put("scope", scope.name());
				if (sessionIdSet.size() > 1) {
					data.put("tuoguan", true);
				} else {
					data.put("tuoguan", false);
				}
				mo.setData(data);
				String payLoad = gson.toJson(mo);
				sessionIdSet.forEach((sessionId) -> {
					WebSocketSession session = idSessionMap.get(sessionId);
					if (session != null) {
						sendMessage(session, payLoad);
					} else {

					}
				});
			}
		});
	}

	private void sendMessage(WebSocketSession session, String message) {
		synchronized (session) {
			try {
				session.sendMessage(new TextMessage(message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Scheduled(cron = "0/10 * * * * ?")
	public void closeOTSessions() {
		sessionIdActivetimeMap.forEach((id, time) -> {
			if ((System.currentTimeMillis() - time) > (30 * 1000)) {
				WebSocketSession sessionToClose = idSessionMap.get(id);
				if (sessionToClose != null) {
					try {
						sessionToClose.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void closeSessionForPlayer(String playerId) {
		Set<String> sessionIdSet = playerIdSessionIdsMap.get(playerId);
		if (sessionIdSet != null) {
			sessionIdSet.forEach((sessionId) -> {
				WebSocketSession session = idSessionMap.get(sessionId);
				if (session != null) {
					try {
						session.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public boolean hasSessionForPlayer(String playerId) {
		return playerIdSessionIdsMap.get(playerId) != null;
	}
}
