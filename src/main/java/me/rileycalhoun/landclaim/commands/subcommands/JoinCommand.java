package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.invites.InviteCache;
import org.bukkit.entity.Player;

import java.util.Optional;

public class JoinCommand extends SubCommand {

    private final InviteCache inviteCache;

    public JoinCommand(LandClaim plugin) {
        super(plugin);
        this.inviteCache = plugin.getInviteCache();
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join a town";
    }

    @Override
    public String getUsage() {
        return "join <town>";
    }

    @Override
    public int getRequiredArgs() {
        return 1;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.of("landclaim.town.join");
    }

    @Override
    public boolean getRequireTown() {
        return false;
    }

    @Override
    public void execute(Citizen citizen, Player player, String[] args) {
        if (townsCache.getTownByUUID(citizen.getTownUniqueId()) != null) {
            player.sendMessage(format(player, language.TOWN_NOT_ALLOWED));
            return;
        }

        Town town = townsCache.getTownByName(args[0]);
        if (town == null) {
            player.sendMessage(format(player, language.TOWN_DOES_NOT_EXIST));
            return;
        }

        if (!inviteCache.isInvited(town, player)) {
            player.sendMessage(format(player, language.NOT_INVITED));
            return;
        }

        citizen.joinTown(town);
        player.sendMessage(format(player, language.TOWN_JOINED));
        citizensCache.getCitizensInTown(town).forEach(c ->
                c.sendMessageIfOnline(format(player, language.PLAYER_JOINED)));
    }
}
