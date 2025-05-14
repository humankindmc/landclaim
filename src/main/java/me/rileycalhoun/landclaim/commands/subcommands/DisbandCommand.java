package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.towns.Town;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;

public class DisbandCommand extends SubCommand {

    public final HashMap<OfflinePlayer, Town> disbandConfirmation;

    public DisbandCommand(LandClaim plugin) {
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
        return false;
    }

    @Override
    public void execute(Citizen citizen, Player player, String[] args) {
        if (args.length == 0) {
            Town town = townsCache.getTownByUUID(citizen.getTownUniqueId());
            if (town == null) {
                player.sendMessage(format(player, language.PLAYER_NOT_IN_TOWN));
                return;
            }

            // TODO: Make the player confirm disbanding via '/town disband confirm'
            if (citizen.getCitizenRank() != CitizenRank.MAYOR) {
                player.sendMessage(format(player, language.TOWN_MAYOR_REQUIRED));
                return;
            }

            disbandConfirmation.put(player, town);
            player.sendMessage(format(player, language.TOWN_DISBAND_CONFIRMATION));
        } else {
            if (args[0].equalsIgnoreCase("confirm")) {
                if (!disbandConfirmation.containsKey(player)) {
                    player.sendMessage(format(player, language.TOWN_ILLEGAL_CONFIRMATION));
                    return;
                }

                Town town = disbandConfirmation.get(player);
                disbandConfirmation.remove(player);
                plugin.getServer().broadcastMessage(format(player, language.PLAYER_DISBAND_TOWN));
                townsCache.disbandTown(town);
            } else {
                if (!player.hasPermission("landclaim.town.disband.other")) {
                    player.sendMessage(format(player, language.NO_PERMISSION));
                    return;
                }

                Town town = townsCache.getTownByName(args[0]);

                if (town == null) {
                    player.sendMessage(format(player, language.TOWN_DOES_NOT_EXIST));
                    return;
                }

                disbandConfirmation.put(player, town);
                player.sendMessage(format(player, language.TOWN_DISBAND_CONFIRMATION));
            }
        }
    }
}
