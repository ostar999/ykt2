package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 62\u00020\u0001:\u000256B}\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011Bi\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005¢\u0006\u0002\u0010\u0012J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0005HÆ\u0003J\t\u0010!\u001a\u00020\u0005HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0005HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003J\t\u0010%\u001a\u00020\u0005HÆ\u0003J\t\u0010&\u001a\u00020\u0005HÆ\u0003J\t\u0010'\u001a\u00020\u0005HÆ\u0003Jm\u0010(\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u0005HÆ\u0001J\u0013\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010,\u001a\u00020\u0003HÖ\u0001J\t\u0010-\u001a\u00020\u0005HÖ\u0001J!\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0014R\u0011\u0010\r\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0014¨\u00067"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillShare;", "", "seen1", "", "id", "", "inviteSign", "practiceListId", "shareCodeUrl", "shareH5Url", "shareIconUrl", "shareIntroduce", "shareJumpLink", "shareTitle", "shareVideoUrl", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getInviteSign", "getPracticeListId", "getShareCodeUrl", "getShareH5Url", "getShareIconUrl", "getShareIntroduce", "getShareJumpLink", "getShareTitle", "getShareVideoUrl", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class SkillShare {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String id;

    @NotNull
    private final String inviteSign;

    @NotNull
    private final String practiceListId;

    @NotNull
    private final String shareCodeUrl;

    @NotNull
    private final String shareH5Url;

    @NotNull
    private final String shareIconUrl;

    @NotNull
    private final String shareIntroduce;

    @NotNull
    private final String shareJumpLink;

    @NotNull
    private final String shareTitle;

    @NotNull
    private final String shareVideoUrl;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillShare$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/SkillShare;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<SkillShare> serializer() {
            return SkillShare$$serializer.INSTANCE;
        }
    }

    public SkillShare() {
        this((String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, 1023, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ SkillShare(int i2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, SkillShare$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.id = "";
        } else {
            this.id = str;
        }
        if ((i2 & 2) == 0) {
            this.inviteSign = "";
        } else {
            this.inviteSign = str2;
        }
        if ((i2 & 4) == 0) {
            this.practiceListId = "";
        } else {
            this.practiceListId = str3;
        }
        if ((i2 & 8) == 0) {
            this.shareCodeUrl = "";
        } else {
            this.shareCodeUrl = str4;
        }
        if ((i2 & 16) == 0) {
            this.shareH5Url = "";
        } else {
            this.shareH5Url = str5;
        }
        if ((i2 & 32) == 0) {
            this.shareIconUrl = "";
        } else {
            this.shareIconUrl = str6;
        }
        if ((i2 & 64) == 0) {
            this.shareIntroduce = "";
        } else {
            this.shareIntroduce = str7;
        }
        if ((i2 & 128) == 0) {
            this.shareJumpLink = "";
        } else {
            this.shareJumpLink = str8;
        }
        if ((i2 & 256) == 0) {
            this.shareTitle = "";
        } else {
            this.shareTitle = str9;
        }
        if ((i2 & 512) == 0) {
            this.shareVideoUrl = "";
        } else {
            this.shareVideoUrl = str10;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull SkillShare self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.id, "")) {
            output.encodeStringElement(serialDesc, 0, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.inviteSign, "")) {
            output.encodeStringElement(serialDesc, 1, self.inviteSign);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.practiceListId, "")) {
            output.encodeStringElement(serialDesc, 2, self.practiceListId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.shareCodeUrl, "")) {
            output.encodeStringElement(serialDesc, 3, self.shareCodeUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.shareH5Url, "")) {
            output.encodeStringElement(serialDesc, 4, self.shareH5Url);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.shareIconUrl, "")) {
            output.encodeStringElement(serialDesc, 5, self.shareIconUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.shareIntroduce, "")) {
            output.encodeStringElement(serialDesc, 6, self.shareIntroduce);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.shareJumpLink, "")) {
            output.encodeStringElement(serialDesc, 7, self.shareJumpLink);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.shareTitle, "")) {
            output.encodeStringElement(serialDesc, 8, self.shareTitle);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.shareVideoUrl, "")) {
            output.encodeStringElement(serialDesc, 9, self.shareVideoUrl);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component10, reason: from getter */
    public final String getShareVideoUrl() {
        return this.shareVideoUrl;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getInviteSign() {
        return this.inviteSign;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getPracticeListId() {
        return this.practiceListId;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getShareCodeUrl() {
        return this.shareCodeUrl;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getShareH5Url() {
        return this.shareH5Url;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getShareIconUrl() {
        return this.shareIconUrl;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getShareIntroduce() {
        return this.shareIntroduce;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getShareJumpLink() {
        return this.shareJumpLink;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getShareTitle() {
        return this.shareTitle;
    }

    @NotNull
    public final SkillShare copy(@NotNull String id, @NotNull String inviteSign, @NotNull String practiceListId, @NotNull String shareCodeUrl, @NotNull String shareH5Url, @NotNull String shareIconUrl, @NotNull String shareIntroduce, @NotNull String shareJumpLink, @NotNull String shareTitle, @NotNull String shareVideoUrl) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(inviteSign, "inviteSign");
        Intrinsics.checkNotNullParameter(practiceListId, "practiceListId");
        Intrinsics.checkNotNullParameter(shareCodeUrl, "shareCodeUrl");
        Intrinsics.checkNotNullParameter(shareH5Url, "shareH5Url");
        Intrinsics.checkNotNullParameter(shareIconUrl, "shareIconUrl");
        Intrinsics.checkNotNullParameter(shareIntroduce, "shareIntroduce");
        Intrinsics.checkNotNullParameter(shareJumpLink, "shareJumpLink");
        Intrinsics.checkNotNullParameter(shareTitle, "shareTitle");
        Intrinsics.checkNotNullParameter(shareVideoUrl, "shareVideoUrl");
        return new SkillShare(id, inviteSign, practiceListId, shareCodeUrl, shareH5Url, shareIconUrl, shareIntroduce, shareJumpLink, shareTitle, shareVideoUrl);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SkillShare)) {
            return false;
        }
        SkillShare skillShare = (SkillShare) other;
        return Intrinsics.areEqual(this.id, skillShare.id) && Intrinsics.areEqual(this.inviteSign, skillShare.inviteSign) && Intrinsics.areEqual(this.practiceListId, skillShare.practiceListId) && Intrinsics.areEqual(this.shareCodeUrl, skillShare.shareCodeUrl) && Intrinsics.areEqual(this.shareH5Url, skillShare.shareH5Url) && Intrinsics.areEqual(this.shareIconUrl, skillShare.shareIconUrl) && Intrinsics.areEqual(this.shareIntroduce, skillShare.shareIntroduce) && Intrinsics.areEqual(this.shareJumpLink, skillShare.shareJumpLink) && Intrinsics.areEqual(this.shareTitle, skillShare.shareTitle) && Intrinsics.areEqual(this.shareVideoUrl, skillShare.shareVideoUrl);
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getInviteSign() {
        return this.inviteSign;
    }

    @NotNull
    public final String getPracticeListId() {
        return this.practiceListId;
    }

    @NotNull
    public final String getShareCodeUrl() {
        return this.shareCodeUrl;
    }

    @NotNull
    public final String getShareH5Url() {
        return this.shareH5Url;
    }

    @NotNull
    public final String getShareIconUrl() {
        return this.shareIconUrl;
    }

    @NotNull
    public final String getShareIntroduce() {
        return this.shareIntroduce;
    }

    @NotNull
    public final String getShareJumpLink() {
        return this.shareJumpLink;
    }

    @NotNull
    public final String getShareTitle() {
        return this.shareTitle;
    }

    @NotNull
    public final String getShareVideoUrl() {
        return this.shareVideoUrl;
    }

    public int hashCode() {
        return (((((((((((((((((this.id.hashCode() * 31) + this.inviteSign.hashCode()) * 31) + this.practiceListId.hashCode()) * 31) + this.shareCodeUrl.hashCode()) * 31) + this.shareH5Url.hashCode()) * 31) + this.shareIconUrl.hashCode()) * 31) + this.shareIntroduce.hashCode()) * 31) + this.shareJumpLink.hashCode()) * 31) + this.shareTitle.hashCode()) * 31) + this.shareVideoUrl.hashCode();
    }

    @NotNull
    public String toString() {
        return "SkillShare(id=" + this.id + ", inviteSign=" + this.inviteSign + ", practiceListId=" + this.practiceListId + ", shareCodeUrl=" + this.shareCodeUrl + ", shareH5Url=" + this.shareH5Url + ", shareIconUrl=" + this.shareIconUrl + ", shareIntroduce=" + this.shareIntroduce + ", shareJumpLink=" + this.shareJumpLink + ", shareTitle=" + this.shareTitle + ", shareVideoUrl=" + this.shareVideoUrl + ")";
    }

    public SkillShare(@NotNull String id, @NotNull String inviteSign, @NotNull String practiceListId, @NotNull String shareCodeUrl, @NotNull String shareH5Url, @NotNull String shareIconUrl, @NotNull String shareIntroduce, @NotNull String shareJumpLink, @NotNull String shareTitle, @NotNull String shareVideoUrl) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(inviteSign, "inviteSign");
        Intrinsics.checkNotNullParameter(practiceListId, "practiceListId");
        Intrinsics.checkNotNullParameter(shareCodeUrl, "shareCodeUrl");
        Intrinsics.checkNotNullParameter(shareH5Url, "shareH5Url");
        Intrinsics.checkNotNullParameter(shareIconUrl, "shareIconUrl");
        Intrinsics.checkNotNullParameter(shareIntroduce, "shareIntroduce");
        Intrinsics.checkNotNullParameter(shareJumpLink, "shareJumpLink");
        Intrinsics.checkNotNullParameter(shareTitle, "shareTitle");
        Intrinsics.checkNotNullParameter(shareVideoUrl, "shareVideoUrl");
        this.id = id;
        this.inviteSign = inviteSign;
        this.practiceListId = practiceListId;
        this.shareCodeUrl = shareCodeUrl;
        this.shareH5Url = shareH5Url;
        this.shareIconUrl = shareIconUrl;
        this.shareIntroduce = shareIntroduce;
        this.shareJumpLink = shareJumpLink;
        this.shareTitle = shareTitle;
        this.shareVideoUrl = shareVideoUrl;
    }

    public /* synthetic */ SkillShare(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? "" : str2, (i2 & 4) != 0 ? "" : str3, (i2 & 8) != 0 ? "" : str4, (i2 & 16) != 0 ? "" : str5, (i2 & 32) != 0 ? "" : str6, (i2 & 64) != 0 ? "" : str7, (i2 & 128) != 0 ? "" : str8, (i2 & 256) != 0 ? "" : str9, (i2 & 512) == 0 ? str10 : "");
    }
}
