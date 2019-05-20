package com.mye.mine.entity;

import java.util.Arrays;
import java.util.List;

/**
 * 楼栋名字
 * 房间名字列表
 * 服务器ID：公建 和  玉岩
 */
public class Hotel {
    private String hotel;
    private String rooms;
    private List<String> roomList;
    private String datasource;

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
        String[] tmp = this.rooms.trim().split("\\s+");
        this.roomList = Arrays.asList(tmp);
    }

    public List<String> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<String> roomList) {
        this.roomList = roomList;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
}
