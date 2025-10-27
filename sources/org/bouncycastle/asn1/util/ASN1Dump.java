package org.bouncycastle.asn1.util;

import cn.hutool.core.text.StrPool;
import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.BERApplicationSpecific;
import org.bouncycastle.asn1.BERConstructedOctetString;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.DERApplicationSpecific;
import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERBoolean;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DEREnumerated;
import org.bouncycastle.asn1.DERExternal;
import org.bouncycastle.asn1.DERGeneralizedTime;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERT61String;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTCTime;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.DERUnknownTag;
import org.bouncycastle.asn1.DERVisibleString;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes9.dex */
public class ASN1Dump {
    private static final int SAMPLE_SIZE = 32;
    private static final String TAB = "    ";

    public static void _dumpAsString(String str, boolean z2, DERObject dERObject, StringBuffer stringBuffer) {
        StringBuilder sb;
        String string;
        BigInteger value;
        String str2;
        String strOutputApplicationSpecific;
        String time;
        String property = System.getProperty("line.separator");
        if (!(dERObject instanceof ASN1Sequence)) {
            if (dERObject instanceof DERTaggedObject) {
                String str3 = str + TAB;
                stringBuffer.append(str);
                stringBuffer.append(dERObject instanceof BERTaggedObject ? "BER Tagged [" : "Tagged [");
                DERTaggedObject dERTaggedObject = (DERTaggedObject) dERObject;
                stringBuffer.append(Integer.toString(dERTaggedObject.getTagNo()));
                stringBuffer.append(']');
                if (!dERTaggedObject.isExplicit()) {
                    stringBuffer.append(" IMPLICIT ");
                }
                stringBuffer.append(property);
                if (!dERTaggedObject.isEmpty()) {
                    _dumpAsString(str3, z2, dERTaggedObject.getObject(), stringBuffer);
                    return;
                } else {
                    stringBuffer.append(str3);
                    stringBuffer.append("EMPTY");
                }
            } else if (dERObject instanceof BERSet) {
                Enumeration objects = ((ASN1Set) dERObject).getObjects();
                String str4 = str + TAB;
                stringBuffer.append(str);
                stringBuffer.append("BER Set");
                while (true) {
                    stringBuffer.append(property);
                    while (objects.hasMoreElements()) {
                        Object objNextElement = objects.nextElement();
                        if (objNextElement == null) {
                            break;
                        } else {
                            _dumpAsString(str4, z2, objNextElement instanceof DERObject ? (DERObject) objNextElement : ((DEREncodable) objNextElement).getDERObject(), stringBuffer);
                        }
                    }
                    return;
                    stringBuffer.append(str4);
                    stringBuffer.append(PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL);
                }
            } else {
                if (!(dERObject instanceof DERSet)) {
                    if (dERObject instanceof DERObjectIdentifier) {
                        sb = new StringBuilder();
                        sb.append(str);
                        sb.append("ObjectIdentifier(");
                        sb.append(((DERObjectIdentifier) dERObject).getId());
                    } else if (dERObject instanceof DERBoolean) {
                        sb = new StringBuilder();
                        sb.append(str);
                        sb.append("Boolean(");
                        sb.append(((DERBoolean) dERObject).isTrue());
                    } else {
                        if (dERObject instanceof DERInteger) {
                            sb = new StringBuilder();
                            sb.append(str);
                            sb.append("Integer(");
                            value = ((DERInteger) dERObject).getValue();
                        } else if (dERObject instanceof BERConstructedOctetString) {
                            ASN1OctetString aSN1OctetString = (ASN1OctetString) dERObject;
                            stringBuffer.append(str + "BER Constructed Octet String" + StrPool.BRACKET_START + aSN1OctetString.getOctets().length + "] ");
                            if (z2) {
                                strOutputApplicationSpecific = dumpBinaryDataAsString(str, aSN1OctetString.getOctets());
                                stringBuffer.append(strOutputApplicationSpecific);
                                return;
                            }
                        } else {
                            if (!(dERObject instanceof DEROctetString)) {
                                if (dERObject instanceof DERBitString) {
                                    DERBitString dERBitString = (DERBitString) dERObject;
                                    stringBuffer.append(str + "DER Bit String" + StrPool.BRACKET_START + dERBitString.getBytes().length + ", " + dERBitString.getPadBits() + "] ");
                                    if (z2) {
                                        strOutputApplicationSpecific = dumpBinaryDataAsString(str, dERBitString.getBytes());
                                    }
                                } else {
                                    if (dERObject instanceof DERIA5String) {
                                        sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append("IA5String(");
                                        time = ((DERIA5String) dERObject).getString();
                                    } else if (dERObject instanceof DERUTF8String) {
                                        sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append("UTF8String(");
                                        time = ((DERUTF8String) dERObject).getString();
                                    } else if (dERObject instanceof DERPrintableString) {
                                        sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append("PrintableString(");
                                        time = ((DERPrintableString) dERObject).getString();
                                    } else if (dERObject instanceof DERVisibleString) {
                                        sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append("VisibleString(");
                                        time = ((DERVisibleString) dERObject).getString();
                                    } else if (dERObject instanceof DERBMPString) {
                                        sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append("BMPString(");
                                        time = ((DERBMPString) dERObject).getString();
                                    } else if (dERObject instanceof DERT61String) {
                                        sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append("T61String(");
                                        time = ((DERT61String) dERObject).getString();
                                    } else if (dERObject instanceof DERUTCTime) {
                                        sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append("UTCTime(");
                                        time = ((DERUTCTime) dERObject).getTime();
                                    } else if (dERObject instanceof DERGeneralizedTime) {
                                        sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append("GeneralizedTime(");
                                        time = ((DERGeneralizedTime) dERObject).getTime();
                                    } else {
                                        if (dERObject instanceof DERUnknownTag) {
                                            sb = new StringBuilder();
                                            sb.append(str);
                                            sb.append("Unknown ");
                                            DERUnknownTag dERUnknownTag = (DERUnknownTag) dERObject;
                                            sb.append(Integer.toString(dERUnknownTag.getTag(), 16));
                                            sb.append(" ");
                                            string = new String(Hex.encode(dERUnknownTag.getData()));
                                        } else {
                                            if (dERObject instanceof BERApplicationSpecific) {
                                                str2 = ASN1Encodable.BER;
                                            } else if (dERObject instanceof DERApplicationSpecific) {
                                                str2 = ASN1Encodable.DER;
                                            } else if (dERObject instanceof DEREnumerated) {
                                                sb = new StringBuilder();
                                                sb.append(str);
                                                sb.append("DER Enumerated(");
                                                value = ((DEREnumerated) dERObject).getValue();
                                            } else {
                                                if (dERObject instanceof DERExternal) {
                                                    DERExternal dERExternal = (DERExternal) dERObject;
                                                    stringBuffer.append(str + "External " + property);
                                                    StringBuilder sb2 = new StringBuilder();
                                                    sb2.append(str);
                                                    sb2.append(TAB);
                                                    String string2 = sb2.toString();
                                                    if (dERExternal.getDirectReference() != null) {
                                                        stringBuffer.append(string2 + "Direct Reference: " + dERExternal.getDirectReference().getId() + property);
                                                    }
                                                    if (dERExternal.getIndirectReference() != null) {
                                                        stringBuffer.append(string2 + "Indirect Reference: " + dERExternal.getIndirectReference().toString() + property);
                                                    }
                                                    if (dERExternal.getDataValueDescriptor() != null) {
                                                        _dumpAsString(string2, z2, dERExternal.getDataValueDescriptor(), stringBuffer);
                                                    }
                                                    stringBuffer.append(string2 + "Encoding: " + dERExternal.getEncoding() + property);
                                                    _dumpAsString(string2, z2, dERExternal.getExternalContent(), stringBuffer);
                                                    return;
                                                }
                                                sb = new StringBuilder();
                                                sb.append(str);
                                                string = dERObject.toString();
                                            }
                                            strOutputApplicationSpecific = outputApplicationSpecific(str2, str, z2, dERObject, property);
                                        }
                                        sb.append(string);
                                        sb.append(property);
                                        strOutputApplicationSpecific = sb.toString();
                                    }
                                    sb.append(time);
                                    sb.append(") ");
                                    sb.append(property);
                                    strOutputApplicationSpecific = sb.toString();
                                }
                                stringBuffer.append(strOutputApplicationSpecific);
                                return;
                            }
                            ASN1OctetString aSN1OctetString2 = (ASN1OctetString) dERObject;
                            stringBuffer.append(str + "DER Octet String" + StrPool.BRACKET_START + aSN1OctetString2.getOctets().length + "] ");
                            if (z2) {
                                strOutputApplicationSpecific = dumpBinaryDataAsString(str, aSN1OctetString2.getOctets());
                                stringBuffer.append(strOutputApplicationSpecific);
                                return;
                            }
                        }
                        sb.append(value);
                    }
                    sb.append(")");
                    sb.append(property);
                    strOutputApplicationSpecific = sb.toString();
                    stringBuffer.append(strOutputApplicationSpecific);
                    return;
                }
                Enumeration objects2 = ((ASN1Set) dERObject).getObjects();
                String str5 = str + TAB;
                stringBuffer.append(str);
                stringBuffer.append("DER Set");
                while (true) {
                    stringBuffer.append(property);
                    while (objects2.hasMoreElements()) {
                        Object objNextElement2 = objects2.nextElement();
                        if (objNextElement2 == null) {
                            break;
                        } else {
                            _dumpAsString(str5, z2, objNextElement2 instanceof DERObject ? (DERObject) objNextElement2 : ((DEREncodable) objNextElement2).getDERObject(), stringBuffer);
                        }
                    }
                    return;
                    stringBuffer.append(str5);
                    stringBuffer.append(PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL);
                }
            }
            stringBuffer.append(property);
            return;
        }
        Enumeration objects3 = ((ASN1Sequence) dERObject).getObjects();
        String str6 = str + TAB;
        stringBuffer.append(str);
        stringBuffer.append(dERObject instanceof BERSequence ? "BER Sequence" : dERObject instanceof DERSequence ? "DER Sequence" : "Sequence");
        while (true) {
            stringBuffer.append(property);
            while (objects3.hasMoreElements()) {
                Object objNextElement3 = objects3.nextElement();
                if (objNextElement3 == null || objNextElement3.equals(new DERNull())) {
                    break;
                } else {
                    _dumpAsString(str6, z2, objNextElement3 instanceof DERObject ? (DERObject) objNextElement3 : ((DEREncodable) objNextElement3).getDERObject(), stringBuffer);
                }
            }
            return;
            stringBuffer.append(str6);
            stringBuffer.append(PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL);
        }
    }

