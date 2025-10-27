package org.bouncycastle.voms;

import cn.hutool.core.text.StrPool;
import java.util.List;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.IetfAttrSyntax;
import org.bouncycastle.x509.X509Attribute;
import org.bouncycastle.x509.X509AttributeCertificate;

/* loaded from: classes9.dex */
public class VOMSAttribute {
    public static final String VOMS_ATTR_OID = "1.3.6.1.4.1.8005.100.100.4";
    private X509AttributeCertificate myAC;
    private String myHostPort;
    private String myVo;
    private Vector myStringList = new Vector();
    private Vector myFQANs = new Vector();

    public class FQAN {
        String capability;
        String fqan;
        String group;
        String role;

        public FQAN(String str) {
            this.fqan = str;
        }

        public FQAN(String str, String str2, String str3) {
            this.group = str;
            this.role = str2;
            this.capability = str3;
        }

        public String getCapability() {
            if (this.group == null && this.fqan != null) {
                split();
            }
            return this.capability;
        }

        public String getFQAN() {
            String str = this.fqan;
            if (str != null) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.group);
            sb.append("/Role=");
            String str2 = this.role;
            String str3 = "";
            if (str2 == null) {
                str2 = "";
            }
            sb.append(str2);
            if (this.capability != null) {
                str3 = "/Capability=" + this.capability;
            }
            sb.append(str3);
            String string = sb.toString();
            this.fqan = string;
            return string;
        }

        public String getGroup() {
            if (this.group == null && this.fqan != null) {
                split();
            }
            return this.group;
        }

        public String getRole() {
            if (this.group == null && this.fqan != null) {
                split();
            }
            return this.role;
        }

        public void split() {
            this.fqan.length();
            int iIndexOf = this.fqan.indexOf("/Role=");
            if (iIndexOf < 0) {
                return;
            }
            this.group = this.fqan.substring(0, iIndexOf);
            int i2 = iIndexOf + 6;
            int iIndexOf2 = this.fqan.indexOf("/Capability=", i2);
            String str = this.fqan;
            String strSubstring = iIndexOf2 < 0 ? str.substring(i2) : str.substring(i2, iIndexOf2);
            String str2 = null;
            if (strSubstring.length() == 0) {
                strSubstring = null;
            }
            this.role = strSubstring;
            String strSubstring2 = iIndexOf2 < 0 ? null : this.fqan.substring(iIndexOf2 + 12);
            if (strSubstring2 != null && strSubstring2.length() != 0) {
                str2 = strSubstring2;
            }
            this.capability = str2;
        }

        public String toString() {
            return getFQAN();
        }
    }

    public VOMSAttribute(X509AttributeCertificate x509AttributeCertificate) {
        if (x509AttributeCertificate == null) {
            throw new IllegalArgumentException("VOMSAttribute: AttributeCertificate is NULL");
        }
        this.myAC = x509AttributeCertificate;
        X509Attribute[] attributes = x509AttributeCertificate.getAttributes(VOMS_ATTR_OID);
        if (attributes == null) {
            return;
        }
        for (int i2 = 0; i2 != attributes.length; i2++) {
            try {
                IetfAttrSyntax ietfAttrSyntax = new IetfAttrSyntax((ASN1Sequence) attributes[i2].getValues()[0]);
                String string = ((DERIA5String) GeneralName.getInstance(((ASN1Sequence) ietfAttrSyntax.getPolicyAuthority().getDERObject()).getObjectAt(0)).getName()).getString();
                int iIndexOf = string.indexOf("://");
                if (iIndexOf < 0 || iIndexOf == string.length() - 1) {
                    throw new IllegalArgumentException("Bad encoding of VOMS policyAuthority : [" + string + StrPool.BRACKET_END);
                }
                this.myVo = string.substring(0, iIndexOf);
                this.myHostPort = string.substring(iIndexOf + 3);
                if (ietfAttrSyntax.getValueType() != 1) {
                    throw new IllegalArgumentException("VOMS attribute values are not encoded as octet strings, policyAuthority = " + string);
                }
                ASN1OctetString[] aSN1OctetStringArr = (ASN1OctetString[]) ietfAttrSyntax.getValues();
                for (int i3 = 0; i3 != aSN1OctetStringArr.length; i3++) {
                    String str = new String(aSN1OctetStringArr[i3].getOctets());
                    FQAN fqan = new FQAN(str);
                    if (!this.myStringList.contains(str)) {
                        if (str.startsWith("/" + this.myVo + "/")) {
                            this.myStringList.add(str);
                            this.myFQANs.add(fqan);
                        }
                    }
                }
            } catch (IllegalArgumentException e2) {
                throw e2;
            } catch (Exception unused) {
                throw new IllegalArgumentException("Badly encoded VOMS extension in AC issued by " + x509AttributeCertificate.getIssuer());
            }
        }
    }

    public X509AttributeCertificate getAC() {
        return this.myAC;
    }

    public List getFullyQualifiedAttributes() {
        return this.myStringList;
    }

    public String getHostPort() {
        return this.myHostPort;
    }

    public List getListOfFQAN() {
        return this.myFQANs;
    }

    public String getVO() {
        return this.myVo;
    }

    public String toString() {
        return "VO      :" + this.myVo + "\nHostPort:" + this.myHostPort + "\nFQANs   :" + this.myFQANs;
    }
}
