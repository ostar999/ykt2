package org.bouncycastle.asn1.x509;

import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes9.dex */
public class X509Name extends ASN1Encodable {
    public static final DERObjectIdentifier BUSINESS_CATEGORY;
    public static final DERObjectIdentifier C;
    public static final DERObjectIdentifier CN;
    public static final DERObjectIdentifier COUNTRY_OF_CITIZENSHIP;
    public static final DERObjectIdentifier COUNTRY_OF_RESIDENCE;
    public static final DERObjectIdentifier DATE_OF_BIRTH;
    public static final DERObjectIdentifier DC;
    public static final DERObjectIdentifier DMD_NAME;
    public static final DERObjectIdentifier DN_QUALIFIER;
    public static final Hashtable DefaultLookUp;
    public static boolean DefaultReverse;
    public static final Hashtable DefaultSymbols;
    public static final DERObjectIdentifier E;
    public static final DERObjectIdentifier EmailAddress;
    private static final Boolean FALSE;
    public static final DERObjectIdentifier GENDER;
    public static final DERObjectIdentifier GENERATION;
    public static final DERObjectIdentifier GIVENNAME;
    public static final DERObjectIdentifier INITIALS;
    public static final DERObjectIdentifier L;
    public static final DERObjectIdentifier NAME;
    public static final DERObjectIdentifier NAME_AT_BIRTH;
    public static final DERObjectIdentifier O;
    public static final Hashtable OIDLookUp;
    public static final DERObjectIdentifier OU;
    public static final DERObjectIdentifier PLACE_OF_BIRTH;
    public static final DERObjectIdentifier POSTAL_ADDRESS;
    public static final DERObjectIdentifier POSTAL_CODE;
    public static final DERObjectIdentifier PSEUDONYM;
    public static final Hashtable RFC1779Symbols;
    public static final Hashtable RFC2253Symbols;
    public static final DERObjectIdentifier SERIALNUMBER;
    public static final DERObjectIdentifier SN;
    public static final DERObjectIdentifier ST;
    public static final DERObjectIdentifier STREET;
    public static final DERObjectIdentifier SURNAME;
    public static final Hashtable SymbolLookUp;
    public static final DERObjectIdentifier T;
    public static final DERObjectIdentifier TELEPHONE_NUMBER;
    private static final Boolean TRUE;
    public static final DERObjectIdentifier UID;
    public static final DERObjectIdentifier UNIQUE_IDENTIFIER;
    public static final DERObjectIdentifier UnstructuredAddress;
    public static final DERObjectIdentifier UnstructuredName;
    private Vector added;
    private X509NameEntryConverter converter;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private Vector ordering;
    private ASN1Sequence seq;
    private Vector values;

