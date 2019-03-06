package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import java.util.List;

import com.dml.doudizhu.pan.PanActionFrame;

public class PanResultDbo {
	private String id;
	private String gameId;
	private int panNo;
	private boolean chaodi;
	private List<DoudizhuPanPlayerResultDbo> playerResultList;
	private long finishTime;
	private PanActionFrame panActionFrame;

	public PanResultDbo() {
	}

}
