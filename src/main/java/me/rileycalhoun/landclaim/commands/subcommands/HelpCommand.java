package me.rileycalhoun.landclaim.commands.subcommands;

import me.rileycalhoun.landclaim.commands.SubCommand;
import me.rileycalhoun.landclaim.config.LangFile;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

// TODO: Put help command in lang.yml
public class HelpCommand extends SubCommand {

    @NotNull
    private final String helpMessage;

    public HelpCommand(JavaPlugin plugin, TownsCache townsCache, LangFile language, @NotNull String helpMessage) {
        super(plugin);
        this.helpMessage = helpMessage;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Display the help menu";
    }

    @Override
    public String getUsage() {
        return "help";
    }

    @Override
    public int getRequiredArgs() {
        return 0;
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.empty();
    }

    @Override
    public boolean getRequireTown() {
        return false;
    }

    @Override
    public void execute(Player player, String[] args) {
        player.sendMessage(helpMessage);
    }
}
