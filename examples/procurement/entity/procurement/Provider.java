package procurement.entity.procurement;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Provider {
    @JsonProperty("contractor_id")
    private int id;
    private String description;
    private float relationship;
    private float quality;
    private ArrayList<ProductSell> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRelationship() {
        return relationship;
    }

    public void setRelationship(float relationship) {
        this.relationship = relationship;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public ArrayList<ProductSell> getProducts() {
        return products;
    }

    public ProductSell getProductById(int id) {
        for (ProductSell p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void setProducts(ArrayList<ProductSell> products) {
        this.products = products;
    }
}
