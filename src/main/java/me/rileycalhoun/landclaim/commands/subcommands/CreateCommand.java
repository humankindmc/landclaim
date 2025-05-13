package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class CreateCommand extends SubCommand {

    public CreateCommand(JavaPlugin plugin, TownsCache townsCache, LangFile language) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a town";
    }

    @Override
    public String getUsage() {
        return "create <town name>";
    }

    @Override
    public int getRequiredArgs() {
        return 1;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.of("landclaim.town.create");
    }

    @Override
    public boolean getRequireTown() {
        return false;
    }

    @Override
    public void execute(Player player, String[] args) {
        String townName = args[0];
        if (townName.equalsIgnoreCase("confirm")) {
            player.sendMessage(format(player, language.TOWN_ILLEGAL_NAME));
            return;
        }

        if(townsCache.getTownByName(townName).isPresent()) {
            player.sendMessage(format(player, language.TOWN_ALREADY_EXISTS));
            return;
        }

        Optional<Town> optionalTown = townsCache.createTown(townName, player);
        if (optionalTown.isEmpty()) {
            player.sendMessage(format(player, language.SOMETHING_WENT_WRONG));
            return;
        }

        player.sendMessage(format(player, language.TOWN_CREATED));
        plugin.getServer().broadcastMessage(format(player, language.PLAYER_CREATE_TOWN));
    }
}
