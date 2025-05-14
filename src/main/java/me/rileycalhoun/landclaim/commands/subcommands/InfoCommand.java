package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.Optional;

public class InfoCommand extends SubCommand {

    public InfoCommand(LandClaim plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Get information about your town";
    }

    @Override
    public String getUsage() {
        return "info";
    }

    @Override
    public int getRequiredArgs() {
        return 0;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.empty();
    }

    @Override
    public boolean getRequireTown() {
        return true;
    }

    @Override
    public void execute(Citizen citizen, Player player, String[] args) {
        player.sendMessage(format(player, language.TOWN_INFO));
    }
}
