package procurement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Conflict {

    @JsonProperty("risk_id_1")
    private int riskID1;

    @JsonProperty("risk_id_2")
    private int riskID2;

    @JsonProperty("solution_id_1")
    private int solutionID1;

    @JsonProperty("solution_id_2")
    private int solutionID2;

    public int getRiskID1() {
        return riskID1;
    }

    public void setRiskID1(int riskID1) {
        this.riskID1 = riskID1;
    }

    public int getRiskID2() {
        return riskID2;
    }

    public void setRiskID2(int riskID2) {
        this.riskID2 = riskID2;
    }

    public int getSolutionID1() {
        return solutionID1;
    }

    public void setSolutionID1(int solutionID1) {
        this.solutionID1 = solutionID1;
    }

    public int getSolutionID2() {
        return solutionID2;
    }

    public void setSolutionID2(int solutionID2) {
        this.solutionID2 = solutionID2;
    }
}
