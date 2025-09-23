package Mon3tr.events;

import Mon3tr.patch.CrystalPatch;
import Mon3tr.relic.M3HJ;
import Mon3tr.relic.M3LZ;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class GarrisonAgreement extends AbstractImageEvent {
    public static final String ID = "mon3tr:GarrisonAgreement";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;

    boolean selected = false;
    int currentOption;

    public GarrisonAgreement() {
        super(NAME, DESCRIPTIONS[0], "Mon3trResources/img/events/GarrisonAgreement.png");
        boolean lz = AbstractDungeon.player.hasRelic(M3LZ.ID);
        boolean hj = AbstractDungeon.player.hasRelic(M3HJ.ID);
        if (lz && !hj) {
            currentOption = 0;
            imageEventText.setDialogOption(OPTIONS[0],new M3HJ());
        } else if (hj && !lz) {
            currentOption = 1;
            imageEventText.setDialogOption(OPTIONS[1],new M3LZ());
        } else if (lz) {
            currentOption = 2;
            imageEventText.setDialogOption(OPTIONS[2]);
        } else {
            currentOption = 3;
            imageEventText.setDialogOption(OPTIONS[3]);
        }
    }

    @Override
    protected void buttonEffect(int i) {
        if(!selected){
            switch (currentOption) {
                case 0:
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,new M3HJ());
                    AbstractDungeon.uncommonRelicPool.remove(M3HJ.ID);
                    break;
                case 1:
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,new M3LZ());
                    AbstractDungeon.uncommonRelicPool.remove(M3LZ.ID);
                    break;
                case 2:
                    for(int i2 =0;i2<30;i2++)
                        CrystalPatch.starGroup.onDropCrystal(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,150F*Settings.scale,150F*Settings.scale);
                    break;
                case 3:
                    boolean lz = AbstractDungeon.relicRng.randomBoolean();
                    if(lz){
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,new M3LZ());
                        AbstractDungeon.uncommonRelicPool.remove(M3LZ.ID);
                    }
                    else {
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,new M3HJ());
                        AbstractDungeon.uncommonRelicPool.remove(M3HJ.ID);
                    }
                    break;
            }
            imageEventText.clearAllDialogs();
            imageEventText.updateBodyText(DESCRIPTIONS[1]);
            imageEventText.setDialogOption(OPTIONS[4]);
            selected = true;
        }
        else{
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

