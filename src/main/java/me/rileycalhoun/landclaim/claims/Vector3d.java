package me.rileycalhoun.landclaim.claims;

import org.bukkit.Location;

public class Vector3d {

    private final int x, y, z;

    public Vector3d(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d(Location location) {
        this(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public static Vector3d max(Vector3d a, Vector3d b) {
        int max_x = Math.max(a.getX(), b.getX());
        int max_y = Math.max(a.getY(), b.getY());
        int max_z = Math.max(a.getZ(), b.getZ());
        return new Vector3d(max_x, max_y,  max_z);
    }

    public static Vector3d min(Vector3d a, Vector3d b) {
        int min_x = Math.min(a.getX(), b.getX());
        int min_y = Math.min(a.getY(), b.getY());
        int min_z = Math.min(a.getZ(), b.getZ());
        return new Vector3d(min_x, min_y, min_z);
    }

}
