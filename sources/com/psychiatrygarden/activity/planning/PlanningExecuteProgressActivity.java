package com.psychiatrygarden.activity.planning;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.planning.bean.MyTitleBean;
import com.psychiatrygarden.activity.planning.bean.MycalendarBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.widget.RoundProgressBar;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* loaded from: classes5.dex */
public class PlanningExecuteProgressActivity extends BaseActivity {
    private final List<MyTitleBean> list_MyTitleBean = new ArrayList();
    View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.planning.a
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f13549c.lambda$new$0(view);
        }
    };
    private PopupWindow popupWindow_select;

    public class CalendarAdapter extends BaseAdapter {
        List<MycalendarBean> list_item;

        public class ViewHoder {
            private TextView tv_data;

            public ViewHoder(View view) {
                this.tv_data = (TextView) view.findViewById(R.id.tv_data);
            }
        }

        public CalendarAdapter(List<MycalendarBean> list_item) {
            new ArrayList();
            this.list_item = list_item;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.list_item.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return this.list_item.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoder viewHoder;
            if (convertView == null) {
                convertView = LayoutInflater.from(PlanningExecuteProgressActivity.this.mContext).inflate(R.layout.adapter_calendar, (ViewGroup) null);
                viewHoder = new ViewHoder(convertView);
                convertView.setTag(viewHoder);
            } else {
                viewHoder = (ViewHoder) convertView.getTag();
            }
            MycalendarBean mycalendarBean = this.list_item.get(position);
            viewHoder.tv_data.setText(mycalendarBean.getDay());
            String flag = mycalendarBean.getFlag();
            flag.hashCode();
            switch (flag) {
                case "0":
                    viewHoder.tv_data.setTextColor(ContextCompat.getColor(PlanningExecuteProgressActivity.this.mContext, R.color.black));
                    return convertView;
                case "1":
                    viewHoder.tv_data.setTextColor(ContextCompat.getColor(PlanningExecuteProgressActivity.this.mContext, R.color.black));
                    viewHoder.tv_data.setBackgroundResource(R.drawable.punch_card_yes);
                    return convertView;
                case "2":
                    viewHoder.tv_data.setTextColor(ContextCompat.getColor(PlanningExecuteProgressActivity.this.mContext, R.color.black));
                    viewHoder.tv_data.setBackgroundResource(R.drawable.punch_card_no);
                    return convertView;
                case "3":
                    viewHoder.tv_data.setTextColor(ContextCompat.getColor(PlanningExecuteProgressActivity.this.mContext, R.color.white));
                    viewHoder.tv_data.setBackgroundResource(R.drawable.bg_circle_gray);
                    return convertView;
                case "4":
                    viewHoder.tv_data.setTextColor(ContextCompat.getColor(PlanningExecuteProgressActivity.this.mContext, R.color.black));
                    viewHoder.tv_data.setBackgroundResource(R.drawable.bg_circle_gray);
                    viewHoder.tv_data.setText("今");
                    return convertView;
                default:
                    return convertView;
            }
        }
    }

    public class CalendarGridViewAdapter extends BaseAdapter {

        public class ViewHoder {
            private GridView gridView_day;
            private TextView tv_year_month;

            public ViewHoder(View view) {
                this.tv_year_month = (TextView) view.findViewById(R.id.tv_year_month);
                this.gridView_day = (GridView) view.findViewById(R.id.gridView_day);
            }
        }

        public CalendarGridViewAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return PlanningExecuteProgressActivity.this.list_MyTitleBean.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return PlanningExecuteProgressActivity.this.list_MyTitleBean.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoder viewHoder;
            if (convertView == null) {
                convertView = LayoutInflater.from(PlanningExecuteProgressActivity.this.mContext).inflate(R.layout.adapter_gridview, (ViewGroup) null);
                viewHoder = new ViewHoder(convertView);
                convertView.setTag(viewHoder);
            } else {
                viewHoder = (ViewHoder) convertView.getTag();
            }
            viewHoder.tv_year_month.setText(((MyTitleBean) PlanningExecuteProgressActivity.this.list_MyTitleBean.get(position)).getTitle());
            viewHoder.gridView_day.setAdapter((ListAdapter) PlanningExecuteProgressActivity.this.new CalendarAdapter(((MyTitleBean) PlanningExecuteProgressActivity.this.list_MyTitleBean.get(position)).getList()));
            return convertView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogPlanning$1(View view) {
        this.popupWindow_select.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogPlanning$2() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (!CommonUtil.isFastClick() && view.getId() == R.id.iv_actionbar_right) {
            dialogPlanning(view);
        }
    }

    @SuppressLint({"NewApi"})
    public void dialogPlanning(View v2) {
        View viewInflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.popu_planning, (ViewGroup) null);
        viewInflate.findViewById(R.id.view_dismiss).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.planning.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13550c.lambda$dialogPlanning$1(view);
            }
        });
        viewInflate.findViewById(R.id.tv_modify_plan).setOnClickListener(this.mOnclick);
        viewInflate.findViewById(R.id.tv_end_plan).setOnClickListener(this.mOnclick);
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -1);
        this.popupWindow_select = popupWindow;
        popupWindow.setFocusable(true);
        this.popupWindow_select.setOutsideTouchable(true);
        this.popupWindow_select.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.planning.c
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                PlanningExecuteProgressActivity.lambda$dialogPlanning$2();
            }
        });
        this.popupWindow_select.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow_select.showAtLocation(v2, 17, 0, 0);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        int i2;
        int i3;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llay_riqi);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_progress);
        RoundProgressBar roundProgressBar = (RoundProgressBar) findViewById(R.id.ronun_progress);
        ((Button) findViewById(R.id.btn_next)).setVisibility(8);
        linearLayout.setVisibility(8);
        relativeLayout.setVisibility(0);
        roundProgressBar.setCricleColor(ContextCompat.getColor(this.mContext, R.color.transparent_white_bg));
        roundProgressBar.setCricleProgressColor(ContextCompat.getColor(this.mContext, R.color.white));
        roundProgressBar.setProgress(55);
        CalendarGridViewAdapter calendarGridViewAdapter = new CalendarGridViewAdapter();
        ListView listView = (ListView) findViewById(R.id.lv_planning_execute);
        int i4 = 2;
        int i5 = 1;
        int i6 = Calendar.getInstance().get(2) + 1;
        int i7 = i6;
        while (i7 < i6 + 10) {
            MyTitleBean myTitleBean = new MyTitleBean();
            if (i7 <= 12) {
                StringBuilder sb = new StringBuilder();
                sb.append(Calendar.getInstance().get(i5));
                sb.append("年");
                int i8 = i7 % 12;
                if (i8 == 0) {
                    i8 = 12;
                }
                sb.append(i8);
                sb.append("月");
                myTitleBean.setTitle(sb.toString());
                ArrayList arrayList = new ArrayList();
                Calendar calendar = Calendar.getInstance();
                calendar.add(i4, -1);
                int i9 = i5;
                int i10 = 0;
                while (i10 < 300) {
                    MycalendarBean mycalendarBean = new MycalendarBean();
                    int i11 = i6;
                    calendar.add(5, i5);
                    if (calendar.get(i4) + i5 == i7) {
                        if (i9 == i5) {
                            for (int i12 = i5; i12 < calendar.get(7); i12++) {
                                arrayList.add(new MycalendarBean());
                            }
                        }
                        i9++;
                        if (calendar.get(5) < Calendar.getInstance().get(5) - 5) {
                            mycalendarBean.setFlag("0");
                        } else if (calendar.get(5) < Calendar.getInstance().get(5)) {
                            mycalendarBean.setFlag("1");
                        } else if (calendar.get(5) == Calendar.getInstance().get(5) && calendar.get(2) == Calendar.getInstance().get(2) && calendar.get(1) == Calendar.getInstance().get(1)) {
                            mycalendarBean.setFlag("4");
                        } else if (calendar.get(5) < Calendar.getInstance().get(5) + 7) {
                            mycalendarBean.setFlag("2");
                        } else {
                            mycalendarBean.setFlag("3");
                        }
                        mycalendarBean.setYear(calendar.get(1) + "");
                        mycalendarBean.setMonth((calendar.get(2) + 1) + "");
                        mycalendarBean.setDay(calendar.get(5) + "");
                        mycalendarBean.setWeek(calendar.get(7) + "");
                        Log.e("日期", calendar.get(1) + ":" + (calendar.get(2) + 1) + ":" + calendar.get(5) + ":" + calendar.get(7));
                        arrayList.add(mycalendarBean);
                    }
                    i10++;
                    i6 = i11;
                    i4 = 2;
                    i5 = 1;
                }
                i2 = i6;
                myTitleBean.setList(arrayList);
            } else {
                i2 = i6;
                ArrayList arrayList2 = new ArrayList();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.add(2, -1);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Calendar.getInstance().get(1) + 1);
                sb2.append("年");
                int i13 = i7 % 12;
                sb2.append(i13 == 0 ? 12 : i13);
                sb2.append("月");
                myTitleBean.setTitle(sb2.toString());
                int i14 = 1;
                int i15 = 0;
                while (i15 < 300) {
                    MycalendarBean mycalendarBean2 = new MycalendarBean();
                    calendar2.add(5, 1);
                    int i16 = calendar2.get(2) + 1;
                    if (i13 == 0) {
                        i3 = i13;
                        i13 = 12;
                    } else {
                        i3 = i13;
                    }
                    if (i16 == i13) {
                        if (i14 == 1) {
                            for (int i17 = 1; i17 < calendar2.get(7); i17++) {
                                arrayList2.add(new MycalendarBean());
                            }
                        }
                        i14++;
                        mycalendarBean2.setYear(calendar2.get(1) + "");
                        mycalendarBean2.setMonth((calendar2.get(2) + 1) + "");
                        mycalendarBean2.setDay(calendar2.get(5) + "");
                        mycalendarBean2.setWeek(calendar2.get(7) + "");
                        Log.e("日期", calendar2.get(1) + ":" + (calendar2.get(2) + 1) + ":" + calendar2.get(5) + ":" + calendar2.get(7));
                        arrayList2.add(mycalendarBean2);
                    }
                    i15++;
                    i13 = i3;
                }
                myTitleBean.setList(arrayList2);
            }
            this.list_MyTitleBean.add(myTitleBean);
            i7++;
            i6 = i2;
            i4 = 2;
            i5 = 1;
        }
        listView.setAdapter((ListAdapter) calendarGridViewAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("基础阶段");
        this.iv_actionbar_right.setVisibility(0);
        this.mBtnActionbarRight.setVisibility(8);
        this.mBtnActionbarBack.setImageResource(R.drawable.planning_cancel);
        this.iv_actionbar_right.setImageResource(R.drawable.planning_jihua);
        this.iv_actionbar_right.setOnClickListener(this.mOnclick);
        setContentView(R.layout.activity_planning_start_execute);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
