package be.ucll.robinghys.integrationproject.user.model;

import java.util.UUID;

import org.springframework.util.Assert;

public record UserId(UUID id) {
    public UserId {
        Assert.notNull(id, "UserId cannot be null.");
    }

    public UserId() {
        this(UUID.randomUUID());
    }
}
