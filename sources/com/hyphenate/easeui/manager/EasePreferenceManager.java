package com.hyphenate.easeui.manager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import com.hyphenate.easeui.EaseIM;
import java.util.Set;

/* loaded from: classes4.dex */
public class EasePreferenceManager {
    private static final String KEY_AT_GROUPS = "AT_GROUPS";
    private static String SHARED_KEY_SETTING_MERGE_STREAM = "shared_key_setting_merge_stream";
    private static String SHARED_KEY_SETTING_RECORD_ON_SERVER = "shared_key_setting_record_on_server";
    private static EasePreferenceManager instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences mSharedPreferences;

    @SuppressLint({"CommitPrefEdits"})
    private EasePreferenceManager() {
        SharedPreferences sharedPreferences = EaseIM.getInstance().getContext().getSharedPreferences("EM_SP_AT_MESSAGE", 0);
        this.mSharedPreferences = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public static synchronized EasePreferenceManager getInstance() {
        if (instance == null) {
            instance = new EasePreferenceManager();
        }
        return instance;
    }

    public Set<String> getAtMeGroups() {
        return this.mSharedPreferences.getStringSet(KEY_AT_GROUPS, null);
    }

    public String getString(String str) {
        return this.mSharedPreferences.getString(str, "");
    }

    public String getUnSendMsgInfo(String str) {
        return this.mSharedPreferences.getString(str, "");
    }

    public boolean isMergeStream() {
        return this.mSharedPreferences.getBoolean(SHARED_KEY_SETTING_MERGE_STREAM, false);
    }

    public boolean isRecordOnServer() {
        return this.mSharedPreferences.getBoolean(SHARED_KEY_SETTING_RECORD_ON_SERVER, false);
    }

    public void putString(String str, String str2) {
        this.editor.putString(str, str2);
        this.editor.commit();
    }

    public void saveUnSendMsgInfo(String str, String str2) {
        this.editor.putString(str, str2);
        this.editor.apply();
    }

    public void setAtMeGroups(Set<String> set) {
        this.editor.remove(KEY_AT_GROUPS);
        this.editor.putStringSet(KEY_AT_GROUPS, set);
        this.editor.apply();
    }

    public void setMergeStream(boolean z2) {
        this.editor.putBoolean(SHARED_KEY_SETTING_MERGE_STREAM, z2);
        this.editor.apply();
    }

    public void setRecordOnServer(boolean z2) {
        this.editor.putBoolean(SHARED_KEY_SETTING_RECORD_ON_SERVER, z2);
        this.editor.apply();
    }
}
