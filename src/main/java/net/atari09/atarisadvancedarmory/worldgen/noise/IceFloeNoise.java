package net.atari09.atarisadvancedarmory.worldgen.noise;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class IceFloeNoise{
    private static final float FREQ = 0.04f;   // kleiner = größere Schollen
    private static final float GAP = 3f;       // Rissbreite in Blöcken (ungefähr)
    private static final float COVER = 0.75f;  // Anteil der Zellen mit Eis
    private static final float WARP = 12f;     // Stärke der Kantenverbiegung in Blöcken

    private final FastNoiseLite edges = new FastNoiseLite();
    private final FastNoiseLite cells = new FastNoiseLite();

    public IceFloeNoise(long seed) {
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

    public boolean isFloe(int x, int z) {
        FastNoiseLite.Vector2 p = new FastNoiseLite.Vector2(x, z);
        edges.DomainWarp(p); // p ist danach verschoben, beide Noises nutzen es

        // manche Zellen bleiben offenes Wasser
        if (cells.GetNoise(p.x, p.y) > -1f + 2f * COVER) return false;

        // Abstand zur Zellkante: nahe -1 = direkt am Riss
        return edges.GetNoise(p.x, p.y) > -1f + GAP * FREQ;
    }

    private static final float INNER = 3f; // distance from rim to make 2 blocks thick

    public int floeThickness(int x, int z) {
        FastNoiseLite.Vector2 p = new FastNoiseLite.Vector2(x, z);
        edges.DomainWarp(p);

        if (cells.GetNoise(p.x, p.y) > -1f + 2f * COVER) return 0;

        float v = edges.GetNoise(p.x, p.y);
        if (v <= -1f + GAP * FREQ) return 0;                       // Riss
        if (v <= -1f + (GAP + 2f * INNER) * FREQ) return 1;        // Rand: 1 Block
        return 2;
    }

    public static void img() throws Exception {
        IceFloeNoise n = new IceFloeNoise(12345L);
        int size = 1024;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                int t = n.floeThickness(x, z);
                img.setRGB(x, z, t == 0 ? 0x1E4FA0 : t == 1 ? 0xB8E4FF : 0xFFFFFF);
            }
        }
        ImageIO.write(img, "png", new File("floes.png"));
    }
}
