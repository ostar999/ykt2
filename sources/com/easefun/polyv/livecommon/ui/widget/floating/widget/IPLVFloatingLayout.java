package com.easefun.polyv.livecommon.ui.widget.floating.widget;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import com.easefun.polyv.livecommon.ui.widget.floating.enums.PLVFloatingEnums;

/* loaded from: classes3.dex */
public interface IPLVFloatingLayout {
    void destroy();

    View getContentView();

    Point getFloatLocation();

    void hide();

    boolean isShowing();

    void setAutoMoveToEdge(PLVFloatingEnums.AutoEdgeType autoEdgeType);

    void setConsumeTouchEventOnMove(boolean consumeTouchEventOnMove);

    void setContentView(View view);

    void setEnableDrag(boolean enableDrag);

    void setShowType(PLVFloatingEnums.ShowType showType);

    void show(Activity activity);

    void updateFloatLocation(int x2, int y2);

    void updateFloatSize(int width, int height);
}
