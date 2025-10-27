package com.noober.background.drawable;

import android.R;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class DrawableCreator {

    public static class Builder {
        private Drawable checkableDrawable;
        private Integer checkableSolidColor;
        private Integer checkableStrokeColor;
        private Integer checkableTextColor;
        private Drawable checkedDrawable;
        private Integer checkedSolidColor;
        private Integer checkedStrokeColor;
        private Integer checkedTextColor;
        private Float cornersBottomLeftRadius;
        private Float cornersBottomRightRadius;
        private Float cornersRadius;
        private Float cornersTopLeftRadius;
        private Float cornersTopRightRadius;
        private Drawable enabledDrawable;
        private Integer enabledSolidColor;
        private Integer enabledStrokeColor;
        private Integer enabledTextColor;
        private Drawable focusedActivated;
        private Drawable focusedDrawable;
        private Drawable focusedHovered;
        private Integer focusedSolidColor;
        private Integer focusedStrokeColor;
        private Integer focusedTextColor;
        private Integer gradientCenterColor;
        private Float gradientCenterX;
        private Float gradientCenterY;
        private Integer gradientEndColor;
        private Float gradientRadius;
        private Integer gradientStartColor;
        private Drawable pressedDrawable;
        private Integer pressedSolidColor;
        private Integer pressedStrokeColor;
        private Integer pressedTextColor;
        private Integer rippleColor;
        private Drawable selectedDrawable;
        private Integer selectedSolidColor;
        private Integer selectedStrokeColor;
        private Integer selectedTextColor;
        private Float sizeHeight;
        private Float sizeWidth;
        private Integer solidColor;
        private Integer strokeColor;
        private Float strokeWidth;
        private int textColorCount;
        private Drawable unCheckableDrawable;
        private Integer unCheckableSolidColor;
        private Integer unCheckableStrokeColor;
        private Integer unCheckableTextColor;
        private Drawable unCheckedDrawable;
        private Integer unCheckedSolidColor;
        private Integer unCheckedStrokeColor;
        private Integer unCheckedTextColor;
        private Drawable unEnabledDrawable;
        private Integer unEnabledSolidColor;
        private Integer unEnabledStrokeColor;
        private Integer unEnabledTextColor;
        private Drawable unFocusedActivated;
        private Drawable unFocusedDrawable;
        private Drawable unFocusedHovered;
        private Integer unFocusedSolidColor;
        private Integer unFocusedStrokeColor;
        private Integer unFocusedTextColor;
        private Drawable unPressedDrawable;
        private Integer unPressedSolidColor;
        private Integer unPressedStrokeColor;
        private Integer unPressedTextColor;
        private Drawable unSelectedDrawable;
        private Integer unSelectedSolidColor;
        private Integer unSelectedStrokeColor;
        private Integer unSelectedTextColor;
        private Shape shape = Shape.Rectangle;
        private int gradientAngle = -1;
        private Gradient gradient = Gradient.Linear;
        private boolean useLevel = false;
        private Rect padding = new Rect();
        private boolean hasSetPadding = false;
        private float strokeDashWidth = 0.0f;
        private float strokeDashGap = 0.0f;
        private boolean rippleEnable = false;
        private float alpha = -1.0f;
        private boolean hasSelectDrawable = false;
        private GradientDrawable baseGradientDrawable = null;
        private StateListDrawable baseStateListDrawable = null;

        private ColorStateList getColorStateList() {
            int i2;
            int i3 = this.textColorCount;
            int[][] iArr = new int[i3][];
            int[] iArr2 = new int[i3];
            Integer num = this.checkableTextColor;
            if (num != null) {
                iArr[0] = new int[]{R.attr.state_checkable};
                iArr2[0] = num.intValue();
                i2 = 1;
            } else {
                i2 = 0;
            }
            Integer num2 = this.unCheckableTextColor;
            if (num2 != null) {
                iArr[i2] = new int[]{-16842911};
                iArr2[i2] = num2.intValue();
                i2++;
            }
            Integer num3 = this.checkedTextColor;
            if (num3 != null) {
                iArr[i2] = new int[]{R.attr.state_checked};
                iArr2[i2] = num3.intValue();
                i2++;
            }
            Integer num4 = this.unCheckedTextColor;
            if (num4 != null) {
                iArr[i2] = new int[]{-16842912};
                iArr2[i2] = num4.intValue();
                i2++;
            }
            Integer num5 = this.enabledTextColor;
            if (num5 != null) {
                iArr[i2] = new int[]{R.attr.state_enabled};
                iArr2[i2] = num5.intValue();
                i2++;
            }
            Integer num6 = this.unEnabledTextColor;
            if (num6 != null) {
                iArr[i2] = new int[]{-16842910};
                iArr2[i2] = num6.intValue();
                i2++;
            }
            Integer num7 = this.selectedTextColor;
            if (num7 != null) {
                iArr[i2] = new int[]{R.attr.state_selected};
                iArr2[i2] = num7.intValue();
                i2++;
            }
            Integer num8 = this.unSelectedTextColor;
            if (num8 != null) {
                iArr[i2] = new int[]{-16842913};
                iArr2[i2] = num8.intValue();
                i2++;
            }
            Integer num9 = this.pressedTextColor;
            if (num9 != null) {
                iArr[i2] = new int[]{R.attr.state_pressed};
                iArr2[i2] = num9.intValue();
                i2++;
            }
            Integer num10 = this.unPressedTextColor;
            if (num10 != null) {
                iArr[i2] = new int[]{-16842919};
                iArr2[i2] = num10.intValue();
                i2++;
            }
            Integer num11 = this.focusedTextColor;
            if (num11 != null) {
                iArr[i2] = new int[]{R.attr.state_focused};
                iArr2[i2] = num11.intValue();
                i2++;
            }
            Integer num12 = this.unFocusedTextColor;
            if (num12 != null) {
                iArr[i2] = new int[]{-16842908};
                iArr2[i2] = num12.intValue();
            }
            return new ColorStateList(iArr, iArr2);
        }

        @NonNull
        private GradientDrawable getGradientDrawable() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            int i2;
            Float f2;
            GradientDrawable gradientDrawable = this.baseGradientDrawable;
            if (gradientDrawable == null) {
                gradientDrawable = new GradientDrawable();
            }
            GradientDrawable gradientDrawable2 = gradientDrawable;
            gradientDrawable2.setShape(this.shape.value);
            Float f3 = this.cornersRadius;
            if (f3 != null) {
                gradientDrawable2.setCornerRadius(f3.floatValue());
            }
            if (this.cornersBottomLeftRadius != null && this.cornersBottomRightRadius != null && (f2 = this.cornersTopLeftRadius) != null && this.cornersTopRightRadius != null) {
                gradientDrawable2.setCornerRadii(new float[]{f2.floatValue(), this.cornersTopLeftRadius.floatValue(), this.cornersTopRightRadius.floatValue(), this.cornersTopRightRadius.floatValue(), this.cornersBottomRightRadius.floatValue(), this.cornersBottomRightRadius.floatValue(), this.cornersBottomLeftRadius.floatValue(), this.cornersBottomLeftRadius.floatValue()});
            }
            if (this.gradient == Gradient.Linear && (i2 = this.gradientAngle) != -1) {
                int i3 = i2 % 360;
                this.gradientAngle = i3;
                if (i3 % 45 == 0) {
                    GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                    if (i3 != 0) {
                        if (i3 == 45) {
                            orientation = GradientDrawable.Orientation.BL_TR;
                        } else if (i3 == 90) {
                            orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                        } else if (i3 == 135) {
                            orientation = GradientDrawable.Orientation.BR_TL;
                        } else if (i3 == 180) {
                            orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                        } else if (i3 == 225) {
                            orientation = GradientDrawable.Orientation.TR_BL;
                        } else if (i3 == 270) {
                            orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                        } else if (i3 == 315) {
                            orientation = GradientDrawable.Orientation.TL_BR;
                        }
                    }
                    gradientDrawable2.setOrientation(orientation);
                }
            }
            Float f4 = this.gradientCenterX;
            if (f4 != null && this.gradientCenterY != null) {
                gradientDrawable2.setGradientCenter(f4.floatValue(), this.gradientCenterY.floatValue());
            }
            Integer num = this.gradientStartColor;
            if (num != null && this.gradientEndColor != null) {
                gradientDrawable2.setColors(this.gradientCenterColor != null ? new int[]{num.intValue(), this.gradientCenterColor.intValue(), this.gradientEndColor.intValue()} : new int[]{num.intValue(), this.gradientEndColor.intValue()});
            }
            Float f5 = this.gradientRadius;
            if (f5 != null) {
                gradientDrawable2.setGradientRadius(f5.floatValue());
            }
            gradientDrawable2.setGradientType(this.gradient.value);
            gradientDrawable2.setUseLevel(this.useLevel);
            if (this.hasSetPadding) {
                if (Build.VERSION.SDK_INT >= 29) {
                    Rect rect = this.padding;
                    gradientDrawable2.setPadding(rect.left, rect.top, rect.right, rect.bottom);
                } else {
                    try {
                        Field declaredField = gradientDrawable2.getClass().getDeclaredField("mPadding");
                        declaredField.setAccessible(true);
                        declaredField.set(gradientDrawable2, this.padding);
                    } catch (IllegalAccessException e2) {
                        e2.printStackTrace();
                    } catch (NoSuchFieldException e3) {
                        e3.printStackTrace();
                    }
                }
            }
            Float f6 = this.sizeWidth;
            if (f6 != null && this.sizeHeight != null) {
                gradientDrawable2.setSize(f6.intValue(), this.sizeHeight.intValue());
            }
            Float f7 = this.strokeWidth;
            if (f7 != null && f7.floatValue() > 0.0f) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                if (this.pressedStrokeColor != null && this.unPressedStrokeColor != null) {
                    arrayList.add(Integer.valueOf(R.attr.state_pressed));
                    arrayList.add(-16842919);
                    arrayList2.add(this.pressedStrokeColor);
                    arrayList2.add(this.unPressedStrokeColor);
                }
                if (this.checkableStrokeColor != null && this.unCheckableStrokeColor != null) {
                    arrayList.add(Integer.valueOf(R.attr.state_checkable));
                    arrayList.add(-16842911);
                    arrayList2.add(this.checkableStrokeColor);
                    arrayList2.add(this.unCheckableStrokeColor);
                }
                if (this.checkedStrokeColor != null && this.unCheckedStrokeColor != null) {
                    arrayList.add(Integer.valueOf(R.attr.state_checked));
                    arrayList.add(-16842912);
                    arrayList2.add(this.checkedStrokeColor);
                    arrayList2.add(this.unCheckedStrokeColor);
                }
                if (this.enabledStrokeColor != null && this.unEnabledStrokeColor != null) {
                    arrayList.add(Integer.valueOf(R.attr.state_enabled));
                    arrayList.add(-16842910);
                    arrayList2.add(this.enabledStrokeColor);
                    arrayList2.add(this.unEnabledStrokeColor);
                }
                if (this.selectedStrokeColor != null && this.unSelectedStrokeColor != null) {
                    arrayList.add(Integer.valueOf(R.attr.state_selected));
                    arrayList.add(-16842913);
                    arrayList2.add(this.selectedStrokeColor);
                    arrayList2.add(this.unSelectedStrokeColor);
                }
                if (this.focusedStrokeColor != null && this.unFocusedStrokeColor != null) {
                    arrayList.add(Integer.valueOf(R.attr.state_focused));
                    arrayList.add(-16842908);
                    arrayList2.add(this.focusedStrokeColor);
                    arrayList2.add(this.unFocusedStrokeColor);
                }
                if (arrayList.size() > 0) {
                    int[][] iArr = new int[arrayList.size()][];
                    int[] iArr2 = new int[arrayList.size()];
                    Iterator it = arrayList.iterator();
                    int i4 = 0;
                    while (it.hasNext()) {
                        iArr[i4] = new int[]{((Integer) it.next()).intValue()};
                        iArr2[i4] = ((Integer) arrayList2.get(i4)).intValue();
                        i4++;
                    }
                    gradientDrawable2.setStroke(this.strokeWidth.intValue(), new ColorStateList(iArr, iArr2), this.strokeDashWidth, this.strokeDashGap);
                } else if (this.strokeColor != null) {
                    gradientDrawable2.setStroke(this.strokeWidth.intValue(), this.strokeColor.intValue(), this.strokeDashWidth, this.strokeDashGap);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            if (this.pressedSolidColor != null && this.unPressedSolidColor != null) {
                arrayList3.add(Integer.valueOf(R.attr.state_pressed));
                arrayList3.add(-16842919);
                arrayList4.add(this.pressedSolidColor);
                arrayList4.add(this.unPressedSolidColor);
            }
            if (this.checkableSolidColor != null && this.unCheckableSolidColor != null) {
                arrayList3.add(Integer.valueOf(R.attr.state_checkable));
                arrayList3.add(-16842911);
                arrayList4.add(this.checkableSolidColor);
                arrayList4.add(this.unCheckableSolidColor);
            }
            if (this.checkedSolidColor != null && this.unCheckedSolidColor != null) {
                arrayList3.add(Integer.valueOf(R.attr.state_checked));
                arrayList3.add(-16842912);
                arrayList4.add(this.checkedSolidColor);
                arrayList4.add(this.unCheckedSolidColor);
            }
            if (this.enabledSolidColor != null && this.unEnabledSolidColor != null) {
                arrayList3.add(Integer.valueOf(R.attr.state_enabled));
                arrayList3.add(-16842910);
                arrayList4.add(this.enabledSolidColor);
                arrayList4.add(this.unEnabledSolidColor);
            }
            if (this.selectedSolidColor != null && this.unSelectedSolidColor != null) {
                arrayList3.add(Integer.valueOf(R.attr.state_selected));
                arrayList3.add(-16842913);
                arrayList4.add(this.selectedSolidColor);
                arrayList4.add(this.unSelectedSolidColor);
            }
            if (this.focusedSolidColor != null && this.unFocusedSolidColor != null) {
                arrayList3.add(Integer.valueOf(R.attr.state_focused));
                arrayList3.add(-16842908);
                arrayList4.add(this.focusedSolidColor);
                arrayList4.add(this.unFocusedSolidColor);
            }
            if (arrayList3.size() > 0) {
                int[][] iArr3 = new int[arrayList3.size()][];
                int[] iArr4 = new int[arrayList3.size()];
                Iterator it2 = arrayList3.iterator();
                int i5 = 0;
                while (it2.hasNext()) {
                    iArr3[i5] = new int[]{((Integer) it2.next()).intValue()};
                    iArr4[i5] = ((Integer) arrayList4.get(i5)).intValue();
                    i5++;
                }
                gradientDrawable2.setColor(new ColorStateList(iArr3, iArr4));
            } else {
                Integer num2 = this.solidColor;
                if (num2 != null) {
                    gradientDrawable2.setColor(num2.intValue());
                }
            }
            return gradientDrawable2;
        }

        private StateListDrawable getStateListDrawable() {
            StateListDrawable stateListDrawable = this.baseStateListDrawable;
            if (this.checkableDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{R.attr.state_checkable}, this.checkableDrawable);
            }
            if (this.unCheckableDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{-16842911}, this.unCheckableDrawable);
            }
            if (this.checkedDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{R.attr.state_checked}, this.checkedDrawable);
            }
            if (this.unCheckedDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{-16842912}, this.unCheckedDrawable);
            }
            if (this.enabledDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{R.attr.state_enabled}, this.enabledDrawable);
            }
            if (this.unEnabledDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{-16842910}, this.unEnabledDrawable);
            }
            if (this.selectedDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{R.attr.state_selected}, this.selectedDrawable);
            }
            if (this.unSelectedDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{-16842913}, this.unSelectedDrawable);
            }
            if (this.pressedDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{R.attr.state_pressed}, this.pressedDrawable);
            }
            if (this.unPressedDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{-16842919}, this.unPressedDrawable);
            }
            if (this.focusedDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{R.attr.state_focused}, this.focusedDrawable);
            }
            if (this.unFocusedDrawable != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{-16842908}, this.unFocusedDrawable);
            }
            if (this.focusedHovered != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{R.attr.state_hovered}, this.focusedHovered);
            }
            if (this.unFocusedHovered != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{-16843623}, this.unFocusedHovered);
            }
            if (this.focusedActivated != null) {
                stateListDrawable = getStateListDrawable(stateListDrawable);
                stateListDrawable.addState(new int[]{R.attr.state_activated}, this.focusedActivated);
            }
            if (this.unFocusedActivated == null) {
                return stateListDrawable;
            }
            StateListDrawable stateListDrawable2 = getStateListDrawable(stateListDrawable);
            stateListDrawable2.addState(new int[]{-16843518}, this.unFocusedActivated);
            return stateListDrawable2;
        }

        public Drawable build() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            StateListDrawable stateListDrawable;
            GradientDrawable gradientDrawable = null;
            if (this.hasSelectDrawable) {
                stateListDrawable = getStateListDrawable();
            } else {
                gradientDrawable = getGradientDrawable();
                stateListDrawable = null;
            }
            if (this.rippleEnable && this.rippleColor != null) {
                if (stateListDrawable != null) {
                    gradientDrawable = stateListDrawable;
                }
                return new RippleDrawable(ColorStateList.valueOf(this.rippleColor.intValue()), gradientDrawable, gradientDrawable);
            }
            if (gradientDrawable == null) {
                gradientDrawable = stateListDrawable;
            }
            float f2 = this.alpha;
            if (f2 != -1.0f) {
                if (f2 >= 1.0f) {
                    this.alpha = 255.0f;
                } else if (f2 <= 0.0f) {
                    this.alpha = 0.0f;
                } else {
                    this.alpha = f2 * 255.0f;
                }
                gradientDrawable.setAlpha((int) this.alpha);
            }
            return gradientDrawable;
        }

        public ColorStateList buildTextColor() {
            if (this.textColorCount > 0) {
                return getColorStateList();
            }
            return null;
        }

        public Builder setBaseGradientDrawable(GradientDrawable gradientDrawable) {
            this.baseGradientDrawable = gradientDrawable;
            return this;
        }

        public Builder setBaseStateListDrawable(StateListDrawable stateListDrawable) {
            this.baseStateListDrawable = stateListDrawable;
            return this;
        }

        public Builder setCheckableDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.checkableDrawable = drawable;
            return this;
        }

        public Builder setCheckableSolidColor(int i2, int i3) {
            this.checkableSolidColor = Integer.valueOf(i2);
            this.unCheckableSolidColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setCheckableStrokeColor(int i2, int i3) {
            this.checkableStrokeColor = Integer.valueOf(i2);
            this.unCheckableStrokeColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setCheckableTextColor(int i2) {
            this.checkableTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setCheckedDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.checkedDrawable = drawable;
            return this;
        }

        public Builder setCheckedSolidColor(int i2, int i3) {
            this.checkedSolidColor = Integer.valueOf(i2);
            this.unCheckedSolidColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setCheckedStrokeColor(int i2, int i3) {
            this.checkedStrokeColor = Integer.valueOf(i2);
            this.unCheckedStrokeColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setCheckedTextColor(int i2) {
            this.checkedTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setCornersRadius(float f2) {
            this.cornersRadius = Float.valueOf(f2);
            return this;
        }

        public Builder setEnabledDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.enabledDrawable = drawable;
            return this;
        }

        public Builder setEnabledSolidColor(int i2, int i3) {
            this.enabledSolidColor = Integer.valueOf(i2);
            this.unEnabledSolidColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setEnabledStrokeColor(int i2, int i3) {
            this.enabledStrokeColor = Integer.valueOf(i2);
            this.unEnabledStrokeColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setEnabledTextColor(int i2) {
            this.enabledTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setFocusedActivated(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.focusedActivated = drawable;
            return this;
        }

        public Builder setFocusedDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.focusedDrawable = drawable;
            return this;
        }

        public Builder setFocusedHovered(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.focusedHovered = drawable;
            return this;
        }

        public Builder setFocusedSolidColor(int i2, int i3) {
            this.focusedSolidColor = Integer.valueOf(i2);
            this.unFocusedSolidColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setFocusedStrokeColor(int i2, int i3) {
            this.focusedStrokeColor = Integer.valueOf(i2);
            this.unFocusedStrokeColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setFocusedTextColor(int i2) {
            this.focusedTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setGradient(Gradient gradient) {
            this.gradient = gradient;
            return this;
        }

        @TargetApi(16)
        public Builder setGradientAngle(int i2) {
            this.gradientAngle = i2;
            return this;
        }

        public Builder setGradientCenterXY(float f2, float f3) {
            this.gradientCenterX = Float.valueOf(f2);
            this.gradientCenterY = Float.valueOf(f3);
            return this;
        }

        @TargetApi(16)
        public Builder setGradientColor(int i2, int i3) {
            this.gradientStartColor = Integer.valueOf(i2);
            this.gradientEndColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setGradientRadius(float f2) {
            this.gradientRadius = Float.valueOf(f2);
            return this;
        }

        public Builder setPadding(float f2, float f3, float f4, float f5) {
            this.hasSetPadding = true;
            Rect rect = this.padding;
            rect.left = (int) f2;
            rect.top = (int) f3;
            rect.right = (int) f4;
            rect.bottom = (int) f5;
            return this;
        }

        public Builder setPressedDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.pressedDrawable = drawable;
            return this;
        }

        public Builder setPressedSolidColor(int i2, int i3) {
            this.pressedSolidColor = Integer.valueOf(i2);
            this.unPressedSolidColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setPressedStrokeColor(int i2, int i3) {
            this.pressedStrokeColor = Integer.valueOf(i2);
            this.unPressedStrokeColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setPressedTextColor(int i2) {
            this.pressedTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setRipple(boolean z2, int i2) {
            this.rippleEnable = z2;
            this.rippleColor = Integer.valueOf(i2);
            return this;
        }

        public Builder setSelectedDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.selectedDrawable = drawable;
            return this;
        }

        public Builder setSelectedSolidColor(int i2, int i3) {
            this.selectedSolidColor = Integer.valueOf(i2);
            this.unSelectedSolidColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setSelectedStrokeColor(int i2, int i3) {
            this.selectedStrokeColor = Integer.valueOf(i2);
            this.unSelectedStrokeColor = Integer.valueOf(i3);
            return this;
        }

        public Builder setSelectedTextColor(int i2) {
            this.selectedTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setShape(Shape shape) {
            this.shape = shape;
            return this;
        }

        public Builder setShapeAlpha(float f2) {
            this.alpha = f2;
            return this;
        }

        public Builder setSizeHeight(float f2) {
            this.sizeHeight = Float.valueOf(f2);
            return this;
        }

        public Builder setSizeWidth(float f2) {
            this.sizeWidth = Float.valueOf(f2);
            return this;
        }

        public Builder setSolidColor(int i2) {
            this.solidColor = Integer.valueOf(i2);
            return this;
        }

        public Builder setStrokeColor(int i2) {
            this.strokeColor = Integer.valueOf(i2);
            return this;
        }

        public Builder setStrokeDashGap(float f2) {
            this.strokeDashGap = f2;
            return this;
        }

        public Builder setStrokeDashWidth(float f2) {
            this.strokeDashWidth = f2;
            return this;
        }

        public Builder setStrokeWidth(float f2) {
            this.strokeWidth = Float.valueOf(f2);
            return this;
        }

        public Builder setUnCheckableDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.unCheckableDrawable = drawable;
            return this;
        }

        public Builder setUnCheckableTextColor(int i2) {
            this.unCheckableTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setUnCheckedDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.unCheckedDrawable = drawable;
            return this;
        }

        public Builder setUnCheckedTextColor(int i2) {
            this.unCheckedTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setUnEnabledDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.unEnabledDrawable = drawable;
            return this;
        }

        public Builder setUnEnabledTextColor(int i2) {
            this.unEnabledTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setUnFocusedActivated(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.unFocusedActivated = drawable;
            return this;
        }

        public Builder setUnFocusedDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.unFocusedDrawable = drawable;
            return this;
        }

        public Builder setUnFocusedHovered(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.unFocusedHovered = drawable;
            return this;
        }

        public Builder setUnFocusedTextColor(int i2) {
            this.unFocusedTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setUnPressedDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.unPressedDrawable = drawable;
            return this;
        }

        public Builder setUnPressedTextColor(int i2) {
            this.unPressedTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setUnSelectedDrawable(Drawable drawable) {
            this.hasSelectDrawable = true;
            this.unSelectedDrawable = drawable;
            return this;
        }

        public Builder setUnSelectedTextColor(int i2) {
            this.unSelectedTextColor = Integer.valueOf(i2);
            this.textColorCount++;
            return this;
        }

        public Builder setUseLevel(boolean z2) {
            this.useLevel = z2;
            return this;
        }

        public Builder setCornersRadius(float f2, float f3, float f4, float f5) {
            this.cornersBottomLeftRadius = Float.valueOf(f2);
            this.cornersBottomRightRadius = Float.valueOf(f3);
            this.cornersTopLeftRadius = Float.valueOf(f4);
            this.cornersTopRightRadius = Float.valueOf(f5);
            return this;
        }

        @TargetApi(16)
        public Builder setGradientColor(int i2, int i3, int i4) {
            this.gradientStartColor = Integer.valueOf(i2);
            this.gradientCenterColor = Integer.valueOf(i3);
            this.gradientEndColor = Integer.valueOf(i4);
            return this;
        }

        public StateListDrawable getStateListDrawable(StateListDrawable stateListDrawable) {
            return stateListDrawable == null ? new StateListDrawable() : stateListDrawable;
        }
    }

    public enum DrawablePosition {
        Left,
        Right,
        Top,
        Bottom
    }

    public enum Gradient {
        Linear(0),
        Radial(1),
        Sweep(2);

        int value;

        Gradient(int i2) {
            this.value = i2;
        }
    }

    public enum Shape {
        Rectangle(0),
        Oval(1),
        Line(2),
        Ring(3);

        int value;

        Shape(int i2) {
            this.value = i2;
        }
    }

    public static void setDrawable(Drawable drawable, View view, DrawablePosition drawablePosition) {
        if (!(view instanceof TextView)) {
            view.setBackground(drawable);
            return;
        }
        if (drawable == null) {
            TextView textView = (TextView) view;
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            if (drawablePosition == DrawablePosition.Left) {
                compoundDrawables[0] = null;
            } else if (drawablePosition == DrawablePosition.Top) {
                compoundDrawables[1] = null;
            } else if (drawablePosition == DrawablePosition.Right) {
                compoundDrawables[2] = null;
            } else if (drawablePosition == DrawablePosition.Bottom) {
                compoundDrawables[3] = null;
            }
            textView.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
            return;
        }
        if (drawablePosition == DrawablePosition.Left) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(drawable, null, null, null);
            return;
        }
        if (drawablePosition == DrawablePosition.Top) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(null, drawable, null, null);
        } else if (drawablePosition == DrawablePosition.Right) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(null, null, drawable, null);
        } else if (drawablePosition != DrawablePosition.Bottom) {
            view.setBackground(drawable);
        } else {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(null, null, null, drawable);
        }
    }
}
