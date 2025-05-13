package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.claims.ClaimArea;
import org.bukkit.Bukkit;
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
        return "Claim a chunk of land";
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
    public void execute(Citizen citizen, String[] args) {
        Player player = (Player) citizen.getPlayer();
        assert citizen.getTownUniqueId() != null;
        assert citizen.getCitizenRank() != null;

        if (citizen.getCitizenRank() == CitizenRank.MAYOR
                || citizen.getCitizenRank() == CitizenRank.OFFICER) {
            player.sendMessage(format(player, language.TOWN_OFFICER_REQUIRED));
            return;
        }

        Chunk chunk = player.getLocation().getChunk();
        Optional<Town> claimer = claimManager.getChunkOwner(chunk);
        if (claimer.isPresent()) {
            player.sendMessage(format(player, language.CHUNK_ALREADY_CLAIMED));
            return;
        }


        TownClaimEvent event = new TownClaimEvent(town.get(), chunk);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            player.sendMessage(format(player, language.UNABLE_TO_CLAIM_CHUNK));
            return;
        }

        ClaimArea mainClaim = town.get().getMainClaim();
        Optional<ClaimError> claimError = mainClaim.claim(chunk);
        if (claimError.isEmpty()) {
            player.sendMessage(format(player, language.CLAIM_SUCCESS));
        } else {
            if (claimError.get() == ClaimError.ALREADY_CLAIMED) {
                player.sendMessage(format(player, language.CHUNK_NOT_BORDERING));
            } else {
                player.sendMessage(format(player, language.SOMETHING_WENT_WRONG));
            }
        }
    }

}
