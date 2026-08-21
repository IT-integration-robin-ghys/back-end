package be.ucll.robinghys.integrationproject.user.model;

import java.util.ArrayList;
import java.util.List;

import be.ucll.robinghys.integrationproject.terrarium.model.Terrarium;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "user", schema = "public")
public class User {

    @EmbeddedId
    private UserId id;

    @NotBlank(message = "Username cannot be empty.")
    private String username;

    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Terrarium> terrariums = new ArrayList<>();

    public User(String username, String email, String password) {
        this.id = new UserId();
        setUsername(username);
        setEmail(email);
        setPassword(password);
        this.terrariums = new ArrayList<>();
    }

    protected User() {
    }

    public UserId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
