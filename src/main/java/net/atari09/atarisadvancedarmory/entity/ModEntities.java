package net.atari09.atarisadvancedarmory.entity;

import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, AtarisAdvancedArmory.MOD_ID);



    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
