package be.ucll.robinghys.integrationproject.terrarium.model;

import java.util.UUID;

import be.ucll.robinghys.integrationproject.user.model.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "terrarium_request", schema = "public")
public class TerrariumRequest {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id"))
    private TerrariumRequestId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private UserId userId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "terrarium_id"))
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
