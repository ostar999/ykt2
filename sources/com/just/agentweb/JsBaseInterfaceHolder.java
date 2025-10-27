package com.just.agentweb;

import android.webkit.JavascriptInterface;
import com.just.agentweb.AgentWeb;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public abstract class JsBaseInterfaceHolder implements JsInterfaceHolder {
    private AgentWeb.SecurityType mSecurityType;
    private WebCreator mWebCreator;

    public JsBaseInterfaceHolder(WebCreator webCreator, AgentWeb.SecurityType securityType) {
        this.mSecurityType = securityType;
        this.mWebCreator = webCreator;
    }

    @Override // com.just.agentweb.JsInterfaceHolder
    public boolean checkObject(Object obj) throws SecurityException {
        if (this.mWebCreator.getWebViewType() == 2) {
            return true;
        }
        boolean z2 = false;
        for (Method method : obj.getClass().getMethods()) {
            Annotation[] annotations = method.getAnnotations();
            int length = annotations.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if (annotations[i2] instanceof JavascriptInterface) {
                    z2 = true;
                    break;
                }
                i2++;
            }
            if (z2) {
                break;
            }
        }
        return z2;
    }

    public boolean checkSecurity() {
        if (this.mSecurityType != AgentWeb.SecurityType.STRICT_CHECK) {
            return true;
        }
        this.mWebCreator.getWebViewType();
        return true;
    }
}
