package io.agora.rtc.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.lang.ref.WeakReference;

/* loaded from: classes8.dex */
public class PowerConnectionReceiver extends BroadcastReceiver {
    private WeakReference<CommonUtility> mCommonUtility;

    public PowerConnectionReceiver(CommonUtility cu) {
        this.mCommonUtility = new WeakReference<>(cu);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        CommonUtility commonUtility = this.mCommonUtility.get();
        if (commonUtility == null) {
            Logging.w("rtc engine is not ready");
            return;
        }
        if (intent != null) {
            int intExtra = intent.getIntExtra(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, -1);
            int intExtra2 = intent.getIntExtra("scale", -1);
            if (intExtra2 != 0) {
                commonUtility.onPowerChange((int) ((intExtra / intExtra2) * 100.0f));
            }
        }
    }
}
