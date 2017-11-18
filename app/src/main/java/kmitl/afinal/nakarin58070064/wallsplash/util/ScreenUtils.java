package kmitl.afinal.nakarin58070064.wallsplash.util;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class ScreenUtils {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static Drawable getCurrentWallpaper(Context context) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        return wallpaperDrawable;
    }

}
