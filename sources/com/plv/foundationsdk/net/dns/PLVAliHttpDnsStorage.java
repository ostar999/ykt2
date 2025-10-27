package com.plv.foundationsdk.net.dns;

import android.util.Base64;
import com.plv.foundationsdk.component.livedata.PLVAutoSaveLiveData;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public enum PLVAliHttpDnsStorage {
    INSTANCE;

    private static final long KEY_EXPIRE_TIME = TimeUnit.DAYS.toMillis(1);
    private final PLVAutoSaveLiveData<Param> paramLiveData = new PLVAutoSaveLiveData<Param>("plv_ali_http_dns_storage_param") { // from class: com.plv.foundationsdk.net.dns.PLVAliHttpDnsStorage.1
    };

    public static class Param {
        private String key;
        private Long lastAccessTimestamp;

        private Param() {
            this.key = null;
            this.lastAccessTimestamp = Long.valueOf(System.currentTimeMillis());
        }

        public Param setKey(String str) {
            this.key = str;
            return this;
        }

        public Param setLastAccessTimestamp(long j2) {
            this.lastAccessTimestamp = Long.valueOf(j2);
            return this;
        }
    }

    PLVAliHttpDnsStorage() {
    }

    private static String decData(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(decode64("cG9seXZsaXZlNzY1NDMyMQ==").getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(decode64("MTExMTAwMDAxMTExMDAxMQ==").getBytes());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            return decode64(cipher.doFinal(ConvertUtils.hexString2Bytes(str)));
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return null;
        }
    }

    private static String decode64(String str) {
        try {
            return new String(Base64.decode(str, 0), "UTF-8");
        } catch (Exception unused) {
            return null;
        }
    }

    public String getAccountId() {
        return decode64("MTIzMDE4");
    }

    public String getKey() {
        return decData((String) PLVSugarUtil.getNullableOrDefault(new PLVSugarUtil.Supplier<String>() { // from class: com.plv.foundationsdk.net.dns.PLVAliHttpDnsStorage.2
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
            public String get() {
                if (System.currentTimeMillis() - ((Param) PLVAliHttpDnsStorage.this.paramLiveData.getValue()).lastAccessTimestamp.longValue() > PLVAliHttpDnsStorage.KEY_EXPIRE_TIME) {
                    return null;
                }
                return ((Param) PLVAliHttpDnsStorage.this.paramLiveData.getValue()).key;
            }
        }, decode64("NDBjZjRmYWMyZjRkMTY3YThkOTU2OGZmZTFjYmVmOTBiMzBjYjVjZmE4YzE3N2ZmYzcwMzBmNjIyYTgzZjBiOWFiMDAyNTYwZWU2OTYzMGVlZWY4Y2E5MjMyMTUzNzdi")));
    }

    public void setKey(String str) {
        PLVAutoSaveLiveData<Param> pLVAutoSaveLiveData = this.paramLiveData;
        pLVAutoSaveLiveData.postValue(((Param) PLVSugarUtil.getOrDefault(pLVAutoSaveLiveData.getValue(), new Param())).setKey(str).setLastAccessTimestamp(System.currentTimeMillis()));
    }

    private static String decode64(byte[] bArr) {
        try {
            return new String(Base64.decode(bArr, 0), "UTF-8");
        } catch (Exception unused) {
            return null;
        }
    }
}
