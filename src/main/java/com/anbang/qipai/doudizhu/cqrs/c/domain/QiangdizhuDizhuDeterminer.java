package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;
import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.preparedapai.dizhu.DizhuDeterminer;

/**
 * 从座风东的玩家开始抢地主，四次之后，如果每次都抢地主，则东家当地主，否则最后抢地主的玩家当地主
 * 
 * @author lsc
 *
 */
public class QiangdizhuDizhuDeterminer implements DizhuDeterminer {

	private int renshu;
	private Map<String, PlayerQiangdizhuState> playerQiangdizhuMap = new HashMap<>();
	private List<String> qiangdizhuPlayerIds = new ArrayList<>();
	private int qiangdizhuCount;

	@Override
	public String determineToDizhu(Ju ju, String playerId, boolean qiang) throws Exception {

		return null;
	}

}
