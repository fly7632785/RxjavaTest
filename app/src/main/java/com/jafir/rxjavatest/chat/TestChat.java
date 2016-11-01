package com.jafir.rxjavatest.chat;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMChatRoomChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.jafir.rxjavatest.R;
import com.jafir.rxjavatest.base.BaseActivity;
import com.jafir.rxjavatest.base.BasePresenter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jafir on 16/9/21.
 */
public class TestChat extends BaseActivity {


    private static final int CHAT_ROOM = 1;
    //    RecyclerView recyclerView;
    List<ChatMessage> mMessage = new ArrayList<>();
    private EMMessageListener msgListener;
//    private String mRoomid = "243557204861387184";
    private String mRoomid = "246789127884767664";

    private InputLayout mInputLayout;
    //    private MyChatAdapter adapter;
    private EMChatRoomChangeListener emChatRoomChangeListener;
    private ListView mListView;
    private MyListAdapter mListAdapter;
    private String mMyUsername = "fly7632785";
    private String mMyPassword = "7632785";
    private EMGroup emGroup;


    @Override
    protected void beforSetContentView() {
        super.beforSetContentView();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hide();
    }



    @Override
    public boolean onNavigateUp() {
        Logger.d("onNavigateUp");
        return super.onNavigateUp();
    }

    @Override
    public boolean onNavigateUpFromChild(Activity child) {
        Logger.d("onNavigateUpFromChild");
        return super.onNavigateUpFromChild(child);
    }

    private void hide() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    protected int createLayoutView(){
        return R.layout.aty_chat;
    }


    @Override
    protected void initView() {
//        adapter = new MyChatAdapter(mMessage);
//        recyclerView = (RecyclerView) findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
//        recyclerView.setAdapter(adapter);

        mListView = (ListView) findViewById(R.id.listview);
        mListAdapter = new MyListAdapter(mMessage);
        mListView.setDividerHeight(0);
        mListView.setAdapter(mListAdapter);


        mInputLayout = (InputLayout) findViewById(R.id.input_layout);
        mInputLayout.setOnSendListener(new InputLayout.OnSenListener() {
            @Override
            public void onSend(String s) {
                send(s, mRoomid, CHAT_ROOM);

            }

            @Override
            public void onHide() {
                hide();
            }
        });
        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputLayout.open();
            }
        });
        findViewById(R.id.open1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                register();
                login();
            }
        }).start();


    }

    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(TestChat.this, "发送成功", Toast.LENGTH_SHORT).show();
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setType(2);
                    chatMessage.setName(((EMMessage) msg.obj).getFrom());
                    chatMessage.setWords(((EMMessage) msg.obj).getBody().toString());

//                    adapter.add(0, chatMessage);
                    mListAdapter.add(chatMessage);
//                    recyclerView.smoothScrollToPosition(0);
                    mListView.smoothScrollToPosition(mListView.getAdapter().getCount() - 1);
                    mInputLayout.clearText();
                    break;
                case 2:
                    Toast.makeText(TestChat.this, "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(TestChat.this, msg.obj.toString() + "进来", Toast.LENGTH_SHORT).show();
                    ChatMessage in = new ChatMessage();
                    in.setType(1);
                    in.setName("直播消息");
                    in.setWords(msg.obj.toString() + "加入了房间");
                    Logger.d(msg.obj.toString() + "加入了房间");
//                    adapter.add(0, in);
                    mListAdapter.add(in);
                    mListView.smoothScrollToPosition(mListView.getAdapter().getCount() - 1);
//                    recyclerView.smoothScrollToPosition(0);
                    break;
                case 4:
                    Toast.makeText(TestChat.this, msg.obj.toString() + "出去", Toast.LENGTH_SHORT).show();
                    Logger.d(msg.obj.toString() + "离开了房间");
                    ChatMessage out = new ChatMessage();
                    out.setType(1);
                    out.setName("直播消息");
                    out.setWords(msg.obj.toString() + "离开了房间");
//                    adapter.add(0, out);
                    mListAdapter.add(out);
//                    recyclerView.smoothScrollToPosition(0);
                    mListView.smoothScrollToPosition(mListView.getAdapter().getCount() - 1);
                    break;
                case 5:
//                    登录成功
                    go();
                    break;
                case 6:
//                    登录失败
                    break;
                case 7:
//                    加入聊天室成功
                    Toast.makeText(TestChat.this, "加入聊天室成功", Toast.LENGTH_SHORT).show();
                    break;
                case 8:
//                    加入聊天室失败
                    Toast.makeText(TestChat.this, "加入聊天室失败", Toast.LENGTH_SHORT).show();
                    break;
                case 9:
//                    获取到聊天室列别
                    addChatRoom(mRoomid);
                    break;
                case 10:
                    List<ChatMessage> addData = (List<ChatMessage>) msg.obj;
                    mListAdapter.addData(addData);
                    mListView.smoothScrollToPosition(mListView.getAdapter().getCount() - 1);
                    break;

            }

        }
    };


    private void go() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getChatRoomList();
