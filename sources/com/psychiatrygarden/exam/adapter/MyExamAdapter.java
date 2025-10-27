package com.psychiatrygarden.exam.adapter;

import android.os.SystemClock;
import android.util.ArrayMap;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.MyExamDataBean;
import com.psychiatrygarden.exam.RvCountDownHelper;
import com.psychiatrygarden.utils.LogUtils;
import com.yikaobang.yixue.R;
import java.util.Iterator;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes5.dex */
public class MyExamAdapter extends BaseMultiItemQuickAdapter<MyExamDataBean, BaseViewHolder> implements LifecycleObserver {
    private final RvCountDownHelper countDownHelper;
    private final ArrayMap<Integer, CountDownShow> timeShowMap;

    public static class CountDownShow {
        private int hour;
        private String hourShow;
        private int min;
        private String minShow;
        private int sec;
        private String secShow;

        public int getHour() {
            return this.hour;
        }

        public String getHourShow() {
            return this.hourShow;
        }

        public int getMin() {
            return this.min;
        }

        public String getMinShow() {
            return this.minShow;
        }

        public int getSec() {
            return this.sec;
        }

        public String getSecShow() {
            return this.secShow;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public void setHourShow(String hourShow) {
            this.hourShow = hourShow;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public void setMinShow(String minShow) {
            this.minShow = minShow;
        }

        public void setSec(int sec) {
            this.sec = sec;
        }

        public void setSecShow(String secShow) {
            this.secShow = secShow;
        }
    }

    public MyExamAdapter(RecyclerView recyclerView, RvCountDownHelper.OnTimeCollectListener listener) {
        addItemType(0, R.layout.item_my_exam_not_start_normal);
        addItemType(1, R.layout.item_my_exam_start_ing);
        addItemType(2, R.layout.item_my_exam_finished);
        addItemType(3, R.layout.item_my_exam_not_start_count_down);
        RvCountDownHelper rvCountDownHelper = new RvCountDownHelper(this, recyclerView);
        this.countDownHelper = rvCountDownHelper;
        rvCountDownHelper.setOnTimeCollectListener(listener);
        this.timeShowMap = new ArrayMap<>();
    }

    private CountDownShow getCountDownShowStr(long remain, int pos) {
        StringBuilder sb;
        String string;
        StringBuilder sb2;
        String string2;
        if (this.timeShowMap.get(Integer.valueOf(pos)) == null) {
            this.timeShowMap.put(Integer.valueOf(pos), new CountDownShow());
        }
        CountDownShow countDownShow = this.timeShowMap.get(Integer.valueOf(pos));
        int i2 = (int) (remain / 3600);
        long j2 = remain % 3600;
        int i3 = (int) (j2 / 60);
        int i4 = (int) (j2 % 60);
        countDownShow.setHour(i2);
        String str = TarConstants.VERSION_POSIX;
        if (i2 <= 0) {
            string = TarConstants.VERSION_POSIX;
        } else {
            if (i2 < 10) {
                sb = new StringBuilder();
                sb.append("0");
                sb.append(i2);
            } else {
                sb = new StringBuilder();
                sb.append(i2);
                sb.append("");
            }
            string = sb.toString();
        }
        countDownShow.setHourShow(string);
        countDownShow.setMin(i3);
        if (i3 <= 0) {
            string2 = TarConstants.VERSION_POSIX;
        } else {
            if (i3 < 10) {
                sb2 = new StringBuilder();
                sb2.append("0");
                sb2.append(i3);
            } else {
                sb2 = new StringBuilder();
                sb2.append(i3);
                sb2.append("");
            }
            string2 = sb2.toString();
        }
        countDownShow.setMinShow(string2);
        countDownShow.setSec(i4);
        if (i4 > 0) {
            if (i4 < 10) {
                str = "0" + i4;
            } else {
                str = i4 + "";
            }
        }
        countDownShow.setSecShow(str);
        return countDownShow;
    }

    private int getNotStartExamCount() {
        Iterator it = getData().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            i2 += ((MyExamDataBean) it.next()).getStatus() == 0 ? 1 : 0;
        }
        return i2;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void startCountDown() {
        if (getNotStartExamCount() > 0) {
            this.countDownHelper.startCountDown();
            LogUtils.d("Lifecycle", "startCountDown");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopCountDown() {
        this.countDownHelper.stopCountDown();
        LogUtils.d("Lifecycle", "stopCountDown");
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, MyExamDataBean item) {
        holder.setText(R.id.tv_name, item.getTitle()).setText(R.id.tv_time, item.getStartTimeShow());
        if (item.getStatus() == 3 && ((int) (((item.getStartTimeStamp() - item.getServerTime()) / 3600) / 1000)) < 24) {
            long remainTimeStamp = item.getRemainTimeStamp() - SystemClock.elapsedRealtime();
            if (remainTimeStamp > 1000) {
                this.countDownHelper.addPosition2CountDown(holder.getLayoutPosition());
                CountDownShow countDownShowStr = getCountDownShowStr(remainTimeStamp / 1000, holder.getLayoutPosition());
                holder.setText(R.id.tv_hour, countDownShowStr.getHourShow()).setText(R.id.tv_min, countDownShowStr.getMinShow()).setText(R.id.tv_sec, countDownShowStr.getSecShow());
            }
        }
    }
}
