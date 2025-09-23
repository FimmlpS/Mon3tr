package Mon3tr.card.skill;

import Mon3tr.action.LoseAllStrengthAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class MakeNoAllowance extends AbstractMon3trCard {
    public static final String ID = "mon3tr:MakeNoAllowance";
    private static final CardStrings cardStrings;

    public MakeNoAllowance(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.ENEMY);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseAllStrengthAction(m));
    }

    @Override
    public void triggerWhenDrawn() {
        this.flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,magicNumber),magicNumber));
        for(AbstractMonster m :AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                addToBot(new ApplyPowerAction(m,m,new StrengthPower(m,magicNumber),magicNumber));
            }
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



