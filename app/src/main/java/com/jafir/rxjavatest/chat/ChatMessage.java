package com.jafir.rxjavatest.chat;

/**
 * Created by jafir on 16/9/21.
 */
public class ChatMessage {

    private String name;
    private String words;
    //   判断消息种类 是系统消息还是人的消息
//  0是别人 1是系统 2是自己
    private int type = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {

        switch (type) {
            case 0:
                if (name.length() >= 4) {
                    name = "游客_" + name.substring(name.length() - 4, name.length());
                } else {
                    name = "游客_" + name;
                }
                break;
            case 1:
                break;
            case 2:
                break;
        }
        this.name = name;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public int getType() {
        return type;
    }

    /**
     * 要放在所有set方法最前面执行
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }
}
