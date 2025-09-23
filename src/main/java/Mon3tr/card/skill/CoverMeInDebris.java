package Mon3tr.card.skill;

import Mon3tr.action.DebrisAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.helper.RightHitbox;
import Mon3tr.power.CrystalPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CoverMeInDebris extends AbstractMon3trCard implements HitboxListener {
    public static final String ID = "mon3tr:CoverMeInDebris";
    private static final CardStrings cardStrings;
    public static int DebrisID = 0;
    public int debrisID;

    public CoverMeInDebris(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseMagicNumber = 8;
        this.magicNumber = 8;
        this.exhaust = true;
        this.technicalNeeded = 8;

        this.debrisID = DebrisID;
        DebrisID++;

        RightHitbox rhb = new RightHitbox(-10000F,-10000F, IMG_WIDTH_S, IMG_HEIGHT_S);
        rhb.setListener(this);
        this.hb = rhb;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new CrystalPower(p,magicNumber),magicNumber));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(4);
        }
    }

    @Override
    public void clicked(Hitbox hitbox) {
        if(hitbox instanceof RightHitbox){
            RightHitbox rhb = (RightHitbox) hitbox;
            if(rhb.rightClicked){
                if(!inBattleAndInExhaust(true))
                    return;
                addToBot(new DebrisAction(this));
                if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW){
                    this.flash();
                    AbstractDungeon.closeCurrentScreen();
                }
            }
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard copy = super.makeStatEquivalentCopy();
        if(copy instanceof CoverMeInDebris){
            ((CoverMeInDebris) copy).debrisID = debrisID;
        }
        return copy;
    }

    @Override
    public void startClicking(Hitbox hitbox) {

    }

    @Override
    public void hoverStarted(Hitbox hitbox) {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}





