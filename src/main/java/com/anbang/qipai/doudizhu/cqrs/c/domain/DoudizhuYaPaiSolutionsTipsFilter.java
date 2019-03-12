package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dml.doudizhu.pai.dianshuzu.HuojianDianShuZu;
import com.dml.doudizhu.player.action.da.YaPaiSolutionsTipsFilter;
import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DuiziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianduiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LiansanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.SanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;

public class DoudizhuYaPaiSolutionsTipsFilter implements YaPaiSolutionsTipsFilter {

	@Override
	public List<DaPaiDianShuSolution> filter(List<DaPaiDianShuSolution> YaPaiSolutions,
			Map<Integer, PukePai> allShoupai) {
		int[] dianshuCountArray = new int[15];
		for (PukePai pukePai : allShoupai.values()) {
			DianShu dianShu = pukePai.getPaiMian().dianShu();
			dianshuCountArray[dianShu.ordinal()]++;
		}
		List<DaPaiDianShuSolution> filtedSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> danzhangSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> duiziSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> sanzhangSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> shunziSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> lianduiSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> liansanzhangSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> zhadanSolutionList = new LinkedList<>();
		for (DaPaiDianShuSolution solution : YaPaiSolutions) {
			DianShuZu dianshuZu = solution.getDianShuZu();
			// 三张
			if (dianshuZu instanceof SanzhangDianShuZu) {
				SanzhangDianShuZu sanzhangDianShuZu = (SanzhangDianShuZu) dianshuZu;
				DianShu dianshu = sanzhangDianShuZu.getDianShu();
				if (dianshuCountArray[dianshu.ordinal()] == 3) {
					if (sanzhangSolutionList.isEmpty()) {
						sanzhangSolutionList.add(solution);
					} else {
						int length = sanzhangSolutionList.size();
						int i = 0;
						while (i < length) {
							if (((SanzhangDianShuZu) sanzhangSolutionList.get(i).getDianShuZu()).getDianShu()
									.compareTo(sanzhangDianShuZu.getDianShu()) > 0) {
								sanzhangSolutionList.add(i, solution);
								break;
							}
							if (i == length - 1) {
								sanzhangSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 对子
			if (dianshuZu instanceof DuiziDianShuZu) {
				DuiziDianShuZu duiziDianShuZu = (DuiziDianShuZu) dianshuZu;
				DianShu dianshu = duiziDianShuZu.getDianShu();
				if (dianshuCountArray[dianshu.ordinal()] == 2) {
					if (duiziSolutionList.isEmpty()) {
						duiziSolutionList.add(solution);
					} else {
						int length = duiziSolutionList.size();
						int i = 0;
						while (i < length) {
							if (((DuiziDianShuZu) duiziSolutionList.get(i).getDianShuZu()).getDianShu()
									.compareTo(duiziDianShuZu.getDianShu()) > 0) {
								duiziSolutionList.add(i, solution);
								break;
							}
							if (i == length - 1) {
								duiziSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 单张
			if (dianshuZu instanceof DanzhangDianShuZu) {
				DanzhangDianShuZu danzhangDianShuZu = (DanzhangDianShuZu) dianshuZu;
				DianShu dianshu = danzhangDianShuZu.getDianShu();
				if (dianshuCountArray[dianshu.ordinal()] == 1) {
					if (danzhangSolutionList.isEmpty()) {
						danzhangSolutionList.add(solution);
					} else {
						int length = danzhangSolutionList.size();
						int i = 0;
						while (i < length) {
							if (((DanzhangDianShuZu) danzhangSolutionList.get(i).getDianShuZu()).getDianShu()
									.compareTo(danzhangDianShuZu.getDianShu()) > 0) {
								danzhangSolutionList.add(i, solution);
								break;
							}
							if (i == length - 1) {
								danzhangSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 连三张
			if (dianshuZu instanceof LiansanzhangDianShuZu) {
				LiansanzhangDianShuZu liansanzhangDianShuZu = (LiansanzhangDianShuZu) dianshuZu;
				DianShu[] lianXuDianShuArray = liansanzhangDianShuZu.getLianXuDianShuArray();
				boolean allSuccess = true;
				for (DianShu dianshu : lianXuDianShuArray) {
					if (dianshuCountArray[dianshu.ordinal()] != 3) {
						allSuccess = false;
						break;
					}
				}
				if (allSuccess) {
					if (liansanzhangSolutionList.isEmpty()) {
						liansanzhangSolutionList.add(solution);
					} else {
						int length = liansanzhangSolutionList.size();
						int i = 0;
						while (i < length) {
							if (((LiansanzhangDianShuZu) liansanzhangSolutionList.get(i).getDianShuZu())
									.getLianXuDianShuArray()[0].compareTo(
											((LiansanzhangDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
								liansanzhangSolutionList.add(i, solution);
								break;
							}
							if (i == length - 1) {
								liansanzhangSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 连对
			if (dianshuZu instanceof LianduiDianShuZu) {
				LianduiDianShuZu lianduiDianShuZu = (LianduiDianShuZu) dianshuZu;
				DianShu[] lianXuDianShuArray = lianduiDianShuZu.getLianXuDianShuArray();
				boolean allSuccess = true;
				for (DianShu dianshu : lianXuDianShuArray) {
					if (dianshuCountArray[dianshu.ordinal()] != 2) {
						allSuccess = false;
						break;
					}
				}
				if (allSuccess) {
					if (lianduiSolutionList.isEmpty()) {
						lianduiSolutionList.add(solution);
					} else {
						int length = lianduiSolutionList.size();
						int i = 0;
						while (i < length) {
							if (((LianduiDianShuZu) lianduiSolutionList.get(i).getDianShuZu())
									.getLianXuDianShuArray()[0]
											.compareTo(((LianduiDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
								lianduiSolutionList.add(i, solution);
								break;
							}
							if (i == length - 1) {
								lianduiSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 顺子
			if (dianshuZu instanceof ShunziDianShuZu) {
				ShunziDianShuZu shunziDianShuZu = (ShunziDianShuZu) dianshuZu;
				DianShu[] lianXuDianShuArray = shunziDianShuZu.getLianXuDianShuArray();
				boolean allSuccess = true;
				for (DianShu dianshu : lianXuDianShuArray) {
					if (dianshuCountArray[dianshu.ordinal()] != 1) {
						allSuccess = false;
						break;
					}
				}
				if (allSuccess) {
					if (shunziSolutionList.isEmpty()) {
						shunziSolutionList.add(solution);
					} else {
						int length = shunziSolutionList.size();
						int i = 0;
						while (i < length) {
							if (((ShunziDianShuZu) shunziSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0]
									.compareTo(((ShunziDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
								shunziSolutionList.add(i, solution);
								break;
							}
							if (i == length - 1) {
								shunziSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 单个炸弹
			if (dianshuZu instanceof DanGeZhadanDianShuZu) {
				DanGeZhadanDianShuZu danGeZhadanDianShuZu = (DanGeZhadanDianShuZu) dianshuZu;
				DianShu dianshu = danGeZhadanDianShuZu.getDianShu();
				if (dianshuCountArray[dianshu.ordinal()] == 4) {
					if (zhadanSolutionList.isEmpty()) {
						zhadanSolutionList.add(solution);
					} else {
						int length = zhadanSolutionList.size();
						int i = 0;
						while (i < length) {
							if (((DanGeZhadanDianShuZu) zhadanSolutionList.get(i).getDianShuZu()).getDianShu()
									.compareTo(danGeZhadanDianShuZu.getDianShu()) > 0) {
								zhadanSolutionList.add(i, solution);
								break;
							}
							if (i == length - 1) {
								zhadanSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 火箭
			if (dianshuZu instanceof HuojianDianShuZu) {
				zhadanSolutionList.add(solution);
			}
		}
		filtedSolutionList.addAll(danzhangSolutionList);
		filtedSolutionList.addAll(duiziSolutionList);
		filtedSolutionList.addAll(sanzhangSolutionList);
		filtedSolutionList.addAll(shunziSolutionList);
		filtedSolutionList.addAll(lianduiSolutionList);
		filtedSolutionList.addAll(liansanzhangSolutionList);
		filtedSolutionList.addAll(zhadanSolutionList);
		return filtedSolutionList;
	}

}
