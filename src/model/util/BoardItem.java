package model.util;

public enum BoardItem {

    SPACE(0, ""),

    UNREACHABLE(0, ""),

    POINT(10, "assets/icons/game/boosts/eatable_point.png"),

    SPEED_BOOST(20, "assets/icons/game/boosts/speed_boost.png"),

    LIFE_BOOST(20, "assets/icons/game/boosts/life_boost.png"),

    SHIELD_BOOST(20, "assets/icons/game/boosts/shield_boost.png"),

    CHERRY_POINT_BOOST(100, "assets/icons/game/boosts/cherry_point_boost.png"),

    SLOWDOWN_GHOST_BOOST(20, "assets/icons/game/boosts/ghost_slowdown_boost.png"),

    WALL(0, ""),

    PACMAN_GHOST_COLLISION(0, "");

    private BoardItem(int value, String iconPath) {
        this.eatValue = value;
        this.staticIconPath = iconPath;
    }

    public final int eatValue;
    public final String staticIconPath;

}
