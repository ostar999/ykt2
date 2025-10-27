package com.easefun.polyv.livecommon.module.utils;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.collection.SimpleArrayMap;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVLiveLocalActionHelper {
    private static volatile PLVLiveLocalActionHelper INSTANCE = null;
    private static final int MAX_COUNT = 10;
    private static final String NAME = "PLVLocationAction";
    private String currentChannel;
    private SharedPreferences sp = Utils.getApp().getSharedPreferences(NAME, 0);
    private SimpleArrayMap<String, Action> actions = new SimpleArrayMap<>(10);

    public static class Action {
        public int bitrate;
        String channel;
        public boolean isPortrait;
        long timestamp = System.currentTimeMillis();
        public boolean isEnableCamera = true;
        public boolean isFrontCamera = true;
        public int pptType = 0;

        public Action(String channel) {
            this.channel = channel;
        }
    }

    private PLVLiveLocalActionHelper() {
        Map<String, ?> all = this.sp.getAll();
        long jCurrentTimeMillis = System.currentTimeMillis() - 86400000;
        SharedPreferences.Editor editorEdit = this.sp.edit();
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            Action action = (Action) PLVGsonUtil.fromJson(Action.class, (String) entry.getValue());
            if (all.size() < 10 || action == null || action.timestamp > jCurrentTimeMillis) {
                this.actions.put(entry.getKey(), action);
            } else {
                editorEdit.remove(entry.getKey());
            }
        }
        editorEdit.commit();
    }

    public static PLVLiveLocalActionHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (PLVLiveLocalActionHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVLiveLocalActionHelper();
                }
            }
        }
        return INSTANCE;
    }

    private void saveAction(Action action) {
        this.sp.edit().putString(this.currentChannel, PLVGsonUtil.toJson(action)).commit();
    }

    public void enterChannel(String currentChannel) {
        this.currentChannel = currentChannel;
    }

    public Action getChannelAction(String channel) {
        Action action = this.actions.get(channel);
        if (action != null) {
            return action;
        }
        Action action2 = new Action(channel);
        action2.isPortrait = ScreenUtils.isPortrait();
        this.actions.put(channel, action2);
        return action2;
    }

    public String getCurrentChannel() {
        return this.currentChannel;
    }

    public void updateAction(@NonNull Action action) {
        if (action == null) {
            return;
        }
        this.actions.put(action.channel, action);
        saveAction(action);
    }

    public void updateBitrate(int bitrate) {
        Action channelAction = getChannelAction(this.currentChannel);
        channelAction.bitrate = bitrate;
        saveAction(channelAction);
    }

    public void updateCameraDirection(boolean isFrontCamera) {
        Action channelAction = getChannelAction(this.currentChannel);
        channelAction.isFrontCamera = isFrontCamera;
        saveAction(channelAction);
    }

    public void updateCameraEnable(boolean isEnable) {
        Action channelAction = getChannelAction(this.currentChannel);
        channelAction.isEnableCamera = isEnable;
        saveAction(channelAction);
    }

    public void updateOrientation(boolean isPortrait) {
        Action channelAction = getChannelAction(this.currentChannel);
        channelAction.isPortrait = isPortrait;
        saveAction(channelAction);
    }

    public void updatePptType(int type) {
        Action channelAction = getChannelAction(this.currentChannel);
        channelAction.pptType = type;
        saveAction(channelAction);
    }
}
