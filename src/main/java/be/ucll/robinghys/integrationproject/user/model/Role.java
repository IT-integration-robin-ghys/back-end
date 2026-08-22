package be.ucll.robinghys.integrationproject.user.model;

public enum Role {
    user(1),
    admin(2);

    private final int level;

    Role(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean hasPermission(Role requiredRole) {
        return this.level >= requiredRole.level;
    }
}
