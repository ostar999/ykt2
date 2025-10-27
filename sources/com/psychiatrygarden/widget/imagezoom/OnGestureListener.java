package com.psychiatrygarden.widget.imagezoom;

/* loaded from: classes6.dex */
public interface OnGestureListener {
    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX, float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);
}
