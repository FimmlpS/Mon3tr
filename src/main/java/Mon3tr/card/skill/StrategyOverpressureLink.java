package Mon3tr.card.skill;

import Mon3tr.action.TryChangeStrategyAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.attack.StrategyFollowUp;
import Mon3tr.helper.RightHitbox;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;

public class StrategyOverpressureLink extends AbstractMon3trCard implements HitboxListener {
    public static final String ID = "mon3tr:StrategyOverpressureLink";
    private static final CardStrings cardStrings;

    public StrategyOverpressureLink(){
        this(true);
    }

    public StrategyOverpressureLink(boolean preview){
        super(ID, cardStrings.NAME, -2, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.RARE,CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
        this.selfRetain = true;
        RightHitbox rhb = new RightHitbox(-10000F,-10000F, IMG_WIDTH_S, IMG_HEIGHT_S);
        rhb.setListener(this);
        this.hb = rhb;
        if(preview){
            StrategyOverpressureLink preOver = new StrategyOverpressureLink(false);
            preOver.changeStrategy(false,true);
            this.cardsToPreview = preOver;
        }
        isStrategyCard = true;
        canChangeStrategy = true;
        dontSetStrategyDescription = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        if(!specialType){
//            addToBot(new DrawCardAction(magicNumber));
//            addToBot(new GainEnergyAction(2));
//        }
//        else{
//            addToBot(new ApplyPowerAction(p,p,new DrawCardNextTurnPower(p,magicNumber),magicNumber));
//            addToBot(new ApplyPowerAction(p,p,new EnergizedPower(p,2),2));
//        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(1);
            if(cardsToPreview!=null){
                cardsToPreview.upgrade();
            }
        }
    }

    public void changeStrategy(boolean flash, boolean changeNumber){
        if(specialType){
            specialType = false;
            if(changeNumber){
                this.baseMagicNumber -= 1;
                this.magicNumber = baseMagicNumber;
            }
            if(flash){
                this.flash(Color.BLUE.cpy());
            }
            name = cardStrings.NAME + (upgraded?"+":"");
            rawDescription = cardStrings.DESCRIPTION;
        }
        else{
            specialType = true;
            if(changeNumber){
                this.baseMagicNumber += 1;
                this.magicNumber = baseMagicNumber;
            }
            if(flash){
                this.flash(Color.RED.cpy());
            }
            name = cardStrings.EXTENDED_DESCRIPTION[0] + (upgraded?"+":"");
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        }
        initializeTitle();
        initializeDescription();
        if(cardsToPreview instanceof StrategyOverpressureLink){
            ((StrategyOverpressureLink) cardsToPreview).changeStrategy(false,true);
        }
        if(!inBattleAndInHand())
            resetAttributes();
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
                if(!inBattleAndInHand())
                    return;
                //得在自己回合
                if(AbstractDungeon.actionManager.turnHasEnded)
                    return;
                addToBot(new TryChangeStrategyAction(this));
            }
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



