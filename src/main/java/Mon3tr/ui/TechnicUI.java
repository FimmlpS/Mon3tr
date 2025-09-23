package Mon3tr.ui;

import Mon3tr.effect.TechnicalFlashVfx;
import Mon3tr.helper.ImageHelper;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.TechnicPatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;

public class TechnicUI extends AbstractPanel {
    private static final UIStrings uiStrings;

    private Hitbox hitbox;
    private Color bgColor = Color.WHITE.cpy();
    public static float fontScale = 1F;
    public static float unFontScale = 1F;

    TechnicalFlashVfx vfx;

    public TechnicUI(){
        super(200F* Settings.xScale,360*Settings.yScale,-480F*Settings.scale,360F*Settings.scale,12F*Settings.scale,-12F*Settings.scale, ImageHelper.skillPower.getTexture(),true);
        hitbox = new Hitbox(160F*Settings.scale,120F*Settings.scale);
        this.vfx = new TechnicalFlashVfx(this);
    }

    public void update(){
        super.updatePositions();
        vfx.update();
        this.hitbox.move(this.current_x,this.current_y);
        this.hitbox.update();
        if (this.hitbox.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }
        if(fontScale!=1F)
            fontScale = MathHelper.scaleLerpSnap(fontScale,1F);
        if(unFontScale!=1F)
            unFontScale = MathHelper.scaleLerpSnap(unFontScale,1F);
    }

    @Override
    public void render(SpriteBatch sb) {
        vfx.render(sb);
        if(this.hitbox.hovered){
            bgColor.a = 0.5F;
        }
        else{
            bgColor.a = 1F;
        }
        sb.setColor(bgColor);
        if(this.img!=null){
            sb.draw(this.img, this.current_x - img_width/2F, this.current_y - img_height/2F, this.img_width, this.img_height, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
        }
        String pe=String.valueOf(MeltdownPatch.meltDownCounter);
        String unPe = String.valueOf(MeltdownPatch.meltDownCounterMax);
        TechnicPatch.technicFont.getData().setScale(fontScale);
        TechnicPatch.technicTotalFont.getData().setScale(unFontScale);
        FontHelper.renderFontCentered(sb, TechnicPatch.technicFont,pe,current_x,current_y+24F*Settings.scale);
        FontHelper.renderFontCentered(sb, TechnicPatch.technicTotalFont,unPe,current_x,current_y-36F*Settings.scale);
        hitbox.render(sb);
        sb.setColor(Color.WHITE);
        if (this.hitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(50.0F * Settings.scale, 600.0F * Settings.scale, uiStrings.TEXT[0], uiStrings.TEXT[1]+pe+uiStrings.TEXT[2]+unPe);
        }
    }

    public static void onAmountChange(boolean un){
        if(un){
            unFontScale = 2F;
        }
        else
            fontScale = 2F;
    }


    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("mon3tr:Technic");
    }
}
