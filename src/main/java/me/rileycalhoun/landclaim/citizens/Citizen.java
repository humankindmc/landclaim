package me.rileycalhoun.landclaim.citizens;

import me.rileycalhoun.landclaim.towns.Town;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
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

    public boolean isInTown() {
        return townUniqueId != null;
    }

    public void joinTown(Town town) {
        setTownUniqueId(town.getUniqueId());
        setCitizenRank(CitizenRank.OFFICER);
    }

    public void leaveTown() {
        setTownUniqueId(null);
        setCitizenRank(null);
    }

    public void promote() {
        CitizenRank newRank = switch (citizenRank) {
            case CITIZEN -> CitizenRank.OFFICER;
            case OFFICER -> CitizenRank.MAYOR;
            case null, default -> null;
        };

        if(newRank != null) {
            setCitizenRank(newRank);
        }
    }

    public void demote() {
        CitizenRank newRank = switch (citizenRank) {
            case CITIZEN, OFFICER -> CitizenRank.CITIZEN;
            case MAYOR -> CitizenRank.OFFICER;
            case null -> null;
        };

        if(newRank != null) {
            setCitizenRank(newRank);
        }
    }

    public void sendMessageIfOnline(String message) {
        if (player.isOnline()) {
            Player onlinePlayer = Bukkit.getPlayer(player.getUniqueId());
            if (onlinePlayer != null) {
                onlinePlayer.sendMessage(message);
            }
        }
    }

}
