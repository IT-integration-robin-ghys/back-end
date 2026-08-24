package be.ucll.robinghys.integrationproject.terrarium.model;

import java.util.ArrayList;
import java.util.List;

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

    private List<Double> temperatures;

    private List<Double> humidities;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Terrarium(String name) {
        this.id = new TerrariumId();
        setName(name);
        this.humidities = new ArrayList<>();
        this.temperatures = new ArrayList<>();
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

    public List<Double> getTemperatures() {
        return temperatures;
    }

    public void addTemperature(Double temperature) {
        this.temperatures.add(temperature);
    }

    public List<Double> getHumidities() {
        return humidities;
    }

    public void addHumidity(Double humidity) {
        this.humidities.add(humidity);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
