package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import de.berlin.hwr.basketistics.Persistency.Repository.Repository;


public class TeamReportViewModel extends AndroidViewModel {

    Repository repository = new Repository(getApplication());

    TeamReportViewModel(@NonNull Application application){
    super(application);
    }
    

    public class TeamReport {

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

        public TeamReport(
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


    private TeamReport fetchPlayerReports()
    {


        TeamReport teamReport = new TeamReport(
                0,
                repository.getOnePointer(),
                repository.getTwoPointer(),
                repository.getThreePointer(),
                repository.getOnePointerAttempt(),
                repository.getTwoPointerAttempt(),
                repository.getThreePointerAttempt(),
                repository.getRebounds(),
                repository.getAssists(),
                repository.getSteals(),
                repository.getBlocks(),
                repository.getTurnover(),
                repository.getFouls(),
                repository.getGamesPlayed()
                );


        return teamReport;
    }

    public TeamReport getReport(){
        return fetchPlayerReports();
    }

}

