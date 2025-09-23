package Mon3tr.modcore;

import Mon3tr.helper.StringHelper;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.io.IOException;
import java.util.Properties;

public class ModConfig {
    private static final String ALL_USER_REFRACTOR = "mon3tr:ALL_USER_REFRACTOR";
    public static boolean allUseRefractor = false;
    public static SpireConfig config = null;
    private static Properties defaultSetting = new Properties();
    private static ModPanel settingsPanel;

    public static void initModSettings(){
        defaultSetting.setProperty(ALL_USER_REFRACTOR, String.valueOf(allUseRefractor));
        try {
            config = new SpireConfig("Mon3tr_FimmlpS","Common",defaultSetting);
            config.load();
            allUseRefractor = config.getBool(ALL_USER_REFRACTOR);
        }
        catch (Exception e){
            Mon3trMod.logSomething("Init Config Failed" + e.getLocalizedMessage());
        }
    }

    public static void initModConfigMenu(){
        settingsPanel = new ModPanel();
        addEnableMenu();
        UIStrings uiStrings = StringHelper.config;
        String modConfDesc = uiStrings.TEXT[1];
        Texture badge  = ImageMaster.loadImage("Mon3trResources/img/orbs/EnergyOrb.png");
        BaseMod.registerModBadge(badge,"mon3tr","FimmlpS",modConfDesc,settingsPanel);
    }

    private static void addEnableMenu(){
        UIStrings uiStrings = StringHelper.config;
        ModLabeledToggleButton btn = new ModLabeledToggleButton(uiStrings.TEXT[0],350F,650F, Settings.CREAM_COLOR, FontHelper.charDescFont,allUseRefractor,settingsPanel, modLabel -> {
        },modToggleButton -> {
            allUseRefractor = modToggleButton.enabled;
            config.setString(ALL_USER_REFRACTOR, String.valueOf(allUseRefractor));
            try {
                config.save();
            } catch (IOException e) {
                Mon3trMod.logSomething("save config credit failed" + e.getLocalizedMessage());
            }
        });

        settingsPanel.addUIElement(btn);
    }
}
