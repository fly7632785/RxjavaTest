package com.jafir.rxjavatest.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jafir.rxjavatest.BuildConfig;
import com.jafir.rxjavatest.FlowLayoutTest.FlowLayoutActivity;
import com.jafir.rxjavatest.GifView;
import com.jafir.rxjavatest.R;
import com.jafir.rxjavatest.base.BaseActivity;
import com.jafir.rxjavatest.utils.CalendarText;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;


/**
 * 假如这个activity是用来显示车行详情的
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    private Button button;
    private Button button1;
    private CalendarText calendarText;

    private GifView gifview;
    private ImageView img;


    @Override
    protected int createLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
//        init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view1 = new View(this);
        view1.setBackgroundResource(R.drawable.corner2);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view1,
                params
        );

        Log.w("main", "onCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("main", "pause");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.w("main", "onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("main", "ondestoy");
    }

    @Override
    protected void initView() {
        log(BuildConfig.test);

        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        calendarText = (CalendarText) findViewById(R.id.calendar_text);
        calendarText.setmPaintCoor(Color.CYAN);
        calendarText.setText("wodeddsfsdafdsfasdfsdafsafasfaasdfedsafasdfdsafasfa");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FlowLayoutActivity.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarText.setChoose(!calendarText.isChoose());

            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                calendarText.setSelect(!calendarText.isSelect());

                long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
                long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
                long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;

                log("memory:" + "total:" + total);
                log("memory:" + "max:" + max);
                log("memory:" + "free:" + free);
                return true;
            }
        });

    }


    private int i;

    /**
     * 引用了presenter
     */
    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    private void init() {


        final Observable<Object> subscriber = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext("123");
                    subscriber.onNext("345");
                    subscriber.onNext("1546");
                    subscriber.onCompleted();
                }
            }
        });


        subscriber.repeat(2).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.d("debug", o.toString());
            }
        });

        final Observable<Long> o1 = Observable.defer(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                return Observable.just(System.currentTimeMillis());
            }
        });


        final Observable<Long> o2 = Observable.just(System.currentTimeMillis());


//        final Observable observable = Observable.interval(1, TimeUnit.SECONDS);
//        final Subscriber subscriber1 = new Subscriber() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                Log.d("debug","interval:"+o.toString());
//            }
//        };
//        observable.subscribe(subscriber1);
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                subscriber1.unsubscribe();
//
//                o1.subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        Log.d("debug","o1:"+aLong);
//                    }
//                });
//
//                o2.subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        Log.d("debug","o2:"+aLong);
//                    }
//                });
//            }
//        });


        Observable.just(11).buffer(4);


        mapObserver().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log(integer + "");
            }
        });
        castObserver().subscribe(new Action1<Dog>() {
            @Override
            public void call(Dog dog) {
                log(dog.getName());
            }
        });
        castObserver1().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                log("error" + e);
            }

            @Override
            public void onNext(String s) {
                log(s);
            }
        });


        scanObesr().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("scan" + integer);
            }
        });

        zip().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer o) {

                log("zip:" + o);
            }
        });


    }


    private Observable merge() {
        return Observable.merge(Observable.just(1, 2, 3), Observable.just(4, 6, 7, 7));
    }

    private Observable zip() {
        return Observable.zip(Observable.just(1), Observable.just(2), (Integer i1, Integer i2) -> i1 + i2);

    }


    private Observable<Integer> mapObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer * 10;
            }
        });
    }


    private Observable<Dog> castObserver() {
        return Observable.just(getAnimal())
                .cast(Dog.class);
    }

    private Observable<String> castObserver1() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).cast(String.class);
    }

    //满足条件的第一个
    private Observable<Integer> first() {
        return Observable.just(123, 1, 2, 4).first(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer < 10;
            }
        });
    }

    private Observable<Integer> scanObesr() {
        return Observable.from(new Integer[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2}).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer * integer2;
            }
        });
    }


    Animal getAnimal() {
        return new Dog();
    }

    class Animal {
        protected String name = "Animal";

        Animal() {
            log("create " + name);
        }

        String getName() {
            return name;
        }
    }

    private void log(String s) {
        Log.d("debug", s);
    }

    class Dog extends Animal {
        Dog() {
            name = getClass().getSimpleName();
            Log.d("debug", "create " + name);
        }

    }
}
