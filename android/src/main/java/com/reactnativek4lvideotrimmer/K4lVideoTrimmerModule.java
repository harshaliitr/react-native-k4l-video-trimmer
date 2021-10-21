package com.reactnativek4lvideotrimmer;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = K4lVideoTrimmerModule.NAME)
public class K4lVideoTrimmerModule extends ReactContextBaseJavaModule {
    public static final String NAME = "K4lVideoTrimmer";
    static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";

    public K4lVideoTrimmerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    public void multiply(int a, int b, Promise promise) {
        promise.resolve(a * b);
    }

    @ReactMethod
    void navigateToTrimmer(@NonNull String uri) {
        Activity activity = getCurrentActivity();
        if (activity != null) {
          Intent intent = new Intent(activity, TrimmerActivity.class);
          intent.putExtra("EXTRA_VIDEO_PATH", uri);
          activity.startActivity(intent); // change to startActivityForResult so that callback can be called with trimmed video
        }
    }
}
