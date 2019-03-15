package com.anbang.qipai.doudizhu.cqrs.q.dao;

import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameInfoDbo;

public interface GameInfoDboDao {

	void save(GameInfoDbo info);

	List<GameInfoDbo> findByGameIdAndPanNo(String gameId, int panNo);

}
