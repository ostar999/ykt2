package com.hyphenate.chat;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMChatRoomChangeListener;
import com.hyphenate.EMResultCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.adapter.EMAChatRoom;
import com.hyphenate.chat.adapter.EMAChatRoomManager;
import com.hyphenate.chat.adapter.EMAChatRoomManagerListener;
import com.hyphenate.chat.adapter.EMAError;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EMChatRoomManager {
    EMAChatRoomManagerListener chatRoomListenerImpl;
    private List<EMChatRoomChangeListener> chatRoomListeners = Collections.synchronizedList(new ArrayList());
    private List<EMChatRoom> chatRooms = Collections.synchronizedList(new ArrayList());
    EMAChatRoomManager emaObject;
    EMClient mClient;
    private ExecutorService threadPool;

    public EMChatRoomManager(EMClient eMClient, EMAChatRoomManager eMAChatRoomManager) {
        this.threadPool = null;
        EMAChatRoomManagerListener eMAChatRoomManagerListener = new EMAChatRoomManagerListener() { // from class: com.hyphenate.chat.EMChatRoomManager.6
            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onAddAdmin(EMAChatRoom eMAChatRoom, String str) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onAdminAdded(eMAChatRoom.getId(), str);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onAddMuteList(EMAChatRoom eMAChatRoom, List<String> list, long j2) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onMuteListAdded(eMAChatRoom.getId(), list, j2);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onAllMemberMuteStateChanged(EMAChatRoom eMAChatRoom, boolean z2) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onAllMemberMuteStateChanged(eMAChatRoom.getId(), z2);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onAnnouncementChanged(EMAChatRoom eMAChatRoom, String str) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onAnnouncementChanged(eMAChatRoom.getId(), str);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onAttributesRemoved(String str, String str2, String str3) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    for (EMChatRoomChangeListener eMChatRoomChangeListener : EMChatRoomManager.this.chatRoomListeners) {
                        List<String> jsonRemove = EMChatRoomManager.this.parseJsonRemove(str2);
                        if (jsonRemove != null) {
                            try {
                                if (jsonRemove.size() > 0) {
                                    eMChatRoomChangeListener.onAttributesRemoved(str, jsonRemove, str3);
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onAttributesUpdate(String str, String str2, String str3) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    for (EMChatRoomChangeListener eMChatRoomChangeListener : EMChatRoomManager.this.chatRoomListeners) {
                        Map<String, String> jsonUpdate = EMChatRoomManager.this.parseJsonUpdate(str2);
                        if (jsonUpdate != null) {
                            try {
                                if (jsonUpdate.size() > 0) {
                                    eMChatRoomChangeListener.onAttributesUpdate(str, jsonUpdate, str3);
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onLeaveChatRoom(EMAChatRoom eMAChatRoom, int i2) {
                EMClient.getInstance().chatManager().caches.remove(eMAChatRoom.getId());
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        for (EMChatRoomChangeListener eMChatRoomChangeListener : EMChatRoomManager.this.chatRoomListeners) {
                            if (i2 == 1) {
                                eMChatRoomChangeListener.onChatRoomDestroyed(eMAChatRoom.getId(), eMAChatRoom.getName());
                            } else {
                                eMChatRoomChangeListener.onRemovedFromChatRoom(i2, eMAChatRoom.getId(), eMAChatRoom.getName(), EMClient.getInstance().getCurrentUser());
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onMemberJoinedChatRoom(EMAChatRoom eMAChatRoom, String str) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onMemberJoined(eMAChatRoom.getId(), str);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onMemberLeftChatRoom(EMAChatRoom eMAChatRoom, String str) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onMemberExited(eMAChatRoom.getId(), eMAChatRoom.getName(), str);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onOwnerChanged(EMAChatRoom eMAChatRoom, String str, String str2) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onOwnerChanged(eMAChatRoom.getId(), str, str2);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onRemoveAdmin(EMAChatRoom eMAChatRoom, String str) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onAdminRemoved(eMAChatRoom.getId(), str);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onRemoveMutes(EMAChatRoom eMAChatRoom, List<String> list) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onMuteListRemoved(eMAChatRoom.getId(), list);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onUpdateSpecificationFromChatroom(EMAChatRoom eMAChatRoom) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    for (EMChatRoomChangeListener eMChatRoomChangeListener : EMChatRoomManager.this.chatRoomListeners) {
                        if (eMAChatRoom != null) {
                            try {
                                eMChatRoomChangeListener.onSpecificationChanged(new EMChatRoom(eMAChatRoom));
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onWhiteListAdded(EMAChatRoom eMAChatRoom, List<String> list) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onWhiteListAdded(eMAChatRoom.getId(), list);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // com.hyphenate.chat.adapter.EMAChatRoomManagerListener, com.hyphenate.chat.adapter.EMAChatRoomManagerListenerInterface
            public void onWhiteListRemoved(EMAChatRoom eMAChatRoom, List<String> list) {
                synchronized (EMChatRoomManager.this.chatRoomListeners) {
                    try {
                        Iterator it = EMChatRoomManager.this.chatRoomListeners.iterator();
                        while (it.hasNext()) {
                            ((EMChatRoomChangeListener) it.next()).onWhiteListRemoved(eMAChatRoom.getId(), list);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        };
        this.chatRoomListenerImpl = eMAChatRoomManagerListener;
        this.emaObject = eMAChatRoomManager;
        eMAChatRoomManager.addListener(eMAChatRoomManagerListener);
        this.mClient = eMClient;
        this.threadPool = Executors.newCachedThreadPool();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, String> fetchChatroomAttributes(String str, List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        String strFetchChatroomAttributes = this.emaObject.fetchChatroomAttributes(str, list, eMAError);
        handleError(eMAError);
        return parseJson(strFetchChatroomAttributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleError(EMAError eMAError) throws HyphenateException {
        if (eMAError.errCode() != 0) {
            throw new HyphenateException(eMAError);
        }
    }

    private void handlePartialError(EMAError eMAError) throws HyphenateException {
        if (eMAError.errCode() != 0 && eMAError.errCode() != 7) {
            throw new HyphenateException(eMAError);
        }
    }

    private Map<String, Integer> parseCodeJson(String str) {
        HashMap map = new HashMap();
        if (TextUtils.isEmpty(str)) {
            return map;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, Integer.valueOf(jSONObject.getInt(next)));
            }
        } catch (JSONException e2) {
            EMLog.e("parseCodeJson", e2.getMessage());
        }
        return map;
    }

    private Map<String, String> parseJson(String str) throws HyphenateException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        HashMap map = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject.getString(next));
            }
            return map;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new HyphenateException(303, "Unknown server error");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> parseJsonRemove(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray = new JSONObject(str).getJSONObject("result").getJSONArray("successKeys");
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                arrayList.add(jSONArray.getString(i2));
            }
            return arrayList;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, String> parseJsonUpdate(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        HashMap map = new HashMap();
        try {
            ArrayList arrayList = new ArrayList();
            JSONObject jSONObject = new JSONObject(str);
            JSONArray jSONArray = jSONObject.getJSONObject("result").getJSONArray("successKeys");
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                arrayList.add(jSONArray.getString(i2));
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("properties");
            Iterator<String> itKeys = jSONObject2.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String string = jSONObject2.getString(next);
                if (arrayList.contains(next)) {
                    map.put(next, string);
                }
            }
            return map;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, Integer> removeChatroomAttribute(String str, List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        String strRemoveChatroomAttributes = this.emaObject.removeChatroomAttributes(str, list, false, eMAError);
        handlePartialError(eMAError);
        return parseCodeJson(strRemoveChatroomAttributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, Integer> removeChatroomAttributeForced(String str, List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        String strRemoveChatroomAttributes = this.emaObject.removeChatroomAttributes(str, list, true, eMAError);
        handlePartialError(eMAError);
        return parseCodeJson(strRemoveChatroomAttributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, Integer> removeChatroomAttributes(String str, List<String> list, EMAError eMAError) {
        return parseCodeJson(this.emaObject.removeChatroomAttributes(str, list, false, eMAError));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, Integer> removeChatroomAttributesForced(String str, List<String> list, EMAError eMAError) {
        return parseCodeJson(this.emaObject.removeChatroomAttributes(str, list, true, eMAError));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, Integer> setChatroomAttribute(String str, Map<String, String> map, boolean z2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        String chatroomAttributes = this.emaObject.setChatroomAttributes(str, toJsonString(map, z2), false, eMAError);
        handlePartialError(eMAError);
        return parseCodeJson(chatroomAttributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, Integer> setChatroomAttributeForced(String str, Map<String, String> map, boolean z2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        String chatroomAttributes = this.emaObject.setChatroomAttributes(str, toJsonString(map, z2), true, eMAError);
        handlePartialError(eMAError);
        return parseCodeJson(chatroomAttributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, Integer> setChatroomAttributes(String str, Map<String, String> map, boolean z2, EMAError eMAError) {
        return parseCodeJson(this.emaObject.setChatroomAttributes(str, toJsonString(map, z2), false, eMAError));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, Integer> setChatroomAttributesForced(String str, Map<String, String> map, boolean z2, EMAError eMAError) {
        return parseCodeJson(this.emaObject.setChatroomAttributes(str, toJsonString(map, z2), true, eMAError));
    }

    private String toJsonString(Map<String, String> map, boolean z2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("metaData", new JSONObject(map));
            jSONObject.put("autoDelete", z2 ? "DELETE" : "NO_DELETE");
            return jSONObject.toString();
        } catch (JSONException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public EMChatRoom addChatRoomAdmin(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomAddChatroomAdmin = this.emaObject.addChatroomAdmin(str, str2, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomAddChatroomAdmin);
    }

    public void addChatRoomChangeListener(EMChatRoomChangeListener eMChatRoomChangeListener) {
        this.chatRoomListeners.add(eMChatRoomChangeListener);
    }

    public void addToChatRoomWhiteList(final String str, final List<String> list, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.22
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMAChatRoom eMAChatRoomAddToWhiteList = EMChatRoomManager.this.emaObject.addToWhiteList(str, list, eMAError);
                    EMChatRoomManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMChatRoom(eMAChatRoomAddToWhiteList));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncAddChatRoomAdmin(final String str, final String str2, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.15
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.addChatRoomAdmin(str, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncBlockChatroomMembers(final String str, final List<String> list, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.19
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.blockChatroomMembers(str, list));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncChangeChatRoomSubject(final String str, final String str2, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.changeChatRoomSubject(str, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncChangeChatroomDescription(final String str, final String str2, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.10
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.changeChatroomDescription(str, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncChangeOwner(final String str, final String str2, final EMValueCallBack<EMChatRoom> eMValueCallBack) throws HyphenateException {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.14
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.changeOwner(str, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncCreateChatRoom(final String str, final String str2, final String str3, final int i2, final List<String> list, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.createChatRoom(str, str2, str3, i2, list));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncDestroyChatRoom(final String str, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatRoomManager.this.destroyChatRoom(str);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchChatRoomAllAttributesFromServer(final String str, @NonNull final EMValueCallBack<Map<String, String>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.35
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.fetchChatroomAttributes(str, null));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchChatRoomAnnouncement(final String str, final EMValueCallBack<String> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.29
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.fetchChatRoomAnnouncement(str));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchChatRoomBlackList(final String str, final int i2, final int i3, final EMValueCallBack<List<String>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.21
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.fetchChatRoomBlackList(str, i2, i3));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchChatRoomFromServer(final String str, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.fetchChatRoomFromServer(str));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchChatRoomMembers(final String str, final String str2, final int i2, final EMValueCallBack<EMCursorResult<String>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.fetchChatRoomMembers(str, str2, i2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchChatRoomMuteList(final String str, final int i2, final int i3, final EMValueCallBack<Map<String, Long>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.17
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.fetchChatRoomMuteList(str, i2, i3));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchChatroomAttributesFromServer(final String str, final List<String> list, @NonNull final EMValueCallBack<Map<String, String>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.34
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.fetchChatroomAttributes(str, list));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchPublicChatRoomsFromServer(final int i2, final int i3, final EMValueCallBack<EMPageResult<EMChatRoom>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.fetchPublicChatRoomsFromServer(i2, i3));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    @Deprecated
    public void asyncFetchPublicChatRoomsFromServer(final int i2, final String str, final EMValueCallBack<EMCursorResult<EMChatRoom>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.fetchPublicChatRoomsFromServer(i2, str));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncMuteChatRoomMembers(final String str, final List<String> list, final long j2, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.12
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.muteChatRoomMembers(str, list, j2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncRemoveChatRoomAdmin(final String str, final String str2, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.16
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.removeChatRoomAdmin(str, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncRemoveChatRoomAttributeFromServer(final String str, final String str2, @NonNull final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.37
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TextUtils.isEmpty(str2)) {
                        eMCallBack.onError(110, "remove Attribute key Cannot be an empty string");
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(str2);
                    EMChatRoomManager.this.removeChatroomAttribute(str, arrayList);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncRemoveChatRoomAttributeFromServerForced(final String str, final String str2, @NonNull final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.39
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TextUtils.isEmpty(str2)) {
                        eMCallBack.onError(110, "remove Attribute key Cannot be an empty string");
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(str2);
                    EMChatRoomManager.this.removeChatroomAttributeForced(str, arrayList);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncRemoveChatRoomAttributesFromServer(final String str, final List<String> list, @NonNull final EMResultCallBack<Map<String, Integer>> eMResultCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.36
            @Override // java.lang.Runnable
            public void run() {
                List list2 = list;
                if (list2 == null || list2.size() == 0) {
                    eMResultCallBack.onResult(110, new HashMap());
                    return;
                }
                EMAError eMAError = new EMAError();
                eMResultCallBack.onResult(eMAError.errCode(), EMChatRoomManager.this.removeChatroomAttributes(str, list, eMAError));
            }
        });
    }

    public void asyncRemoveChatRoomAttributesFromServerForced(final String str, final List<String> list, @NonNull final EMResultCallBack<Map<String, Integer>> eMResultCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.38
            @Override // java.lang.Runnable
            public void run() {
                List list2 = list;
                if (list2 == null || list2.size() == 0) {
                    eMResultCallBack.onResult(110, new HashMap());
                    return;
                }
                EMAError eMAError = new EMAError();
                eMResultCallBack.onResult(eMAError.errCode(), EMChatRoomManager.this.removeChatroomAttributesForced(str, list, eMAError));
            }
        });
    }

    public void asyncRemoveChatRoomMembers(final String str, final List<String> list, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.18
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.removeChatRoomMembers(str, list));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncSetChatroomAttribute(final String str, final String str2, final String str3, final boolean z2, @NonNull final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.31
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TextUtils.isEmpty(str2)) {
                        eMCallBack.onError(110, "add Attributes key Cannot be an empty string");
                        return;
                    }
                    HashMap map = new HashMap();
                    map.put(str2, str3);
                    EMChatRoomManager.this.setChatroomAttribute(str, map, z2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncSetChatroomAttributeForced(final String str, final String str2, final String str3, final boolean z2, @NonNull final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.33
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TextUtils.isEmpty(str2)) {
                        eMCallBack.onError(110, "add Attributes key Cannot be an empty string");
                        return;
                    }
                    HashMap map = new HashMap();
                    map.put(str2, str3);
                    EMChatRoomManager.this.setChatroomAttributeForced(str, map, z2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncSetChatroomAttributes(final String str, final Map<String, String> map, final boolean z2, @NonNull final EMResultCallBack<Map<String, Integer>> eMResultCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.30
            @Override // java.lang.Runnable
            public void run() {
                Map map2 = map;
                if (map2 == null || map2.size() == 0) {
                    eMResultCallBack.onResult(110, new HashMap());
                    return;
                }
                EMAError eMAError = new EMAError();
                eMResultCallBack.onResult(eMAError.errCode(), EMChatRoomManager.this.setChatroomAttributes(str, map, z2, eMAError));
            }
        });
    }

    public void asyncSetChatroomAttributesForced(final String str, final Map<String, String> map, final boolean z2, @NonNull final EMResultCallBack<Map<String, Integer>> eMResultCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.32
            @Override // java.lang.Runnable
            public void run() {
                Map map2 = map;
                if (map2 == null || map2.size() == 0) {
                    eMResultCallBack.onResult(110, new HashMap());
                    return;
                }
                EMAError eMAError = new EMAError();
                eMResultCallBack.onResult(eMAError.errCode(), EMChatRoomManager.this.setChatroomAttributesForced(str, map, z2, eMAError));
            }
        });
    }

    public void asyncUnBlockChatRoomMembers(final String str, final List<String> list, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.20
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.unblockChatRoomMembers(str, list));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncUnMuteChatRoomMembers(final String str, final List<String> list, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.13
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatRoomManager.this.unMuteChatRoomMembers(str, list));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncUpdateChatRoomAnnouncement(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.28
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatRoomManager.this.updateChatRoomAnnouncement(str, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public EMChatRoom blockChatroomMembers(String str, List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomBlockChatroomMembers = this.emaObject.blockChatroomMembers(str, list, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomBlockChatroomMembers);
    }

    public EMChatRoom changeChatRoomSubject(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomChangeChatroomSubject = this.emaObject.changeChatroomSubject(str, str2, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomChangeChatroomSubject);
    }

    public EMChatRoom changeChatroomDescription(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomChangeChatroomDescription = this.emaObject.changeChatroomDescription(str, str2, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomChangeChatroomDescription);
    }

    public EMChatRoom changeOwner(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomTransferChatroomOwner = this.emaObject.transferChatroomOwner(str, str2, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomTransferChatroomOwner);
    }

    public void checkIfInChatRoomWhiteList(final String str, final EMValueCallBack<Boolean> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.24
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    Boolean boolValueOf = Boolean.valueOf(EMChatRoomManager.this.emaObject.checkIfInWhiteList(str, eMAError));
                    EMChatRoomManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(boolValueOf);
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public EMChatRoom createChatRoom(String str, String str2, String str3, int i2, List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomCreateChatRoom = this.emaObject.createChatRoom(str, str2, str3, EMChatRoom.EMChatRoomStyle.EMChatRoomStylePublicOpenJoin.ordinal(), i2, list, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomCreateChatRoom);
    }

    public void destroyChatRoom(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.destroyChatroom(str, eMAError);
        handleError(eMAError);
    }

    public String fetchChatRoomAnnouncement(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        String strFetchChatRoomAnnouncement = this.emaObject.fetchChatRoomAnnouncement(str, eMAError);
        handleError(eMAError);
        return strFetchChatRoomAnnouncement;
    }

    public List<String> fetchChatRoomBlackList(String str, int i2, int i3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        List<String> listFetchChatRoomBlackList = this.emaObject.fetchChatRoomBlackList(str, i2, i3, eMAError);
        handleError(eMAError);
        return listFetchChatRoomBlackList;
    }

    public EMChatRoom fetchChatRoomFromServer(String str) throws HyphenateException {
        return fetchChatRoomFromServer(str, false);
    }

    public EMChatRoom fetchChatRoomFromServer(String str, boolean z2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomFetchChatroomSpecification = this.emaObject.fetchChatroomSpecification(str, eMAError, z2);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomFetchChatroomSpecification);
    }

    public EMCursorResult<String> fetchChatRoomMembers(String str, String str2, int i2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<String> eMCursorResultFetchChatroomMembers = this.emaObject.fetchChatroomMembers(str, str2, i2, eMAError);
        handleError(eMAError);
        return eMCursorResultFetchChatroomMembers;
    }

    public Map<String, Long> fetchChatRoomMuteList(String str, int i2, int i3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        Map<String, Long> mapFetchChatRoomMuteList = this.emaObject.fetchChatRoomMuteList(str, i2, i3, eMAError);
        handleError(eMAError);
        return mapFetchChatRoomMuteList;
    }

    public void fetchChatRoomWhiteList(final String str, final EMValueCallBack<List<String>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.25
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    List<String> listFetchChatRoomWhiteList = EMChatRoomManager.this.emaObject.fetchChatRoomWhiteList(str, eMAError);
                    EMChatRoomManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(listFetchChatRoomWhiteList);
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    @Deprecated
    public EMCursorResult<EMChatRoom> fetchPublicChatRoomsFromServer(int i2, String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<EMAChatRoom> eMCursorResultFetchChatroomsWithCursor = this.emaObject.fetchChatroomsWithCursor(str, i2, eMAError);
        handleError(eMAError);
        EMCursorResult<EMChatRoom> eMCursorResult = new EMCursorResult<>();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((List) eMCursorResultFetchChatroomsWithCursor.getData()).iterator();
        while (it.hasNext()) {
            arrayList.add(new EMChatRoom((EMAChatRoom) it.next()));
        }
        eMCursorResult.setCursor(eMCursorResultFetchChatroomsWithCursor.getCursor());
        eMCursorResult.setData(arrayList);
        this.chatRooms.clear();
        this.chatRooms.addAll(arrayList);
        return eMCursorResult;
    }

    public EMPageResult<EMChatRoom> fetchPublicChatRoomsFromServer(int i2, int i3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMPageResult<EMAChatRoom> eMPageResultFetchChatroomsWithPage = this.emaObject.fetchChatroomsWithPage(i2, i3, eMAError);
        handleError(eMAError);
        List data = eMPageResultFetchChatroomsWithPage.getData();
        int pageCount = eMPageResultFetchChatroomsWithPage.getPageCount();
        EMPageResult<EMChatRoom> eMPageResult = new EMPageResult<>();
        ArrayList arrayList = new ArrayList();
        Iterator it = data.iterator();
        while (it.hasNext()) {
            arrayList.add(new EMChatRoom((EMAChatRoom) it.next()));
        }
        eMPageResult.setPageCount(pageCount);
        eMPageResult.setData(arrayList);
        this.chatRooms.clear();
        this.chatRooms.addAll(arrayList);
        return eMPageResult;
    }

    @Deprecated
    public List<EMChatRoom> getAllChatRooms() {
        return Collections.unmodifiableList(this.chatRooms);
    }

    public EMChatRoom getChatRoom(String str) {
        EMAChatRoom chatroom = this.emaObject.getChatroom(str);
        if (chatroom == null) {
            return null;
        }
        return new EMChatRoom(chatroom);
    }

    public void joinChatRoom(final String str, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        this.threadPool.submit(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.1
            @Override // java.lang.Runnable
            public void run() {
                EMAError eMAError = new EMAError();
                EMChatRoom eMChatRoom = new EMChatRoom(EMChatRoomManager.this.emaObject.joinChatRoom(str, eMAError));
                if (eMAError.errCode() == 0 || eMAError.errCode() == 701) {
                    eMValueCallBack.onSuccess(eMChatRoom);
                } else {
                    eMValueCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                }
            }
        });
    }

    public void leaveChatRoom(final String str) {
        EMChatRoom chatRoom = getChatRoom(str);
        if (chatRoom == null) {
            return;
        }
        EMOptions options = EMClient.getInstance().getOptions();
        boolean zIsChatroomOwnerLeaveAllowed = options.isChatroomOwnerLeaveAllowed();
        String owner = chatRoom.getOwner();
        if (zIsChatroomOwnerLeaveAllowed || !owner.equals(EMSessionManager.getInstance().getLastLoginUser())) {
            if (options.isDeleteMessagesAsExitChatRoom()) {
                EMClient.getInstance().chatManager().deleteConversation(str, true);
            }
            this.threadPool.submit(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.2
                @Override // java.lang.Runnable
                public void run() {
                    EMChatRoomManager.this.emaObject.leaveChatRoom(str, new EMAError());
                }
            });
        }
    }

    public void muteAllMembers(final String str, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.26
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMAChatRoom eMAChatRoomMuteAllMembers = EMChatRoomManager.this.emaObject.muteAllMembers(str, eMAError);
                    EMChatRoomManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMChatRoom(eMAChatRoomMuteAllMembers));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public EMChatRoom muteChatRoomMembers(String str, List<String> list, long j2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomMuteChatroomMembers = this.emaObject.muteChatroomMembers(str, list, j2, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomMuteChatroomMembers);
    }

    public void onLogout() {
        this.chatRoomListeners.clear();
    }

    public EMChatRoom removeChatRoomAdmin(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomRemoveChatRoomAdmin = this.emaObject.removeChatRoomAdmin(str, str2, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomRemoveChatRoomAdmin);
    }

    @Deprecated
    public void removeChatRoomChangeListener(EMChatRoomChangeListener eMChatRoomChangeListener) {
        removeChatRoomListener(eMChatRoomChangeListener);
    }

    public void removeChatRoomListener(EMChatRoomChangeListener eMChatRoomChangeListener) {
        this.chatRoomListeners.remove(eMChatRoomChangeListener);
    }

    public EMChatRoom removeChatRoomMembers(String str, List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomRemoveChatRoomMembers = this.emaObject.removeChatRoomMembers(str, list, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomRemoveChatRoomMembers);
    }

    public void removeFromChatRoomWhiteList(final String str, final List<String> list, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.23
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMAChatRoom eMAChatRoomRemoveFromWhiteList = EMChatRoomManager.this.emaObject.removeFromWhiteList(str, list, eMAError);
                    EMChatRoomManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMChatRoom(eMAChatRoomRemoveFromWhiteList));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public EMChatRoom unMuteChatRoomMembers(String str, List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomUnmuteChatRoomMembers = this.emaObject.unmuteChatRoomMembers(str, list, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomUnmuteChatRoomMembers);
    }

    public EMChatRoom unblockChatRoomMembers(String str, List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAChatRoom eMAChatRoomUnblockChatRoomMembers = this.emaObject.unblockChatRoomMembers(str, list, eMAError);
        handleError(eMAError);
        return new EMChatRoom(eMAChatRoomUnblockChatRoomMembers);
    }

    public void unmuteAllMembers(final String str, final EMValueCallBack<EMChatRoom> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatRoomManager.27
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMAChatRoom eMAChatRoomUnmuteAllMembers = EMChatRoomManager.this.emaObject.unmuteAllMembers(str, eMAError);
                    EMChatRoomManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMChatRoom(eMAChatRoomUnmuteAllMembers));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void updateChatRoomAnnouncement(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.updateChatRoomAnnouncement(str, str2, eMAError);
        handleError(eMAError);
    }
}
