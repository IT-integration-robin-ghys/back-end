package be.ucll.robinghys.integrationproject.terrarium.model;

import java.util.UUID;

import org.springframework.util.Assert;

public record TerrariumRequestId(UUID id) {
    public TerrariumRequestId {
        Assert.notNull(id, "TerrariumRequestId cannot be null.");
    }

    public TerrariumRequestId() {
        this(UUID.randomUUID());
    }
}
