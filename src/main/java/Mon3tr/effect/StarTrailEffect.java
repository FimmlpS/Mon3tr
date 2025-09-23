package Mon3tr.effect;

import Mon3tr.patch.MeltdownPatch;
import Mon3tr.ui.Star;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Pool;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class StarTrailEffect extends AbstractGameEffect implements Pool.Poolable {
    private static final float EFFECT_DUR = 0.5F;
    private static final float DUR_DIV_2 = 0.25F;
    private static TextureAtlas.AtlasRegion img = null;
    private static final int W = 12;
    private static final int W_DIV_2 = 6;
    private static final float SCALE_MULTI;
    private float x;
    private float y;

    boolean drop = false;

    public StarTrailEffect() {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/blurDot2");
        }

        this.renderBehind = false;
    }

    public void init(float x,float y,boolean drop) {
        this.duration = 0.5F;
        this.startingDuration = 0.5F;
        this.x = x - 6.0F;
        this.y = y - 6.0F;
        if (!drop) {
            if (MeltdownPatch.meltdownType == MeltdownPatch.MeltdownType.Meltdown) {
                this.color = new Color(191F / 255F, 113F / 255F, 94F / 255F, 0.6F);
            } else if (MeltdownPatch.meltdownType == MeltdownPatch.MeltdownType.Overload) {
                this.color = new Color(127F / 255F, 143F / 255F, 250F / 255F, 0.6F);
            } else {
                this.color = new Color(1F, 1F, 1F, 0.6F);
            }
        }
        else {
            this.color = new Color(207F / 255F, 252F / 255F, 89F / 255F, 0.8F);
        }
        this.drop = drop;
        this.scale = 0.01F;
        this.isDone = false;
    }

    public void init(float x, float y) {
       this.init(x,y,false);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.25F) {
            this.scale = this.duration * SCALE_MULTI * (drop?2F:1F);
        } else {
            this.scale = (this.duration - 0.25F) * SCALE_MULTI * (drop?2F:1F);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            Star.trailEffectPool.free(this);
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 0.16F, this.duration / 0.5F);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(img, this.x, this.y, 6.0F, 6.0F, 12.0F, 12.0F, this.scale, this.scale, 0.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }

    public void reset() {
    }

    static {
        SCALE_MULTI = Settings.scale * 6.0F;
    }
}

