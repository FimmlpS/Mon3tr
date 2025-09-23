package Mon3tr.card.status;

import Mon3tr.helper.StringHelper;
import Mon3tr.power.PersonalityPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PersonFalse extends CustomCard {
    public static final String ID = "mon3tr:PersonFalse";
    private static final CardStrings cardStrings;

    public PersonFalse() {
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID,CardType.STATUS), -2, cardStrings.DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void onChoseThisOption() {
        AbstractPower p = AbstractDungeon.player.getPower(PersonalityPower.POWER_ID);
        if(p instanceof PersonalityPower) {
            ((PersonalityPower) p).setReverse(false);
        }
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new PersonalityPower(AbstractDungeon.player,-1),-1));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    public void upgrade() {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




