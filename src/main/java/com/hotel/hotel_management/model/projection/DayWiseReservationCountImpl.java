package com.hotel.hotel_management.model.projection;

public class DayWiseReservationCountImpl implements DayWiseReservationCount{
    private Integer count;
    private String startDate;

    public DayWiseReservationCountImpl(Integer count, String startDate) {
        this.count = count;
        this.startDate = startDate;
    }

    @Override
    public Integer getCount() {
        return this.count;
    }

    @Override
    public String getStartDate() {
        return this.startDate;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
