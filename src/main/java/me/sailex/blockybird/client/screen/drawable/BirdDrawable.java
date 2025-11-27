package me.sailex.blockybird.client.screen.drawable;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;

import static me.sailex.blockybird.client.BlockyBirdClient.MOD_ID;

public class BirdDrawable implements Drawable {

    private static final Identifier BIRD_UPFLAP = Identifier.of(MOD_ID, "yellowbird-upflap.png");
    private static final Identifier BIRD_MIDFLAP = Identifier.of(MOD_ID, "yellowbird-midflap.png");
    private static final Identifier BIRD_DOWNFLAP = Identifier.of(MOD_ID, "yellowbird-downflap.png");
    public static final int BIRD_TEXTURE_HEIGHT = 24;
    public static final int BIRD_TEXTURE_WIDTH = 34;

    private static final float JUMP_SPEED = 3.7f;
    private static final float FALLING_CONSTANT = 0.20f;
    private static final int BIRD_ANIMATION_SPEED = 5;
    private static final float BIRD_TOP_ROTATION = (float) Math.toRadians(-25);
    private static final float BIRD_BOTTOM_ROTATION = (float) Math.toRadians(90);

    private float birdPositionY;
    private final float birdPositionX;
    private float verticalSpeed = 0f;
    private int birdAnimationIndex = 0;

    private long tickCounter = 0;

    private final Identifier[] birdTypes = { BIRD_UPFLAP, BIRD_MIDFLAP, BIRD_DOWNFLAP };

    public BirdDrawable(int width, int height) {
        this.birdPositionY = (float) height / 2;
        this.birdPositionX = (float) (width - BIRD_TEXTURE_WIDTH) / 2;
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
        matrices.translate(birdPositionX, birdPositionY);

        float angle = Math.min(BIRD_BOTTOM_ROTATION, Math.max(-verticalSpeed - 2, BIRD_TOP_ROTATION));
        matrices.rotate(angle);
        matrices.translate(-((float) BIRD_TEXTURE_WIDTH / 2), -((float) BIRD_TEXTURE_HEIGHT / 2));

        context.drawTexture(RenderPipelines.GUI_TEXTURED, birdTypes[birdAnimationIndex],
                0, 0,
                0, 0,
                BIRD_TEXTURE_WIDTH, BIRD_TEXTURE_HEIGHT,
                BIRD_TEXTURE_WIDTH, BIRD_TEXTURE_HEIGHT
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

    public float getPositionX() {
        return birdPositionX;
    }
}
