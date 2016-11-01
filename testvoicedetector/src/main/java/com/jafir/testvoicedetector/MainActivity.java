package com.jafir.testvoicedetector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;

import static com.jafir.testvoicedetector.R.id.to;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "debug";
    private TextView text;
    private StringBuilder sss = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5809c659");

//        testPermis1();

        findViewById(to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TestMuneActivity.class));
          }
        });

        testPer();

        final CountTextView countTextView = (CountTextView) findViewById(R.id.counttext);

        countTextView.setText(new String[]{"3","2","1","go!"});
        countTextView.setTextSize(200);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countTextView.start();
            }
        });

//        init();

    }

    private void testPermis1() {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)) {
            init();
            Log.i(DEBUG_TAG, "user has the permission already!");
        } else {
            Log.i(DEBUG_TAG, "user do not have this permission!");
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
        }
    }



    private void testPer() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                init();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.RECORD_AUDIO)
                .check();
    }


    private void testPermission() {
//        首先检查有没有权限
//        如果有
//        用ContextCompat 可以兼容23以下
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
//            do something
        } else {
//            去请求权限
//            用activityCompat可以兼容23以下
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 11);
        }


    }


    private void init() {
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        final SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this, null);
//2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
//        3.开始听写

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIat.startListening(mRecoListener);
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIat.isListening()) {
                    mIat.stopListening();
                }
            }
        });

        text = (TextView) findViewById(R.id.text);

//          必须要付费了之后才能使用
//        voiceDetect();
//听写监听器

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            init();
            Log.i(DEBUG_TAG, "==request success==");
        }
    }


    private RecognizerListener mRecoListener = new RecognizerListener() {

        //听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
//一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
//关于解析Json的代码可参见MscDemo中JsonParser类；
//isLast等于true时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d("Result:", results.getResultString());
            VoiceBean v = new Gson().fromJson(results.getResultString(), VoiceBean.class);
            sss.append(results.getResultString() + "\n");
            text.setText(sss.toString());
        }

        @Override
        public void onError(SpeechError speechError) {
            String errors = speechError.getPlainDescription(true); //获取错误码描述
            Log.d("errors:", errors.toString());
            text.setText("error:" + errors.toString());
        }


        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        public void onBeginOfSpeech() {
            sss.append("开始\n");
            text.setText(sss.toString());

        }
        //音量值0~30

        //结束录音
        public void onEndOfSpeech() {
            sss.append("结束\n");
            text.setText(sss.toString());

        }


        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };


    private void voiceDetect() {
        //云端语法识别：如需本地识别请参照本地识别
//1.创建SpeechRecognizer对象
        final SpeechRecognizer mAsr = SpeechRecognizer.createRecognizer(this, null);
// ABNF语法示例，可以说”北京到上海”
        String mCloudGrammar = "#ABNF 1.0 UTF-8; \n" +
                "        languagezh-CN;\n" +
                "        mode voice;\n" +
                "        root $main;\n" +
                "        $main = $place1 到$place2 ;\n" +
                "        $place1 = 北京 | 武汉 | 南京 | 天津 | 天京 | 东京;\n" +
                "        $place2 = 上海 | 合肥; ";
//2.构建语法文件
        mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        int ret = mAsr.buildGrammar("abnf", mCloudGrammar, grammarListener);
        if (ret != ErrorCode.SUCCESS) {
            Log.d("debug", "语法构建失败,错误码：" + ret);
        } else {
            Log.d("debug", "语法构建成功");
        }
//3.开始识别,设置引擎类型为云端
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
//设置grammarId
        mAsr.setParameter(SpeechConstant.CLOUD_GRAMMAR, "1000");

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ress = mAsr.startListening(mRecoListener);
                Log.d("debug", "ress:"+ress);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAsr.isListening()) {
                    mAsr.stopListening();
                }

            }
        });


    }

    //构建语法监听器
    private GrammarListener grammarListener = new GrammarListener() {
        @Override
        public void onBuildFinish(String grammarId, SpeechError error) {
            if (error == null) {
                if (!TextUtils.isEmpty(grammarId)) {
//构建语法成功，请保存grammarId用于识别
                } else {
                    Log.d("debug", "语法构建失败,错误码：" + error.getErrorCode());
                }
            }
        }
    };
}
