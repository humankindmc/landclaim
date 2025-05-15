package me.rileycalhoun.landclaim.claims;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ClaimedChunk(int x, int z, @NotNull UUID worldUniqueId, @NotNull UUID townUniqueId) {

    public Chunk getMinecraftChunk() {
        World world = Bukkit.getWorld(worldUniqueId);
        return world == null ? null : world.getChunkAt(x, z);
    }

}
