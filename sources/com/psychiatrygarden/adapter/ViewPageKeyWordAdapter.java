package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.bean.ForumTopBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.forum.ForumSearchAct;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class ViewPageKeyWordAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final List<ForumTopBean.KeyWordsData> data;
    private boolean isKeyWord;
    private final Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public ViewPageKeyWordAdapter(Context mContext, List<ForumTopBean.KeyWordsData> data, boolean isKeyWord) {
        this.mContext = mContext;
        this.data = data;
        this.isKeyWord = isKeyWord;
    }

    private void isLoginAction(String json) {
        if (UserConfig.isLogin()) {
            PublicMethodActivity.getInstance().mToActivity(json);
        } else {
            this.mContext.startActivity(new Intent(this.mContext, (Class<?>) LoginActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(ForumTopBean.KeyWordsData keyWordsData, View view) {
        if (this.isKeyWord) {
            ForumSearchAct.newIntent(this.mContext, keyWordsData.getKeywords(), keyWordsData.getTarget_params(), 0);
        } else {
            isLoginAction(new Gson().toJson(keyWordsData.getTarget_params()));
        }
    }

    public List<ForumTopBean.KeyWordsData> getData() {
        return this.data;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        onBindViewHolder((ViewHolder) holder, position, (List<Object>) payloads);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_banner, parent, false);
        viewInflate.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return new ViewHolder(viewInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (this.data.size() == 0) {
            return;
        }
        final ForumTopBean.KeyWordsData keyWordsData = this.data.get(position % this.data.size());
        if (this.isKeyWord) {
            holder.title.setTextSize(14.0f);
            holder.title.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(this.mContext) == 1 ? "#454C64" : "#C2C6CB"));
            holder.title.setText(keyWordsData.getKeywords());
        } else {
            holder.title.setTextSize(12.0f);
            holder.title.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(this.mContext) == 1 ? "#C49231" : "#FFC100"));
            holder.title.setText(keyWordsData.getTitle());
        }
        holder.title.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.wf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15149c.lambda$onBindViewHolder$0(keyWordsData, view);
            }
        });
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder((ViewPageKeyWordAdapter) holder, position, payloads);
    }
}
