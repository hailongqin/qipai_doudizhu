package com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.QiangdizhuInfoDbo;

public interface QiangdizhuInfoDboRepository extends MongoRepository<QiangdizhuInfoDbo, String> {

	QiangdizhuInfoDbo findOneByGameIdAndPanNo(String gameId, int panNo);
}
