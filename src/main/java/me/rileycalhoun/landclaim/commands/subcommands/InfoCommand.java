package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class InfoCommand extends SubCommand {

    public InfoCommand(JavaPlugin plugin, TownsCache townsCache, LangFile language) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Get information about your town";
    }

    @Override
    public String getUsage() {
        return "info";
    }

    @Override
    public int getRequiredArgs() {
        return 0;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.empty();
    }

    @Override
    public boolean getRequireTown() {
        return true;
    }

    @Override
    public void execute(Player player, String[] args) {
        Optional<Town> town = townsCache.getTownByPlayer(player);
        assert town.isPresent();
        player.sendMessage(format(player, language.TOWN_INFO));
    }
}
