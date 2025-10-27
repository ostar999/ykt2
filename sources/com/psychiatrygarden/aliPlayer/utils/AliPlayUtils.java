package com.psychiatrygarden.aliPlayer.utils;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.player.source.VidSts;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AliPlayUtils {
    static VidSts vidSts;

    public static String getDownloadVideoDefinition(Context context) {
        return SharePreferencesUtils.readStrConfig(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "") + "_download_definition", context, VideoDefinition.LD.getDefinition());
    }

    public static String getDownloadVideoDefinitionLabel(Context context) {
        return SharePreferencesUtils.readStrConfig(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "") + "_download_definition_label", context, VideoDefinition.LD.getLabel());
    }

    public static String getPlayVideoDefinition(Context context) {
        return SharePreferencesUtils.readStrConfig(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "") + "_play_definition", context, VideoDefinition.LD.getDefinition());
    }

    public static String getPlayVideoDefinitionLabel(Context context) {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "");
        VideoDefinition videoDefinition = VideoDefinition.LD;
        videoDefinition.getLabel();
        return SharePreferencesUtils.readStrConfig(strConfig + "_play_definition_label", context, videoDefinition.getLabel());
    }

    public static VidSts getVidSts(String vid) {
        String strPostsync;
        try {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("vid", "" + vid);
            strPostsync = YJYHttpUtils.postsync(ProjectApp.instance(), NetworkRequestsURL.getCourseAkApi, ajaxParams);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (strPostsync == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(strPostsync);
        if (jSONObject.optString("code").equals("200")) {
            JSONObject jSONObject2 = new JSONObject(jSONObject.optString("data"));
            String strOptString = jSONObject2.optString("akId");
            String strOptString2 = jSONObject2.optString("akSecret");
            String strOptString3 = jSONObject2.optString("st");
            VidSts vidSts2 = new VidSts();
            vidSts = vidSts2;
            vidSts2.setVid(vid);
            vidSts.setAccessKeyId(DesUtil.decode(CommonParameter.DES_KEY_ALI, strOptString));
            vidSts.setAccessKeySecret(DesUtil.decode(CommonParameter.DES_KEY_ALI, strOptString2));
            vidSts.setSecurityToken(DesUtil.decode(CommonParameter.DES_KEY_ALI, strOptString3));
            return vidSts;
        }
        return null;
    }

    public static void saveDownloadVideoDefinition(String definition, Context context) {
        if (TextUtils.isEmpty(definition)) {
            return;
        }
        SharePreferencesUtils.writeStrConfig(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "") + "_download_definition", definition, context);
    }

    public static void saveDownloadVideoDefinitionLabel(String definition, Context context) {
        if (TextUtils.isEmpty(definition)) {
            return;
        }
        SharePreferencesUtils.writeStrConfig(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "") + "_download_definition_label", definition, context);
    }

    public static void savePlayVideoDefinition(String definition, Context context) {
        if (TextUtils.isEmpty(definition)) {
            return;
        }
        SharePreferencesUtils.writeStrConfig(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "") + "_play_definition", definition, context);
    }

    public static void savePlayVideoDefinitionLabel(String definition, Context context) {
        if (TextUtils.isEmpty(definition)) {
            return;
        }
        SharePreferencesUtils.writeStrConfig(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "") + "_play_definition_label", definition, context);
    }
}
