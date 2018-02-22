package utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.net.URL;

import dcube.com.salonseek.R;

public class Utilities {

    private static final float BLUR_RADIUS = 25f;

    public  Bitmap getBlurBitmap_fromURL(String url_image , Context c) {

        if (null == url_image) return null;

        Bitmap image = null;
        try {

            URL url = new URL(url_image);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (Exception e) {


        }

        return blur(image,c);
    }

    public Bitmap blur(Bitmap image , Context context) {

        if (image == null)
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.salonpic);

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(context);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }
}
