package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class BasketisticsViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> playerId;
    private MutableLiveData<Integer> playerNumber;
    private MutableLiveData<String> playerName;
    private MutableLiveData<Integer> points;
    private MutableLiveData<Integer> assist;
    private MutableLiveData<Integer> rebound;
    private MutableLiveData<Integer> foul;
    private MutableLiveData<Integer> block;
    private MutableLiveData<Integer> turnover;
    private MutableLiveData<Integer> steal;

    // Konstruktor
    public BasketisticsViewModel(@NonNull Application application) {
        super(application);
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
        Integer currPoints = getPoints().getValue() + iPoints;
        this.setPoints(currPoints);
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
        this.setPoints(currAssist);
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
        this.setPoints(currRebound);
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
        this.setPoints(currFoul);
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
        this.setPoints(currBlock);
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
        this.setPoints(currTurnover);
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
        this.setPoints(currSteal);
    }
}