//            }
//        }).start();

        addChatRoom(mRoomid);
        addRoomListener();
        addMessageListener();
    }

    private void addMessageListener() {
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                Logger.d("收到消息:");
                List<ChatMessage> addData = new ArrayList<>();
                for (EMMessage message : messages) {
                    ChatMessage chatMessage = new ChatMessage();
                    if (message.getFrom().equals(mMyUsername)) {
                        chatMessage.setType(2);
                    }
                    chatMessage.setName(message.getFrom());
                    EMTextMessageBody body = (EMTextMessageBody) message.getBody();
                    chatMessage.setWords(body.getMessage());
                    addData.add(chatMessage);
                }
//                adapter.addDataReverse(addData);
//                mListAdapter.addData(addData);
//                recyclerView.smoothScrollToPosition(0);
                Message message1 = new Message();
                message1.obj = addData;
                message1.what = 10;
                handler.sendMessage(message1);

                //收到消息
                for (EMMessage message : messages) {
                    Logger.d("收到消息:" + message.getUserName() + ":" + ((EMTextMessageBody) message.getBody()).getMessage().toString());
                }

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
                Logger.d("消息状态变动:");
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    private void addRoomListener() {
        emChatRoomChangeListener = new EMChatRoomChangeListener() {

            @Override
            public void onChatRoomDestroyed(String roomId, String roomName) {
                if (roomId.equals(mRoomid)) {
//                    finish();
                }
            }

            @Override
            public void onMemberJoined(String roomId, String participant) {
                Message message = new Message();
                message.what = 3;
                message.obj = participant;
                handler.sendMessage(message);
            }

            @Override
            public void onMemberExited(String roomId, String roomName,
                                       String participant) {
                Message message = new Message();
                message.what = 4;
                message.obj = participant;
                handler.sendMessage(message);

            }

            @Override
            public void onMemberKicked(String roomId, String roomName,
                                       String participant) {
                if (roomId.equals(mRoomid)) {
//                    finish();
                }
            }

        };

        EMClient.getInstance().chatroomManager().addChatRoomChangeListener(emChatRoomChangeListener);
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    private void getChatRoomList() {
        try {
            EMCursorResult<EMChatRoom> cursorResult = EMClient.getInstance().chatroomManager().fetchPublicChatRoomsFromServer(10, null);
            List<EMChatRoom> chatRooms = cursorResult.getData();

            for (int i = 0; i < chatRooms.size(); i++) {
                Logger.d("cur" + chatRooms.get(i).getName());
            }

            EMChatRoom room = chatRooms.get(1);
            mRoomid = room.getId();
            Logger.d("roomin:" + mRoomid);
            handler.sendEmptyMessage(9);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    private void addChatRoom(String roomId) {
        //roomId为聊天室ID
        EMClient.getInstance().chatroomManager().joinChatRoom(roomId, new EMValueCallBack<EMChatRoom>() {

            @Override
            public void onSuccess(EMChatRoom value) {
                //加入聊天室成功
                Logger.d("加入聊天室成功" + value.getName());
                handler.sendEmptyMessage(7);
            }

            @Override
            public void onError(final int error, String errorMsg) {
                //加入聊天室失败
                Logger.d("加入聊天室shibai");
                handler.sendEmptyMessage(8);
            }
        });

    }


    private void send(String content, String id, int chatType) {
//创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, id);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Logger.d("发送成功");
                Message message1 = new Message();
                message1.obj = message;
                message1.what = 1;
                handler.sendMessage(message1);

            }

            @Override
            public void onError(int i, String s) {
                handler.sendEmptyMessage(2);

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
//如果是群聊，设置chattype，默认是单聊
        if (chatType == CHAT_ROOM)
            message.setChatType(EMMessage.ChatType.ChatRoom);
//发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        EMClient.getInstance().chatManager().setMessageListened(message);
    }

    private void register() {
//注册失败会抛出HyphenateException
        try {
            EMClient.getInstance().createAccount("fly7632785", "7632785");//同步方法
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        EMClient.getInstance().login(mMyUsername, mMyPassword, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                handler.sendEmptyMessage(5);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
                handler.sendEmptyMessage(6);
            }
        });
    }

    private void logOut() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub

            }
        });
    }


    class MyChatAdapter extends RecyclerView.Adapter<MyChatAdapter.MyHolder> {

        private List<ChatMessage> mData;

        public MyChatAdapter(List<ChatMessage> data) {
            mData = data;
        }

        public void remove(int position) {
            this.mData.remove(position);
            this.notifyItemRemoved(position);
        }

        public void add(int position, ChatMessage item) {
            this.mData.add(position, item);
            this.notifyItemInserted(position);
        }

        public void addReverse(ChatMessage item) {
            this.mData.add(item);
            this.notifyDataSetChanged();
        }

        public void setNewData(List<ChatMessage> data) {
            this.mData = data;

            this.notifyDataSetChanged();
        }

        public void addData(List<ChatMessage> data) {
            this.mData.addAll(data);
            this.notifyDataSetChanged();
        }

        public void addDataReverse(List<ChatMessage> data) {
            this.mData.addAll(data);
            Collections.reverse(mData);
            this.notifyDataSetChanged();
        }


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.item_chat, null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            ChatMessage emMessage = mData.get(position);
            String color = "#44324f";
            if (emMessage.getType() == 1) {
                color = "#ffa100";//系统提示 颜色不同
            }
            String s = "<font color=\"#28b4a9\">" + emMessage.getName()
                    + " : </font><font color=\"" + color + "\">"
                    + emMessage.getWords() + "</font>";

            holder.textView.setText(Html.fromHtml(s));
        }


        @Override
        public int getItemCount() {
            return mData.size();
        }

        protected class MyHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public MyHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }


    class MyListAdapter extends BaseAdapter {

        private List<ChatMessage> mData;

        public MyListAdapter(List<ChatMessage> mData) {
            this.mData = mData;
        }

        public void add(ChatMessage item) {
            this.mData.add(item);
            this.notifyDataSetChanged();
        }


        public void setNewData(List<ChatMessage> data) {
            this.mData = data;
            this.notifyDataSetChanged();
        }

        public void addData(List<ChatMessage> data) {
            this.mData.addAll(data);
            this.notifyDataSetChanged();
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

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(parent.getContext(), R.layout.item_chat, null);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ChatMessage emMessage = mData.get(position);
            String color = "#44324f";
            if (emMessage.getType() == 1) {
                color = "#ffa100";//系统提示 颜色不同
            }
            String s = "<font color=\"#28b4a9\">" + emMessage.getName()
                    + " : </font><font color=\"" + color + "\">"
                    + emMessage.getWords() + "</font>";

            holder.textView.setText(Html.fromHtml(s));

            return convertView;
        }

        private class ViewHolder {
            TextView textView;
        }

    }


    @Override
    public void onBackPressed() {
        EMClient.getInstance().chatroomManager().leaveChatRoom(mRoomid);
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        EMClient.getInstance().chatroomManager().removeChatRoomChangeListener(emChatRoomChangeListener);
        logOut();
        super.onBackPressed();

    }

}
