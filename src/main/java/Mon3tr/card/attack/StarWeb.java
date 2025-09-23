package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.action.StarWebAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StarWeb extends AbstractMon3trCard {
    public static final String ID = "mon3tr:StarWeb";
    private static final CardStrings cardStrings;

    public StarWeb(){
        super(ID, cardStrings.NAME, 0, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        this.baseDamage = 4;
        this.damage = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if((!isInAutoplay)||(MeltdownPatch.meltdownType == MeltdownPatch.MeltdownType.Meltdown))
            addToBot(new Mon3trAttackAction(true));
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
        addToBot(new StarWebAction(this));
    }

    @Override
    public void triggerWhenDrawn() {
        this.flash();
        addToBot(new MakeTempCardInDrawPileAction(this,1,true,true));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



