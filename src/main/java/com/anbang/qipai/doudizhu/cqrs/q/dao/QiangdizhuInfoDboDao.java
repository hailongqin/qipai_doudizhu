package com.anbang.qipai.doudizhu.cqrs.q.dao;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.QiangdizhuInfoDbo;

public interface QiangdizhuInfoDboDao {

	void save(QiangdizhuInfoDbo info);

	QiangdizhuInfoDbo findByGameIdAndPanNo(String gameId, int panNo);
}
