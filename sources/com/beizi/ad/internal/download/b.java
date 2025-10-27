package com.beizi.ad.internal.download;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.beizi.ad.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class b extends BaseExpandableListAdapter {

    /* renamed from: a, reason: collision with root package name */
    private Context f4150a;

    /* renamed from: b, reason: collision with root package name */
    private List<c> f4151b;

    public class a {

        /* renamed from: a, reason: collision with root package name */
        TextView f4152a;

        /* renamed from: b, reason: collision with root package name */
        BeiZiWebView f4153b;

        public a() {
        }
    }

    /* renamed from: com.beizi.ad.internal.download.b$b, reason: collision with other inner class name */
    public class C0053b {

        /* renamed from: a, reason: collision with root package name */
        TextView f4155a;

        /* renamed from: b, reason: collision with root package name */
        ImageView f4156b;

        /* renamed from: c, reason: collision with root package name */
        View f4157c;

        public C0053b() {
        }
    }

    public b(Context context, List<c> list) {
        ArrayList arrayList = new ArrayList();
        this.f4151b = arrayList;
        this.f4150a = context;
        arrayList.clear();
        this.f4151b.addAll(list);
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int i2, int i3) {
        return this.f4151b.get(i2).c();
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int i2, int i3) {
        return i3;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int i2, int i3, boolean z2, View view, ViewGroup viewGroup) {
        View viewInflate = LayoutInflater.from(this.f4150a).inflate(R.layout.beizi_download_dialog_expand_child_item, (ViewGroup) null);
        a aVar = new a();
        aVar.f4152a = (TextView) viewInflate.findViewById(R.id.beizi_addeci_content_tv);
        aVar.f4153b = (BeiZiWebView) viewInflate.findViewById(R.id.beizi_addeci_content_wb);
        viewInflate.setTag(aVar);
        if ("text".equals(this.f4151b.get(i2).b())) {
            aVar.f4152a.setVisibility(0);
            aVar.f4153b.setVisibility(8);
            aVar.f4152a.setText(this.f4151b.get(i2).c());
        } else if ("h5".equals(this.f4151b.get(i2).b())) {
            aVar.f4152a.setVisibility(8);
            aVar.f4153b.setVisibility(0);
            aVar.f4153b.loadUrl(this.f4151b.get(i2).c());
        }
        return viewInflate;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int i2) {
        return 1;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int i2) {
        return this.f4151b.get(i2);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.f4151b.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int i2) {
        return i2;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int i2, boolean z2, View view, ViewGroup viewGroup) {
        C0053b c0053b;
        if (view == null) {
            view = LayoutInflater.from(this.f4150a).inflate(R.layout.beizi_download_dialog_expand_parent_item, (ViewGroup) null);
            c0053b = new C0053b();
            c0053b.f4155a = (TextView) view.findViewById(R.id.beizi_addep_title_tv);
            c0053b.f4156b = (ImageView) view.findViewById(R.id.beizi_addep_fold_iv);
            c0053b.f4157c = view.findViewById(R.id.beizi_addep_item_divider_view);
            view.setTag(c0053b);
        } else {
            c0053b = (C0053b) view.getTag();
        }
        c0053b.f4155a.setText(this.f4151b.get(i2).a());
        if (z2) {
            c0053b.f4155a.setTextColor(Color.parseColor("#FF8E15"));
            c0053b.f4156b.setBackgroundResource(R.mipmap.beizi_icon_arrow_unfold);
        } else {
            c0053b.f4155a.setTextColor(Color.parseColor("#333333"));
            c0053b.f4156b.setBackgroundResource(R.mipmap.beizi_icon_arrow_fold);
        }
        if (i2 == 0) {
            c0053b.f4157c.setVisibility(8);
        } else {
            c0053b.f4157c.setVisibility(0);
        }
        return view;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int i2, int i3) {
        return false;
    }
}
