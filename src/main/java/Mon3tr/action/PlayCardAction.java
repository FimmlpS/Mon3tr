package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayCardAction extends AbstractGameAction {
    private boolean purge = false;

    AbstractCard card;

    boolean freeToPlay=false;

    public PlayCardAction(AbstractCard card, AbstractCreature target, boolean purge) {
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.purge = purge;

    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (card==null) {
                this.isDone = true;
                return;
            }
            card.purgeOnUse = this.purge;
            AbstractDungeon.player.limbo.group.add(card);
            int dif = 0;
            card.current_x = (float)Settings.WIDTH / 2.0F + 180F * dif * Settings.xScale;
            card.current_y = (float)Settings.HEIGHT / 2.0F + 300F * Settings.scale;
            card.target_x = (float)Settings.WIDTH / 2.0F + 180F * dif * Settings.xScale;
            card.target_y = (float)Settings.HEIGHT / 2.0F + 300F * Settings.scale;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.32F;
            card.targetDrawScale = 0.75F;

            card.applyPowers();
            if(target==null||target.isDeadOrEscaped())
                target = AbstractDungeon.getRandomMonster();
            card.calculateCardDamage(target==null?null:(AbstractMonster) target);
            card.isInAutoplay = true;
            this.addToTop(new NewQueueCardAction(card, this.target, false, true));
            //this.addToTop(new UnlimboAction(card));
            this.addToTop(new WaitAction(0.1F));
            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }

            this.isDone = true;
        }

    }
}
