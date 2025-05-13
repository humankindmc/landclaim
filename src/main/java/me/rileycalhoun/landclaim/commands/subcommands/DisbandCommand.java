package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Optional;

public class DisbandCommand extends SubCommand {

    public final HashMap<OfflinePlayer, Town> disbandConfirmation;

    public DisbandCommand(JavaPlugin plugin, TownsCache townsCache, LangFile language) {
        super(plugin);
        disbandConfirmation = new HashMap<>();
    }

    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getDescription() {
        return "Disband your town";
    }

    @Override
    public String getUsage() {
        return "disband [town name]";
    }

    @Override
    public int getRequiredArgs() {
        return 0;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.of("landclaim.town.disband.self");
    }

    @Override
    public boolean getRequireTown() {
        return true;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            Optional<Town> town = townsCache.getTownByPlayer(player);
            assert town.isPresent();

            // TODO: Make the player confirm disbanding via '/town disband confirm'
            if (!town.get().isMayor(player)) {
                player.sendMessage(format(player, language.TOWN_MAYOR_REQUIRED));
                return;
            }

            disbandConfirmation.put(player, town.get());
            player.sendMessage(format(player, language.TOWN_DISBAND_CONFIRMATION));
        } else {
            if (args[0].equalsIgnoreCase("confirm")) {
                if (!disbandConfirmation.containsKey(player)) {
                    player.sendMessage(format(player, language.TOWN_ILLEGAL_CONFIRMATION));
                    return;
                }

                Town town = disbandConfirmation.get(player);
                TownDisbandEvent event = new TownDisbandEvent(town);
                Bukkit.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled()) {
                    player.sendMessage(format(player, language.SOMETHING_WENT_WRONG));
                } else {
                    disbandConfirmation.remove(player);
                    plugin.getServer().broadcastMessage(format(player, language.PLAYER_DISBAND_TOWN));
                    townsCache.disbandTown(town);
                }
            } else {
                if (!player.hasPermission("landclaim.town.disband.other")) {
                    player.sendMessage(format(player, language.NO_PERMISSION));
                    return;
                }

                Optional<Town> town = townsCache.getTownByName(args[0]);

                if (town.isEmpty()) {
                    player.sendMessage(format(player, language.TOWN_DOES_NOT_EXIST));
                    return;
                }

                disbandConfirmation.put(player, town.get());
                player.sendMessage(format(player, language.TOWN_DISBAND_CONFIRMATION));
            }
        }
    }
}
