package net.atari09.atarisadvancedarmory.worldgen.noise;

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

    public boolean isEdge(int x, int z){
        FastNoiseLite.Vector2 p = new FastNoiseLite.Vector2(x, z);
        edges.DomainWarp(p); // p ist danach verschoben, beide Noises nutzen es

        // manche Zellen bleiben offenes Wasser
        if (cells.GetNoise(p.x, p.y) > -1f + 2f * COVER) return false;

        // Abstand zur Zellkante: nahe -1 = direkt am Riss
        return edges.GetNoise(p.x, p.y) > -2f + GAP * FREQ;
    }
}
