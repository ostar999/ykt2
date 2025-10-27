package org.eclipse.jetty.servlet;

import com.tencent.open.SocialConstants;
import java.io.IOException;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;

/* loaded from: classes9.dex */
public class FilterMapping implements Dumpable {
    public static final int ALL = 31;
    public static final int ASYNC = 16;
    public static final int DEFAULT = 0;
    public static final int ERROR = 8;
    public static final int FORWARD = 2;
    public static final int INCLUDE = 4;
    public static final int REQUEST = 1;
    private int _dispatches = 0;
    private String _filterName;
    private transient FilterHolder _holder;
    private String[] _pathSpecs;
    private String[] _servletNames;

    /* renamed from: org.eclipse.jetty.servlet.FilterMapping$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$javax$servlet$DispatcherType;

        static {
            int[] iArr = new int[DispatcherType.values().length];
            $SwitchMap$javax$servlet$DispatcherType = iArr;
            try {
                iArr[DispatcherType.REQUEST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$javax$servlet$DispatcherType[DispatcherType.ASYNC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$javax$servlet$DispatcherType[DispatcherType.FORWARD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$javax$servlet$DispatcherType[DispatcherType.INCLUDE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$javax$servlet$DispatcherType[DispatcherType.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static DispatcherType dispatch(String str) {
        if (SocialConstants.TYPE_REQUEST.equalsIgnoreCase(str)) {
            return DispatcherType.REQUEST;
        }
        if ("forward".equalsIgnoreCase(str)) {
            return DispatcherType.FORWARD;
        }
        if ("include".equalsIgnoreCase(str)) {
            return DispatcherType.INCLUDE;
        }
        if ("error".equalsIgnoreCase(str)) {
            return DispatcherType.ERROR;
        }
        if ("async".equalsIgnoreCase(str)) {
            return DispatcherType.ASYNC;
        }
        throw new IllegalArgumentException(str);
    }

    public boolean appliesTo(String str, int i2) {
        if (appliesTo(i2)) {
            int i3 = 0;
            while (true) {
                String[] strArr = this._pathSpecs;
                if (i3 >= strArr.length) {
                    break;
                }
                String str2 = strArr[i3];
                if (str2 != null && PathMap.match(str2, str, true)) {
                    return true;
                }
                i3++;
            }
        }
        return false;
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        appendable.append(String.valueOf(this)).append("\n");
    }

    public FilterHolder getFilterHolder() {
        return this._holder;
    }

    public String getFilterName() {
        return this._filterName;
    }

    public String[] getPathSpecs() {
        return this._pathSpecs;
    }

    public String[] getServletNames() {
        return this._servletNames;
    }

    public void setDispatcherTypes(EnumSet<DispatcherType> enumSet) {
        this._dispatches = 0;
        if (enumSet != null) {
            if (enumSet.contains(DispatcherType.ERROR)) {
                this._dispatches |= 8;
            }
            if (enumSet.contains(DispatcherType.FORWARD)) {
                this._dispatches |= 2;
            }
            if (enumSet.contains(DispatcherType.INCLUDE)) {
                this._dispatches |= 4;
            }
            if (enumSet.contains(DispatcherType.REQUEST)) {
                this._dispatches |= 1;
            }
            if (enumSet.contains(DispatcherType.ASYNC)) {
                this._dispatches |= 16;
            }
        }
    }

    public void setDispatches(int i2) {
        this._dispatches = i2;
    }

    public void setFilterHolder(FilterHolder filterHolder) {
        this._holder = filterHolder;
        setFilterName(filterHolder.getName());
    }

    public void setFilterName(String str) {
        this._filterName = str;
    }

    public void setPathSpec(String str) {
        this._pathSpecs = new String[]{str};
    }

    public void setPathSpecs(String[] strArr) {
        this._pathSpecs = strArr;
    }

    public void setServletName(String str) {
        this._servletNames = new String[]{str};
    }

    public void setServletNames(String[] strArr) {
        this._servletNames = strArr;
    }

    public String toString() {
        return TypeUtil.asList(this._pathSpecs) + "/" + TypeUtil.asList(this._servletNames) + "==" + this._dispatches + "=>" + this._filterName;
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public String dump() {
        return AggregateLifeCycle.dump(this);
    }

    public boolean appliesTo(int i2) {
        int i3 = this._dispatches;
        return i3 == 0 ? i2 == 1 || (i2 == 16 && this._holder.isAsyncSupported()) : (i2 & i3) != 0;
    }

    public static int dispatch(DispatcherType dispatcherType) {
        int i2 = AnonymousClass1.$SwitchMap$javax$servlet$DispatcherType[dispatcherType.ordinal()];
        if (i2 == 1) {
            return 1;
        }
        int i3 = 2;
        if (i2 == 2) {
            return 16;
        }
        if (i2 != 3) {
            i3 = 4;
            if (i2 != 4) {
                if (i2 == 5) {
                    return 8;
                }
                throw new IllegalArgumentException(dispatcherType.toString());
            }
        }
        return i3;
    }
}
