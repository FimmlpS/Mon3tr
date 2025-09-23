package Mon3tr.action;

import Mon3tr.character.Mon3tr;
import Mon3tr.modcore.Mon3trMod;
import Mon3tr.patch.MeltdownPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.FtueTip;

public class TutorialAction extends AbstractGameAction {
    public TutorialAction() {}

    private static final UIStrings uiStrings;

    @Override
    public void update() {
        if(AbstractDungeon.player instanceof Mon3tr && !MeltdownPatch.meltdownGroup.isEmpty()){
            if(!Mon3trMod.TutorialTriggered){
                float x = AbstractDungeon.player.hb.cX + 80F * Settings.scale;
                float y = AbstractDungeon.player.hb.cY;
                AbstractDungeon.ftue = new FtueTip(uiStrings.TEXT[0],uiStrings.TEXT[1],x,y,MeltdownPatch.meltdownGroup.getBottomCard());

                Mon3trMod.TutorialTriggered = true;
                try {
                    SpireConfig config = new SpireConfig("Mon3tr_FimmlpS","Common");
                    config.setBool("tutorialTriggered", true);
                    config.save();
                }
                catch (Exception e) {}
            }
        }
        this.isDone = true;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("mon3tr:Tutorial");
    }

}
