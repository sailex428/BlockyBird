package me.sailex.blockybird.client.screen;

import me.sailex.blockybird.client.screen.drawable.BirdDrawable;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;

import static me.sailex.blockybird.client.BlockyBirdClient.MOD_ID;

public class BlockyBirdScreen extends Screen {

    private static final Identifier BACKGROUND_DAY = Identifier.of(MOD_ID, "background-day.png");
    private static final Identifier BACKGROUND_NIGHT = Identifier.of(MOD_ID, "background-night.png");

    private BirdDrawable bird;

    public BlockyBirdScreen() {
        super(Text.of("BlockyBird"));
    }

    @Override
    protected void init() {
        super.init();
        this.bird = new BirdDrawable(this.width, this.height);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        bird.render(context, mouseX, mouseY, delta);
    }

    public boolean mouseClicked(Click click, boolean doubled) {
        return bird.mouseClicked();
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.renderBackground(context, mouseX, mouseY, deltaTicks);
//        context.drawTexture(RenderPipelines.GUI_TEXTURED, BACKGROUND_DAY,
//                (this.width - 142) / 2,
//                0, 0, 0,
//                142, 250,
//                288, 500
//        );
    }

}
