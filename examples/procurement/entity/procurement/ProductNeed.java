package procurement.entity.procurement;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductNeed {
    @JsonProperty("product_id")
    private int id;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
