package me.sailex.blockybird.client;

import me.sailex.blockybird.client.command.BlockyBirdCommand;
import me.sailex.blockybird.client.sound.BlockyBirdSounds;
import net.fabricmc.api.ClientModInitializer;

public class BlockyBirdClient implements ClientModInitializer {

    public static final String MOD_ID = "blocky-bird";

    @Override
    public void onInitializeClient() {
        BlockyBirdSounds.initialize();
        BlockyBirdCommand command = new BlockyBirdCommand();
        command.registerCommand();
    }
}
