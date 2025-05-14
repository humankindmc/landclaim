package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.claims.ClaimTools;
import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.claims.ClaimArea;
import me.rileycalhoun.landclaim.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    public void execute(Citizen citizen, Player player, String[] args) {
        assert citizen.getTownUniqueId() != null;
        assert citizen.getCitizenRank() != null;

        if (citizen.getCitizenRank() == CitizenRank.MAYOR
                || citizen.getCitizenRank() == CitizenRank.OFFICER) {
            player.sendMessage(format(player, language.TOWN_OFFICER_REQUIRED));
            return;
        }

        if (InventoryUtils.isInInventory(player.getInventory(), ClaimTools.getClaimWand())) {
            player.sendMessage(ChatColor.RED + "You already have the Claim Wand!");
            return;
        }

        player.getInventory().addItem(
                ClaimTools.getClaimWand()
        );

        player.sendMessage(ChatColor.GREEN + "You have been given a " + ChatColor.GRAY + "claim wand" + ChatColor.GREEN + "!");
    }

}
