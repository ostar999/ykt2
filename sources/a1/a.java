package a1;

import android.R;
import android.view.View;
import android.widget.TextView;
import com.hjq.toast.config.IToast;

/* loaded from: classes4.dex */
public final /* synthetic */ class a {
    public static TextView a(IToast iToast, View view) {
        if (!(view instanceof TextView)) {
            View viewFindViewById = view.findViewById(R.id.message);
            if (viewFindViewById instanceof TextView) {
                return (TextView) viewFindViewById;
            }
            throw new IllegalArgumentException("You must include a TextView with an ID value of message (xml code: android:id=\"@android:id/message\", java code: view.setId(android.R.id.message))");
        }
        if (view.getId() == -1) {
            view.setId(R.id.message);
        } else if (view.getId() != 16908299) {
            throw new IllegalArgumentException("You must set the ID value of TextView to android.R.id.message");
        }
        return (TextView) view;
    }
}
