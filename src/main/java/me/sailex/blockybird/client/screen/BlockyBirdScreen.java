package me.sailex.blockybird.client.screen;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static me.sailex.blockybird.client.BlockyBirdClient.MOD_ID;

public class BlockyBirdScreen extends Screen {

    private static final Identifier BIRD_UPFLAP = Identifier.of(MOD_ID, "yellowbird-upflap.png");
    private static final Identifier BACKGROUND_DAY = Identifier.of(MOD_ID, "background_day.png");
    private static final Identifier BACKGROUND_NIGHT = Identifier.of(MOD_ID, "background_night.png");

    private static final int JUMP_SPEED = 1;
    private static final int FALLING_CONSTANT = 2;

    private int verticalSpeed = 0;
    private int positionYBird = this.height / 2;

    public BlockyBirdScreen() {
        super(Text.of("BlockyBird"));
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        positionYBird += verticalSpeed;
        verticalSpeed -= FALLING_CONSTANT;

        context.drawTexture(RenderPipelines.GUI_TEXTURED, BIRD_UPFLAP, (this.width - 288) / 2, positionYBird, 0, 0, 34, 24, 34, 24);
    }

    public boolean mouseClicked(Click click, boolean doubled) {
        verticalSpeed = JUMP_SPEED;
        return true;
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        System.out.println(deltaTicks);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, BACKGROUND_DAY, (this.width - 288) / 2, 0, 0, 0, 288, 500, 288, 500);
    }

}
