package procurement.entity.procurement;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class ProductSell {
    @JsonProperty("product_id")
    private int id;
    @JsonProperty("sell_price")
    private long sellPrice;
    @JsonProperty("buy_price")
    private long buyPrice;

    private ArrayList<Discount> discounts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(ArrayList<Discount> discounts) {
        this.discounts = discounts;
    }

    public long getPriceAtDate(Date date) {
        long price = sellPrice;
        for (Discount discount : discounts) {
            if (date.after(discount.getFrom()) && date.before(discount.getTo())) {
                price = (long) ( (1 - discount.getRate()) * price);
                break;
            }
        }
        return price;
    }
}
