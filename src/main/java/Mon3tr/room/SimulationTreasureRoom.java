package Mon3tr.room;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;

public class SimulationTreasureRoom extends TreasureRoomBoss {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public SimulationTreasureRoom() {
        super();
    }

    public void onPlayerEntry() {
        CardCrawlGame.music.silenceBGM();
        if (AbstractDungeon.actNum < 4 || !AbstractPlayer.customMods.contains("Blight Chests")) {
            AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
        }

        this.playBGM("SHRINE");
        this.chest = new SimulationBossChest();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("TreasureRoomBoss");
        TEXT = uiStrings.TEXT;
    }
}

