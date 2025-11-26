package me.sailex.blockybird.client.screen;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;

import static me.sailex.blockybird.client.BlockyBirdClient.MOD_ID;

public class BlockyBirdScreen extends Screen {

    private static final Identifier BIRD_UPFLAP = Identifier.of(MOD_ID, "yellowbird-upflap.png");
    private static final Identifier BIRD_MIDFLAP = Identifier.of(MOD_ID, "yellowbird-midflap.png");
    private static final Identifier BIRD_DOWNFLAP = Identifier.of(MOD_ID, "yellowbird-downflap.png");

    private static final Identifier BACKGROUND_DAY = Identifier.of(MOD_ID, "background-day.png");
    private static final Identifier BACKGROUND_NIGHT = Identifier.of(MOD_ID, "background-night.png");

    private static final float JUMP_SPEED = 3f;
    private static final float FALLING_CONSTANT = 0.20f;
    private static final int BIRD_ANIMATION_SPEED = 5;
    private static final float BIRD_TOP_ROTATION = (float) Math.toRadians(-25);
    private static final float BIRD_BOTTOM_ROTATION = (float) Math.toRadians(90);

    private float verticalSpeed = 0f;
    private float birdPositionY = (float) this.height / 2;
    private int birdAnimationIndex = 0;

    private long tickCounter = 0;

    private final Identifier[] birdTypes = { BIRD_UPFLAP, BIRD_MIDFLAP, BIRD_DOWNFLAP };

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

        drawBird(context);

        tickCounter++;
    }

    private void drawBird(DrawContext context) {
        birdPositionY -= verticalSpeed;
        verticalSpeed -= FALLING_CONSTANT;

        if (verticalSpeed > -1) {
            if (tickCounter % BIRD_ANIMATION_SPEED == 0) {
                birdAnimationIndex++;
                if (birdAnimationIndex == birdTypes.length) {
                    birdAnimationIndex = 0;
                }
            }
        } else {
            birdAnimationIndex = 1;
        }

        Matrix3x2fStack matrices = context.getMatrices();
        matrices.pushMatrix();

        matrices.translate((float) (this.width - 17) / 2, birdPositionY);

        float angle = Math.min(BIRD_BOTTOM_ROTATION, Math.max(-verticalSpeed - 2, BIRD_TOP_ROTATION));
        matrices.rotate(angle);
        matrices.translate(-17, -12);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, birdTypes[birdAnimationIndex],
                0, 0,
                0, 0,
                34, 24,
                34, 24
        );
        matrices.popMatrix();
    }

    public boolean mouseClicked(Click click, boolean doubled) {
        verticalSpeed = JUMP_SPEED;
        return true;
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
