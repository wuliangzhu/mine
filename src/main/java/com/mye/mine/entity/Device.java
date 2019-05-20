package com.mye.mine.entity;

/**
 * 设备
 */
public class Device {
    // 设备Id
    private long id;
    // 管家序列号
    private String tid;
    // 设备名字
    private String name;
    // 房间名字
    private int roomId;
    // 酒店Id
    private String hotelGid;

    // 房间名字
    private String roomName;
    // 酒店名字
    private String hotelName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getHotelGid() {
        return hotelGid;
    }

    public void setHotelGid(String hotelGid) {
        this.hotelGid = hotelGid;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}
