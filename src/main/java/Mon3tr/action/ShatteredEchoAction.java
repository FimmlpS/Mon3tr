package Mon3tr.action;

import Mon3tr.card.attack.MemoryFragment;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShatteredEchoAction extends AbstractGameAction {
    public ShatteredEchoAction(AbstractCreature target, int amt, boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amt;
        this.upgraded = upgraded;
        this.target = target;
    }

    boolean upgraded = false;

    @Override
    public void update() {
        if(amount>0&&target!=null){
            float padding = 0.65F* AbstractCard.IMG_WIDTH;
            for(int i = 0;i<amount;i++){
                AbstractCard cardPlayed = new MemoryFragment();
                if(upgraded)
                    cardPlayed.upgrade();
                boolean left = i%2==0;
                AbstractDungeon.player.limbo.group.add(cardPlayed);
                cardPlayed.purgeOnUse = true;
                cardPlayed.current_x = (float)Settings.WIDTH / 2.0F;
                cardPlayed.current_y = (float)Settings.HEIGHT * 1.15F;
                int index = (i+1)/2;
                cardPlayed.target_x = Settings.WIDTH/2F + (left?-1:1) * padding * index;
                cardPlayed.target_y = 0.7F * Settings.HEIGHT;
                cardPlayed.targetAngle = 0.0F;
                cardPlayed.lighten(false);
                cardPlayed.drawScale = 0.6F;
                cardPlayed.targetDrawScale = 0.6F;
                cardPlayed.applyPowers();
                this.addToTop(new NewQueueCardAction(cardPlayed, target,false, true));
            }
            addToTop(new WaitAction(0.1F));
        }
        this.isDone = true;
    }
}
