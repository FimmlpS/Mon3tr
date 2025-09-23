package Mon3tr.card.attack;

import Mon3tr.action.CrackRandomAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import Mon3tr.power.PersonalityPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrackStrike extends AbstractMon3trCard {
    public static final String ID = "mon3tr:CrackStrike";
    private static final CardStrings cardStrings;

    public CrackStrike(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.COMMON,CardTarget.ALL_ENEMY);
        this.baseDamage = 8;
        this.damage = 8;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction());
        for(int i =0;i<2;i++){
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p,damage,damageTypeForTurn),OtherEnum.MON3TR_ATTACK_EFFECT));
        }
        addToBot(new ApplyPowerAction(p,p,new PersonalityPower(p,-2),-2));
//        addToBot(new CrackRandomAction(new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT,null));
//        addToBot(new CrackRandomAction(new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT,this));
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


