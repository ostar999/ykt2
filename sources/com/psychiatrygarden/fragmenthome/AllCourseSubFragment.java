package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.C;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.event.OnRefreshEvent;
import com.psychiatrygarden.event.RefreshChannelData;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.utils.VerticalImageSpan;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class AllCourseSubFragment extends BaseFragment {
    private RecyclerView allCourseSubListview;
    private int channel_id;
    private boolean hasSetEmptyView;
    private BaseQuickAdapter mCommAdapter;
    private Context mContext;
    private int page = 1;
    private int previousFirstVisibleItem = 0;
    private long previousEventTime = 0;
    private List<CourseBeanTest> dataList = new ArrayList();
    private boolean isLoadMore = false;
    int scrollHeight = 0;
    private boolean isFirstLoad = false;

    public class CourseBeanTest {
        String title;

        public CourseBeanTest(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }
    }

    private void finishLoadMore(boolean success) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof QuestionDatabaseFragmentNew) {
            ((QuestionDatabaseFragmentNew) parentFragment).finishLoadMore(success, this.channel_id);
        }
    }

    private void finishLoadMoreNoMoreData() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof QuestionDatabaseFragmentNew) {
            ((AllCourseFragment) parentFragment).finishLoadMoreNoMoreData(this.channel_id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishRefresh(boolean success) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof AllCourseFragment) {
            ((AllCourseFragment) parentFragment).finishRefresh(success, this.channel_id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initViews$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEmptyView$1(View view) {
        this.page = 1;
        getData();
        EventBus.getDefault().post("refreshForumTopData");
    }

    private void setEmptyView(String type, int viewType) {
        int i2;
        String str;
        if (this.hasSetEmptyView) {
            return;
        }
        this.hasSetEmptyView = true;
        i2 = 0;
        this.mCommAdapter.setList(new ArrayList(0));
        this.mCommAdapter.setEmptyView(R.layout.layout_empty_view);
        type.hashCode();
        switch (type) {
            case "-3":
                i2 = SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.ic_empty_data_circle_night : R.drawable.ic_empty_data_circle_day;
                str = "暂无发帖";
                break;
            case "-4":
                i2 = SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.ic_empty_data_collection_night : R.drawable.ic_empty_data_collection_day;
                str = "暂无收藏";
                break;
            case "-5":
                i2 = SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.ic_empty_data_comment_night : R.drawable.ic_empty_data_comment_day;
                str = "暂无评论";
                break;
            default:
                str = "";
                break;
        }
        CustomEmptyView customEmptyView = new CustomEmptyView(this.mContext, i2, str);
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15632c.lambda$setEmptyView$1(view);
            }
        });
        if (viewType == 1) {
            customEmptyView.setLoadFileResUi(this.mContext);
            customEmptyView.setIsShowReloadBtn(true, "点击刷新页面");
        }
        customEmptyView.showEmptyView();
        this.mCommAdapter.setEmptyView(customEmptyView);
    }

    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("limit", "20");
    }

    public void getImageData(StringBuffer stringBuffer, TextView mTextView) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuffer.toString());
            Matcher matcher = Pattern.compile("\\[[^\\]]+\\]").matcher(stringBuffer.toString());
            while (matcher.find()) {
                String strGroup = matcher.group();
                if (strGroup.contains("http")) {
                    spannableStringBuilder.setSpan(new VerticalImageSpan(new URLImageParser(mTextView, this.mContext, (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1))), matcher.start(), matcher.end(), 33);
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(stringBuffer);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_all_course_sub;
    }

    public void getScrollView(int dy) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.allCourseSubListview.getLayoutManager();
        int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int childCount = linearLayoutManager.getChildCount();
        this.scrollHeight += dy;
        if (dy >= 0) {
            if (dy > 0) {
                this.isFirstLoad = true;
            }
        } else {
            if (childCount <= 0 || iFindFirstVisibleItemPosition <= 7 || this.previousFirstVisibleItem == iFindFirstVisibleItemPosition) {
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.previousFirstVisibleItem = iFindFirstVisibleItemPosition;
            this.previousEventTime = jCurrentTimeMillis;
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.allCourseSubListview);
        this.allCourseSubListview = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BaseQuickAdapter<CourseBeanTest, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<CourseBeanTest, BaseViewHolder>(R.layout.item_all_course_sub) { // from class: com.psychiatrygarden.fragmenthome.AllCourseSubFragment.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder vHolder, CourseBeanTest dataBean) {
                vHolder.setText(R.id.allCourseSubTitle, dataBean.getTitle());
            }
        };
        this.mCommAdapter = baseQuickAdapter;
        this.allCourseSubListview.setAdapter(baseQuickAdapter);
        this.allCourseSubListview.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.fragmenthome.AllCourseSubFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView2, int newState) {
                super.onScrollStateChanged(recyclerView2, newState);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView2, int dx, int dy) {
                super.onScrolled(recyclerView2, dx, dy);
                AllCourseSubFragment.this.getScrollView(dy);
            }
        });
        this.mCommAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.g
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                AllCourseSubFragment.lambda$initViews$0(baseQuickAdapter2, view, i2);
            }
        });
        for (int i2 = 0; i2 < 30; i2++) {
            this.dataList.add(new CourseBeanTest("数据：" + i2));
        }
        this.mCommAdapter.setList(this.dataList);
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.AllCourseSubFragment.3
            @Override // java.lang.Runnable
            public void run() {
                AllCourseSubFragment.this.finishRefresh(true);
            }
        }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();
        this.channel_id = arguments != null ? arguments.getInt("id", 0) : 0;
    }

    public void onEventMainThread(OnRefreshEvent event) {
    }

    public void onEventMainThread(RefreshChannelData data) {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(final String str) {
        if (str.equals("isRefaulfCircle")) {
            this.allCourseSubListview.smoothScrollToPosition(0);
            this.page = 1;
            getData();
        } else if (str.equals("ebook_logout")) {
            UserConfig.getInstance().logOut();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(EventBusMessage<String> eventBusMessage) {
        if (eventBusMessage.getKey().equals("returnjump")) {
            this.page = 1;
            getData();
        }
    }
}
