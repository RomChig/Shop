package ua.nure.ihnatov.SummaryTask.model;

import java.util.Hashtable;
import java.util.Map;

public class Cart extends Entity {
    private Map<Product, Long> mapProduct;
    private User user;
    private Product product;

    public Cart(User user, Product product) {
        mapProduct = new Hashtable<>();
        this.user = user;
        this.product = product;
    }

    public Cart() {
    }

    public Product getProduct() {
        return product;
    }

    public Map<Product, Long> getMapProduct() {
        return mapProduct;
    }

    public void setMapProduct(Map<Product, Long> mapProduct) {
        this.mapProduct = mapProduct;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "id=" + getId() +
                "user=" + user +
                ", product=" + product +
                '}';
    }
}
