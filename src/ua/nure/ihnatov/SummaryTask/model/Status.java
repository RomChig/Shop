package ua.nure.ihnatov.SummaryTask.model;

public class Status extends Entity {
    private String name;

    public Status(Long id) {
        this.setId(id);
    }

    public Status() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id-'" + getId() + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
