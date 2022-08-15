package com.example.models;

public class BookingTimeSlot implements Comparable<BookingTimeSlot>{
    private Long startTime;
    private Long endTime;

    public BookingTimeSlot(long startTime, long endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public int compareTo(BookingTimeSlot o) {
        if (startTime > o.getStartTime())
            return 1;
        else if (startTime < o.getStartTime())
            return -1;
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (this == obj)
            return true;
        BookingTimeSlot other = (BookingTimeSlot) obj;
        return startTime.equals(other.startTime);
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }
}
