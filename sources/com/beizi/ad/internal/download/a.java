package com.beizi.ad.internal.download;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.beizi.ad.R;
import com.beizi.ad.internal.utilities.ImageManager;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class a extends Dialog {

    /* renamed from: com.beizi.ad.internal.download.a$a, reason: collision with other inner class name */
    public static class C0052a {

        /* renamed from: a, reason: collision with root package name */
        private TextView f4135a;

        /* renamed from: b, reason: collision with root package name */
        private TextView f4136b;

        /* renamed from: c, reason: collision with root package name */
        private TextView f4137c;

        /* renamed from: d, reason: collision with root package name */
        private ImageView f4138d;

        /* renamed from: e, reason: collision with root package name */
        private ImageView f4139e;

        /* renamed from: f, reason: collision with root package name */
        private ExpandableListView f4140f;

        /* renamed from: g, reason: collision with root package name */
        private LinearLayout f4141g;

        /* renamed from: h, reason: collision with root package name */
        private View f4142h;

        /* renamed from: i, reason: collision with root package name */
        private b f4143i;

        /* renamed from: j, reason: collision with root package name */
        private com.beizi.ad.a.a f4144j;

        /* renamed from: k, reason: collision with root package name */
        private a f4145k;

        /* renamed from: l, reason: collision with root package name */
        private Context f4146l;

        public C0052a(Context context) {
            this.f4146l = context;
            this.f4145k = new a(context, R.style.beizi_custom_dialog);
            View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.beizi_download_dialog, (ViewGroup) null, false);
            this.f4142h = viewInflate;
            this.f4145k.addContentView(viewInflate, new ViewGroup.LayoutParams(-1, -2));
            this.f4138d = (ImageView) this.f4142h.findViewById(R.id.beizi_download_dialog_close_iv);
            this.f4139e = (ImageView) this.f4142h.findViewById(R.id.beizi_download_dialog_icon_iv);
            this.f4135a = (TextView) this.f4142h.findViewById(R.id.beizi_download_dialog_name_tv);
            this.f4136b = (TextView) this.f4142h.findViewById(R.id.beizi_download_dialog_version_tv);
            this.f4137c = (TextView) this.f4142h.findViewById(R.id.beizi_download_dialog_developer_tv);
            this.f4140f = (ExpandableListView) this.f4142h.findViewById(R.id.beizi_download_dialog_expand_lv);
            this.f4141g = (LinearLayout) this.f4142h.findViewById(R.id.beizi_download_dialog_download_ll);
            WindowManager.LayoutParams attributes = this.f4145k.getWindow().getAttributes();
            Point point = new Point();
            this.f4145k.getWindow().getWindowManager().getDefaultDisplay().getSize(point);
            attributes.width = point.x;
            attributes.height = (int) (point.y * 0.45d);
            attributes.gravity = 80;
            this.f4145k.getWindow().setAttributes(attributes);
        }

        public C0052a a(com.beizi.ad.a.a aVar) {
            this.f4144j = aVar;
            return this;
        }

        public C0052a a(b bVar) {
            this.f4143i = bVar;
            return this;
        }

        public a a() {
            this.f4138d.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.download.a.a.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    C0052a.this.f4145k.dismiss();
                    C0052a.this.f4143i.a();
                }
            });
            this.f4141g.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.download.a.a.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    C0052a.this.f4145k.dismiss();
                    C0052a.this.f4143i.b();
                }
            });
            if (this.f4139e != null && !TextUtils.isEmpty(this.f4144j.n())) {
                try {
                    ImageManager.with(null).getBitmap(this.f4144j.n(), new ImageManager.BitmapLoadedListener() { // from class: com.beizi.ad.internal.download.a.a.3
                        @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
                        public void onBitmapLoadFailed() {
                        }

                        @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
                        public void onBitmapLoaded(Bitmap bitmap) {
                            C0052a.this.f4139e.setImageBitmap(bitmap);
                        }
                    });
                } catch (Exception unused) {
                }
            }
            if (this.f4135a != null && !TextUtils.isEmpty(this.f4144j.e())) {
                this.f4135a.setText(this.f4144j.e());
            }
            if (this.f4136b != null && !TextUtils.isEmpty(this.f4144j.i())) {
                this.f4136b.setText("版本号 ：" + this.f4144j.i());
            }
            if (this.f4137c != null && !TextUtils.isEmpty(this.f4144j.j())) {
                this.f4137c.setText("开发者 ：" + this.f4144j.j());
            }
            ArrayList arrayList = new ArrayList();
            c cVar = new c();
            cVar.a("应用权限");
            if (!TextUtils.isEmpty(this.f4144j.l())) {
                cVar.c(this.f4144j.l());
                cVar.b("h5");
            } else if (!TextUtils.isEmpty(this.f4144j.k())) {
                cVar.c(this.f4144j.k());
                cVar.b("text");
            }
            arrayList.add(cVar);
            c cVar2 = new c();
            cVar2.a("隐私协议");
            if (!TextUtils.isEmpty(this.f4144j.m())) {
                cVar2.c(this.f4144j.m());
                cVar2.b("h5");
            }
            arrayList.add(cVar2);
            this.f4140f.setAdapter(new com.beizi.ad.internal.download.b(this.f4146l, arrayList));
            this.f4145k.setContentView(this.f4142h);
            this.f4145k.setCancelable(false);
            this.f4145k.setCanceledOnTouchOutside(false);
            return this.f4145k;
        }
    }

    public interface b {
        void a();

        void b();
    }

    public a(@NonNull Context context, int i2) {
        super(context, i2);
    }
}
