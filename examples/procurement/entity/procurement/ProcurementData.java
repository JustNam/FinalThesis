package procurement.entity.procurement;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class ProcurementData {
    @JsonProperty("project_id")
    private int id;

    @JsonProperty("inflation_rate")
    private float inflationRate;

    @JsonProperty("start_date")
    private Date startDate;

    private ArrayList<Package> packages;

    @JsonProperty("contractors")
    private ArrayList<Provider> providers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(float inflationRate) {
        this.inflationRate = inflationRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ArrayList<Package> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<Package> packages) {
        this.packages = packages;
    }

    public ArrayList<Provider> getProviders() {
        return providers;
    }

    public void setProviders(ArrayList<Provider> providers) {
        this.providers = providers;
    }
}
