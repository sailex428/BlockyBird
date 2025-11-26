package me.sailex.blockybird.client.screen.drawable;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.joml.Matrix3x2fStack;

import static me.sailex.blockybird.client.BlockyBirdClient.MOD_ID;

public class PipeDrawable {

    private static final Identifier PIPE = Identifier.of(MOD_ID, "pipe-green.png");
    public static final int TEXTURE_WIDTH = 52;
    public static final int TEXTURE_HEIGHT = 320;
    private final Direction direction;

    private int x = 0;
    private final int y;

    public PipeDrawable(Direction direction, int y) {
        this.direction = direction;
        this.y = y;
    }

    public void render(DrawContext context) {
        Matrix3x2fStack matrices = context.getMatrices();
        matrices.pushMatrix();

        matrices.translate(this.x, this.y);

        if (direction == Direction.DOWN) {
            matrices.rotate((float) Math.toRadians(180));
            matrices.translate(-TEXTURE_WIDTH, -TEXTURE_HEIGHT);
        }

        context.drawTexture(RenderPipelines.GUI_TEXTURED, PIPE,
                0, 0,
                0, 0,
                TEXTURE_WIDTH, TEXTURE_HEIGHT,
                TEXTURE_WIDTH, TEXTURE_HEIGHT);

        matrices.popMatrix();
    }

    public boolean isOver(float x1, float y1, float x2, float y2) {
        return x + TEXTURE_WIDTH >= x1 && x <= x2 &&
                y <= y2 && y + TEXTURE_HEIGHT >= y1;
    }

    public void setX(int x) {
        this.x = x;
    }

}
