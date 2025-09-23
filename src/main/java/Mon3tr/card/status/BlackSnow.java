package Mon3tr.card.status;

import Mon3tr.action.IterationDrawAction;
import Mon3tr.action.TakeTurnAction;
import Mon3tr.helper.StringHelper;
import Mon3tr.monster.Empgrd;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlackSnow extends CustomCard {
    public static final String ID = "mon3tr:BlackSnow";
    private static final CardStrings cardStrings;

    public BlackSnow() {
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID,CardType.STATUS), 0, cardStrings.DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerOnExhaust() {
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(mo instanceof Empgrd && !mo.isDeadOrEscaped()) {
                addToBot(new TakeTurnAction(mo));
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


