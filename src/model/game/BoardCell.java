package model.game;

import model.player.PlayerModel;
import model.util.BoardItem;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BoardCell {

    private BoardItem item;
    private final LinkedList<PlayerModel> players;

    public BoardCell(BoardItem item) {
        this.item = item;
        this.players = new LinkedList<>();
    }

    public BoardItem getItem() {
        return this.item;
    }

    public void setItem(BoardItem item) {
        this.item = item;
    }

    public PlayerModel getFirstPlayer() {
        synchronized (this.players) {
            try {
                return this.players.getFirst();
            } catch (NoSuchElementException nse) {
                return null;
            }
        }
    }

    public int currCellPlayerCount() {
        synchronized (this.players) {
            return this.players.size();
        }
    }

    public void addPlayerToCell(PlayerModel player) {
        synchronized (this.players) {
            this.players.add(player);
        }
    }

    public void removePlayerFromCell(PlayerModel playerModel) {
        synchronized (this.players) {
            this.players.remove(playerModel);
        }
    }

    public void removeAllPlayers() {
        synchronized (this.players) {
            this.players.clear();
        }
    }
}
