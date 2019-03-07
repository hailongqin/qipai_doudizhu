package com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.doudizhu.cqrs.q.dao.QiangdizhuInfoDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb.repository.QiangdizhuInfoDboRepository;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.QiangdizhuInfoDbo;

@Component
public class MongodbQiangdizhuInfoDboDao implements QiangdizhuInfoDboDao {

	@Autowired
	private QiangdizhuInfoDboRepository repository;

	@Override
	public void save(QiangdizhuInfoDbo info) {
		repository.save(info);
	}

	@Override
	public QiangdizhuInfoDbo findByGameIdAndPanNo(String gameId, int panNo) {
		return repository.findOneByGameIdAndPanNo(gameId, panNo);
	}

}
