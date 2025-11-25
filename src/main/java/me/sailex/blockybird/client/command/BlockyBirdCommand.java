package me.sailex.blockybird.client.command;

import com.jcraft.jorbis.Block;
import me.sailex.blockybird.client.screen.BlockyBirdScreen;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class BlockyBirdCommand {

    private final BlockyBirdScreen screen;

    public BlockyBirdCommand() {
        this.screen = new BlockyBirdScreen();
    }

    public void registerCommand() {
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(literal("blockybird").executes(context -> {
                System.out.println("open screen");
                context.getSource().getClient().setScreen(screen);
                return 0;
            }));
        }));
    }

}
