package Mon3tr.action;

import Mon3tr.helper.StringHelper;
import Mon3tr.modifier.MissingMod;
import Mon3tr.patch.Mon3trTag;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class EternityMissesPresentAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final float DURATION;

    public EternityMissesPresentAction() {
        this.p = AbstractDungeon.player;
        this.setValues(null, null, 1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
    }

    ArrayList<AbstractCard> removed = new ArrayList<>();

    private void returnCards(){
        AbstractDungeon.player.hand.group.addAll(removed);
        removed.clear();
    }

    private void markLoved(AbstractCard c){
        CardModifierManager.addModifier(c,new MissingMod());
    }


    public void update() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.hasTag(Mon3trTag.MISSING_MON3TR)) {
                    removed.add(c);
                }
            }
            if (removed.size() == AbstractDungeon.player.hand.size()) {
                this.isDone = true;
                return;
            }

            for (AbstractCard c : removed) {
                AbstractDungeon.player.hand.group.remove(c);
            }
            AbstractDungeon.handCardSelectScreen.open(StringHelper.actions.TEXT[7], this.amount, false, false);

            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                markLoved(c);
                AbstractDungeon.player.hand.group.add(c);
            }
            returnCards();
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}

