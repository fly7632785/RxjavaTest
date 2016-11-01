package com.jafir.rxjavatest.chat;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by jafir on 16/9/22.
 */
public class GroupApiManager {
    /**
     * 创建群组
     *
     * @param groupName  群组名称
     * @param desc       群组简介
     * @param allMembers 群组初始成员，如果只有自己传空数组即可
     * @param reason     邀请成员加入的reason
     * @param option     群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupStyle}
     * @return 创建好的group
     * @throws HyphenateException
     */
    public static EMGroup createGroup() {

        EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
        option.maxUsers = 200;
        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
        try {
            return EMClient.getInstance().groupManager().createGroup("mylove", "这是我们俩的小天地", new String[]{}, "快进来吧", option);
        } catch (HyphenateException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void add(String groupId, String[] newmembers) {
        //群主加人调用此方法
        try {
            EMClient.getInstance().groupManager().addUsersToGroup(groupId, newmembers);//需异步处理
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }


}
