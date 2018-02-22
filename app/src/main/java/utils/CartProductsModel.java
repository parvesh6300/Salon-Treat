package utils;

/**
 * Created by Sagar on 19/07/17.
 */

public class CartProductsModel {

    private String product_name;
    private String product_seller;
    private int product_qty;
    private int product_qty_in_stock;
    private String product_type;
    private float product_cost;
    private int thumbnail;

    public CartProductsModel()
    {

    }

    public CartProductsModel(String product_name, String product_seller, int product_qty,
                             int product_qty_in_stock, String product_type, float product_cost, int thumbnail) {
        this.product_name = product_name;
        this.product_seller = product_seller;
        this.product_qty = product_qty;
        this.product_qty_in_stock = product_qty_in_stock;
        this.product_type = product_type;
        this.product_cost = product_cost;
        this.thumbnail = thumbnail;
    }


    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_seller() {
        return product_seller;
    }

    public void setProduct_seller(String product_seller) {
        this.product_seller = product_seller;
    }

    public int getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(int product_qty) {
        this.product_qty = product_qty;
    }

    public int getProduct_qty_in_stock() {
        return product_qty_in_stock;
    }

    public void setProduct_qty_in_stock(int product_qty_in_stock) {
        this.product_qty_in_stock = product_qty_in_stock;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public float getProduct_cost() {
        return product_cost;
    }

    public void setProduct_cost(float product_cost) {
        this.product_cost = product_cost;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
