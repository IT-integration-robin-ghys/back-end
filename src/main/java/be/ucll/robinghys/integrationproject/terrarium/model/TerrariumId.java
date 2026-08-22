package be.ucll.robinghys.integrationproject.terrarium.model;

import java.util.UUID;

import org.springframework.util.Assert;

public record TerrariumId(UUID id) {
    public TerrariumId {
        Assert.notNull(id, "TerrariumId cannot be null.");
    }

    public TerrariumId() {
        this(UUID.randomUUID());
    }
}
