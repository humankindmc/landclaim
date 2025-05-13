package me.rileycalhoun.landclaim;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.rileycalhoun.landclaim.towns.Town;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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
        if (params.equalsIgnoreCase("town_name")) {
            return landClaim.getTownManager()
                    .getTownByPlayer(player)
                    .map(Town::getName)
                    .orElse(null);
        }

        if (params.equalsIgnoreCase("town_description")) {
            return landClaim.getTownManager()
                    .getTownByPlayer(player)
                    .map(Town::getDescription)
                    .orElse(null);
        }

        if (params.equalsIgnoreCase("town_mayor")) {
            return landClaim.getTownManager()
                    .getTownByPlayer(player)
                    .map(t -> t.getMayor()
                            .getPlayer()
                            .getName())
                    .orElse(null);
        }

        if (params.equalsIgnoreCase("town_citizens_colored")) {
            Optional<Town> optionalTown = landClaim.getTownManager().getTownByPlayer(player);
            if (optionalTown.isEmpty()) return null;

            Town town = optionalTown.get();
            StringBuilder infoMessage = new StringBuilder();

            for (int i = 0; i < town.getCitizens().size(); i++) {
                TownCitizen citizen = town.getCitizens().get(i);

                OfflinePlayer citizenPlayer = citizen.getPlayer();
                if (citizenPlayer.isOnline()) {
                    infoMessage.append(ChatColor.GREEN)
                            .append(citizenPlayer.getName());
                } else {
                    infoMessage.append(ChatColor.RED)
                            .append(citizenPlayer.getName());
                }

                if (i < town.getCitizens().size() - 1) {
                    infoMessage.append(ChatColor.GRAY)
                            .append(",");
                }
            }

            return infoMessage.toString();
        }
        if (params.equalsIgnoreCase("town_citizens")) {
            Optional<Town> optionalTown = landClaim.getTownManager().getTownByPlayer(player);
            if (optionalTown.isEmpty()) return null;

            Town town = optionalTown.get();
            StringBuilder infoMessage = new StringBuilder();

            for (int i = 0; i < town.getCitizens().size(); i++) {
                TownCitizen citizen = town.getCitizens().get(i);

                OfflinePlayer citizenPlayer = citizen.getPlayer();
                infoMessage.append(citizenPlayer.getName());

                if (i < town.getCitizens().size() - 1) {
                    infoMessage.append(",");
                }
            }

            return infoMessage.toString();
        }

        return null;
    }
}
