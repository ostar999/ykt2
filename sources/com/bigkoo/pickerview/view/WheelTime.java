package com.bigkoo.pickerview.view;

import android.view.View;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.bigkoo.pickerview.listener.ISelectTimeCallback;
import com.bigkoo.pickerview.utils.ChinaDate;
import com.bigkoo.pickerview.utils.LunarCalendar;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.tencent.connect.common.Constants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/* loaded from: classes2.dex */
public class WheelTime {
    private static final int DEFAULT_END_DAY = 31;
    private static final int DEFAULT_END_MONTH = 12;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_START_MONTH = 1;
    private static final int DEFAULT_START_YEAR = 1900;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int currentYear;
    private int gravity;
    private ISelectTimeCallback mSelectChangeCallback;
    private int textSize;
    private boolean[] type;
    private View view;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_minutes;
    private WheelView wv_month;
    private WheelView wv_seconds;
    private WheelView wv_year;
    private int startYear = 1900;
    private int endYear = 2100;
    private int startMonth = 1;
    private int endMonth = 12;
    private int startDay = 1;
    private int endDay = 31;
    private boolean isLunarCalendar = false;

    public WheelTime(View view, boolean[] zArr, int i2, int i3) {
        this.view = view;
        this.type = zArr;
        this.gravity = i2;
        this.textSize = i3;
    }

    private String getLunarTime() {
        int currentItem;
        boolean z2;
        StringBuilder sb = new StringBuilder();
        int currentItem2 = this.wv_year.getCurrentItem() + this.startYear;
        if (ChinaDate.leapMonth(currentItem2) == 0 || (this.wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(currentItem2) <= 0) {
            int currentItem3 = this.wv_month.getCurrentItem();
            currentItem = currentItem3 + 1;
            z2 = false;
            int[] iArrLunarToSolar = LunarCalendar.lunarToSolar(currentItem2, currentItem, this.wv_day.getCurrentItem() + 1, z2);
            sb.append(iArrLunarToSolar[0]);
            sb.append("-");
            sb.append(iArrLunarToSolar[1]);
            sb.append("-");
            sb.append(iArrLunarToSolar[2]);
            sb.append(" ");
            sb.append(this.wv_hours.getCurrentItem());
            sb.append(":");
            sb.append(this.wv_minutes.getCurrentItem());
            sb.append(":");
            sb.append(this.wv_seconds.getCurrentItem());
            return sb.toString();
        }
        if ((this.wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(currentItem2) == 1) {
            currentItem = this.wv_month.getCurrentItem();
            z2 = true;
            int[] iArrLunarToSolar2 = LunarCalendar.lunarToSolar(currentItem2, currentItem, this.wv_day.getCurrentItem() + 1, z2);
            sb.append(iArrLunarToSolar2[0]);
            sb.append("-");
            sb.append(iArrLunarToSolar2[1]);
            sb.append("-");
            sb.append(iArrLunarToSolar2[2]);
            sb.append(" ");
            sb.append(this.wv_hours.getCurrentItem());
            sb.append(":");
            sb.append(this.wv_minutes.getCurrentItem());
            sb.append(":");
            sb.append(this.wv_seconds.getCurrentItem());
            return sb.toString();
        }
        currentItem = this.wv_month.getCurrentItem();
        z2 = false;
        int[] iArrLunarToSolar22 = LunarCalendar.lunarToSolar(currentItem2, currentItem, this.wv_day.getCurrentItem() + 1, z2);
        sb.append(iArrLunarToSolar22[0]);
        sb.append("-");
        sb.append(iArrLunarToSolar22[1]);
        sb.append("-");
        sb.append(iArrLunarToSolar22[2]);
        sb.append(" ");
        sb.append(this.wv_hours.getCurrentItem());
        sb.append(":");
        sb.append(this.wv_minutes.getCurrentItem());
        sb.append(":");
        sb.append(this.wv_seconds.getCurrentItem());
        return sb.toString();
    }

    private void setChangedListener(WheelView wheelView) {
        if (this.mSelectChangeCallback != null) {
            wheelView.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelTime.5
                @Override // com.contrarywind.listener.OnItemSelectedListener
                public void onItemSelected(int i2) {
                    WheelTime.this.mSelectChangeCallback.onTimeSelectChanged();
                }
            });
        }
    }

