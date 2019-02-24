package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import de.berlin.hwr.basketistics.Persistency.Repository.Repository;

public class EventViewModel extends AndroidViewModel {

    private PlayerEvents[] playerEvents = new PlayerEvents[5];
    private Repository repository;

    public EventViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        this.playerEvents = repository.getAllPlayerEvents();
    }

    public PlayerEvents getPlayerEvents(int playerIndex) {
        return playerEvents[playerIndex];
    }

    public class PlayerEvents {
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

        public void addPoints(Integer points) {
            this.points.setValue(this.points.getValue() + points);
        }

        public MutableLiveData<Integer> getAssist() {
            return assist;
        }

        public void addAssist(Integer assist) {
            this.assist.setValue(this.assist.getValue() + assist);
        }

        public MutableLiveData<Integer> getRebound() {
            return rebound;
        }

        public void addRebound(Integer rebound) {
            this.rebound.setValue(this.rebound.getValue() + rebound);
        }

        public MutableLiveData<Integer> getFoul() {
            return foul;
        }

        public void addFoul(Integer foul) {
            this.foul.setValue(this.foul.getValue() + foul);
        }

        public MutableLiveData<Integer> getBlock() {
            return block;
        }

        public void addBlock(Integer block) {
            this.block.setValue(this.block.getValue() + block);
        }

        public MutableLiveData<Integer> getTurnover() {
            return turnover;
        }

        public void addTurnover(Integer turnover) {
            this.turnover.setValue(this.turnover.getValue() + turnover);
        }

        public MutableLiveData<Integer> getSteal() {
            return steal;
        }

        public void addSteal(Integer steal) {
            this.steal.setValue(this.steal.getValue() + steal);
        }
    }
}
