package com.anbang.qipai.doudizhu.web.vo;

import java.util.List;

import com.dml.puke.pai.PaiListValueObject;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;

public class PanValueObjectVO {
	private int no;
	private PaiListValueObject paiListValueObject;
	private List<DianShuZuPaiZu> dachuPaiZuList;
	private Position actionPosition;
	private String latestDapaiPlayerId;

	public PanValueObjectVO() {
	}
}
