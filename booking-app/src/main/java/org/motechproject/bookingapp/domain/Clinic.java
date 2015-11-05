package org.motechproject.bookingapp.domain;

import org.motechproject.mds.annotations.Entity;
import org.motechproject.mds.annotations.Field;

import java.util.List;

@Entity
public class Clinic {

    @Field(required = true)
    private String location;

    private List<Room> rooms;
    private Integer maxScreeningVisits;
    private Integer maxPrimeVisits;
    private Integer maxPrimeFollowUpVisits;
    private Integer maxBoosterVisits;
    private Integer maxBoosterFollowUpVisits;
    private Integer maxBoosterSecondFollowUpVisits;
    private Integer maxBoosterThirdFollowUpVisits;
    private Integer maxLongTermFollowUpVisits;
    private Integer maxLongTermSecondFollowUpVisits;
    private Integer maxLongTestThirdFollowUpVisits;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Integer getMaxScreeningVisits() {
        return maxScreeningVisits;
    }

    public void setMaxScreeningVisits(Integer maxScreeningVisits) {
        this.maxScreeningVisits = maxScreeningVisits;
    }

    public Integer getMaxPrimeVisits() {
        return maxPrimeVisits;
    }

    public void setMaxPrimeVisits(Integer maxPrimeVisits) {
        this.maxPrimeVisits = maxPrimeVisits;
    }

    public Integer getMaxPrimeFollowUpVisits() {
        return maxPrimeFollowUpVisits;
    }

    public void setMaxPrimeFollowUpVisits(Integer maxPrimeFollowUpVisits) {
        this.maxPrimeFollowUpVisits = maxPrimeFollowUpVisits;
    }

    public Integer getMaxBoosterVisits() {
        return maxBoosterVisits;
    }

    public void setMaxBoosterVisits(Integer maxBoosterVisits) {
        this.maxBoosterVisits = maxBoosterVisits;
    }

    public Integer getMaxBoosterFollowUpVisits() {
        return maxBoosterFollowUpVisits;
    }

    public void setMaxBoosterFollowUpVisits(Integer maxBoosterFollowUpVisits) {
        this.maxBoosterFollowUpVisits = maxBoosterFollowUpVisits;
    }

    public Integer getMaxBoosterSecondFollowUpVisits() {
        return maxBoosterSecondFollowUpVisits;
    }

    public void setMaxBoosterSecondFollowUpVisits(Integer maxBoosterSecondFollowUpVisits) {
        this.maxBoosterSecondFollowUpVisits = maxBoosterSecondFollowUpVisits;
    }

    public Integer getMaxBoosterThirdFollowUpVisits() {
        return maxBoosterThirdFollowUpVisits;
    }

    public void setMaxBoosterThirdFollowUpVisits(Integer maxBoosterThirdFollowUpVisits) {
        this.maxBoosterThirdFollowUpVisits = maxBoosterThirdFollowUpVisits;
    }

    public Integer getMaxLongTermFollowUpVisits() {
        return maxLongTermFollowUpVisits;
    }

    public void setMaxLongTermFollowUpVisits(Integer maxLongTermFollowUpVisits) {
        this.maxLongTermFollowUpVisits = maxLongTermFollowUpVisits;
    }

    public Integer getMaxLongTermSecondFollowUpVisits() {
        return maxLongTermSecondFollowUpVisits;
    }

    public void setMaxLongTermSecondFollowUpVisits(Integer maxLongTermSecondFollowUpVisits) {
        this.maxLongTermSecondFollowUpVisits = maxLongTermSecondFollowUpVisits;
    }

    public Integer getMaxLongTestThirdFollowUpVisits() {
        return maxLongTestThirdFollowUpVisits;
    }

    public void setMaxLongTestThirdFollowUpVisits(Integer maxLongTestThirdFollowUpVisits) {
        this.maxLongTestThirdFollowUpVisits = maxLongTestThirdFollowUpVisits;
    }
}
