package Mon3tr.orbs;

import Mon3tr.effect.CountryIgniteEffect;
import Mon3tr.effect.CountrySnowEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;

public class CountryOrb {
    private BobEffect effect = new BobEffect(2.0F);
    private float activateTimer;
    public boolean activated = false;
    public boolean hidden = false;
    public boolean playedSfx = false;
    private Color color;
    private float x;
    private float y;
    private float particleTimer = 0.0F;
    private static final float PARTICLE_INTERVAL = 0.06F;
    private int index;

    public CountryOrb(float x, float y, int index) {
        this.x = x * Settings.scale + MathUtils.random(-10.0F, 10.0F) * Settings.scale;
        this.y = y * Settings.scale + MathUtils.random(-10.0F, 10.0F) * Settings.scale;
        this.activateTimer = (float)index * 0.2F;
        this.color = Color.BLACK.cpy();
        this.color.a = 0.0F;
        this.hidden = true;
        this.index = index;
    }

    public void activate(float oX, float oY) {
        this.playedSfx = false;
        this.activated = true;
        this.hidden = false;
        this.activateTimer = (float)index * 0.2F;
    }

    public void deactivate() {
        this.activated = false;
    }

    public void hide() {
        this.hidden = true;
    }

    public void update(float oX, float oY) {
        if (!this.hidden) {
            if (this.activated) {
                this.activateTimer -= Gdx.graphics.getDeltaTime();
                if (this.activateTimer < 0.0F) {
                    if (!this.playedSfx) {
                        this.playedSfx = true;
                        //AbstractDungeon.effectsQueue.add(new CountryIgniteEffect(this.x + oX, this.y + oY));
                    }

                    this.color.a = MathHelper.fadeLerpSnap(this.color.a, 1.0F);
                    this.effect.update();
                    this.effect.update();
                    this.particleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.particleTimer < 0.0F) {
                        AbstractDungeon.effectList.add(new CountrySnowEffect(this.x + oX + this.effect.y * 2.0F, this.y + oY + this.effect.y * 2.0F,4-index));
                        this.particleTimer = 0.06F;
                    }
                }
            } else {
                this.effect.update();
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    //AbstractDungeon.effectList.add(new GhostlyWeakFireEffect(this.x + oX + this.effect.y * 2.0F, this.y + oY + this.effect.y * 2.0F));
                    this.particleTimer = 0.06F;
                }
            }
        } else {
            this.color.a = MathHelper.fadeLerpSnap(this.color.a, 0.0F);
        }

    }
}
