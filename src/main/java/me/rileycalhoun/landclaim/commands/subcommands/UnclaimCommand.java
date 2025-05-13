package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import me.rileycalhoun.landclaim.claims.ClaimsCache;
import me.rileycalhoun.landclaim.claims.ClaimArea;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class UnclaimCommand extends SubCommand {

    private final ClaimsCache claimsCache;

    public UnclaimCommand(JavaPlugin plugin, TownsCache townsCache, LangFile language) {
        super(plugin);
        this.claimsCache = townsCache.getClaimManager();
    }

    @Override
    public String getName() {
        return "unclaim";
    }

    @Override
    public String getDescription() {
        return "Unclaim a chunk of land";
    }

    @Override
    public String getUsage() {
        return "unclaim";
    }

    @Override
    public int getRequiredArgs() {
        return 0;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.of("landclaim.town.unclaim");
    }

    @Override
    public boolean getRequireTown() {
        return true;
    }

    @Override
    public void execute(Player player, String[] args) {
        Optional<Town> town = townsCache.getTownByPlayer(player);
        assert town.isPresent();

        if (!town.get().isMayor(player) && !town.get().isOfficer(player)) {
            player.sendMessage(format(player, language.TOWN_OFFICER_REQUIRED));
            return;
        }

        Chunk chunk = player.getLocation().getChunk();
        Optional<Town> claimer = claimsCache.getChunkOwner(chunk);
        if (claimer.isEmpty() || !claimer.equals(town)) {
            player.sendMessage(format(player, language.CHUNK_NOT_CLAIMED));
            return;
        }

        TownUnclaimEvent event = new TownUnclaimEvent(town.get(), chunk);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            player.sendMessage(format(player, language.SOMETHING_WENT_WRONG));
            return;
        }

        ClaimArea mainClaim = town.get().getMainClaim();
        if (mainClaim.unclaim(chunk).isEmpty()) {
            player.sendMessage(format(player, language.CHUNK_UNCLAIMED));
        } else {
            player.sendMessage(format(player, language.SOMETHING_WENT_WRONG));
        }
    }
}
