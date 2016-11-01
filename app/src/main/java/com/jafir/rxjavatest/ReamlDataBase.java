package com.jafir.rxjavatest;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jafir.rxjavatest.base.BaseActivity;
import com.jafir.rxjavatest.base.BasePresenter;
import com.jafir.rxjavatest.bean.RxEvent;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by jafir on 16/8/1.
 */
public class ReamlDataBase extends BaseActivity {


    private Realm reaml;
    private BottomSheetDialog dialog;

    @Override
    protected int createLayoutView() {
        return R.layout.activity_reaml;
    }

    @Override
    protected void initData() {
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        reaml = Realm.getDefaultInstance();

//
//        TextView textView1 = new TextView(this);
//        textView1.setText("12312321312312312312");
//        AlertDialog d = new AlertDialog.Builder(this)
//                .setView(textView1)
//                .setPositiveButton("ada", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
//                .create();


//        TextView t1 = (TextView) findViewById(R.id.text);
//        t1.setText("\ue415 \ue40a\ue341 127927");

//
//        dialog = new BottomSheetDialog(this);
//        TextView textView = new TextView(this);
//        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
//        textView.setText("12312321312312312312");
//        dialog.setContentView(textView);
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                Log.d("debug", "dismiss");
//                View parent = (View) textView.getParent();
//                BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
//                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            }
//        });
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                Log.d("debug", "cancel");
//                View parent = (View) textView.getParent();
//                BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
//                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            }
//        });
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                d.show();
//            }
//        });
//
//        textView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textView.setText(1111 + "");
//            }
//        });

//        dialog.show();


        View view = findViewById(R.id.read);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read();
//                dialog = new BottomSheetDialog(ReamlDataBase.this);
//                dialog.setContentView(textView);
//                if (dialog != null) {
//                    dialog.show();

//                    return;
//                }
//                dialog = new BottomSheetDialog(ReamlDataBase.this);
//                dialog.setContentView(textView);
//                dialog.show();
            }
        });

        findViewById(R.id.write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                write();
//                d.show();
                startActivity(new Intent(ReamlDataBase.this, RxbusActivity.class));

            }


        });


        RxBus.getDefault().toObservable(RxEvent.class)
                .subscribe(new Action1<RxEvent>() {
                    @Override
                    public void call(RxEvent rxEvent) {
                        Log.d("debug", "xrevent:" + rxEvent.getName());
                        ((TextView) findViewById(R.id.text)).setText("变了");
                    }
                });



        RxBus.getDefault().toObservableSticky(RxEvent.class)        // 建议在Sticky时,在操作符内主动try,catch        
                .map(new Func1<RxEvent, RxEvent>() {
                    @Override
                    public RxEvent call(RxEvent eventSticky) {
                        try {
                            // 变换操作
                            throw new RuntimeException("12312");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return eventSticky;
                    }
                })
                .subscribe(new Action1<RxEvent>() {
                    @Override
                    public void call(RxEvent eventSticky) {
                        try {
                            // 处理接收的事件
                            Log.d("debug","stickrx:"+eventSticky.getName());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private int count;

//    private void write() {
//        reaml.beginTransaction();
//        Classs cls = reaml.createObject(Classs.class);
//        cls.setName("一班");
//        cls.setId(6 + count++);
//        RealmList<Teacher> teachers = new RealmList<>();
//
//        for (int i = 0; i < 3; i++) {
//
//            Teacher teacher = reaml.createObject(Teacher.class);
//            teacher.setName("teacher" + i);
//            teachers.add(teacher);
//        }
//
//        Student stu = reaml.createObject(Student.class);
//        stu.setAge(1);
//        stu.setName("xiaoming");
//        stu.setCls(cls);
//        stu.setTeacher(teachers);
//        reaml.commitTransaction();
//
//
//        RealmMigration migration = new RealmMigration() {
//            @Override
//            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
//
//                // DynamicRealm exposes an editable schema
//                RealmSchema schema = realm.getSchema();
//
//                // Migrate to version 1: Add a new class
//                // Example:
//                // public Person extends RealmObject {
//                //     private String name;
//                //     private int age;
//                //     // getters and setters left out for brevity
//                // }
//                if (oldVersion == 0) {
//                    schema.create("Person")
//                            .addField("name", String.class)
//                            .addField("age", int.class);
//                    oldVersion++;
//                }
//
//                // Migrate to version 2: Add a primary key + object references
//                // Example:
//                // public Person extends RealmObject {
//                //     private String name;
//                //     @PrimaryKey
//                //     private int age;
//                //     private Dog favoriteDog;
//                //     private RealmList<Dog> dogs;
//                //     // getters and setters left out for brevity
//                // }
//                if (oldVersion == 1) {
//                    schema.get("Person")
//                            .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
//                            .addRealmObjectField("favoriteDog", schema.get("Dog"))
//                            .addRealmListField("dogs", schema.get("Dog"));
//                    oldVersion++;
//                }
//            }
//        };
//
//    }

    private void read() {

        List<Student> s = reaml.where(Student.class)
                .equalTo("teacher.name", "teacher" + 1)
                .findAll();
        for (Student student : s) {
            Log.d("debug", "stu:" + student.toString());
            for (Teacher teacher : student.getTeacher()) {
                Log.d("debug", "teacher:" + teacher.toString());
            }
        }
        Log.d("debug", "read:" + s.toString());

    }

    @Override
    protected void initView() {

    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(Emojix.wrap(newBase));
//    }


    @Override
    protected BasePresenter createPresenter() {

        return null;
    }
}
