package org.bouncycastle.crypto.digests;

import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.Digest;

/* loaded from: classes9.dex */
public class NullDigest implements Digest {
    private ByteArrayOutputStream bOut = new ByteArrayOutputStream();

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        byte[] byteArray = this.bOut.toByteArray();
        System.arraycopy(byteArray, 0, bArr, i2, byteArray.length);
        reset();
        return byteArray.length;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.bOut.size();
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.bOut.reset();
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b3) {
        this.bOut.write(b3);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        this.bOut.write(bArr, i2, i3);
    }
}
