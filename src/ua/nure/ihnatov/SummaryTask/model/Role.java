package ua.nure.ihnatov.SummaryTask.model;

public class Role extends Entity {
    private String name;

    public Role(Long id) {
        this.setId(id);
    }

    public Role() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
