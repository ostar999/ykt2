package com.bigkoo.pickerview.view;

import android.graphics.Typeface;
import android.view.View;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import java.util.List;

/* loaded from: classes2.dex */
public class WheelOptions<T> {
    private boolean isRestoreItem;
    private boolean linkage = true;
    private List<T> mOptions1Items;
    private List<List<T>> mOptions2Items;
    private List<List<List<T>>> mOptions3Items;
    private OnOptionsSelectChangeListener optionsSelectChangeListener;
    private View view;
    private OnItemSelectedListener wheelListener_option1;
    private OnItemSelectedListener wheelListener_option2;
    private WheelView wv_option1;
    private WheelView wv_option2;
    private WheelView wv_option3;

    public WheelOptions(View view, boolean z2) {
        this.isRestoreItem = z2;
        this.view = view;
        this.wv_option1 = (WheelView) view.findViewById(R.id.options1);
        this.wv_option2 = (WheelView) view.findViewById(R.id.options2);
        this.wv_option3 = (WheelView) view.findViewById(R.id.options3);
    }

    private void itemSelected(int i2, int i3, int i4) {
        if (this.mOptions1Items != null) {
            this.wv_option1.setCurrentItem(i2);
        }
        List<List<T>> list = this.mOptions2Items;
        if (list != null) {
            this.wv_option2.setAdapter(new ArrayWheelAdapter(list.get(i2)));
            this.wv_option2.setCurrentItem(i3);
        }
        List<List<List<T>>> list2 = this.mOptions3Items;
        if (list2 != null) {
            this.wv_option3.setAdapter(new ArrayWheelAdapter(list2.get(i2).get(i3)));
            this.wv_option3.setCurrentItem(i4);
        }
    }

    private void setLineSpacingMultiplier() {
    }

    public int[] getCurrentItems() {
        int[] iArr = new int[3];
        iArr[0] = this.wv_option1.getCurrentItem();
        List<List<T>> list = this.mOptions2Items;
        if (list == null || list.size() <= 0) {
            iArr[1] = this.wv_option2.getCurrentItem();
        } else {
            iArr[1] = this.wv_option2.getCurrentItem() > this.mOptions2Items.get(iArr[0]).size() - 1 ? 0 : this.wv_option2.getCurrentItem();
        }
        List<List<List<T>>> list2 = this.mOptions3Items;
        if (list2 == null || list2.size() <= 0) {
            iArr[2] = this.wv_option3.getCurrentItem();
        } else {
            iArr[2] = this.wv_option3.getCurrentItem() <= this.mOptions3Items.get(iArr[0]).get(iArr[1]).size() - 1 ? this.wv_option3.getCurrentItem() : 0;
        }
        return iArr;
    }

    public View getView() {
        return this.view;
    }

    public void isCenterLabel(boolean z2) {
        this.wv_option1.isCenterLabel(z2);
        this.wv_option2.isCenterLabel(z2);
        this.wv_option3.isCenterLabel(z2);
    }

    public void setAlphaGradient(boolean z2) {
        this.wv_option1.setAlphaGradient(z2);
        this.wv_option2.setAlphaGradient(z2);
        this.wv_option3.setAlphaGradient(z2);
    }

    public void setCurrentItems(int i2, int i3, int i4) {
        if (this.linkage) {
            itemSelected(i2, i3, i4);
            return;
        }
        this.wv_option1.setCurrentItem(i2);
        this.wv_option2.setCurrentItem(i3);
        this.wv_option3.setCurrentItem(i4);
    }

    public void setCyclic(boolean z2) {
        this.wv_option1.setCyclic(z2);
        this.wv_option2.setCyclic(z2);
        this.wv_option3.setCyclic(z2);
    }

    public void setDividerColor(int i2) {
        this.wv_option1.setDividerColor(i2);
        this.wv_option2.setDividerColor(i2);
        this.wv_option3.setDividerColor(i2);
    }

    public void setDividerType(WheelView.DividerType dividerType) {
        this.wv_option1.setDividerType(dividerType);
        this.wv_option2.setDividerType(dividerType);
        this.wv_option3.setDividerType(dividerType);
    }

    public void setItemsVisible(int i2) {
        this.wv_option1.setItemsVisibleCount(i2);
        this.wv_option2.setItemsVisibleCount(i2);
        this.wv_option3.setItemsVisibleCount(i2);
    }

    public void setLabels(String str, String str2, String str3) {
        if (str != null) {
            this.wv_option1.setLabel(str);
        }
        if (str2 != null) {
            this.wv_option2.setLabel(str2);
        }
        if (str3 != null) {
            this.wv_option3.setLabel(str3);
        }
    }

    public void setLineSpacingMultiplier(float f2) {
        this.wv_option1.setLineSpacingMultiplier(f2);
        this.wv_option2.setLineSpacingMultiplier(f2);
        this.wv_option3.setLineSpacingMultiplier(f2);
    }

