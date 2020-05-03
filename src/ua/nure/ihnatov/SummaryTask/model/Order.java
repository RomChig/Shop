package ua.nure.ihnatov.SummaryTask.model;

import java.sql.Date;

public class Order extends Entity {
    private Double bill;
    private User user;
    private Status status;
    private Date dateCreated;

    public Order(Double bill, User user, Status status) {
        this.bill = bill;
        this.user = user;
        this.status = status;
    }

    public Order() {
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "OrderDAO{" +
                "id=" + getId() +
                ", bill=" + bill +
                ", user=" + user +
                ", status=" + status +
                ", date_create=" + dateCreated +
                '}';
    }
}
