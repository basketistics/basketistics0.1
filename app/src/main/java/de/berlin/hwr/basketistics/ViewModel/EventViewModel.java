package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.Player;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.Persistency.Repository.Repository;

public class EventViewModel extends AndroidViewModel {

    private final static String TAG = "EventViewModel";

    private PlayerEvents[] playerEvents = new PlayerEvents[5];
    private Repository repository;

    public EventViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        // this.playerEvents = repository.getAllEvents();

        // For Testing
        if (playerEvents[0] == null) {
            for (int i = 0; i < 5; i++) {
                playerEvents[i] = new PlayerEvents(
                        new PlayerEntity( "Player", "" + i, i, "Player " + i),
                        0,
                        0,
                        0,
                        0, 
                        0,
                        0,
                        0);
            }
        }
    }

    public PlayerEvents getPlayerEvents(int playerIndex) {
        return playerEvents[playerIndex];
    }

    public class PlayerEvents {
        private MutableLiveData<PlayerEntity> player = new MutableLiveData<PlayerEntity>();
        private MutableLiveData<Integer> points = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> assist = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> rebound = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> foul = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> block = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> turnover = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> steal = new MutableLiveData<Integer>();

        // Constructor
        public PlayerEvents(
                PlayerEntity player,
                int points,
                int assist,
                int rebound,
                int foul,
                int block,
                int turnover,
                int steal) {

            this.player.setValue(player);
            this.points.setValue(points);
            this.assist.setValue(assist);
            this.rebound.setValue(rebound);
            this.foul.setValue(foul);
            this.block.setValue(block);
            this.turnover.setValue(turnover);
            this.steal.setValue(steal);
        }

        public MutableLiveData<PlayerEntity> getPlayer() {
            return player;
        }

        public void setPlayer(PlayerEntity player) {
            this.player.setValue(player);
        }

        public MutableLiveData<Integer> getPoints() {
            return points;
        }

        public void addPoints(Integer playerIndex, Integer points) {
            this.points.setValue(this.points.getValue() + points);
            repository.insertEvent(new EventEntity(1, player.getValue().getId(), 1));
        }

        public MutableLiveData<Integer> getAssist() {
            return assist;
        }

        public void addAssist(Integer playerIndex, Integer assist) {
            this.assist.setValue(this.assist.getValue() + assist);
            repository.insertEvent(new EventEntity(2, player.getValue().getId(), 1));
        }

        public MutableLiveData<Integer> getRebound() {
            return rebound;
        }

        public void addRebound(Integer playerIndex, Integer rebound) {
            this.rebound.setValue(this.rebound.getValue() + rebound);
            repository.insertEvent(new EventEntity(3, player.getValue().getId(), 1));

            // Test
            List<EventEntity> events = repository.getAllEvents();
            for (EventEntity eventEntity : events) {
                Log.i(TAG, eventEntity.getTimestamp() + " " + eventEntity.getId() + " " + eventEntity.toString() + " " + eventEntity.getPlayer() + " " + eventEntity.getEventType());
            }
        }

        public MutableLiveData<Integer> getFoul() {
            return foul;
        }

        public void addFoul(Integer playerIndex, Integer foul) {
            this.foul.setValue(this.foul.getValue() + foul);
            repository.insertEvent(new EventEntity(4, player.getValue().getId(), 1));
        }

        public MutableLiveData<Integer> getBlock() {
            return block;
        }

        public void addBlock(Integer playerIndex, Integer block) {
            this.block.setValue(this.block.getValue() + block);
            repository.insertEvent(new EventEntity(5, player.getValue().getId(), 1));
        }

        public MutableLiveData<Integer> getTurnover() {
            return turnover;
        }

        public void addTurnover(Integer playerIndex, Integer turnover) {
            this.turnover.setValue(this.turnover.getValue() + turnover);
            repository.insertEvent(new EventEntity(6, player.getValue().getId(), 1));
        }

        public MutableLiveData<Integer> getSteal() {
            return steal;
        }

        public void addSteal(Integer playerIndex, Integer steal) {
            this.steal.setValue(this.steal.getValue() + steal);
            repository.insertEvent(new EventEntity(7, player.getValue().getId(), 1));
        }
    }
}
