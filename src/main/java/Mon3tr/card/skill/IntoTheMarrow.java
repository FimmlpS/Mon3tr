package Mon3tr.card.skill;

import Mon3tr.action.IntoMarrowAction;
import Mon3tr.action.OneTimeAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IntoTheMarrow extends AbstractMon3trCard {
    public static final String ID = "mon3tr:IntoTheMarrow";
    private static final CardStrings cardStrings;
    boolean extended = false;
    private String rawDes;

    public IntoTheMarrow(){
        super(ID, cardStrings.NAME,2, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.COMMON,CardTarget.ENEMY);
        this.baseMagicNumber = 25;
        this.magicNumber = 25;
        this.purgeOnUse = true;
        this.tags.add(CardTags.HEALING);
        rawDes = cardStrings.DESCRIPTION;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new OneTimeAction(this));
        addToBot(new IntoMarrowAction(m,(float)this.magicNumber/100F));
    }

    private void setExtend(boolean extend){
        if(extended && !extend){
            extended = false;
            rawDescription = rawDes;
            initializeDescription();
        }
        else if(!extended && extend){
            extended = true;
            String tmp = rawDescription;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0]+  rawDes;
            rawDes = tmp;
            initializeDescription();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if(mo==null){
            setExtend(false);
        }
        else{
            int lose = (int)(mo.maxHealth * (float)this.magicNumber/100F);
            baseDamage = lose;
            damage = lose;
            isDamageModified = false;
            setExtend(true);
        }
    }

    @Override
    public void applyPowers() {
        setExtend(false);
        super.applyPowers();
    }

    @Override
    public void upgrade() {

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void onMoveToDiscard() {
        setExtend(false);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




