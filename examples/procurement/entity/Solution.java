package procurement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Solution {


    @JsonProperty("risk_id")
    private int solutionID;

    @JsonProperty("cost")
    private int cost;

    @JsonProperty("diff")
    private int diff;

    @JsonProperty("priority")
    private int priority;

    @JsonProperty("time")
    private int time;

    public int getSolutionID() {
        return solutionID;
    }

    public void setSolutionID(int solutionID) {
        this.solutionID = solutionID;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
