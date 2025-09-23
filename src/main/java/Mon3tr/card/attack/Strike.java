package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strike extends AbstractMon3trCard {
    public static final String ID = "mon3tr:Strike";
    private static final CardStrings cardStrings;

    public Strike(){
        super(ID, cardStrings.NAME, 1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.BASIC,CardTarget.ENEMY);
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTags.STRIKE);
        this.baseDamage = 6;
        this.damage =6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction());
        addToBot(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(3);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
