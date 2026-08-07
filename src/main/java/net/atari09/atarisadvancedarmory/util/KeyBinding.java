package net.atari09.atarisadvancedarmory.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_AAA = "key.category.atarisadvancedarmory.atari";
    public static final String KEY_PULL_WEAPON_OUT = "key.atarisadvancedarmory.pull_weapon_out";

    public static final KeyMapping PULL_WEAPON_OUT_KEY = new KeyMapping(KEY_PULL_WEAPON_OUT, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_AAA);


}
