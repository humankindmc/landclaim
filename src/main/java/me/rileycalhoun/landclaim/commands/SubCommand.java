package me.rileycalhoun.landclaim.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizensCache;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Optional;

public abstract class SubCommand {

    protected final LandClaim plugin;

    protected final LangFile language;
    protected final CitizensCache citizensCache;
    protected final TownsCache townsCache;

    public SubCommand(LandClaim plugin) {
        this.plugin = plugin;
        this.language = plugin.getLangFile();
        this.citizensCache = plugin.getCitizensCache();
        this.townsCache = plugin.getTownsCache();
    }

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getUsage();
    public abstract int getRequiredArgs();
    public abstract Optional<String> getPermission();
    public abstract boolean getRequireTown();

    public abstract void execute(Citizen citizen, Player player, String[] args);

    protected String format(OfflinePlayer player, String message) {
        return ChatColor.translateAlternateColorCodes('&',
                PlaceholderAPI.setPlaceholders(
                        player, message
                )
        );
    }

}
