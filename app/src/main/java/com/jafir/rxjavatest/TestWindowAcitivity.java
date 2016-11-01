package com.jafir.rxjavatest;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.jafir.rxjavatest.base.BaseActivity;
import com.jafir.rxjavatest.base.BasePresenter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by jafir on 16/7/25.
 */
public class TestWindowAcitivity extends BaseActivity {
    private static final String TAG = "phone";
    private WindowManager mWindowManager;
    private PhoneBroadCast cast = new PhoneBroadCast();
    private boolean isRecording;
    private boolean isPlay;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    @Override
    protected int createLayoutView() {

        return R.layout.activity_test_window;
    }

    @Override
    protected void initData() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (!Settings.canDrawOverlays(TestWindowAcitivity.this)) {
//
//                        if (WindowUtils.isMIUIRom()) {
//                            Log.w("debug", "是MIUI");
//                            WindowUtils.openMiuiPermissionActivity(TestWindowAcitivity.this);
//                        } else {
//                            Log.w("debug", "no是MIUI");
//                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                                    Uri.parse("package:" + getPackageName()));
//                            startActivityForResult(intent, 1);
//                        }
//                    } else {
//                        Log.w("debug", "有权限");
//                        WindowUtils.showPopupWindow(TestWindowAcitivity.this);
//                    }
//                } else {
//                    if (WindowUtils.isMIUIRom()) {
//                        if (WindowUtils.isMiuiFloatWindowOpAllowed(TestWindowAcitivity.this)) {
//                            Log.w("debug", "有权限");
//                            WindowUtils.showPopupWindow(TestWindowAcitivity.this);
//                        } else {
//                            WindowUtils.openMiuiPermissionActivity(TestWindowAcitivity.this);
//                        }
//                        Log.w("debug", "是MIUI");
//
//
//                    } else {
//                        Log.w("debug", "no是MIUI");
//                        WindowUtils.showPopupWindow(TestWindowAcitivity.this);
//                    }
//                    WindowUtils.showPopupWindow(TestWindowAcitivity.this);
//
//                }
                WindowUtils.showPopupWindow(TestWindowAcitivity.this);


            }
        });


        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("debug", getPackageName());
                Log.e("debug", getComponentName().toString());
                Log.e("debug", getComponentName().toShortString());

                ShortcutUtil.addAppShortcut(TestWindowAcitivity.this, "我的快捷", TestWindowAcitivity.class, R.mipmap.ic_launcher);
//                ShortcutUtil.addShortcut(TestWindowAcitivity.this,new Intent());
            }
        });


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record();
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });


    }


    private void record() {


        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();

            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            File file = new File(Environment.getExternalStorageDirectory().getPath());
            if (!file.exists()) {
                file.mkdirs();
            }
            File file1 = new File(file, "record.amr");
            if (!file1.exists()) {
                try {
                    file1.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            Log.d("debug", file1.toString());
            mediaRecorder.setOutputFile(file1.getPath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        }
        if (!isRecording) {
            try {
                mediaRecorder.prepare();

                Log.d("debug", "parepare");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();
            isRecording = true;
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isRecording = false;
                }
            });


        } else {
            Log.d("debug", "stop");
            mediaRecorder.stop();
            isRecording = false;
        }

    }

    private void play() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/record.amr");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.seekTo(0);
                    Log.d("debug", "compl");
                }
            });
        }


        if (!mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
            }
            mediaPlayer.start();
            Log.d("debug", "start");
        } else {
            Log.d("debug", "stop");
            mediaPlayer.stop();
//            mediaPlayer.release();
        }
    }


    private void invoke() {

        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentThreadClass = activityThreadClass.getDeclaredMethod("currentActivityThread");
//            设置访问权限
            currentThreadClass.setAccessible(true);
//             拿到方法对象
            Object currentThread = currentThreadClass.invoke(null);


            Field mInstrumentField = activityThreadClass.getDeclaredField("mInstrumention");
            mInstrumentField.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) mInstrumentField.get(currentThread);


//            创建代理对象
            Instrumentation proxyInstrument = new MyInstrumention(instrumentation);

//            偷梁换柱
            mInstrumentField.set(currentThread, proxyInstrument);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
                // Permission Denied
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(TestWindowAcitivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestWindowAcitivity.this, "权限授予成功！", Toast.LENGTH_SHORT).show();
                    WindowUtils.showPopupWindow(TestWindowAcitivity.this);
                }
            }

        }

    }


    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter createPresenter() {


        return null;
    }

    private class MyInstrumention extends Instrumentation {
        Instrumentation mBase;

        public MyInstrumention(Instrumentation instrumentation) {
            this.mBase = instrumentation;
        }

        public void execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target
                , Intent intent, int requestCode, Bundle options
        ) {


//            hook


        }


    }
}
