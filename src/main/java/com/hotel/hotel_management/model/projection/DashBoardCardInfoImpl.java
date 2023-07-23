package com.hotel.hotel_management.model.projection;

public class DashBoardCardInfoImpl implements DashBoardCardInfo{
    private Integer count;
    private String name;
    public DashBoardCardInfoImpl(Integer count, String name) {
        this.count = count;
        this.name = name;
    }
    @Override
    public Integer getCount() {
        return this.count;
    }
    @Override
    public String getName() {
        return this.name;
    }
}
