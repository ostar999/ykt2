package com.psychiatrygarden.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.bean.CommentBeiNum;
import com.psychiatrygarden.bean.KuangBeiStaDataBean;
import com.psychiatrygarden.utils.ColorPhrase;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes5.dex */
public class KuangBeiPagerAdapter extends PagerAdapter {
    private Context context;
    private long[] list_questionid;
    private Handler mHandler;
    private Map<Integer, View> map = new HashMap();

    public KuangBeiPagerAdapter(Context context, long[] list_questionid, KuangBeiStaDataBean mKuangBeiStaDataBean, Handler mHandler) {
        this.context = context;
        this.list_questionid = list_questionid;
        this.mHandler = mHandler;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView(this.map.get(Integer.valueOf(position)));
        this.map.remove(Integer.valueOf(position));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void finishUpdate(View arg0) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        return this.list_questionid.length;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(@NonNull Object object) {
        return -2;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @SuppressLint({"NewApi"})
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        return LayoutInflater.from(this.context).inflate(R.layout.item_kuangbei_detail, (ViewGroup) null);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View v2, @NonNull Object arg1) {
        return v2 == arg1;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Parcelable saveState() {
        return null;
    }

    public void setData(int indext, List<CommentBeiNum> list_CommentBeiNum) {
    }

    public void setRefultData(KuangBeiStaDataBean mKuangBeiStaDataBean, int position, long question_id) {
        List<KuangBeiStaDataBean.DataBean> data;
        String number;
        double right_count;
        if (mKuangBeiStaDataBean == null || (data = mKuangBeiStaDataBean.getData()) == null || data.size() <= 0) {
            return;
        }
        TextView textView = (TextView) this.map.get(Integer.valueOf(position)).findViewById(R.id.tongji);
        Button button = (Button) this.map.get(Integer.valueOf(position)).findViewById(R.id.btv_kuangbei_comment);
        char c3 = 0;
        int i2 = 0;
        while (i2 < data.size()) {
            if (data.get(i2).getQuestion_id() == question_id) {
                Locale locale = Locale.CHINA;
                Object[] objArr = new Object[1];
                objArr[c3] = Integer.valueOf(data.get(i2).getComment_count());
                button.setText(String.format(locale, "%d评论", objArr));
                if (data.get(i2).getRight_count() + data.get(i2).getWrong_count() > 0) {
                    right_count = (data.get(i2).getRight_count() * 100) / (data.get(i2).getRight_count() + data.get(i2).getWrong_count());
                    number = CommonUtil.getNumber(right_count);
                } else {
                    number = "0";
                    right_count = 0.0d;
                }
                if (data.get(i2).getAnswer().getRight_count() + data.get(i2).getAnswer().getWrong_count() > 0) {
                    String str = "统计：本题" + data.get(i2).getCollection_count() + "人收藏，全部考生作答" + (data.get(i2).getRight_count() + data.get(i2).getWrong_count()) + "次，对" + data.get(i2).getRight_count() + "次，正确率" + number + "%，本人作答{" + (data.get(i2).getAnswer().getRight_count() + data.get(i2).getAnswer().getWrong_count()) + "}次，对{" + data.get(i2).getAnswer().getRight_count() + "}次，正确率{" + CommonUtil.getNumber((data.get(i2).getAnswer().getRight_count() * 100) / (data.get(i2).getAnswer().getRight_count() + data.get(i2).getAnswer().getWrong_count())) + "%}";
                    if (right_count > (data.get(i2).getAnswer().getRight_count() * 100) / (data.get(i2).getAnswer().getRight_count() + data.get(i2).getAnswer().getWrong_count())) {
                        textView.setText(SkinManager.getCurrentSkinType(this.context) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                    } else {
                        textView.setText(SkinManager.getCurrentSkinType(this.context) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10572282).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                    }
                } else {
                    String str2 = "统计：本题" + data.get(i2).getCollection_count() + "人收藏，全部考生作答" + (data.get(i2).getRight_count() + data.get(i2).getWrong_count()) + "次，对" + data.get(i2).getRight_count() + "次，正确率" + number + "%，本人作答{?}次，对{?}次，正确率{?%}";
                    textView.setText(SkinManager.getCurrentSkinType(this.context) == 0 ? ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                }
            }
            i2++;
            c3 = 0;
        }
    }

    public void setWebViewRefushShow(int indext) {
        try {
            if (((Button) this.map.get(Integer.valueOf(indext)).findViewById(R.id.btv_kuangbei_comment)).getVisibility() == 0) {
                this.mHandler.sendEmptyMessage(1);
            } else {
                this.mHandler.sendEmptyMessage(2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void startUpdate(@NonNull View arg0) {
    }
}
