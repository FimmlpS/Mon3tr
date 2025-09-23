package Mon3tr.ui;

import Mon3tr.character.Mon3tr;
import Mon3tr.helper.ImageHelper;
import Mon3tr.helper.StringHelper;
import Mon3tr.modcore.Mon3trMod;
import Mon3tr.patch.ClassEnum;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import java.io.IOException;
import java.util.ArrayList;

public class CharSelectScreen implements ISubscriber, CustomSavable<Integer> {
    public static CharSelectScreen Inst;
    public Hitbox leftHb;
    public Hitbox rightHb;
    public Hitbox mode01Hb;
    public Hitbox mode02Hb;
    public String curName = "";
    public String nextName = "";
    public int index = 0;
    private static final ArrayList<Char> chars;
    private static final UIStrings uiStrings;
    private static final UIStrings modeStrings;

    public boolean hardMode01 = false;
    public boolean hardMode02 = false;

    private static float MODE_TEXT_W;

    public static boolean hardModeEnable(int index) {
        if (Inst != null) {
            if (index == 1)
                return Inst.hardMode01;
            if (index == 2)
                return Inst.hardMode02;
        }
        return false;
    }

    public static Char getChar(){
        if(Inst==null){
            return chars.get(0);
        }
        return chars.get(Inst.index);
    }

    public void Initialize(){
        int i = Mon3trMod.DefaultCharIndex;
        if(index != i && i>=0){
            index = i;
        }
        refresh();

        hardMode01 = Mon3trMod.HardModeEnable01;
        hardMode02 = Mon3trMod.HardModeEnable02;
    }

    public CharSelectScreen(){
        this.index = 0;
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        float centerX = (float)Settings.WIDTH * 0.55F;
        float centerY = (float)Settings.HEIGHT * 0.75F;
        this.leftHb.move(centerX - 200.0F * Settings.scale, centerY);
        this.rightHb.move(centerX + 200.0F * Settings.scale, centerY);

        //mode
        this.hardMode01 = false;
        this.hardMode02 = false;
        MODE_TEXT_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, modeStrings.TEXT[0],9999.0F, 0.0F);
        this.mode01Hb = new Hitbox(MODE_TEXT_W + 100.0F * Settings.scale, 70.0F * Settings.scale);
        this.mode01Hb.move(centerX + 360F * Settings.scale, centerY + 70F * Settings.scale);
        this.mode02Hb = new Hitbox(MODE_TEXT_W + 100.0F * Settings.scale, 70.0F * Settings.scale);
        this.mode02Hb.move(centerX + 360F * Settings.scale, centerY);

        BaseMod.subscribe(this);

