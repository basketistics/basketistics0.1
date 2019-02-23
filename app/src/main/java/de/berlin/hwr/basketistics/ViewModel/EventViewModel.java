package de.berlin.hwr.basketistics.ViewModel;

import android.arch.lifecycle.MutableLiveData;

public class EventViewModel {

    private PlayerEvents[] playerEvents = new PlayerEvents[5];

    private class PlayerEvents {
        private MutableLiveData<Integer> playerId = new MutableLiveData<Integer>();
        private MutableLiveData<String> playerFirstName = new MutableLiveData<String>();
        private MutableLiveData<Integer> playerNumber = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> points = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> assist = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> rebound = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> foul = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> block = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> turnover = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> steal = new MutableLiveData<Integer>();

        public MutableLiveData<Integer> getPlayerId() {
            return playerId;
        }

        public void setPlayerId(Integer playerId) {
            this.playerId.setValue(playerId);
        }

        public MutableLiveData<String> getPlayerFirstName() {
            return playerFirstName;
        }

        public void setPlayerFirstName(String playerFirstName) {
            this.playerFirstName.setValue(playerFirstName);
        }

        public MutableLiveData<Integer> getPlayerNumber() {
            return playerNumber;
        }

        public void setPlayerNumber(Integer playerNumber) {
            this.playerNumber.setValue(playerNumber);
        }

        public MutableLiveData<Integer> getPoints() {
            return points;
        }

        public void setPoints(Integer points) {
            this.points.setValue(points);
        }

        public MutableLiveData<Integer> getAssist() {
            return assist;
        }

        public void setAssist(Integer assist) {
            this.assist.setValue(assist);
        }

        public MutableLiveData<Integer> getRebound() {
            return rebound;
        }

        public void setRebound(Integer rebound) {
            this.rebound.setValue(rebound);
        }

        public MutableLiveData<Integer> getFoul() {
            return foul;
        }

        public void setFoul(Integer foul) {
            this.foul.setValue(foul);
        }

        public MutableLiveData<Integer> getBlock() {
            return block;
        }

        public void setBlock(Integer block) {
            this.block.setValue(block);
        }

        public MutableLiveData<Integer> getTurnover() {
            return turnover;
        }

        public void setTurnover(Integer turnover) {
            this.turnover.setValue(turnover);
        }

        public MutableLiveData<Integer> getSteal() {
            return steal;
        }

        public void setSteal(Integer steal) {
            this.steal.setValue(steal);
        }
    }
}
