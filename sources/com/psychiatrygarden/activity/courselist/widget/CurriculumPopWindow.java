package com.psychiatrygarden.activity.courselist.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DividerItemDecoration;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.viewfilter.Utils;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class CurriculumPopWindow extends PartShadowPopupView {
    public ClickIml clickIml;
    public ImageView close;
    public Context context;
    public RecyclerView recyclerView;

    /* renamed from: com.psychiatrygarden.activity.courselist.widget.CurriculumPopWindow$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<String, BaseViewHolder> {
        public AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(String str, View view) {
            CurriculumPopWindow.this.clickIml.onClickView(str);
            CurriculumPopWindow.this.dismiss();
        }

        @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
        public void convert(BaseViewHolder helper, final String item) {
            ImageView imageView;
            TextView textView = (TextView) helper.getView(R.id.title);
            imageView = (ImageView) helper.getView(R.id.icon);
            item.hashCode();
            switch (item) {
                case "我的下载":
                    if (SkinManager.getCurrentSkinType(CurriculumPopWindow.this.context) != 1) {
                        imageView.setImageResource(R.drawable.my_curr_down);
                        break;
                    } else {
                        imageView.setImageResource(R.drawable.my_curr_down_night);
                        break;
                    }
                case "我的收藏":
                    if (SkinManager.getCurrentSkinType(CurriculumPopWindow.this.context) != 1) {
                        imageView.setImageResource(R.drawable.my_curr_collect);
                        break;
                    } else {
                        imageView.setImageResource(R.drawable.my_curr_collect_night);
                        break;
                    }
                case "我的笔记":
                    if (SkinManager.getCurrentSkinType(CurriculumPopWindow.this.context) != 1) {
                        imageView.setImageResource(R.drawable.my_curr_note);
                        break;
                    } else {
                        imageView.setImageResource(R.drawable.my_curr_note_night);
                        break;
                    }
                case "我的评论":
                    if (SkinManager.getCurrentSkinType(CurriculumPopWindow.this.context) != 1) {
                        imageView.setImageResource(R.drawable.my_curr_commet);
                        break;
                    } else {
                        imageView.setImageResource(R.drawable.my_curr_commet_night);
                        break;
                    }
            }
            textView.setText(item);
            helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.widget.c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12202c.lambda$convert$0(item, view);
                }
            });
        }
    }

    public interface ClickIml {
        void onClickView(String item);
    }

    public CurriculumPopWindow(@NonNull @NotNull Context context, ClickIml clickIml) {
        super(context);
        this.context = context;
        this.clickIml = clickIml;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_curriculum_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.close);
        this.close = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.widget.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12201c.lambda$onCreate$0(view);
            }
        });
        CommonUtil.roteImg(this.close, 0);
        int screenWidth = ((ScreenUtil.getScreenWidth((Activity) this.context) - (Utils.dip2px(this.context, 15.0f) * 2)) - (Utils.dip2px(this.context, 50.0f) * 4)) / 3;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager((Activity) this.context, 0, false));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this.context, 1, screenWidth, 0));
        ArrayList arrayList = new ArrayList();
        arrayList.add("我的收藏");
        arrayList.add("我的评论");
        arrayList.add("我的笔记");
        arrayList.add("我的下载");
        this.recyclerView.setAdapter(new AnonymousClass1(R.layout.layout_curriculum_pop_item, arrayList));
    }
}
