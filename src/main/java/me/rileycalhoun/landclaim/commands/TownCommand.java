package me.rileycalhoun.landclaim.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizensCache;
import me.rileycalhoun.landclaim.commands.subcommands.*;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TownCommand implements CommandExecutor {

    private final List<SubCommand> subCommands;
    private final String helpMessage;
    private final TownsCache townsCache;
    private final CitizensCache citizensCache;
    private final LangFile language;

    public TownCommand(LandClaim plugin) {
        this.subCommands = new ArrayList<>();
        this.townsCache = plugin.getTownsCache();
        this.citizensCache = plugin.getCitizensCache();
        this.language = plugin.getLangFile();

        subCommands.add(new InfoCommand(plugin));
        subCommands.add(new CreateCommand(plugin));
        subCommands.add(new DisbandCommand(plugin));
        subCommands.add(new InviteCommand(plugin));
        subCommands.add(new ClaimCommand(plugin));
        subCommands.add(new UnclaimCommand(plugin));
        subCommands.add(new PromoteCommand(plugin));
        subCommands.add(new DemoteCommand(plugin));
        subCommands.add(new JoinCommand(plugin));
        subCommands.add(new LeaveCommand(plugin));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < subCommands.size(); i++) {
            SubCommand subCommand = subCommands.get(i);

            stringBuilder.append(ChatColor.GREEN)
                    .append("/town ")
                    .append(subCommand.getUsage())
                    .append(ChatColor.GRAY)
                    .append(ChatColor.GREEN)
                    .append(" - ")
                    .append(subCommand.getDescription())
                    .append(ChatColor.RESET);

            if (i < subCommands.size() - 1) {
                stringBuilder.append('\n');
            }
        }

        this.helpMessage = stringBuilder.toString();
        subCommands.add(new HelpCommand(plugin, helpMessage));
    }

    private Optional<SubCommand> getSubCommandByName(String name) {
        return subCommands.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(helpMessage);
        } else {
            String subCommandName = args[0];
            Optional<SubCommand> optionalSubCommand = getSubCommandByName(subCommandName);
            if (optionalSubCommand.isEmpty()) {
                sender.sendMessage(ChatColor.RED + "Unknown subcommand! Type /town help for help.");
                return true;
            }

            SubCommand subCommand = optionalSubCommand.get();
            if (subCommand.getPermission().isPresent()
                && !player.hasPermission(subCommand.getPermission().get())) {
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                PlaceholderAPI.setPlaceholders(player, language.NO_PERMISSION))
                );
                return true;
            }

            Citizen citizen = citizensCache.getCitizenByUUID(player.getUniqueId());
            assert citizen != null;

            if (subCommand.getRequireTown() && townsCache.getTownByUUID(citizen.getTownUniqueId()) == null) {
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                PlaceholderAPI.setPlaceholders(player, language.TOWN_REQUIRED))
                );
                return true;
            }

            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            if (subCommand.getRequiredArgs() > newArgs.length) {
                player.sendMessage(ChatColor.RED + "Usage: /town " + subCommand.getUsage());
                return true;
            }

            subCommand.execute(citizen, player, newArgs);
        }

        return true;
    }

}
