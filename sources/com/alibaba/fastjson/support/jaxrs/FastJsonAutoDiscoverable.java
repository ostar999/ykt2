package com.alibaba.fastjson.support.jaxrs;

import com.yikaobang.yixue.R2;
import javax.annotation.Priority;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;

@Priority(R2.attr.icon_src_selected)
/* loaded from: classes2.dex */
public class FastJsonAutoDiscoverable implements AutoDiscoverable {
    public static volatile boolean autoDiscover = true;

    public void configure(FeatureContext featureContext) {
        if (featureContext.getConfiguration().isRegistered(FastJsonFeature.class) || !autoDiscover) {
            return;
        }
        featureContext.register(FastJsonFeature.class);
    }
}
