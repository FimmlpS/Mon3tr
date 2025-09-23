package Mon3tr.card.skill;

import Mon3tr.action.FocusAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.helper.RightHitbox;
import Mon3tr.helper.StringHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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

public class StrategyTree extends AbstractMon3trCard implements HitboxListener {
    public static final String ID = "mon3tr:StrategyTree";
    private static final CardStrings cardStrings;

    public StrategyTree(){
        this(true);
    }

    public StrategyTree(boolean preview){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
        RightHitbox rhb = new RightHitbox(-10000F,-10000F, IMG_WIDTH_S, IMG_HEIGHT_S);
        rhb.setListener(this);
        this.hb = rhb;
        if(preview){
            StrategyTree preTree = new StrategyTree(false);
            preTree.changeStrategy(false,true);
            this.cardsToPreview = preTree;
        }
        isStrategyCard = true;
        canChangeStrategy = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!specialType){
            addToBot(new DrawCardAction(this.magicNumber));
            addToBot(new ApplyPowerAction(p,p,new EnergizedPower(p,1),1));
        }
        else{
            addToBot(new GainEnergyAction(1));
            addToBot(new ApplyPowerAction(p,p,new DrawCardNextTurnPower(p,magicNumber),magicNumber));
        }
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

    public void changeStrategy(boolean flash,boolean changeNumber){
        if(specialType){
            specialType = false;
            if(flash){
                this.flash(Color.BLUE.cpy());
            }
            loadCardImage(StringHelper.getCardIMGPatch(ID,CardType.SKILL));
            name = cardStrings.NAME + (upgraded?"+":"");
            rawDescription = cardStrings.DESCRIPTION;
        }
        else{
            specialType = true;
            if(flash){
                this.flash(Color.RED.cpy());
            }
            loadCardImage(StringHelper.getCardStrategyIMGPath(ID,CardType.SKILL));
            name = cardStrings.EXTENDED_DESCRIPTION[0] + (upgraded?"+":"");
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        }
        initializeTitle();
        initializeDescription();
        if(cardsToPreview instanceof StrategyTree){
            ((StrategyTree) cardsToPreview).changeStrategy(false,true);
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
                changeStrategy(true,true);
                applyPowers();
            }
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}





