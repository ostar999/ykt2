package com.yddmi.doctor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.tencent.connect.common.Constants;
import com.yddmi.doctor.R;
import com.yddmi.doctor.widget.NumberKeyBoardView;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringBuilderJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0015\u0018\u00002\u00020\u0001:\u000267B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\"\u0010\"\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0012\u0010$\u001a\u00020#2\b\b\u0002\u0010%\u001a\u00020\u000fH\u0002J\u001a\u0010&\u001a\u00020#2\b\b\u0002\u0010'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020\u000bH\u0002J\u000e\u0010)\u001a\u00020#2\u0006\u0010*\u001a\u00020\u000fJ\u0018\u0010+\u001a\u00020#2\u0006\u0010,\u001a\u00020\u000f2\b\b\u0002\u0010-\u001a\u00020\u000fJ\u0010\u0010.\u001a\u00020#2\u0006\u0010,\u001a\u00020\u000fH\u0002J\u0010\u0010/\u001a\u00020#2\b\u0010(\u001a\u0004\u0018\u00010\u000bJ\u001c\u00100\u001a\u00020#2\b\u0010(\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u0015J\u000e\u00102\u001a\u00020#2\u0006\u00103\u001a\u00020\u0007J\u000e\u00104\u001a\u00020#2\u0006\u00105\u001a\u00020\u0017R\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.¢\u0006\u0002\n\u0000R\u0012\u0010\u001f\u001a\u00060 j\u0002`!X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lcom/yddmi/doctor/widget/NumberKeyBoardView;", "Landroidx/constraintlayout/widget/ConstraintLayout;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "KEYBOARD_NUMBER", "", "", "[Ljava/lang/String;", "KEYBOARD_TEXT", "antoMaxDone", "", "canInputDot", "hasDot", "mAdapter", "Lcom/yddmi/doctor/widget/NumberKeyBoardView$KeyBoardAdapter;", "mEt", "Landroid/widget/EditText;", "mListener", "Lcom/yddmi/doctor/widget/NumberKeyBoardView$OnListener;", "mRecordList", "Ljava/util/LinkedList;", "maxLength", "rooView", "Landroid/view/View;", "rv", "Landroidx/recyclerview/widget/RecyclerView;", "strBuilder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "dealInit", "", "dealInputStr", RequestParameters.SUBRESOURCE_DELETE, "dealStrAddOrDel", "del", "str", "setAutoMaxDone", "auto", "setCanInputDot", "dot", "refreshAdapter", "setHasDot", "setInputStr", "setInputStrBuildEt", "et", "setMaxLength", "max", "setOnListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "KeyBoardAdapter", "OnListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nNumberKeyBoardView.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NumberKeyBoardView.kt\ncom/yddmi/doctor/widget/NumberKeyBoardView\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,243:1\n13635#2,2:244\n*S KotlinDebug\n*F\n+ 1 NumberKeyBoardView.kt\ncom/yddmi/doctor/widget/NumberKeyBoardView\n*L\n161#1:244,2\n*E\n"})
/* loaded from: classes6.dex */
public final class NumberKeyBoardView extends ConstraintLayout {

    @NotNull
    private final String[] KEYBOARD_NUMBER;

    @NotNull
    private final String[] KEYBOARD_TEXT;
    private boolean antoMaxDone;
    private boolean canInputDot;
    private boolean hasDot;
    private KeyBoardAdapter mAdapter;

    @Nullable
    private EditText mEt;

    @Nullable
    private OnListener mListener;

    @NotNull
    private final LinkedList<String> mRecordList;
    private int maxLength;
    private View rooView;
    private RecyclerView rv;

    @NotNull
    private final StringBuilder strBuilder;

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0019\u001aB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\fH\u0016J\u0018\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0016J\u0019\u0010\u0015\u001a\u00020\u000e2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\bR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\n¨\u0006\u001b"}, d2 = {"Lcom/yddmi/doctor/widget/NumberKeyBoardView$KeyBoardAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/yddmi/doctor/widget/NumberKeyBoardView$KeyBoardAdapter$ViewHolder;", "moist", "", "", "([Ljava/lang/String;)V", "adapterListener", "Lcom/yddmi/doctor/widget/NumberKeyBoardView$KeyBoardAdapter$OnItemClickListener;", "list", "[Ljava/lang/String;", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", PLVRxEncryptDataFunction.SET_DATA_METHOD, "newList", "setOnItemClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "OnItemClickListener", "ViewHolder", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class KeyBoardAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Nullable
        private OnItemClickListener adapterListener;

        @NotNull
        private String[] list;

        @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lcom/yddmi/doctor/widget/NumberKeyBoardView$KeyBoardAdapter$OnItemClickListener;", "", "onItemClick", "", "position", "", "str", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public interface OnItemClickListener {
            void onItemClick(int position, @NotNull String str);
        }

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/yddmi/doctor/widget/NumberKeyBoardView$KeyBoardAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "tv", "Landroid/widget/TextView;", "getTv", "()Landroid/widget/TextView;", "setTv", "(Landroid/widget/TextView;)V", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final class ViewHolder extends RecyclerView.ViewHolder {

            @NotNull
            private TextView tv;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ViewHolder(@NotNull View itemView) {
                super(itemView);
                Intrinsics.checkNotNullParameter(itemView, "itemView");
                View viewFindViewById = itemView.findViewById(R.id.showTv);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById, "itemView.findViewById(R.id.showTv)");
                this.tv = (TextView) viewFindViewById;
            }

            @NotNull
            public final TextView getTv() {
                return this.tv;
            }

            public final void setTv(@NotNull TextView textView) {
                Intrinsics.checkNotNullParameter(textView, "<set-?>");
                this.tv = textView;
            }
        }

        public KeyBoardAdapter(@NotNull String[] moist) {
            Intrinsics.checkNotNullParameter(moist, "moist");
            this.list = moist;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onBindViewHolder$lambda$0(KeyBoardAdapter this$0, int i2, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            OnItemClickListener onItemClickListener = this$0.adapterListener;
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(i2, this$0.list[i2]);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.list.length;
        }

        public final void setData(@NotNull String[] newList) {
            Intrinsics.checkNotNullParameter(newList, "newList");
            this.list = newList;
            notifyDataSetChanged();
        }

        public final void setOnItemClickListener(@NotNull OnItemClickListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.adapterListener = listener;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            holder.getTv().setText(this.list[position]);
            holder.getTv().setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.widget.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NumberKeyBoardView.KeyBoardAdapter.onBindViewHolder$lambda$0(this.f26060c, position, view);
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NotNull
        public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_number_keyboard_item, parent, false);
            Intrinsics.checkNotNullExpressionValue(viewInflate, "from(parent.context)\n   …oard_item, parent, false)");
            return new ViewHolder(viewInflate);
        }
    }

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0012\u0010\u0006\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/yddmi/doctor/widget/NumberKeyBoardView$OnListener;", "", "onDone", "", "str", "", "onTextChange", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnListener {
        void onDone(@Nullable String str);

        void onTextChange(@Nullable String str);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public NumberKeyBoardView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public NumberKeyBoardView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ NumberKeyBoardView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    private final void dealInit(Context context, AttributeSet attrs, int defStyleAttr) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.widget_number_keyboard, (ViewGroup) this, true);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "from(context).inflate(R.…ber_keyboard, this, true)");
        this.rooView = viewInflate;
        KeyBoardAdapter keyBoardAdapter = null;
        if (viewInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rooView");
            viewInflate = null;
        }
        View viewFindViewById = viewInflate.findViewById(R.id.recyclerView2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "rooView.findViewById(R.id.recyclerView2)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById;
        this.rv = recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rv");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        this.mAdapter = new KeyBoardAdapter(this.canInputDot ? this.KEYBOARD_TEXT : this.KEYBOARD_NUMBER);
        RecyclerView recyclerView2 = this.rv;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rv");
            recyclerView2 = null;
        }
        KeyBoardAdapter keyBoardAdapter2 = this.mAdapter;
        if (keyBoardAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            keyBoardAdapter2 = null;
        }
        recyclerView2.setAdapter(keyBoardAdapter2);
        KeyBoardAdapter keyBoardAdapter3 = this.mAdapter;
        if (keyBoardAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            keyBoardAdapter = keyBoardAdapter3;
        }
        keyBoardAdapter.setOnItemClickListener(new KeyBoardAdapter.OnItemClickListener() { // from class: com.yddmi.doctor.widget.NumberKeyBoardView.dealInit.1
            @Override // com.yddmi.doctor.widget.NumberKeyBoardView.KeyBoardAdapter.OnItemClickListener
            public void onItemClick(int position, @NotNull String str) {
                Intrinsics.checkNotNullParameter(str, "str");
                if (Intrinsics.areEqual(str, StrPool.DOT)) {
                    if (!NumberKeyBoardView.this.canInputDot || NumberKeyBoardView.this.hasDot) {
                        return;
                    } else {
                        NumberKeyBoardView.this.hasDot = true;
                    }
                }
                if (Intrinsics.areEqual(str, "X")) {
                    if (!NumberKeyBoardView.this.mRecordList.isEmpty()) {
                        NumberKeyBoardView.this.dealStrAddOrDel(true, "");
                    }
                    NumberKeyBoardView.this.dealInputStr(true);
                } else {
                    if (str.length() > 0) {
                        NumberKeyBoardView.this.dealStrAddOrDel(false, str);
                        NumberKeyBoardView.dealInputStr$default(NumberKeyBoardView.this, false, 1, null);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealInputStr(boolean delete) {
        if ((!this.mRecordList.isEmpty()) && !delete && this.canInputDot) {
            if (Intrinsics.areEqual(CollectionsKt___CollectionsKt.first((List) this.mRecordList), StrPool.DOT)) {
                this.mRecordList.add(0, "0");
            }
            if (Intrinsics.areEqual(CollectionsKt___CollectionsKt.first((List) this.mRecordList), "0")) {
                if (this.mRecordList.size() == 1) {
                    this.mRecordList.add(1, StrPool.DOT);
                    this.hasDot = true;
                } else if (this.mRecordList.size() > 1 && !Intrinsics.areEqual(this.mRecordList.get(1), StrPool.DOT)) {
                    this.mRecordList.add(1, StrPool.DOT);
                    this.hasDot = true;
                }
            }
        }
        StringsKt__StringBuilderJVMKt.clear(this.strBuilder);
        Iterator<String> it = this.mRecordList.iterator();
        while (it.hasNext()) {
            this.strBuilder.append(it.next());
        }
        OnListener onListener = this.mListener;
        if (onListener != null) {
            onListener.onTextChange(this.strBuilder.toString());
        }
    }

    public static /* synthetic */ void dealInputStr$default(NumberKeyBoardView numberKeyBoardView, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        numberKeyBoardView.dealInputStr(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealStrAddOrDel(boolean del, String str) {
        if (del) {
            if (this.mEt == null) {
                if (this.mRecordList.getLast().equals(StrPool.DOT)) {
                    this.hasDot = false;
                }
                this.mRecordList.removeLast();
                return;
            } else {
                if (this.mRecordList.size() > 0) {
                    EditText editText = this.mEt;
                    Intrinsics.checkNotNull(editText);
                    int selectionStart = editText.getSelectionStart() - 1;
                    if (Intrinsics.areEqual(this.mRecordList.get(selectionStart), StrPool.DOT)) {
                        this.hasDot = false;
                    }
                    if (this.mRecordList.size() > 1 && selectionStart == 0 && Intrinsics.areEqual(this.mRecordList.get(1), StrPool.DOT)) {
                        return;
                    }
                    this.mRecordList.remove(selectionStart);
                    return;
                }
                return;
            }
        }
        if (-1 != this.maxLength) {
            if (this.mRecordList.size() < this.maxLength) {
                this.mRecordList.add(str);
            }
            if (this.antoMaxDone && this.mRecordList.size() == this.maxLength) {
                dealInputStr$default(this, false, 1, null);
                OnListener onListener = this.mListener;
                if (onListener != null) {
                    onListener.onDone(this.strBuilder.toString());
                    return;
                }
                return;
            }
            return;
        }
        if (this.mEt == null) {
            this.mRecordList.add(str);
            return;
        }
        int size = this.mRecordList.size();
        EditText editText2 = this.mEt;
        Intrinsics.checkNotNull(editText2);
        if (size < ViewExtKt.getMaxLength(editText2)) {
            LinkedList<String> linkedList = this.mRecordList;
            EditText editText3 = this.mEt;
            Intrinsics.checkNotNull(editText3);
            linkedList.add(editText3.getSelectionStart(), str);
        }
    }

    public static /* synthetic */ void dealStrAddOrDel$default(NumberKeyBoardView numberKeyBoardView, boolean z2, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        numberKeyBoardView.dealStrAddOrDel(z2, str);
    }

    public static /* synthetic */ void setCanInputDot$default(NumberKeyBoardView numberKeyBoardView, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z3 = true;
        }
        numberKeyBoardView.setCanInputDot(z2, z3);
    }

    private final void setHasDot(boolean dot) {
        this.hasDot = dot;
    }

    public static /* synthetic */ void setInputStrBuildEt$default(NumberKeyBoardView numberKeyBoardView, String str, EditText editText, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            editText = null;
        }
        numberKeyBoardView.setInputStrBuildEt(str, editText);
    }

    public final void setAutoMaxDone(boolean auto) {
        this.antoMaxDone = auto;
    }

    public final void setCanInputDot(boolean dot, boolean refreshAdapter) {
        this.canInputDot = dot;
        if (refreshAdapter) {
            KeyBoardAdapter keyBoardAdapter = null;
            if (dot) {
                KeyBoardAdapter keyBoardAdapter2 = this.mAdapter;
                if (keyBoardAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                } else {
                    keyBoardAdapter = keyBoardAdapter2;
                }
                keyBoardAdapter.setData(this.KEYBOARD_TEXT);
                return;
            }
            KeyBoardAdapter keyBoardAdapter3 = this.mAdapter;
            if (keyBoardAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            } else {
                keyBoardAdapter = keyBoardAdapter3;
            }
            keyBoardAdapter.setData(this.KEYBOARD_NUMBER);
        }
    }

    public final void setInputStr(@Nullable String str) {
        this.mRecordList.clear();
        boolean z2 = false;
        if (str != null) {
            char[] charArray = str.toCharArray();
            Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
            boolean z3 = false;
            for (char c3 : charArray) {
                this.mRecordList.add(String.valueOf(c3));
                if (Character.valueOf(c3).equals(StrPool.DOT)) {
                    z3 = true;
                }
            }
            z2 = z3;
        }
        this.hasDot = z2;
    }

    public final void setInputStrBuildEt(@Nullable String str, @Nullable EditText et) {
        this.mEt = et;
        setInputStr(str);
    }

    public final void setMaxLength(int max) {
        this.maxLength = max;
    }

    public final void setOnListener(@NotNull OnListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public NumberKeyBoardView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.KEYBOARD_TEXT = new String[]{"1", "2", "3", "4", "5", Constants.VIA_SHARE_TYPE_INFO, "7", Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, Constants.VIA_SHARE_TYPE_MINI_PROGRAM, "", "0", StrPool.DOT};
        this.KEYBOARD_NUMBER = new String[]{"1", "2", "3", "4", "5", Constants.VIA_SHARE_TYPE_INFO, "7", Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, Constants.VIA_SHARE_TYPE_MINI_PROGRAM, "", "0", "X"};
        this.mRecordList = new LinkedList<>();
        this.strBuilder = new StringBuilder();
        this.canInputDot = true;
        this.antoMaxDone = true;
        this.maxLength = 6;
        dealInit(context, attributeSet, i2);
    }
}
