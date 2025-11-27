package me.sailex.blockybird.client.screen;

import me.sailex.blockybird.client.screen.drawable.BirdDrawable;
import me.sailex.blockybird.client.screen.drawable.PipeDrawable;
import me.sailex.blockybird.client.screen.drawable.PipePairsDrawable;
import me.sailex.blockybird.client.screen.drawable.PointsDrawable;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class BlockyBirdScreen extends Screen {

    private BirdDrawable bird;
    private PipePairsDrawable pipePairs;
    private PointsDrawable points;
    private PipePair next;

    public BlockyBirdScreen() {
        super(Text.of("BlockyBird"));
    }

    @Override
    protected void init() {
        super.init();
        this.bird = new BirdDrawable(this.width, this.height);
        this.pipePairs = new PipePairsDrawable(this.width, this.height);
        this.points = new PointsDrawable(this.width);

        addDrawable(bird);
        addDrawable(pipePairs);
        addDrawable(points);
        PipePair.resetCount();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        checkGameOver();
        updatePoints();
    }

    private void checkGameOver() {
        if (isBirdOutOfScreen() || isBirdTouchingPipe()) {
            this.client.setScreen(null);
        }
    }

    private boolean isBirdTouchingPipe() {
        return this.pipePairs.getPipePairs().stream().anyMatch(pipePair -> pipePair.isOver(
                bird.getPositionX(),
                bird.getPositionX() + (float) BirdDrawable.BIRD_TEXTURE_WIDTH / 2,
                bird.getPositionY() - 10,
                bird.getPositionY() + (float) BirdDrawable.BIRD_TEXTURE_HEIGHT / 2
                )
        );
    }

    private boolean isBirdOutOfScreen() {
        return bird.getPositionY() + BirdDrawable.BIRD_TEXTURE_HEIGHT > this.height || bird.getPositionY() < 0;
    }

    private void updatePoints() {
        if (this.next == null) {
            this.next = getNextPipePair();
        } else if (this.next.getX() < (width / 2 - PipeDrawable.TEXTURE_WIDTH)) {
            points.updatePoints(next.getId());
            this.next = getNextPipePair();
        }
    }

    private PipePair getNextPipePair() {
        PipePair next = this.pipePairs.getPipePairs().getLast();
        for (PipePair pipePair : this.pipePairs.getPipePairs()) {
            if (pipePair.getX() < next.getX() &&
                pipePair.getX() > width / 2
            ) {
                next = pipePair;
            }
        }
        return next;
    }

    public boolean mouseClicked(Click click, boolean doubled) {
        return bird.mouseClicked();
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.renderBackground(context, mouseX, mouseY, deltaTicks);
    }

}
