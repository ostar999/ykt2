package com.xiaomi.push;

import cn.hutool.core.text.StrPool;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes6.dex */
public class bi implements go {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f24636a;

    /* renamed from: a, reason: collision with other field name */
    private gc f209a;

    /* renamed from: a, reason: collision with other field name */
    private SimpleDateFormat f212a = new SimpleDateFormat("hh:mm:ss aaa");

    /* renamed from: a, reason: collision with other field name */
    private a f208a = null;

    /* renamed from: b, reason: collision with root package name */
    private a f24637b = null;

    /* renamed from: a, reason: collision with other field name */
    private gf f210a = null;

    /* renamed from: a, reason: collision with other field name */
    private final String f211a = "[Slim] ";

    public class a implements gh, gp {

        /* renamed from: a, reason: collision with other field name */
        String f213a;

        public a(boolean z2) {
            this.f213a = z2 ? " RCV " : " Sent ";
        }

        @Override // com.xiaomi.push.gh
        public void a(fv fvVar) {
            StringBuilder sb;
            String string;
            if (bi.f24636a) {
                sb = new StringBuilder();
                sb.append("[Slim] ");
                sb.append(bi.this.f212a.format(new Date()));
                sb.append(this.f213a);
                string = fvVar.toString();
            } else {
                sb = new StringBuilder();
                sb.append("[Slim] ");
                sb.append(bi.this.f212a.format(new Date()));
                sb.append(this.f213a);
                sb.append(" Blob [");
                sb.append(fvVar.m428a());
                sb.append(",");
                sb.append(fvVar.a());
                sb.append(",");
                sb.append(fvVar.e());
                string = StrPool.BRACKET_END;
            }
            sb.append(string);
            com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
        }

        @Override // com.xiaomi.push.gh
        public void a(gt gtVar) {
            StringBuilder sb;
            String strMo468a;
            if (bi.f24636a) {
                sb = new StringBuilder();
                sb.append("[Slim] ");
                sb.append(bi.this.f212a.format(new Date()));
                sb.append(this.f213a);
                sb.append(" PKT ");
                strMo468a = gtVar.mo468a();
            } else {
                sb = new StringBuilder();
                sb.append("[Slim] ");
                sb.append(bi.this.f212a.format(new Date()));
                sb.append(this.f213a);
                sb.append(" PKT [");
                sb.append(gtVar.k());
                sb.append(",");
                sb.append(gtVar.j());
                strMo468a = StrPool.BRACKET_END;
            }
            sb.append(strMo468a);
            com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
        }

        @Override // com.xiaomi.push.gp
        /* renamed from: a, reason: collision with other method in class */
        public boolean mo234a(gt gtVar) {
            return true;
        }
    }

    static {
        f24636a = v.a() == 1;
    }

    public bi(gc gcVar) {
        this.f209a = gcVar;
        a();
    }

    private void a() {
        this.f208a = new a(true);
        this.f24637b = new a(false);
        gc gcVar = this.f209a;
        a aVar = this.f208a;
        gcVar.a(aVar, aVar);
        gc gcVar2 = this.f209a;
        a aVar2 = this.f24637b;
        gcVar2.b(aVar2, aVar2);
        this.f210a = new bj(this);
    }
}
