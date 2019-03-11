package com.anbang.qipai.doudizhu.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.dml.doudizhu.pan.PanValueObject;
import com.dml.puke.pai.PaiListValueObject;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;

public class PanValueObjectVO {
	private int no;
	private List<DoudizhuPlayerValueObjectVO> doudizhuPlayerList;
	private PaiListValueObject paiListValueObject;
	private List<DianShuZuPaiZu> dachuPaiZuList;
	private Position actionPosition;
	private String latestDapaiPlayerId;

	public PanValueObjectVO() {
	}

	public PanValueObjectVO(PanValueObject panValueObject) {
		no = panValueObject.getNo();
		doudizhuPlayerList = new ArrayList<>();
		panValueObject.getDoudizhuPlayerList()
				.forEach((doudizhuPlayer) -> doudizhuPlayerList.add(new DoudizhuPlayerValueObjectVO(doudizhuPlayer)));
		paiListValueObject = panValueObject.getPaiListValueObject();
		dachuPaiZuList = panValueObject.getDachuPaiZuList();
		actionPosition = panValueObject.getActionPosition();
		latestDapaiPlayerId = panValueObject.getLatestDapaiPlayerId();
	}
}
