package utils;

/**
 * Created by yadi on 22/06/16.
 */
public class Deal {
    private String deal, price;

    public Deal() {
    }

    public Deal(String deal, String price) {
        this.deal = deal;
        this.price = price;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}