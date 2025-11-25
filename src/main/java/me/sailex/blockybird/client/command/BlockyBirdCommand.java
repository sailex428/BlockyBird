package me.sailex.blockybird.client.command;

import me.sailex.blockybird.client.screen.BlockyBirdScreen;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class BlockyBirdCommand {

    public void registerCommand() {
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(literal("blockybird").executes(context -> {
                context.getSource().getClient().send(() -> context.getSource().getClient().setScreen( new BlockyBirdScreen()));
                return 0;
            }));
        }));
    }

}
