package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

/* loaded from: classes9.dex */
public interface CallAdapter<R, T> {

    public static abstract class Factory {
        public static Type getParameterUpperBound(int i2, ParameterizedType parameterizedType) {
            return Utils.getParameterUpperBound(i2, parameterizedType);
        }

        public static Class<?> getRawType(Type type) {
            return Utils.getRawType(type);
        }

        @Nullable
        public abstract CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit);
    }

    T adapt(Call<R> call);

    Type responseType();
}
