package com.noober.background.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;
import com.noober.background.R;

/* loaded from: classes4.dex */
public class TextViewGradientColor implements ITextViewOperator {
    private int endColor = -1;
    private int startColor = -1;
    private int orientation = 0;

    @Override // com.noober.background.drawable.ITextViewOperator
    public void invoke(Context context, AttributeSet attributeSet, final TextView textView) {
        int i2;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.bl_text);
        try {
        } catch (Exception unused) {
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
        if (typedArrayObtainStyledAttributes.getIndexCount() == 0) {
            typedArrayObtainStyledAttributes.recycle();
            return;
        }
        for (int i3 = 0; i3 < typedArrayObtainStyledAttributes.getIndexCount(); i3++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i3);
            if (index == R.styleable.bl_text_bl_text_gradient_endColor) {
                this.endColor = typedArrayObtainStyledAttributes.getColor(index, -1);
            } else if (index == R.styleable.bl_text_bl_text_gradient_startColor) {
                this.startColor = typedArrayObtainStyledAttributes.getColor(index, -1);
            } else if (index == R.styleable.bl_text_bl_text_gradient_orientation) {
                this.orientation = typedArrayObtainStyledAttributes.getInt(index, 0);
            }
        }
        int i4 = this.endColor;
        if (i4 != -1 || (i2 = this.startColor) == -1) {
            int i5 = this.startColor;
            if (i5 == -1 && i4 != -1) {
                textView.setTextColor(i4);
            } else if (i4 != -1 && i5 != -1) {
                if (this.orientation == 0) {
                    textView.post(new Runnable() { // from class: com.noober.background.drawable.TextViewGradientColor.1
                        @Override // java.lang.Runnable
                        public void run() {
                            textView.getPaint().setShader(new LinearGradient(0.0f, 0.0f, 0.0f, textView.getPaint().descent() - textView.getPaint().ascent(), TextViewGradientColor.this.startColor, TextViewGradientColor.this.endColor, Shader.TileMode.REPEAT));
                            textView.invalidate();
                        }
                    });
                } else {
                    textView.post(new Runnable() { // from class: com.noober.background.drawable.TextViewGradientColor.2
                        @Override // java.lang.Runnable
                        public void run() {
                            textView.getPaint().setShader(new LinearGradient(0.0f, 0.0f, textView.getMeasuredWidth(), 0.0f, TextViewGradientColor.this.startColor, TextViewGradientColor.this.endColor, Shader.TileMode.REPEAT));
                            textView.invalidate();
                        }
                    });
                }
            }
        } else {
            textView.setTextColor(i2);
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
