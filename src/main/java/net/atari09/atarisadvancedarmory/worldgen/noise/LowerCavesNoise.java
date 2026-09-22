package net.atari09.atarisadvancedarmory.worldgen.noise;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class LowerCavesNoise {
    private static final float FREQ = 0.01f;   // kleiner = größere Schollen
    private static final float GAP = 3f;       // Rissbreite in Blöcken (ungefähr)
    private static final float COVER = 0.75f;  // Anteil der Zellen mit Eis
    private static final float WARP = 12f;     // Stärke der Kantenverbiegung in Blöcken

    private final FastNoiseLite edges = new FastNoiseLite();
    private final FastNoiseLite cells = new FastNoiseLite();

    public LowerCavesNoise(long seed) {
        int s = Long.hashCode(seed);

        edges.SetSeed(s);
        edges.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        edges.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Euclidean);
        edges.SetCellularReturnType(FastNoiseLite.CellularReturnType.Distance2Sub);
        edges.SetFrequency(FREQ);
        edges.SetDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2);
        edges.SetDomainWarpAmp(WARP);

        // gleiche Zellen, aber mit einem Zufallswert pro Zelle (für COVER)
        cells.SetSeed(s);
        cells.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        cells.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Euclidean);
        cells.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
        cells.SetFrequency(FREQ);
    }

    public boolean isCave(int x, int z) {
        FastNoiseLite.Vector2 p = new FastNoiseLite.Vector2(x, z);
        edges.DomainWarp(p); // p ist danach verschoben, beide Noises nutzen es

        // manche Zellen bleiben offenes Wasser
        //if (cells.GetNoise(p.x, p.y) > -1f + 2f * COVER) return false;

        // Abstand zur Zellkante: nahe -1 = direkt am Riss
        return edges.GetNoise(p.x, p.y) < -0.9f + GAP * FREQ;
    }

    private static final float INNER = 3f; // distance from rim to make 2 blocks thick

    public float getValue(int x, int z) {
        FastNoiseLite.Vector2 p = new FastNoiseLite.Vector2(x, z);
        edges.DomainWarp(p);



        float v = edges.GetNoise(p.x, p.y);
        if (!isCave(x,z)) return 0f;
        return v;
    }

    public static void img() throws Exception {
        LowerCavesNoise n = new LowerCavesNoise(12345L);
        int size = 1024;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                boolean t = n.isCave(x, z);
                img.setRGB(x, z, t? 0x00000000 : 0xFFFFFFFF);
            }
        }
        ImageIO.write(img, "png", new File("caves.png"));
    }
}
