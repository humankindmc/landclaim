package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import me.rileycalhoun.landclaim.towns.invites.InviteManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class JoinCommand extends SubCommand {

    private final InviteManager inviteManager;

    public JoinCommand(JavaPlugin plugin, TownsCache townsCache, LangFile language) {
        super(plugin);
        this.inviteManager = townsCache.getInviteManager();
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
    public void execute(Player player, String[] args) {
        if (townsCache.getTownByPlayer(player).isPresent()) {
            player.sendMessage(format(player, language.TOWN_NOT_ALLOWED));
            return;
        }

        Optional<Town> town = townsCache.getTownByName(args[0]);
        if (town.isEmpty()) {
            player.sendMessage(format(player, language.TOWN_DOES_NOT_EXIST));
            return;
        }

        if (!inviteManager.isInvited(town.get(), player)) {
            player.sendMessage(format(player, language.NOT_INVITED));
            return;
        }

        boolean result = inviteManager.joinTown(town.get(), player);
        if (!result) {
            player.sendMessage(format(player, language.SOMETHING_WENT_WRONG));
            return;
        }

        player.sendMessage(format(player, language.TOWN_JOINED));
        town.get().broadcastMessage(format(player, language.PLAYER_JOINED));
    }
}
