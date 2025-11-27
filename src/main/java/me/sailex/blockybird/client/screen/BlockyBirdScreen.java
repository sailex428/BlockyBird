package me.sailex.blockybird.client.screen;

import me.sailex.blockybird.client.screen.drawable.BirdDrawable;
import me.sailex.blockybird.client.screen.drawable.PipeDrawable;
import me.sailex.blockybird.client.screen.drawable.PipePairsDrawable;
import me.sailex.blockybird.client.screen.drawable.PointsDrawable;
import me.sailex.blockybird.client.sound.BlockyBirdSounds;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;

public class BlockyBirdScreen extends Screen {

    private BirdDrawable bird;
    private PipePairsDrawable pipePairs;
    private PointsDrawable points;
    private PipePair next;
    private boolean isGameOver;

    public BlockyBirdScreen() {
        super(Text.of("BlockyBird"));
        isGameOver = false;
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
        BlockyBirdSounds.playSound(BlockyBirdSounds.SWOOSH);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        checkGameOver();
        updatePoints();

        if (!isGameOver) {
            pipePairs.updatePositions();
        }
    }

    private void checkGameOver() {
        if (!isGameOver) {
            if (isBirdOutOfScreen()) {
                isGameOver = true;
            }
            if (isBirdTouchingPipe()) {
                isGameOver = true;
                BlockyBirdSounds.playSound(BlockyBirdSounds.HIT);
                BlockyBirdSounds.playSound(BlockyBirdSounds.DIE);
            }
        }
    }

    private boolean isBirdTouchingPipe() {
        return this.pipePairs.getPipePairs().stream().anyMatch(pipePair -> pipePair.isOver(
                bird.getPositionX(),
                bird.getPositionX() + (float) BirdDrawable.BIRD_TEXTURE_WIDTH / 2,
                bird.getPositionY() - 13,
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
        } else if (
            this.next.getX() < (width / 2 - PipeDrawable.TEXTURE_WIDTH) &&
            next.getId() > points.getPointCount()
        ) {
            BlockyBirdSounds.playSound(BlockyBirdSounds.POINT);
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

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        return handleClick();
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (input.isEnterOrSpace()) {
            return handleClick();
        } else if (input.isEscape()) {
            this.client.setScreen(null);
            return true;
        }
        return false;
    }

    private boolean handleClick() {
        if (isGameOver) {
            this.client.send(() -> client.setScreen(new BlockyBirdScreen()));
        } else {
            BlockyBirdSounds.playSound(BlockyBirdSounds.WING);
            bird.mouseClicked();
        }
        return true;
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.renderBackground(context, mouseX, mouseY, deltaTicks);
    }

}
