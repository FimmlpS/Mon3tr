package Mon3tr.helper;

import Mon3tr.ui.CharSelectScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ImageHelper {
    public static final TextureAtlas.AtlasRegion gear;
    public static final TextureAtlas.AtlasRegion skillPower;
    public static final TextureAtlas.AtlasRegion skillPower_O;
    public static final TextureAtlas.AtlasRegion crystalItem;

    public static final TextureAtlas.AtlasRegion attackHit;
    public static final TextureAtlas.AtlasRegion attackHit2;

    public static String getCharSelectBg(){
        if(CharSelectScreen.getChar().index==0){
            return "Mon3trResources/img/charSelect/Mon3trBG.png";
        }
        return "Mon3trResources/img/charSelect/Mon3trBG2.png";
    }

    static {
        gear = new TextureAtlas.AtlasRegion(new Texture("Mon3trResources/img/512/iterationGear.png"),0,0,128,128);
        skillPower = new TextureAtlas.AtlasRegion(new Texture("Mon3trResources/img/ui/SkillPower.png"),0,0,256,256);
        skillPower_O = new TextureAtlas.AtlasRegion(new Texture("Mon3trResources/img/ui/SkillPower_O.png"),0,0,256,256);
        crystalItem = new TextureAtlas.AtlasRegion(new Texture("Mon3trResources/img/ui/CrystalItem.png"),0,0,64,64);
        attackHit = new TextureAtlas.AtlasRegion(new Texture("Mon3trResources/img/effect/AttackHit.png"),0,0,512,96);
        attackHit2 = new TextureAtlas.AtlasRegion(new Texture("Mon3trResources/img/effect/AttackHit2.png"),0,0,512,96);
    }
}
