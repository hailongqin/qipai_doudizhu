package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.qiangdizhu.CannotQiangdizhuException;
import com.anbang.qipai.doudizhu.cqrs.c.domain.qiangdizhu.QiangdizhuDizhuDeterminer;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.QiangdizhuResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhu;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.Qiangdizhu;
import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.pan.PanActionFrame;
import com.dml.doudizhu.preparedapai.avaliablepai.OneAvaliablePaiFiller;
import com.dml.doudizhu.preparedapai.fapai.OnePlayerOnePaiFaPaiStrategy;
import com.dml.doudizhu.preparedapai.lipai.DianshuOrPaishuShoupaiSortStrategy;
import com.dml.doudizhu.preparedapai.luanpai.RandomLuanPaiStrategy;
import com.dml.doudizhu.preparedapai.position.RandomMenfengDeterminer;
import com.dml.doudizhu.preparedapai.xianda.DizhuXiandaDeterminer;
import com.dml.mpgame.game.GameValueObject;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGame;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.player.GamePlayer;
import com.dml.mpgame.game.player.PlayerPlaying;

public class PukeGame extends FixedPlayersMultipanAndVotetofinishGame {
	private int panshu;
	private int renshu;
	private boolean qxp;// 去小牌
	private Ju ju;
	private Map<String, Integer> playeTotalScoreMap = new HashMap<>();

	public QiangdizhuResult qiangdizhu(String playerId, boolean qiang, long currentTime) throws Exception {
		if (!state.name().equals(Qiangdizhu.name)) {
			throw new CannotQiangdizhuException();
		}
		QiangdizhuResult result = new QiangdizhuResult();
		Pan currentPan = ju.getCurrentPan();
		QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = (QiangdizhuDizhuDeterminer) ju.getDizhuDeterminer();
		String dizhu = qiangdizhuDizhuDeterminer.determineToDizhu(ju, playerId, qiang);
		if (dizhu != null) {
			currentPan.setDizhuPlayerId(dizhu);
			state = new Playing();
			updateAllPlayersState(new PlayerPlaying());
			ju.startPlaying(currentTime);
		}
		result.setPukeGame(new PukeGameValueObject(this));
		result.setPlayerQiangdizhuMap(qiangdizhuDizhuDeterminer.getPlayerQiangdizhuMap());
		return result;
	}

	public PanActionFrame createJuAndStartFirstPan(long startTime) throws Exception {
		ju = new Ju();
		ju.setPanFinishiDeterminer(new DoudizhuPanFinishiDeterminer());
		ju.setJuFinishiDeterminer(new DoudizhuJuFinishiDeterminer());
		ju.setAvaliablePaiFiller(new OneAvaliablePaiFiller());
		ju.setLuanPaiStrategyForFirstPan(new RandomLuanPaiStrategy());
		ju.setLuanPaiStrategyForNextPan(new RandomLuanPaiStrategy());
		ju.setFaPaiStrategyForFirstPan(new OnePlayerOnePaiFaPaiStrategy());
		ju.setFaPaiStrategyForNextPan(new OnePlayerOnePaiFaPaiStrategy());
		QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = new QiangdizhuDizhuDeterminer();
		qiangdizhuDizhuDeterminer.setRenshu(renshu);
		qiangdizhuDizhuDeterminer.init(ju);
		ju.setDizhuDeterminer(qiangdizhuDizhuDeterminer);
		ju.setMenfengDeterminerForFirstPan(new RandomMenfengDeterminer());
		ju.setMenfengDeterminerForNextPan(new RandomMenfengDeterminer());
		ju.setXiandaDeterminerForFirstPan(new DizhuXiandaDeterminer());
		ju.setXiandaDeterminerForNextPan(new DizhuXiandaDeterminer());
		ju.setShoupaiSortStrategy(new DianshuOrPaishuShoupaiSortStrategy());
		ju.setWaihaoGenerator(new DoudizhuWaihaoGenerator());
		ju.setCurrentPanResultBuilder(new DoudizhuCurrentPanResultBuilder());
		ju.setJuResultBuilder(new DoudizhuJuResultBuilder());
		ju.setAllKedaPaiSolutionsGenerator(new DoudizhuAllKedaPaiSolutionsGenerator());
		ju.setDianShuZuYaPaiSolutionCalculator(new DoudizhuDianShuZuYaPaiSolutionCalculator());
		ju.setZaDanYaPaiSolutionCalculator(new DoudizhuZaDanYaPaiSolutionCalculator());
		ju.setDaPaiSolutionsTipsFilter(new DoudizhuDaPaiSolutionsTipsFilter());
		ju.setYaPaiSolutionsTipsFilter(new DoudizhuYaPaiSolutionsTipsFilter());

		ju.startFirstPan(allPlayerIds(), startTime);
		return ju.getCurrentPan().findLatestActionFrame();
	}

	public PukeActionResult da(String playerId, List<Integer> paiIds, String dianshuZuheIdx, long actionTime)
			throws Exception {
		PanActionFrame panActionFrame = ju.da(playerId, paiIds, dianshuZuheIdx, actionTime);
		PukeActionResult result = new PukeActionResult();
		result.setPanActionFrame(panActionFrame);
		return result;
	}

	public PukeActionResult guo(String playerId, long actionTime) throws Exception {
		PanActionFrame panActionFrame = null;
		panActionFrame = ju.guo(playerId, actionTime);

		PukeActionResult result = new PukeActionResult();
		result.setPanActionFrame(panActionFrame);
		if (state.name().equals(VoteNotPassWhenPlaying.name)) {
			state = new Playing();
		}
		result.setPukeGame(new PukeGameValueObject(this));
		return result;
	}

	public PanActionFrame findFirstPanActionFrame() {
		return ju.getCurrentPan().findLatestActionFrame();
	}

	@Override
	protected boolean checkToFinishGame() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean checkToFinishCurrentPan() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void startNextPan() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updatePlayerToExtendedVotingState(GamePlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateToExtendedVotingState() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updatePlayerToExtendedVotedState(GamePlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void recoveryPlayersStateFromExtendedVoting() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateToVoteNotPassStateFromExtendedVoting() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends GameValueObject> T toValueObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(long currentTime) throws Exception {
		createJuAndStartFirstPan(currentTime);
		state = new Qiangdizhu();
		updateAllPlayersState(new PlayerQiangdizhu());
	}

	@Override
	public void finish() throws Exception {
		// TODO Auto-generated method stub

	}

	public int getPanshu() {
		return panshu;
	}

	public void setPanshu(int panshu) {
		this.panshu = panshu;
	}

	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}

	public boolean isQxp() {
		return qxp;
	}

	public void setQxp(boolean qxp) {
		this.qxp = qxp;
	}

	public Ju getJu() {
		return ju;
	}

	public void setJu(Ju ju) {
		this.ju = ju;
	}

	public Map<String, Integer> getPlayeTotalScoreMap() {
		return playeTotalScoreMap;
	}

	public void setPlayeTotalScoreMap(Map<String, Integer> playeTotalScoreMap) {
		this.playeTotalScoreMap = playeTotalScoreMap;
	}

}
