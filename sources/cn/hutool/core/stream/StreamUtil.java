package cn.hutool.core.stream;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.CharsetUtil;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* loaded from: classes.dex */
public class StreamUtil {
    public static <T> String join(Stream<T> stream, CharSequence charSequence) {
        return (String) stream.collect(CollectorUtil.joining(charSequence));
    }

    @SafeVarargs
    public static <T> Stream<T> of(T... tArr) throws IllegalArgumentException {
        Assert.notNull(tArr, "Array must be not null!", new Object[0]);
        return Stream.of((Object[]) tArr);
    }

    public static <T> String join(Stream<T> stream, CharSequence charSequence, Function<T, ? extends CharSequence> function) {
        return (String) stream.collect(CollectorUtil.joining(charSequence, function));
    }

    public static <T> Stream<T> of(Iterable<T> iterable) {
        return of((Iterable) iterable, false);
    }

    public static <T> Stream<T> of(Iterable<T> iterable, boolean z2) throws IllegalArgumentException {
        Assert.notNull(iterable, "Iterable must be not null!", new Object[0]);
        if (iterable instanceof Collection) {
            return z2 ? ((Collection) iterable).parallelStream() : ((Collection) iterable).stream();
        }
        return StreamSupport.stream(iterable.spliterator(), z2);
    }

    public static <T> Stream<T> of(Iterator<T> it) {
        return of((Iterator) it, false);
    }

    public static <T> Stream<T> of(Iterator<T> it, boolean z2) throws IllegalArgumentException {
        Assert.notNull(it, "iterator must not be null!", new Object[0]);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, 0), z2);
    }

    public static Stream<String> of(File file) {
        return of(file, CharsetUtil.CHARSET_UTF_8);
    }

    public static Stream<String> of(Path path) {
        return of(path, CharsetUtil.CHARSET_UTF_8);
    }

    public static Stream<String> of(File file, Charset charset) throws IllegalArgumentException {
        Assert.notNull(file, "File must be not null!", new Object[0]);
        return of(file.toPath(), charset);
    }

    public static Stream<String> of(Path path, Charset charset) {
        try {
            return Files.lines(path, charset);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static <T> Stream<T> of(T t2, UnaryOperator<T> unaryOperator, int i2) {
        return Stream.iterate(t2, unaryOperator).limit(i2);
    }
}
