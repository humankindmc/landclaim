package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.claims.ClaimedArea;
import me.rileycalhoun.landclaim.claims.ClaimsCache;
import me.rileycalhoun.landclaim.commands.SubCommand;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.Optional;

public class ClaimCommand extends SubCommand {

    public ClaimCommand(LandClaim plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getDescription() {
        return "Claim a chunk of land!";
    }

    @Override
    public String getUsage() {
        return "claim";
    }

    @Override
    public int getRequiredArgs() {
        return 0;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.of("landclaim.town.claim");
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
        if (claimsCache.isChunkClaimed(chunk)) {
            player.sendMessage(format(player, language.CHUNK_ALREADY_CLAIMED));
            return;
        }

        ClaimedArea claimedArea = claimsCache.getClaimByUUID(citizen.getTownUniqueId());
        if (!claimedArea.isChunkTouchingClaim(chunk)) {
            player.sendMessage(format(player, language.CHUNK_NOT_BORDERING));
            return;
        }

        claimedArea.claim(chunk);
        player.sendMessage(format(player, language.CLAIM_SUCCESS));
    }
}
