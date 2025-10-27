package com.cicada.player.utils.media;

import com.cicada.player.utils.NativeUsed;
import java.util.List;

@NativeUsed
/* loaded from: classes3.dex */
public class EncryptionInfo {
    public String scheme;
    public int crypt_byte_block = 0;
    public int skip_byte_block = 0;
    public byte[] key_id = null;
    public byte[] iv = null;
    public List<SubsampleEncryptionInfo> subsamples = null;

    public void setIv(byte[] bArr) {
        this.iv = bArr;
    }

    public void setKeyId(byte[] bArr) {
        this.key_id = bArr;
    }

    public void setScheme(String str) {
        this.scheme = str;
    }

    public void setSubsamples(Object obj) {
        this.subsamples = (List) obj;
    }
}
