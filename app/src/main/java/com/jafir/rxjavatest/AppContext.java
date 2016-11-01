package com.jafir.rxjavatest;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by jafir on 16/7/21.
 */
public class AppContext extends Application {

    public  static Context app;
    public  static String  baseUrl = "http://172.28.1.199:9082/";

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplicationContext();
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
//初始化
        EMClient.getInstance().init(app, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);


    }


}
