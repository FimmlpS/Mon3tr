package Mon3tr.effect;

import Mon3tr.helper.ImageHelper;
import Mon3tr.patch.MeltdownPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Mon3trFlashAtkImgEffect extends AbstractGameEffect {
    public TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float xScale;
    private float yScale;
    float rotation1;
    float rotation2;
    boolean heavy;

    public Mon3trFlashAtkImgEffect(float x, float y,boolean heavy) {
        this.duration = 0.5F; // 0.2 appear 0.1last 0.2 disappear
        this.startingDuration = 0.6F;
        if(MeltdownPatch.meltdownType == MeltdownPatch.MeltdownType.Meltdown)
            this.img = ImageHelper.attackHit;
        else
            this.img = ImageHelper.attackHit2;

        this.x = x - (float) this.img.packedWidth / 2.0F;
        this.y = y - (float) this.img.packedHeight / 2.0F;
        color = Color.WHITE.cpy();
        scale = Settings.scale;
        xScale = 0F;
        yScale = 0F;
        rotation1 = 40.0F + MathUtils.random(-15.0F, 15.0F);
        rotation2 = rotation1 + 90F;
        this.heavy = heavy;
        //playSound
        if(!heavy)
            CardCrawlGame.sound.play("ATTACK_FAST");
        else
            CardCrawlGame.sound.play("ATTACK_HEAVY");
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < this.startingDuration /8.0F) {
            this.color.a = this.duration / (this.startingDuration / 8.0F);
        }
        else if(this.duration > this.startingDuration * 7F/8F) {
            this.color.a = (startingDuration-duration) / (this.startingDuration / 8.0F);
        }

        if(duration > this.startingDuration *3F/5F) {
            xScale = scale * (startingDuration-duration) / (this.startingDuration*2F / 5F);
            yScale = scale * (startingDuration-duration) / (this.startingDuration*2F / 5F);
        }
        else if(duration < this.startingDuration *2F/5F) {
            xScale = scale;
            yScale = scale * duration / (this.startingDuration *2f/ 5F);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            this.color.a = 0.0F;
            xScale = scale;
            yScale = 0F;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(this.img!=null){
            sb.setColor(this.color);
            sb.draw(img,x,y,img.packedWidth/2F,img.packedHeight/2F,img.packedWidth,img.packedHeight,xScale,yScale,rotation1);
            sb.draw(img,x,y,img.packedWidth/2F,img.packedHeight/2F,img.packedWidth,img.packedHeight,xScale,yScale,rotation2);
            if(heavy){
                sb.draw(img,x + 20F * Settings.scale,y,img.packedWidth/2F,img.packedHeight/2F,img.packedWidth,img.packedHeight,xScale,yScale,rotation1);
                sb.draw(img,x + 20F * Settings.scale,y,img.packedWidth/2F,img.packedHeight/2F,img.packedWidth,img.packedHeight,xScale,yScale,rotation2);
            }
        }
    }

    @Override
    public void dispose() {

    }
}
