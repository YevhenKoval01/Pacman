package controller.util;

public enum MapType {

    MAP1("assets/maps/images/map1_31x21.png", "31 x 21"),

    MAP2("assets/maps/images/map2_23x23.png", "23 x 23"),

    MAP3("assets/maps/images/map3_22x23.png", "22 x 23"),

    USER_GENERATED("", "");

    private MapType(String imagePath, String dimension) {
        this.IMAGE_PATH = imagePath;
        this.DIMENSION = dimension;
    }

    public final String IMAGE_PATH;
    public final String DIMENSION;

}
