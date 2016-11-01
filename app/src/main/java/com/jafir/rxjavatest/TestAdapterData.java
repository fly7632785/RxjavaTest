package com.jafir.rxjavatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jafir on 16/9/30.
 */
public class TestAdapterData extends AppCompatActivity {


    private ListView listView;
    private MyAdapter adapter;
    private View add;
    private View remove;
    private View button;
    private List mList = new ArrayList<>();
    private View button1;
    private View button2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_adapter_data);
        initView();
    }


    private void initView() {

        listView = (ListView) findViewById(R.id.listview);
        Person person1 = new Person("123", 1);
        Person person2 = new Person("123324", 2);
        Person person3 = new Person("1245353", 3);
        mList.add(person1);
        mList.add(person2);
        mList.add(person3);

        adapter = new MyAdapter(mList);
        listView.setAdapter(adapter);


        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person p = (Person) mList.get(2);
                p.setAge(100);
                adapter.notifyDataSetChanged();
                SwipeRefreshLayout swipeRefreshLayout;

            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Person p = (Person) adapter.mData.get(2);
                        p.setAge(1001);
                        Logger.d("age:" + ((Person) mList.get(2)).getAge());
                        adapter.notifyDataSetChanged();
                    }
                });
        button1
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Person p1 = new Person("jafir", 1);
                        Person p2 = new Person("111111", 111);
                        adapter.mData.add(p1);
                        adapter.mData.add(p2);
//                        adapter.notifyDataSetChanged();
                        adapter.notifyDataSetInvalidated();
                    }
                });
        button2
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Logger.d("age:" + ((Person) mList.get(2)).getAge());
                        mList.add(new Person("jafif2", 1));
                        mList.add(new Person("jafif4", 1));
                        adapter.notifyDataSetChanged();
                    }
                });

    }


    class MyAdapter extends BaseAdapter {

        public List mData = new ArrayList<>();

        public MyAdapter(List mData) {
            this.mData.addAll(mData);
        }


        public void setData(List data) {
            this.mData.addAll(data);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Person p
                    = (Person) mData.get(position);
            LinearLayout linea = new LinearLayout(parent.getContext());
            TextView name = new TextView(parent.getContext());
            name.setText(p.getName());
            TextView age = new TextView(parent.getContext());
            age.setText("\n" + p.getAge() + "");
            linea.addView(name);
            linea.addView(age);
            return linea
                    ;
        }
    }


    class Person {


        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
