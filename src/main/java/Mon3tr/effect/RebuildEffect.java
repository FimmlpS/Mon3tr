package Mon3tr.effect;

import Mon3tr.card.skill.SelfFix;
import Mon3tr.card.special.*;
import Mon3tr.character.Mon3tr;
import Mon3tr.helper.Mon3trHelper;
import Mon3tr.patch.CrystalPatch;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.SimulationPatch;
import Mon3tr.relic.BadOrganization;
import Mon3tr.relic.GeneSeed;
import Mon3tr.relic.LiveDust;
import Mon3tr.ui.RebuildOption;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.HealNumberEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RebuildEffect extends AbstractGameEffect {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DUR = 1.5F;
    private Color screenColor;

    private boolean optionSelected = false;
    private RebuildOption rebuildOption;
    RebuildPhase currentPhase;

    ArrayList<AbstractCard> cardsToRebuild;

    public static final ArrayList<String> specialRelics = new ArrayList<>();


    public RebuildEffect(RebuildOption option) {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = DUR;
        this.screenColor.a = 0.0F;
        this.rebuildOption = option;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        currentPhase = RebuildPhase.OPTION;
    }

    @Override
    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            duration -= Gdx.graphics.getDeltaTime();
            updateBlackScreenColor();
        }

        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            if (currentPhase == RebuildPhase.OPTION) {
                Iterator<AbstractCard> var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                while (var1.hasNext()) {
                    AbstractCard c = var1.next();
                    if (c instanceof RebuildRelic) {
                        ArrayList<AbstractCard> options = new ArrayList<>();
                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            if (r.tier != AbstractRelic.RelicTier.BOSS) {
                                if(CrystalPatch.RelicField.unRefractor.get(r))
                                    continue;
                                RelicPreviewCard previewCard = new RelicPreviewCard();
                                previewCard.setRelic(r);
                                options.add(previewCard);
                            }
                        }
                        if (!options.isEmpty()) {
                            optionSelected = false;
                            cardsToRebuild = options;
                            currentPhase = RebuildPhase.RELIC_SELECT;
                            rebuildOption.triggerIt();
                            CrystalPatch.loseCrystal(8);
                        }
                        else {
                            this.duration = 0.1F;
                            currentPhase = RebuildPhase.END;
                        }
                    } else if (c instanceof RebuildCard) {
                        ArrayList<AbstractCard> options = new ArrayList<>();
                        for(AbstractCard card:AbstractDungeon.player.masterDeck.group){
                            if(card.type != AbstractCard.CardType.CURSE){
                                options.add(card);
                            }
                        }
                        if (!options.isEmpty()) {
                            optionSelected = false;
                            cardsToRebuild = options;
                            currentPhase = RebuildPhase.CARD_SELECT;
                            rebuildOption.triggerIt();
                            CrystalPatch.loseCrystal(8);
                        }
                        else {
                            this.duration = 0.1F;
                            currentPhase = RebuildPhase.END;
                        }
                    } else if (c instanceof RebuildTechnic) {
                        AbstractDungeon.topLevelEffectsQueue.add(new HealNumberEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY,8));
                        MeltdownPatch.increaseMeltdownCounterStart(8);
                        rebuildOption.triggerIt();
                        CrystalPatch.loseCrystal(8);
                        //充能也会增加事件遗物的出现概率
                        SimulationPatch.SimulationRelicChance += 0.15F;
                        this.duration = 0.1F;
                        currentPhase = RebuildPhase.END;
                    } else if (c instanceof RebuildProsts) {
                        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(new SelfFix(),Settings.WIDTH/2F,Settings.HEIGHT/2F));
                        rebuildOption.triggerIt();
                        CrystalPatch.loseCrystal(20);
                        this.duration = 0.1F;
                        currentPhase = RebuildPhase.END;
                    } else {
                        //return
                        this.duration = 0.1F;
                        currentPhase = RebuildPhase.END;
                    }

                    break;
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            else if(currentPhase == RebuildPhase.RELIC_SELECT){
                Iterator<AbstractCard> var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                while (var1.hasNext()) {
                    AbstractCard c = var1.next();
                    if(c instanceof RelicPreviewCard){
                        AbstractRelic r = ((RelicPreviewCard) c).getRelicCopy();
                        if(r!=null){
                            AbstractDungeon.player.loseRelic(r.relicId);
                            ArrayList<AbstractRelic> relics = Mon3trHelper.getRebuildRelics(r,3);
                            //2025/6/29 增加结局相关遗物
                            addSpecialRelics(relics);
                            ArrayList<AbstractCard> options = new ArrayList<>();
                            for (AbstractRelic rt : relics) {
                                RelicPreviewCard previewCard = new RelicPreviewCard();
                                previewCard.setRelic(rt);
                                previewCard.setTotalRelics(relics);
                                options.add(previewCard);
                            }
                            AbstractDungeon.cardRewardScreen.customCombatOpen(options,TEXT[options.size()==3?3:5],false);
                            currentPhase = RebuildPhase.RELIC_SELECT_2;
                        }
                    }
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            else if(currentPhase == RebuildPhase.CARD_SELECT){
                Iterator<AbstractCard> var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                while (var1.hasNext()) {
                    AbstractCard c = var1.next();
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    CardCrawlGame.sound.play("CARD_EXHAUST");
                    ArrayList<AbstractCard> options = Mon3trHelper.getRebuildCards(c,3);
                    AbstractDungeon.cardRewardScreen.customCombatOpen(options,TEXT[4],false);
                    currentPhase = RebuildPhase.CARD_SELECT_2;
                    //重构卡牌也会增加事件遗物的出现概率
                    SimulationPatch.SimulationRelicChance += 0.15F;
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
        }
        else if(currentPhase == RebuildPhase.RELIC_SELECT_2){
            if(AbstractDungeon.cardRewardScreen.discoveryCard !=null){
                AbstractDungeon.cardRewardScreen.discoveryCard.onChoseThisOption();
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
                currentPhase = RebuildPhase.END;
                duration = 0.1F;
            }
        }
        else if(currentPhase == RebuildPhase.CARD_SELECT_2){
            if(AbstractDungeon.cardRewardScreen.discoveryCard !=null){
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(AbstractDungeon.cardRewardScreen.discoveryCard.makeCopy(),Settings.WIDTH/2F,Settings.HEIGHT/2F));
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
                currentPhase = RebuildPhase.END;
                duration = 0.1F;
            }
        }
        //2025/6/29 ADD: 将重构遗物和重构卡牌开放给所有角色
        if (this.duration < 1.0F && currentPhase == RebuildPhase.OPTION && !optionSelected) {
            optionSelected = true;
            ArrayList<AbstractCard> shownCards = new ArrayList<>();
            if (CrystalPatch.crystalAmount >= 8) {
                shownCards.add(new RebuildRelic());
                shownCards.add(new RebuildCard());
                if(AbstractDungeon.player instanceof Mon3tr)
                    shownCards.add(new RebuildTechnic());
            }
            if (CrystalPatch.crystalAmount >= 20 && AbstractDungeon.player instanceof Mon3tr) {
                boolean add = true;
                for(AbstractCard c : AbstractDungeon.player.masterDeck.group){
                    if(c instanceof SelfFix) {
                        add = false;
                        break;
                    }
                }
                if(add)
                    shownCards.add(new RebuildProsts());
            }
            shownCards.add(new RebuildReturn());
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmp.group = shownCards;
            AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[0], false, false, true, false);
        }
        else if(this.duration <1.0F && currentPhase == RebuildPhase.RELIC_SELECT && !optionSelected){
            optionSelected = true;
            if(cardsToRebuild != null) {
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                tmp.group = cardsToRebuild;
                AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[1], false, false, true, false);
            }
        }
        else if(this.duration <1.0F && currentPhase == RebuildPhase.CARD_SELECT && !optionSelected){
            optionSelected = true;
            if(cardsToRebuild != null) {
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                tmp.group = cardsToRebuild;
                AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[2], false, false, true, false);
            }
        }

        if (this.duration < 0F) {
            this.isDone = true;
            ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
        }
    }

    private void updateBlackScreenColor(){
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }
    }

    private void addSpecialRelics(ArrayList<AbstractRelic> relics){
        ArrayList<AbstractRelic> relicToAdd = new ArrayList<>();
        for(String s:specialRelics){
            if(!AbstractDungeon.player.hasRelic(s)){
                relicToAdd.add(RelicLibrary.getRelic(s).makeCopy());
            }
        }
        if(!relicToAdd.isEmpty()){
            boolean add = AbstractDungeon.relicRng.randomBoolean(SimulationPatch.SimulationRelicChance);
            if(add){
                Collections.shuffle(relicToAdd,AbstractDungeon.relicRng.random);
                SimulationPatch.SimulationRelicChance = 0.3F;
                relics.add(relicToAdd.get(0));
            }
            else {
                SimulationPatch.SimulationRelicChance += 0.15F;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float)Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }
        else if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD){
            AbstractDungeon.cardRewardScreen.render(sb);
        }
    }

    @Override
    public void dispose() {

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("mon3tr:RebuildEffect");
        TEXT = uiStrings.TEXT;

        specialRelics.add(BadOrganization.ID);
        specialRelics.add(GeneSeed.ID);
        specialRelics.add(LiveDust.ID);
    }

    public enum RebuildPhase{
        OPTION,
        RELIC_SELECT,
        RELIC_SELECT_2,
        CARD_SELECT,
        CARD_SELECT_2,
        END
    }
}
