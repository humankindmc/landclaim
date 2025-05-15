package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PromoteCommand extends SubCommand {

    public PromoteCommand(LandClaim plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "promote";
    }

    @Override
    public String getDescription() {
        return "Promote someone to officer or mayor";
    }

    @Override
    public String getUsage() {
        return "promote <citizen>";
    }

    @Override
    public int getRequiredArgs() {
        return 0;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.of("landclaim.town.promote");
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
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(format(player, language.PLAYER_NOT_ONLINE));
            return;
        }

        if (targetPlayer.getName().equalsIgnoreCase(player.getName())) {
            player.sendMessage(format(player, language.CANNOT_PROMOTE_SELF));
            return;
        }

        Citizen targetCitizen = citizensCache.getCitizenByUUID(targetPlayer.getUniqueId());
        assert targetCitizen != null;

        if (targetCitizen.getTownUniqueId() != citizen.getTownUniqueId()) {
            player.sendMessage(format(player, language.PLAYER_NOT_IN_TOWN));
            return;
        }


        if (args.length >= 2 && args[1].equalsIgnoreCase("confirm")) {
            targetCitizen.promote();
            targetPlayer.sendMessage(format(targetPlayer, language.PLAYER_PROMOTED));
            player.sendMessage(format(targetPlayer, language.PROMOTE_SUCCESS));
        } else {
            if (targetCitizen.getCitizenRank() == CitizenRank.CITIZEN) {
                player.sendMessage(format(targetPlayer, language.TOWN_PROMOTE_CONFIRMATION_OFFICER));
            } else if (targetCitizen.getCitizenRank() == CitizenRank.OFFICER) {
                player.sendMessage(format(targetPlayer, language.TOWN_PROMOTE_CONFIRMATION_MAYOR));
            }
        }
    }

}
