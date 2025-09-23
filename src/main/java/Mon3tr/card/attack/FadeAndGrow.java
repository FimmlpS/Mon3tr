package Mon3tr.card.attack;

import Mon3tr.action.FadeAndGrowAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class FadeAndGrow extends AbstractMon3trCard {
    public static final String ID = "mon3tr:FadeAndGrow";
    private static final CardStrings cardStrings;

    public FadeAndGrow(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        this.baseDamage = 5;
        this.damage = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(false));
        addToBot(new VFXAction(new LightningEffect(m.hb.cX, m.hb.cY)));
        addToBot(new SFXAction("ORB_LIGHTNING_EVOKE", 0.4F));
        addToBot(new SFXAction("ORB_LIGHTNING_PASSIVE", -0.2F));
        addToBot(new FadeAndGrowAction(m,new DamageInfo(p,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




