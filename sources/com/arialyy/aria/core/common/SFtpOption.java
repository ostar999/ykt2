package com.arialyy.aria.core.common;

import android.text.TextUtils;
import com.arialyy.aria.core.FtpUrlEntity;
import com.arialyy.aria.core.IdEntity;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.ComponentUtil;

/* loaded from: classes2.dex */
public class SFtpOption extends BaseOption {
    private String charSet;
    private String password;
    private String protocol;
    private FtpUrlEntity urlEntity;
    private String userName;
    private boolean isNeedLogin = false;
    private IdEntity idEntity = new IdEntity();

    public SFtpOption() throws ClassNotFoundException {
        ComponentUtil.getInstance().checkComponentExist(4);
    }

    public SFtpOption charSet(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("字符编码为空");
        }
        this.charSet = str;
        return this;
    }

    public SFtpOption login(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "用户名不能为null");
            return this;
        }
        if (TextUtils.isEmpty(str2)) {
            ALog.e(this.TAG, "密码不能为null");
            return this;
        }
        this.userName = str;
        this.password = str2;
        this.isNeedLogin = true;
        return this;
    }

    public SFtpOption setKnowHostPath(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "knowhost 文件路径为空");
            return this;
        }
        this.idEntity.knowHost = str;
        return this;
    }

    public SFtpOption setProtocol(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "设置协议失败，协议信息为空");
            return this;
        }
        this.protocol = str;
        return this;
    }

    public SFtpOption setPrvKey(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "设置私钥证书失败，证书内容为空");
            return this;
        }
        this.idEntity.prvKey = str;
        return this;
    }

    public SFtpOption setPrvKeyPass(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "设置证书密码失败，证书密码为空");
            return this;
        }
        this.idEntity.prvPass = str;
        return this;
    }

    public SFtpOption setPubKey(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "设置公钥失败，证书内容为空");
            return this;
        }
        this.idEntity.pubKey = str;
        return this;
    }

    public void setUrlEntity(FtpUrlEntity ftpUrlEntity) {
        this.urlEntity = ftpUrlEntity;
        ftpUrlEntity.needLogin = this.isNeedLogin;
        ftpUrlEntity.user = this.userName;
        ftpUrlEntity.password = this.password;
        ftpUrlEntity.idEntity = this.idEntity;
    }
}