    public void setLinkage(boolean z2) {
        this.linkage = z2;
    }

    public void setNPicker(List<T> list, List<T> list2, List<T> list3) {
        this.wv_option1.setAdapter(new ArrayWheelAdapter(list));
        this.wv_option1.setCurrentItem(0);
        if (list2 != null) {
            this.wv_option2.setAdapter(new ArrayWheelAdapter(list2));
        }
        WheelView wheelView = this.wv_option2;
        wheelView.setCurrentItem(wheelView.getCurrentItem());
        if (list3 != null) {
            this.wv_option3.setAdapter(new ArrayWheelAdapter(list3));
        }
        WheelView wheelView2 = this.wv_option3;
        wheelView2.setCurrentItem(wheelView2.getCurrentItem());
        this.wv_option1.setIsOptions(true);
        this.wv_option2.setIsOptions(true);
        this.wv_option3.setIsOptions(true);
        if (this.optionsSelectChangeListener != null) {
            this.wv_option1.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelOptions.4
                @Override // com.contrarywind.listener.OnItemSelectedListener
                public void onItemSelected(int i2) {
                    WheelOptions.this.optionsSelectChangeListener.onOptionsSelectChanged(i2, WheelOptions.this.wv_option2.getCurrentItem(), WheelOptions.this.wv_option3.getCurrentItem());
                }
            });
        }
        if (list2 == null) {
            this.wv_option2.setVisibility(8);
        } else {
            this.wv_option2.setVisibility(0);
            if (this.optionsSelectChangeListener != null) {
                this.wv_option2.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelOptions.5
                    @Override // com.contrarywind.listener.OnItemSelectedListener
                    public void onItemSelected(int i2) {
                        WheelOptions.this.optionsSelectChangeListener.onOptionsSelectChanged(WheelOptions.this.wv_option1.getCurrentItem(), i2, WheelOptions.this.wv_option3.getCurrentItem());
                    }
                });
            }
        }
        if (list3 == null) {
            this.wv_option3.setVisibility(8);
            return;
        }
        this.wv_option3.setVisibility(0);
        if (this.optionsSelectChangeListener != null) {
            this.wv_option3.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelOptions.6
                @Override // com.contrarywind.listener.OnItemSelectedListener
                public void onItemSelected(int i2) {
                    WheelOptions.this.optionsSelectChangeListener.onOptionsSelectChanged(WheelOptions.this.wv_option1.getCurrentItem(), WheelOptions.this.wv_option2.getCurrentItem(), i2);
                }
            });
        }
    }

    public void setOptionsSelectChangeListener(OnOptionsSelectChangeListener onOptionsSelectChangeListener) {
        this.optionsSelectChangeListener = onOptionsSelectChangeListener;
    }

    public void setPicker(List<T> list, List<List<T>> list2, List<List<List<T>>> list3) {
        this.mOptions1Items = list;
        this.mOptions2Items = list2;
        this.mOptions3Items = list3;
        this.wv_option1.setAdapter(new ArrayWheelAdapter(list));
        this.wv_option1.setCurrentItem(0);
        List<List<T>> list4 = this.mOptions2Items;
        if (list4 != null) {
            this.wv_option2.setAdapter(new ArrayWheelAdapter(list4.get(0)));
        }
        WheelView wheelView = this.wv_option2;
        wheelView.setCurrentItem(wheelView.getCurrentItem());
        List<List<List<T>>> list5 = this.mOptions3Items;
        if (list5 != null) {
            this.wv_option3.setAdapter(new ArrayWheelAdapter(list5.get(0).get(0)));
        }
        WheelView wheelView2 = this.wv_option3;
        wheelView2.setCurrentItem(wheelView2.getCurrentItem());
        this.wv_option1.setIsOptions(true);
        this.wv_option2.setIsOptions(true);
        this.wv_option3.setIsOptions(true);
        if (this.mOptions2Items == null) {
            this.wv_option2.setVisibility(8);
        } else {
            this.wv_option2.setVisibility(0);
        }
        if (this.mOptions3Items == null) {
            this.wv_option3.setVisibility(8);
        } else {
            this.wv_option3.setVisibility(0);
        }
        this.wheelListener_option1 = new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelOptions.1
            @Override // com.contrarywind.listener.OnItemSelectedListener
            public void onItemSelected(int i2) {
                int currentItem;
                if (WheelOptions.this.mOptions2Items == null) {
                    if (WheelOptions.this.optionsSelectChangeListener != null) {
                        WheelOptions.this.optionsSelectChangeListener.onOptionsSelectChanged(WheelOptions.this.wv_option1.getCurrentItem(), 0, 0);
                        return;
                    }
                    return;
                }
                if (WheelOptions.this.isRestoreItem) {
                    currentItem = 0;
                } else {
                    currentItem = WheelOptions.this.wv_option2.getCurrentItem();
                    if (currentItem >= ((List) WheelOptions.this.mOptions2Items.get(i2)).size() - 1) {
                        currentItem = ((List) WheelOptions.this.mOptions2Items.get(i2)).size() - 1;
                    }
                }
                WheelOptions.this.wv_option2.setAdapter(new ArrayWheelAdapter((List) WheelOptions.this.mOptions2Items.get(i2)));
                WheelOptions.this.wv_option2.setCurrentItem(currentItem);
                if (WheelOptions.this.mOptions3Items != null) {
                    WheelOptions.this.wheelListener_option2.onItemSelected(currentItem);
                } else if (WheelOptions.this.optionsSelectChangeListener != null) {
                    WheelOptions.this.optionsSelectChangeListener.onOptionsSelectChanged(i2, currentItem, 0);
                }
            }
        };
        this.wheelListener_option2 = new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelOptions.2
            @Override // com.contrarywind.listener.OnItemSelectedListener
            public void onItemSelected(int i2) {
                int size = 0;
                if (WheelOptions.this.mOptions3Items == null) {
                    if (WheelOptions.this.optionsSelectChangeListener != null) {
                        WheelOptions.this.optionsSelectChangeListener.onOptionsSelectChanged(WheelOptions.this.wv_option1.getCurrentItem(), i2, 0);
                        return;
                    }
                    return;
                }
                int currentItem = WheelOptions.this.wv_option1.getCurrentItem();
                if (currentItem >= WheelOptions.this.mOptions3Items.size() - 1) {
                    currentItem = WheelOptions.this.mOptions3Items.size() - 1;
                }
                if (i2 >= ((List) WheelOptions.this.mOptions2Items.get(currentItem)).size() - 1) {
                    i2 = ((List) WheelOptions.this.mOptions2Items.get(currentItem)).size() - 1;
                }
                if (!WheelOptions.this.isRestoreItem) {
                    size = WheelOptions.this.wv_option3.getCurrentItem() >= ((List) ((List) WheelOptions.this.mOptions3Items.get(currentItem)).get(i2)).size() + (-1) ? ((List) ((List) WheelOptions.this.mOptions3Items.get(currentItem)).get(i2)).size() - 1 : WheelOptions.this.wv_option3.getCurrentItem();
                }
                WheelOptions.this.wv_option3.setAdapter(new ArrayWheelAdapter((List) ((List) WheelOptions.this.mOptions3Items.get(WheelOptions.this.wv_option1.getCurrentItem())).get(i2)));
                WheelOptions.this.wv_option3.setCurrentItem(size);
                if (WheelOptions.this.optionsSelectChangeListener != null) {
                    WheelOptions.this.optionsSelectChangeListener.onOptionsSelectChanged(WheelOptions.this.wv_option1.getCurrentItem(), i2, size);
                }
            }
        };
        if (list != null && this.linkage) {
            this.wv_option1.setOnItemSelectedListener(this.wheelListener_option1);
        }
        if (list2 != null && this.linkage) {
            this.wv_option2.setOnItemSelectedListener(this.wheelListener_option2);
        }
        if (list3 == null || !this.linkage || this.optionsSelectChangeListener == null) {
            return;
        }
        this.wv_option3.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelOptions.3
            @Override // com.contrarywind.listener.OnItemSelectedListener
            public void onItemSelected(int i2) {
                WheelOptions.this.optionsSelectChangeListener.onOptionsSelectChanged(WheelOptions.this.wv_option1.getCurrentItem(), WheelOptions.this.wv_option2.getCurrentItem(), i2);
            }
        });
    }

    public void setTextColorCenter(int i2) {
        this.wv_option1.setTextColorCenter(i2);
        this.wv_option2.setTextColorCenter(i2);
        this.wv_option3.setTextColorCenter(i2);
    }

    public void setTextColorOut(int i2) {
        this.wv_option1.setTextColorOut(i2);
        this.wv_option2.setTextColorOut(i2);
        this.wv_option3.setTextColorOut(i2);
    }

    public void setTextContentSize(int i2) {
        float f2 = i2;
        this.wv_option1.setTextSize(f2);
        this.wv_option2.setTextSize(f2);
        this.wv_option3.setTextSize(f2);
    }

    public void setTextXOffset(int i2, int i3, int i4) {
        this.wv_option1.setTextXOffset(i2);
        this.wv_option2.setTextXOffset(i3);
        this.wv_option3.setTextXOffset(i4);
    }

    public void setTypeface(Typeface typeface) {
        this.wv_option1.setTypeface(typeface);
        this.wv_option2.setTypeface(typeface);
        this.wv_option3.setTypeface(typeface);
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setCyclic(boolean z2, boolean z3, boolean z4) {
        this.wv_option1.setCyclic(z2);
        this.wv_option2.setCyclic(z3);
        this.wv_option3.setCyclic(z4);
    }
}
