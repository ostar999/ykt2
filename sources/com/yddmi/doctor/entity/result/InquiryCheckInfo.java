package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.psychiatrygarden.db.SQLHelper;
import com.psychiatrygarden.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.Transient;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\bW\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 y2\u00020\u0001:\u0002xyBË\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\u0010\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0016\u001a\u00020\u0003\u0012\u0006\u0010\u0017\u001a\u00020\u0003\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a¢\u0006\u0002\u0010\u001bB\u0099\u0002\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\u0012\b\u0002\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0003\u0012\b\b\u0002\u0010 \u001a\u00020\u0003\u0012\b\b\u0002\u0010!\u001a\u00020\u0013¢\u0006\u0002\u0010\"J\u0010\u0010S\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010&J\u000b\u0010T\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010U\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010V\u001a\u00020\u0003HÆ\u0003J\t\u0010W\u001a\u00020\u0013HÆ\u0003J\u000b\u0010X\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010Y\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010&J\t\u0010Z\u001a\u00020\u0003HÆ\u0003J\t\u0010[\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\\\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010]\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010^\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010_\u001a\u00020\u0003HÆ\u0003J\t\u0010`\u001a\u00020\u0013HÆ\u0003J\t\u0010a\u001a\u00020\u0003HÆ\u0003J\t\u0010b\u001a\u00020\u0003HÆ\u0003J\t\u0010c\u001a\u00020\u0013HÆ\u0003J\u000b\u0010d\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010e\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010f\u001a\u00020\u0003HÆ\u0003J\u0010\u0010g\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010&J\u000b\u0010h\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0013\u0010i\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\rHÆ\u0003J\u000b\u0010j\u001a\u0004\u0018\u00010\u0006HÆ\u0003J¢\u0002\u0010k\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0012\b\u0002\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u001d\u001a\u00020\u00032\b\b\u0002\u0010\u001e\u001a\u00020\u00132\b\b\u0002\u0010\u001f\u001a\u00020\u00032\b\b\u0002\u0010 \u001a\u00020\u00032\b\b\u0002\u0010!\u001a\u00020\u0013HÆ\u0001¢\u0006\u0002\u0010lJ\u0013\u0010m\u001a\u00020\u00132\b\u0010n\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010o\u001a\u00020\u0003HÖ\u0001J\t\u0010p\u001a\u00020\u0006HÖ\u0001J!\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u00020\u00002\u0006\u0010t\u001a\u00020u2\u0006\u0010v\u001a\u00020wHÇ\u0001R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0015\u0010\u0015\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010'\u001a\u0004\b%\u0010&R\u0011\u0010\u0016\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R$\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b.\u0010$R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b/\u0010$R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010'\u001a\u0004\b0\u0010&R\u0011\u0010\u0017\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010)R&\u0010\u001c\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b1\u00102\u001a\u0004\b3\u0010$\"\u0004\b4\u00105R$\u0010!\u001a\u00020\u00138\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b6\u00102\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R$\u0010\u001f\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b;\u00102\u001a\u0004\b<\u0010)\"\u0004\b=\u0010>R$\u0010 \u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b?\u00102\u001a\u0004\b@\u0010)\"\u0004\bA\u0010>R$\u0010\u001e\u001a\u00020\u00138\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\bB\u00102\u001a\u0004\bC\u00108\"\u0004\bD\u0010:R$\u0010\u001d\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\bE\u00102\u001a\u0004\bF\u0010)\"\u0004\bG\u0010>R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bH\u0010$R\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010)\"\u0004\bJ\u0010>R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bK\u0010$R\u0011\u0010\u0011\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bL\u0010)R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bM\u0010$R\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010'\u001a\u0004\bN\u0010&R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bO\u0010$R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\bP\u00108R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010$R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bR\u0010$¨\u0006z"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "", "seen1", "", "id", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, "", "title", "course", DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, "pid", "result", "children", "", "name", "way", "description", "parentId", SQLHelper.SELECTED, "", "picUrl", "categoryId", "childNode", "isCategory", "ancestors", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Integer;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/lang/String;Ljava/lang/Integer;IILjava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "itemIndex", "itemState", "itemSelected", "itemPosition", "itemSelectNum", "itemIsTitle", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Integer;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/lang/String;Ljava/lang/Integer;IILjava/lang/String;Ljava/lang/String;IZIIZ)V", "getAncestors", "()Ljava/lang/String;", "getCategoryId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getChildNode", "()I", "getChildren", "()Ljava/util/List;", "setChildren", "(Ljava/util/List;)V", "getCourse", "getDescription", "getId", "getItemIndex$annotations", "()V", "getItemIndex", "setItemIndex", "(Ljava/lang/String;)V", "getItemIsTitle$annotations", "getItemIsTitle", "()Z", "setItemIsTitle", "(Z)V", "getItemPosition$annotations", "getItemPosition", "setItemPosition", "(I)V", "getItemSelectNum$annotations", "getItemSelectNum", "setItemSelectNum", "getItemSelected$annotations", "getItemSelected", "setItemSelected", "getItemState$annotations", "getItemState", "setItemState", "getLabel", "getLevel", "setLevel", "getName", "getParentId", "getPicUrl", "getPid", "getResult", "getSelected", "getTitle", "getWay", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Integer;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/lang/String;Ljava/lang/Integer;IILjava/lang/String;Ljava/lang/String;IZIIZ)Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryCheckInfo {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String ancestors;

    @Nullable
    private final Integer categoryId;
    private final int childNode;

    @Nullable
    private List<InquiryCheckInfo> children;

    @Nullable
    private final String course;

    @Nullable
    private final String description;

    @Nullable
    private final Integer id;
    private final int isCategory;

    @Nullable
    private String itemIndex;
    private boolean itemIsTitle;
    private int itemPosition;
    private int itemSelectNum;
    private boolean itemSelected;
    private int itemState;

    @Nullable
    private final String label;
    private int level;

    @Nullable
    private final String name;
    private final int parentId;

    @Nullable
    private final String picUrl;

    @Nullable
    private final Integer pid;

    @Nullable
    private final String result;
    private final boolean selected;

    @Nullable
    private final String title;

    @Nullable
    private final String way;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryCheckInfo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryCheckInfo> serializer() {
            return InquiryCheckInfo$$serializer.INSTANCE;
        }
    }

    public InquiryCheckInfo() {
        this(null, null, null, null, 0, null, null, null, null, null, null, 0, false, null, null, 0, 0, null, null, 0, false, 0, 0, false, 16777215, null);
    }

    public InquiryCheckInfo(@Nullable Integer num, @Nullable String str, @Nullable String str2, @Nullable String str3, int i2, @Nullable Integer num2, @Nullable String str4, @Nullable List<InquiryCheckInfo> list, @Nullable String str5, @Nullable String str6, @Nullable String str7, int i3, boolean z2, @Nullable String str8, @Nullable Integer num3, int i4, int i5, @Nullable String str9, @Nullable String str10, int i6, boolean z3, int i7, int i8, boolean z4) {
        this.id = num;
        this.label = str;
        this.title = str2;
        this.course = str3;
        this.level = i2;
        this.pid = num2;
        this.result = str4;
        this.children = list;
        this.name = str5;
        this.way = str6;
        this.description = str7;
        this.parentId = i3;
        this.selected = z2;
        this.picUrl = str8;
        this.categoryId = num3;
        this.childNode = i4;
        this.isCategory = i5;
        this.ancestors = str9;
        this.itemIndex = str10;
        this.itemState = i6;
        this.itemSelected = z3;
        this.itemPosition = i7;
        this.itemSelectNum = i8;
        this.itemIsTitle = z4;
    }

    @Transient
    public static /* synthetic */ void getItemIndex$annotations() {
    }

    @Transient
    public static /* synthetic */ void getItemIsTitle$annotations() {
    }

    @Transient
    public static /* synthetic */ void getItemPosition$annotations() {
    }

    @Transient
    public static /* synthetic */ void getItemSelectNum$annotations() {
    }

    @Transient
    public static /* synthetic */ void getItemSelected$annotations() {
    }

    @Transient
    public static /* synthetic */ void getItemState$annotations() {
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryCheckInfo self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.id) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.label, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.label);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.title, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.title);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.course, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.course);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.level != 0) {
            output.encodeIntElement(serialDesc, 4, self.level);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || (num2 = self.pid) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 5, IntSerializer.INSTANCE, self.pid);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.result, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.result);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.children, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 7, new ArrayListSerializer(BuiltinSerializersKt.getNullable(InquiryCheckInfo$$serializer.INSTANCE)), self.children);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.way, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.way);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.description, "")) {
            output.encodeNullableSerializableElement(serialDesc, 10, StringSerializer.INSTANCE, self.description);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || self.parentId != -1) {
            output.encodeIntElement(serialDesc, 11, self.parentId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || self.selected) {
            output.encodeBooleanElement(serialDesc, 12, self.selected);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || !Intrinsics.areEqual(self.picUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 13, StringSerializer.INSTANCE, self.picUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || (num3 = self.categoryId) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 14, IntSerializer.INSTANCE, self.categoryId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 15) || self.childNode != -1) {
            output.encodeIntElement(serialDesc, 15, self.childNode);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 16) || self.isCategory != 1) {
            output.encodeIntElement(serialDesc, 16, self.isCategory);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 17) || !Intrinsics.areEqual(self.ancestors, "")) {
            output.encodeNullableSerializableElement(serialDesc, 17, StringSerializer.INSTANCE, self.ancestors);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getWay() {
        return this.way;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* renamed from: component12, reason: from getter */
    public final int getParentId() {
        return this.parentId;
    }

    /* renamed from: component13, reason: from getter */
    public final boolean getSelected() {
        return this.selected;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getPicUrl() {
        return this.picUrl;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final Integer getCategoryId() {
        return this.categoryId;
    }

    /* renamed from: component16, reason: from getter */
    public final int getChildNode() {
        return this.childNode;
    }

    /* renamed from: component17, reason: from getter */
    public final int getIsCategory() {
        return this.isCategory;
    }

    @Nullable
    /* renamed from: component18, reason: from getter */
    public final String getAncestors() {
        return this.ancestors;
    }

    @Nullable
    /* renamed from: component19, reason: from getter */
    public final String getItemIndex() {
        return this.itemIndex;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getLabel() {
        return this.label;
    }

    /* renamed from: component20, reason: from getter */
    public final int getItemState() {
        return this.itemState;
    }

    /* renamed from: component21, reason: from getter */
    public final boolean getItemSelected() {
        return this.itemSelected;
    }

    /* renamed from: component22, reason: from getter */
    public final int getItemPosition() {
        return this.itemPosition;
    }

    /* renamed from: component23, reason: from getter */
    public final int getItemSelectNum() {
        return this.itemSelectNum;
    }

    /* renamed from: component24, reason: from getter */
    public final boolean getItemIsTitle() {
        return this.itemIsTitle;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getCourse() {
        return this.course;
    }

    /* renamed from: component5, reason: from getter */
    public final int getLevel() {
        return this.level;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final Integer getPid() {
        return this.pid;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    @Nullable
    public final List<InquiryCheckInfo> component8() {
        return this.children;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final InquiryCheckInfo copy(@Nullable Integer id, @Nullable String label, @Nullable String title, @Nullable String course, int level, @Nullable Integer pid, @Nullable String result, @Nullable List<InquiryCheckInfo> children, @Nullable String name, @Nullable String way, @Nullable String description, int parentId, boolean selected, @Nullable String picUrl, @Nullable Integer categoryId, int childNode, int isCategory, @Nullable String ancestors, @Nullable String itemIndex, int itemState, boolean itemSelected, int itemPosition, int itemSelectNum, boolean itemIsTitle) {
        return new InquiryCheckInfo(id, label, title, course, level, pid, result, children, name, way, description, parentId, selected, picUrl, categoryId, childNode, isCategory, ancestors, itemIndex, itemState, itemSelected, itemPosition, itemSelectNum, itemIsTitle);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryCheckInfo)) {
            return false;
        }
        InquiryCheckInfo inquiryCheckInfo = (InquiryCheckInfo) other;
        return Intrinsics.areEqual(this.id, inquiryCheckInfo.id) && Intrinsics.areEqual(this.label, inquiryCheckInfo.label) && Intrinsics.areEqual(this.title, inquiryCheckInfo.title) && Intrinsics.areEqual(this.course, inquiryCheckInfo.course) && this.level == inquiryCheckInfo.level && Intrinsics.areEqual(this.pid, inquiryCheckInfo.pid) && Intrinsics.areEqual(this.result, inquiryCheckInfo.result) && Intrinsics.areEqual(this.children, inquiryCheckInfo.children) && Intrinsics.areEqual(this.name, inquiryCheckInfo.name) && Intrinsics.areEqual(this.way, inquiryCheckInfo.way) && Intrinsics.areEqual(this.description, inquiryCheckInfo.description) && this.parentId == inquiryCheckInfo.parentId && this.selected == inquiryCheckInfo.selected && Intrinsics.areEqual(this.picUrl, inquiryCheckInfo.picUrl) && Intrinsics.areEqual(this.categoryId, inquiryCheckInfo.categoryId) && this.childNode == inquiryCheckInfo.childNode && this.isCategory == inquiryCheckInfo.isCategory && Intrinsics.areEqual(this.ancestors, inquiryCheckInfo.ancestors) && Intrinsics.areEqual(this.itemIndex, inquiryCheckInfo.itemIndex) && this.itemState == inquiryCheckInfo.itemState && this.itemSelected == inquiryCheckInfo.itemSelected && this.itemPosition == inquiryCheckInfo.itemPosition && this.itemSelectNum == inquiryCheckInfo.itemSelectNum && this.itemIsTitle == inquiryCheckInfo.itemIsTitle;
    }

    @Nullable
    public final String getAncestors() {
        return this.ancestors;
    }

    @Nullable
    public final Integer getCategoryId() {
        return this.categoryId;
    }

    public final int getChildNode() {
        return this.childNode;
    }

    @Nullable
    public final List<InquiryCheckInfo> getChildren() {
        return this.children;
    }

    @Nullable
    public final String getCourse() {
        return this.course;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    public final String getItemIndex() {
        return this.itemIndex;
    }

    public final boolean getItemIsTitle() {
        return this.itemIsTitle;
    }

    public final int getItemPosition() {
        return this.itemPosition;
    }

    public final int getItemSelectNum() {
        return this.itemSelectNum;
    }

    public final boolean getItemSelected() {
        return this.itemSelected;
    }

    public final int getItemState() {
        return this.itemState;
    }

    @Nullable
    public final String getLabel() {
        return this.label;
    }

    public final int getLevel() {
        return this.level;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    public final int getParentId() {
        return this.parentId;
    }

    @Nullable
    public final String getPicUrl() {
        return this.picUrl;
    }

    @Nullable
    public final Integer getPid() {
        return this.pid;
    }

    @Nullable
    public final String getResult() {
        return this.result;
    }

    public final boolean getSelected() {
        return this.selected;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getWay() {
        return this.way;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        Integer num = this.id;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.label;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.title;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.course;
        int iHashCode4 = (((iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.level) * 31;
        Integer num2 = this.pid;
        int iHashCode5 = (iHashCode4 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str4 = this.result;
        int iHashCode6 = (iHashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        List<InquiryCheckInfo> list = this.children;
        int iHashCode7 = (iHashCode6 + (list == null ? 0 : list.hashCode())) * 31;
        String str5 = this.name;
        int iHashCode8 = (iHashCode7 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.way;
        int iHashCode9 = (iHashCode8 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.description;
        int iHashCode10 = (((iHashCode9 + (str7 == null ? 0 : str7.hashCode())) * 31) + this.parentId) * 31;
        boolean z2 = this.selected;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        int i3 = (iHashCode10 + i2) * 31;
        String str8 = this.picUrl;
        int iHashCode11 = (i3 + (str8 == null ? 0 : str8.hashCode())) * 31;
        Integer num3 = this.categoryId;
        int iHashCode12 = (((((iHashCode11 + (num3 == null ? 0 : num3.hashCode())) * 31) + this.childNode) * 31) + this.isCategory) * 31;
        String str9 = this.ancestors;
        int iHashCode13 = (iHashCode12 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.itemIndex;
        int iHashCode14 = (((iHashCode13 + (str10 != null ? str10.hashCode() : 0)) * 31) + this.itemState) * 31;
        boolean z3 = this.itemSelected;
        int i4 = z3;
        if (z3 != 0) {
            i4 = 1;
        }
        int i5 = (((((iHashCode14 + i4) * 31) + this.itemPosition) * 31) + this.itemSelectNum) * 31;
        boolean z4 = this.itemIsTitle;
        return i5 + (z4 ? 1 : z4 ? 1 : 0);
    }

    public final int isCategory() {
        return this.isCategory;
    }

    public final void setChildren(@Nullable List<InquiryCheckInfo> list) {
        this.children = list;
    }

    public final void setItemIndex(@Nullable String str) {
        this.itemIndex = str;
    }

    public final void setItemIsTitle(boolean z2) {
        this.itemIsTitle = z2;
    }

    public final void setItemPosition(int i2) {
        this.itemPosition = i2;
    }

    public final void setItemSelectNum(int i2) {
        this.itemSelectNum = i2;
    }

    public final void setItemSelected(boolean z2) {
        this.itemSelected = z2;
    }

    public final void setItemState(int i2) {
        this.itemState = i2;
    }

    public final void setLevel(int i2) {
        this.level = i2;
    }

    @NotNull
    public String toString() {
        return "InquiryCheckInfo(id=" + this.id + ", label=" + this.label + ", title=" + this.title + ", course=" + this.course + ", level=" + this.level + ", pid=" + this.pid + ", result=" + this.result + ", children=" + this.children + ", name=" + this.name + ", way=" + this.way + ", description=" + this.description + ", parentId=" + this.parentId + ", selected=" + this.selected + ", picUrl=" + this.picUrl + ", categoryId=" + this.categoryId + ", childNode=" + this.childNode + ", isCategory=" + this.isCategory + ", ancestors=" + this.ancestors + ", itemIndex=" + this.itemIndex + ", itemState=" + this.itemState + ", itemSelected=" + this.itemSelected + ", itemPosition=" + this.itemPosition + ", itemSelectNum=" + this.itemSelectNum + ", itemIsTitle=" + this.itemIsTitle + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryCheckInfo(int i2, Integer num, String str, String str2, String str3, int i3, Integer num2, String str4, List list, String str5, String str6, String str7, int i4, boolean z2, String str8, Integer num3, int i5, int i6, String str9, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryCheckInfo$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.id = 0;
        } else {
            this.id = num;
        }
        if ((i2 & 2) == 0) {
            this.label = "";
        } else {
            this.label = str;
        }
        if ((i2 & 4) == 0) {
            this.title = "";
        } else {
            this.title = str2;
        }
        if ((i2 & 8) == 0) {
            this.course = "";
        } else {
            this.course = str3;
        }
        if ((i2 & 16) == 0) {
            this.level = 0;
        } else {
            this.level = i3;
        }
        if ((i2 & 32) == 0) {
            this.pid = 0;
        } else {
            this.pid = num2;
        }
        if ((i2 & 64) == 0) {
            this.result = "";
        } else {
            this.result = str4;
        }
        this.children = (i2 & 128) == 0 ? new ArrayList() : list;
        if ((i2 & 256) == 0) {
            this.name = "";
        } else {
            this.name = str5;
        }
        if ((i2 & 512) == 0) {
            this.way = "";
        } else {
            this.way = str6;
        }
        if ((i2 & 1024) == 0) {
            this.description = "";
        } else {
            this.description = str7;
        }
        if ((i2 & 2048) == 0) {
            this.parentId = -1;
        } else {
            this.parentId = i4;
        }
        if ((i2 & 4096) == 0) {
            this.selected = false;
        } else {
            this.selected = z2;
        }
        if ((i2 & 8192) == 0) {
            this.picUrl = "";
        } else {
            this.picUrl = str8;
        }
        this.categoryId = (i2 & 16384) == 0 ? -1 : num3;
        if ((32768 & i2) == 0) {
            this.childNode = -1;
        } else {
            this.childNode = i5;
        }
        this.isCategory = (65536 & i2) == 0 ? 1 : i6;
        if ((i2 & 131072) == 0) {
            this.ancestors = "";
        } else {
            this.ancestors = str9;
        }
        this.itemIndex = "";
        this.itemState = 0;
        this.itemSelected = this.selected;
        this.itemPosition = -1;
        this.itemSelectNum = 0;
        this.itemIsTitle = false;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ InquiryCheckInfo(Integer num, String str, String str2, String str3, int i2, Integer num2, String str4, List list, String str5, String str6, String str7, int i3, boolean z2, String str8, Integer num3, int i4, int i5, String str9, String str10, int i6, boolean z3, int i7, int i8, boolean z4, int i9, DefaultConstructorMarker defaultConstructorMarker) {
        String str11;
        int i10;
        Integer num4;
        Integer num5 = (i9 & 1) != 0 ? num : num;
        String str12 = (i9 & 2) != 0 ? "" : str;
        String str13 = (i9 & 4) != 0 ? "" : str2;
        String str14 = (i9 & 8) != 0 ? "" : str3;
        int i11 = (i9 & 16) != 0 ? 0 : i2;
        num = (i9 & 32) == 0 ? num2 : 0;
        String str15 = (i9 & 64) != 0 ? "" : str4;
        List arrayList = (i9 & 128) != 0 ? new ArrayList() : list;
        String str16 = (i9 & 256) != 0 ? "" : str5;
        String str17 = (i9 & 512) != 0 ? "" : str6;
        String str18 = (i9 & 1024) != 0 ? "" : str7;
        int i12 = (i9 & 2048) != 0 ? -1 : i3;
        boolean z5 = (i9 & 4096) != 0 ? false : z2;
        String str19 = (i9 & 8192) != 0 ? "" : str8;
        str11 = "";
        if ((i9 & 16384) != 0) {
            i10 = -1;
            num4 = -1;
        } else {
            i10 = -1;
            num4 = num3;
        }
        this(num5, str12, str13, str14, i11, num, str15, arrayList, str16, str17, str18, i12, z5, str19, num4, (i9 & 32768) != 0 ? i10 : i4, (i9 & 65536) != 0 ? 1 : i5, (i9 & 131072) != 0 ? str11 : str9, (i9 & 262144) == 0 ? str10 : "", (i9 & 524288) != 0 ? 0 : i6, (i9 & 1048576) != 0 ? z5 : z3, (i9 & 2097152) == 0 ? i7 : i10, (i9 & 4194304) != 0 ? 0 : i8, (i9 & 8388608) != 0 ? false : z4);
    }
}
