package model.game;

import java.util.Comparator;

public class GameScoreComparator implements Comparator<GameScore> {
    public GameScoreComparator() {}

    @Override
    public int compare(GameScore o1, GameScore o2) {
        int res = Long.compare(o2.getGainedPoints(), o1.getGainedPoints());
        if (res == 0)
            res = Integer.compare(o1.getMinutes(), o2.getMinutes());
        if (res == 0)
            res = Integer.compare(o1.getSeconds(), o2.getSeconds());
        if (res == 0)
            res = o2.getPlayedDate().compareTo(o1.getPlayedDate());
        return res;
    }
}
