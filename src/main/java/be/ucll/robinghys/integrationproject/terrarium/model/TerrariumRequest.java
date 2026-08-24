package be.ucll.robinghys.integrationproject.terrarium.model;

import java.util.UUID;

import be.ucll.robinghys.integrationproject.user.model.UserId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "terrarium_request", schema = "public")
public class TerrariumRequest {

    @EmbeddedId
    private TerrariumRequestId id;

    private UserId userId;

    private TerrariumId terrariumId;

    public TerrariumRequest(UUID id, UserId userId, TerrariumId terrariumId) {
        this.id = new TerrariumRequestId(id);
        setUserId(userId);
        setTerrariumId(terrariumId);
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public TerrariumId getTerrariumId() {
        return terrariumId;
    }

    public void setTerrariumId(TerrariumId terrariumId) {
        this.terrariumId = terrariumId;
    }
}
