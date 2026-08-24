package be.ucll.robinghys.integrationproject.terrarium.model;

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

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id"))
    private TerrariumRequestId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private UserId userId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "terrarium_id"))
    private TerrariumId terrariumId;

    private Status status;

    public TerrariumRequest(UserId userId, TerrariumId terrariumId) {
        this.id = new TerrariumRequestId();
        setUserId(userId);
        setTerrariumId(terrariumId);
        this.status = Status.PENDING;
    }

    protected TerrariumRequest() {
    }

    public TerrariumRequestId getId() {
        return id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
