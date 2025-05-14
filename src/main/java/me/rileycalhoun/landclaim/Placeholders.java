package me.rileycalhoun.landclaim;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.towns.Town;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Placeholders extends PlaceholderExpansion {

    private final LandClaim landClaim;

    public Placeholders(final LandClaim landClaim) {
        this.landClaim = landClaim;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "landclaim";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", landClaim.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return landClaim.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        return getPlaceholders(player, params);
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return getPlaceholders(player, params);
    }

    public @Nullable String getPlaceholders(OfflinePlayer player, @NotNull String params) {
        Citizen citizen = landClaim.getCitizensCache().getCitizenByUUID(player.getUniqueId());
        assert citizen != null;

        if (params.equalsIgnoreCase("town_name")) {
            Town town = landClaim.getTownsCache().getTownByUUID(citizen.getTownUniqueId());
            return town == null ? null : town.getName();
        }

        if (params.equalsIgnoreCase("town_motd")) {
            Town town = landClaim.getTownsCache().getTownByUUID(citizen.getTownUniqueId());
            return town == null ? null : town.getMotd();
        }

        // TODO: Implement this
        if (params.equalsIgnoreCase("town_mayor")) {
            return null;
        }

        if (params.equalsIgnoreCase("town_citizens_colored")) {
            Town town = landClaim.getTownsCache().getTownByUUID(citizen.getTownUniqueId());
            if (town == null) return null;

            List<Citizen> citizens = landClaim.getCitizensCache()
                    .getCitizensInTown(town);
            return formatCitizens(citizens, true);
        }

        if (params.equalsIgnoreCase("town_citizens")) {
            Town town = landClaim.getTownsCache().getTownByUUID(citizen.getTownUniqueId());
            if (town == null) return null;

            List<Citizen> citizens = landClaim.getCitizensCache()
                    .getCitizensInTown(town);
            return formatCitizens(citizens, false);
        }

        return null;
    }

    private String formatCitizens(List<Citizen> citizens, boolean color) {
        StringBuilder infoMessage = new StringBuilder();

        for (int i = 0; i < citizens.size(); i++) {
            Citizen citizen = citizens.get(i);
            OfflinePlayer citizenPlayer = citizen.getPlayer();

            if (color) {
                if (citizenPlayer.isOnline()) {
                    infoMessage.append(ChatColor.GREEN);
                } else {
                    infoMessage.append(ChatColor.RED);
                }
            }

            infoMessage.append(citizenPlayer.getName());

            if (color) {
                infoMessage.append(ChatColor.GRAY);
            }

            if (i < citizens.size() - 1) {
                infoMessage.append(",");
            }
        }

        return infoMessage.toString();
    }

}
