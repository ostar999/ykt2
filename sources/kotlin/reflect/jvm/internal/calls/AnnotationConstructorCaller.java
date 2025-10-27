package kotlin.reflect.jvm.internal.calls;

import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.common.net.HttpHeaders;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0000\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\u0002 !B?\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006¢\u0006\u0002\u0010\u000eJ\u001b\u0010\u001c\u001a\u0004\u0018\u00010\u00102\n\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u001eH\u0016¢\u0006\u0002\u0010\u001fR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006\""}, d2 = {"Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "", "jClass", "Ljava/lang/Class;", "parameterNames", "", "", "callMode", "Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$CallMode;", "origin", "Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$Origin;", "methods", "Ljava/lang/reflect/Method;", "(Ljava/lang/Class;Ljava/util/List;Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$CallMode;Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$Origin;Ljava/util/List;)V", "defaultValues", "", "erasedParameterTypes", "member", "getMember", "()Ljava/lang/Void;", "parameterTypes", "Ljava/lang/reflect/Type;", "getParameterTypes", "()Ljava/util/List;", "returnType", "getReturnType", "()Ljava/lang/reflect/Type;", "call", AliyunLogKey.KEY_ARGS, "", "([Ljava/lang/Object;)Ljava/lang/Object;", "CallMode", HttpHeaders.ORIGIN, "kotlin-reflection"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAnnotationConstructorCaller.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AnnotationConstructorCaller.kt\nkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,181:1\n1549#2:182\n1620#2,3:183\n1549#2:186\n1620#2,2:187\n1622#2:190\n1549#2:191\n1620#2,3:192\n1549#2:195\n1620#2,3:196\n1#3:189\n11425#4:199\n11536#4,4:200\n*S KotlinDebug\n*F\n+ 1 AnnotationConstructorCaller.kt\nkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller\n*L\n28#1:182\n28#1:183,3\n35#1:186\n35#1:187,2\n35#1:190\n37#1:191\n37#1:192,3\n20#1:195\n20#1:196,3\n53#1:199\n53#1:200,4\n*E\n"})
/* loaded from: classes8.dex */
public final class AnnotationConstructorCaller implements Caller {

    @NotNull
    private final CallMode callMode;

    @NotNull
    private final List<Object> defaultValues;

    @NotNull
    private final List<Class<?>> erasedParameterTypes;

    @NotNull
    private final Class<?> jClass;

    @NotNull
    private final List<Method> methods;

    @NotNull
    private final List<String> parameterNames;

    @NotNull
    private final List<Type> parameterTypes;

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$CallMode;", "", "(Ljava/lang/String;I)V", "CALL_BY_NAME", "POSITIONAL_CALL", "kotlin-reflection"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum CallMode {
        CALL_BY_NAME,
        POSITIONAL_CALL
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$Origin;", "", "(Ljava/lang/String;I)V", "JAVA", "KOTLIN", "kotlin-reflection"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum Origin {
        JAVA,
        KOTLIN
    }

    public AnnotationConstructorCaller(@NotNull Class<?> jClass, @NotNull List<String> parameterNames, @NotNull CallMode callMode, @NotNull Origin origin, @NotNull List<Method> methods) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        Intrinsics.checkNotNullParameter(parameterNames, "parameterNames");
        Intrinsics.checkNotNullParameter(callMode, "callMode");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(methods, "methods");
        this.jClass = jClass;
        this.parameterNames = parameterNames;
        this.callMode = callMode;
        this.methods = methods;
        List<Method> list = methods;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Method) it.next()).getGenericReturnType());
        }
        this.parameterTypes = arrayList;
        List<Method> list2 = this.methods;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            Class<?> it3 = ((Method) it2.next()).getReturnType();
            Intrinsics.checkNotNullExpressionValue(it3, "it");
            Class<?> wrapperByPrimitive = ReflectClassUtilKt.getWrapperByPrimitive(it3);
            if (wrapperByPrimitive != null) {
                it3 = wrapperByPrimitive;
            }
            arrayList2.add(it3);
        }
        this.erasedParameterTypes = arrayList2;
        List<Method> list3 = this.methods;
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        Iterator<T> it4 = list3.iterator();
        while (it4.hasNext()) {
            arrayList3.add(((Method) it4.next()).getDefaultValue());
        }
        this.defaultValues = arrayList3;
        if (this.callMode == CallMode.POSITIONAL_CALL && origin == Origin.JAVA && (!CollectionsKt___CollectionsKt.minus(this.parameterNames, "value").isEmpty())) {
            throw new UnsupportedOperationException("Positional call of a Java annotation constructor is allowed only if there are no parameters or one parameter named \"value\". This restriction exists because Java annotations (in contrast to Kotlin)do not impose any order on their arguments. Use KCallable#callBy instead.");
        }
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    @Nullable
    public Object call(@NotNull Object[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        checkArguments(args);
        ArrayList arrayList = new ArrayList(args.length);
        int length = args.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            Object obj = args[i2];
            int i4 = i3 + 1;
            Object objTransformKotlinToJvm = (obj == null && this.callMode == CallMode.CALL_BY_NAME) ? this.defaultValues.get(i3) : AnnotationConstructorCallerKt.transformKotlinToJvm(obj, this.erasedParameterTypes.get(i3));
            if (objTransformKotlinToJvm == null) {
                AnnotationConstructorCallerKt.throwIllegalArgumentType(i3, this.parameterNames.get(i3), this.erasedParameterTypes.get(i3));
                throw null;
            }
            arrayList.add(objTransformKotlinToJvm);
            i2++;
            i3 = i4;
        }
        return AnnotationConstructorCallerKt.createAnnotationInstance(this.jClass, MapsKt__MapsKt.toMap(CollectionsKt___CollectionsKt.zip(this.parameterNames, arrayList)), this.methods);
    }

    public void checkArguments(@NotNull Object[] objArr) {
        Caller.DefaultImpls.checkArguments(this, objArr);
    }

    @Nullable
    public Void getMember() {
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    /* renamed from: getMember, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ Member mo2078getMember() {
        return (Member) getMember();
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    @NotNull
    public List<Type> getParameterTypes() {
        return this.parameterTypes;
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    @NotNull
    public Type getReturnType() {
        return this.jClass;
    }

    public /* synthetic */ AnnotationConstructorCaller(Class cls, List list, CallMode callMode, Origin origin, List list2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        List list3;
        if ((i2 & 16) != 0) {
            List list4 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
            Iterator it = list4.iterator();
            while (it.hasNext()) {
                arrayList.add(cls.getDeclaredMethod((String) it.next(), new Class[0]));
            }
            list3 = arrayList;
        } else {
            list3 = list2;
        }
        this(cls, list, callMode, origin, list3);
    }
}
