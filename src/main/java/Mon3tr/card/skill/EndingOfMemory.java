package Mon3tr.card.skill;

import Mon3tr.action.MemoryAction;
import Mon3tr.action.RandomDiscardToHandAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EndingOfMemory extends AbstractMon3trCard {
    public static final String ID = "mon3tr:EndingOfMemory";
    private static final CardStrings cardStrings;

    public EndingOfMemory(){
        super(ID, cardStrings.NAME,-2, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(dontTriggerOnUseCard){
            addToBot(new MemoryAction());
        }
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        if(isSide()){
            this.dontTriggerOnUseCard = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
        }
    }

    private boolean isSide(){
        boolean played = false;
        if(AbstractDungeon.player.hand.size()>0){
            if(AbstractDungeon.player.hand.getTopCard()==this)
                played = true;
            else if(upgraded && AbstractDungeon.player.hand.getBottomCard()==this)
                played = true;
        }
        return played;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void triggerOnGlowCheck() {
        if(isSide()){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
        else{
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




