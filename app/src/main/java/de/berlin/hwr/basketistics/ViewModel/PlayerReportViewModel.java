package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Repository.Repository;


public class PlayerReportViewModel extends AndroidViewModel {

    Repository repository = new Repository(getApplication());
    private int playerId;
    

    PlayerReportViewModel(@NonNull Application application){
    super(application);
    }
    
    public void setPlayerId(int playerId)
    {
        this.playerId = playerId;
    }

    public class PlayerReport {

        public int playerId;
        public int points;
        public int onePoint;
        public int twoPoints;
        public int threePoints;
        public int onePointAttempt;
        public int twoPointsAttempt;
        public int threePointsAttempt;
        public int rebound;
        public int assist;
        public int steal;
        public int block;
        public int turnover;
        public int foul;
        public int gamesPlayed;

        public PlayerReport(
                int playerId,
                int points,
                int onePoint,
                int twoPoints,
                int threePoints,
                int onePointAttempt,
                int twoPointsAttempt,
                int threePointsAttempt,
                int rebound,
                int assist,
                int steal,
                int block,
                int turnover,
                int foul,
                int gamesPlayed
        ) {


            this.playerId = playerId;
            this.points = points;
            this.onePoint = onePoint;
            this.twoPoints = twoPoints;
            this.threePoints = threePoints;
            this.onePointAttempt = onePointAttempt;
            this.twoPointsAttempt = twoPointsAttempt;
            this.threePointsAttempt = threePointsAttempt;
            this.rebound = rebound;
            this.assist = assist;
            this.steal = steal;
            this.block = block;
            this.turnover = turnover;
            this.foul = foul;
            this.gamesPlayed = gamesPlayed;
        }
    }


    private PlayerReport fetchPlayerReports()
    {


        PlayerReport playerReport = new PlayerReport(
                playerId,
                0,
                repository.getOnePointerByPlayerId(playerId),
                repository.getTwoPointerByPlayerId(playerId),
                repository.getThreePointerByPlayerId(playerId),
                repository.getOnePointerAttemptByPlayerId(playerId),
                repository.getTwoPointerAttemptByPlayerId(playerId),
                repository.getThreePointerAttemptByPlayerId(playerId),
                repository.getReboundsByPlayerId(playerId),
                repository.getAssistsByPlayerId(playerId),
                repository.getStealsByPlayerId(playerId),
                repository.getBlocksByPlayerId(playerId),
                repository.getTurnoverByPlayerId(playerId),
                repository.getFoulsByPlayerId(playerId),
                repository.getGamesPlayedByPlayerId(playerId)
                );


        return playerReport;
    }

    public PlayerReport getReportByPlayerId(){
        return fetchPlayerReports();
    }

}

