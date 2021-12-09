package com.reactnativek4lvideotrimmer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.gowtham.library.ui.ActVideoTrimmer;
import com.gowtham.library.utils.TrimType;
import com.gowtham.library.utils.TrimVideo;

//import life.knowledge4.videotrimmer.K4LVideoTrimmer;
//import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

public class TrimmerActivity extends AppCompatActivity {

  String videoUri;

  ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
    new ActivityResultContracts.StartActivityForResult(),
    result -> {
      if (result.getResultCode() == Activity.RESULT_OK &&
        result.getData() != null) {
        Uri uri = Uri.parse(TrimVideo.getTrimmedVideoPath(result.getData()));
        Intent returnIntent = new Intent();
        returnIntent.setDataAndType(uri, "video/mp4");
        setResult(1, returnIntent);
        finish();
      } else {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("error", 1);
        returnIntent.setDataAndType("", "video/mp4");
        setResult(2, returnIntent);
        finish();
      }
    });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trimmer);

    Intent extraIntent = getIntent();
    Uri path = Uri.parse(extraIntent.getStringExtra("EXTRA_VIDEO_PATH"));
    Long duration = Long.valueOf(extraIntent.getStringExtra("VIDEO_TRIM_DURATION"));
    this.videoUri = String.valueOf(path);
    openTrimActivity(this.videoUri, duration);
  }

  private void openTrimActivity(String path, Long duration) {
     TrimVideo.activity(String.valueOf(path))
      .setCompressOption(new CompressOption(30,"1M",1200,1200))
      .setTrimType(TrimType.FIXED_DURATION)
      .setHideSeekBar(true)
      .setFixedDuration(duration)
      .setTitle("select maximum " + duration + " seconds")
      .start(TrimmerActivity.this, startForResult);
  }
}
