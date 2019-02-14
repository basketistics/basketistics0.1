package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

public class BasketisticsViewModel extends AndroidViewModel {

    private final static String TAG = "BasketisticsViewModel";

    private MutableLiveData<Integer> playerId = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> playerNumber = new MutableLiveData<Integer>();
    private MutableLiveData<String> playerName = new MutableLiveData<String>();
    private MutableLiveData<Integer> points = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> assist = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> rebound = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> foul = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> block = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> turnover = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> steal = new MutableLiveData<Integer>();

    // Construktor
    public BasketisticsViewModel(@NonNull Application application) {
        super(application);

        playerId.setValue(0);
        playerNumber.setValue(0);
        playerName.setValue("");
        points.setValue(0);
        assist.setValue(0);
        rebound.setValue(0);
        foul.setValue(0);
        block.setValue(0);
        turnover.setValue(0);
        steal.setValue(0);
    }

    //// ---------- getter and "setter" ---------- ////

    // playerId
    public MutableLiveData<Integer> getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId.setValue(playerId);
    }

    // playerNumber
    public MutableLiveData<Integer> getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber.setValue(playerNumber);
    }

    // playerName
    public MutableLiveData<String> getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName.setValue(playerName);
    }

    // points
    public MutableLiveData<Integer> getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points.setValue(points);
    }

    public void incPoints(Integer iPoints) {
        Log.i(TAG, "entered incPoints().");
        Integer currPoints = getPoints().getValue() + iPoints;
        this.setPoints(currPoints);
        Log.i(TAG, "Points was set to " + getPoints().getValue());
    }

    //assist
    public MutableLiveData<Integer> getAssist() {
        return assist;
    }

    public void setAssist(Integer assist) {
        this.assist .setValue(assist);
    }

    public void incAssist(Integer iAssist) {
        Integer currAssist = getAssist().getValue() + iAssist;
        this.setAssist(currAssist);
    }

    // rebound
    public MutableLiveData<Integer> getRebound() {
        return rebound;
    }

    public void setRebound(Integer rebound) {
        this.rebound.setValue(rebound);
    }

    public void incRebound(Integer iRebound) {
        Integer currRebound = getRebound().getValue() + iRebound;
        this.setRebound(currRebound);
    }


    // foul
    public MutableLiveData<Integer> getFoul() {
        return foul;
    }

    public void setFoul(Integer foul) {
        this.foul.setValue(foul);
    }

    public void incFoul(Integer iFoul) {
        Integer currFoul = getFoul().getValue() + iFoul;
        this.setFoul(currFoul);
    }

    // block
    public MutableLiveData<Integer> getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block.setValue(block);
    }

    public void incBlock(Integer iBlock) {
        Integer currBlock = getBlock().getValue() + iBlock;
        this.setBlock(currBlock);
    }

    // turnover
    public MutableLiveData<Integer> getTurnover() {
        return turnover;
    }

    public void setTurnover(Integer turnover) {
        this.turnover.setValue(turnover);
    }

    public void incTurnover(Integer iTurnover) {
        Integer currTurnover = getTurnover().getValue() + iTurnover;
        this.setTurnover(currTurnover);
    }

    // steal
    public MutableLiveData<Integer> getSteal() {
        return steal;
    }

    public void setSteal(Integer steal) {
        this.steal.setValue(steal);
    }

    public void incSteal(Integer iSteal) {
        Integer currSteal = getSteal().getValue() + iSteal;
        this.setSteal(currSteal);
    }
}
