package retrofit2;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/* loaded from: classes9.dex */
abstract class ParameterHandler<T> {

    public static final class Body<T> extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;
        private final Method method;

        /* renamed from: p, reason: collision with root package name */
        private final int f28186p;

        public Body(Method method, int i2, Converter<T, RequestBody> converter) {
            this.method = method;
            this.f28186p = i2;
            this.converter = converter;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable T t2) {
            if (t2 == null) {
                throw Utils.parameterError(this.method, this.f28186p, "Body parameter value must not be null.", new Object[0]);
            }
            try {
                requestBuilder.setBody(this.converter.convert(t2));
            } catch (IOException e2) {
                throw Utils.parameterError(this.method, e2, this.f28186p, "Unable to convert " + t2 + " to RequestBody", new Object[0]);
            }
        }
    }

    public static final class Field<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        public Field(String str, Converter<T, String> converter, boolean z2) {
            Objects.requireNonNull(str, "name == null");
            this.name = str;
            this.valueConverter = converter;
            this.encoded = z2;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable T t2) throws IOException {
            String strConvert;
            if (t2 == null || (strConvert = this.valueConverter.convert(t2)) == null) {
                return;
            }
            requestBuilder.addFormField(this.name, strConvert, this.encoded);
        }
    }

    public static final class FieldMap<T> extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Method method;

        /* renamed from: p, reason: collision with root package name */
        private final int f28187p;
        private final Converter<T, String> valueConverter;

        public FieldMap(Method method, int i2, Converter<T, String> converter, boolean z2) {
            this.method = method;
            this.f28187p = i2;
            this.valueConverter = converter;
            this.encoded = z2;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable Map<String, T> map) throws IOException {
            if (map == null) {
                throw Utils.parameterError(this.method, this.f28187p, "Field map was null.", new Object[0]);
            }
            for (Map.Entry<String, T> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key == null) {
                    throw Utils.parameterError(this.method, this.f28187p, "Field map contained null key.", new Object[0]);
                }
                T value = entry.getValue();
                if (value == null) {
                    throw Utils.parameterError(this.method, this.f28187p, "Field map contained null value for key '" + key + "'.", new Object[0]);
                }
                String strConvert = this.valueConverter.convert(value);
                if (strConvert == null) {
                    throw Utils.parameterError(this.method, this.f28187p, "Field map value '" + value + "' converted to null by " + this.valueConverter.getClass().getName() + " for key '" + key + "'.", new Object[0]);
                }
                requestBuilder.addFormField(key, strConvert, this.encoded);
            }
        }
    }

    public static final class Header<T> extends ParameterHandler<T> {
        private final String name;
        private final Converter<T, String> valueConverter;

        public Header(String str, Converter<T, String> converter) {
            Objects.requireNonNull(str, "name == null");
            this.name = str;
            this.valueConverter = converter;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable T t2) throws IOException {
            String strConvert;
            if (t2 == null || (strConvert = this.valueConverter.convert(t2)) == null) {
                return;
            }
            requestBuilder.addHeader(this.name, strConvert);
        }
    }

    public static final class HeaderMap<T> extends ParameterHandler<Map<String, T>> {
        private final Method method;

        /* renamed from: p, reason: collision with root package name */
        private final int f28188p;
        private final Converter<T, String> valueConverter;

        public HeaderMap(Method method, int i2, Converter<T, String> converter) {
            this.method = method;
            this.f28188p = i2;
            this.valueConverter = converter;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable Map<String, T> map) throws IOException {
            if (map == null) {
                throw Utils.parameterError(this.method, this.f28188p, "Header map was null.", new Object[0]);
            }
            for (Map.Entry<String, T> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key == null) {
                    throw Utils.parameterError(this.method, this.f28188p, "Header map contained null key.", new Object[0]);
                }
                T value = entry.getValue();
                if (value == null) {
                    throw Utils.parameterError(this.method, this.f28188p, "Header map contained null value for key '" + key + "'.", new Object[0]);
                }
                requestBuilder.addHeader(key, this.valueConverter.convert(value));
            }
        }
    }

    public static final class Headers extends ParameterHandler<okhttp3.Headers> {
        private final Method method;

        /* renamed from: p, reason: collision with root package name */
        private final int f28189p;

        public Headers(Method method, int i2) {
            this.method = method;
            this.f28189p = i2;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable okhttp3.Headers headers) {
            if (headers == null) {
                throw Utils.parameterError(this.method, this.f28189p, "Headers parameter must not be null.", new Object[0]);
            }
            requestBuilder.addHeaders(headers);
        }
    }

    public static final class Part<T> extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;
        private final okhttp3.Headers headers;
        private final Method method;

        /* renamed from: p, reason: collision with root package name */
        private final int f28190p;

        public Part(Method method, int i2, okhttp3.Headers headers, Converter<T, RequestBody> converter) {
            this.method = method;
            this.f28190p = i2;
            this.headers = headers;
            this.converter = converter;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable T t2) {
            if (t2 == null) {
                return;
            }
            try {
                requestBuilder.addPart(this.headers, this.converter.convert(t2));
            } catch (IOException e2) {
                throw Utils.parameterError(this.method, this.f28190p, "Unable to convert " + t2 + " to RequestBody", e2);
            }
        }
    }

    public static final class PartMap<T> extends ParameterHandler<Map<String, T>> {
        private final Method method;

        /* renamed from: p, reason: collision with root package name */
        private final int f28191p;
        private final String transferEncoding;
        private final Converter<T, RequestBody> valueConverter;

        public PartMap(Method method, int i2, Converter<T, RequestBody> converter, String str) {
            this.method = method;
            this.f28191p = i2;
            this.valueConverter = converter;
            this.transferEncoding = str;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable Map<String, T> map) throws IOException {
            if (map == null) {
                throw Utils.parameterError(this.method, this.f28191p, "Part map was null.", new Object[0]);
            }
            for (Map.Entry<String, T> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key == null) {
                    throw Utils.parameterError(this.method, this.f28191p, "Part map contained null key.", new Object[0]);
                }
                T value = entry.getValue();
                if (value == null) {
                    throw Utils.parameterError(this.method, this.f28191p, "Part map contained null value for key '" + key + "'.", new Object[0]);
                }
                requestBuilder.addPart(okhttp3.Headers.of("Content-Disposition", "form-data; name=\"" + key + "\"", "Content-Transfer-Encoding", this.transferEncoding), this.valueConverter.convert(value));
            }
        }
    }

    public static final class Path<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final Method method;
        private final String name;

        /* renamed from: p, reason: collision with root package name */
        private final int f28192p;
        private final Converter<T, String> valueConverter;

        public Path(Method method, int i2, String str, Converter<T, String> converter, boolean z2) {
            this.method = method;
            this.f28192p = i2;
            Objects.requireNonNull(str, "name == null");
            this.name = str;
            this.valueConverter = converter;
            this.encoded = z2;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable T t2) throws IOException {
            if (t2 != null) {
                requestBuilder.addPathParam(this.name, this.valueConverter.convert(t2), this.encoded);
                return;
            }
            throw Utils.parameterError(this.method, this.f28192p, "Path parameter \"" + this.name + "\" value must not be null.", new Object[0]);
        }
    }

    public static final class Query<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        public Query(String str, Converter<T, String> converter, boolean z2) {
            Objects.requireNonNull(str, "name == null");
            this.name = str;
            this.valueConverter = converter;
            this.encoded = z2;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable T t2) throws IOException {
            String strConvert;
            if (t2 == null || (strConvert = this.valueConverter.convert(t2)) == null) {
                return;
            }
            requestBuilder.addQueryParam(this.name, strConvert, this.encoded);
        }
    }

    public static final class QueryMap<T> extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Method method;

        /* renamed from: p, reason: collision with root package name */
        private final int f28193p;
        private final Converter<T, String> valueConverter;

        public QueryMap(Method method, int i2, Converter<T, String> converter, boolean z2) {
            this.method = method;
            this.f28193p = i2;
            this.valueConverter = converter;
            this.encoded = z2;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable Map<String, T> map) throws IOException {
            if (map == null) {
                throw Utils.parameterError(this.method, this.f28193p, "Query map was null", new Object[0]);
            }
            for (Map.Entry<String, T> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key == null) {
                    throw Utils.parameterError(this.method, this.f28193p, "Query map contained null key.", new Object[0]);
                }
                T value = entry.getValue();
                if (value == null) {
                    throw Utils.parameterError(this.method, this.f28193p, "Query map contained null value for key '" + key + "'.", new Object[0]);
                }
                String strConvert = this.valueConverter.convert(value);
                if (strConvert == null) {
                    throw Utils.parameterError(this.method, this.f28193p, "Query map value '" + value + "' converted to null by " + this.valueConverter.getClass().getName() + " for key '" + key + "'.", new Object[0]);
                }
                requestBuilder.addQueryParam(key, strConvert, this.encoded);
            }
        }
    }

    public static final class QueryName<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final Converter<T, String> nameConverter;

        public QueryName(Converter<T, String> converter, boolean z2) {
            this.nameConverter = converter;
            this.encoded = z2;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable T t2) throws IOException {
            if (t2 == null) {
                return;
            }
            requestBuilder.addQueryParam(this.nameConverter.convert(t2), null, this.encoded);
        }
    }

    public static final class RawPart extends ParameterHandler<MultipartBody.Part> {
        static final RawPart INSTANCE = new RawPart();

        private RawPart() {
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable MultipartBody.Part part) {
            if (part != null) {
                requestBuilder.addPart(part);
            }
        }
    }

    public static final class RelativeUrl extends ParameterHandler<Object> {
        private final Method method;

        /* renamed from: p, reason: collision with root package name */
        private final int f28194p;

        public RelativeUrl(Method method, int i2) {
            this.method = method;
            this.f28194p = i2;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable Object obj) {
            if (obj == null) {
                throw Utils.parameterError(this.method, this.f28194p, "@Url parameter is null.", new Object[0]);
            }
            requestBuilder.setRelativeUrl(obj);
        }
    }

    public static final class Tag<T> extends ParameterHandler<T> {
        final Class<T> cls;

        public Tag(Class<T> cls) {
            this.cls = cls;
        }

        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, @Nullable T t2) {
            requestBuilder.addTag(this.cls, t2);
        }
    }

    public abstract void apply(RequestBuilder requestBuilder, @Nullable T t2) throws IOException;

    public final ParameterHandler<Object> array() {
        return new ParameterHandler<Object>() { // from class: retrofit2.ParameterHandler.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // retrofit2.ParameterHandler
            public void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
                if (obj == null) {
                    return;
                }
                int length = Array.getLength(obj);
                for (int i2 = 0; i2 < length; i2++) {
                    ParameterHandler.this.apply(requestBuilder, Array.get(obj, i2));
                }
            }
        };
    }

    public final ParameterHandler<Iterable<T>> iterable() {
        return new ParameterHandler<Iterable<T>>() { // from class: retrofit2.ParameterHandler.1
            @Override // retrofit2.ParameterHandler
            public void apply(RequestBuilder requestBuilder, @Nullable Iterable<T> iterable) throws IOException {
                if (iterable == null) {
                    return;
                }
                Iterator<T> it = iterable.iterator();
                while (it.hasNext()) {
                    ParameterHandler.this.apply(requestBuilder, it.next());
                }
            }
        };
    }
}
