package Mon3tr.power;

import Mon3tr.action.CanTriggerTurbulentAction;
import Mon3tr.action.TriggerTurbulentAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TurbulentFlowPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:TurbulentFlowPower";
    private static final PowerStrings powerStrings;
    public boolean triggered = false;

    public TurbulentFlowPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/TurbulentFlowPower_84.png";
        String path48 = "Mon3trResources/img/powers/TurbulentFlowPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 15;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+ this.amount+ powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new CanTriggerTurbulentAction(this));
    }

    @Override
    public void onDrawOrDiscard() {
        if(!triggered && !AbstractDungeon.actionManager.turnHasEnded){
            addToBot(new TriggerTurbulentAction(this));
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(!triggered && !AbstractDungeon.actionManager.turnHasEnded){
            addToBot(new TriggerTurbulentAction(this));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}



