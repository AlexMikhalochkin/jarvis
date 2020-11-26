package com.mikhalochkin.jarvis.model;

public enum Room {

    KITCHEN(7),
    LIVING_ROOM(8),
    HALLWAY(9),
    RESTROOM(10),
    BATHROOM(11),
    BEDROOM(12),
    CHILDROOM(13),
    ROOM1(22),
    ROOM2(23),
    ROOM3(24),
    ROOM4(25),
    ROOM5(26),
    ROOM6(27),
    ROOM7(28);

    private final int port;

    Room(int port) {
        this.port = port;
    }

    /**
     * @return plc port for the room.
     */
    public int getPort() {
        return port;
    }
}
