package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.interfaceclass.BiaoqianInterface;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.Locale;

/* loaded from: classes6.dex */
public class BiaoPupWindow extends CenterPopupView {
    private CommAdapter<BiaoqianBeab.DataBean> adapter;
    private ImageView chose;
    private Context context;
    private List<BiaoqianBeab.DataBean> dataBiaoBIao;
    private GridView gridView;
    private BiaoqianInterface mBiaoqianInterface;

    public BiaoPupWindow(@NonNull Context mContext, List<BiaoqianBeab.DataBean> dataBiaoBIao, final BiaoqianInterface mBiaoqianInterfaceListenr) {
        super(mContext);
        this.context = mContext;
        this.dataBiaoBIao = dataBiaoBIao;
        this.mBiaoqianInterface = mBiaoqianInterfaceListenr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(AdapterView adapterView, View view, int i2, long j2) {
        List<BiaoqianBeab.DataBean> list = this.dataBiaoBIao;
        if (list == null || list.size() <= 0) {
            return;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.dataBiaoBIao.size(); i4++) {
            if (this.dataBiaoBIao.get(i4).getUser_label().equals("1")) {
                i3++;
            }
        }
        if (i3 >= 2 && this.dataBiaoBIao.get(i2).getUser_label().equals("0")) {
            ToastUtil.shortToast(this.context, "最多选择两个标签！");
            return;
        }
        if (this.dataBiaoBIao.get(i2).getUser_label().equals("1")) {
            this.dataBiaoBIao.get(i2).setUser_label("0");
            if (this.dataBiaoBIao.get(i2).getCount() - 1 > 0) {
                this.dataBiaoBIao.get(i2).setCount(this.dataBiaoBIao.get(i2).getCount() - 1);
            } else {
                this.dataBiaoBIao.get(i2).setCount(0);
            }
        } else {
            this.dataBiaoBIao.get(i2).setUser_label("1");
            this.dataBiaoBIao.get(i2).setCount(this.dataBiaoBIao.get(i2).getCount() + 1);
        }
        this.mBiaoqianInterface.mBiaoianLinster(this.dataBiaoBIao, false);
        this.adapter.notifyDataSetChanged();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_biao_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.chose);
        this.chose = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17109c.lambda$onCreate$0(view);
            }
        });
        this.gridView = (GridView) findViewById(R.id.gridview);
        CommAdapter<BiaoqianBeab.DataBean> commAdapter = new CommAdapter<BiaoqianBeab.DataBean>(this.dataBiaoBIao, this.context, R.layout.biaoqiantv) { // from class: com.psychiatrygarden.widget.BiaoPupWindow.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, BiaoqianBeab.DataBean dataBean, int position) {
                TextView textView = (TextView) vHolder.getView(R.id.biaoqiantv);
                if (dataBean.getCount() > 0) {
                    if (SkinManager.getCurrentSkinType(BiaoPupWindow.this.context) == 0) {
                        if (position > 2) {
                            textView.setTextColor(ContextCompat.getColor(BiaoPupWindow.this.context, R.color.gray_font_new2));
                        } else {
                            textView.setTextColor(ContextCompat.getColor(BiaoPupWindow.this.context, R.color.black));
                        }
                    } else if (position > 2) {
                        textView.setTextColor(ContextCompat.getColor(BiaoPupWindow.this.context, R.color.jiucuo_night));
                    } else {
                        textView.setTextColor(ContextCompat.getColor(BiaoPupWindow.this.context, R.color.question_color_night));
                    }
                    textView.setText(String.format(Locale.CHINA, "%s %d", dataBean.getLabel(), Integer.valueOf(dataBean.getCount())));
                } else {
                    if (SkinManager.getCurrentSkinType(BiaoPupWindow.this.context) == 0) {
                        textView.setTextColor(ContextCompat.getColor(BiaoPupWindow.this.context, R.color.gray_font_new2));
                    } else {
                        textView.setTextColor(ContextCompat.getColor(BiaoPupWindow.this.context, R.color.jiucuo_night));
                    }
                    textView.setText(dataBean.getLabel());
                }
                if (!((BiaoqianBeab.DataBean) BiaoPupWindow.this.dataBiaoBIao.get(position)).getUser_label().equals("1")) {
                    if (SkinManager.getCurrentSkinType(BiaoPupWindow.this.context) == 1) {
                        textView.setBackground(ContextCompat.getDrawable(BiaoPupWindow.this.context, R.drawable.gray_deek_round_bg_night));
                        return;
                    } else {
                        textView.setBackground(ContextCompat.getDrawable(BiaoPupWindow.this.context, R.drawable.gray_deek_round_bg));
                        return;
                    }
                }
                if (SkinManager.getCurrentSkinType(BiaoPupWindow.this.context) == 0) {
                    textView.setBackground(ContextCompat.getDrawable(BiaoPupWindow.this.context, R.drawable.preg_deek_rond_bg));
                    textView.setTextColor(ContextCompat.getColor(BiaoPupWindow.this.context, R.color.white));
                } else {
                    textView.setBackground(ContextCompat.getDrawable(BiaoPupWindow.this.context, R.drawable.preg_deek_rond_bg_night));
                    textView.setTextColor(ContextCompat.getColor(BiaoPupWindow.this.context, R.color.question_color_night));
                }
            }
        };
        this.adapter = commAdapter;
        this.gridView.setAdapter((ListAdapter) commAdapter);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.widget.z
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f17137c.lambda$onCreate$1(adapterView, view, i2, j2);
            }
        });
    }
}
