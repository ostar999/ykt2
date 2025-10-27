package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Date;

/* loaded from: classes4.dex */
public final class DigitalDocumentBuilder extends IndexableBuilder<DigitalDocumentBuilder> {
    public DigitalDocumentBuilder() {
        super("DigitalDocument");
    }

    public final DigitalDocumentBuilder setAuthor(@NonNull PersonBuilder... personBuilderArr) {
        return put(SocializeProtocolConstants.AUTHOR, personBuilderArr);
    }

    public final DigitalDocumentBuilder setDateCreated(@NonNull Date date) {
        return put("dateCreated", date.getTime());
    }

    public final DigitalDocumentBuilder setDateModified(@NonNull Date date) {
        return put("dateModified", date.getTime());
    }

    public final DigitalDocumentBuilder setHasDigitalDocumentPermission(@NonNull DigitalDocumentPermissionBuilder... digitalDocumentPermissionBuilderArr) {
        return put("hasDigitalDocumentPermission", digitalDocumentPermissionBuilderArr);
    }

    public final DigitalDocumentBuilder setText(@NonNull String str) {
        return put("text", str);
    }

    public DigitalDocumentBuilder(String str) {
        super(str);
    }
}
