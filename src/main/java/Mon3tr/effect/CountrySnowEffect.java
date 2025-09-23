package Mon3tr.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CountrySnowEffect extends AbstractGameEffect {
    private TextureAtlas.AtlasRegion img = this.getImg();
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float baseSize = 1F;
    private static final float DUR = 1.0F;

    public CountrySnowEffect(float x, float y ,float baseSize) {
        this(x,y,baseSize, new Color(0.25F,0.25F,0.25F,1F),1F,1F);
    }

    public CountrySnowEffect(float x, float y ,float baseSize,Color aimColor,float vXSize,float vYSize) {
        if(baseSize < 0)
            baseSize = 0F;
        this.baseSize =baseSize+1F;
        this.x = x + MathUtils.random(-2.0F, 2.0F) * Settings.scale - (float)this.img.packedWidth / 2.0F;
        this.y = y + MathUtils.random(-2.0F, 2.0F) * Settings.scale - (float)this.img.packedHeight / 2.0F;
        this.vX = MathUtils.random(-10.0F*vXSize, 10.0F*vXSize) * Settings.scale;
        this.vY = MathUtils.random(20.0F, 20F+130F*vYSize) * Settings.scale;
        this.duration = 1.0F;
        this.color = aimColor;
        this.color.a = 0.0F;

        this.scale = Settings.scale * MathUtils.random(baseSize+1F, (baseSize+1F)*1.2F);
    }

    private TextureAtlas.AtlasRegion getImg() {
        switch(MathUtils.random(0, 2)) {
            case 0:
                return ImageMaster.TORCH_FIRE_1;
            case 1:
                return ImageMaster.TORCH_FIRE_2;
            default:
                return ImageMaster.TORCH_FIRE_3;
        }
    }

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        if (this.scale > 0.1F) {
            this.scale -= Gdx.graphics.getDeltaTime() / baseSize;
        }

        this.color.a = this.duration / 2.0F;
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * MathUtils.random(0.95F, 1.05F), this.scale * MathUtils.random(0.95F, 1.05F), this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}


