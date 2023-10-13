package com.existfragger.rnimagesize;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;

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
            String scheme = u.getScheme();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            ExifInterface exifInterface = null;

            int height = 0;
            int width = 0;
            int rotation = 0;

            if (ContentResolver.SCHEME_FILE.equals(scheme) || ContentResolver.SCHEME_CONTENT.equals(scheme) || ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
                // ContentResolver.openInputStream() can only handle SCHEME_FILE, SCHEME_CONTENT and SCHEME_ANDROID_RESOURCE
                // https://developer.android.com/reference/android/content/ContentResolver?hl=en#openInputStream(android.net.Uri)
                ContentResolver contentResolver = getReactApplicationContext().getContentResolver();
                BitmapFactory.decodeStream(
                    contentResolver.openInputStream(u),
                    null,
                    options
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    exifInterface = new ExifInterface(contentResolver.openInputStream(u));
                } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                    // For SCHEME_FILE, Use ExifInterface(String filename) if the SDK version is less than 24(N)
                    exifInterface = new ExifInterface(u.getPath());
                }
            } else {
                // ContentResolver.openInputStream() cannot handle this scheme, treat it as remote uri
                URL url = new URL(uri);
                BitmapFactory.decodeStream(url.openStream(), null, options);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    exifInterface = new ExifInterface(url.openStream());
                }
            }
            height = options.outHeight;
            width = options.outWidth;
            if (exifInterface != null) {
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                    rotation = 90;
                else if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                    rotation = 180;
                else if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                    rotation = 270;
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
