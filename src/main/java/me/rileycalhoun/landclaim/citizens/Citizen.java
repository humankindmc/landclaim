package me.rileycalhoun.landclaim.citizens;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Citizen {

    @NotNull
    private final OfflinePlayer player;

    @Nullable
    private UUID townUniqueId;

    @Nullable
    private CitizenRank citizenRank;

    public Citizen(
            @NotNull OfflinePlayer player,
            @Nullable UUID townUniqueId,
            @Nullable CitizenRank citizenRank
    ) {
        this.player = player;
        this.townUniqueId = townUniqueId;
        this.citizenRank = citizenRank;
    }

    @NotNull
    public OfflinePlayer getPlayer() {
        return player;
    }

    @Nullable
    public UUID getTownUniqueId() {
        return townUniqueId;
    }

    public void setTownUniqueId(@Nullable UUID townUniqueId) {
        this.townUniqueId = townUniqueId;
    }

    @Nullable
    public CitizenRank getCitizenRank() {
        return citizenRank;
    }

    public void setCitizenRank(@Nullable CitizenRank citizenRank) {
        this.citizenRank = citizenRank;
    }
}
