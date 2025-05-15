package me.rileycalhoun.landclaim.claims;

import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public record ClaimedArea(@NotNull UUID townUniqueId, @NotNull List<ClaimedChunk> claimedChunks) {

    public boolean hasChunk(Chunk chunk) {
        return hasChunk(chunk.getWorld().getUID(), chunk.getX(), chunk.getZ());
    }

    public boolean hasChunk(UUID worldUniqueId, int x, int z) {
        for (ClaimedChunk chunk : claimedChunks) {
            if(!chunk.worldUniqueId().equals(worldUniqueId)) {
                continue;
            }

            if(chunk.x() == x && chunk.z() == z) {
                return true;
            }
        }

        return false;
    }

    public boolean isChunkTouchingClaim(Chunk chunk) {
        return isChunkTouchingClaim(chunk.getWorld().getUID(), chunk.getX(), chunk.getZ());
    }

    public boolean isChunkTouchingClaim(UUID worldUniqueId, int x, int z) {
        for (ClaimedChunk chunk : claimedChunks) {
            if (!chunk.worldUniqueId().equals(worldUniqueId)) {
                continue;
            }

            if (x == chunk.x() + 1 || x == chunk.x() - 1
                    || z == chunk.z() + 1 || z == chunk.z() - 1) {
                return true;
            }
        }

        return false;
    }

    public void claim(Chunk chunk) {
        claim(chunk.getWorld().getUID(), chunk.getX(), chunk.getZ());
    }

    public void claim(UUID worldUniqueId, int x, int z) {
        ClaimedChunk chunk = new ClaimedChunk(x, z, worldUniqueId, townUniqueId);
        // TODO: ChunkClaimedEvent
        claimedChunks.add(chunk);
    }

    public void unclaim(Chunk chunk) {
        unclaim(chunk.getWorld().getUID(), chunk.getX(), chunk.getZ());
    }

    public void unclaim(UUID worldUniqueId, int x, int z) {
        Iterator<ClaimedChunk> iterator = claimedChunks.iterator();
        while(iterator.hasNext()) {
            ClaimedChunk chunk = iterator.next();
            if(chunk.worldUniqueId().equals(worldUniqueId) && chunk.x() == x && chunk.z() == z) {
                iterator.remove();
                break;
            }
        }
    }

}
