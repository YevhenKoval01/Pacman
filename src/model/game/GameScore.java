package model.game;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class GameScore implements Serializable, Comparable<GameScore> {

    private static final long serialVersionUID = 1;

    private long gainedPoints;
    private int seconds;
    private int minutes;
    private String gameName;
    private LocalDateTime playedDate;

    public GameScore() {}

    public GameScore(long gainedPoints, int seconds, int minutes, String gameName) {
        this.gainedPoints = gainedPoints;
        this.seconds = seconds;
        this.minutes = minutes;
        this.gameName = gameName;
        this.playedDate = LocalDateTime.now();
    }

    public long getGainedPoints() {
        return this.gainedPoints;
    }

    public void setGainedPoints(long gainedPoints) {
        this.gainedPoints = gainedPoints;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getGameName() {
        return this.gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public LocalDateTime getPlayedDate() {
        return this.playedDate;
    }

    public void setPlayedDate(LocalDateTime playedDate) {
        this.playedDate = playedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GameScore gameScore = (GameScore) o;
        return this.gainedPoints == gameScore.gainedPoints &&
                this.seconds == gameScore.seconds &&
                this.minutes == gameScore.minutes &&
                Objects.equals(this.gameName, gameScore.gameName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.gainedPoints, this.seconds, this.minutes, this.gameName);
    }

    @Override
    public String toString() {
        return "GameScore:" +
                "\n\t-gainedPoints=" + this.gainedPoints +
                "\n\t-seconds=" + this.seconds +
                "\n\t-minutes=" + this.minutes +
                "\n\t-gameName=" + this.gameName;
    }

    @Override
    public int compareTo(GameScore o) {
        int res = Long.compare(o.gainedPoints, this.gainedPoints);
        if (res == 0)
            res = Integer.compare(o.minutes, this.minutes);
        if (res == 0)
            res = Integer.compare(o.seconds, this.seconds);
        if (res == 0)
            res = o.playedDate.compareTo(this.playedDate);
        return res;
    }
}
