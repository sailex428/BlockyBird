package me.sailex.blockybird.client.screen;

import me.sailex.blockybird.client.screen.drawable.BirdDrawable;
import me.sailex.blockybird.client.screen.drawable.PipePairsDrawable;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class BlockyBirdScreen extends Screen {

    private BirdDrawable bird;
    private PipePairsDrawable pipePairs;

    public BlockyBirdScreen() {
        super(Text.of("BlockyBird"));
    }

    @Override
    protected void init() {
        super.init();
        this.bird = new BirdDrawable(this.width, this.height);
        this.pipePairs = new PipePairsDrawable(this.width, this.height);

        addDrawable(bird);
        addDrawable(pipePairs);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        checkGameOver();
    }

    private void checkGameOver() {
        if (isBirdOutOfScreen() || isBirdTouchingPipe()) {
            this.client.setScreen(null);
        }
    }

    private boolean isBirdTouchingPipe() {
        return this.pipePairs.getPipePairs().stream().anyMatch(pipePair -> pipePair.isOver(
                bird.getPositionX(),
                bird.getPositionX() + BirdDrawable.BIRD_TEXTURE_WIDTH,
                bird.getPositionY(),
                bird.getPositionY() + BirdDrawable.BIRD_TEXTURE_HEIGHT)
        );
    }

    private boolean isBirdOutOfScreen() {
        return bird.getPositionY() + BirdDrawable.BIRD_TEXTURE_HEIGHT > this.height || bird.getPositionY() < 0;
    }

    public boolean mouseClicked(Click click, boolean doubled) {
        return bird.mouseClicked();
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
//        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
//        TextureSetup textureSetup = TextureSetup.of(textureManager.getTexture(AbstractEndPortalBlockEntityRenderer.SKY_TEXTURE).getGlTextureView(),
//                textureManager.getTexture(AbstractEndPortalBlockEntityRenderer.PORTAL_TEXTURE).getGlTextureView());
//        context.fill(RenderPipelines.END_PORTAL, textureSetup, 0, 0, this.width, this.height);
        super.renderBackground(context, mouseX, mouseY, deltaTicks);
    }

}
