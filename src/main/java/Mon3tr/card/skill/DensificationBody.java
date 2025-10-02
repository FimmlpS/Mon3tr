package Mon3tr.card.skill;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import Mon3tr.power.CrystalPower;
import Mon3tr.power.PersonalityPower;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DensificationBody extends AbstractMon3trCard {
    public static final String ID = "mon3tr:DensificationBody";
    private static final CardStrings cardStrings;

    public DensificationBody(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseBlock = 7;
        this.block = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        AbstractPower per = p.getPower(PersonalityPower.POWER_ID);
        if(per!=null && per.amount<0){
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(block,true), DamageInfo.DamageType.THORNS, OtherEnum.MON3TR_ATTACK_EFFECT));
        }
    }

    @Override
    protected void applyPowersToBlock() {
        int tmp = baseBlock;
        AbstractPower cry = AbstractDungeon.player.getPower(CrystalPower.POWER_ID);
        if(cry != null && cry.amount>0) {
            baseBlock += cry.amount;
        }
        super.applyPowersToBlock();
        baseBlock = tmp;
        isBlockModified = baseBlock!=block;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBlock(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




