package com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.doudizhu.cqrs.q.dao.GameLatestInfoDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb.repository.GameLatestInfoDboRepository;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestInfoDbo;

@Component
public class MongodbGameLatestInfoDboDao implements GameLatestInfoDboDao {
	@Autowired
	private GameLatestInfoDboRepository repository;

	@Override
	public void save(GameLatestInfoDbo info) {
		repository.save(info);
	}

	@Override
	public GameLatestInfoDbo findById(String gameId) {
		return repository.findOne(gameId);
	}

}
