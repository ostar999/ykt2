package com.easefun.polyv.livescenes.chatroom;

import com.easefun.polyv.livescenes.model.PLVSListUsersVO;
import com.easefun.polyv.livescenes.model.PolyvChatFunctionSwitchVO;
import com.easefun.polyv.livescenes.model.PolyvLiveClassDetailVO;
import com.plv.foundationsdk.utils.PLVReflectionUtil;
import com.plv.livescenes.chatroom.PLVChatApiRequestHelper;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.livescenes.model.PLVListUsersVO;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.plv.livescenes.model.PLVPlaybackListVO;
import com.plv.livescenes.playback.video.PLVPlaybackListType;
import com.plv.socket.user.PLVAuthorizationBean;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import org.json.JSONException;

@Deprecated
/* loaded from: classes3.dex */
public class PolyvChatApiRequestHelper {
    private static volatile PolyvChatApiRequestHelper singleton;

    private PolyvChatApiRequestHelper() {
    }

    public static Observable<ResponseBody> closeRoom(String str, boolean z2) {
        return PLVChatApiRequestHelper.closeRoom(str, z2);
    }

    public static PolyvChatApiRequestHelper getInstance() {
        if (singleton == null) {
            synchronized (PolyvChatApiRequestHelper.class) {
                if (singleton == null) {
                    singleton = new PolyvChatApiRequestHelper();
                }
            }
        }
        return singleton;
    }

    public static Observable<PLVSListUsersVO> getListUsers(String str, int i2, int i3) {
        return PLVChatApiRequestHelper.getListUsers(str, i2, i3).map(new Function<PLVListUsersVO, PLVSListUsersVO>() { // from class: com.easefun.polyv.livescenes.chatroom.PolyvChatApiRequestHelper.3
            @Override // io.reactivex.functions.Function
            public PLVSListUsersVO apply(@NonNull PLVListUsersVO pLVListUsersVO) throws Exception {
                return (PLVSListUsersVO) PLVReflectionUtil.copyField(pLVListUsersVO, new PLVSListUsersVO());
            }
        });
    }

    public String generateUser(String str, String str2, String str3, String str4, PLVAuthorizationBean pLVAuthorizationBean, String str5) throws JSONException {
        return PLVChatApiRequestHelper.getInstance().generateUser(str, str2, str3, str4, pLVAuthorizationBean, str5);
    }

    public Observable<ResponseBody> getChatPlaybackMessage(String str, int i2, int i3, int i4, String str2, boolean z2) {
        return PLVChatApiRequestHelper.getInstance().getChatPlaybackMessage(str, i2, i3, i4, str2, z2);
    }

    public Observable<PLVPlaybackListVO> getPlaybackList(String str, int i2, int i3, PLVPlaybackListType pLVPlaybackListType) {
        return PLVChatApiRequestHelper.getInstance().getPlaybackList(str, i2, i3, pLVPlaybackListType);
    }

    public Observable<PolyvChatFunctionSwitchVO> requestFunctionSwitch(String str) {
        return PLVChatApiRequestHelper.getInstance().requestFunctionSwitch(str).map(new Function<PLVChatFunctionSwitchVO, PolyvChatFunctionSwitchVO>() { // from class: com.easefun.polyv.livescenes.chatroom.PolyvChatApiRequestHelper.1
            @Override // io.reactivex.functions.Function
            public PolyvChatFunctionSwitchVO apply(@NonNull PLVChatFunctionSwitchVO pLVChatFunctionSwitchVO) throws Exception {
                return (PolyvChatFunctionSwitchVO) PLVReflectionUtil.copyField(pLVChatFunctionSwitchVO, new PolyvChatFunctionSwitchVO());
            }
        });
    }

    public Observable<PolyvLiveClassDetailVO> requestLiveClassDetailApi(String str, String str2, String str3) {
        return PLVChatApiRequestHelper.getInstance().requestLiveClassDetailApi(str, str2, str3).map(new Function<PLVLiveClassDetailVO, PolyvLiveClassDetailVO>() { // from class: com.easefun.polyv.livescenes.chatroom.PolyvChatApiRequestHelper.2
            @Override // io.reactivex.functions.Function
            public PolyvLiveClassDetailVO apply(@NonNull PLVLiveClassDetailVO pLVLiveClassDetailVO) throws Exception {
                PolyvLiveClassDetailVO polyvLiveClassDetailVO = new PolyvLiveClassDetailVO();
                polyvLiveClassDetailVO.setCode(pLVLiveClassDetailVO.getCode());
                polyvLiveClassDetailVO.setData(pLVLiveClassDetailVO.getData());
                polyvLiveClassDetailVO.setMessage(pLVLiveClassDetailVO.getMessage());
                polyvLiveClassDetailVO.setStatus(pLVLiveClassDetailVO.getStatus());
                return polyvLiveClassDetailVO;
            }
        });
    }

    public Observable<ResponseBody> sendChatPlaybackMessage(String str, String str2, int i2, String str3, String str4, String str5, String str6) {
        return PLVChatApiRequestHelper.getInstance().sendChatPlaybackMessage(str, str2, i2, str3, str4, str5, str6);
    }

    public static Observable<PLVListUsersVO> getListUsers(String str, int i2, int i3, int i4, boolean z2, String str2) {
        return PLVChatApiRequestHelper.getListUsers(str, i2, i3, i4, z2, str2);
    }
}
