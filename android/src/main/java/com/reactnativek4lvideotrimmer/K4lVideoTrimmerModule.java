package com.reactnativek4lvideotrimmer;

import static com.gowtham.library.utils.TrimVideo.TRIM_VIDEO_OPTION;
import static com.gowtham.library.utils.TrimVideo.TRIM_VIDEO_URI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
// import com.google.gson.Gson;
import com.gowtham.library.ui.ActVideoTrimmer;
import com.gowtham.library.utils.TrimType;
import com.gowtham.library.utils.TrimVideo;
import com.gowtham.library.utils.TrimVideoOptions;

@ReactModule(name = K4lVideoTrimmerModule.NAME)
public class K4lVideoTrimmerModule extends ReactContextBaseJavaModule implements ActivityEventListener {
  public static final String NAME = "K4lVideoTrimmer";
  static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
  Promise promise;

  public K4lVideoTrimmerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(this);
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
  void navigateToTrimmer(@NonNull String uri, @NonNull String duration, Promise promise) {
    this.promise = promise;
    Activity activity = getCurrentActivity();
    if (activity != null) {
      Intent intent = new Intent(activity, TrimmerActivity.class);
      intent.putExtra("EXTRA_VIDEO_PATH", uri);
      intent.putExtra("VIDEO_TRIM_DURATION", duration);
      activity.startActivityForResult(intent, 1);
    }
  }

  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    if (promise == null) {
      return;
    }
    if (data == null) {
      this.promise.resolve(null);
    }
    String dataString = data.getDataString();
    if (dataString == null) {
      this.promise.resolve(null);
    }
    if (dataString.isEmpty()) {
      this.promise.resolve(null);
    }
    this.promise.resolve(dataString);
  }

  @Override
  public void onNewIntent(Intent intent) {
  }
}
