package ua.nure.ihnatov.SummaryTask.model;

import java.io.Serializable;

public class User extends Entity implements Serializable {
    private static final long serialVersionUID = -6889036256149495388L;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private Boolean isBlocked;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(Long id) {
        this.setId(id);
    }

    public User(String login, String password, String firstName, String lastName, Role role) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "User [id =" + getId()
                + ", login=" + login
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", role=" + role
                + ", isBlocked=" + isBlocked + "]";
    }
}
