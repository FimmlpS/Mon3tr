package Mon3tr.card.status;

import Mon3tr.action.Mon3trChangeStyleAction;
import Mon3tr.helper.RightHitbox;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.power.OverloadPower;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class StrategyOverload extends CustomCard implements HitboxListener {
    public static final String ID = "mon3tr:StrategyOverload";
    private static final CardStrings cardStrings;

    public StrategyOverload(){
        this(true);
    }

    public StrategyOverload(boolean preview){
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID, CardType.SKILL), -2, cardStrings.DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        RightHitbox rhb = new RightHitbox(-10000F,-10000F, IMG_WIDTH_S, IMG_HEIGHT_S);
        rhb.setListener(this);
        this.hb = rhb;
        this.purgeOnUse = true;
        if(preview)
            this.cardsToPreview = new StrategyMeltdown(false);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        //防回响
        if(!isInAutoplay&&!dontTriggerOnUseCard){
            addToBot(new VFXAction(new WhirlwindEffect(Color.RED.cpy(), true)));
            addToBot(new VFXAction(new WhirlwindEffect()));
            addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new OverloadPower(abstractPlayer,2),2));
            addToBot(new SkipEnemiesTurnAction());
            addToBot(new PressEndTurnButtonAction());
            addToBot(new Mon3trChangeStyleAction(2));
        }
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.isDraggingCard) {
            float tmpScale = this.drawScale * 0.7F;
            this.cardsToPreview.current_x = this.current_x;
            this.cardsToPreview.current_y = this.current_y + IMG_HEIGHT * 0.9F * this.drawScale;
            this.cardsToPreview.drawScale = tmpScale;
            this.cardsToPreview.render(sb);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(MeltdownPatch.meltDownCounter<40){
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return true;
    }

    @Override
    public void hoverStarted(Hitbox hitbox) {

    }

    @Override
    public void startClicking(Hitbox hitbox) {

    }

    @Override
    public void clicked(Hitbox hitbox) {
        if(hitbox instanceof RightHitbox){
            RightHitbox rhb = (RightHitbox) hitbox;
            if(rhb.rightClicked){
                MeltdownPatch.changeStrategy(this);
            }
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
