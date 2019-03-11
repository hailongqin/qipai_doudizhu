package com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.doudizhu.cqrs.q.dao.GameInfoDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb.repository.GameInfoDboRepository;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameInfoDbo;

@Component
public class MongodbGameInfoDboDao implements GameInfoDboDao {

	@Autowired
	private GameInfoDboRepository repository;

	@Override
	public void save(GameInfoDbo info) {
		repository.save(info);
	}

}
