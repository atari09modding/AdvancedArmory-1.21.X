package net.atari09.atarisadvancedarmory.client;

public class ScreenShake {

    public static int ticks = 0;
    public static float strength = 0f;

    public static void start(int duration, float intensity) {
        ticks = duration;
        strength = intensity;
    }

    public static void tick() {
        if (ticks > 0) {
            ticks--;
        }
    }
}
