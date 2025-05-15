package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.claims.ClaimedArea;
import me.rileycalhoun.landclaim.commands.SubCommand;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.Optional;

public class UnclaimCommand extends SubCommand {

    public UnclaimCommand(LandClaim plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "unclaim";
    }

    @Override
    public String getDescription() {
        return "Unclaim a chunk.";
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
    public void execute(Citizen citizen, Player player, String[] args) {
        assert citizen.getTownUniqueId() != null;
        assert citizen.getCitizenRank() != null;

        if (citizen.getCitizenRank().getValue() < CitizenRank.OFFICER.getValue()) {
            player.sendMessage(format(player, language.TOWN_OFFICER_REQUIRED));
            return;
        }

        Chunk chunk = player.getLocation().getChunk();
        if (!claimsCache.isChunkClaimed(chunk)) {
            player.sendMessage(format(player, language.CHUNK_NOT_CLAIMED));
            return;
        }

        ClaimedArea claimedArea = claimsCache.getClaimByUUID(citizen.getTownUniqueId());
        if(!claimedArea.hasChunk(chunk)) {
            player.sendMessage(format(player, language.CHUNK_NOT_CLAIMED));
            return;
        }

        claimedArea.unclaim(chunk);
        player.sendMessage(format(player, language.CHUNK_UNCLAIMED));
    }

}
