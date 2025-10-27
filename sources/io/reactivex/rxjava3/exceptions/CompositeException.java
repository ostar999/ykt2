package io.reactivex.rxjava3.exceptions;

import cn.hutool.core.text.StrPool;
import io.reactivex.rxjava3.annotations.NonNull;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;

/* loaded from: classes8.dex */
public final class CompositeException extends RuntimeException {
    private static final long serialVersionUID = 3026362227162912146L;
    private Throwable cause;
    private final List<Throwable> exceptions;
    private final String message;

    public static final class ExceptionOverview extends RuntimeException {
        private static final long serialVersionUID = 3875212506787802066L;

        public ExceptionOverview(String message) {
            super(message);
        }

        @Override // java.lang.Throwable
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    public static abstract class PrintStreamOrWriter {
        public abstract PrintStreamOrWriter append(Object o2);
    }

    public static final class WrappedPrintStream extends PrintStreamOrWriter {
        private final PrintStream printStream;

        public WrappedPrintStream(PrintStream printStream) {
            this.printStream = printStream;
        }

        @Override // io.reactivex.rxjava3.exceptions.CompositeException.PrintStreamOrWriter
        public WrappedPrintStream append(Object o2) {
            this.printStream.print(o2);
            return this;
        }
    }

    public static final class WrappedPrintWriter extends PrintStreamOrWriter {
        private final PrintWriter printWriter;

        public WrappedPrintWriter(PrintWriter printWriter) {
            this.printWriter = printWriter;
        }

        @Override // io.reactivex.rxjava3.exceptions.CompositeException.PrintStreamOrWriter
        public WrappedPrintWriter append(Object o2) {
            this.printWriter.print(o2);
            return this;
        }
    }

    public CompositeException(@NonNull Throwable... exceptions) {
        this(exceptions == null ? Collections.singletonList(new NullPointerException("exceptions was null")) : Arrays.asList(exceptions));
    }

    private void appendStackTrace(PrintStreamOrWriter output, Throwable ex, String prefix) {
        output.append(prefix).append(ex).append('\n');
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            output.append("\t\tat ").append(stackTraceElement).append('\n');
        }
        if (ex.getCause() != null) {
            output.append("\tCaused by: ");
            appendStackTrace(output, ex.getCause(), "");
        }
    }

    @Override // java.lang.Throwable
    @NonNull
    public synchronized Throwable getCause() {
        int i2;
        if (this.cause == null) {
            String property = System.getProperty("line.separator");
            if (this.exceptions.size() > 1) {
                IdentityHashMap identityHashMap = new IdentityHashMap();
                StringBuilder sb = new StringBuilder();
                sb.append("Multiple exceptions (");
                sb.append(this.exceptions.size());
                sb.append(")");
                sb.append(property);
                for (Throwable cause : this.exceptions) {
                    int i3 = 0;
                    while (true) {
                        if (cause != null) {
                            for (int i4 = 0; i4 < i3; i4++) {
                                sb.append("  ");
                            }
                            sb.append("|-- ");
                            sb.append(cause.getClass().getCanonicalName());
                            sb.append(": ");
                            String message = cause.getMessage();
                            if (message == null || !message.contains(property)) {
                                sb.append(message);
                                sb.append(property);
                            } else {
                                sb.append(property);
                                for (String str : message.split(property)) {
                                    for (int i5 = 0; i5 < i3 + 2; i5++) {
                                        sb.append("  ");
                                    }
                                    sb.append(str);
                                    sb.append(property);
                                }
                            }
                            int i6 = 0;
                            while (true) {
                                i2 = i3 + 2;
                                if (i6 >= i2) {
                                    break;
                                }
                                sb.append("  ");
                                i6++;
                            }
                            StackTraceElement[] stackTrace = cause.getStackTrace();
                            if (stackTrace.length > 0) {
                                sb.append("at ");
                                sb.append(stackTrace[0]);
                                sb.append(property);
                            }
                            if (identityHashMap.containsKey(cause)) {
                                Throwable cause2 = cause.getCause();
                                if (cause2 != null) {
                                    for (int i7 = 0; i7 < i2; i7++) {
                                        sb.append("  ");
                                    }
                                    sb.append("|-- ");
                                    sb.append("(cause not expanded again) ");
                                    sb.append(cause2.getClass().getCanonicalName());
                                    sb.append(": ");
                                    sb.append(cause2.getMessage());
                                    sb.append(property);
                                }
                            } else {
                                identityHashMap.put(cause, Boolean.TRUE);
                                cause = cause.getCause();
                                i3++;
                            }
                        }
                    }
                }
                this.cause = new ExceptionOverview(sb.toString().trim());
            } else {
                this.cause = this.exceptions.get(0);
            }
        }
        return this.cause;
    }

    @NonNull
    public List<Throwable> getExceptions() {
        return this.exceptions;
    }

    @Override // java.lang.Throwable
    @NonNull
    public String getMessage() {
        return this.message;
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public int size() {
        return this.exceptions.size();
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream s2) {
        printStackTrace(new WrappedPrintStream(s2));
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter s2) {
        printStackTrace(new WrappedPrintWriter(s2));
    }

    public CompositeException(@NonNull Iterable<? extends Throwable> errors) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (errors != null) {
            for (Throwable th : errors) {
                if (th instanceof CompositeException) {
                    linkedHashSet.addAll(((CompositeException) th).getExceptions());
                } else if (th != null) {
                    linkedHashSet.add(th);
                } else {
                    linkedHashSet.add(new NullPointerException("Throwable was null!"));
                }
            }
        } else {
            linkedHashSet.add(new NullPointerException("errors was null"));
        }
        if (!linkedHashSet.isEmpty()) {
            List<Throwable> listUnmodifiableList = Collections.unmodifiableList(new ArrayList(linkedHashSet));
            this.exceptions = listUnmodifiableList;
            this.message = listUnmodifiableList.size() + " exceptions occurred. ";
            return;
        }
        throw new IllegalArgumentException("errors is empty");
    }

    private void printStackTrace(PrintStreamOrWriter output) {
        output.append(this).append("\n");
        for (StackTraceElement stackTraceElement : getStackTrace()) {
            output.append("\tat ").append(stackTraceElement).append("\n");
        }
        int i2 = 1;
        for (Throwable th : this.exceptions) {
            output.append("  ComposedException ").append(Integer.valueOf(i2)).append(" :\n");
            appendStackTrace(output, th, StrPool.TAB);
            i2++;
        }
        output.append("\n");
    }
}
