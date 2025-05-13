package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class LeaveCommand extends SubCommand {

    public LeaveCommand(JavaPlugin plugin, TownsCache townsCache, LangFile language) {
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
    public void execute(Player player, String[] args) {
        Optional<Town> town = townsCache.getTownByPlayer(player);
        assert town.isPresent();

        assert town.get().getCitizen(player).isPresent();
        TownRank rank = town.get()
                .getCitizen(player)
                .get()
                .getRank();
        if (rank == TownRank.MAYOR) {
            player.sendMessage(format(player, language.MUST_DISBAND));
            return;
        }

        boolean result = town.get().removePlayer(player);
        if (!result) {
            player.sendMessage(format(player, language.SOMETHING_WENT_WRONG));
            return;
        }

        player.sendMessage(format(player, language.TOWN_LEFT));
        town.get().broadcastMessage(format(player, language.PLAYER_LEFT));
    }
}
