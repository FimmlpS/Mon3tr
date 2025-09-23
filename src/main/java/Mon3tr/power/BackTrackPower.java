package Mon3tr.power;

import Mon3tr.action.BackTrackRecordAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class BackTrackPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:BackTrackPower";
    private static final PowerStrings powerStrings;

    public ArrayList<AbstractCard> record = new ArrayList<>();

    public BackTrackPower(AbstractCreature owner) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/BackTrackPower_84.png";
        String path48 = "Mon3trResources/img/powers/BackTrackPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 99;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
        for (AbstractCard c : record) {
            this.description += (c.name + " ");
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL) {
            addToBot(new BackTrackRecordAction(this,card));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}




