package me.sailex.blockybird.client.screen.drawable;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.util.Identifier;

import static me.sailex.blockybird.client.BlockyBirdClient.MOD_ID;

public class PointsDrawable implements Drawable {

    private static final int NUMBER_GAP = 1;
    private static final int TEXTURE_WIDTH = 24;
    private static final int TEXTURE_HEIGHT = 36;
    private final int screenWidth;
    private int points;

    public PointsDrawable(int screenWidth) {
        this.screenWidth = screenWidth;
        this.points = 0;
    }

    public void updatePoints(int points) {
        this.points = points;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        String[] numbers = String.valueOf(points).split("");

        int startX = (screenWidth - numbers.length * (TEXTURE_WIDTH) - (numbers.length - 1) * NUMBER_GAP) / 2;
        for (int i = 0; i < numbers.length; i++) {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, Identifier.of(MOD_ID, numbers[i] + ".png"),
                    startX + i * (TEXTURE_WIDTH) + i * NUMBER_GAP, 20,
                    0 , 0,
                    TEXTURE_WIDTH, TEXTURE_HEIGHT,
                    TEXTURE_WIDTH, TEXTURE_HEIGHT
            );
        }
    }

    public int getPointCount() {
        return points;
    }
}
