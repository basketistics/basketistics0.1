package de.berlin.hwr.basketistics.ViewModel;

import android.arch.lifecycle.AndroidViewModel;
import android.app.Application;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Repository.Repository;


public class SingleGameReportViewModel extends AndroidViewModel {

    Repository repository = new Repository(getApplication());
    private int matchId;

    private List<PlayerReport> playerReports = new ArrayList<PlayerReport>();

    SingleGameReportViewModel(@NonNull Application application){
    super(application);


    }
    public void setMatchId(int matchId)
    {
        this.matchId = matchId;
    }

    public class PlayerReport {


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
        public boolean starter;
        public long spielzeit;

        public PlayerReport(int points, int onePoint, int twoPoints, int threePoints, int onePointAttempt, int twoPointsAttempt, int threePointsAttempt, int rebound, int assist, int steal, int block, int turnover, int foul, boolean starter, long spielzeit) {


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
            this.starter = starter;
            this.spielzeit = spielzeit;
        }
    }


    private void fetchPlayerReports()
    {


        List<Integer> playerIds = repository.getPlayerIdsByMatch(matchId);
        for (Integer id: playerIds)
        {
            playerReports.add(new PlayerReport(
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    true,
                    0));

        }
    }

}

