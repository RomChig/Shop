package ua.nure.ihnatov.SummaryTask.model;

import java.sql.Date;
import java.util.Objects;

public class Product extends Entity {
    private String name;
    private String type;
    private String capacity;
    private String color;
    private Double price;
    private String currency;
    private Date dateAdded;
    private String imageName;

    public Product(Long id) {
        this.setId(id);
    }

    public Product() {
    }

    public Product(String name, String type, String capacity, String color, Double price, String currency, String imageName) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.color = color;
        this.price = price;
        this.currency = currency;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getName().equals(product.getName()) &&
                getType().equals(product.getType()) &&
                getCapacity().equals(product.getCapacity()) &&
                getColor().equals(product.getColor()) &&
                getPrice().equals(product.getPrice()) &&
                getCurrency().equals(product.getCurrency()) &&
                getDateAdded().equals(product.getDateAdded()) &&
                getImageName().equals(product.getImageName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getCapacity(), getColor(), getPrice(), getCurrency(), getDateAdded(), getImageName());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", capacity='" + capacity + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", dateAdded=" + dateAdded + '\'' +
                ", image=" + imageName +
                '}';
    }
}
