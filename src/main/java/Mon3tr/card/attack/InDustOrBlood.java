package Mon3tr.card.attack;

import Mon3tr.action.DustBloodAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.action.TakeStrengthAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InDustOrBlood extends AbstractMon3trCard {
    public static final String ID = "mon3tr:InDustOrBlood";
    private static final CardStrings cardStrings;

    public InDustOrBlood(){
        super(ID, cardStrings.NAME, 2, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        this.baseDamage = 12;
        this.damage =12;
        this.cardsToPreview = new MemoryFragment();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(true));
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
        addToBot(new DustBloodAction(m));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(4);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



