package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class KickCommand extends SubCommand {

    public KickCommand(LandClaim plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "Kick a citizen from your town.";
    }

    @Override
    public String getUsage() {
        return "kick <player name>";
    }

    @Override
    public int getRequiredArgs() {
        return 1;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.of("landclaim.town.kick");
    }

    @Override
    public boolean getRequireTown() {
        return true;
    }

    // TODO: Implement
    @Override
    public void execute(Citizen citizen, Player player, String[] args) {
        assert citizen.getTownUniqueId() != null;
        assert citizen.getCitizenRank() != null;

        if (citizen.getCitizenRank().getValue() < CitizenRank.OFFICER.getValue()) {
            player.sendMessage(format(player, language.TOWN_OFFICER_REQUIRED));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if(targetPlayer == null) {
            player.sendMessage(format(player, language.PLAYER_NOT_ONLINE));
            return;
        }

        Citizen targetCitizen = citizensCache.getCitizenByUUID(targetPlayer.getUniqueId());
        assert targetCitizen != null;
        if(targetCitizen.getTownUniqueId() == null
                || !targetCitizen.getTownUniqueId().equals(citizen.getTownUniqueId())) {
            player.sendMessage(format(player, language.PLAYER_NOT_IN_TOWN));
            return;
        }

        assert targetCitizen.getCitizenRank() != null;
        if(targetCitizen.getCitizenRank().getValue() >= CitizenRank.OFFICER.getValue()
            && citizen.getCitizenRank() != CitizenRank.MAYOR) {
            player.sendMessage(format(player, language.TOWN_MAYOR_REQUIRED));
            return;
        }

        targetCitizen.leaveTown();
        player.sendMessage(format(targetPlayer, language.KICK_SUCCESS));
        targetPlayer.sendMessage(format(player, language.PLAYER_KICKED));
    }
}
