package me.sailex.blockybird.client;

import me.sailex.blockybird.client.command.BlockyBirdCommand;
import net.fabricmc.api.ClientModInitializer;

public class BlockyBirdClient implements ClientModInitializer {

    public static final String MOD_ID = "blocky-bird";

    @Override
    public void onInitializeClient() {
        BlockyBirdCommand command = new BlockyBirdCommand();
        command.registerCommand();
    }
}
