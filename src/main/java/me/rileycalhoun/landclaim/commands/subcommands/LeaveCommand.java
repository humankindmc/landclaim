package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class LeaveCommand extends SubCommand {

    public LeaveCommand(LandClaim plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leave a town";
    }

    @Override
    public String getUsage() {
        return "leave";
    }

    @Override
    public int getRequiredArgs() {
        return 0;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.of("landclaim.town.leave");
    }

    @Override
    public boolean getRequireTown() {
        return true;
    }

    @Override
    public void execute(Citizen citizen, Player player, String[] args) {
        Town town = townsCache.getTownByUUID(citizen.getTownUniqueId());
        assert town != null;

        if (citizen.getCitizenRank() == CitizenRank.MAYOR) {
            player.sendMessage(format(player, language.MUST_DISBAND));
            return;
        }

        citizen.leaveTown();
        player.sendMessage(format(player, language.TOWN_LEFT));
        citizensCache.getCitizensInTown(town).forEach(c ->
                c.sendMessageIfOnline(format(player, language.PLAYER_LEFT)));
    }
}
