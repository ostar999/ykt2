package com.huawei.hms.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes4.dex */
public abstract class AbstractDialog {

    /* renamed from: a, reason: collision with root package name */
    public Activity f8099a;

    /* renamed from: b, reason: collision with root package name */
    public AlertDialog f8100b;

    /* renamed from: c, reason: collision with root package name */
    public Callback f8101c;

    public interface Callback {
        void onCancel(AbstractDialog abstractDialog);

        void onDoWork(AbstractDialog abstractDialog);
    }

    public class a implements DialogInterface.OnClickListener {
        public a() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i2) {
            AbstractDialog.this.fireDoWork();
        }
    }

    public class b implements DialogInterface.OnClickListener {
        public b() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i2) {
            AbstractDialog.this.cancel();
        }
    }

    public class c implements DialogInterface.OnCancelListener {
        public c() {
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            AbstractDialog.this.fireCancel();
        }
    }

    public class d implements DialogInterface.OnKeyListener {
        public d() {
        }

        @Override // android.content.DialogInterface.OnKeyListener
        public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
            if (4 != i2 || keyEvent.getAction() != 1) {
                return false;
            }
            AbstractDialog.this.cancel();
            return true;
        }
    }

    public static int a(Context context) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier("androidhwext:style/Theme.Emui", null, null);
    }

    public void cancel() {
        AlertDialog alertDialog = this.f8100b;
        if (alertDialog != null) {
            alertDialog.cancel();
        }
    }

    public void dismiss() {
        AlertDialog alertDialog = this.f8100b;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public void fireCancel() {
        Callback callback = this.f8101c;
        if (callback != null) {
            callback.onCancel(this);
        }
    }

    public void fireDoWork() {
        Callback callback = this.f8101c;
        if (callback != null) {
            callback.onDoWork(this);
        }
    }

    public Activity getActivity() {
        return this.f8099a;
    }

    public int getDialogThemeId() {
        return a(this.f8099a) != 0 ? 0 : 3;
    }

    public AlertDialog onCreateDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getDialogThemeId());
        String strOnGetTitleString = onGetTitleString(activity);
        if (strOnGetTitleString != null) {
            builder.setTitle(strOnGetTitleString);
        }
        String strOnGetMessageString = onGetMessageString(activity);
        if (strOnGetMessageString != null) {
            builder.setMessage(strOnGetMessageString);
        }
        String strOnGetPositiveButtonString = onGetPositiveButtonString(activity);
        if (strOnGetPositiveButtonString != null) {
            builder.setPositiveButton(strOnGetPositiveButtonString, new a());
        }
        String strOnGetNegativeButtonString = onGetNegativeButtonString(activity);
        if (strOnGetNegativeButtonString != null) {
            builder.setNegativeButton(strOnGetNegativeButtonString, new b());
        }
        return builder.create();
    }

    public abstract String onGetMessageString(Context context);

    public abstract String onGetNegativeButtonString(Context context);

    public abstract String onGetPositiveButtonString(Context context);

    public abstract String onGetTitleString(Context context);

    public void setMessage(CharSequence charSequence) {
        AlertDialog alertDialog = this.f8100b;
        if (alertDialog != null) {
            alertDialog.setMessage(charSequence);
        }
    }

    public void setTitle(CharSequence charSequence) {
        AlertDialog alertDialog = this.f8100b;
        if (alertDialog != null) {
            alertDialog.setTitle(charSequence);
        }
    }

    public void show(Activity activity, Callback callback) {
        this.f8099a = activity;
        this.f8101c = callback;
        if (activity == null || activity.isFinishing()) {
            HMSLog.e("AbstractDialog", "In show, The activity is null or finishing.");
            return;
        }
        AlertDialog alertDialogOnCreateDialog = onCreateDialog(this.f8099a);
        this.f8100b = alertDialogOnCreateDialog;
        alertDialogOnCreateDialog.setCanceledOnTouchOutside(false);
        this.f8100b.setOnCancelListener(new c());
        this.f8100b.setOnKeyListener(new d());
        this.f8100b.show();
    }
}
