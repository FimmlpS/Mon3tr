package Mon3tr.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

public class ShowCardAndAttachEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.2F;
    private AbstractCard card;
    private AbstractCreature target;

    boolean half = false;

    public static final float OFFSET_Y = 100F* Settings.scale;


    public ShowCardAndAttachEffect(AbstractCard card, AbstractCreature target){
        this.card = card.makeStatEquivalentCopy();
        this.target = target;
        this.card.current_x = (float) Settings.WIDTH * 0.5F;
        this.card.current_y = (float) Settings.HEIGHT * 0.5F;
        this.card.target_x = target.hb.cX;
        this.card.target_y = target.hb.cY;

        this.duration = EFFECT_DUR;

        AbstractDungeon.effectList.add(new CardPoofEffect(this.card.target_x,this.card.target_y));
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 0.8F;

        CardCrawlGame.sound.play("CARD_OBTAIN");



    }

    public ShowCardAndAttachEffect(AbstractCard card, AbstractCreature target, boolean randomPosition){
        this.card = card.makeStatEquivalentCopy();
        this.target = target;
        this.card.current_x = (float) Settings.WIDTH * 0.5F;
        this.card.current_y = (float) Settings.HEIGHT * 0.5F;
        if(!randomPosition){
            this.card.target_x = target.hb.cX;
            this.card.target_y = target.hb.cY;
        }
        else{
            this.card.target_x = target.hb.cX + MathUtils.random(Settings.scale*-80F,Settings.scale*80F);
            this.card.target_y = target.hb.cY + MathUtils.random(Settings.scale*-80F,Settings.scale*80F);
        }

        this.duration = EFFECT_DUR;

        AbstractDungeon.effectList.add(new CardPoofEffect(this.card.target_x,this.card.target_y));
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 0.8F;

        CardCrawlGame.sound.play("CARD_OBTAIN");
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if(this.duration<0.8F&&!half){
            this.card.targetDrawScale = 0.01F;
            this.half = true;
        }

        if(this.duration< 0.0F){
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(!this.isDone){
            this.card.render(spriteBatch);
        }
    }

    @Override
    public void dispose() {

    }
}

