package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DemoteCommand extends SubCommand {

    public DemoteCommand(LandClaim plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "demote";
    }

    @Override
    public String getDescription() {
        return "Demote a citizen";
    }

    @Override
    public String getUsage() {
        return "demote <player name>";
    }

    @Override
    public int getRequiredArgs() {
        return 1;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.of("landclaim.town.demote");
    }

    @Override
    public boolean getRequireTown() {
        return true;
    }

    @Override
    public void execute(Citizen citizen, Player player, String[] args) {
        assert citizen.getTownUniqueId() != null;
        assert citizen.getCitizenRank() != null;

        if (citizen.getCitizenRank().getValue() < CitizenRank.MAYOR.getValue()) {
            player.sendMessage(format(player, language.TOWN_MAYOR_REQUIRED));
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(format(player, language.PLAYER_NOT_ONLINE));
            return;
        }

        if (targetPlayer.getName().equalsIgnoreCase(player.getName())) {
            player.sendMessage(format(player, language.CANNOT_DEMOTE_SELF));
            return;
        }

        Citizen targetCitizen = citizensCache.getCitizenByUUID(targetPlayer.getUniqueId());
        assert targetCitizen != null;

        if (targetCitizen.getTownUniqueId() != citizen.getTownUniqueId()) {
            player.sendMessage(format(player, language.PLAYER_NOT_IN_TOWN));
            return;
        }

        if (args.length >= 2 && args[1].equalsIgnoreCase("confirm")) {
            if (targetCitizen.getCitizenRank() == CitizenRank.CITIZEN) {
                player.sendMessage(format(player, language.CANNOT_DEMOTE_PAST_CITIZEN));
                return;
            }

            targetCitizen.demote();
            targetPlayer.sendMessage(format(player, language.PLAYER_DEMOTED));
            player.sendMessage(format(player, language.DEMOTE_SUCCESS));
        } else {
            if (targetCitizen.getCitizenRank() == CitizenRank.CITIZEN) {
                player.sendMessage(format(player, language.CANNOT_DEMOTE_PAST_CITIZEN));
            } else if (targetCitizen.getCitizenRank() == CitizenRank.OFFICER) {
                player.sendMessage(format(targetPlayer, language.TOWN_DEMOTE_CONFIRMATION));
            }
        }
    }

}
