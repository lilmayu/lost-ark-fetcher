package dev.mayuna.lostarkfetcher.objects.api.other;

import lombok.Getter;

public enum LostArkRegion {

    NORTH_AMERICA_WEST("North America West", 0),
    NORTH_AMERICA_EAST("North America East", 1),
    EUROPE_CENTRAL("Europe Central", 2),
    EUROPE_WEST("Europe West", 3),
    SOUTH_AMERICA("South America", 4);

    private final @Getter String prettyName;
    private final @Getter int dataIndex;

    LostArkRegion(String prettyName, int dataIndex) {
        this.prettyName = prettyName;
        this.dataIndex = dataIndex;
    }

    public static LostArkRegion get(String name) {
        for (LostArkRegion region : LostArkRegion.values()) {
            if (region.name().equalsIgnoreCase(name) || region.prettyName.equalsIgnoreCase(name)) {
                return region;
            }
        }

        return null;
    }

    public static LostArkRegion get(int dataIndex) {
        for (LostArkRegion region : LostArkRegion.values()) {
            if (region.dataIndex == dataIndex) {
                return region;
            }
        }

        return null;
    }
}
