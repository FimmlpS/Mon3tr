package Mon3tr.card.skill;

import Mon3tr.action.FocusAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.attack.WeaveBlock;
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

public class StrategyFocus extends AbstractMon3trCard implements HitboxListener {
    public static final String ID = "mon3tr:StrategyFocus";
    private static final CardStrings cardStrings;

    public StrategyFocus(){
        this(true);
    }

    public StrategyFocus(boolean preview){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
        RightHitbox rhb = new RightHitbox(-10000F,-10000F, IMG_WIDTH_S, IMG_HEIGHT_S);
        rhb.setListener(this);
        this.hb = rhb;
        if(preview){
            StrategyFocus preFocus = new StrategyFocus(false);
            preFocus.changeStrategy(false,true);
            this.cardsToPreview = preFocus;
        }
        isStrategyCard = true;
        canChangeStrategy = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int extra = specialType?(magicNumber):(upgraded?1:0);
        addToBot(new FocusAction(specialType,extra));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            if(!specialType){
                rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                initializeDescription();
            }
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
            rawDescription = upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION;
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
        if(cardsToPreview instanceof StrategyFocus){
            ((StrategyFocus) cardsToPreview).changeStrategy(false,true);
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




