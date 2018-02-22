package utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by yadi on 27/05/16.
 */
public class FontCache {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {

        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {

                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
                Log.v("assets"  , context.getAssets().toString());

            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }
        return typeface;
    }
}