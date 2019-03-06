package com.anbang.qipai.doudizhu.cqrs.c.domain;

import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGameValueObject;

public class PukeGameValueObject extends FixedPlayersMultipanAndVotetofinishGameValueObject {

	private int panshu;
	private int renshu;
	private boolean qxp;// 去小牌

	public PukeGameValueObject(PukeGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

}
