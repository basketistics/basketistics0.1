package de.berlin.hwr.basketistics.ViewModel;

import android.arch.lifecycle.AndroidViewModel;
import android.app.Application;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
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
        public boolean starter;
        public long spielzeit;

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
                boolean starter,
                long spielzeit
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
            this.starter = starter;
            this.spielzeit = spielzeit;
        }
    }

    // TODO: TEST!
    private long calcTimePlayed(Boolean isStarter, long startTime, long endTime, List<EventEntity> eventEntities) {

        long time = 0;
        boolean listIsNotEmpty = true;

        int i = 0;
        if (isStarter) {
            if (eventEntities.size() % 2 == 0) {
                time = eventEntities.get(0).getTimestamp().getTime() - startTime;
            }
            while (listIsNotEmpty) {
                time += eventEntities.get(i + 1).getTimestamp().getTime() - eventEntities.get(i).getTimestamp().getTime();
                if (eventEntities.get(i + 2) == null ) { listIsNotEmpty = false; }
                if (eventEntities.get(i + 2) != null && eventEntities.get(i + 3) == null ) {
                    time += endTime - eventEntities.get(i + 2).getTimestamp().getTime();
                    listIsNotEmpty = false;
                }
            }
        }

        return time;
    }

    private void fetchPlayerReports()
    {


        List<Integer> playerIds = repository.getPlayerIdsByMatch(matchId);
        for (Integer id: playerIds)
        {
            boolean thisPlayerStarts;
            List<Integer> starterList = repository.getStarterByMatchId(matchId);
            if (starterList.contains(id))
                thisPlayerStarts = true;
            else thisPlayerStarts = false;
            playerReports.add(new PlayerReport(
                    id,
                    0,
                    repository.getOnePointerByPlayerAndMatch(id, matchId),
                    repository.getTwoPointerByPlayerAndMatch(id, matchId),
                    repository.getThreePointerByPlayerAndMatch(id, matchId),
                    repository.getOnePointerAttemptByPlayerAndMatch(id, matchId),
                    repository.getTwoPointerAttemptByPlayerAndMatch(id, matchId),
                    repository.getThreePointerAttemptByPlayerAndMatch(id, matchId),
                    repository.getReboundsByPlayerAndMatch(id, matchId),
                    repository.getAssistsByPlayerAndMatch(id, matchId),
                    repository.getStealsByPlayerAndMatch(id, matchId),
                    repository.getBlocksByPlayerAndMatch(id, matchId),
                    repository.getTurnoverByPlayerAndMatch(id, matchId),
                    repository.getFoulsByPlayerAndMatch(id, matchId),
                    thisPlayerStarts,
                    0));

        }
    }

    public PlayerReport getReportByPlayerId(int playerId){
        fetchPlayerReports();
        for (PlayerReport pr: playerReports)
        {
            if(pr.playerId==playerId)
                return pr;
        }
        return playerReports.get(1);
    }

}

