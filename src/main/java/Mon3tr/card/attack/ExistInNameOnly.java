package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.OtherEnum;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ExistInNameOnly extends AbstractMon3trCard {
    public static final String ID = "mon3tr:ExistInNameOnly";
    private static final CardStrings cardStrings;

    public ExistInNameOnly(){
        super(ID, cardStrings.NAME, 2, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        this.baseDamage = 9;
        this.damage = 9;
        this.baseMagicNumber = 3;
        this.magicNumber = 3;
        this.iterationCounter = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(true));
        addToBot(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn), OtherEnum.MON3TR_ATTACK_HEAVY_EFFECT));
        //addToBot(new ExistInNameOnlyAction(-1,m,new DamageInfo(p,this.damage,this.damageTypeForTurn), OtherEnum.MON3TR_ATTACK_HEAVY_EFFECT));
        for(int i =0;i<iterationCounter;i++){
            int bDamage = this.damage - magicNumber * (i+1);
            if(bDamage<0)
                bDamage = 0;
            TextAboveCreatureAction action = new TextAboveCreatureAction(m, StringHelper.actions.TEXT[0] + (i+1) + StringHelper.actions.TEXT[1]);
            ReflectionHacks.setPrivate(action, AbstractGameAction.class,"duration",0.05F);
            addToBot(action);
            addToBot(new DamageAction(m,new DamageInfo(p,bDamage,this.damageTypeForTurn), OtherEnum.MON3TR_ATTACK_HEAVY_EFFECT));
        }
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

