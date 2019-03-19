package com.anbang.qipai.doudizhu.cqrs.c.domain;

/**
 * 倍数
 */
public class DoudizhuBeishu {
	private int renshu;
	private int qiangdizhuCount;
	private boolean dipaiHasDuiA;
	private boolean dipaiHasDuier;
	private boolean dipaiTonghua;
	private boolean dipaiShunzi;
	private boolean dipaiTongdianshu;
	private boolean dipaiXiaoyuShi;
	private boolean dipaihasXiaowang;
	private boolean dipaihasDawang;
	private int dachuZhadanCount;
	private boolean chuntian;
	private boolean fanchuntian;
	private int value = 1;

	public void calculate() {
		int beishu = 1;
		if (renshu == 2) {
			if (qiangdizhuCount == 2) {
				beishu = beishu << 1;
			} else if (qiangdizhuCount == 3) {
				beishu = beishu << 2;
			} else if (qiangdizhuCount == 4) {
				beishu *= 5;
			} else if (qiangdizhuCount == 5) {
				beishu *= 6;
			}
		} else {
			if (qiangdizhuCount == 1) {
				beishu = beishu << 1;
			} else if (qiangdizhuCount == 2) {
				beishu = beishu << 2;
			} else if (qiangdizhuCount == 3) {
				beishu *= 5;
			} else if (qiangdizhuCount == 4) {
				beishu *= 6;
			}
		}
		if (dipaiHasDuiA) {
			beishu = beishu << 1;
		}
		if (dipaiHasDuier) {
			beishu = beishu << 1;
		}
		if (dipaiTonghua) {
			beishu *= 3;
		}
		if (dipaiShunzi) {
			beishu *= 3;
		}
		if (dipaiTongdianshu) {
			beishu *= 3;
		}
		if (dipaiXiaoyuShi) {
			beishu *= 3;
		}
		if (dipaihasDawang && dipaihasXiaowang) {
			beishu = beishu << 2;
		} else if (dipaihasDawang) {
			beishu = beishu << 1;
		} else if (renshu == 2 && dipaihasXiaowang) {
			beishu = beishu << 1;
		}
		if (chuntian) {
			beishu = beishu << 1;
		}
		if (fanchuntian) {
			beishu = beishu << 1;
		}
		beishu = beishu << dachuZhadanCount;
		value = beishu;
	}

	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
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
