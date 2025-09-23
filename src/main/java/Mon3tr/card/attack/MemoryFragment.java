package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.skill.ThePastOne;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.OtherEnum;
import Mon3tr.power.CrystalPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MemoryFragment extends CustomCard {
    public static final String ID = "mon3tr:MemoryFragment";
    private static final CardStrings cardStrings;

    public MemoryFragment(){
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID,CardType.ATTACK),0, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.ENEMY);
        this.exhaust = true;
        this.baseDamage = 3;
        this.damage = 3;
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction());
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
        addToBot(new ApplyPowerAction(m,p,new CrystalPower(m,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        MemoryFragment memoryFragment = (MemoryFragment) super.makeStatEquivalentCopy();
        memoryFragment.retain = this.retain;
        return memoryFragment;
    }


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}





