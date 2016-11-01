package com.jafir.rxjavatest;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by jafir on 16/7/25.
 */
public class PhoneBroadCast extends BroadcastReceiver {

    private static final String TAG = "phone";
    private boolean incomingFlag;
    private String incoming_number;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
//
//        Log.i(TAG, "RINGING :" + intent.getStringExtra("incoming_number"));
//        //如果是来电
//        TelephonyManager tm =
//                (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
//
//        Toast.makeText(context, "来电", Toast.LENGTH_SHORT).show();
//        switch (tm.getCallState()) {
//            case TelephonyManager.CALL_STATE_RINGING:
//                incomingFlag = true;//标识当前是来电
//                incoming_number = intent.getStringExtra("incoming_number");
//
//
//                WindowUtils.showPopupWindow(context);
//
//                Log.i(TAG, "RINGING :" + incoming_number);
//                break;
//            case TelephonyManager.CALL_STATE_OFFHOOK:
//                Log.i(TAG, "incoming ACCEPT :" + incoming_number);
//                break;
//
//            case TelephonyManager.CALL_STATE_IDLE:
//                Log.i(TAG, "incoming IDLE" + incoming_number);
//
//                break;
//        }


        //查了下android文档，貌似没有专门用于接收来电的action,所以，非去电即来电.
        //如果我们想要监听电话的拨打状况，需要这么几步 :
//    *第一：
//    获取电话服务管理器TelephonyManager manager = this.getSystemService(TELEPHONY_SERVICE);
//    *第二：通过TelephonyManager注册我们要监听的电话状态改变事件。manager.listen(new
//
//    MyPhoneStateListener(),
//
//    *PhoneStateListener.LISTEN_CALL_STATE);这里的PhoneStateListener.LISTEN_CALL_STATE就是我们想要
//    *监听的状态改变事件，初次之外，还有很多其他事件哦。
//            *第三步：
//    通过extends PhoneStateListener来定制自己的规则
//    。将其对象传递给第二步作为参数。
//            *第四步：这一步很重要，那就是给应用添加权限。android.permission.READ_PHONE_STATE


        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        //设置一个监听器
    }


    PhoneStateListener listener = new PhoneStateListener() {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            //注意，方法必须写在super方法后面，否则incomingNumber无法获取到值。
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    if (incomingNumber != null) {
                        incoming_number = incomingNumber;
                    }
//                    显示挂断的悬浮窗
                    Log.i(TAG, "挂断 :" + incoming_number);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (incomingNumber != null) {
                        incoming_number = incomingNumber;
                    }
                    Log.i(TAG, "接听 :" + incoming_number);
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    if (incomingNumber != null) {
                        incoming_number = incomingNumber;
                    }
//                    显示来电的悬浮窗
                    WindowUtils.showPopupWindow(context);
                    Log.i(TAG, "来电 :" + incoming_number);
                    //输出来电号码
                    break;
            }
        }
    };

}
