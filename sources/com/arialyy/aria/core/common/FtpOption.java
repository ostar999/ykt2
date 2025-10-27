package com.arialyy.aria.core.common;

import android.text.TextUtils;
import c.c;
import com.alipay.sdk.sys.a;
import com.arialyy.aria.core.FtpUrlEntity;
import com.arialyy.aria.core.IdEntity;
import com.arialyy.aria.core.processor.IFtpUploadInterceptor;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CheckUtil;
import com.caverock.androidsvg.SVGParser;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.text.DateFormatSymbols;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public class FtpOption extends BaseOption {
    private static final Map<String, Object> LANGUAGE_CODE_MAP;
    private String account;
    private String activeExternalIPAddress;
    private String charSet;
    private int maxPort;
    private int minPort;
    private String password;
    private String protocol;
    private IFtpUploadInterceptor uploadInterceptor;
    private FtpUrlEntity urlEntity;
    private String userName;
    private boolean isNeedLogin = false;
    private boolean isImplicit = true;
    private int connMode = 0;
    private String defaultDateFormatStr = null;
    private String recentDateFormatStr = null;
    private String serverLanguageCode = null;
    private String shortMonthNames = null;
    private String serverTimeZoneId = null;
    private String systemKey = FTPServerIdentifier.SYST_UNIX;
    private IdEntity idEntity = new IdEntity();

    public interface FTPServerIdentifier {
        public static final String SYST_AS400 = "AS/400";
        public static final String SYST_L8 = "TYPE: L8";
        public static final String SYST_MACOS_PETER = "MACOS PETER";
        public static final String SYST_MVS = "MVS";
        public static final String SYST_NETWARE = "NETWARE";
        public static final String SYST_NT = "WINDOWS";
        public static final String SYST_OS2 = "OS/2";
        public static final String SYST_OS400 = "OS/400";
        public static final String SYST_UNIX = "UNIX";
        public static final String SYST_UNIX_TRIM_LEADING = "UNIX_LTRIM";
        public static final String SYST_VMS = "VMS";
    }

    static {
        TreeMap treeMap = new TreeMap();
        LANGUAGE_CODE_MAP = treeMap;
        treeMap.put(SocializeProtocolConstants.PROTOCOL_KEY_EN, Locale.ENGLISH);
        treeMap.put(SocializeProtocolConstants.PROTOCOL_KEY_DE, Locale.GERMAN);
        treeMap.put(AdvanceSetting.NETWORK_TYPE, Locale.ITALIAN);
        treeMap.put("es", new Locale("es", "", ""));
        treeMap.put("pt", new Locale("pt", "", ""));
        treeMap.put("da", new Locale("da", "", ""));
        treeMap.put(a.f3323h, new Locale(a.f3323h, "", ""));
        treeMap.put(SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO, new Locale(SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO, "", ""));
        treeMap.put("nl", new Locale("nl", "", ""));
        treeMap.put("ro", new Locale("ro", "", ""));
        treeMap.put("sq", new Locale("sq", "", ""));
        treeMap.put(c.f2218l, new Locale(c.f2218l, "", ""));
        treeMap.put("sk", new Locale("sk", "", ""));
        treeMap.put("sl", new Locale("sl", "", ""));
        treeMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FR, "jan|fév|mar|avr|mai|jun|jui|aoû|sep|oct|nov|déc");
    }

    public static DateFormatSymbols getDateFormatSymbols(String str) {
        String[] strArrSplitShortMonthString = splitShortMonthString(str);
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.US);
        dateFormatSymbols.setShortMonths(strArrSplitShortMonthString);
        return dateFormatSymbols;
    }

    public static Collection<String> getSupportedLanguageCodes() {
        return LANGUAGE_CODE_MAP.keySet();
    }

    public static DateFormatSymbols lookupDateFormatSymbols(String str) {
        Object obj = LANGUAGE_CODE_MAP.get(str);
        if (obj != null) {
            if (obj instanceof Locale) {
                return new DateFormatSymbols((Locale) obj);
            }
            if (obj instanceof String) {
                return getDateFormatSymbols((String) obj);
            }
        }
        return new DateFormatSymbols(Locale.US);
    }

    private static String[] splitShortMonthString(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
        if (12 != stringTokenizer.countTokens()) {
            throw new IllegalArgumentException("expecting a pipe-delimited string containing 12 tokens");
        }
        String[] strArr = new String[13];
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            strArr[i2] = stringTokenizer.nextToken();
            i2++;
        }
        strArr[i2] = "";
        return strArr;
    }

    public FtpOption charSet(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("字符编码为空");
        }
        this.charSet = str;
        return this;
    }

    public FtpOption login(String str, String str2) {
        return login(str, str2, null);
    }

    public FtpOption setActiveExternalIPAddress(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "ip为空");
            return this;
        }
        if (CheckUtil.checkIp(str)) {
            this.activeExternalIPAddress = str;
            return this;
        }
        ALog.e(this.TAG, "ip地址错误：" + str);
        return this;
    }

    public FtpOption setActivePortRange(int i2, int i3) {
        if (i2 > i3) {
            ALog.e(this.TAG, "设置端口范围错误，minPort > maxPort");
            return this;
        }
        if (i2 <= 0 || i2 >= 65535) {
            ALog.e(this.TAG, "端口范围错误");
            return this;
        }
        if (i3 >= 65535) {
            ALog.e(this.TAG, "端口范围错误");
            return this;
        }
        this.minPort = i2;
        this.maxPort = i3;
        return this;
    }

    public FtpOption setAlias(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "设置证书别名失败，证书别名为空");
            return this;
        }
        this.idEntity.keyAlias = str;
        return this;
    }

    public FtpOption setConnectionMode(int i2) {
        if (i2 == 0 || i2 == 1) {
            this.connMode = i2;
            return this;
        }
        ALog.e(this.TAG, "连接模式设置失败，默认启用被动模式");
        return this;
    }

    public FtpOption setDefaultDateFormatStr(String str) {
        this.defaultDateFormatStr = str;
        return this;
    }

    public FtpOption setImplicit(boolean z2) {
        this.isImplicit = z2;
        return this;
    }

    public FtpOption setProtocol(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "设置协议失败，协议信息为空");
            return this;
        }
        this.protocol = str;
        return this;
    }

    public FtpOption setRecentDateFormatStr(String str) {
        this.recentDateFormatStr = str;
        return this;
    }

    public FtpOption setServerIdentifier(String str) {
        this.systemKey = str;
        return this;
    }

    public FtpOption setServerLanguageCode(String str) {
        this.serverLanguageCode = str;
        return this;
    }

    public void setServerTimeZoneId(String str) {
        this.serverTimeZoneId = str;
    }

    public void setShortMonthNames(String str) {
        this.shortMonthNames = str;
    }

    public FtpOption setStorePass(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "设置证书密码失败，证书密码为空");
            return this;
        }
        this.idEntity.storePass = str;
        return this;
    }

    public FtpOption setStorePath(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(this.TAG, "设置证书路径失败，证书路径为空");
            return this;
        }
        this.idEntity.storePath = str;
        return this;
    }

    public FtpOption setUploadInterceptor(IFtpUploadInterceptor iFtpUploadInterceptor) {
        if (iFtpUploadInterceptor == null) {
            throw new NullPointerException("ftp拦截器为空");
        }
        CheckUtil.checkMemberClass(iFtpUploadInterceptor.getClass());
        this.uploadInterceptor = iFtpUploadInterceptor;
        return this;
    }

    public void setUrlEntity(FtpUrlEntity ftpUrlEntity) {
        this.urlEntity = ftpUrlEntity;
        ftpUrlEntity.needLogin = this.isNeedLogin;
        ftpUrlEntity.user = this.userName;
        ftpUrlEntity.password = this.password;
        ftpUrlEntity.account = this.account;
        IdEntity idEntity = this.idEntity;
        ftpUrlEntity.idEntity = idEntity;
        if (TextUtils.isEmpty(idEntity.storePath) && TextUtils.isEmpty(this.idEntity.prvKey)) {
            return;
        }
        ftpUrlEntity.isFtps = true;
        ftpUrlEntity.protocol = this.protocol;
        ftpUrlEntity.isImplicit = this.isImplicit;
    }

    public FtpOption login(String str, String str2, String str3) {
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
        this.account = str3;
        this.isNeedLogin = true;
        return this;
    }
}
