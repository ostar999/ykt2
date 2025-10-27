package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.Objects;

/* loaded from: classes6.dex */
public class CircleEntranceGuidePop extends FullScreenPopupView {
    private int circleXOffset;

    public CircleEntranceGuidePop(@NonNull Context context, int circleX) {
        super(context);
        this.circleXOffset = circleX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_circle_entrance_guide;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.iv_down_triangle);
        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.o2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16750c.lambda$onCreate$0(view);
            }
        });
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            imageView.setImageResource(R.drawable.ic_down_triangle_night);
        }
        boolean zEquals = Objects.equals(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_course, getContext(), "0"), "1");
        boolean zEquals2 = Objects.equals(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_shop, getContext(), "0"), "1");
        int i2 = getContext().getResources().getDisplayMetrics().widthPixels / 8;
        if (!zEquals && zEquals2) {
            imageView.setTranslationX(i2);
        }
        if (!zEquals || zEquals2) {
            return;
        }
        imageView.setTranslationX(-i2);
    }
}
