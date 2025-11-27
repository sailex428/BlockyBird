package me.sailex.blockybird.client.screen;

import me.sailex.blockybird.client.screen.drawable.PipeDrawable;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Direction;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PipePair {

    private static AtomicInteger count = new AtomicInteger(0);
    private static final int PIPE_GAP = 75;

    private final PipeDrawable pipeDrawableDown;
    private final PipeDrawable pipeDrawableUp;
    private final int screenHeight;
    private final int verticalGap;
    private final int id;
    private final int randomY;
    private final int maxRandomYOffset;

    private int x = 0;

    public PipePair(int screenHeight, int lastRandomYPosition) {
        this.screenHeight = screenHeight;
        this.verticalGap = screenHeight / 7 + 20;
        this.id = count.incrementAndGet();
        this.maxRandomYOffset = screenHeight / 2;
        this.randomY = getRandomYPosition(lastRandomYPosition);
        this.pipeDrawableDown = new PipeDrawable(Direction.DOWN, randomY - PipeDrawable.TEXTURE_HEIGHT - PIPE_GAP / 2);
        this.pipeDrawableUp = new PipeDrawable(Direction.UP, randomY + PIPE_GAP / 2);
    }

    public void render(DrawContext context) {
        this.pipeDrawableDown.render(context);
        this.pipeDrawableUp.render(context);
    }

    public void updatePosition(int x) {
        this.x = x;
        this.pipeDrawableDown.setX(x);
        this.pipeDrawableUp.setX(x);
    }

    private int getRandomYPosition(int lastRandomYPosition) {
        Random random = new Random();
        int up = Math.min(this.screenHeight - verticalGap, lastRandomYPosition + maxRandomYOffset);
        int bottom = Math.max(verticalGap, lastRandomYPosition - maxRandomYOffset);
        return random.nextInt(bottom, up);
    }

    public boolean isOver(float x1, float x2, float y1, float y2) {
        return pipeDrawableDown.isOver(x1, x2, y1, y2) || pipeDrawableUp.isOver(x1, x2, y1, y2);
    }

    public int getX() {
        return x;
    }

    public int getId() {
        return id;
    }

    public static void resetCount() {
        count = new AtomicInteger(0);
    }

    public int getRandomYPosition() {
        return this.randomY;
    }

}
