package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.towns.Town;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CreateCommand extends SubCommand {

    public CreateCommand(LandClaim plugin) {
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
    public void execute(Citizen citizen, Player player, String[] args) {
        String townName = args[0];
        if (townName.equalsIgnoreCase("confirm")) {
            player.sendMessage(format(player, language.TOWN_ILLEGAL_NAME));
            return;
        }

        if(townsCache.getTownByName(townName) != null) {
            player.sendMessage(format(player, language.TOWN_ALREADY_EXISTS));
            return;
        }

        Town town = townsCache.createTown(townName);
        if (town == null) {
            player.sendMessage(format(player, language.SOMETHING_WENT_WRONG));
            return;
        }

        citizen.setTownUniqueId(town.getUniqueId());
        citizen.setCitizenRank(CitizenRank.MAYOR);

        player.sendMessage(format(player, language.TOWN_CREATED));
        plugin.getServer().broadcastMessage(format(player, language.PLAYER_CREATE_TOWN));
    }
}
