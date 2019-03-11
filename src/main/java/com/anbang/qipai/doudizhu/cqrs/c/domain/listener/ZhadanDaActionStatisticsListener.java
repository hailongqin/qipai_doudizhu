package com.anbang.qipai.doudizhu.cqrs.c.domain.listener;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.player.action.da.DaAction;
import com.dml.doudizhu.player.action.da.DaActionStatisticsListener;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;

public class ZhadanDaActionStatisticsListener implements DaActionStatisticsListener {

	private int dachuZhadanCount;

	@Override
	public void updateForNextPan() {
		dachuZhadanCount = 0;
	}

	@Override
	public void update(DaAction daAction, Ju ju) {
		DianShuZu dianShuZu = daAction.getDachuPaiZu().getDianShuZu();
		if (dianShuZu instanceof ZhadanDianShuZu) {
			dachuZhadanCount++;
		}
	}

	public int getDachuZhadanCount() {
		return dachuZhadanCount;
	}

	public void setDachuZhadanCount(int dachuZhadanCount) {
		this.dachuZhadanCount = dachuZhadanCount;
	}

}