    static {
        DERObjectIdentifier dERObjectIdentifier = new DERObjectIdentifier("2.5.4.6");
        C = dERObjectIdentifier;
        DERObjectIdentifier dERObjectIdentifier2 = new DERObjectIdentifier("2.5.4.10");
        O = dERObjectIdentifier2;
        DERObjectIdentifier dERObjectIdentifier3 = new DERObjectIdentifier("2.5.4.11");
        OU = dERObjectIdentifier3;
        DERObjectIdentifier dERObjectIdentifier4 = new DERObjectIdentifier("2.5.4.12");
        T = dERObjectIdentifier4;
        DERObjectIdentifier dERObjectIdentifier5 = new DERObjectIdentifier("2.5.4.3");
        CN = dERObjectIdentifier5;
        DERObjectIdentifier dERObjectIdentifier6 = new DERObjectIdentifier("2.5.4.5");
        SN = dERObjectIdentifier6;
        DERObjectIdentifier dERObjectIdentifier7 = new DERObjectIdentifier("2.5.4.9");
        STREET = dERObjectIdentifier7;
        SERIALNUMBER = dERObjectIdentifier6;
        DERObjectIdentifier dERObjectIdentifier8 = new DERObjectIdentifier("2.5.4.7");
        L = dERObjectIdentifier8;
        DERObjectIdentifier dERObjectIdentifier9 = new DERObjectIdentifier("2.5.4.8");
        ST = dERObjectIdentifier9;
        DERObjectIdentifier dERObjectIdentifier10 = new DERObjectIdentifier("2.5.4.4");
        SURNAME = dERObjectIdentifier10;
        DERObjectIdentifier dERObjectIdentifier11 = new DERObjectIdentifier("2.5.4.42");
        GIVENNAME = dERObjectIdentifier11;
        DERObjectIdentifier dERObjectIdentifier12 = new DERObjectIdentifier("2.5.4.43");
        INITIALS = dERObjectIdentifier12;
        DERObjectIdentifier dERObjectIdentifier13 = new DERObjectIdentifier("2.5.4.44");
        GENERATION = dERObjectIdentifier13;
        DERObjectIdentifier dERObjectIdentifier14 = new DERObjectIdentifier("2.5.4.45");
        UNIQUE_IDENTIFIER = dERObjectIdentifier14;
        DERObjectIdentifier dERObjectIdentifier15 = new DERObjectIdentifier("2.5.4.15");
        BUSINESS_CATEGORY = dERObjectIdentifier15;
        DERObjectIdentifier dERObjectIdentifier16 = new DERObjectIdentifier("2.5.4.17");
        POSTAL_CODE = dERObjectIdentifier16;
        DERObjectIdentifier dERObjectIdentifier17 = new DERObjectIdentifier("2.5.4.46");
        DN_QUALIFIER = dERObjectIdentifier17;
        DERObjectIdentifier dERObjectIdentifier18 = new DERObjectIdentifier("2.5.4.65");
        PSEUDONYM = dERObjectIdentifier18;
        DERObjectIdentifier dERObjectIdentifier19 = new DERObjectIdentifier("1.3.6.1.5.5.7.9.1");
        DATE_OF_BIRTH = dERObjectIdentifier19;
        DERObjectIdentifier dERObjectIdentifier20 = new DERObjectIdentifier("1.3.6.1.5.5.7.9.2");
        PLACE_OF_BIRTH = dERObjectIdentifier20;
        DERObjectIdentifier dERObjectIdentifier21 = new DERObjectIdentifier("1.3.6.1.5.5.7.9.3");
        GENDER = dERObjectIdentifier21;
        DERObjectIdentifier dERObjectIdentifier22 = new DERObjectIdentifier("1.3.6.1.5.5.7.9.4");
        COUNTRY_OF_CITIZENSHIP = dERObjectIdentifier22;
        DERObjectIdentifier dERObjectIdentifier23 = new DERObjectIdentifier("1.3.6.1.5.5.7.9.5");
        COUNTRY_OF_RESIDENCE = dERObjectIdentifier23;
        DERObjectIdentifier dERObjectIdentifier24 = new DERObjectIdentifier("1.3.36.8.3.14");
        NAME_AT_BIRTH = dERObjectIdentifier24;
        DERObjectIdentifier dERObjectIdentifier25 = new DERObjectIdentifier("2.5.4.16");
        POSTAL_ADDRESS = dERObjectIdentifier25;
        DMD_NAME = new DERObjectIdentifier("2.5.4.54");
        ASN1ObjectIdentifier aSN1ObjectIdentifier = X509ObjectIdentifiers.id_at_telephoneNumber;
        TELEPHONE_NUMBER = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = X509ObjectIdentifiers.id_at_name;
        NAME = aSN1ObjectIdentifier2;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
        EmailAddress = aSN1ObjectIdentifier3;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
        UnstructuredName = aSN1ObjectIdentifier4;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
        UnstructuredAddress = aSN1ObjectIdentifier5;
        E = aSN1ObjectIdentifier3;
        DERObjectIdentifier dERObjectIdentifier26 = new DERObjectIdentifier("0.9.2342.19200300.100.1.25");
        DC = dERObjectIdentifier26;
        DERObjectIdentifier dERObjectIdentifier27 = new DERObjectIdentifier("0.9.2342.19200300.100.1.1");
        UID = dERObjectIdentifier27;
        DefaultReverse = false;
        Hashtable hashtable = new Hashtable();
        DefaultSymbols = hashtable;
        Hashtable hashtable2 = new Hashtable();
        RFC2253Symbols = hashtable2;
        Hashtable hashtable3 = new Hashtable();
        RFC1779Symbols = hashtable3;
        Hashtable hashtable4 = new Hashtable();
        DefaultLookUp = hashtable4;
        OIDLookUp = hashtable;
        SymbolLookUp = hashtable4;
        TRUE = new Boolean(true);
        FALSE = new Boolean(false);
        hashtable.put(dERObjectIdentifier, "C");
        hashtable.put(dERObjectIdentifier2, "O");
        hashtable.put(dERObjectIdentifier4, ExifInterface.GPS_DIRECTION_TRUE);
        hashtable.put(dERObjectIdentifier3, "OU");
        hashtable.put(dERObjectIdentifier5, "CN");
        hashtable.put(dERObjectIdentifier8, "L");
        hashtable.put(dERObjectIdentifier9, "ST");
        hashtable.put(dERObjectIdentifier6, "SERIALNUMBER");
        hashtable.put(aSN1ObjectIdentifier3, "E");
        hashtable.put(dERObjectIdentifier26, "DC");
        hashtable.put(dERObjectIdentifier27, "UID");
        hashtable.put(dERObjectIdentifier7, "STREET");
        hashtable.put(dERObjectIdentifier10, "SURNAME");
        hashtable.put(dERObjectIdentifier11, "GIVENNAME");
        hashtable.put(dERObjectIdentifier12, "INITIALS");
        hashtable.put(dERObjectIdentifier13, "GENERATION");
        hashtable.put(aSN1ObjectIdentifier5, "unstructuredAddress");
        hashtable.put(aSN1ObjectIdentifier4, "unstructuredName");
        hashtable.put(dERObjectIdentifier14, "UniqueIdentifier");
        hashtable.put(dERObjectIdentifier17, "DN");
        hashtable.put(dERObjectIdentifier18, "Pseudonym");
        hashtable.put(dERObjectIdentifier25, "PostalAddress");
        hashtable.put(dERObjectIdentifier24, "NameAtBirth");
        hashtable.put(dERObjectIdentifier22, "CountryOfCitizenship");
        hashtable.put(dERObjectIdentifier23, "CountryOfResidence");
        hashtable.put(dERObjectIdentifier21, "Gender");
        hashtable.put(dERObjectIdentifier20, "PlaceOfBirth");
        hashtable.put(dERObjectIdentifier19, "DateOfBirth");
        hashtable.put(dERObjectIdentifier16, "PostalCode");
        hashtable.put(dERObjectIdentifier15, "BusinessCategory");
        hashtable.put(aSN1ObjectIdentifier, "TelephoneNumber");
        hashtable.put(aSN1ObjectIdentifier2, "Name");
        hashtable2.put(dERObjectIdentifier, "C");
        hashtable2.put(dERObjectIdentifier2, "O");
        hashtable2.put(dERObjectIdentifier3, "OU");
        hashtable2.put(dERObjectIdentifier5, "CN");
        hashtable2.put(dERObjectIdentifier8, "L");
        hashtable2.put(dERObjectIdentifier9, "ST");
        hashtable2.put(dERObjectIdentifier7, "STREET");
        hashtable2.put(dERObjectIdentifier26, "DC");
        hashtable2.put(dERObjectIdentifier27, "UID");
        hashtable3.put(dERObjectIdentifier, "C");
        hashtable3.put(dERObjectIdentifier2, "O");
        hashtable3.put(dERObjectIdentifier3, "OU");
        hashtable3.put(dERObjectIdentifier5, "CN");
        hashtable3.put(dERObjectIdentifier8, "L");
        hashtable3.put(dERObjectIdentifier9, "ST");
        hashtable3.put(dERObjectIdentifier7, "STREET");
        hashtable4.put(am.aF, dERObjectIdentifier);
        hashtable4.put("o", dERObjectIdentifier2);
        hashtable4.put("t", dERObjectIdentifier4);
        hashtable4.put("ou", dERObjectIdentifier3);
        hashtable4.put(AdvanceSetting.CLEAR_NOTIFICATION, dERObjectIdentifier5);
        hashtable4.put(NotifyType.LIGHTS, dERObjectIdentifier8);
        hashtable4.put("st", dERObjectIdentifier9);
        hashtable4.put("sn", dERObjectIdentifier6);
        hashtable4.put("serialnumber", dERObjectIdentifier6);
        hashtable4.put("street", dERObjectIdentifier7);
        hashtable4.put("emailaddress", aSN1ObjectIdentifier3);
        hashtable4.put(SocializeProtocolConstants.PROTOCOL_KEY_DESCRIPTOR, dERObjectIdentifier26);
        hashtable4.put(AliyunLogKey.KEY_EVENT, aSN1ObjectIdentifier3);
        hashtable4.put("uid", dERObjectIdentifier27);
        hashtable4.put("surname", dERObjectIdentifier10);
        hashtable4.put("givenname", dERObjectIdentifier11);
        hashtable4.put("initials", dERObjectIdentifier12);
        hashtable4.put("generation", dERObjectIdentifier13);
        hashtable4.put("unstructuredaddress", aSN1ObjectIdentifier5);
        hashtable4.put("unstructuredname", aSN1ObjectIdentifier4);
        hashtable4.put("uniqueidentifier", dERObjectIdentifier14);
        hashtable4.put(AliyunLogKey.KEY_DEFINITION, dERObjectIdentifier17);
        hashtable4.put("pseudonym", dERObjectIdentifier18);
        hashtable4.put("postaladdress", dERObjectIdentifier25);
        hashtable4.put("nameofbirth", dERObjectIdentifier24);
        hashtable4.put("countryofcitizenship", dERObjectIdentifier22);
        hashtable4.put("countryofresidence", dERObjectIdentifier23);
        hashtable4.put("gender", dERObjectIdentifier21);
        hashtable4.put("placeofbirth", dERObjectIdentifier20);
        hashtable4.put("dateofbirth", dERObjectIdentifier19);
        hashtable4.put("postalcode", dERObjectIdentifier16);
        hashtable4.put("businesscategory", dERObjectIdentifier15);
        hashtable4.put("telephonenumber", aSN1ObjectIdentifier);
        hashtable4.put("name", aSN1ObjectIdentifier2);
    }

