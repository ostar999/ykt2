package com.hyphenate.easeui.domain;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import cn.hutool.core.text.CharPool;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.easeui.utils.HanziToPinyin;
import com.hyphenate.util.EMLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EaseUser implements Serializable {
    private String avatar;
    private String birth;
    private int contact;
    private String email;
    private String ext;
    private int gender;
    private String initialLetter;
    private long lastModifyTimestamp;
    private long modifyInitialLetterTimestamp;
    private String nickname;
    private String phone;
    private String sign;

    @NonNull
    private String username;

    public class GetInitialLetter {
        private String defaultLetter = DictionaryFactory.SHARP;

        public GetInitialLetter() {
        }

        public String getLetter(String str) {
            if (TextUtils.isEmpty(str)) {
                return this.defaultLetter;
            }
            if (Character.isDigit(str.toLowerCase().charAt(0))) {
                return this.defaultLetter;
            }
            String pinyin = HanziToPinyin.getPinyin(str);
            EMLog.e("pinyin", "letter: " + pinyin);
            if (TextUtils.isEmpty(pinyin)) {
                return this.defaultLetter;
            }
            String upperCase = pinyin.substring(0, 1).toUpperCase();
            char cCharAt = upperCase.charAt(0);
            return (cCharAt < 'A' || cCharAt > 'Z') ? this.defaultLetter : upperCase;
        }
    }

    public EaseUser() {
    }

    public static List<EaseUser> parse(List<String> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new EaseUser(it.next()));
            }
        }
        return arrayList;
    }

    public static List<EaseUser> parseUserInfo(Map<String, EMUserInfo> map) {
        ArrayList arrayList = new ArrayList();
        if (map != null && !map.isEmpty()) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                EMUserInfo eMUserInfo = map.get(it.next());
                EaseUser easeUser = new EaseUser(eMUserInfo.getUserId());
                easeUser.setNickname(eMUserInfo.getNickName());
                easeUser.setAvatar(eMUserInfo.getAvatarUrl());
                easeUser.setEmail(eMUserInfo.getEmail());
                easeUser.setGender(eMUserInfo.getGender());
                easeUser.setBirth(eMUserInfo.getBirth());
                easeUser.setSign(eMUserInfo.getSignature());
                easeUser.setExt(eMUserInfo.getExt());
                if (!eMUserInfo.getUserId().equals(EMClient.getInstance().getCurrentUser())) {
                    arrayList.add(easeUser);
                }
            }
        }
        return arrayList;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getBirth() {
        return this.birth;
    }

    public int getContact() {
        return this.contact;
    }

    public String getEmail() {
        return this.email;
    }

    public String getExt() {
        return this.ext;
    }

    public int getGender() {
        return this.gender;
    }

    public String getInitialLetter() {
        String str = this.initialLetter;
        if (str != null && this.lastModifyTimestamp <= this.modifyInitialLetterTimestamp) {
            return str;
        }
        if (TextUtils.isEmpty(this.nickname)) {
            this.initialLetter = getInitialLetter(this.username);
        } else {
            this.initialLetter = getInitialLetter(this.nickname);
        }
        this.modifyInitialLetterTimestamp = System.currentTimeMillis();
        return this.initialLetter;
    }

    public long getLastModifyTimestamp() {
        return this.lastModifyTimestamp;
    }

    public long getModifyInitialLetterTimestamp() {
        return this.modifyInitialLetterTimestamp;
    }

    public String getNickname() {
        return TextUtils.isEmpty(this.nickname) ? this.username : this.nickname;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getSign() {
        return this.sign;
    }

    @NonNull
    public String getUsername() {
        return this.username;
    }

    public void setAvatar(String str) {
        this.avatar = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastModifyTimestamp = jCurrentTimeMillis;
        setLastModifyTimestamp(jCurrentTimeMillis);
    }

    public void setBirth(String str) {
        this.birth = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastModifyTimestamp = jCurrentTimeMillis;
        setLastModifyTimestamp(jCurrentTimeMillis);
    }

    public void setContact(int i2) {
        this.contact = i2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastModifyTimestamp = jCurrentTimeMillis;
        setLastModifyTimestamp(jCurrentTimeMillis);
    }

    public void setEmail(String str) {
        this.email = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastModifyTimestamp = jCurrentTimeMillis;
        setLastModifyTimestamp(jCurrentTimeMillis);
    }

    public void setExt(String str) {
        this.ext = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastModifyTimestamp = jCurrentTimeMillis;
        setLastModifyTimestamp(jCurrentTimeMillis);
    }

    public void setGender(int i2) {
        this.gender = i2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastModifyTimestamp = jCurrentTimeMillis;
        setLastModifyTimestamp(jCurrentTimeMillis);
    }

    public void setInitialLetter(String str) {
        this.initialLetter = str;
        this.modifyInitialLetterTimestamp = System.currentTimeMillis();
    }

    public void setLastModifyTimestamp(long j2) {
        this.lastModifyTimestamp = j2;
    }

    public void setModifyInitialLetterTimestamp(long j2) {
        this.modifyInitialLetterTimestamp = j2;
    }

    public void setNickname(String str) {
        this.nickname = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastModifyTimestamp = jCurrentTimeMillis;
        setLastModifyTimestamp(jCurrentTimeMillis);
    }

    public void setPhone(String str) {
        this.phone = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastModifyTimestamp = jCurrentTimeMillis;
        setLastModifyTimestamp(jCurrentTimeMillis);
    }

    public void setSign(String str) {
        this.sign = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastModifyTimestamp = jCurrentTimeMillis;
        setLastModifyTimestamp(jCurrentTimeMillis);
    }

    public void setUsername(@NonNull String str) {
        this.username = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastModifyTimestamp = jCurrentTimeMillis;
        setLastModifyTimestamp(jCurrentTimeMillis);
    }

    public String toString() {
        return "EaseUser{username='" + this.username + CharPool.SINGLE_QUOTE + ", nickname='" + this.nickname + CharPool.SINGLE_QUOTE + ", initialLetter='" + this.initialLetter + CharPool.SINGLE_QUOTE + ", avatar='" + this.avatar + CharPool.SINGLE_QUOTE + ", email='" + this.email + CharPool.SINGLE_QUOTE + ", phone='" + this.phone + CharPool.SINGLE_QUOTE + ", gender='" + this.gender + CharPool.SINGLE_QUOTE + ", sign='" + this.sign + CharPool.SINGLE_QUOTE + ", birth='" + this.birth + CharPool.SINGLE_QUOTE + ", ext='" + this.ext + CharPool.SINGLE_QUOTE + ", contact=" + this.contact + '}';
    }

    public EaseUser(@NonNull String str) {
        this.username = str;
    }

    public static List<EaseUser> parse(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        if (strArr != null && strArr.length != 0) {
            for (String str : strArr) {
                arrayList.add(new EaseUser(str));
            }
        }
        return arrayList;
    }

    public String getInitialLetter(String str) {
        return new GetInitialLetter().getLetter(str);
    }
}