    private void setContentTextSize() {
        this.wv_day.setTextSize(this.textSize);
        this.wv_month.setTextSize(this.textSize);
        this.wv_year.setTextSize(this.textSize);
        this.wv_hours.setTextSize(this.textSize);
        this.wv_minutes.setTextSize(this.textSize);
        this.wv_seconds.setTextSize(this.textSize);
    }

    private void setLunar(int i2, int i3, int i4, boolean z2, int i5, int i6, int i7) {
        WheelView wheelView = (WheelView) this.view.findViewById(R.id.year);
        this.wv_year = wheelView;
        wheelView.setAdapter(new ArrayWheelAdapter(ChinaDate.getYears(this.startYear, this.endYear)));
        this.wv_year.setLabel("");
        this.wv_year.setCurrentItem(i2 - this.startYear);
        this.wv_year.setGravity(this.gravity);
        WheelView wheelView2 = (WheelView) this.view.findViewById(R.id.month);
        this.wv_month = wheelView2;
        wheelView2.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(i2)));
        this.wv_month.setLabel("");
        int iLeapMonth = ChinaDate.leapMonth(i2);
        if (iLeapMonth == 0 || (i3 <= iLeapMonth - 1 && !z2)) {
            this.wv_month.setCurrentItem(i3);
        } else {
            this.wv_month.setCurrentItem(i3 + 1);
        }
        this.wv_month.setGravity(this.gravity);
        this.wv_day = (WheelView) this.view.findViewById(R.id.day);
        if (ChinaDate.leapMonth(i2) == 0) {
            this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(i2, i3))));
        } else {
            this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(i2))));
        }
        this.wv_day.setLabel("");
        this.wv_day.setCurrentItem(i4 - 1);
        this.wv_day.setGravity(this.gravity);
        WheelView wheelView3 = (WheelView) this.view.findViewById(R.id.hour);
        this.wv_hours = wheelView3;
        wheelView3.setAdapter(new NumericWheelAdapter(0, 23));
        this.wv_hours.setCurrentItem(i5);
        this.wv_hours.setGravity(this.gravity);
        WheelView wheelView4 = (WheelView) this.view.findViewById(R.id.min);
        this.wv_minutes = wheelView4;
        wheelView4.setAdapter(new NumericWheelAdapter(0, 59));
        this.wv_minutes.setCurrentItem(i6);
        this.wv_minutes.setGravity(this.gravity);
        WheelView wheelView5 = (WheelView) this.view.findViewById(R.id.second);
        this.wv_seconds = wheelView5;
        wheelView5.setAdapter(new NumericWheelAdapter(0, 59));
        this.wv_seconds.setCurrentItem(i6);
        this.wv_seconds.setGravity(this.gravity);
        this.wv_year.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelTime.1
            @Override // com.contrarywind.listener.OnItemSelectedListener
            public void onItemSelected(int i8) {
                int iMonthDays;
                int i9 = i8 + WheelTime.this.startYear;
                WheelTime.this.wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(i9)));
                if (ChinaDate.leapMonth(i9) == 0 || WheelTime.this.wv_month.getCurrentItem() <= ChinaDate.leapMonth(i9) - 1) {
                    WheelTime.this.wv_month.setCurrentItem(WheelTime.this.wv_month.getCurrentItem());
                } else {
                    WheelTime.this.wv_month.setCurrentItem(WheelTime.this.wv_month.getCurrentItem() + 1);
                }
                int currentItem = WheelTime.this.wv_day.getCurrentItem();
                if (ChinaDate.leapMonth(i9) == 0 || WheelTime.this.wv_month.getCurrentItem() <= ChinaDate.leapMonth(i9) - 1) {
                    WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(i9, WheelTime.this.wv_month.getCurrentItem() + 1))));
                    iMonthDays = ChinaDate.monthDays(i9, WheelTime.this.wv_month.getCurrentItem() + 1);
                } else if (WheelTime.this.wv_month.getCurrentItem() == ChinaDate.leapMonth(i9) + 1) {
                    WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(i9))));
                    iMonthDays = ChinaDate.leapDays(i9);
                } else {
                    WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(i9, WheelTime.this.wv_month.getCurrentItem()))));
                    iMonthDays = ChinaDate.monthDays(i9, WheelTime.this.wv_month.getCurrentItem());
                }
                int i10 = iMonthDays - 1;
                if (currentItem > i10) {
                    WheelTime.this.wv_day.setCurrentItem(i10);
                }
                if (WheelTime.this.mSelectChangeCallback != null) {
                    WheelTime.this.mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        this.wv_month.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelTime.2
            @Override // com.contrarywind.listener.OnItemSelectedListener
            public void onItemSelected(int i8) {
                int iMonthDays;
                int currentItem = WheelTime.this.wv_year.getCurrentItem() + WheelTime.this.startYear;
                int currentItem2 = WheelTime.this.wv_day.getCurrentItem();
                if (ChinaDate.leapMonth(currentItem) == 0 || i8 <= ChinaDate.leapMonth(currentItem) - 1) {
                    int i9 = i8 + 1;
                    WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(currentItem, i9))));
                    iMonthDays = ChinaDate.monthDays(currentItem, i9);
                } else if (WheelTime.this.wv_month.getCurrentItem() == ChinaDate.leapMonth(currentItem) + 1) {
                    WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(currentItem))));
                    iMonthDays = ChinaDate.leapDays(currentItem);
                } else {
                    WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(currentItem, i8))));
                    iMonthDays = ChinaDate.monthDays(currentItem, i8);
                }
                int i10 = iMonthDays - 1;
                if (currentItem2 > i10) {
                    WheelTime.this.wv_day.setCurrentItem(i10);
                }
                if (WheelTime.this.mSelectChangeCallback != null) {
                    WheelTime.this.mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        setChangedListener(this.wv_day);
        setChangedListener(this.wv_hours);
        setChangedListener(this.wv_minutes);
        setChangedListener(this.wv_seconds);
        boolean[] zArr = this.type;
        if (zArr.length != 6) {
            throw new RuntimeException("type[] length is not 6");
        }
        this.wv_year.setVisibility(zArr[0] ? 0 : 8);
        this.wv_month.setVisibility(this.type[1] ? 0 : 8);
        this.wv_day.setVisibility(this.type[2] ? 0 : 8);
        this.wv_hours.setVisibility(this.type[3] ? 0 : 8);
        this.wv_minutes.setVisibility(this.type[4] ? 0 : 8);
        this.wv_seconds.setVisibility(this.type[5] ? 0 : 8);
        setContentTextSize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setReDay(int i2, int i3, int i4, int i5, List<String> list, List<String> list2) {
        int currentItem = this.wv_day.getCurrentItem();
        if (list.contains(String.valueOf(i3))) {
            if (i5 > 31) {
                i5 = 31;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(i4, i5));
        } else if (list2.contains(String.valueOf(i3))) {
            if (i5 > 30) {
                i5 = 30;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(i4, i5));
        } else if ((i2 % 4 != 0 || i2 % 100 == 0) && i2 % 400 != 0) {
            if (i5 > 28) {
                i5 = 28;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(i4, i5));
        } else {
            if (i5 > 29) {
                i5 = 29;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(i4, i5));
        }
        if (currentItem > this.wv_day.getAdapter().getItemsCount() - 1) {
            this.wv_day.setCurrentItem(this.wv_day.getAdapter().getItemsCount() - 1);
        }
    }

    private void setSolar(int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8;
        int i9;
        String[] strArr = {"1", "3", "5", "7", Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, Constants.VIA_REPORT_TYPE_SET_AVATAR};
        String[] strArr2 = {"4", Constants.VIA_SHARE_TYPE_INFO, Constants.VIA_SHARE_TYPE_MINI_PROGRAM, Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE};
        final List listAsList = Arrays.asList(strArr);
        final List listAsList2 = Arrays.asList(strArr2);
        this.currentYear = i2;
        WheelView wheelView = (WheelView) this.view.findViewById(R.id.year);
        this.wv_year = wheelView;
        wheelView.setAdapter(new NumericWheelAdapter(this.startYear, this.endYear));
        this.wv_year.setCurrentItem(i2 - this.startYear);
        this.wv_year.setGravity(this.gravity);
        WheelView wheelView2 = (WheelView) this.view.findViewById(R.id.month);
        this.wv_month = wheelView2;
        int i10 = this.startYear;
        int i11 = this.endYear;
        if (i10 == i11) {
            wheelView2.setAdapter(new NumericWheelAdapter(this.startMonth, this.endMonth));
            this.wv_month.setCurrentItem((i3 + 1) - this.startMonth);
        } else if (i2 == i10) {
            wheelView2.setAdapter(new NumericWheelAdapter(this.startMonth, 12));
            this.wv_month.setCurrentItem((i3 + 1) - this.startMonth);
        } else if (i2 == i11) {
            wheelView2.setAdapter(new NumericWheelAdapter(1, this.endMonth));
            this.wv_month.setCurrentItem(i3);
        } else {
            wheelView2.setAdapter(new NumericWheelAdapter(1, 12));
            this.wv_month.setCurrentItem(i3);
        }
        this.wv_month.setGravity(this.gravity);
        this.wv_day = (WheelView) this.view.findViewById(R.id.day);
        boolean z2 = (i2 % 4 == 0 && i2 % 100 != 0) || i2 % 400 == 0;
        int i12 = this.startYear;
        int i13 = this.endYear;
        if (i12 == i13 && this.startMonth == this.endMonth) {
            int i14 = i3 + 1;
            if (listAsList.contains(String.valueOf(i14))) {
                if (this.endDay > 31) {
                    this.endDay = 31;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            } else if (listAsList2.contains(String.valueOf(i14))) {
                if (this.endDay > 30) {
                    this.endDay = 30;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            } else if (z2) {
                if (this.endDay > 29) {
                    this.endDay = 29;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            } else {
                if (this.endDay > 28) {
                    this.endDay = 28;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            }
            this.wv_day.setCurrentItem(i4 - this.startDay);
        } else if (i2 == i12 && (i9 = i3 + 1) == this.startMonth) {
            if (listAsList.contains(String.valueOf(i9))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, 31));
            } else if (listAsList2.contains(String.valueOf(i9))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, 30));
            } else {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, z2 ? 29 : 28));
            }
            this.wv_day.setCurrentItem(i4 - this.startDay);
        } else if (i2 == i13 && (i8 = i3 + 1) == this.endMonth) {
            if (listAsList.contains(String.valueOf(i8))) {
                if (this.endDay > 31) {
                    this.endDay = 31;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            } else if (listAsList2.contains(String.valueOf(i8))) {
                if (this.endDay > 30) {
                    this.endDay = 30;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            } else if (z2) {
                if (this.endDay > 29) {
                    this.endDay = 29;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            } else {
                if (this.endDay > 28) {
                    this.endDay = 28;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            }
            this.wv_day.setCurrentItem(i4 - 1);
        } else {
            int i15 = i3 + 1;
            if (listAsList.contains(String.valueOf(i15))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (listAsList2.contains(String.valueOf(i15))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, z2 ? 29 : 28));
            }
            this.wv_day.setCurrentItem(i4 - 1);
        }
        this.wv_day.setGravity(this.gravity);
        WheelView wheelView3 = (WheelView) this.view.findViewById(R.id.hour);
        this.wv_hours = wheelView3;
        wheelView3.setAdapter(new NumericWheelAdapter(0, 23));
        this.wv_hours.setCurrentItem(i5);
        this.wv_hours.setGravity(this.gravity);
        WheelView wheelView4 = (WheelView) this.view.findViewById(R.id.min);
        this.wv_minutes = wheelView4;
        wheelView4.setAdapter(new NumericWheelAdapter(0, 59));
        this.wv_minutes.setCurrentItem(i6);
        this.wv_minutes.setGravity(this.gravity);
        WheelView wheelView5 = (WheelView) this.view.findViewById(R.id.second);
        this.wv_seconds = wheelView5;
        wheelView5.setAdapter(new NumericWheelAdapter(0, 59));
        this.wv_seconds.setCurrentItem(i7);
        this.wv_seconds.setGravity(this.gravity);
        this.wv_year.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelTime.3
            @Override // com.contrarywind.listener.OnItemSelectedListener
            public void onItemSelected(int i16) {
                int i17 = i16 + WheelTime.this.startYear;
                WheelTime.this.currentYear = i17;
                int currentItem = WheelTime.this.wv_month.getCurrentItem();
                if (WheelTime.this.startYear == WheelTime.this.endYear) {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(WheelTime.this.startMonth, WheelTime.this.endMonth));
                    if (currentItem > WheelTime.this.wv_month.getAdapter().getItemsCount() - 1) {
                        currentItem = WheelTime.this.wv_month.getAdapter().getItemsCount() - 1;
                        WheelTime.this.wv_month.setCurrentItem(currentItem);
                    }
                    int i18 = currentItem + WheelTime.this.startMonth;
                    if (WheelTime.this.startMonth == WheelTime.this.endMonth) {
                        WheelTime wheelTime = WheelTime.this;
                        wheelTime.setReDay(i17, i18, wheelTime.startDay, WheelTime.this.endDay, listAsList, listAsList2);
                    } else if (i18 == WheelTime.this.startMonth) {
                        WheelTime wheelTime2 = WheelTime.this;
                        wheelTime2.setReDay(i17, i18, wheelTime2.startDay, 31, listAsList, listAsList2);
                    } else if (i18 == WheelTime.this.endMonth) {
                        WheelTime wheelTime3 = WheelTime.this;
                        wheelTime3.setReDay(i17, i18, 1, wheelTime3.endDay, listAsList, listAsList2);
                    } else {
                        WheelTime.this.setReDay(i17, i18, 1, 31, listAsList, listAsList2);
                    }
                } else if (i17 == WheelTime.this.startYear) {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(WheelTime.this.startMonth, 12));
                    if (currentItem > WheelTime.this.wv_month.getAdapter().getItemsCount() - 1) {
                        currentItem = WheelTime.this.wv_month.getAdapter().getItemsCount() - 1;
                        WheelTime.this.wv_month.setCurrentItem(currentItem);
                    }
                    int i19 = currentItem + WheelTime.this.startMonth;
                    if (i19 == WheelTime.this.startMonth) {
                        WheelTime wheelTime4 = WheelTime.this;
                        wheelTime4.setReDay(i17, i19, wheelTime4.startDay, 31, listAsList, listAsList2);
                    } else {
                        WheelTime.this.setReDay(i17, i19, 1, 31, listAsList, listAsList2);
                    }
                } else if (i17 == WheelTime.this.endYear) {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(1, WheelTime.this.endMonth));
                    if (currentItem > WheelTime.this.wv_month.getAdapter().getItemsCount() - 1) {
                        currentItem = WheelTime.this.wv_month.getAdapter().getItemsCount() - 1;
                        WheelTime.this.wv_month.setCurrentItem(currentItem);
                    }
                    int i20 = 1 + currentItem;
                    if (i20 == WheelTime.this.endMonth) {
                        WheelTime wheelTime5 = WheelTime.this;
                        wheelTime5.setReDay(i17, i20, 1, wheelTime5.endDay, listAsList, listAsList2);
                    } else {
                        WheelTime.this.setReDay(i17, i20, 1, 31, listAsList, listAsList2);
                    }
                } else {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(1, 12));
                    WheelTime wheelTime6 = WheelTime.this;
                    wheelTime6.setReDay(i17, 1 + wheelTime6.wv_month.getCurrentItem(), 1, 31, listAsList, listAsList2);
                }
                if (WheelTime.this.mSelectChangeCallback != null) {
                    WheelTime.this.mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        this.wv_month.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.bigkoo.pickerview.view.WheelTime.4
            @Override // com.contrarywind.listener.OnItemSelectedListener
            public void onItemSelected(int i16) {
                int i17 = i16 + 1;
                if (WheelTime.this.startYear == WheelTime.this.endYear) {
                    int i18 = (i17 + WheelTime.this.startMonth) - 1;
                    if (WheelTime.this.startMonth == WheelTime.this.endMonth) {
                        WheelTime wheelTime = WheelTime.this;
                        wheelTime.setReDay(wheelTime.currentYear, i18, WheelTime.this.startDay, WheelTime.this.endDay, listAsList, listAsList2);
                    } else if (WheelTime.this.startMonth == i18) {
                        WheelTime wheelTime2 = WheelTime.this;
                        wheelTime2.setReDay(wheelTime2.currentYear, i18, WheelTime.this.startDay, 31, listAsList, listAsList2);
                    } else if (WheelTime.this.endMonth == i18) {
                        WheelTime wheelTime3 = WheelTime.this;
                        wheelTime3.setReDay(wheelTime3.currentYear, i18, 1, WheelTime.this.endDay, listAsList, listAsList2);
                    } else {
                        WheelTime wheelTime4 = WheelTime.this;
                        wheelTime4.setReDay(wheelTime4.currentYear, i18, 1, 31, listAsList, listAsList2);
                    }
                } else if (WheelTime.this.currentYear == WheelTime.this.startYear) {
                    int i19 = (i17 + WheelTime.this.startMonth) - 1;
                    if (i19 == WheelTime.this.startMonth) {
                        WheelTime wheelTime5 = WheelTime.this;
                        wheelTime5.setReDay(wheelTime5.currentYear, i19, WheelTime.this.startDay, 31, listAsList, listAsList2);
                    } else {
                        WheelTime wheelTime6 = WheelTime.this;
                        wheelTime6.setReDay(wheelTime6.currentYear, i19, 1, 31, listAsList, listAsList2);
                    }
                } else if (WheelTime.this.currentYear != WheelTime.this.endYear) {
                    WheelTime wheelTime7 = WheelTime.this;
                    wheelTime7.setReDay(wheelTime7.currentYear, i17, 1, 31, listAsList, listAsList2);
                } else if (i17 == WheelTime.this.endMonth) {
                    WheelTime wheelTime8 = WheelTime.this;
                    wheelTime8.setReDay(wheelTime8.currentYear, WheelTime.this.wv_month.getCurrentItem() + 1, 1, WheelTime.this.endDay, listAsList, listAsList2);
                } else {
                    WheelTime wheelTime9 = WheelTime.this;
                    wheelTime9.setReDay(wheelTime9.currentYear, WheelTime.this.wv_month.getCurrentItem() + 1, 1, 31, listAsList, listAsList2);
                }
                if (WheelTime.this.mSelectChangeCallback != null) {
                    WheelTime.this.mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        setChangedListener(this.wv_day);
        setChangedListener(this.wv_hours);
        setChangedListener(this.wv_minutes);
        setChangedListener(this.wv_seconds);
        boolean[] zArr = this.type;
        if (zArr.length != 6) {
            throw new IllegalArgumentException("type[] length is not 6");
        }
        this.wv_year.setVisibility(zArr[0] ? 0 : 8);
        this.wv_month.setVisibility(this.type[1] ? 0 : 8);
        this.wv_day.setVisibility(this.type[2] ? 0 : 8);
        this.wv_hours.setVisibility(this.type[3] ? 0 : 8);
        this.wv_minutes.setVisibility(this.type[4] ? 0 : 8);
        this.wv_seconds.setVisibility(this.type[5] ? 0 : 8);
        setContentTextSize();
    }

    public int getEndYear() {
        return this.endYear;
    }

    public int getStartYear() {
        return this.startYear;
    }

    public String getTime() {
        if (this.isLunarCalendar) {
            return getLunarTime();
        }
        StringBuilder sb = new StringBuilder();
        if (this.currentYear == this.startYear) {
            int currentItem = this.wv_month.getCurrentItem();
            int i2 = this.startMonth;
            if (currentItem + i2 == i2) {
                sb.append(this.wv_year.getCurrentItem() + this.startYear);
                sb.append("-");
                sb.append(this.wv_month.getCurrentItem() + this.startMonth);
                sb.append("-");
                sb.append(this.wv_day.getCurrentItem() + this.startDay);
                sb.append(" ");
                sb.append(this.wv_hours.getCurrentItem());
                sb.append(":");
                sb.append(this.wv_minutes.getCurrentItem());
                sb.append(":");
                sb.append(this.wv_seconds.getCurrentItem());
            } else {
                sb.append(this.wv_year.getCurrentItem() + this.startYear);
                sb.append("-");
                sb.append(this.wv_month.getCurrentItem() + this.startMonth);
                sb.append("-");
                sb.append(this.wv_day.getCurrentItem() + 1);
                sb.append(" ");
                sb.append(this.wv_hours.getCurrentItem());
                sb.append(":");
                sb.append(this.wv_minutes.getCurrentItem());
                sb.append(":");
                sb.append(this.wv_seconds.getCurrentItem());
            }
        } else {
            sb.append(this.wv_year.getCurrentItem() + this.startYear);
            sb.append("-");
            sb.append(this.wv_month.getCurrentItem() + 1);
            sb.append("-");
            sb.append(this.wv_day.getCurrentItem() + 1);
            sb.append(" ");
            sb.append(this.wv_hours.getCurrentItem());
            sb.append(":");
            sb.append(this.wv_minutes.getCurrentItem());
            sb.append(":");
            sb.append(this.wv_seconds.getCurrentItem());
        }
        return sb.toString();
    }

    public View getView() {
        return this.view;
    }

    public void isCenterLabel(boolean z2) {
        this.wv_day.isCenterLabel(z2);
        this.wv_month.isCenterLabel(z2);
        this.wv_year.isCenterLabel(z2);
        this.wv_hours.isCenterLabel(z2);
        this.wv_minutes.isCenterLabel(z2);
        this.wv_seconds.isCenterLabel(z2);
    }

    public boolean isLunarMode() {
        return this.isLunarCalendar;
    }

    public void setAlphaGradient(boolean z2) {
        this.wv_day.setAlphaGradient(z2);
        this.wv_month.setAlphaGradient(z2);
        this.wv_year.setAlphaGradient(z2);
        this.wv_hours.setAlphaGradient(z2);
        this.wv_minutes.setAlphaGradient(z2);
        this.wv_seconds.setAlphaGradient(z2);
    }

    public void setCyclic(boolean z2) {
        this.wv_year.setCyclic(z2);
        this.wv_month.setCyclic(z2);
        this.wv_day.setCyclic(z2);
        this.wv_hours.setCyclic(z2);
        this.wv_minutes.setCyclic(z2);
        this.wv_seconds.setCyclic(z2);
    }

    public void setDividerColor(int i2) {
        this.wv_day.setDividerColor(i2);
        this.wv_month.setDividerColor(i2);
        this.wv_year.setDividerColor(i2);
        this.wv_hours.setDividerColor(i2);
        this.wv_minutes.setDividerColor(i2);
        this.wv_seconds.setDividerColor(i2);
    }

    public void setDividerType(WheelView.DividerType dividerType) {
        this.wv_day.setDividerType(dividerType);
        this.wv_month.setDividerType(dividerType);
        this.wv_year.setDividerType(dividerType);
        this.wv_hours.setDividerType(dividerType);
        this.wv_minutes.setDividerType(dividerType);
        this.wv_seconds.setDividerType(dividerType);
    }

    public void setEndYear(int i2) {
        this.endYear = i2;
    }

    public void setItemsVisible(int i2) {
        this.wv_day.setItemsVisibleCount(i2);
        this.wv_month.setItemsVisibleCount(i2);
        this.wv_year.setItemsVisibleCount(i2);
        this.wv_hours.setItemsVisibleCount(i2);
        this.wv_minutes.setItemsVisibleCount(i2);
        this.wv_seconds.setItemsVisibleCount(i2);
    }

    public void setLabels(String str, String str2, String str3, String str4, String str5, String str6) {
        if (this.isLunarCalendar) {
            return;
        }
        if (str != null) {
            this.wv_year.setLabel(str);
        } else {
            this.wv_year.setLabel(this.view.getContext().getString(R.string.pickerview_year));
        }
        if (str2 != null) {
            this.wv_month.setLabel(str2);
        } else {
            this.wv_month.setLabel(this.view.getContext().getString(R.string.pickerview_month));
        }
        if (str3 != null) {
            this.wv_day.setLabel(str3);
        } else {
            this.wv_day.setLabel(this.view.getContext().getString(R.string.pickerview_day));
        }
        if (str4 != null) {
            this.wv_hours.setLabel(str4);
        } else {
            this.wv_hours.setLabel(this.view.getContext().getString(R.string.pickerview_hours));
        }
        if (str5 != null) {
            this.wv_minutes.setLabel(str5);
        } else {
            this.wv_minutes.setLabel(this.view.getContext().getString(R.string.pickerview_minutes));
        }
        if (str6 != null) {
            this.wv_seconds.setLabel(str6);
        } else {
            this.wv_seconds.setLabel(this.view.getContext().getString(R.string.pickerview_seconds));
        }
    }

    public void setLineSpacingMultiplier(float f2) {
        this.wv_day.setLineSpacingMultiplier(f2);
        this.wv_month.setLineSpacingMultiplier(f2);
        this.wv_year.setLineSpacingMultiplier(f2);
        this.wv_hours.setLineSpacingMultiplier(f2);
        this.wv_minutes.setLineSpacingMultiplier(f2);
        this.wv_seconds.setLineSpacingMultiplier(f2);
    }

    public void setLunarMode(boolean z2) {
        this.isLunarCalendar = z2;
    }

    public void setPicker(int i2, int i3, int i4) {
        setPicker(i2, i3, i4, 0, 0, 0);
    }

    public void setRangDate(Calendar calendar, Calendar calendar2) {
        if (calendar == null && calendar2 != null) {
            int i2 = calendar2.get(1);
            int i3 = calendar2.get(2) + 1;
            int i4 = calendar2.get(5);
            int i5 = this.startYear;
            if (i2 > i5) {
                this.endYear = i2;
                this.endMonth = i3;
                this.endDay = i4;
                return;
            } else {
                if (i2 == i5) {
                    int i6 = this.startMonth;
                    if (i3 > i6) {
                        this.endYear = i2;
                        this.endMonth = i3;
                        this.endDay = i4;
                        return;
                    } else {
                        if (i3 != i6 || i4 <= this.startDay) {
                            return;
                        }
                        this.endYear = i2;
                        this.endMonth = i3;
                        this.endDay = i4;
                        return;
                    }
                }
                return;
            }
        }
        if (calendar == null || calendar2 != null) {
            if (calendar == null || calendar2 == null) {
                return;
            }
            this.startYear = calendar.get(1);
            this.endYear = calendar2.get(1);
            this.startMonth = calendar.get(2) + 1;
            this.endMonth = calendar2.get(2) + 1;
            this.startDay = calendar.get(5);
            this.endDay = calendar2.get(5);
            return;
        }
        int i7 = calendar.get(1);
        int i8 = calendar.get(2) + 1;
        int i9 = calendar.get(5);
        int i10 = this.endYear;
        if (i7 < i10) {
            this.startMonth = i8;
            this.startDay = i9;
            this.startYear = i7;
        } else if (i7 == i10) {
            int i11 = this.endMonth;
            if (i8 < i11) {
                this.startMonth = i8;
                this.startDay = i9;
                this.startYear = i7;
            } else {
                if (i8 != i11 || i9 >= this.endDay) {
                    return;
                }
                this.startMonth = i8;
                this.startDay = i9;
                this.startYear = i7;
            }
        }
    }

    public void setSelectChangeCallback(ISelectTimeCallback iSelectTimeCallback) {
        this.mSelectChangeCallback = iSelectTimeCallback;
    }

    public void setStartYear(int i2) {
        this.startYear = i2;
    }

    public void setTextColorCenter(int i2) {
        this.wv_day.setTextColorCenter(i2);
        this.wv_month.setTextColorCenter(i2);
        this.wv_year.setTextColorCenter(i2);
        this.wv_hours.setTextColorCenter(i2);
        this.wv_minutes.setTextColorCenter(i2);
        this.wv_seconds.setTextColorCenter(i2);
    }

    public void setTextColorOut(int i2) {
        this.wv_day.setTextColorOut(i2);
        this.wv_month.setTextColorOut(i2);
        this.wv_year.setTextColorOut(i2);
        this.wv_hours.setTextColorOut(i2);
        this.wv_minutes.setTextColorOut(i2);
        this.wv_seconds.setTextColorOut(i2);
    }

    public void setTextXOffset(int i2, int i3, int i4, int i5, int i6, int i7) {
        this.wv_year.setTextXOffset(i2);
        this.wv_month.setTextXOffset(i3);
        this.wv_day.setTextXOffset(i4);
        this.wv_hours.setTextXOffset(i5);
        this.wv_minutes.setTextXOffset(i6);
        this.wv_seconds.setTextXOffset(i7);
    }

    public void setPicker(int i2, int i3, int i4, int i5, int i6, int i7) {
        if (!this.isLunarCalendar) {
            setSolar(i2, i3, i4, i5, i6, i7);
        } else {
            int[] iArrSolarToLunar = LunarCalendar.solarToLunar(i2, i3 + 1, i4);
            setLunar(iArrSolarToLunar[0], iArrSolarToLunar[1] - 1, iArrSolarToLunar[2], iArrSolarToLunar[3] == 1, i5, i6, i7);
        }
    }
}
