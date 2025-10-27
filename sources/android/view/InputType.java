package android.view;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.mobile.auth.gatewayauth.Constant;
import com.psychiatrygarden.utils.CommonParameter;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import splitties.experimental.ExperimentalSplittiesApi;
import splitties.views.InputType.Class;

@ExperimentalSplittiesApi
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0087@\u0018\u0000 \u0016*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0002\u0015\u0016B\u0014\b\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0003HÖ\u0003¢\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u0005HÖ\u0001¢\u0006\u0004\b\u0010\u0010\u0007J\u0010\u0010\u0011\u001a\u00020\u0012HÖ\u0001¢\u0006\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u0088\u0001\u0004ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0017"}, d2 = {"Lsplitties/views/InputType;", ExifInterface.GPS_DIRECTION_TRUE, "Lsplitties/views/InputType$Class;", "", "value", "", "constructor-impl", "(I)I", "getValue", "()I", "equals", "", "other", "equals-impl", "(ILjava/lang/Object;)Z", "hashCode", "hashCode-impl", "toString", "", "toString-impl", "(I)Ljava/lang/String;", "Class", "Companion", "splitties-views_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
@JvmInline
/* loaded from: classes9.dex */
public final class InputType<T extends Class> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int value;

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lsplitties/views/InputType$Class;", "", "()V", ExifInterface.TAG_DATETIME, "Number", "Phone", "Text", "Lsplitties/views/InputType$Class$DateTime;", "Lsplitties/views/InputType$Class$Number;", "Lsplitties/views/InputType$Class$Phone;", "Lsplitties/views/InputType$Class$Text;", "splitties-views_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static abstract class Class {

        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lsplitties/views/InputType$Class$DateTime;", "Lsplitties/views/InputType$Class;", "()V", "splitties-views_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
        public static final class DateTime extends Class {

            @NotNull
            public static final DateTime INSTANCE = new DateTime();

            private DateTime() {
                super(null);
            }
        }

        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lsplitties/views/InputType$Class$Number;", "Lsplitties/views/InputType$Class;", "()V", "splitties-views_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
        public static final class Number extends Class {

            @NotNull
            public static final Number INSTANCE = new Number();

            private Number() {
                super(null);
            }
        }

        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lsplitties/views/InputType$Class$Phone;", "Lsplitties/views/InputType$Class;", "()V", "splitties-views_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
        public static final class Phone extends Class {

            @NotNull
            public static final Phone INSTANCE = new Phone();

            private Phone() {
                super(null);
            }
        }

        @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00032\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lsplitties/views/InputType$Class$Text;", "Lsplitties/views/InputType$Class;", "()V", "Companion", "FinalFlag", "Lsplitties/views/InputType$Class$Text$Companion;", "Lsplitties/views/InputType$Class$Text$FinalFlag;", "splitties-views_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
        public static abstract class Text extends Class {

            @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lsplitties/views/InputType$Class$Text$FinalFlag;", "Lsplitties/views/InputType$Class$Text;", "()V", "splitties-views_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
            public static final class FinalFlag extends Text {

                @NotNull
                public static final FinalFlag INSTANCE = new FinalFlag();

                private FinalFlag() {
                    super(null);
                }
            }

            private Text() {
                super(null);
            }

            public /* synthetic */ Text(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        private Class() {
        }

        public /* synthetic */ Class(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0016\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\f\u0010\u0007R!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0007R!\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0007R!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0007R!\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0007R!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00140\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0007R!\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0007R!\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0007R!\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0007R!\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b \u0010\u0007R!\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b\"\u0010\u0007R!\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b$\u0010\u0007R!\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b&\u0010\u0007R!\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b(\u0010\u0007R!\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b*\u0010\u0007R!\u0010+\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b,\u0010\u0007R!\u0010-\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b.\u0010\u0007R!\u0010/\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b0\u0010\u0007R!\u00101\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048Æ\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0006\u001a\u0004\b2\u0010\u0007\u0082\u0002\u000f\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u00063"}, d2 = {"Lsplitties/views/InputType$Companion;", "", "()V", "date", "Lsplitties/views/InputType;", "Lsplitties/views/InputType$Class$DateTime;", "getDate-wyFExG4", "()I", "dateTime", "getDateTime-wyFExG4", "emailAddress", "Lsplitties/views/InputType$Class$Text$Companion;", "getEmailAddress-wyFExG4", "emailSubject", "getEmailSubject-wyFExG4", "filter", "getFilter-wyFExG4", "longMessage", "getLongMessage-wyFExG4", Constant.LOGIN_ACTIVITY_NUMBER, "Lsplitties/views/InputType$Class$Number;", "getNumber-wyFExG4", "numberPassword", "getNumberPassword-wyFExG4", CommonParameter.password, "getPassword-wyFExG4", "personName", "getPersonName-wyFExG4", AliyunLogCommon.TERMINAL_TYPE, "Lsplitties/views/InputType$Class$Phone;", "getPhone-wyFExG4", "phonetic", "getPhonetic-wyFExG4", "postalAddress", "getPostalAddress-wyFExG4", "shortMessage", "getShortMessage-wyFExG4", "text", "getText-wyFExG4", CrashHianalyticsData.TIME, "getTime-wyFExG4", "uri", "getUri-wyFExG4", "visiblePassword", "getVisiblePassword-wyFExG4", "webEditText", "getWebEditText-wyFExG4", "webEmailAddress", "getWebEmailAddress-wyFExG4", "webPassword", "getWebPassword-wyFExG4", "splitties-views_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getDate-wyFExG4, reason: not valid java name */
        public final int m2593getDatewyFExG4() {
            return InputType.m2587constructorimpl(20);
        }

        /* renamed from: getDateTime-wyFExG4, reason: not valid java name */
        public final int m2594getDateTimewyFExG4() {
            return InputType.m2587constructorimpl(4);
        }

        /* renamed from: getEmailAddress-wyFExG4, reason: not valid java name */
        public final int m2595getEmailAddresswyFExG4() {
            return InputType.m2587constructorimpl(33);
        }

        /* renamed from: getEmailSubject-wyFExG4, reason: not valid java name */
        public final int m2596getEmailSubjectwyFExG4() {
            return InputType.m2587constructorimpl(49);
        }

        /* renamed from: getFilter-wyFExG4, reason: not valid java name */
        public final int m2597getFilterwyFExG4() {
            return InputType.m2587constructorimpl(177);
        }

        /* renamed from: getLongMessage-wyFExG4, reason: not valid java name */
        public final int m2598getLongMessagewyFExG4() {
            return InputType.m2587constructorimpl(81);
        }

        /* renamed from: getNumber-wyFExG4, reason: not valid java name */
        public final int m2599getNumberwyFExG4() {
            return InputType.m2587constructorimpl(2);
        }

        /* renamed from: getNumberPassword-wyFExG4, reason: not valid java name */
        public final int m2600getNumberPasswordwyFExG4() {
            return InputType.m2587constructorimpl(18);
        }

        /* renamed from: getPassword-wyFExG4, reason: not valid java name */
        public final int m2601getPasswordwyFExG4() {
            return InputType.m2587constructorimpl(129);
        }

        /* renamed from: getPersonName-wyFExG4, reason: not valid java name */
        public final int m2602getPersonNamewyFExG4() {
            return InputType.m2587constructorimpl(97);
        }

        /* renamed from: getPhone-wyFExG4, reason: not valid java name */
        public final int m2603getPhonewyFExG4() {
            return InputType.m2587constructorimpl(3);
        }

        /* renamed from: getPhonetic-wyFExG4, reason: not valid java name */
        public final int m2604getPhoneticwyFExG4() {
            return InputType.m2587constructorimpl(193);
        }

        /* renamed from: getPostalAddress-wyFExG4, reason: not valid java name */
        public final int m2605getPostalAddresswyFExG4() {
            return InputType.m2587constructorimpl(113);
        }

        /* renamed from: getShortMessage-wyFExG4, reason: not valid java name */
        public final int m2606getShortMessagewyFExG4() {
            return InputType.m2587constructorimpl(65);
        }

        /* renamed from: getText-wyFExG4, reason: not valid java name */
        public final int m2607getTextwyFExG4() {
            return InputType.m2587constructorimpl(1);
        }

        /* renamed from: getTime-wyFExG4, reason: not valid java name */
        public final int m2608getTimewyFExG4() {
            return InputType.m2587constructorimpl(36);
        }

        /* renamed from: getUri-wyFExG4, reason: not valid java name */
        public final int m2609getUriwyFExG4() {
            return InputType.m2587constructorimpl(17);
        }

        /* renamed from: getVisiblePassword-wyFExG4, reason: not valid java name */
        public final int m2610getVisiblePasswordwyFExG4() {
            return InputType.m2587constructorimpl(145);
        }

        /* renamed from: getWebEditText-wyFExG4, reason: not valid java name */
        public final int m2611getWebEditTextwyFExG4() {
            return InputType.m2587constructorimpl(161);
        }

        /* renamed from: getWebEmailAddress-wyFExG4, reason: not valid java name */
        public final int m2612getWebEmailAddresswyFExG4() {
            return InputType.m2587constructorimpl(209);
        }

        /* renamed from: getWebPassword-wyFExG4, reason: not valid java name */
        public final int m2613getWebPasswordwyFExG4() {
            return InputType.m2587constructorimpl(225);
        }
    }

    @PublishedApi
    private /* synthetic */ InputType(int i2) {
        this.value = i2;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ InputType m2586boximpl(int i2) {
        return new InputType(i2);
    }

    @PublishedApi
    /* renamed from: constructor-impl, reason: not valid java name */
    public static <T extends Class> int m2587constructorimpl(int i2) {
        return i2;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2588equalsimpl(int i2, Object obj) {
        return (obj instanceof InputType) && i2 == ((InputType) obj).getValue();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2589equalsimpl0(int i2, int i3) {
        return i2 == i3;
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2590hashCodeimpl(int i2) {
        return i2;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2591toStringimpl(int i2) {
        return "InputType(value=" + i2 + ')';
    }

    public boolean equals(Object obj) {
        return m2588equalsimpl(getValue(), obj);
    }

    public final int getValue() {
        return getValue();
    }

    public int hashCode() {
        return m2590hashCodeimpl(getValue());
    }

    public String toString() {
        return m2591toStringimpl(getValue());
    }

    /* renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ int getValue() {
        return this.value;
    }
}
