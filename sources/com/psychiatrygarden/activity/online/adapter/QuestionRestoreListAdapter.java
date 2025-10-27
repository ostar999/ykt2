package com.psychiatrygarden.activity.online.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.bumptech.glide.Glide;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.AllRestoreAndExplainActivity;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.online.bean.QuestionErroCorrectionBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.MyExpendView;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionRestoreListAdapter extends BaseAdapter implements PinnedSectionListView1.PinnedSectionListAdapter {
    private Context context;
    private List<QuestionErroCorrectionBean.DataDTO> list;
    public Activity mActivity;
    public QuestionErroCorrectionBean.DataDTO mH;
    private String question_column;
    private String question_id;
    public List<QuestionErroCorrectionBean.DataDTO> time_line;

    public static class ViewHolder {
        TextView group_nametv;
        ImageView iv_accept_state;
        ImageView iv_userhead;
        LinearLayout lineremen;
        RelativeLayout mLyContent;
        TextView mTvLookMore;
        TextView mTvRestoreStatus;
        MyExpendView myexptervew;
        LinearLayout threeview;
        TextView tv_restoration_content;
        TextView tv_restoration_content_explin;
        TextView tv_restoration_type;
        TextView tv_time;
        TextView tv_username;
        TextView tv_vip_day;
    }

    public QuestionRestoreListAdapter(Context context, List<QuestionErroCorrectionBean.DataDTO> list, List<QuestionErroCorrectionBean.DataDTO> time_line, String question_column, String question_id, BaseActivity mActivity, boolean isNewComzantong) {
        setList(list);
        this.context = context;
        this.mActivity = mActivity;
        this.time_line = time_line;
        this.question_column = question_column;
        this.question_id = question_id;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$4() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$0(QuestionErroCorrectionBean.DataDTO dataDTO, View view) {
        Intent intent = new Intent(this.mActivity, (Class<?>) AllRestoreAndExplainActivity.class);
        intent.putExtra("question_id", this.question_id + "");
        intent.putExtra("title", dataDTO.getName() + "");
        intent.putExtra("question_column", this.question_column);
        this.mActivity.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getView$1(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$2(QuestionErroCorrectionBean.DataDTO dataDTO, View view) {
        Intent intent = new Intent(this.context, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", "" + dataDTO.getUser_id());
        this.context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$3(QuestionErroCorrectionBean.DataDTO dataDTO, View view) {
        Intent intent = new Intent(this.mActivity, (Class<?>) AllRestoreAndExplainActivity.class);
        intent.putExtra("question_id", this.question_id + "");
        intent.putExtra("title", dataDTO.getName() + "");
        intent.putExtra("question_column", this.question_column);
        this.mActivity.startActivity(intent);
    }

    public void Toast_pop(View v2, int flag) {
        ImageView imageView = new ImageView(v2.getContext());
        if (flag == 0) {
            imageView.setBackgroundResource(R.drawable.zan_animation);
        } else {
            imageView.setBackgroundResource(R.drawable.cai_animation);
        }
        final PopupWindow popupWindow = new PopupWindow(imageView, -2, -2);
        if (flag == 0) {
            popupWindow.setAnimationStyle(R.style.popshowzan);
        } else {
            popupWindow.setAnimationStyle(R.style.popshowcai);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.online.adapter.t
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                QuestionRestoreListAdapter.lambda$Toast_pop$4();
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] iArr = new int[2];
        v2.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        v2.getLocalVisibleRect(rect);
        if (flag == 0) {
            popupWindow.showAtLocation(v2, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        } else {
            popupWindow.showAtLocation(v2, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        }
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.adapter.u
            @Override // java.lang.Runnable
            public final void run() {
                popupWindow.dismiss();
            }
        }, 1000L);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    public List<QuestionErroCorrectionBean.DataDTO> getList() {
        return this.list;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View converView, ViewGroup viewGrop) throws NumberFormatException {
        View viewInflate;
        ViewHolder viewHolder;
        if (converView == null) {
            viewHolder = new ViewHolder();
            viewInflate = LayoutInflater.from(this.context).inflate(R.layout.item_question_restore_list, (ViewGroup) null);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_accept_state);
            viewHolder.iv_accept_state = imageView;
            imageView.setVisibility(8);
            viewHolder.mTvRestoreStatus = (TextView) viewInflate.findViewById(R.id.tv_restore_status);
            viewHolder.tv_restoration_type = (TextView) viewInflate.findViewById(R.id.tv_restoration_type);
            viewHolder.tv_vip_day = (TextView) viewInflate.findViewById(R.id.tv_vip_day);
            viewHolder.tv_restoration_content = (TextView) viewInflate.findViewById(R.id.tv_restoration_content);
            viewHolder.myexptervew = (MyExpendView) viewInflate.findViewById(R.id.myexptervew);
            viewHolder.tv_restoration_content_explin = (TextView) viewInflate.findViewById(R.id.tv_restoration_content_explin);
            viewHolder.iv_userhead = (ImageView) viewInflate.findViewById(R.id.iv_userhead);
            viewHolder.tv_time = (TextView) viewInflate.findViewById(R.id.tv_time);
            viewHolder.tv_username = (TextView) viewInflate.findViewById(R.id.tv_username);
            viewHolder.lineremen = (LinearLayout) viewInflate.findViewById(R.id.lineremen);
            viewHolder.mLyContent = (RelativeLayout) viewInflate.findViewById(R.id.ly_content);
            viewHolder.group_nametv = (TextView) viewInflate.findViewById(R.id.group_nametv);
            viewHolder.threeview = (LinearLayout) viewInflate.findViewById(R.id.threeview);
            viewHolder.mTvLookMore = (TextView) viewInflate.findViewById(R.id.tv_look_more);
            viewInflate.setTag(viewHolder);
        } else {
            viewInflate = converView;
            viewHolder = (ViewHolder) converView.getTag();
        }
        final QuestionErroCorrectionBean.DataDTO item = getItem(position);
        if (item.getType() == 1) {
            viewHolder.group_nametv.setText(item.getName());
            viewHolder.lineremen.setVisibility(0);
            viewHolder.mLyContent.setVisibility(8);
            viewHolder.threeview.setVisibility(8);
            viewHolder.lineremen.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.p
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13134c.lambda$getView$0(item, view);
                }
            });
        } else {
            viewHolder.lineremen.setVisibility(8);
            viewHolder.mLyContent.setVisibility(0);
            viewHolder.tv_username.setText(item.getNickname());
            Glide.with(this.context).load((Object) GlideUtils.generateUrl(item.getAvatar())).placeholder(new ColorDrawable(ContextCompat.getColor(viewHolder.iv_userhead.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(viewHolder.iv_userhead);
            viewHolder.tv_restoration_type.setText(item.getError_type_title());
            if (!TextUtils.isEmpty(item.getGive_vip()) && Integer.parseInt(item.getGive_vip()) > 0) {
                viewHolder.tv_vip_day.setVisibility(0);
                viewHolder.tv_vip_day.setText(item.getGive_vip_str());
            } else {
                viewHolder.tv_vip_day.setVisibility(8);
            }
            viewHolder.myexptervew.setIs_del(item.getIs_del());
            viewHolder.myexptervew.setText(item.getContent(), item.is_showValue());
            viewHolder.myexptervew.setListener(new MyExpendView.OnExpandStateChangeListener() { // from class: com.psychiatrygarden.activity.online.adapter.QuestionRestoreListAdapter.1
                @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
                public void onClickStateChange(final View v2) {
                    UserConfig.getUserId().equals(item.getUser_id());
                }

                @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
                public void onExpandStateChanged(boolean isExpanded) {
                    item.setIs_showValue(isExpanded);
                }
            });
            if (TextUtils.isEmpty(item.getContent_explain())) {
                viewHolder.tv_restoration_content_explin.setVisibility(8);
            } else {
                viewHolder.tv_restoration_content_explin.setVisibility(0);
                viewHolder.tv_restoration_content_explin.setText(item.getContent_explain());
            }
            viewHolder.tv_time.setText(item.getTimeStr());
            if (TextUtils.isEmpty(item.getIs_adopt())) {
                viewHolder.mTvRestoreStatus.setVisibility(8);
            } else {
                viewHolder.mTvRestoreStatus.setVisibility(0);
                viewHolder.mTvRestoreStatus.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
                int i2 = Integer.parseInt(item.getIs_adopt());
                if (i2 == 0) {
                    viewHolder.mTvRestoreStatus.setText("待审核");
                    viewHolder.mTvRestoreStatus.setBackgroundResource(R.drawable.question_restore_audit);
                } else if (i2 == 1) {
                    viewHolder.mTvRestoreStatus.setText("已采纳");
                    viewHolder.mTvRestoreStatus.setBackgroundResource(R.drawable.question_restore_accept);
                } else if (i2 == 2) {
                    viewHolder.mTvRestoreStatus.setText("未采纳");
                    viewHolder.mTvRestoreStatus.setBackgroundResource(R.drawable.question_restore_no_accept);
                } else if (i2 == 3) {
                    viewHolder.mTvRestoreStatus.setText("部分采纳");
                    viewHolder.mTvRestoreStatus.setTextColor(Color.parseColor("#74BF39"));
                    viewHolder.mTvRestoreStatus.setBackgroundResource(R.drawable.question_restore_part_accept);
                }
            }
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    QuestionRestoreListAdapter.lambda$getView$1(view);
                }
            });
            viewHolder.iv_userhead.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13136c.lambda$getView$2(item, view);
                }
            });
            viewHolder.threeview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13138c.lambda$getView$3(item, view);
                }
            });
            if (item.getId() == null || item.getId().equals("")) {
                if (item.getOtherView() == 3) {
                    viewHolder.threeview.setVisibility(0);
                    viewHolder.mLyContent.setVisibility(8);
                    if (this.question_column.equals(RequestParameters.X_OSS_RESTORE)) {
                        viewHolder.mTvLookMore.setText("查看全部");
                    } else {
                        viewHolder.mTvLookMore.setText("查看全部");
                    }
                } else {
                    viewHolder.threeview.setVisibility(8);
                }
                return viewInflate;
            }
            viewHolder.threeview.setVisibility(8);
        }
        return viewInflate;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 2;
    }

    @Override // com.psychiatrygarden.widget.PinnedSectionListView1.PinnedSectionListAdapter
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    public void refresh(ArrayList<QuestionErroCorrectionBean.DataDTO> arr) {
        setList(arr);
        notifyDataSetChanged();
    }

    public void setList(List<QuestionErroCorrectionBean.DataDTO> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
    }

    public void setRefeault(List<QuestionErroCorrectionBean.DataDTO> time_line) {
        this.time_line.addAll(time_line);
    }

    @Override // android.widget.Adapter
    public QuestionErroCorrectionBean.DataDTO getItem(int position) {
        return this.list.get(position);
    }

    public QuestionRestoreListAdapter(Context context, List<QuestionErroCorrectionBean.DataDTO> list, Activity mActivity) {
        setList(list);
        this.context = context;
        this.mActivity = mActivity;
    }
}
