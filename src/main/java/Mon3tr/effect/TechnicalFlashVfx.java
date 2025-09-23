package Mon3tr.effect;

import Mon3tr.helper.ImageHelper;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.ui.TechnicUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class TechnicalFlashVfx extends AbstractGameEffect {
    private TextureAtlas.AtlasRegion img;
    private float yScale;
    public static float maxYScale = 1.05F;
    public static float minYScale = 0.8F;
    private TechnicUI ui;

    private Color outlineRColor;
    private Color outlineBColor;
    private Color outlineWColor;

    ArrayList<SingleVfx> singleVfxes = new ArrayList<>();

    public TechnicalFlashVfx(TechnicUI ui){
        this.duration = startingDuration = 0.9F;
        this.ui = ui;
        this.yScale = 0F;
        this.img = ImageHelper.skillPower_O;
        outlineBColor = new Color(127F/255F,143F/255F,250F/255F,1F);
        outlineRColor = new Color(191F/255F,113F/255F,94F/255F,1F);
        outlineWColor = Color.WHITE.cpy();
    }

    @Override
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        Iterator<SingleVfx> var = singleVfxes.iterator();
        while (var.hasNext()){
            SingleVfx singleVfx = var.next();
            singleVfx.update(delta);
            if(singleVfx.isDone){
                var.remove();
            }
        }
        duration -= delta;
        if(duration<0F){
            duration = startingDuration;
            if(MeltdownPatch.meltDownCounter>=40)
                singleVfxes.add(new SingleVfx(img,1.8F));
        }
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        if(MeltdownPatch.meltDownCounter >=80){
            this.color = outlineRColor;
        }
        else if(MeltdownPatch.meltDownCounter>=40){
            this.color = outlineBColor;
        }
        else {
            this.color = outlineWColor;
        }
        sb.setColor(this.color);
        for(SingleVfx singleVfx:singleVfxes){
            singleVfx.render(sb);
        }
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose() {

    }

    public class SingleVfx{
        private TextureAtlas.AtlasRegion img;

        public SingleVfx(TextureAtlas.AtlasRegion img, float dur){
            this.startDuration = dur;
            this.duration = dur;
            this.img = img;
            isDone = false;
        }

        public float alpha;
        public float startDuration;
        public float duration;
        public float yScale;
        public boolean isDone;

        public void update(float delta){
            duration -= delta;
            yScale = maxYScale + (minYScale-maxYScale) * (duration/startingDuration);
            alpha = this.duration/3.4F;
            if(duration<0F){
                isDone = true;
            }
        }

        public void render(SpriteBatch sb){
            Color c = sb.getColor();
            c.a = alpha;
            sb.setColor(c);
            sb.draw(this.img, ui.current_x + this.img.offsetX - (float)this.img.originalWidth / 2.0F, ui.current_y + this.img.offsetY - (float)this.img.originalHeight / 2.0F, (float)this.img.originalWidth / 2.0F - this.img.offsetX, (float)this.img.originalHeight / 2.0F - this.img.offsetY, (float)this.img.packedWidth, (float)this.img.packedHeight, 1F * (this.yScale + 0.9F) * 0.6F * Settings.scale, 1F * (this.yScale + 0.9F) * 0.6F * Settings.scale, 0F);
        }
    }
}
