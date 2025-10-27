package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class DigitalDocumentPermissionBuilder extends IndexableBuilder<DigitalDocumentPermissionBuilder> {
    public static final String COMMENT_PERMISSION = "CommentPermission";
    public static final String READ_PERMISSION = "ReadPermission";
    public static final String WRITE_PERMISSION = "WritePermission";

    public DigitalDocumentPermissionBuilder() {
        super("DigitalDocumentPermission");
    }

    public final DigitalDocumentPermissionBuilder setGrantee(@NonNull PersonBuilder... personBuilderArr) {
        return put("grantee", personBuilderArr);
    }

    public final DigitalDocumentPermissionBuilder setPermissionType(@NonNull String str) {
        return put("permissionType", str);
    }
}
