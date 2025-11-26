package me.sailex.blockybird.client.screen.drawable;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;

import static me.sailex.blockybird.client.BlockyBirdClient.MOD_ID;

public class BirdDrawable implements Drawable {

    private final int screenWidth;

    private static final Identifier BIRD_UPFLAP = Identifier.of(MOD_ID, "yellowbird-upflap.png");
    private static final Identifier BIRD_MIDFLAP = Identifier.of(MOD_ID, "yellowbird-midflap.png");
    private static final Identifier BIRD_DOWNFLAP = Identifier.of(MOD_ID, "yellowbird-downflap.png");
    private static final int BIRD_TEXTURE_HEIGHT = 12;

    private static final float JUMP_SPEED = 3f;
    private static final float FALLING_CONSTANT = 0.20f;
    private static final int BIRD_ANIMATION_SPEED = 5;
    private static final float BIRD_TOP_ROTATION = (float) Math.toRadians(-25);
    private static final float BIRD_BOTTOM_ROTATION = (float) Math.toRadians(90);

    private float birdPositionY;
    private float verticalSpeed = 0f;
    private int birdAnimationIndex = 0;

    private long tickCounter = 0;

    private final Identifier[] birdTypes = { BIRD_UPFLAP, BIRD_MIDFLAP, BIRD_DOWNFLAP };

    public BirdDrawable(int width, int height) {
        this.screenWidth = width;
        this.birdPositionY = (float) height / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
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

        matrices.translate((float) (this.screenWidth - 17) / 2, birdPositionY);

        float angle = Math.min(BIRD_BOTTOM_ROTATION, Math.max(-verticalSpeed - 2, BIRD_TOP_ROTATION));
        matrices.rotate(angle);
        matrices.translate(-17, -BIRD_TEXTURE_HEIGHT);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, birdTypes[birdAnimationIndex],
                0, 0,
                0, 0,
                34, 24,
                34, 24
        );
        matrices.popMatrix();

        tickCounter++;
    }

    public boolean mouseClicked() {
        verticalSpeed = JUMP_SPEED;
        return true;
    }

    public float getPositionY() {
        return this.birdPositionY;
    }
}
