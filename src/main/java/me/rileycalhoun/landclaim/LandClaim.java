package me.rileycalhoun.landclaim;

import me.rileycalhoun.landclaim.citizens.CitizensCache;
import me.rileycalhoun.landclaim.claims.ClaimsCache;
import me.rileycalhoun.landclaim.commands.TownCommand;
import me.rileycalhoun.landclaim.storage.LangFile;
import me.rileycalhoun.landclaim.invites.InviteCache;
import me.rileycalhoun.landclaim.listener.ConnectionListener;
import me.rileycalhoun.landclaim.storage.TownsFile;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LandClaim extends JavaPlugin {

    private CitizensCache citizensCache;
    private TownsCache townsCache;
    private ClaimsCache claimsCache;
    private InviteCache inviteCache;

    private LangFile langFile;
    private TownsFile townsFile;

    @Override
    public void onEnable() {
        long startTime = System.nanoTime();

        getLogger().info("Verifying dependencies are installed...");
        if (noDependency("PlaceholderAPI")) {
            getLogger().severe("Could not find PlaceholderAPI! Please add the plugin and restart.");
            disablePlugin();
            return;
        }

        if (noDependency("Vault")) {
            getLogger().severe("Could not find Vault! Please add the plugin and restart.");
            disablePlugin();
            return;
        }

        getLogger().info("Initializing dependency hooks...");
        if(!new Placeholders(this).register()) {
            getLogger().severe("Something went wrong while attempting to register with PlaceholderAPI.");
            disablePlugin();
            return;
        }

        getLogger().info("Getting language file...");
        try {
            this.langFile = new LangFile(getDataFolder());
        } catch(IOException | NullPointerException e) {
            getLogger().severe("Could not get language file: " + e.getMessage());
            disablePlugin();
            return;
        }

        getLogger().info("Getting towns file...");
        try {
            this.townsFile = new TownsFile(getDataFolder());
        } catch (IOException e) {
            getLogger().severe("Could not get towns file: " + e.getMessage());
            disablePlugin();
            return;
        }

        getLogger().info("Initializing caches...");
        this.citizensCache = new CitizensCache(100);
        this.townsCache = new TownsCache(this, 100);
        this.claimsCache = new ClaimsCache(100);
        this.inviteCache = new InviteCache(100);

        getLogger().info("Initializing commands...");
        registerCommands();

        getLogger().info("Initializing listeners...");
        registerEvents();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // get the time in ms
        getLogger().info("Done! The plugin was started in " + duration + "ms.");

        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            getLogger().info("Saving all files...");
            saveAllFiles();
            getLogger().info("Done!");
        }, 20L * 60L * 10L, 20L * 60L * 10L);
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving all files...");
        saveAllFiles();

        getLogger().info("LandClaim has been disabled!");
    }

    public CitizensCache getCitizensCache() {
        return citizensCache;
    }

    public TownsCache getTownsCache() {
        return townsCache;
    }

    public ClaimsCache getClaimsCache() {
        return claimsCache;
    }

    public InviteCache getInviteCache() {
        return inviteCache;
    }

    public LangFile getLangFile() {
        return langFile;
    }

    public TownsFile getTownsFile() {
        return townsFile;
    }

    private void saveAllFiles() {
        getTownsCache().saveTowns();
    }

    private boolean noDependency(String pluginName) {
        return !Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }

    private void disablePlugin() {
        getServer().getPluginManager().disablePlugin(this);
    }

    private void registerEvents() {
        registerEvent(new ConnectionListener(this));
    }

    private void registerEvent(Listener event) {
        this.getServer().getPluginManager().registerEvents(event, this);
    }

    private void registerCommands() {
        registerCommand("town", new TownCommand(this));
    }

    private void registerCommand(
            @NotNull String name,
            @NotNull CommandExecutor executor
    ) {
        PluginCommand command = getCommand(name);

        if (command == null) {
            getLogger()
                    .severe("LandClaim could not find the '" + name + "' command. Contact the plugin developer.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        command.setExecutor(executor);
    }

}
