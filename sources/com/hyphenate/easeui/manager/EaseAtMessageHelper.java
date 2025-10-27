package com.hyphenate.easeui.manager;

import android.text.TextUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;

/* loaded from: classes4.dex */
public class EaseAtMessageHelper {
    private static EaseAtMessageHelper instance;
    private Set<String> atMeGroupList;
    private List<String> toAtUserList = new ArrayList();

    private EaseAtMessageHelper() {
        this.atMeGroupList = null;
        Set<String> atMeGroups = EasePreferenceManager.getInstance().getAtMeGroups();
        this.atMeGroupList = atMeGroups;
        if (atMeGroups == null) {
            this.atMeGroupList = new HashSet();
        }
    }

    public static synchronized EaseAtMessageHelper get() {
        if (instance == null) {
            instance = new EaseAtMessageHelper();
        }
        return instance;
    }

    public void addAtUser(String str) {
        synchronized (this.toAtUserList) {
            if (!this.toAtUserList.contains(str)) {
                this.toAtUserList.add(str);
            }
        }
    }

    public JSONArray atListToJsonArray(List<String> list) {
        JSONArray jSONArray = new JSONArray();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            jSONArray.put(list.get(i2));
        }
        return jSONArray;
    }

    public void cleanToAtUserList() {
        synchronized (this.toAtUserList) {
            this.toAtUserList.clear();
        }
    }

    public boolean containsAtAll(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        sb.append(EaseIM.getInstance().getContext().getString(R.string.all_members));
        return str.contains(sb.toString());
    }

    public boolean containsAtUsername(String str) {
        EaseUser userInfo;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        synchronized (this.toAtUserList) {
            Iterator<String> it = this.toAtUserList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (EaseUserUtils.getUserInfo(next) != null && (userInfo = EaseUserUtils.getUserInfo(next)) != null) {
                    next = userInfo.getNickname();
                }
                if (str.contains(next)) {
                    return true;
                }
            }
            return false;
        }
    }

    public Set<String> getAtMeGroups() {
        return this.atMeGroupList;
    }

    public List<String> getAtMessageUsernames(String str) {
        EaseUser userInfo;
        ArrayList arrayList = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this.toAtUserList) {
            for (String str2 : this.toAtUserList) {
                if (str.contains((EaseUserUtils.getUserInfo(str2) == null || (userInfo = EaseUserUtils.getUserInfo(str2)) == null) ? str2 : userInfo.getNickname())) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(str2);
                }
            }
        }
        return arrayList;
    }

    public boolean hasAtMeMsg(String str) {
        return this.atMeGroupList.contains(str);
    }

    public boolean isAtMeMsg(EMMessage eMMessage) {
        if (EaseUserUtils.getUserInfo(eMMessage.getFrom()) != null) {
            try {
                JSONArray jSONArrayAttribute = eMMessage.getJSONArrayAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG);
                for (int i2 = 0; i2 < jSONArrayAttribute.length(); i2++) {
                    if (jSONArrayAttribute.getString(i2).equals(EMClient.getInstance().getCurrentUser())) {
                        return true;
                    }
                }
            } catch (Exception unused) {
                String stringAttribute = eMMessage.getStringAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, null);
                if (stringAttribute != null && stringAttribute.toUpperCase().equals("ALL")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void parseMessages(List<EMMessage> list) {
        int size = this.atMeGroupList.size();
        for (EMMessage eMMessage : (EMMessage[]) list.toArray(new EMMessage[list.size()])) {
            if (eMMessage.getChatType() == EMMessage.ChatType.GroupChat) {
                String to = eMMessage.getTo();
                try {
                    JSONArray jSONArrayAttribute = eMMessage.getJSONArrayAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG);
                    int i2 = 0;
                    while (true) {
                        if (i2 >= jSONArrayAttribute.length()) {
                            break;
                        }
                        if (EMClient.getInstance().getCurrentUser().equals(jSONArrayAttribute.getString(i2)) && !this.atMeGroupList.contains(to)) {
                            this.atMeGroupList.add(to);
                            break;
                        }
                        i2++;
                    }
                } catch (Exception unused) {
                    String stringAttribute = eMMessage.getStringAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, null);
                    if (stringAttribute != null && stringAttribute.toUpperCase().equals("ALL") && !this.atMeGroupList.contains(to)) {
                        this.atMeGroupList.add(to);
                    }
                }
                if (this.atMeGroupList.size() != size) {
                    EasePreferenceManager.getInstance().setAtMeGroups(this.atMeGroupList);
                }
            }
        }
    }

    public void removeAtMeGroup(String str) {
        if (this.atMeGroupList.contains(str)) {
            this.atMeGroupList.remove(str);
            EasePreferenceManager.getInstance().setAtMeGroups(this.atMeGroupList);
        }
    }
}
