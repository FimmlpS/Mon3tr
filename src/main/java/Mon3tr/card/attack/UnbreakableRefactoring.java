package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.action.RefactorAction;
import Mon3tr.action.SVFXAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.effect.PowerFlashEffect;
import Mon3tr.helper.RightHitbox;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UnbreakableRefactoring extends AbstractMon3trCard implements HitboxListener {
    public static final String ID = "mon3tr:UnbreakableRefactoring";
    private static final CardStrings cardStrings;

    public UnbreakableRefactoring(){
        super(ID, cardStrings.NAME ,2, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.RARE,CardTarget.ALL_ENEMY);
        this.baseDamage = 16;
        this.damage = 16;
        this.baseMagicNumber = 6;
        this.magicNumber = 6;
        this.technicalNeeded = 12;
        this.isMultiDamage = true;

        RightHitbox rhb = new RightHitbox(-10000F,-10000F, IMG_WIDTH_S, IMG_HEIGHT_S);
        rhb.setListener(this);
        this.hb = rhb;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(true));
        addToBot(new SVFXAction("MON3TR_UNBREAKABLE",0.2F,0.7F));
        for (AbstractMonster m1 : AbstractDungeon.getMonsters().monsters) {
            if (m1 != null && !m1.isDeadOrEscaped()) {
                if (Settings.FAST_MODE) {
                    this.addToBot(new VFXAction(new PowerFlashEffect(m1.hb.cX, m1.hb.cY,"Mon3trResources/img/powers/MonsterPower_84.png")));
                } else {
                    this.addToBot(new VFXAction(new PowerFlashEffect(m1.hb.cX, m1.hb.cY,"Mon3trResources/img/powers/MonsterPower_84.png"), 0.2F));
                }
            }
        }
        addToBot(new DamageAllEnemiesAction(p,multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(4);
            upgradeMagicNumber(2);
        }
    }

    @Override
    public void clicked(Hitbox hitbox) {
        if(hitbox instanceof RightHitbox){
            RightHitbox rhb = (RightHitbox) hitbox;
            if(rhb.rightClicked){
                if(!inBattleAndInDiscard(true))
                    return;
                addToBot(new RefactorAction(this));
                if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.DISCARD_VIEW){
                    this.flash();
                    AbstractDungeon.closeCurrentScreen();
                }
            }
        }
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
