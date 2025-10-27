package net.lingala.zip4j.crypto.PBKDF2;

/* loaded from: classes9.dex */
class PBKDF2HexFormatter {
    public boolean fromString(PBKDF2Parameters pBKDF2Parameters, String str) throws NumberFormatException {
        String[] strArrSplit;
        if (pBKDF2Parameters == null || str == null || (strArrSplit = str.split(":")) == null || strArrSplit.length != 3) {
            return true;
        }
        byte[] bArrHex2bin = BinTools.hex2bin(strArrSplit[0]);
        int i2 = Integer.parseInt(strArrSplit[1]);
        byte[] bArrHex2bin2 = BinTools.hex2bin(strArrSplit[2]);
        pBKDF2Parameters.setSalt(bArrHex2bin);
        pBKDF2Parameters.setIterationCount(i2);
        pBKDF2Parameters.setDerivedKey(bArrHex2bin2);
        return false;
    }

    public String toString(PBKDF2Parameters pBKDF2Parameters) {
        return BinTools.bin2hex(pBKDF2Parameters.getSalt()) + ":" + String.valueOf(pBKDF2Parameters.getIterationCount()) + ":" + BinTools.bin2hex(pBKDF2Parameters.getDerivedKey());
    }
}
