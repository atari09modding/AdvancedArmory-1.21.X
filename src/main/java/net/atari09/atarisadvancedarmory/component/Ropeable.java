package net.atari09.atarisadvancedarmory.component;

import net.minecraft.world.phys.Vec3;

public interface Ropeable{
    void setOnRope(boolean b);
    boolean isOnRope();
    Vec3 getRopeCenter();
    void setRopeCenter(Vec3 center);

}
