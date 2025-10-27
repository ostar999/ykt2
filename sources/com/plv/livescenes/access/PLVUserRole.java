package com.plv.livescenes.access;

import com.plv.foundationsdk.utils.PLVSugarUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'STREAMER_TEACHER' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes2.dex */
public final class PLVUserRole {
    private static final /* synthetic */ PLVUserRole[] $VALUES;
    public static final PLVUserRole HI_CLASS_NORMAL_STUDENT;
    public static final PLVUserRole HI_CLASS_TEACHER;
    public static final PLVUserRole STREAMER_GRANTED_PAINT_USER;
    public static final PLVUserRole STREAMER_GRANTED_SPEAKER_USER;
    public static final PLVUserRole STREAMER_NORMAL_GUEST;
    public static final PLVUserRole STREAMER_TEACHER;
    private final List<PLVUserAbility> abilities;

    static {
        PLVUserRole pLVUserRole = new PLVUserRole("HI_CLASS_TEACHER", 0, PLVSugarUtil.listOf(PLVUserAbility.HI_CLASS_ZOOM_CAN_DRAG_ITEM_VIEW, PLVUserAbility.HI_CLASS_ZOOM_CAN_SEND_UPDATE_ITEM_VIEW, PLVUserAbility.HI_CLASS_ZOOM_CAN_SEND_REMOVE_ITEM_VIEW));
        HI_CLASS_TEACHER = pLVUserRole;
        PLVUserRole pLVUserRole2 = new PLVUserRole("HI_CLASS_NORMAL_STUDENT", 1, PLVSugarUtil.listOf(PLVUserAbility.HI_CLASS_ZOOM_NEED_REACT_UPDATE_EVENT));
        HI_CLASS_NORMAL_STUDENT = pLVUserRole2;
        PLVUserAbility pLVUserAbility = PLVUserAbility.STREAMER_DOCUMENT_ALLOW_UPLOAD_PPT;
        PLVUserAbility pLVUserAbility2 = PLVUserAbility.STREAMER_DOCUMENT_ALLOW_OPEN_PPT;
        PLVUserAbility pLVUserAbility3 = PLVUserAbility.STREAMER_DOCUMENT_ALLOW_PULL_PPT;
        PLVUserAbility pLVUserAbility4 = PLVUserAbility.STREAMER_DOCUMENT_ALLOW_DELETE_PPT;
        PLVUserAbility pLVUserAbility5 = PLVUserAbility.STREAMER_DOCUMENT_ALLOW_SWITCH_PPT_WHITEBOARD;
        PLVUserAbility pLVUserAbility6 = PLVUserAbility.STREAMER_DOCUMENT_ALLOW_TURN_PAGE;
        PLVUserAbility pLVUserAbility7 = PLVUserAbility.STREAMER_DOCUMENT_ALLOW_WHITE_BOARD_ADD;
        PLVUserAbility pLVUserAbility8 = PLVUserAbility.STREAMER_DOCUMENT_ALLOW_USE_PAINT;
        PLVUserAbility pLVUserAbility9 = PLVUserAbility.STREAMER_DOCUMENT_ALLOW_SWITCH_WITH_FIRST_SCREEN_TO_ALL_USER;
        PLVUserAbility pLVUserAbility10 = PLVUserAbility.STREAMER_GRANT_PERMISSION_SHARE_SCREEN;
        PLVUserAbility pLVUserAbility11 = PLVUserAbility.STREAMER_MEMBER_TRANSFER_SPEAKER_PERMISSION;
        PLVUserRole pLVUserRole3 = new PLVUserRole("STREAMER_TEACHER", 2, PLVSugarUtil.listOf(pLVUserAbility, pLVUserAbility2, pLVUserAbility3, pLVUserAbility4, pLVUserAbility5, pLVUserAbility6, pLVUserAbility7, pLVUserAbility8, pLVUserAbility9, pLVUserAbility10, PLVUserAbility.STREAMER_MEMBER_CONTROL_SPEAKER_PERMISSION, pLVUserAbility11, PLVUserAbility.STREAMER_ALONE_ALLOW_CHANGE_PUSH_RATIO));
        STREAMER_TEACHER = pLVUserRole3;
        PLVUserRole pLVUserRole4 = new PLVUserRole("STREAMER_NORMAL_GUEST", 3, new ArrayList());
        STREAMER_NORMAL_GUEST = pLVUserRole4;
        PLVUserRole pLVUserRole5 = new PLVUserRole("STREAMER_GRANTED_SPEAKER_USER", 4, PLVSugarUtil.listOf(pLVUserAbility, pLVUserAbility2, pLVUserAbility3, pLVUserAbility4, pLVUserAbility5, pLVUserAbility6, pLVUserAbility7, pLVUserAbility8, pLVUserAbility9, pLVUserAbility10, pLVUserAbility11));
        STREAMER_GRANTED_SPEAKER_USER = pLVUserRole5;
        PLVUserRole pLVUserRole6 = new PLVUserRole("STREAMER_GRANTED_PAINT_USER", 5, PLVSugarUtil.listOf(pLVUserAbility8));
        STREAMER_GRANTED_PAINT_USER = pLVUserRole6;
        $VALUES = new PLVUserRole[]{pLVUserRole, pLVUserRole2, pLVUserRole3, pLVUserRole4, pLVUserRole5, pLVUserRole6};
    }

    private PLVUserRole(String str, int i2, List list) {
        this.abilities = Collections.unmodifiableList(list);
    }

    public static PLVUserRole valueOf(String str) {
        return (PLVUserRole) Enum.valueOf(PLVUserRole.class, str);
    }

    public static PLVUserRole[] values() {
        return (PLVUserRole[]) $VALUES.clone();
    }

    public List<PLVUserAbility> getAbilities() {
        return this.abilities;
    }

    public boolean hasAbility(PLVUserAbility pLVUserAbility) {
        return this.abilities.contains(pLVUserAbility);
    }
}
