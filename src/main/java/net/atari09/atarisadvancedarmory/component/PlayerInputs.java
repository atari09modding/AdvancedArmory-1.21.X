package net.atari09.atarisadvancedarmory.component;

public interface PlayerInputs {
    boolean isRopeSwinging();
    void setRopeSwinging(boolean b);

    boolean isClimbingRopeUp();
    boolean isClimbingRopeDown();

    void setClimbingRope(int i);

}
