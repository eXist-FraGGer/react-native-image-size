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
    public RNImageSizeModule(final ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void getSize(String uri, final Promise promise) {
        try {
            Uri u = Uri.parse(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            int height = 0;
            int width = 0;

            if (uri.startsWith("file://")) {
                BitmapFactory.decodeFile(u.getPath(), options);
                ExifInterface exifInterface = new ExifInterface(u.getPath());
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                int rotationDegrees = 0;
                if (orientation == ExifInterface.ORIENTATION_ROTATE_90 || orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                    height = options.outWidth;
                    width = options.outHeight;
                } else {
                    height = options.outHeight;
                    width = options.outWidth;
                }
            } else {
                URL url = new URL(uri);
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
                height = bitmap.getHeight();
                width = bitmap.getWidth();
            }

            WritableMap map = Arguments.createMap();

            map.putInt("height", height);
            map.putInt("width", width);

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
