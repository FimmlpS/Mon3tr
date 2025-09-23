package Mon3tr.helper;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.Settings;

public class PatchHelper {
    //在这里定义各种常量

    public static final float SP_START_INFO_X;
    public static final float SP_DEST_INFO_X;
    public static final Color CREAM_COLOR = new Color(-597249);


    static {
        SP_START_INFO_X = (float)Settings.WIDTH + 100F * Settings.xScale;
        SP_DEST_INFO_X = (float) Settings.WIDTH/2F;
    }
}
