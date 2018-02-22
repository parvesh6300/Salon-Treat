package utils;

/**
 * Created by Sagar on 18/07/17.
 */

public class Album {


    private String name;
    private String product_type;
    private float product_cost;
    private int thumbnail;



    public Album() {

    }

    public float getProduct_cost() {
        return product_cost;
    }

    public void setProduct_cost(float product_cost) {
        this.product_cost = product_cost;
    }

    public Album(String name, float product_cost, int thumbnail, String product_type) {
        this.name = name;
        this.product_cost = product_cost;
        this.thumbnail = thumbnail;
        this.product_type = product_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }


    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

}
