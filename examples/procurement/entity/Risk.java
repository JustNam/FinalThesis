package procurement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Risk {

    @JsonProperty("risk_id")
    private int riskID;

    @JsonProperty("financial_impact")
    private int financialImpact;

    @JsonProperty("risk_level")
    private int riskLevel;

    @JsonProperty("probability")
    private double probability;

    @JsonProperty("solutions")
    private ArrayList<Solution> solutions;

    public int getRiskID() {
        return riskID;
    }

    public void setRiskID(int riskID) {
        this.riskID = riskID;
    }

    public int getFinancialImpact() {
        return financialImpact;
    }

    public void setFinancialImpact(int financialImpact) {
        this.financialImpact = financialImpact;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(int riskLevel) {
        this.riskLevel = riskLevel;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public ArrayList<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(ArrayList<Solution> solutions) {
        this.solutions = solutions;
    }
}
