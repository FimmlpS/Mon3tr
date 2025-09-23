package Mon3tr.card.attack;

import Mon3tr.action.ExhaustTechnicalAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class CrippleStrike extends AbstractMon3trCard {
    public static final String ID = "mon3tr:CrippleStrike";
    private static final CardStrings cardStrings;

    public CrippleStrike(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        this.technicalNeeded = 8;
        this.baseDamage = 10;
        this.damage = 10;
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!isInAutoplay)
            addToBot(new ExhaustTechnicalAction(technicalNeeded));
        addToBot(new Mon3trAttackAction());
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
        addToBot(new ApplyPowerAction(m,m,new VulnerablePower(m,magicNumber,false),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(!MeltdownPatch.isMeltdownTurn() && !isInAutoplay && MeltdownPatch.meltDownCounter<technicalNeeded){
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0]+technicalNeeded+cardStrings.EXTENDED_DESCRIPTION[1];
            return false;
        }
        return super.canUse(p, m);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



