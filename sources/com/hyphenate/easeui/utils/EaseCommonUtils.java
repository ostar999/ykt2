package com.hyphenate.easeui.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;
import cn.hutool.core.text.StrPool;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.provider.EaseUserProfileProvider;
import com.hyphenate.util.EMLog;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseCommonUtils {
    private static final String TAG = "CommonUtils";

    /* renamed from: com.hyphenate.easeui.utils.EaseCommonUtils$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$EMMessage$Type;

        static {
            int[] iArr = new int[EMMessage.Type.values().length];
            $SwitchMap$com$hyphenate$chat$EMMessage$Type = iArr;
            try {
                iArr[EMMessage.Type.LOCATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.VOICE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.VIDEO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.CUSTOM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.TXT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.FILE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static EMMessage createExpressionMessage(String str, String str2, String str3) {
        EMMessage eMMessageCreateTxtSendMessage = EMMessage.createTxtSendMessage(StrPool.BRACKET_START + str2 + StrPool.BRACKET_END, str);
        if (str3 != null) {
            eMMessageCreateTxtSendMessage.setAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, str3);
        }
        eMMessageCreateTxtSendMessage.setAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, true);
        return eMMessageCreateTxtSendMessage;
    }

    public static float dip2px(Context context, float f2) {
        return TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }

    public static int getChatType(EMConversation eMConversation) {
        if (eMConversation.isGroup()) {
            return eMConversation.getType() == EMConversation.EMConversationType.ChatRoom ? 3 : 2;
        }
        return 1;
    }

    public static EMConversation.EMConversationType getConversationType(int i2) {
        return i2 == 1 ? EMConversation.EMConversationType.Chat : i2 == 2 ? EMConversation.EMConversationType.GroupChat : EMConversation.EMConversationType.ChatRoom;
    }

    public static String getLetter(String str) {
        return new GetInitialLetter(null).getLetter(str);
    }

    public static String getMessageDigest(EMMessage eMMessage, Context context) {
        EaseUser user;
        String string = "";
        switch (AnonymousClass1.$SwitchMap$com$hyphenate$chat$EMMessage$Type[eMMessage.getType().ordinal()]) {
            case 1:
                if (eMMessage.direct() != EMMessage.Direct.RECEIVE) {
                    string = getString(context, R.string.location_prefix);
                    break;
                } else {
                    String string2 = getString(context, R.string.location_recv);
                    EaseUserProfileProvider userProvider = EaseIM.getInstance().getUserProvider();
                    String from = eMMessage.getFrom();
                    if (userProvider != null && userProvider.getUser(from) != null && (user = userProvider.getUser(from)) != null && !TextUtils.isEmpty(user.getNickname())) {
                        from = user.getNickname();
                    }
                    return String.format(string2, from);
                }
            case 2:
                string = getString(context, R.string.picture);
                break;
            case 3:
                string = getString(context, R.string.voice_prefix);
                break;
            case 4:
                string = getString(context, R.string.video);
                break;
            case 5:
                string = getString(context, R.string.custom);
                break;
            case 6:
                EMTextMessageBody eMTextMessageBody = (EMTextMessageBody) eMMessage.getBody();
                if (eMTextMessageBody != null) {
                    if (!eMMessage.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                        if (!eMMessage.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                            if (!eMMessage.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false) || !TextUtils.isEmpty(eMTextMessageBody.getMessage())) {
                                string = eMTextMessageBody.getMessage();
                                break;
                            } else {
                                string = getString(context, R.string.dynamic_expression);
                                break;
                            }
                        } else {
                            string = getString(context, R.string.video_call) + eMTextMessageBody.getMessage();
                            break;
                        }
                    } else {
                        string = getString(context, R.string.voice_call) + eMTextMessageBody.getMessage();
                        break;
                    }
                }
                break;
            case 7:
                string = getString(context, R.string.file);
                break;
            default:
                EMLog.e(TAG, "error, unknow type");
                return "";
        }
        Log.e("TAG", "message text = " + string);
        return string;
    }

    public static float[] getScreenInfo(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        float[] fArr = new float[5];
        if (windowManager != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            fArr[0] = displayMetrics.widthPixels;
            fArr[1] = displayMetrics.heightPixels;
            fArr[2] = displayMetrics.densityDpi;
            fArr[3] = displayMetrics.density;
            fArr[4] = displayMetrics.scaledDensity;
        }
        return fArr;
    }

    public static String getString(Context context, int i2) {
        return context.getResources().getString(i2);
    }

    public static String getTopActivity(Context context) throws SecurityException {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningTasks(1);
        return runningTasks != null ? runningTasks.get(0).topActivity.getClassName() : "";
    }

    public static boolean isNetWorkConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        return context != null && (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
    }

    public static boolean isSdcardExist() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isSilentMessage(EMMessage eMMessage) {
        return eMMessage.getBooleanAttribute("em_ignore_notification", false);
    }

    public static boolean isTimestamp(String str) throws NumberFormatException {
        long j2;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            j2 = Long.parseLong(str);
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            j2 = 0;
        }
        return j2 > 0;
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.hyphenate.easeui.utils.EaseCommonUtils$1GetInitialLetter] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.hyphenate.easeui.utils.EaseCommonUtils$1GetInitialLetter] */
    public static void setUserInitialLetter(EaseUser easeUser) {
        if (TextUtils.isEmpty(easeUser.getNickname())) {
            easeUser.setInitialLetter(!TextUtils.isEmpty(easeUser.getUsername()) ? new Object() { // from class: com.hyphenate.easeui.utils.EaseCommonUtils.1GetInitialLetter
                public String getLetter(String str) {
                    String upperCase;
                    char cCharAt;
                    if (TextUtils.isEmpty(str) || Character.isDigit(str.toLowerCase().charAt(0))) {
                        return DictionaryFactory.SHARP;
                    }
                    String pinyin = HanziToPinyin.getPinyin(str);
                    return (TextUtils.isEmpty(pinyin) || (cCharAt = (upperCase = pinyin.substring(0, 1).toUpperCase()).charAt(0)) < 'A' || cCharAt > 'Z') ? DictionaryFactory.SHARP : upperCase;
                }
            }.getLetter(easeUser.getUsername()) : DictionaryFactory.SHARP);
        } else {
            easeUser.setInitialLetter(new Object() { // from class: com.hyphenate.easeui.utils.EaseCommonUtils.1GetInitialLetter
                public String getLetter(String str) {
                    String upperCase;
                    char cCharAt;
                    if (TextUtils.isEmpty(str) || Character.isDigit(str.toLowerCase().charAt(0))) {
                        return DictionaryFactory.SHARP;
                    }
                    String pinyin = HanziToPinyin.getPinyin(str);
                    return (TextUtils.isEmpty(pinyin) || (cCharAt = (upperCase = pinyin.substring(0, 1).toUpperCase()).charAt(0)) < 'A' || cCharAt > 'Z') ? DictionaryFactory.SHARP : upperCase;
                }
            }.getLetter(easeUser.getNickname()));
        }
    }

    public static float sp2px(Context context, float f2) {
        return TypedValue.applyDimension(2, f2, context.getResources().getDisplayMetrics());
    }

    public static class GetInitialLetter {
        private String defaultLetter;

        private GetInitialLetter() {
            this.defaultLetter = DictionaryFactory.SHARP;
        }

        public String getLetter(String str) {
            if (TextUtils.isEmpty(str)) {
                return this.defaultLetter;
            }
            if (Character.isDigit(str.toLowerCase().charAt(0))) {
                return this.defaultLetter;
            }
            String pinyin = HanziToPinyin.getPinyin(str);
            if (TextUtils.isEmpty(pinyin)) {
                return this.defaultLetter;
            }
            String upperCase = pinyin.substring(0, 1).toUpperCase();
            char cCharAt = upperCase.charAt(0);
            return (cCharAt < 'A' || cCharAt > 'Z') ? this.defaultLetter : upperCase;
        }

        public /* synthetic */ GetInitialLetter(AnonymousClass1 anonymousClass1) {
            this();
        }
    }
}