        BaseMod.addSaveField("mon3tr_char", this);
        Initialize();
    }

    public void refresh(){
        Mon3trMod.DefaultCharIndex = this.index;
        Char c = chars.get(index);
        this.curName = c.name;
        this.nextName = chars.get(nextIndex()).name;
        if(CardCrawlGame.mainMenuScreen!=null){
            CharacterSelectScreen css = CardCrawlGame.mainMenuScreen.charSelectScreen;
            if(css!=null){
                for(CharacterOption option:css.options){
                    if (option.c instanceof Mon3tr){
                        Mon3tr m = (Mon3tr) option.c;
                        String newPath = ImageHelper.getCharSelectBg();
                        BaseMod.playerPortraitMap.put(ClassEnum.Mon3tr_CLASS,newPath);
                        Texture t = ImageMaster.loadImage(newPath);
                        ReflectionHacks.setPrivate(option,CharacterOption.class,"portraitImg",t);
                        css.bgCharImg = t;
                        ReflectionHacks.setPrivate(option,CharacterOption.class,"flavorText",m.getDescriptionSelect());
                        CharSelectInfo info = ReflectionHacks.getPrivate(option,CharacterOption.class,"charInfo");
                        if(info!=null){
                            info.relics = m.getStartingRelics();
                            info.deck = m.getStartingDeck();
                        }
                        break;
                    }
                }
            }
        }
    }

    public void refreshMode(){
        Mon3trMod.HardModeEnable01 = this.hardMode01;
        Mon3trMod.HardModeEnable02 = this.hardMode02;
    }

    public int prevIndex() {
        return this.index - 1 < 0 ? chars.size() - 1 : this.index - 1;
    }

    public int nextIndex() {
        return this.index + 1 > chars.size() - 1 ? 0 : this.index + 1;
    }

    public void update() {
        this.updateInput();
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == ClassEnum.Mon3tr_CLASS) {
            this.leftHb.update();
            this.rightHb.update();
            this.mode01Hb.update();
            this.mode02Hb.update();
            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.index = this.prevIndex();
                try {
                    SpireConfig config = new SpireConfig("Mon3tr_FimmlpS","Common");
                    config.setInt("defaultChar",this.index);
                    config.save();
                }catch (IOException var2){
                    var2.printStackTrace();
                }
                this.refresh();
            }

            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.index = this.nextIndex();
                try {
                    SpireConfig config = new SpireConfig("Mon3tr_FimmlpS","Common");
                    config.setInt("defaultChar",this.index);
                    config.save();
                }catch (IOException var2){
                    var2.printStackTrace();
                }
                this.refresh();
            }

            if(this.mode01Hb.clicked){
                this.mode01Hb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.hardMode01 = !this.hardMode01;
                try {
                    SpireConfig config = new SpireConfig("Mon3tr_FimmlpS","Common");
                    config.setBool("hardModeEnable01",this.hardMode01);
                    config.save();
                }catch (IOException var2){
                    var2.printStackTrace();
                }
                this.refreshMode();
            }

            if(this.mode02Hb.clicked){
                this.mode02Hb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.hardMode02 = !this.hardMode02;
                try {
                    SpireConfig config = new SpireConfig("Mon3tr_FimmlpS","Common");
                    config.setBool("hardModeEnable02",this.hardMode02);
                    config.save();
                }catch (IOException var2){
                    var2.printStackTrace();
                }
                this.refreshMode();
            }

            if (InputHelper.justClickedLeft) {
                if (this.leftHb.hovered) {
                    this.leftHb.clickStarted = true;
                }

                if (this.rightHb.hovered) {
                    this.rightHb.clickStarted = true;
                }

                if (this.mode01Hb.hovered) {
                    this.mode01Hb.clickStarted = true;
                }

                if(this.mode02Hb.hovered){
                    this.mode02Hb.clickStarted = true;
                }
            }
        }

    }

    public void render(SpriteBatch sb) {
        float centerX = (float)Settings.WIDTH * 0.55F;
        float centerY = (float)Settings.HEIGHT * 0.75F;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, uiStrings.TEXT[0], centerX, centerY + 50.0F * Settings.scale, Color.WHITE, 1.25F);
        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.curName, centerX, centerY, Settings.GOLD_COLOR);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.nextName, centerX + dist * 1.5F, centerY - dist *0.5F, color);
        if (this.leftHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb.cX - 24.0F, this.leftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if (this.rightHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.rightHb.cX - 24.0F, this.rightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        this.rightHb.render(sb);
        this.leftHb.render(sb);

        this.renderHardMode(sb);
    }

    private void renderHardMode(SpriteBatch sb){
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.OPTION_TOGGLE, mode01Hb.cX - MODE_TEXT_W/2F - 14F*Settings.scale, mode01Hb.cY - 16F * Settings.scale, 16F, 16F,32.0F, 32.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, false);
        if (this.mode01Hb.hovered) {
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, modeStrings.TEXT[0], mode01Hb.cX + 14F*Settings.scale, mode01Hb.cY, Settings.GREEN_TEXT_COLOR);
            TipHelper.renderGenericTip((float)InputHelper.mX - 140.0F * Settings.scale, (float)InputHelper.mY - 140.0F * Settings.scale, modeStrings.TEXT[0], modeStrings.EXTRA_TEXT[0]);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, modeStrings.TEXT[0], mode01Hb.cX + 14F*Settings.scale, mode01Hb.cY, Settings.GOLD_COLOR);
        }
        if(hardMode01){
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.OPTION_TOGGLE_ON, mode01Hb.cX - MODE_TEXT_W/2F - 14F*Settings.scale, mode01Hb.cY - 16F * Settings.scale, 16F, 16F,32.0F, 32.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, false);
        }

        sb.draw(ImageMaster.OPTION_TOGGLE, mode02Hb.cX - MODE_TEXT_W/2F - 14F*Settings.scale, mode02Hb.cY - 16F * Settings.scale, 16F, 16F,32.0F, 32.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, false);
        if (this.mode02Hb.hovered) {
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, modeStrings.TEXT[1], mode02Hb.cX + 14F*Settings.scale, mode02Hb.cY, Settings.GREEN_TEXT_COLOR);
            TipHelper.renderGenericTip((float)InputHelper.mX - 140.0F * Settings.scale, (float)InputHelper.mY - 140.0F * Settings.scale, modeStrings.TEXT[1], modeStrings.EXTRA_TEXT[1]);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, modeStrings.TEXT[1], mode02Hb.cX + 14F*Settings.scale, mode02Hb.cY, Settings.GOLD_COLOR);
        }
        if(hardMode02){
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.OPTION_TOGGLE_ON, mode02Hb.cX - MODE_TEXT_W/2F - 14F*Settings.scale, mode02Hb.cY - 16F * Settings.scale, 16F, 16F,32.0F, 32.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, false);
        }
    }

    public static class Char{
        public int index;
        public String name;
        public Char(int index){
            this.index = index;
            name = uiStrings.EXTRA_TEXT[index];
        }
    }

    @Override
    public void onLoad(Integer integer) {
        this.index = integer;
        refresh();
    }

    @Override
    public Integer onSave() {
        return this.index;
    }

    static {
        uiStrings = StringHelper.charSelects;
        modeStrings = StringHelper.modeSelects;
        chars = new ArrayList<>();
        chars.add(new Char(0));
        chars.add(new Char(1));
        Inst = new CharSelectScreen();
    }
}
