package atproj.cyplay.com.asperteamapi.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

import atproj.cyplay.com.asperteamapi.util.DesignUtils;

/**
 * Created by andre on 06-Apr-18.
 */

public class CircleTransform implements Transformation {

    private Context _context;
    private int _colorStrokeRes;

    public CircleTransform(Context context, int colorStrokeRes) {
        _context = context;
        _colorStrokeRes = colorStrokeRes;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size/2f;

        // background paint
        Paint paintBG = new Paint();
        paintBG.setColor(_context.getResources().getColor(_colorStrokeRes));
        paintBG.setAntiAlias(true);

        // draw background circle
        canvas.drawCircle(r, r, r, paintBG);

        // draw main image
        canvas.drawCircle(r, r, r- DesignUtils.dpToPixel(_context, 2), paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}
