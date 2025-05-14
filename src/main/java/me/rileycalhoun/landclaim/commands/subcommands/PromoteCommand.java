package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import me.rileycalhoun.landclaim.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PromoteCommand extends SubCommand {

    public PromoteCommand(LandClaim plugin) {
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
    public void execute(Citizen citizen, Player player, String[] args) {
        assert citizen.getTownUniqueId() != null;
        assert citizen.getCitizenRank() != null;

        if (citizen.getCitizenRank().getValue() < CitizenRank.MAYOR.getValue()) {
            player.sendMessage(format(player, language.TOWN_MAYOR_REQUIRED));
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(format(player, language.PLAYER_NOT_ONLINE));
            return;
        }

        if (targetPlayer.getName().equalsIgnoreCase(player.getName())) {
            player.sendMessage(format(player, "&cYou cannot promote yourself past mayor!"));
            return;
        }

        Citizen targetCitizen = citizensCache.getCitizenByUUID(targetPlayer.getUniqueId());
        assert targetCitizen != null;

        if (targetCitizen.getTownUniqueId() != citizen.getTownUniqueId()) {
            player.sendMessage(format(player, language.PLAYER_NOT_IN_TOWN));
            return;
        }


        if (args.length >= 2 && args[1].equalsIgnoreCase("confirm")) {
            targetCitizen.promote();
            targetPlayer.sendMessage(format(player, "&aYou have been promoted to &7" + targetCitizen.getCitizenRank()));
            player.sendMessage(format(player, "&aYou have promoted &7" + targetPlayer.getName() + "&a to &7" + targetCitizen.getCitizenRank()));
        } else {
            if (targetCitizen.getCitizenRank() == CitizenRank.CITIZEN) {
                player.sendMessage(format(player, "&aAre you sure you want to promote &7" + player.getName()
                        + " &ato &7OFFICER&a? Type /town promote " + targetPlayer.getName() + " to confirm."));
            } else if (targetCitizen.getCitizenRank() == CitizenRank.OFFICER) {
                player.sendMessage(format(player, "&aAre you sure you want to promote &7" + player.getName()
                        + " &ato &7MAYOR&a? Type /town promote " + targetPlayer.getName() + " to confirm."));
            }
        }
    }

}
