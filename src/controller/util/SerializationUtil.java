package controller.util;

import model.game.GameScore;
import model.util.BoardItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationUtil {

    private final static File SCORES_DIR = new File("assets/scores");
    private final static File MAPS_DIR = new File("assets/maps/serialized");

    public static boolean writeScores(GameScore score) {
        String writePath = SCORES_DIR.getPath() + "/" + score.getGameName() + "_" + System.currentTimeMillis() + ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(writePath))) {
            oos.writeObject(score);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static List<GameScore> readAllScores() {
        File[] files = SCORES_DIR.listFiles();
        List<GameScore> scores = new ArrayList<>();

        if (files == null) return scores;

        for (File f : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                GameScore score = (GameScore) ois.readObject();
                scores.add(score);
            } catch (IOException | ClassNotFoundException e) {
            }
        }

        return scores;
    }

    public static BoardItem[][] readMap(MapType mapType) {
        File file = new File(MAPS_DIR + "/" + mapType.toString().toLowerCase() + ".ser");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (BoardItem[][]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {}
        return null;
    }

}