    private static String calculateAscString(byte[] bArr, int i2, int i3) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i4 = i2; i4 != i2 + i3; i4++) {
            byte b3 = bArr[i4];
            if (b3 >= 32 && b3 <= 126) {
                stringBuffer.append((char) b3);
            }
        }
        return stringBuffer.toString();
    }

    public static String dumpAsString(Object obj) {
        return dumpAsString(obj, false);
    }

    public static String dumpAsString(Object obj, boolean z2) {
        DERObject dERObject;
        StringBuffer stringBuffer = new StringBuffer();
        if (obj instanceof DERObject) {
            dERObject = (DERObject) obj;
        } else {
            if (!(obj instanceof DEREncodable)) {
                return "unknown object type " + obj.toString();
            }
            dERObject = ((DEREncodable) obj).getDERObject();
        }
        _dumpAsString("", z2, dERObject, stringBuffer);
        return stringBuffer.toString();
    }

    private static String dumpBinaryDataAsString(String str, byte[] bArr) {
        String strCalculateAscString;
        String property = System.getProperty("line.separator");
        StringBuffer stringBuffer = new StringBuffer();
        String str2 = str + TAB;
        stringBuffer.append(property);
        for (int i2 = 0; i2 < bArr.length; i2 += 32) {
            int length = bArr.length - i2;
            stringBuffer.append(str2);
            if (length > 32) {
                stringBuffer.append(new String(Hex.encode(bArr, i2, 32)));
                stringBuffer.append(TAB);
                strCalculateAscString = calculateAscString(bArr, i2, 32);
            } else {
                stringBuffer.append(new String(Hex.encode(bArr, i2, bArr.length - i2)));
                for (int length2 = bArr.length - i2; length2 != 32; length2++) {
                    stringBuffer.append("  ");
                }
                stringBuffer.append(TAB);
                strCalculateAscString = calculateAscString(bArr, i2, bArr.length - i2);
            }
            stringBuffer.append(strCalculateAscString);
            stringBuffer.append(property);
        }
        return stringBuffer.toString();
    }

    private static String outputApplicationSpecific(String str, String str2, boolean z2, DERObject dERObject, String str3) {
        DERApplicationSpecific dERApplicationSpecific = (DERApplicationSpecific) dERObject;
        StringBuffer stringBuffer = new StringBuffer();
        if (!dERApplicationSpecific.isConstructed()) {
            return str2 + str + " ApplicationSpecific[" + dERApplicationSpecific.getApplicationTag() + "] (" + new String(Hex.encode(dERApplicationSpecific.getContents())) + ")" + str3;
        }
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(dERApplicationSpecific.getObject(16));
            stringBuffer.append(str2 + str + " ApplicationSpecific[" + dERApplicationSpecific.getApplicationTag() + StrPool.BRACKET_END + str3);
            Enumeration objects = aSN1Sequence.getObjects();
            while (objects.hasMoreElements()) {
                _dumpAsString(str2 + TAB, z2, (DERObject) objects.nextElement(), stringBuffer);
            }
        } catch (IOException e2) {
            stringBuffer.append(e2);
        }
        return stringBuffer.toString();
    }
}
