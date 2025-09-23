package Mon3tr.events;

import Mon3tr.patch.CrystalPatch;
import Mon3tr.relic.Mon2tr;
import Mon3tr.relic.Mon3trR;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class ACloth extends AbstractImageEvent {
    public static final String ID = "mon3tr:ACloth";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;

    boolean selected = false;
    int currentOption;

    public ACloth() {
        super(NAME, DESCRIPTIONS[0], "Mon3trResources/img/events/ACloth.png");
        boolean lz = AbstractDungeon.player.hasRelic(Mon3trR.ID);
        boolean hj = AbstractDungeon.player.hasRelic(Mon2tr.ID);
        if (lz && !hj) {
            currentOption = 0;
            imageEventText.setDialogOption(OPTIONS[0],new Mon2tr());
        } else if (hj && !lz) {
            currentOption = 1;
            imageEventText.setDialogOption(OPTIONS[1],new Mon3trR());
        } else if (lz) {
            currentOption = 2;
            imageEventText.setDialogOption(OPTIONS[2]);
        } else {
            currentOption = 3;
            imageEventText.setDialogOption(OPTIONS[3],true);
        }
        imageEventText.setDialogOption(OPTIONS[4]);
    }

    @Override
    protected void buttonEffect(int i) {
        if (!selected) {
            if (i == 0)
                switch (currentOption) {
                    case 0:
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new Mon2tr());
                        break;
                    case 1:
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new Mon3trR());
                        break;
                    case 2:
                        int effectCount = 0;
                        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                            if (c.canUpgrade()) {
                                ++effectCount;
                                if (effectCount <= 20) {
                                    float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                                    float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                                }

                                c.upgrade();
                                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                            }
                        }
                        break;
                }
            else if (i == 1) {
                for (int i2 = 0; i2 < 10; i2++)
                    CrystalPatch.starGroup.onDropCrystal(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, 150F * Settings.scale, 150F * Settings.scale);
            }
            imageEventText.clearAllDialogs();
            imageEventText.updateBodyText(DESCRIPTIONS[1]);
            imageEventText.setDialogOption(OPTIONS[5]);
            selected = true;
        } else {
            openMap();
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        }
    }


    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
}


