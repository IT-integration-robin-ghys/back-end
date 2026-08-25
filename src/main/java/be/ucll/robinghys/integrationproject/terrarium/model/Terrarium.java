package be.ucll.robinghys.integrationproject.terrarium.model;

import java.util.UUID;

import be.ucll.robinghys.integrationproject.user.model.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "terrarium", schema = "public")
public class Terrarium {

    @EmbeddedId
    private TerrariumId id;

    @NotBlank(message = "Terrarium name cannot be empty.")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String apiKey;

    public Terrarium(String name, UUID id) {
        this.id = new TerrariumId(id);
        setName(name);
        this.apiKey = null;
    }

    protected Terrarium() {
    }

    public TerrariumId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
