package me.sailex.blockybird.client.screen.drawable;

import me.sailex.blockybird.client.screen.PipePair;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;

import java.util.ArrayDeque;
import java.util.Deque;

public class PipePairsDrawable implements Drawable {

    private static final int PIPE_HORIZONTAL_GAP = 120;
    private static final int PIPE_HORIZONTAL_SPEED = 2;
    private final Deque<PipePair> pipePairs;

    private final int startX;
    private final int screenWidth;
    private final int screenHeight;

    public PipePairsDrawable(int screenWidth, int screenHeight) {
        this.pipePairs = new ArrayDeque<>();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.startX = screenWidth / 2 + PIPE_HORIZONTAL_GAP;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        updateDrawables();
        renderDrawables(context);
    }

    private void updateDrawables() {
        PipePair first = pipePairs.peekFirst();
        if (first != null && first.getX() + PipeDrawable.TEXTURE_WIDTH < 0) {
            pipePairs.remove();
        }

        PipePair last = pipePairs.peekLast();
        while (pipePairs.isEmpty() || last.getX() <= screenWidth) {
            int x;
            int lastRandomYPosition;
            if (pipePairs.isEmpty()) {
                x = startX;
                lastRandomYPosition = screenHeight / 2;
            } else {
                x = pipePairs.peekFirst().getX();
                lastRandomYPosition = last.getRandomYPosition();
            }
            last = new PipePair(screenHeight, lastRandomYPosition);
            last.updatePosition(x + pipePairs.size() * (PIPE_HORIZONTAL_GAP + PipeDrawable.TEXTURE_WIDTH));
            pipePairs.add(last);
        }
    }

    public void updatePositions() {
        for (PipePair pipePair : pipePairs) {
            pipePair.updatePosition(pipePair.getX() - PIPE_HORIZONTAL_SPEED);
        }
    }

    private void renderDrawables(DrawContext context) {
        for (PipePair drawable : this.pipePairs) {
            drawable.render(context);
        }
    }

    public Deque<PipePair> getPipePairs() {
        return this.pipePairs;
    }

}
