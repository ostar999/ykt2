package retrofit2.adapter.rxjava3;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

/* loaded from: classes9.dex */
public final class RxJava3CallAdapterFactory extends CallAdapter.Factory {
    private final boolean isAsync;

    @Nullable
    private final Scheduler scheduler;

    private RxJava3CallAdapterFactory(@Nullable Scheduler scheduler, boolean z2) {
        this.scheduler = scheduler;
        this.isAsync = z2;
    }

    public static RxJava3CallAdapterFactory create() {
        return new RxJava3CallAdapterFactory(null, true);
    }

    public static RxJava3CallAdapterFactory createSynchronous() {
        return new RxJava3CallAdapterFactory(null, false);
    }

    public static RxJava3CallAdapterFactory createWithScheduler(Scheduler scheduler) {
        if (scheduler != null) {
            return new RxJava3CallAdapterFactory(scheduler, false);
        }
        throw new NullPointerException("scheduler == null");
    }

    @Override // retrofit2.CallAdapter.Factory
    @Nullable
    public CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        Type parameterUpperBound;
        boolean z2;
        boolean z3;
        Class<?> rawType = CallAdapter.Factory.getRawType(type);
        if (rawType == Completable.class) {
            return new RxJava3CallAdapter(Void.class, this.scheduler, this.isAsync, false, true, false, false, false, true);
        }
        boolean z4 = rawType == Flowable.class;
        boolean z5 = rawType == Single.class;
        boolean z6 = rawType == Maybe.class;
        if (rawType != Observable.class && !z4 && !z5 && !z6) {
            return null;
        }
        if (!(type instanceof ParameterizedType)) {
            String str = !z4 ? !z5 ? z6 ? "Maybe" : "Observable" : "Single" : "Flowable";
            throw new IllegalStateException(str + " return type must be parameterized as " + str + "<Foo> or " + str + "<? extends Foo>");
        }
        Type parameterUpperBound2 = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) type);
        Class<?> rawType2 = CallAdapter.Factory.getRawType(parameterUpperBound2);
        if (rawType2 == Response.class) {
            if (!(parameterUpperBound2 instanceof ParameterizedType)) {
                throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
            }
            parameterUpperBound = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) parameterUpperBound2);
            z3 = false;
            z2 = false;
        } else if (rawType2 != Result.class) {
            parameterUpperBound = parameterUpperBound2;
            z2 = true;
            z3 = false;
        } else {
            if (!(parameterUpperBound2 instanceof ParameterizedType)) {
                throw new IllegalStateException("Result must be parameterized as Result<Foo> or Result<? extends Foo>");
            }
            parameterUpperBound = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) parameterUpperBound2);
            z3 = true;
            z2 = false;
        }
        return new RxJava3CallAdapter(parameterUpperBound, this.scheduler, this.isAsync, z3, z2, z4, z5, z6, false);
    }
}
