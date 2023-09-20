package com.existfragger.rnimagesize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import java.io.InputStream;
import java.net.URL;

public class RNImageSizeModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;

    public RNImageSizeModule(final ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void getSize(String uri, final Promise promise) {
        try {
            Uri u = Uri.parse(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            int height = 0;
            int width = 0;
            int rotation = 0;

            if (uri.startsWith("file://")) {
                BitmapFactory.decodeFile(u.getPath(), options);
                ExifInterface exifInterface = new ExifInterface(u.getPath());
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                  rotation = 90;
                else if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                  rotation = 180;
                else if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                  rotation = 270;
                height = options.outHeight;
                width = options.outWidth;
            } else if (uri.startsWith("content://")) {
                BitmapFactory.decodeStream(
                    getReactApplicationContext().getContentResolver().openInputStream(u),
                    null,
                    options
                );
                height = options.outHeight;
                width = options.outWidth;
            } else {
                URL url = new URL(uri);
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
                height = bitmap.getHeight();
                InputStream input = this.reactContext.getContentResolver().openInputStream(Uri.parse(uri));
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) input);
                height = bitmap.getHeight();
                width = bitmap.getWidth();
            }

            WritableMap map = Arguments.createMap();

            map.putInt("height", height);
            map.putInt("width", width);
            map.putInt("rotation", rotation);

            promise.resolve(map);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @Override
    public String getName() {
        return "RNImageSize";
    }
}
