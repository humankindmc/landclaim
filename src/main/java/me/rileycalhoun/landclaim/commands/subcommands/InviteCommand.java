package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import me.rileycalhoun.landclaim.towns.invites.InviteManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class InviteCommand extends SubCommand {

    private final InviteManager inviteManager;
    public InviteCommand(JavaPlugin plugin, TownsCache townsCache, LangFile language) {
        super(plugin);
        this.inviteManager = townsCache.getInviteManager();
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
    public void execute(Player player, String[] args) {
        Optional<Town> town = townsCache.getTownByPlayer(player);
        assert town.isPresent();

        if (args[0].equalsIgnoreCase(player.getName())) {
            player.sendMessage(format(player, language.CANNOT_INVITE_SELF));
            return;
        }

        Player invited = plugin.getServer().getPlayer(args[0]);
        if(invited == null) {
            player.sendMessage(format(player, language.PLAYER_NOT_ONLINE));
            return;
        }

        if (townsCache.getTownByPlayer(invited).isPresent()) {
            player.sendMessage(format(player, language.PLAYER_ALREADY_IN_TOWN));
            return;
        }

        inviteManager.invite(town.get(), invited);

        invited.sendMessage(format(player, language.TOWN_INVITE));
        town.get().broadcastMessage(format(player, language.PLAYER_INVITE));
    }

}
