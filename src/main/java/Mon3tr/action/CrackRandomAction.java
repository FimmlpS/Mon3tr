package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class CrackRandomAction extends AbstractGameAction {
    private DamageInfo info;
    public static ArrayList<AbstractCreature> targets = new ArrayList<>();

    public CrackRandomAction(DamageInfo info, AttackEffect effect, AbstractCard card) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        if(card==null)
            targets.clear();
        else {
            this.card = card;
        }
    }

    AbstractCard card;

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            targets.add(target);
            if(card!=null){
                if(targets.size()==2 && targets.get(0)==targets.get(1)){
                    this.addToBot(new ExhaustSpecificCardAction(card,AbstractDungeon.player.hand));
                    this.addToBot(new ExhaustSpecificCardAction(card,AbstractDungeon.player.discardPile));
                    card.flash();
                }
            }
            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
        }

        this.isDone = true;
    }
}
