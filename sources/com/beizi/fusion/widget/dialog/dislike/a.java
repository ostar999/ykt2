package com.beizi.fusion.widget.dialog.dislike;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.beizi.fusion.R;
import com.beizi.fusion.g.ap;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class a extends Dialog {

    /* renamed from: b, reason: collision with root package name */
    private static h f5501b;

    /* renamed from: a, reason: collision with root package name */
    private List<b> f5502a;

    /* renamed from: c, reason: collision with root package name */
    private int f5503c;

    /* renamed from: d, reason: collision with root package name */
    private int f5504d;

    /* renamed from: com.beizi.fusion.widget.dialog.dislike.a$a, reason: collision with other inner class name */
    public static class C0074a {

        /* renamed from: a, reason: collision with root package name */
        private RecyclerView f5505a;

        /* renamed from: b, reason: collision with root package name */
        private View f5506b;

        /* renamed from: c, reason: collision with root package name */
        private a f5507c;

        /* renamed from: d, reason: collision with root package name */
        private c f5508d;

        public C0074a(Context context) {
            this.f5507c = new a(context, R.style.beizi_custom_dialog);
            View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.beizi_dislike_dialog, (ViewGroup) null, false);
            this.f5506b = viewInflate;
            this.f5507c.addContentView(viewInflate, new ViewGroup.LayoutParams(-1, -2));
            this.f5505a = (RecyclerView) this.f5506b.findViewById(R.id.beizi_dislike_reasons_list_recycleview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(1);
            this.f5505a.setLayoutManager(linearLayoutManager);
            a.f5501b.a(new e() { // from class: com.beizi.fusion.widget.dialog.dislike.a.a.1
                @Override // com.beizi.fusion.widget.dialog.dislike.a.e
                public void a(View view, int i2) {
                    if (C0074a.this.f5507c != null) {
                        C0074a.this.f5507c.dismiss();
                    }
                    if (C0074a.this.f5508d != null) {
                        C0074a.this.f5508d.a();
                    }
                }
            });
            this.f5505a.setAdapter(a.f5501b);
            WindowManager.LayoutParams attributes = this.f5507c.getWindow().getAttributes();
            this.f5507c.getWindow().getWindowManager().getDefaultDisplay().getSize(new Point());
            attributes.width = (int) (r0.x * 0.85d);
            attributes.gravity = 17;
            this.f5507c.getWindow().setAttributes(attributes);
        }

        public C0074a a(c cVar) {
            this.f5508d = cVar;
            return this;
        }

        public a a() {
            this.f5507c.setContentView(this.f5506b);
            this.f5507c.setCancelable(true);
            this.f5507c.setCanceledOnTouchOutside(true);
            return this.f5507c;
        }
    }

    public class b {

        /* renamed from: a, reason: collision with root package name */
        int f5510a;

        /* renamed from: b, reason: collision with root package name */
        String f5511b;

        /* renamed from: c, reason: collision with root package name */
        List<d> f5512c;

        public b() {
        }

        public List<d> a() {
            return this.f5512c;
        }

        public String b() {
            return this.f5511b;
        }

        public int getType() {
            return this.f5510a;
        }

        public void a(List<d> list) {
            this.f5512c = list;
        }

        public void a(int i2) {
            this.f5510a = i2;
        }

        public void a(String str) {
            this.f5511b = str;
        }
    }

    public interface c {
        void a();
    }

    public class d {

        /* renamed from: a, reason: collision with root package name */
        String f5514a;

        public d() {
        }

        public String a() {
            return this.f5514a;
        }

        public void a(String str) {
            this.f5514a = str;
        }
    }

    public interface e {
        void a(View view, int i2);
    }

    public interface f {
        void a(View view, int i2);
    }

    public class g extends RecyclerView.Adapter {

        /* renamed from: b, reason: collision with root package name */
        private List<d> f5517b;

        /* renamed from: c, reason: collision with root package name */
        private Context f5518c;

        /* renamed from: d, reason: collision with root package name */
        private f f5519d;

        /* renamed from: com.beizi.fusion.widget.dialog.dislike.a$g$a, reason: collision with other inner class name */
        public class C0075a extends RecyclerView.ViewHolder {

            /* renamed from: a, reason: collision with root package name */
            TextView f5522a;

            public C0075a(View view) {
                super(view);
                this.f5522a = (TextView) view.findViewById(R.id.beizi_dislike_item_multi_two_recycleview_item);
            }
        }

        public g(Context context, List<d> list) {
            this.f5518c = context;
            this.f5517b = list;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.f5517b.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint({"RecyclerView"}) final int i2) {
            C0075a c0075a = (C0075a) viewHolder;
            c0075a.f5522a.setText(this.f5517b.get(i2).a());
            ap.a(c0075a.itemView, "#FFFAF6F6", 0, "", 10);
            c0075a.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.widget.dialog.dislike.a.g.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (g.this.f5519d != null) {
                        g.this.f5519d.a(view, i2);
                    }
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
            return new C0075a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.beizi_dislike_item_multi_two_recycle_item, viewGroup, false));
        }

        public void a(f fVar) {
            this.f5519d = fVar;
        }
    }

    public class h extends RecyclerView.Adapter {

        /* renamed from: a, reason: collision with root package name */
        public e f5524a;

        /* renamed from: c, reason: collision with root package name */
        private Context f5526c;

        /* renamed from: d, reason: collision with root package name */
        private List<b> f5527d;

        /* renamed from: com.beizi.fusion.widget.dialog.dislike.a$h$a, reason: collision with other inner class name */
        public class C0076a extends RecyclerView.ViewHolder {

            /* renamed from: b, reason: collision with root package name */
            private TextView f5533b;

            public C0076a(View view) {
                super(view);
                this.f5533b = (TextView) view.findViewById(R.id.beizi_dislike_item_multi_one_title);
            }
        }

        public class b extends RecyclerView.ViewHolder {

            /* renamed from: b, reason: collision with root package name */
            private TextView f5535b;

            /* renamed from: c, reason: collision with root package name */
            private RecyclerView f5536c;

            public b(View view) {
                super(view);
                this.f5535b = (TextView) view.findViewById(R.id.beizi_dislike_item_multi_two_title);
                this.f5536c = (RecyclerView) view.findViewById(R.id.beizi_dislike_item_multi_two_recycleview);
            }
        }

        public h(Context context, List<b> list) {
            this.f5526c = context;
            this.f5527d = list;
        }

        public void a(e eVar) {
            this.f5524a = eVar;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.f5527d.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i2) {
            return (this.f5527d.get(i2).a() == null || this.f5527d.get(i2).a().size() <= 0) ? a.this.f5503c : a.this.f5504d;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i2) {
            if (viewHolder instanceof C0076a) {
                ((C0076a) viewHolder).f5533b.setText(this.f5527d.get(i2).b());
            } else {
                b bVar = (b) viewHolder;
                bVar.f5535b.setText(this.f5527d.get(i2).b());
                bVar.f5536c.setLayoutManager(new FlowLayoutManager());
                a aVar = a.this;
                g gVar = aVar.new g(aVar.getContext(), this.f5527d.get(i2).a());
                bVar.f5536c.setAdapter(gVar);
                gVar.a(new f() { // from class: com.beizi.fusion.widget.dialog.dislike.a.h.1
                    @Override // com.beizi.fusion.widget.dialog.dislike.a.f
                    public void a(View view, int i3) {
                        e eVar = h.this.f5524a;
                        if (eVar != null) {
                            eVar.a(view, i3);
                        }
                    }
                });
                bVar.f5536c.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.beizi.fusion.widget.dialog.dislike.a.h.2
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                        super.getItemOffsets(rect, view, recyclerView, state);
                        rect.bottom = 30;
                        rect.left = 60;
                    }
                });
            }
            if (this.f5524a != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.widget.dialog.dislike.a.h.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        int layoutPosition = viewHolder.getLayoutPosition();
                        if (a.this.f5504d != h.this.getItemViewType(layoutPosition)) {
                            h.this.f5524a.a(viewHolder.itemView, layoutPosition);
                        }
                    }
                });
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
            return i2 == a.this.f5503c ? new C0076a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.beizi_dislike_item_multi_one, viewGroup, false)) : new b(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.beizi_dislike_item_multi_two, viewGroup, false));
        }
    }

    public a(@NonNull Context context, int i2) {
        super(context, i2);
        this.f5502a = null;
        this.f5503c = 1;
        this.f5504d = 2;
        List<b> listB = b();
        this.f5502a = listB;
        f5501b = new h(context, listB);
    }

    private List<b> b() {
        ArrayList arrayList = new ArrayList();
        b bVar = new b();
        bVar.a("内容无法正常展示（卡顿、黑白屏）");
        bVar.a(this.f5503c);
        arrayList.add(bVar);
        b bVar2 = new b();
        bVar2.a("不感兴趣");
        bVar2.a(this.f5503c);
        arrayList.add(bVar2);
        b bVar3 = new b();
        bVar3.a("无法关闭");
        bVar3.a(this.f5503c);
        arrayList.add(bVar3);
        ArrayList arrayList2 = new ArrayList();
        d dVar = new d();
        dVar.a("疑似抄袭");
        arrayList2.add(dVar);
        d dVar2 = new d();
        dVar2.a("虚假欺诈");
        arrayList2.add(dVar2);
        d dVar3 = new d();
        dVar3.a("违法违规");
        arrayList2.add(dVar3);
        d dVar4 = new d();
        dVar4.a("低俗色情");
        arrayList2.add(dVar4);
        b bVar4 = new b();
        bVar4.a("举报广告");
        bVar4.a(arrayList2);
        bVar4.a(this.f5503c);
        arrayList.add(bVar4);
        return arrayList;
    }
}