    public X509Name() {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
    }

    public X509Name(String str) {
        this(DefaultReverse, DefaultLookUp, str);
    }

    public X509Name(String str, X509NameEntryConverter x509NameEntryConverter) {
        this(DefaultReverse, DefaultLookUp, str, x509NameEntryConverter);
    }

    public X509Name(Hashtable hashtable) {
        this((Vector) null, hashtable);
    }

    public X509Name(Vector vector, Hashtable hashtable) {
        this(vector, hashtable, new X509DefaultEntryConverter());
    }

    public X509Name(Vector vector, Hashtable hashtable, X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = x509NameEntryConverter;
        if (vector != null) {
            for (int i2 = 0; i2 != vector.size(); i2++) {
                this.ordering.addElement(vector.elementAt(i2));
                this.added.addElement(FALSE);
            }
        } else {
            Enumeration enumerationKeys = hashtable.keys();
            while (enumerationKeys.hasMoreElements()) {
                this.ordering.addElement(enumerationKeys.nextElement());
                this.added.addElement(FALSE);
            }
        }
        for (int i3 = 0; i3 != this.ordering.size(); i3++) {
            DERObjectIdentifier dERObjectIdentifier = (DERObjectIdentifier) this.ordering.elementAt(i3);
            if (hashtable.get(dERObjectIdentifier) == null) {
                throw new IllegalArgumentException("No attribute for object id - " + dERObjectIdentifier.getId() + " - passed to distinguished name");
            }
            this.values.addElement(hashtable.get(dERObjectIdentifier));
        }
    }

