package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.interfaceclass.BiaoqianInterface;
import com.psychiatrygarden.utils.FlowLayout;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.TagAdapter;
import com.psychiatrygarden.utils.TagMuilFlowLayout;
import com.yikaobang.yixue.R;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class PopupBiaoWindow extends PopupWindow implements View.OnClickListener {
    private RelativeLayout biao_rel;
    private ImageView chose;
    private Context context;
    private List<BiaoqianBeab.DataBean> dataBiaoBIao;
    private List<BiaoqianBeab.DataBean> dataBiaoxiu;
    private LinearLayout linrel;
    private BiaoqianInterface mBiaoqianInterface;
    private CommAdapter<String> mCommAdapter;
    private TagMuilFlowLayout mTagMuilFlowLayout;
    private View view;
    private TextView xuanzetijiao;

    public PopupBiaoWindow(Context mContext, final List<BiaoqianBeab.DataBean> dataBiaoNU, final BiaoqianInterface mBiaoqianInterfaceListenr) {
        super(mContext);
        this.context = mContext;
        this.dataBiaoBIao = dataBiaoNU;
        this.dataBiaoxiu = dataBiaoNU;
        this.mBiaoqianInterface = mBiaoqianInterfaceListenr;
        View viewInflate = ((LayoutInflater) mContext.getSystemService("layout_inflater")).inflate(R.layout.acitivity_baioqain, (ViewGroup) null);
        this.view = viewInflate;
        setContentView(viewInflate);
        this.view.measure(0, 0);
        setWidth(-1);
        setHeight(-1);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        setBackgroundDrawable(mContext.getResources().getDrawable(R.color.dialog_transparent_bg));
        this.chose = (ImageView) this.view.findViewById(R.id.chose);
        this.xuanzetijiao = (TextView) this.view.findViewById(R.id.xuanzetijiao);
        this.biao_rel = (RelativeLayout) this.view.findViewById(R.id.biao_rel);
        LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.linrel);
        this.linrel = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.PopupBiaoWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
            }
        });
        TagMuilFlowLayout tagMuilFlowLayout = (TagMuilFlowLayout) this.view.findViewById(R.id.mTagMuilFlowLayout);
        this.mTagMuilFlowLayout = tagMuilFlowLayout;
        tagMuilFlowLayout.setMaxSelectCount(2);
        this.biao_rel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.PopupBiaoWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PopupBiaoWindow.this.dismiss();
            }
        });
        this.chose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.PopupBiaoWindow.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PopupBiaoWindow.this.dismiss();
            }
        });
        this.xuanzetijiao.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.PopupBiaoWindow.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                Collections.sort(PopupBiaoWindow.this.dataBiaoBIao);
                PopupBiaoWindow.this.mBiaoqianInterface.mBiaoianLinster(PopupBiaoWindow.this.dataBiaoBIao, false);
                PopupBiaoWindow.this.dismiss();
            }
        });
        final LayoutInflater layoutInflaterFrom = LayoutInflater.from(mContext);
        this.mTagMuilFlowLayout.setDataBiao(this.dataBiaoBIao);
        this.mTagMuilFlowLayout.setAdapter(new TagAdapter<BiaoqianBeab.DataBean>(this.dataBiaoBIao) { // from class: com.psychiatrygarden.widget.PopupBiaoWindow.5
            @Override // com.psychiatrygarden.utils.TagAdapter
            public void onSelected(int position, View view, boolean isClick) {
                super.onSelected(position, view, isClick);
                if (PopupBiaoWindow.this.dataBiaoBIao == null || PopupBiaoWindow.this.dataBiaoBIao.size() <= 0) {
                    return;
                }
                TextView textView = (TextView) view.findViewById(R.id.biaoqiantv);
                if (SkinManager.getCurrentSkinType(PopupBiaoWindow.this.context) == 0) {
                    textView.setTextColor(PopupBiaoWindow.this.context.getResources().getColor(R.color.white));
                } else {
                    textView.setTextColor(PopupBiaoWindow.this.context.getResources().getColor(R.color.question_color_night));
                }
                if (((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).isChange()) {
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setChange(false);
                } else {
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setChange(true);
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setCutXuni(((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getCount());
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setUser_label_Xuni(((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getUser_label());
                }
                if (!((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getUser_label().equals("1")) {
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setUser_label("1");
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setCount(((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getCount() + 1);
                    textView.setText(((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getLabel() + " " + ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getCount());
                }
                if (isClick) {
                    PopupBiaoWindow.this.mBiaoqianInterface.mBiaoianLinster(PopupBiaoWindow.this.dataBiaoBIao, false);
                    notifyDataChanged();
                }
            }

            @Override // com.psychiatrygarden.utils.TagAdapter
            public void unSelected(int position, View view, boolean isClick) {
                super.unSelected(position, view, isClick);
                if (PopupBiaoWindow.this.dataBiaoBIao == null || PopupBiaoWindow.this.dataBiaoBIao.size() <= 0) {
                    return;
                }
                TextView textView = (TextView) view.findViewById(R.id.biaoqiantv);
                if (SkinManager.getCurrentSkinType(PopupBiaoWindow.this.context) == 0) {
                    textView.setTextColor(PopupBiaoWindow.this.context.getResources().getColor(R.color.gray_biao));
                } else {
                    textView.setTextColor(PopupBiaoWindow.this.context.getResources().getColor(R.color.jiucuo_night));
                }
                if (((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).isChange()) {
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setChange(false);
                } else {
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setChange(true);
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setCutXuni(((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getCount());
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setUser_label_Xuni(((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getUser_label());
                }
                if (((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getUser_label().equals("1")) {
                    ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setUser_label("0");
                    if (((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getCount() - 1 > 0) {
                        ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setCount(((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getCount() - 1);
                        textView.setText(((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getLabel() + " " + ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getCount());
                    } else {
                        ((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).setCount(0);
                        textView.setText(((BiaoqianBeab.DataBean) PopupBiaoWindow.this.dataBiaoBIao.get(position)).getLabel());
                    }
                }
                if (isClick) {
                    PopupBiaoWindow.this.mBiaoqianInterface.mBiaoianLinster(PopupBiaoWindow.this.dataBiaoBIao, false);
                    notifyDataChanged();
                }
            }

            @Override // com.psychiatrygarden.utils.TagAdapter
            public View getView(FlowLayout parent, int position, BiaoqianBeab.DataBean dataBean) {
                TextView textView = (TextView) layoutInflaterFrom.inflate(R.layout.biaoqiantv, (ViewGroup) PopupBiaoWindow.this.mTagMuilFlowLayout, false);
                if (dataBean.getCount() > 0) {
                    if (SkinManager.getCurrentSkinType(PopupBiaoWindow.this.context) == 0) {
                        if (position > 2) {
                            textView.setTextColor(PopupBiaoWindow.this.context.getResources().getColor(R.color.gray_biao));
                        } else {
                            textView.setTextColor(PopupBiaoWindow.this.context.getResources().getColor(R.color.black));
                        }
                    } else if (position > 2) {
                        textView.setTextColor(PopupBiaoWindow.this.context.getResources().getColor(R.color.jiucuo_night));
                    } else {
                        textView.setTextColor(PopupBiaoWindow.this.context.getResources().getColor(R.color.question_color_night));
                    }
                    textView.setText(dataBean.getLabel() + " " + dataBean.getCount() + "");
                } else {
                    if (SkinManager.getCurrentSkinType(PopupBiaoWindow.this.context) == 0) {
                        textView.setTextColor(PopupBiaoWindow.this.context.getResources().getColor(R.color.gray_biao));
                    } else {
                        textView.setTextColor(PopupBiaoWindow.this.context.getResources().getColor(R.color.jiucuo_night));
                    }
                    textView.setText(dataBean.getLabel());
                }
                return textView;
            }
        });
    }

    public List<BiaoqianBeab.DataBean> initData(List<BiaoqianBeab.DataBean> list) {
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (list.get(i2).isChange()) {
                    list.get(i2).setCount(list.get(i2).getCutXuni());
                    list.get(i2).setUser_label(list.get(i2).getUser_label_Xuni());
                    list.get(i2).setChange(false);
                }
            }
        }
        return list;
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NewApi"})
    public void onClick(View v2) {
        v2.getId();
    }

    public void showPopUp(View view) {
        showAtLocation(view, 17, 0, 0);
    }
}
