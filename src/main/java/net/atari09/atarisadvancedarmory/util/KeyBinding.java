package net.atari09.atarisadvancedarmory.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_AAA = "key.category.atarisadvancedarmory.atari";
    public static final String KEY_PULL_WEAPON_OUT = "key.atarisadvancedarmory.pull_weapon_out";
    public static final String KEY_ROPE_UP = "key.atarisadvancedarmory.rope_up";
    public static final String KEY_ROPE_DOWN = "key.atarisadvancedarmory.rope_down";

    public static final KeyMapping PULL_WEAPON_OUT_KEY = new KeyMapping(KEY_PULL_WEAPON_OUT, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_AAA);

    public static final KeyMapping ROPE_UP_KEY = new KeyMapping(KEY_ROPE_UP, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UP, KEY_CATEGORY_AAA);

    public static final KeyMapping ROPE_DOWN_KEY = new KeyMapping(KEY_ROPE_DOWN, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, KEY_CATEGORY_AAA);


}