    public X509Name(Vector vector, Vector vector2) {
        this(vector, vector2, new X509DefaultEntryConverter());
    }

    public X509Name(Vector vector, Vector vector2, X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = x509NameEntryConverter;
        if (vector.size() != vector2.size()) {
            throw new IllegalArgumentException("oids vector must be same length as values.");
        }
        for (int i2 = 0; i2 < vector.size(); i2++) {
            this.ordering.addElement(vector.elementAt(i2));
            this.values.addElement(vector2.elementAt(i2));
            this.added.addElement(FALSE);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public X509Name(org.bouncycastle.asn1.ASN1Sequence r8) {
        /*
            r7 = this;
            r7.<init>()
            r0 = 0
            r7.converter = r0
            java.util.Vector r0 = new java.util.Vector
            r0.<init>()
            r7.ordering = r0
            java.util.Vector r0 = new java.util.Vector
            r0.<init>()
            r7.values = r0
            java.util.Vector r0 = new java.util.Vector
            r0.<init>()
            r7.added = r0
            r7.seq = r8
            java.util.Enumeration r8 = r8.getObjects()
        L21:
            boolean r0 = r8.hasMoreElements()
            if (r0 == 0) goto Lc8
            java.lang.Object r0 = r8.nextElement()
            org.bouncycastle.asn1.DEREncodable r0 = (org.bouncycastle.asn1.DEREncodable) r0
            org.bouncycastle.asn1.DERObject r0 = r0.getDERObject()
            org.bouncycastle.asn1.ASN1Set r0 = org.bouncycastle.asn1.ASN1Set.getInstance(r0)
            r1 = 0
            r2 = r1
        L37:
            int r3 = r0.size()
            if (r2 >= r3) goto L21
            org.bouncycastle.asn1.DEREncodable r3 = r0.getObjectAt(r2)
            org.bouncycastle.asn1.ASN1Sequence r3 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r3)
            int r4 = r3.size()
            r5 = 2
            if (r4 != r5) goto Lc0
            java.util.Vector r4 = r7.ordering
            org.bouncycastle.asn1.DEREncodable r5 = r3.getObjectAt(r1)
            org.bouncycastle.asn1.DERObjectIdentifier r5 = org.bouncycastle.asn1.DERObjectIdentifier.getInstance(r5)
            r4.addElement(r5)
            r4 = 1
            org.bouncycastle.asn1.DEREncodable r3 = r3.getObjectAt(r4)
            boolean r4 = r3 instanceof org.bouncycastle.asn1.DERString
            if (r4 == 0) goto L8a
            boolean r4 = r3 instanceof org.bouncycastle.asn1.DERUniversalString
            if (r4 != 0) goto L8a
            org.bouncycastle.asn1.DERString r3 = (org.bouncycastle.asn1.DERString) r3
            java.lang.String r3 = r3.getString()
            int r4 = r3.length()
            if (r4 <= 0) goto L87
            char r4 = r3.charAt(r1)
            r5 = 35
            if (r4 != r5) goto L87
            java.util.Vector r4 = r7.values
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "\\"
            r5.append(r6)
            goto La6
        L87:
            java.util.Vector r4 = r7.values
            goto Lad
        L8a:
            java.util.Vector r4 = r7.values
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "#"
            r5.append(r6)
            org.bouncycastle.asn1.DERObject r3 = r3.getDERObject()
            byte[] r3 = r3.getDEREncoded()
            byte[] r3 = org.bouncycastle.util.encoders.Hex.encode(r3)
            java.lang.String r3 = r7.bytesToString(r3)
        La6:
            r5.append(r3)
            java.lang.String r3 = r5.toString()
        Lad:
            r4.addElement(r3)
            java.util.Vector r3 = r7.added
            if (r2 == 0) goto Lb7
            java.lang.Boolean r4 = org.bouncycastle.asn1.x509.X509Name.TRUE
            goto Lb9
        Lb7:
            java.lang.Boolean r4 = org.bouncycastle.asn1.x509.X509Name.FALSE
        Lb9:
            r3.addElement(r4)
            int r2 = r2 + 1
            goto L37
        Lc0:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "badly sized pair"
            r8.<init>(r0)
            throw r8
        Lc8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x509.X509Name.<init>(org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public X509Name(boolean z2, String str) {
        this(z2, DefaultLookUp, str);
    }

    public X509Name(boolean z2, String str, X509NameEntryConverter x509NameEntryConverter) {
        this(z2, DefaultLookUp, str, x509NameEntryConverter);
    }

    public X509Name(boolean z2, Hashtable hashtable, String str) {
        this(z2, hashtable, str, new X509DefaultEntryConverter());
    }

    public X509Name(boolean z2, Hashtable hashtable, String str, X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = x509NameEntryConverter;
        X509NameTokenizer x509NameTokenizer = new X509NameTokenizer(str);
        while (x509NameTokenizer.hasMoreTokens()) {
            String strNextToken = x509NameTokenizer.nextToken();
            int iIndexOf = strNextToken.indexOf(61);
            if (iIndexOf == -1) {
                throw new IllegalArgumentException("badly formated directory string");
            }
            String strSubstring = strNextToken.substring(0, iIndexOf);
            String strSubstring2 = strNextToken.substring(iIndexOf + 1);
            DERObjectIdentifier dERObjectIdentifierDecodeOID = decodeOID(strSubstring, hashtable);
            if (strSubstring2.indexOf(43) > 0) {
                X509NameTokenizer x509NameTokenizer2 = new X509NameTokenizer(strSubstring2, '+');
                String strNextToken2 = x509NameTokenizer2.nextToken();
                this.ordering.addElement(dERObjectIdentifierDecodeOID);
                this.values.addElement(strNextToken2);
                Vector vector = this.added;
                Boolean bool = FALSE;
                while (true) {
                    vector.addElement(bool);
                    if (x509NameTokenizer2.hasMoreTokens()) {
                        String strNextToken3 = x509NameTokenizer2.nextToken();
                        int iIndexOf2 = strNextToken3.indexOf(61);
                        String strSubstring3 = strNextToken3.substring(0, iIndexOf2);
                        String strSubstring4 = strNextToken3.substring(iIndexOf2 + 1);
                        this.ordering.addElement(decodeOID(strSubstring3, hashtable));
                        this.values.addElement(strSubstring4);
                        vector = this.added;
                        bool = TRUE;
                    }
                }
            } else {
                this.ordering.addElement(dERObjectIdentifierDecodeOID);
                this.values.addElement(strSubstring2);
                this.added.addElement(FALSE);
            }
        }
        if (z2) {
            Vector vector2 = new Vector();
            Vector vector3 = new Vector();
            Vector vector4 = new Vector();
            int i2 = 1;
            for (int i3 = 0; i3 < this.ordering.size(); i3++) {
                if (((Boolean) this.added.elementAt(i3)).booleanValue()) {
                    vector2.insertElementAt(this.ordering.elementAt(i3), i2);
                    vector3.insertElementAt(this.values.elementAt(i3), i2);
                    vector4.insertElementAt(this.added.elementAt(i3), i2);
                    i2++;
                } else {
                    vector2.insertElementAt(this.ordering.elementAt(i3), 0);
                    vector3.insertElementAt(this.values.elementAt(i3), 0);
                    vector4.insertElementAt(this.added.elementAt(i3), 0);
                    i2 = 1;
                }
            }
            this.ordering = vector2;
            this.values = vector3;
            this.added = vector4;
        }
    }

    private void appendValue(StringBuffer stringBuffer, Hashtable hashtable, DERObjectIdentifier dERObjectIdentifier, String str) {
        String id = (String) hashtable.get(dERObjectIdentifier);
        if (id == null) {
            id = dERObjectIdentifier.getId();
        }
        stringBuffer.append(id);
        stringBuffer.append('=');
        int length = stringBuffer.length();
        stringBuffer.append(str);
        int length2 = stringBuffer.length();
        if (str.length() >= 2 && str.charAt(0) == '\\' && str.charAt(1) == '#') {
            length += 2;
        }
        while (length != length2) {
            if (stringBuffer.charAt(length) == ',' || stringBuffer.charAt(length) == '\"' || stringBuffer.charAt(length) == '\\' || stringBuffer.charAt(length) == '+' || stringBuffer.charAt(length) == '=' || stringBuffer.charAt(length) == '<' || stringBuffer.charAt(length) == '>' || stringBuffer.charAt(length) == ';') {
                stringBuffer.insert(length, StrPool.BACKSLASH);
                length++;
                length2++;
            }
            length++;
        }
    }

    private String bytesToString(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i2 = 0; i2 != length; i2++) {
            cArr[i2] = (char) (bArr[i2] & 255);
        }
        return new String(cArr);
    }

    private String canonicalize(String str) {
        String lowerCase = Strings.toLowerCase(str.trim());
        if (lowerCase.length() <= 0 || lowerCase.charAt(0) != '#') {
            return lowerCase;
        }
        DEREncodable dEREncodableDecodeObject = decodeObject(lowerCase);
        return dEREncodableDecodeObject instanceof DERString ? Strings.toLowerCase(((DERString) dEREncodableDecodeObject).getString().trim()) : lowerCase;
    }

    private DERObjectIdentifier decodeOID(String str, Hashtable hashtable) {
        if (Strings.toUpperCase(str).startsWith("OID.")) {
            return new DERObjectIdentifier(str.substring(4));
        }
        if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
            return new DERObjectIdentifier(str);
        }
        DERObjectIdentifier dERObjectIdentifier = (DERObjectIdentifier) hashtable.get(Strings.toLowerCase(str));
        if (dERObjectIdentifier != null) {
            return dERObjectIdentifier;
        }
        throw new IllegalArgumentException("Unknown object id - " + str + " - passed to distinguished name");
    }

    private ASN1Object decodeObject(String str) {
        try {
            return ASN1Object.fromByteArray(Hex.decode(str.substring(1)));
        } catch (IOException e2) {
            throw new IllegalStateException("unknown encoding in name: " + e2);
        }
    }

    private boolean equivalentStrings(String str, String str2) {
        String strCanonicalize = canonicalize(str);
        String strCanonicalize2 = canonicalize(str2);
        return strCanonicalize.equals(strCanonicalize2) || stripInternalSpaces(strCanonicalize).equals(stripInternalSpaces(strCanonicalize2));
    }

    public static X509Name getInstance(Object obj) {
        return (obj == null || (obj instanceof X509Name)) ? (X509Name) obj : obj instanceof X500Name ? new X509Name(ASN1Sequence.getInstance(((X500Name) obj).getDERObject())) : new X509Name(ASN1Sequence.getInstance(obj));
    }

    public static X509Name getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    private String stripInternalSpaces(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() != 0) {
            char cCharAt = str.charAt(0);
            stringBuffer.append(cCharAt);
            int i2 = 1;
            while (i2 < str.length()) {
                char cCharAt2 = str.charAt(i2);
                if (cCharAt != ' ' || cCharAt2 != ' ') {
                    stringBuffer.append(cCharAt2);
                }
                i2++;
                cCharAt = cCharAt2;
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public boolean equals(Object obj) {
        int i2;
        int i3;
        int i4;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (getDERObject().equals(((DEREncodable) obj).getDERObject())) {
            return true;
        }
        try {
            X509Name x509Name = getInstance(obj);
            int size = this.ordering.size();
            if (size != x509Name.ordering.size()) {
                return false;
            }
            boolean[] zArr = new boolean[size];
            if (this.ordering.elementAt(0).equals(x509Name.ordering.elementAt(0))) {
                i4 = 1;
                i3 = size;
                i2 = 0;
            } else {
                i2 = size - 1;
                i3 = -1;
                i4 = -1;
            }
            while (i2 != i3) {
                DERObjectIdentifier dERObjectIdentifier = (DERObjectIdentifier) this.ordering.elementAt(i2);
                String str = (String) this.values.elementAt(i2);
                int i5 = 0;
                while (true) {
                    if (i5 >= size) {
                        z2 = false;
                        break;
                    }
                    if (!zArr[i5] && dERObjectIdentifier.equals((DERObjectIdentifier) x509Name.ordering.elementAt(i5)) && equivalentStrings(str, (String) x509Name.values.elementAt(i5))) {
                        zArr[i5] = true;
                        z2 = true;
                        break;
                    }
                    i5++;
                }
                if (!z2) {
                    return false;
                }
                i2 += i4;
            }
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public boolean equals(Object obj, boolean z2) {
        if (!z2) {
            return equals(obj);
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (getDERObject().equals(((DEREncodable) obj).getDERObject())) {
            return true;
        }
        try {
            X509Name x509Name = getInstance(obj);
            int size = this.ordering.size();
            if (size != x509Name.ordering.size()) {
                return false;
            }
            for (int i2 = 0; i2 < size; i2++) {
                if (!((DERObjectIdentifier) this.ordering.elementAt(i2)).equals((DERObjectIdentifier) x509Name.ordering.elementAt(i2)) || !equivalentStrings((String) this.values.elementAt(i2), (String) x509Name.values.elementAt(i2))) {
                    return false;
                }
            }
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public Vector getOIDs() {
        Vector vector = new Vector();
        for (int i2 = 0; i2 != this.ordering.size(); i2++) {
            vector.addElement(this.ordering.elementAt(i2));
        }
        return vector;
    }

    public Vector getValues() {
        Vector vector = new Vector();
        for (int i2 = 0; i2 != this.values.size(); i2++) {
            vector.addElement(this.values.elementAt(i2));
        }
        return vector;
    }

    public Vector getValues(DERObjectIdentifier dERObjectIdentifier) {
        Vector vector = new Vector();
        for (int i2 = 0; i2 != this.values.size(); i2++) {
            if (this.ordering.elementAt(i2).equals(dERObjectIdentifier)) {
                String strSubstring = (String) this.values.elementAt(i2);
                if (strSubstring.length() > 2 && strSubstring.charAt(0) == '\\' && strSubstring.charAt(1) == '#') {
                    strSubstring = strSubstring.substring(1);
                }
                vector.addElement(strSubstring);
            }
        }
        return vector;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        for (int i2 = 0; i2 != this.ordering.size(); i2++) {
            String strStripInternalSpaces = stripInternalSpaces(canonicalize((String) this.values.elementAt(i2)));
            int iHashCode = this.hashCodeValue ^ this.ordering.elementAt(i2).hashCode();
            this.hashCodeValue = iHashCode;
            this.hashCodeValue = strStripInternalSpaces.hashCode() ^ iHashCode;
        }
        return this.hashCodeValue;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        DERSequence dERSequence;
        if (this.seq == null) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            DERObjectIdentifier dERObjectIdentifier = null;
            int i2 = 0;
            while (i2 != this.ordering.size()) {
                ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
                DERObjectIdentifier dERObjectIdentifier2 = (DERObjectIdentifier) this.ordering.elementAt(i2);
                aSN1EncodableVector3.add(dERObjectIdentifier2);
                aSN1EncodableVector3.add(this.converter.getConvertedValue(dERObjectIdentifier2, (String) this.values.elementAt(i2)));
                if (dERObjectIdentifier == null || ((Boolean) this.added.elementAt(i2)).booleanValue()) {
                    dERSequence = new DERSequence(aSN1EncodableVector3);
                } else {
                    aSN1EncodableVector.add(new DERSet(aSN1EncodableVector2));
                    aSN1EncodableVector2 = new ASN1EncodableVector();
                    dERSequence = new DERSequence(aSN1EncodableVector3);
                }
                aSN1EncodableVector2.add(dERSequence);
                i2++;
                dERObjectIdentifier = dERObjectIdentifier2;
            }
            aSN1EncodableVector.add(new DERSet(aSN1EncodableVector2));
            this.seq = new DERSequence(aSN1EncodableVector);
        }
        return this.seq;
    }

    public String toString() {
        return toString(DefaultReverse, DefaultSymbols);
    }

    public String toString(boolean z2, Hashtable hashtable) {
        StringBuffer stringBuffer = new StringBuffer();
        Vector vector = new Vector();
        StringBuffer stringBuffer2 = null;
        for (int i2 = 0; i2 < this.ordering.size(); i2++) {
            if (((Boolean) this.added.elementAt(i2)).booleanValue()) {
                stringBuffer2.append('+');
                appendValue(stringBuffer2, hashtable, (DERObjectIdentifier) this.ordering.elementAt(i2), (String) this.values.elementAt(i2));
            } else {
                stringBuffer2 = new StringBuffer();
                appendValue(stringBuffer2, hashtable, (DERObjectIdentifier) this.ordering.elementAt(i2), (String) this.values.elementAt(i2));
                vector.addElement(stringBuffer2);
            }
        }
        boolean z3 = true;
        if (z2) {
            for (int size = vector.size() - 1; size >= 0; size--) {
                if (z3) {
                    z3 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(size).toString());
            }
        } else {
            for (int i3 = 0; i3 < vector.size(); i3++) {
                if (z3) {
                    z3 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(i3).toString());
            }
        }
        return stringBuffer.toString();
    }
}
