package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.DESParameters;
import org.bouncycastle.crypto.params.DESedeParameters;

/* loaded from: classes9.dex */
public class DESedeKeyGenerator extends DESKeyGenerator {
    @Override // org.bouncycastle.crypto.generators.DESKeyGenerator, org.bouncycastle.crypto.CipherKeyGenerator
    public byte[] generateKey() {
        int i2 = this.strength;
        byte[] bArr = new byte[i2];
        do {
            this.random.nextBytes(bArr);
            DESParameters.setOddParity(bArr);
        } while (DESedeParameters.isWeakKey(bArr, 0, i2));
        return bArr;
    }

    @Override // org.bouncycastle.crypto.generators.DESKeyGenerator, org.bouncycastle.crypto.CipherKeyGenerator
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.random = keyGenerationParameters.getRandom();
        int strength = (keyGenerationParameters.getStrength() + 7) / 8;
        this.strength = strength;
        if (strength == 0 || strength == 21) {
            this.strength = 24;
        } else if (strength == 14) {
            this.strength = 16;
        } else if (strength != 24 && strength != 16) {
            throw new IllegalArgumentException("DESede key must be 192 or 128 bits long.");
        }
    }
}
