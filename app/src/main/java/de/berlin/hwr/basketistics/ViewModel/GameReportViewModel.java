package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Repository.Repository;


public class GameReportViewModel extends AndroidViewModel {

    Repository repository = new Repository(getApplication());

    GameReportViewModel(@NonNull Application application){
    super(application);

    }

    public class GameReport {

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


        public GameReport(
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
                int foul
        ) {


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
        }
    }


    private GameReport fetchGameReport(int matchId)
    {
       
            GameReport gameReport = new GameReport(
                    0,
                    repository.getOnePointerByMatchId(matchId),
                    repository.getTwoPointerByMatchId(matchId),
                    repository.getThreePointerByMatchId(matchId),
                    repository.getOnePointerAttemptByMatchId(matchId),
                    repository.getTwoPointerAttemptByMatchId(matchId),
                    repository.getThreePointerAttemptByMatchId(matchId),
                    repository.getReboundsByMatchId(matchId),
                    repository.getAssistsByMatchId(matchId),
                    repository.getStealsByMatchId(matchId),
                    repository.getBlocksByMatchId(matchId),
                    repository.getTurnoverByMatchId(matchId),
                    repository.getFoulsByMatchId(matchId)
            );
        return gameReport;
    }



    public GameReport getGameReport(int matchId){


        return fetchGameReport(matchId);
    }

}

