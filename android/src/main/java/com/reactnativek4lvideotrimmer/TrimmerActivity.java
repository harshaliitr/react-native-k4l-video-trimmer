package com.reactnativek4lvideotrimmer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.react.ReactActivity;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

public class TrimmerActivity extends ReactActivity implements OnTrimVideoListener {

  private K4LVideoTrimmer mVideoTrimmer;
  private ProgressDialog mProgressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trimmer);

    Intent extraIntent = getIntent();
    Uri path = Uri.parse(extraIntent.getStringExtra("EXTRA_VIDEO_PATH"));


    //setting progressbar
    mProgressDialog = new ProgressDialog(this);
    mProgressDialog.setCancelable(false);
    mProgressDialog.setMessage(getString(R.string.trimming_progress));
//    mProgressDialog.setMessage("Trimming your video...");

    mVideoTrimmer = findViewById(R.id.timeLine);
    if (mVideoTrimmer != null) {
      mVideoTrimmer.setMaxDuration(20);
      mVideoTrimmer.setOnTrimVideoListener(this);
      mVideoTrimmer.setDestinationPath("/storage/emulated/0/DCIM/MFTrimmer1/");
      mVideoTrimmer.setVideoURI(path);
    }
  }

  @Override
  public void onTrimStarted() {

  }

  @Override
  public void getResult(Uri uri) {
    mProgressDialog.cancel();
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(TrimmerActivity.this, getString(R.string.video_saved_at, uri.getPath()), Toast.LENGTH_LONG).show();
      }
    });
    Intent returnIntent = new Intent();
    returnIntent.putExtra("result",uri.getPath());
    returnIntent.setDataAndType(uri, "video/mp4");
    setResult(Activity.RESULT_OK, returnIntent);
    finish();
  }

  @Override
  public void cancelAction() {
    Intent returnIntent = new Intent();
    setResult(Activity.RESULT_CANCELED, returnIntent);
    finish();
  }

  @Override
  public void onError(String message) {
    Intent returnIntent = new Intent();
    returnIntent.putExtra("error", message);
    setResult(2, returnIntent);
    finish();
  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {

  }
}
