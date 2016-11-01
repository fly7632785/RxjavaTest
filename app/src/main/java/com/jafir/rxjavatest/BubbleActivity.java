package com.jafir.rxjavatest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.jafir.rxjavatest.base.BaseActivity;
import com.jafir.rxjavatest.base.BaseView;
import com.jafir.rxjavatest.main.MainContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jafir on 16/7/21.
 */
public class BubbleActivity extends BaseActivity<MainContract.Presenter> implements BaseView{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("buble", "onCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("bubble", "pause");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.w("bublle", "onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("bulbbke", "ondestoy");
    }

    @Override
    protected int createLayoutView() {
        return R.layout.activity_bubble;
    }

    @Override
    protected void initData() {
        Log.w("bubble", "initdata");
        MyAsyncTask task = new MyAsyncTask();
        task.execute(this);

    }

    @Override
    protected void initView() {
        Log.w("bubble", "initView");
    }


    class MyAsyncTask extends AsyncTask<Activity, Void, List<Call>> {


        @Override
        protected void onPostExecute(List<Call> calls) {
            super.onPostExecute(calls);

            Log.d("debug", calls.toString());


        }

        @Override
        protected List<Call> doInBackground(Activity... params) {

            if (ActivityCompat.checkSelfPermission(params[0], Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }

            ArrayList<Call> calls = new ArrayList<>();


            Cursor cursor = params[0].getContentResolver().query(CallLog.Calls.CONTENT_URI,
                    null, null, null, null);
            if (cursor.moveToFirst()) {
                do {

                    Call call = new Call();
                    //号码
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    //呼叫类型
                    String type;
                    switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)))) {
                        case CallLog.Calls.INCOMING_TYPE:
                            type = "呼入";
                            break;
                        case CallLog.Calls.OUTGOING_TYPE:
                            type = "呼出";
                            break;
                        case CallLog.Calls.MISSED_TYPE:
                            type = "未接";
                            break;
                        default:
                            type = "挂断";//应该是挂断.根据我手机类型判断出的
                            break;
                    }
                    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))));
                    //呼叫时间
                    String time = sfd.format(date);
                    //联系人
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
                    //通话时间,单位:s
                    String duration = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));

                    call.setTime(time);
                    call.setPhone(number);
                    call.setName(name);
                    call.setDuration(duration);
                    call.setType(type);

                    calls.add(call);
                } while (cursor.moveToNext());

            }


            cursor.close();
            return calls;
        }
    }


    @Override
    protected MainContract.Presenter createPresenter() {
        return new BubblePresenter();
    }
}
