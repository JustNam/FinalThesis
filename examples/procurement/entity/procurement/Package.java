package procurement.entity.procurement;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Package {
    @JsonProperty("package_id")
    private int id;

    private Discount timeline;
    @JsonProperty("joined_contractors")
    private ArrayList<Integer> joinedProviders;

    @JsonProperty("estimated_cost")
    private long estimatedCost;

    private ArrayList<ProductNeed> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Discount getTimeline() {
        return timeline;
    }

    public void setTimeline(Discount timeline) {
        this.timeline = timeline;
    }

    public ArrayList<Integer> getJoinedProviders() {
        return joinedProviders;
    }

    public void setJoinedProviders(ArrayList<Integer> joinedProviders) {
        this.joinedProviders = joinedProviders;
    }

    public long getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(long estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public ArrayList<ProductNeed> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductNeed> products) {
        this.products = products;
    }
}

