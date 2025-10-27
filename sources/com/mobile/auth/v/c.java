package com.mobile.auth.v;

import android.content.Context;
import cn.hutool.core.date.DatePattern;
import com.aliyun.auth.core.AliyunVodKey;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JsonerTag;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;

/* loaded from: classes4.dex */
public abstract class c {
    public static final SimpleDateFormat POP_REQUEST_DATE_FORMAT;
    public static final SimpleDateFormat REQUEST_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @JsonerTag(keyName = AliyunVodKey.KEY_VOD_ACTION)
    private String Action;
    private String accessKeySecret;
    private String baseUrl;
    protected String requestMethod;
    private String sign;
    protected String method = null;

    @JsonerTag(keyName = "Timestamp")
    private String timestamp = POP_REQUEST_DATE_FORMAT.format(new Date());
    protected boolean isSign = true;

    @JsonerTag(keyName = AliyunVodKey.KEY_VOD_COMMON_SIGNATURE_METHOD)
    private String signatureMethod = "HMAC-SHA1";

    @JsonerTag(keyName = AliyunVodKey.KEY_VOD_COMMON_SIGNATURE_NONCE)
    private String SignatureNonce = UUID.randomUUID().toString();

    @JsonerTag(keyName = AliyunVodKey.KEY_VOD_COMMON_SIGNATURE_VERSION)
    private String SignatureVersion = "1.0";

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.UTC_PATTERN);
        POP_REQUEST_DATE_FORMAT = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("O"));
    }

    public String buildPopRequestParamas() {
        try {
            List<Field> listA = b.a(getClass());
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            TreeMap treeMap = new TreeMap();
            for (Field field : listA) {
                JsonerTag jsonerTag = (JsonerTag) field.getAnnotation(JsonerTag.class);
                if (jsonerTag != null) {
                    String strKeyName = jsonerTag.keyName();
                    field.setAccessible(true);
                    try {
                        Object obj = field.get(this);
                        if (obj != null) {
                            treeMap.put(strKeyName, obj);
                        }
                    } catch (IllegalAccessException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            for (Map.Entry entry : treeMap.entrySet()) {
                sb2.append("&");
                sb2.append(b.a((String) entry.getKey()));
                sb2.append("=");
                sb2.append(b.a(entry.getValue() == null ? "" : entry.getValue().toString()));
            }
            sb.append("POST");
            sb.append("&");
            sb.append(b.a("/"));
            sb.append("&");
            sb.append(b.a(sb2.toString().substring(1)));
            if (!isSign()) {
                com.mobile.auth.o.a.a((Context) null).b("param:", sb2.toString());
                return sb2.toString();
            }
            return ((Object) sb2) + "&Signature=" + b.a(b.a(sb, this.accessKeySecret + "&"));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String getAccessKeySecret() {
        try {
            return this.accessKeySecret;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String getAction() {
        try {
            return this.Action;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String getBaseUrl() {
        try {
            return this.baseUrl;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String getMethod() {
        try {
            return this.method;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String getRequestMethod() {
        try {
            return this.requestMethod;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String getSign() {
        try {
            return this.sign;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public boolean isSign() {
        try {
            return this.isSign;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public void setAccessKeySecret(String str) {
        try {
            this.accessKeySecret = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setAction(String str) {
        try {
            this.Action = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setBaseUrl(String str) {
        try {
            this.baseUrl = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setMethod(String str) {
        try {
            this.method = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setRequestMethod(String str) {
        try {
            this.requestMethod = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setSign(boolean z2) {
        try {
            this.isSign = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
