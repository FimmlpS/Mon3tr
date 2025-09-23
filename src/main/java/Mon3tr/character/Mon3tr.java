package Mon3tr.character;

import Mon3tr.card.skill.Core;
import Mon3tr.helper.RegisterHelper;
import Mon3tr.modcore.Mon3trMod;
import Mon3tr.patch.ClassEnum;
import Mon3tr.patch.ColorEnum;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.ProstsPatch;
import Mon3tr.ui.CharSelectScreen;
import Mon3tr.ui.StarGroup;
import Mon3tr.ui.TechnicUI;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.combat.HealNumberEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mon3tr extends CustomPlayer {
    public static final String ID = "mon3tr:Mon3tr";

    private static final CharacterStrings characterStrings;

    public static final String[] orbTextures = new String[]{
            "Mon3trResources/img/orbs/1.png",
            "Mon3trResources/img/orbs/2.png",
            "Mon3trResources/img/orbs/3.png",
            "Mon3trResources/img/orbs/4.png",
            "Mon3trResources/img/orbs/6.png",
            "Mon3trResources/img/orbs/5.png",
            "Mon3trResources/img/orbs/2.png",
            "Mon3trResources/img/orbs/3.png",
            "Mon3trResources/img/orbs/4.png",
            "Mon3trResources/img/orbs/6.png",
            "Mon3trResources/img/orbs/5.png",
    };

    public static final String[] orbTexturesMon3tr = new String[]{
            "Mon3trResources/img/orbs/m1.png",
            "Mon3trResources/img/orbs/m2.png",
            "Mon3trResources/img/orbs/m3.png",
            "Mon3trResources/img/orbs/m4.png",
            "Mon3trResources/img/orbs/m5.png",
            "Mon3trResources/img/orbs/mBASE.png",
            "Mon3trResources/img/orbs/m1.png",
            "Mon3trResources/img/orbs/m2.png",
            "Mon3trResources/img/orbs/m3.png",
            "Mon3trResources/img/orbs/m4.png",
            "Mon3trResources/img/orbs/m5.png",
    };

    public static final String VFX = "Mon3trResources/img/orbs/vfx.png";
    public static final String IMG_SHOULDER = "Mon3trResources/img/characters/char_shoulder.png";
    public static final float[] LAYER_SPEED = new float[] {
            -32.0F,
            -16.0F,
            64.0F,
            32.0F,
            64.0F,
            -64.0F,
            -64.0F,
            64.0F,
            -30.0F,
            30.0F
    };

    public static final float[] LAYER_SPEEDMON3TR = new float[] {
            -32.0F,
            0F,
            -16.0F,
            -32F,
            0F,
            -32.0F,
            0F,
            -16.0F,
            -32F,
            0F,
    };

    private static final int ENERGY_PER_TURN =3;
    private static final int STARTING_HP = 85;
    private static final int MAX_HP = 85;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 8;

    //熔毁特效
    //public StarGroup stars = new StarGroup();

    //normal-1 skill2-2 skill3-3
    public int currentState = 1;

    private String idleName = "Idle";
    private String attackName = "Attack";
    private String skill1Name = "Skill_1";
    private String skill2BeginName = "Skill_2_Begin";
    private String skill2IdleName = "Skill_2_Idle";
    private String skill2AttackName = "Skill_2_Loop";
    private String skill2EndName = "Skill_2_End";
    private String skill3BeginName = "Skill_3_Begin";
    private String skill3Begin2Name = "Skill_3_ReStart";
    private String skill3IdleName = "Skill_3_Idle";
    private String skill3AttackName = "Skill_3_Loop";
    private String skill3EndName = "Skill_3_End";
    private String skill3End2Name = "Skill_3_Back";

    public Mon3tr(String name){
        super(name, ClassEnum.Mon3tr_CLASS,orbTexturesMon3tr,VFX,LAYER_SPEEDMON3TR,null,null);
        this.dialogX = this.drawX+ 0.0F* Settings.scale;
        this.dialogY = this.drawY+ 240.0F*Settings.scale;
        this.initializeClass(null,IMG_SHOULDER,IMG_SHOULDER,null,this.getLoadout(),0,-5.0F,260.0F,240.0F,new EnergyManager(ENERGY_PER_TURN));
        this.refreshSkin();
    }

    public void refreshSkin(){
        int type = 0;
        String pathName = "Mon3trResources/img/characters/";
        String fileName = "";
        if(type==0){
            fileName = "mon3tr_A/char_4179_monstr";
        }
        else if(type==1){
            fileName = "test/char_1035_wisdel";
        }
        else if(type==2){
            fileName = "TEST2/enemy_1543_cstlrs";
        }
        else if(type==3){
            fileName = "mm/char_249_mlyss";
        }
        this.loadAnimation(pathName+fileName+".atlas",pathName+fileName+".json",1.75F);

        if(Settings.FAST_MODE){
            state.setTimeScale(1);
        }
        else{
            state.setTimeScale(1);
        }

        if(type==1){
            idleName = "Idle";
            attackName = "Attack";
        }
        else if (type==2){
            idleName="Idle_A";
            attackName="Attack_A";
        }
        else if(type==3){
            idleName="Skill_3_Idle";
            attackName="Skill_3_loop";
        }
        this.state.setAnimation(0,idleName,true);
    }

    public static void onAttack(boolean skilled){
        AbstractPlayer pl = AbstractDungeon.player;
        if(pl.chosenClass==ClassEnum.Mon3tr_CLASS){
            ((Mon3tr)pl).attackAnimation(skilled);
        }
    }

    public void attackAnimation(boolean skilled){
        if(Settings.FAST_MODE){
            state.setTimeScale(1);
        }
        else{
            state.setTimeScale(1);
        }
        if(currentState==1){
            if(!skilled){
                this.state.setAnimation(0,attackName,false);
            }
            else{
                this.state.setAnimation(0,skill1Name,false);
            }
            this.state.addAnimation(0,idleName,true,0F);
        }
        else if(currentState==2){
            this.state.setAnimation(0,skill2AttackName,false);
            this.state.addAnimation(0,skill2IdleName,true,0F);
        }
        else if(currentState==3){
            this.state.setAnimation(0,skill3AttackName,false);
            this.state.addAnimation(0,skill3IdleName,true,0F);
        }
    }

    public void enterOverloadAnimation(){
        currentState = 2;
        this.state.setAnimation(0,skill2BeginName,false);
        this.state.addAnimation(0,skill2IdleName,true,0F);
    }

    public void outerOverloadAnimation(){
        if(currentState==2){
            this.state.setAnimation(0,skill2EndName,false);
            this.state.addAnimation(0,idleName,true,0F);
        }
        currentState = 1;
    }

    public void enterMeltdownAnimation(){
        currentState = 3;
        this.state.setAnimation(0,skill3BeginName,false);
        this.state.addAnimation(0,skill3Begin2Name,false,0.1F);
        this.state.addAnimation(0,skill3IdleName,true,0F);
    }

    public void outerMeltdownAnimation(){
        if(currentState==3){
            this.state.setAnimation(0,skill3EndName,false);
            this.state.addAnimation(0,skill3End2Name,false,0.1F);
            this.state.addAnimation(0,idleName,true,0F);
        }
        currentState = 1;
    }

    public void resetAnimation(){
        if(currentState==2){
            this.state.setAnimation(0,skill2EndName,false);
            this.state.addAnimation(0,idleName,true,0F);
        }
        else if(currentState==3){
            this.state.setAnimation(0,skill3EndName,false);
            this.state.addAnimation(0,skill3End2Name,false,0.1F);
            this.state.addAnimation(0,idleName,true,0F);
        }
        currentState = 1;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        return RegisterHelper.startDeck;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        if(CharSelectScreen.getChar().index==0){
            return RegisterHelper.startRelic;
        }
        else
            return RegisterHelper.startRelic2;
    }

    public String getDescriptionSelect(){
        if(CharSelectScreen.getChar().index==0){
            return characterStrings.TEXT[0];
        }
        else
            return characterStrings.TEXT[3];
    }

    @Override
    public void onVictory() {
        super.onVictory();
//        if(!this.isDying){
//            if(MeltdownPatch.canRecoverCounter){
//                AbstractDungeon.effectList.add(new HealNumberEffect(hb.cX,hb.cY,40));
//                MeltdownPatch.meltDownCounter += 40;
//                if(MeltdownPatch.meltDownCounter>MeltdownPatch.meltDownCounterMax){
//                    MeltdownPatch.meltDownCounter = MeltdownPatch.meltDownCounterMax;
//                }
//                TechnicUI.onAmountChange(false);
//            }
//        }
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0],getDescriptionSelect(),STARTING_HP,MAX_HP,HAND_SIZE,STARTING_GOLD,5,this,getStartingRelics(),getStartingDeck(),false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return ColorEnum.Mon3tr_COLOR;
    }

    @Override
    public Color getCardRenderColor() {
        return Mon3trMod.mColor.cpy();
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Core();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.SKY.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playAV("MON3TR_UNBREAKABLE", MathUtils.random(-0.7F, 0.7F),0.7F);
    }

    @Override
    public void updateOrb(int energyCount) {
        this.energyOrb.updateOrb(energyCount);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return ID;
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Mon3tr(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[2];
    }

    @Override
    public Color getSlashAttackColor() {
        return Mon3trMod.mColor.cpy();
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("Mon3trResources/img/charSelect/cg01.png", "ATTACK_HEAVY"));
        panels.add(new CutscenePanel("Mon3trResources/img/charSelect/cg02.png","HEAL_1"));
        panels.add(new CutscenePanel("Mon3trResources/img/charSelect/cg03.png","BUFF_3"));
        return panels;
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("Mon3trResources/img/charSelect/winbg.png");
    }

    //todo
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getVampireText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public void playDeathAnimation() {
        this.state.setAnimation(0,"Die",false);
    }

    @Override
    public void damage(DamageInfo info) {
        if(info.type != DamageInfo.DamageType.NORMAL && ProstsPatch.prosts!=null && !ProstsPatch.prosts.isDeadOrEscaped() && ProstsPatch.prosts.currentHealth>0){
            ProstsPatch.prosts.damage(info);
        }
        else{
            int thisHealth = this.currentHealth;
            super.damage(info);
            int trueDamage = thisHealth - this.currentHealth;
            if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && trueDamage > 0 && this.isDead) {
                this.playDeathAnimation();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        ProstsPatch.render(sb);
    }

    @Override
    public void update() {
        super.update();
        ProstsPatch.update();
        //stars.update();
    }

    @Override
    public void applyStartOfTurnPowers() {
        super.applyStartOfTurnPowers();
        ProstsPatch.loseBlock();
        ProstsPatch.endOfTurn();
        ProstsPatch.startOfTurn();
    }

    //特殊渲染手牌
    @SpireOverride
    protected void renderCardHotKeyText(SpriteBatch sb){
        if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT || MeltdownPatch.meltdownGroup ==null || MeltdownPatch.meltdownGroup.size()!=1){
            SpireSuper.call(sb);
        }
        else{
            renderHotText(MeltdownPatch.meltdownGroup.getBottomCard(),0,sb);
            int index = 1;
            for(Iterator<AbstractCard> var3 = this.hand.group.iterator(); var3.hasNext(); ++index) {
                AbstractCard card = var3.next();
                if (index < InputActionSet.selectCardActions.length) {
                   renderHotText(card,index,sb);
                }
            }
        }
    }

    private void renderHotText(AbstractCard card,int index, SpriteBatch sb){
        float width = AbstractCard.IMG_WIDTH * card.drawScale / 2.0F;
        float height = AbstractCard.IMG_HEIGHT * card.drawScale / 2.0F;
        float topOfCard = card.current_y + height;
        float textSpacing = 50.0F * Settings.scale;
        float textY = topOfCard + textSpacing;
        float sin = (float)Math.sin((double)(card.angle / 180.0F) * 3.141592653589793D);
        float xOffset = sin * width;
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, InputActionSet.selectCardActions[index].getKeyString(), card.current_x - xOffset, textY, Settings.CREAM_COLOR);
    }



    static {
        characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    }
}
