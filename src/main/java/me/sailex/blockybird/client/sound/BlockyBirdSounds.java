package me.sailex.blockybird.client.sound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static me.sailex.blockybird.client.BlockyBirdClient.MOD_ID;

public class BlockyBirdSounds {

    private BlockyBirdSounds() {}

    public static final SoundEvent DIE = registerSound("die");
    public static final SoundEvent HIT = registerSound("hit");
    public static final SoundEvent POINT = registerSound("point");
    public static final SoundEvent SWOOSH = registerSound("swoosh");
    public static final SoundEvent WING = registerSound("wing");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void playSound(SoundEvent sound) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.world.playSoundClient(sound, SoundCategory.UI, 20, 0);
    }

    public static void initialize() {}

}
