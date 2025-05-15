package me.rileycalhoun.landclaim.towns;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Town {

    @NotNull
    private final UUID uniqueId;

    @NotNull
    private String name;

    @Nullable
    private String motd;

    public Town(@NotNull UUID uniqueId, @NotNull String name) {
        this(uniqueId, name, null);
    }

    public Town(@NotNull UUID uniqueId, @NotNull String name, @Nullable String motd) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.motd = motd;
    }

    @NotNull
    public UUID getUniqueId() {
        return uniqueId;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Nullable
    public String getMotd() {
        return motd;
    }

    public void setMotd(@Nullable String motd) {
        this.motd = motd;
    }

}
