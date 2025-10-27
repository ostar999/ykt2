package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.yikaobang.yixue.R2;
import java.lang.reflect.Array;
import kotlin.io.encoding.Base64;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes9.dex */
public class AESEngine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;

    /* renamed from: m1, reason: collision with root package name */
    private static final int f27825m1 = -2139062144;
    private static final int m2 = 2139062143;
    private static final int m3 = 27;
    private int C0;
    private int C1;
    private int C2;
    private int C3;
    private int ROUNDS;
    private int[][] WorkingKey = null;
    private boolean forEncryption;
    private static final byte[] S = {99, 124, 119, 123, -14, 107, 111, -59, TarConstants.LF_NORMAL, 1, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, TarConstants.LF_FIFO, Utf8.REPLACEMENT_BYTE, -9, -52, TarConstants.LF_BLK, -91, -27, -15, 113, -40, TarConstants.LF_LINK, 21, 4, -57, 35, -61, Ascii.CAN, -106, 5, -102, 7, Ascii.DC2, -128, -30, -21, 39, -78, 117, 9, -125, 44, Ascii.SUB, Ascii.ESC, 110, 90, -96, 82, HttpTokens.SEMI_COLON, -42, -77, 41, -29, 47, -124, TarConstants.LF_GNUTYPE_SPARSE, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, TarConstants.LF_GNUTYPE_LONGNAME, TarConstants.LF_PAX_EXTENDED_HEADER_UC, -49, -48, -17, -86, -5, 67, 77, TarConstants.LF_CHR, -123, 69, -7, 2, 127, 80, 60, -97, -88, 81, -93, SignedBytes.MAX_POWER_OF_TWO, -113, -110, -99, 56, -11, PSSSigner.TRAILER_IMPLICIT, -74, -38, 33, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, Base64.padSymbol, 100, 93, Ascii.EM, 115, 96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, Ascii.DC4, -34, 94, 11, -37, -32, TarConstants.LF_SYMLINK, HttpTokens.COLON, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, TarConstants.LF_CONTIG, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 37, 46, Ascii.FS, -90, -76, -58, -24, -35, 116, Ascii.US, TarConstants.LF_GNUTYPE_LONGLINK, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, TarConstants.LF_DIR, 87, -71, -122, -63, Ascii.GS, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, Ascii.RS, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22};
    private static final byte[] Si = {82, 9, 106, -43, TarConstants.LF_NORMAL, TarConstants.LF_FIFO, -91, 56, -65, SignedBytes.MAX_POWER_OF_TWO, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, 47, -1, -121, TarConstants.LF_BLK, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, TarConstants.LF_SYMLINK, -90, -62, 35, Base64.padSymbol, -18, TarConstants.LF_GNUTYPE_LONGNAME, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, PSSSigner.TRAILER_IMPLICIT, -45, 10, -9, -28, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 5, -72, -77, 69, 6, -48, 44, Ascii.RS, -113, -54, Utf8.REPLACEMENT_BYTE, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, HttpTokens.COLON, -111, 17, 65, 79, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, TarConstants.LF_DIR, -123, -30, -7, TarConstants.LF_CONTIG, -24, Ascii.FS, 117, -33, 110, 71, -15, Ascii.SUB, 113, Ascii.GS, 41, -59, -119, 111, -73, 98, 14, -86, Ascii.CAN, -66, Ascii.ESC, -4, 86, 62, TarConstants.LF_GNUTYPE_LONGLINK, -58, -46, 121, 32, -102, -37, -64, -2, TarConstants.LF_PAX_EXTENDED_HEADER_LC, -51, 90, -12, Ascii.US, -35, -88, TarConstants.LF_CHR, -120, 7, -57, TarConstants.LF_LINK, -79, Ascii.DC2, 16, 89, 39, -128, -20, 95, 96, 81, 127, -87, Ascii.EM, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, HttpTokens.SEMI_COLON, 77, -82, 42, -11, -80, -56, -21, -69, 60, -125, TarConstants.LF_GNUTYPE_SPARSE, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, Ascii.DC4, 99, 85, 33, 12, 125};
    private static final int[] rcon = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, R2.anim.widget_zoom_in, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, R2.array.ease_pdf_file_suffix, 145};
    private static final int[] T0 = {-1520213050, -2072216328, -1720223762, -1921287178, 234025727, -1117033514, -1318096930, 1422247313, 1345335392, 50397442, -1452841010, 2099981142, 436141799, 1658312629, -424957107, -1703512340, 1170918031, -1652391393, 1086966153, -2021818886, 368769775, -346465870, -918075506, 200339707, -324162239, 1742001331, -39673249, -357585083, -1080255453, -140204973, -1770884380, 1539358875, -1028147339, 486407649, -1366060227, 1780885068, 1513502316, 1094664062, 49805301, 1338821763, 1546925160, -190470831, 887481809, 150073849, -1821281822, 1943591083, 1395732834, 1058346282, 201589768, 1388824469, 1696801606, 1589887901, 672667696, -1583966665, 251987210, -1248159185, 151455502, 907153956, -1686077413, 1038279391, 652995533, 1764173646, -843926913, -1619692054, 453576978, -1635548387, 1949051992, 773462580, 756751158, -1301385508, -296068428, -73359269, -162377052, 1295727478, 1641469623, -827083907, 2066295122, 1055122397, 1898917726, -1752923117, -179088474, 1758581177, 0, 753790401, 1612718144, 536673507, -927878791, -312779850, -1100322092, 1187761037, -641810841, 1262041458, -565556588, -733197160, -396863312, 1255133061, 1808847035, 720367557, -441800113, 385612781, -985447546, -682799718, 1429418854, -1803188975, -817543798, 284817897, 100794884, -2122350594, -263171936, 1144798328, -1163944155, -475486133, -212774494, -22830243, -1069531008, -1970303227, -1382903233, -1130521311, 1211644016, 83228145, -541279133, -1044990345, 1977277103, 1663115586, 806359072, 452984805, 250868733, 1842533055, 1288555905, 336333848, 890442534, 804056259, -513843266, -1567123659, -867941240, 957814574, 1472513171, -223893675, -2105639172, 1195195770, -1402706744, -413311558, 723065138, -1787595802, -1604296512, -1736343271, -783331426, 2145180835, 1713513028, 2116692564, -1416589253, -2088204277, -901364084, 703524551, -742868885, 1007948840, 2044649127, -497131844, 487262998, 1994120109, 1004593371, 1446130276, 1312438900, 503974420, -615954030, 168166924, 1814307912, -463709000, 1573044895, 1859376061, -273896381, -1503501628, -1466855111, -1533700815, 937747667, -1954973198, 854058965, 1137232011, 1496790894, -1217565222, -1936880383, 1691735473, -766620004, -525751991, -1267962664, -95005012, 133494003, 636152527, -1352309302, -1904575756, -374428089, 403179536, -709182865, -2005370640, 1864705354, 1915629148, 605822008, -240736681, -944458637, 1371981463, 602466507, 2094914977, -1670089496, 555687742, -582268010, -591544991, -2037675251, -2054518257, -1871679264, 1111375484, -994724495, -1436129588, -666351472, 84083462, 32962295, 302911004, -1553899070, 1597322602, -111716434, -793134743, -1853454825, 1489093017, 656219450, -1180787161, 954327513, 335083755, -1281845205, 856756514, -1150719534, 1893325225, -1987146233, -1483434957, -1231316179, 572399164, -1836611819, 552200649, 1238290055, -11184726, 2015897680, 2061492133, -1886614525, -123625127, -2138470135, 386731290, -624967835, 837215959, -968736124, -1201116976, -1019133566, -1332111063, 1999449434, 286199582, -877612933, -61582168, -692339859, 974525996};
    private static final int[] Tinv0 = {1353184337, 1399144830, -1012656358, -1772214470, -882136261, -247096033, -1420232020, -1828461749, 1442459680, -160598355, -1854485368, 625738485, -52959921, -674551099, -2143013594, -1885117771, 1230680542, 1729870373, -1743852987, -507445667, 41234371, 317738113, -1550367091, -956705941, -413167869, -1784901099, -344298049, -631680363, 763608788, -752782248, 694804553, 1154009486, 1787413109, 2021232372, 1799248025, -579749593, -1236278850, 397248752, 1722556617, -1271214467, 407560035, -2110711067, 1613975959, 1165972322, -529046351, -2068943941, 480281086, -1809118983, 1483229296, 436028815, -2022908268, -1208452270, 601060267, -503166094, 1468997603, 715871590, 120122290, 63092015, -1703164538, -1526188077, -226023376, -1297760477, -1167457534, 1552029421, 723308426, -1833666137, -252573709, -1578997426, -839591323, -708967162, 526529745, -1963022652, -1655493068, -1604979806, 853641733, 1978398372, 971801355, -1427152832, 111112542, 1360031421, -108388034, 1023860118, -1375387939, 1186850381, -1249028975, 90031217, 1876166148, -15380384, 620468249, -1746289194, -868007799, 2006899047, -1119688528, -2004121337, 945494503, -605108103, 1191869601, -384875908, -920746760, 0, -2088337399, 1223502642, -1401941730, 1316117100, -67170563, 1446544655, 517320253, 658058550, 1691946762, 564550760, -783000677, 976107044, -1318647284, 266819475, -761860428, -1634624741, 1338359936, -1574904735, 1766553434, 370807324, 179999714, -450191168, 1138762300, 488053522, 185403662, -1379431438, -1180125651, -928440812, -2061897385, 1275557295, -1143105042, -44007517, -1624899081, -1124765092, -985962940, 880737115, 1982415755, -590994485, 1761406390, 1676797112, -891538985, 277177154, 1076008723, 538035844, 2099530373, -130171950, 288553390, 1839278535, 1261411869, -214912292, -330136051, -790380169, 1813426987, -1715900247, -95906799, 577038663, -997393240, 440397984, -668172970, -275762398, -951170681, -1043253031, -22885748, 906744984, -813566554, 685669029, 646887386, -1530942145, -459458004, 227702864, -1681105046, 1648787028, -1038905866, -390539120, 1593260334, -173030526, -1098883681, 2090061929, -1456614033, -1290656305, 999926984, -1484974064, 1852021992, 2075868123, 158869197, -199730834, 28809964, -1466282109, 1701746150, 2129067946, 147831841, -420997649, -644094022, -835293366, -737566742, -696471511, -1347247055, 824393514, 815048134, -1067015627, 935087732, -1496677636, -1328508704, 366520115, 1251476721, -136647615, 240176511, 804688151, -1915335306, 1303441219, 1414376140, -553347356, -474623586, 461924940, -1205916479, 2136040774, 82468509, 1563790337, 1937016826, 776014843, 1511876531, 1389550482, 861278441, 323475053, -1939744870, 2047648055, -1911228327, -1992551445, -299390514, 902390199, -303751967, 1018251130, 1507840668, 1064563285, 2043548696, -1086863501, -355600557, 1537932639, 342834655, -2032450440, -2114736182, 1053059257, 741614648, 1598071746, 1925389590, 203809468, -1958134744, 1100287487, 1895934009, -558691320, -1662733096, -1866377628, 1636092795, 1890988757, 1952214088, 1113045200};

    private int FFmulX(int i2) {
        return (((i2 & f27825m1) >>> 7) * 27) ^ ((m2 & i2) << 1);
    }

    private void decryptBlock(int[][] iArr) {
        int i2 = this.C0;
        int i3 = this.ROUNDS;
        int[] iArr2 = iArr[i3];
        this.C0 = i2 ^ iArr2[0];
        this.C1 ^= iArr2[1];
        this.C2 ^= iArr2[2];
        this.C3 ^= iArr2[3];
        int i4 = i3 - 1;
        while (true) {
            int[] iArr3 = Tinv0;
            int i5 = this.C0 & 255;
            if (i4 <= 1) {
                int iShift = (((iArr3[i5] ^ shift(iArr3[(this.C3 >> 8) & 255], 24)) ^ shift(iArr3[(this.C2 >> 16) & 255], 16)) ^ shift(iArr3[(this.C1 >> 24) & 255], 8)) ^ iArr[i4][0];
                int iShift2 = (((iArr3[this.C1 & 255] ^ shift(iArr3[(this.C0 >> 8) & 255], 24)) ^ shift(iArr3[(this.C3 >> 16) & 255], 16)) ^ shift(iArr3[(this.C2 >> 24) & 255], 8)) ^ iArr[i4][1];
                int iShift3 = (((iArr3[this.C2 & 255] ^ shift(iArr3[(this.C1 >> 8) & 255], 24)) ^ shift(iArr3[(this.C0 >> 16) & 255], 16)) ^ shift(iArr3[(this.C3 >> 24) & 255], 8)) ^ iArr[i4][2];
                int iShift4 = iArr[i4][3] ^ (shift(iArr3[(this.C0 >> 24) & 255], 8) ^ ((iArr3[this.C3 & 255] ^ shift(iArr3[(this.C2 >> 8) & 255], 24)) ^ shift(iArr3[(this.C1 >> 16) & 255], 16)));
                byte[] bArr = Si;
                int i6 = (((bArr[iShift & 255] & 255) ^ ((bArr[(iShift4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift3 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift2 >> 24) & 255] << Ascii.CAN);
                int[] iArr4 = iArr[0];
                this.C0 = iArr4[0] ^ i6;
                this.C1 = ((((bArr[iShift2 & 255] & 255) ^ ((bArr[(iShift >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift4 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift3 >> 24) & 255] << Ascii.CAN)) ^ iArr4[1];
                this.C2 = ((((bArr[iShift3 & 255] & 255) ^ ((bArr[(iShift2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift4 >> 24) & 255] << Ascii.CAN)) ^ iArr4[2];
                this.C3 = ((((bArr[(iShift2 >> 16) & 255] & 255) << 16) ^ ((bArr[iShift4 & 255] & 255) ^ ((bArr[(iShift3 >> 8) & 255] & 255) << 8))) ^ (bArr[(iShift >> 24) & 255] << Ascii.CAN)) ^ iArr4[3];
                return;
            }
            int iShift5 = (((iArr3[i5] ^ shift(iArr3[(this.C3 >> 8) & 255], 24)) ^ shift(iArr3[(this.C2 >> 16) & 255], 16)) ^ shift(iArr3[(this.C1 >> 24) & 255], 8)) ^ iArr[i4][0];
            int iShift6 = (((iArr3[this.C1 & 255] ^ shift(iArr3[(this.C0 >> 8) & 255], 24)) ^ shift(iArr3[(this.C3 >> 16) & 255], 16)) ^ shift(iArr3[(this.C2 >> 24) & 255], 8)) ^ iArr[i4][1];
            int iShift7 = (((iArr3[this.C2 & 255] ^ shift(iArr3[(this.C1 >> 8) & 255], 24)) ^ shift(iArr3[(this.C0 >> 16) & 255], 16)) ^ shift(iArr3[(this.C3 >> 24) & 255], 8)) ^ iArr[i4][2];
            int i7 = i4 - 1;
            int iShift8 = iArr[i4][3] ^ (((iArr3[this.C3 & 255] ^ shift(iArr3[(this.C2 >> 8) & 255], 24)) ^ shift(iArr3[(this.C1 >> 16) & 255], 16)) ^ shift(iArr3[(this.C0 >> 24) & 255], 8));
            this.C0 = (((iArr3[iShift5 & 255] ^ shift(iArr3[(iShift8 >> 8) & 255], 24)) ^ shift(iArr3[(iShift7 >> 16) & 255], 16)) ^ shift(iArr3[(iShift6 >> 24) & 255], 8)) ^ iArr[i7][0];
            this.C1 = (((iArr3[iShift6 & 255] ^ shift(iArr3[(iShift5 >> 8) & 255], 24)) ^ shift(iArr3[(iShift8 >> 16) & 255], 16)) ^ shift(iArr3[(iShift7 >> 24) & 255], 8)) ^ iArr[i7][1];
            this.C2 = (((iArr3[iShift7 & 255] ^ shift(iArr3[(iShift6 >> 8) & 255], 24)) ^ shift(iArr3[(iShift5 >> 16) & 255], 16)) ^ shift(iArr3[(iShift8 >> 24) & 255], 8)) ^ iArr[i7][2];
            int iShift9 = (shift(iArr3[(iShift6 >> 16) & 255], 16) ^ (iArr3[iShift8 & 255] ^ shift(iArr3[(iShift7 >> 8) & 255], 24))) ^ shift(iArr3[(iShift5 >> 24) & 255], 8);
            i4 = i7 - 1;
            this.C3 = iShift9 ^ iArr[i7][3];
        }
    }

    private void encryptBlock(int[][] iArr) {
        int i2 = this.C0;
        int[] iArr2 = iArr[0];
        this.C0 = i2 ^ iArr2[0];
        this.C1 ^= iArr2[1];
        this.C2 ^= iArr2[2];
        this.C3 ^= iArr2[3];
        int i3 = 1;
        while (i3 < this.ROUNDS - 1) {
            int[] iArr3 = T0;
            int iShift = (((iArr3[this.C0 & 255] ^ shift(iArr3[(this.C1 >> 8) & 255], 24)) ^ shift(iArr3[(this.C2 >> 16) & 255], 16)) ^ shift(iArr3[(this.C3 >> 24) & 255], 8)) ^ iArr[i3][0];
            int iShift2 = (((iArr3[this.C1 & 255] ^ shift(iArr3[(this.C2 >> 8) & 255], 24)) ^ shift(iArr3[(this.C3 >> 16) & 255], 16)) ^ shift(iArr3[(this.C0 >> 24) & 255], 8)) ^ iArr[i3][1];
            int iShift3 = (((iArr3[this.C2 & 255] ^ shift(iArr3[(this.C3 >> 8) & 255], 24)) ^ shift(iArr3[(this.C0 >> 16) & 255], 16)) ^ shift(iArr3[(this.C1 >> 24) & 255], 8)) ^ iArr[i3][2];
            int i4 = i3 + 1;
            int iShift4 = iArr[i3][3] ^ (((iArr3[this.C3 & 255] ^ shift(iArr3[(this.C0 >> 8) & 255], 24)) ^ shift(iArr3[(this.C1 >> 16) & 255], 16)) ^ shift(iArr3[(this.C2 >> 24) & 255], 8));
            this.C0 = (((iArr3[iShift & 255] ^ shift(iArr3[(iShift2 >> 8) & 255], 24)) ^ shift(iArr3[(iShift3 >> 16) & 255], 16)) ^ shift(iArr3[(iShift4 >> 24) & 255], 8)) ^ iArr[i4][0];
            this.C1 = (((iArr3[iShift2 & 255] ^ shift(iArr3[(iShift3 >> 8) & 255], 24)) ^ shift(iArr3[(iShift4 >> 16) & 255], 16)) ^ shift(iArr3[(iShift >> 24) & 255], 8)) ^ iArr[i4][1];
            this.C2 = (((iArr3[iShift3 & 255] ^ shift(iArr3[(iShift4 >> 8) & 255], 24)) ^ shift(iArr3[(iShift >> 16) & 255], 16)) ^ shift(iArr3[(iShift2 >> 24) & 255], 8)) ^ iArr[i4][2];
            this.C3 = (((iArr3[iShift4 & 255] ^ shift(iArr3[(iShift >> 8) & 255], 24)) ^ shift(iArr3[(iShift2 >> 16) & 255], 16)) ^ shift(iArr3[(iShift3 >> 24) & 255], 8)) ^ iArr[i4][3];
            i3 = i4 + 1;
        }
        int[] iArr4 = T0;
        int iShift5 = (((iArr4[this.C0 & 255] ^ shift(iArr4[(this.C1 >> 8) & 255], 24)) ^ shift(iArr4[(this.C2 >> 16) & 255], 16)) ^ shift(iArr4[(this.C3 >> 24) & 255], 8)) ^ iArr[i3][0];
        int iShift6 = (((iArr4[this.C1 & 255] ^ shift(iArr4[(this.C2 >> 8) & 255], 24)) ^ shift(iArr4[(this.C3 >> 16) & 255], 16)) ^ shift(iArr4[(this.C0 >> 24) & 255], 8)) ^ iArr[i3][1];
        int iShift7 = (((iArr4[this.C2 & 255] ^ shift(iArr4[(this.C3 >> 8) & 255], 24)) ^ shift(iArr4[(this.C0 >> 16) & 255], 16)) ^ shift(iArr4[(this.C1 >> 24) & 255], 8)) ^ iArr[i3][2];
        int i5 = i3 + 1;
        int iShift8 = iArr[i3][3] ^ (shift(iArr4[(this.C2 >> 24) & 255], 8) ^ ((iArr4[this.C3 & 255] ^ shift(iArr4[(this.C0 >> 8) & 255], 24)) ^ shift(iArr4[(this.C1 >> 16) & 255], 16)));
        byte[] bArr = S;
        int i6 = (((bArr[iShift5 & 255] & 255) ^ ((bArr[(iShift6 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift7 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift8 >> 24) & 255] << Ascii.CAN);
        int[] iArr5 = iArr[i5];
        this.C0 = iArr5[0] ^ i6;
        this.C1 = ((((bArr[iShift6 & 255] & 255) ^ ((bArr[(iShift7 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift8 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift5 >> 24) & 255] << Ascii.CAN)) ^ iArr5[1];
        this.C2 = ((((bArr[iShift7 & 255] & 255) ^ ((bArr[(iShift8 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift5 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift6 >> 24) & 255] << Ascii.CAN)) ^ iArr5[2];
        this.C3 = ((((bArr[iShift8 & 255] & 255) ^ ((bArr[(iShift5 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift6 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift7 >> 24) & 255] << Ascii.CAN)) ^ iArr5[3];
    }

    private int[][] generateWorkingKey(byte[] bArr, boolean z2) {
        int length = bArr.length / 4;
        if ((length != 4 && length != 6 && length != 8) || length * 4 != bArr.length) {
            throw new IllegalArgumentException("Key length not 128/192/256 bits.");
        }
        int i2 = length + 6;
        this.ROUNDS = i2;
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i2 + 1, 4);
        int i3 = 0;
        int i4 = 0;
        while (i3 < bArr.length) {
            iArr[i4 >> 2][i4 & 3] = (bArr[i3] & 255) | ((bArr[i3 + 1] & 255) << 8) | ((bArr[i3 + 2] & 255) << 16) | (bArr[i3 + 3] << Ascii.CAN);
            i3 += 4;
            i4++;
        }
        int i5 = (this.ROUNDS + 1) << 2;
        for (int i6 = length; i6 < i5; i6++) {
            int i7 = i6 - 1;
            int iSubWord = iArr[i7 >> 2][i7 & 3];
            int i8 = i6 % length;
            if (i8 == 0) {
                iSubWord = subWord(shift(iSubWord, 8)) ^ rcon[(i6 / length) - 1];
            } else if (length > 6 && i8 == 4) {
                iSubWord = subWord(iSubWord);
            }
            int i9 = i6 - length;
            iArr[i6 >> 2][i6 & 3] = iSubWord ^ iArr[i9 >> 2][i9 & 3];
        }
        if (!z2) {
            for (int i10 = 1; i10 < this.ROUNDS; i10++) {
                for (int i11 = 0; i11 < 4; i11++) {
                    int[] iArr2 = iArr[i10];
                    iArr2[i11] = inv_mcol(iArr2[i11]);
                }
            }
        }
        return iArr;
    }

    private int inv_mcol(int i2) {
        int iFFmulX = FFmulX(i2);
        int iFFmulX2 = FFmulX(iFFmulX);
        int iFFmulX3 = FFmulX(iFFmulX2);
        int i3 = i2 ^ iFFmulX3;
        return shift(i3, 24) ^ ((shift(iFFmulX ^ i3, 8) ^ (iFFmulX3 ^ (iFFmulX ^ iFFmulX2))) ^ shift(iFFmulX2 ^ i3, 16));
    }

    private void packBlock(byte[] bArr, int i2) {
        int i3 = i2 + 1;
        int i4 = this.C0;
        bArr[i2] = (byte) i4;
        int i5 = i3 + 1;
        bArr[i3] = (byte) (i4 >> 8);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (i4 >> 16);
        int i7 = i6 + 1;
        bArr[i6] = (byte) (i4 >> 24);
        int i8 = i7 + 1;
        int i9 = this.C1;
        bArr[i7] = (byte) i9;
        int i10 = i8 + 1;
        bArr[i8] = (byte) (i9 >> 8);
        int i11 = i10 + 1;
        bArr[i10] = (byte) (i9 >> 16);
        int i12 = i11 + 1;
        bArr[i11] = (byte) (i9 >> 24);
        int i13 = i12 + 1;
        int i14 = this.C2;
        bArr[i12] = (byte) i14;
        int i15 = i13 + 1;
        bArr[i13] = (byte) (i14 >> 8);
        int i16 = i15 + 1;
        bArr[i15] = (byte) (i14 >> 16);
        int i17 = i16 + 1;
        bArr[i16] = (byte) (i14 >> 24);
        int i18 = i17 + 1;
        int i19 = this.C3;
        bArr[i17] = (byte) i19;
        int i20 = i18 + 1;
        bArr[i18] = (byte) (i19 >> 8);
        bArr[i20] = (byte) (i19 >> 16);
        bArr[i20 + 1] = (byte) (i19 >> 24);
    }

    private int shift(int i2, int i3) {
        return (i2 << (-i3)) | (i2 >>> i3);
    }

    private int subWord(int i2) {
        byte[] bArr = S;
        return (bArr[(i2 >> 24) & 255] << Ascii.CAN) | (bArr[i2 & 255] & 255) | ((bArr[(i2 >> 8) & 255] & 255) << 8) | ((bArr[(i2 >> 16) & 255] & 255) << 16);
    }

    private void unpackBlock(byte[] bArr, int i2) {
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = (bArr[i2] & 255) | ((bArr[i3] & 255) << 8);
        int i6 = i4 + 1;
        int i7 = i5 | ((bArr[i4] & 255) << 16);
        int i8 = i6 + 1;
        this.C0 = i7 | (bArr[i6] << Ascii.CAN);
        int i9 = i8 + 1;
        int i10 = bArr[i8] & 255;
        int i11 = i9 + 1;
        int i12 = ((bArr[i9] & 255) << 8) | i10;
        int i13 = i11 + 1;
        int i14 = i12 | ((bArr[i11] & 255) << 16);
        int i15 = i13 + 1;
        this.C1 = i14 | (bArr[i13] << Ascii.CAN);
        int i16 = i15 + 1;
        int i17 = bArr[i15] & 255;
        int i18 = i16 + 1;
        int i19 = ((bArr[i16] & 255) << 8) | i17;
        int i20 = i18 + 1;
        int i21 = i19 | ((bArr[i18] & 255) << 16);
        int i22 = i20 + 1;
        this.C2 = i21 | (bArr[i20] << Ascii.CAN);
        int i23 = i22 + 1;
        int i24 = bArr[i22] & 255;
        int i25 = i23 + 1;
        int i26 = ((bArr[i23] & 255) << 8) | i24;
        int i27 = i26 | ((bArr[i25] & 255) << 16);
        this.C3 = (bArr[i25 + 1] << Ascii.CAN) | i27;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "AES";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.WorkingKey = generateWorkingKey(((KeyParameter) cipherParameters).getKey(), z2);
            this.forEncryption = z2;
        } else {
            throw new IllegalArgumentException("invalid parameter passed to AES init - " + cipherParameters.getClass().getName());
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.WorkingKey == null) {
            throw new IllegalStateException("AES engine not initialised");
        }
        if (i2 + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 16 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        boolean z2 = this.forEncryption;
        unpackBlock(bArr, i2);
        int[][] iArr = this.WorkingKey;
        if (z2) {
            encryptBlock(iArr);
        } else {
            decryptBlock(iArr);
        }
        packBlock(bArr2, i3);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
