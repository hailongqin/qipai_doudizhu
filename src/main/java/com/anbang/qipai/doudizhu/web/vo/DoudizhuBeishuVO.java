package com.anbang.qipai.doudizhu.web.vo;

import com.anbang.qipai.doudizhu.cqrs.c.domain.DoudizhuBeishu;

public class DoudizhuBeishuVO {
	private int qiangdizhuCount;// 抢地主次数
	private boolean dipaiHasDuiA;// 底牌中有对A
	private boolean dipaiHasDuier;// 底牌中有对2
	private boolean dipaiTonghua;// 三张底牌是同花
	private boolean dipaiShunzi;// 三张底牌是顺子
	private boolean dipaiTongdianshu;// 三张底牌点数相同
	private boolean dipaiXiaoyuShi;// 三张底牌都<10点
	private boolean dipaihasXiaowang;// 三张底牌含小王(三人小王不翻倍)
	private boolean dipaihasDawang;// 三张底牌含大王
	private int dachuZhadanCount;// 玩家打出炸弹 火箭
	private boolean chuntian;// 春天(地主出完所有牌农民一张未出)
	private boolean fanchuntian;// 反春天(任一农民出完牌,地主只出过一手牌)
	private int value;

	public DoudizhuBeishuVO() {

	}

	public DoudizhuBeishuVO(DoudizhuBeishu beishu) {
		qiangdizhuCount = beishu.getQiangdizhuCount();
		dipaiHasDuiA = beishu.isDipaiHasDuiA();
		dipaiHasDuier = beishu.isDipaiHasDuier();
		dipaiTonghua = beishu.isDipaiTonghua();
		dipaiShunzi = beishu.isDipaiShunzi();
		dipaiTongdianshu = beishu.isDipaiTongdianshu();
		dipaiXiaoyuShi = beishu.isDipaiXiaoyuShi();
		dipaihasXiaowang = beishu.isDipaihasXiaowang();
		dipaihasDawang = beishu.isDipaihasDawang();
		dachuZhadanCount = beishu.getDachuZhadanCount();
		chuntian = beishu.isChuntian();
		fanchuntian = beishu.isFanchuntian();
		value = beishu.getValue();
	}

	public int getQiangdizhuCount() {
		return qiangdizhuCount;
	}

	public void setQiangdizhuCount(int qiangdizhuCount) {
		this.qiangdizhuCount = qiangdizhuCount;
	}

	public boolean isDipaiHasDuiA() {
		return dipaiHasDuiA;
	}

	public void setDipaiHasDuiA(boolean dipaiHasDuiA) {
		this.dipaiHasDuiA = dipaiHasDuiA;
	}

	public boolean isDipaiHasDuier() {
		return dipaiHasDuier;
	}

	public void setDipaiHasDuier(boolean dipaiHasDuier) {
		this.dipaiHasDuier = dipaiHasDuier;
	}

	public boolean isDipaiTonghua() {
		return dipaiTonghua;
	}

	public void setDipaiTonghua(boolean dipaiTonghua) {
		this.dipaiTonghua = dipaiTonghua;
	}

	public boolean isDipaiShunzi() {
		return dipaiShunzi;
	}

	public void setDipaiShunzi(boolean dipaiShunzi) {
		this.dipaiShunzi = dipaiShunzi;
	}

	public boolean isDipaiTongdianshu() {
		return dipaiTongdianshu;
	}

	public void setDipaiTongdianshu(boolean dipaiTongdianshu) {
		this.dipaiTongdianshu = dipaiTongdianshu;
	}

	public boolean isDipaiXiaoyuShi() {
		return dipaiXiaoyuShi;
	}

	public void setDipaiXiaoyuShi(boolean dipaiXiaoyuShi) {
		this.dipaiXiaoyuShi = dipaiXiaoyuShi;
	}

	public boolean isDipaihasXiaowang() {
		return dipaihasXiaowang;
	}

	public void setDipaihasXiaowang(boolean dipaihasXiaowang) {
		this.dipaihasXiaowang = dipaihasXiaowang;
	}

	public boolean isDipaihasDawang() {
		return dipaihasDawang;
	}

	public void setDipaihasDawang(boolean dipaihasDawang) {
		this.dipaihasDawang = dipaihasDawang;
	}

	public int getDachuZhadanCount() {
		return dachuZhadanCount;
	}

	public void setDachuZhadanCount(int dachuZhadanCount) {
		this.dachuZhadanCount = dachuZhadanCount;
	}

	public boolean isChuntian() {
		return chuntian;
	}

	public void setChuntian(boolean chuntian) {
		this.chuntian = chuntian;
	}

	public boolean isFanchuntian() {
		return fanchuntian;
	}

	public void setFanchuntian(boolean fanchuntian) {
		this.fanchuntian = fanchuntian;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
