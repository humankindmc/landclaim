package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.towns.Town;
import org.bukkit.entity.Player;

import java.util.Optional;

public class InviteCommand extends SubCommand {

    public InviteCommand(LandClaim plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Invite a player to a town";
    }

    @Override
    public String getUsage() {
        return "invite <player>";
    }

    @Override
    public int getRequiredArgs() {
        return 1;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.of("landclaim.town.invite");
    }

    @Override
    public boolean getRequireTown() {
        return true;
    }

    @Override
    public void execute(Citizen citizen, Player player, String[] args) {
        assert citizen.getTownUniqueId() != null;
        assert citizen.getCitizenRank() != null;

        if (citizen.getCitizenRank().getValue() < CitizenRank.OFFICER.getValue()) {
            player.sendMessage(format(player, language.TOWN_OFFICER_REQUIRED));
            return;
        }

        if (args[0].equalsIgnoreCase(player.getName())) {
            player.sendMessage(format(player, language.CANNOT_INVITE_SELF));
            return;
        }

        Player invited = plugin.getServer().getPlayer(args[0]);
        if(invited == null) {
            player.sendMessage(format(player, language.PLAYER_NOT_ONLINE));
            return;
        }

        Citizen invitedCitizen = citizensCache.getCitizenByUUID(invited.getUniqueId());
        assert invitedCitizen != null;

        if (townsCache.getTownByUUID(invitedCitizen.getTownUniqueId()) != null) {
            player.sendMessage(format(player, language.PLAYER_ALREADY_IN_TOWN));
            return;
        }

        Town town = townsCache.getTownByUUID(citizen.getTownUniqueId());
        inviteCache.invite(town, invited);

        invited.sendMessage(format(player, language.TOWN_INVITE));
        player.sendMessage(format(invited, language.PLAYER_INVITE));
    }

}
