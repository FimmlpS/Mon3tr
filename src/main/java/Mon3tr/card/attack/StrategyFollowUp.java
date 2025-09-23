package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.action.StrategyFollowUpAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.helper.RightHitbox;
import Mon3tr.helper.StringHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StrategyFollowUp extends AbstractMon3trCard implements HitboxListener {
    public static final String ID = "mon3tr:StrategyFollowUp";
    private static final CardStrings cardStrings;

    public StrategyFollowUp(){
        this(true);
    }

    public StrategyFollowUp(boolean preview){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ALL_ENEMY);
        this.baseDamage = 12;
        this.damage = 12;
        this.isMultiDamage = true;
        RightHitbox rhb = new RightHitbox(-10000F,-10000F, IMG_WIDTH_S, IMG_HEIGHT_S);
        rhb.setListener(this);
        this.hb = rhb;
        if(preview){
            StrategyFollowUp preFocus = new StrategyFollowUp(false);
            preFocus.changeStrategy(false,true);
            this.cardsToPreview = preFocus;
        }
        isStrategyCard = true;
        canChangeStrategy = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(true));
        addToBot(new StrategyFollowUpAction(specialType,multiDamage,damageTypeForTurn));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(3);
            if(cardsToPreview!=null){
                cardsToPreview.upgrade();
            }
        }
    }

    public void changeStrategy(boolean flash,boolean changeNumber){
        if(specialType){
            specialType = false;
            if(changeNumber)
                this.baseDamage +=2;
            if(flash){
                this.flash(Color.BLUE.cpy());
            }
            loadCardImage(StringHelper.getCardIMGPatch(ID,CardType.ATTACK));
            name = cardStrings.NAME + (upgraded?"+":"");
            rawDescription = cardStrings.DESCRIPTION;
        }
        else{
            specialType = true;
            if(changeNumber)
                this.baseDamage -=2;
            if(flash){
                this.flash(Color.RED.cpy());
            }
            loadCardImage(StringHelper.getCardStrategyIMGPath(ID,CardType.ATTACK));
            name = cardStrings.EXTENDED_DESCRIPTION[0] + (upgraded?"+":"");
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        }
        initializeTitle();
        initializeDescription();
        if(cardsToPreview instanceof StrategyFollowUp){
            ((StrategyFollowUp) cardsToPreview).changeStrategy(false,true);
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


