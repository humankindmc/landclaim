package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class PromoteCommand extends SubCommand {

    public PromoteCommand(JavaPlugin plugin, TownsCache townsCache, LangFile language) {
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
    public void execute(Player player, String[] args) {
        assert townsCache.getTownByPlayer(player).isPresent();

        Town town = townsCache.getTownByPlayer(player).get();
        Player otherPlayer = Bukkit.getPlayer(args[0]);
        if (otherPlayer == null) {
            player.sendMessage(language.PLAYER_NOT_ONLINE);
            return;
        }

        if (!town.isCitizen(otherPlayer)) {
            player.sendMessage(language.PLAYER_NOT_IN_TOWN);
            return;
        }

        assert town.getCitizen(otherPlayer).isPresent();
        TownCitizen citizen = town.getCitizen(otherPlayer).get();

        if (citizen.getRank() == TownRank.CITIZEN) {

        } else if (citizen.getRank() == TownRank.OFFICER) {

        } else {

        }
    }

}
