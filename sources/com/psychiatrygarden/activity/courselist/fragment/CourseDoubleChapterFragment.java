package com.psychiatrygarden.activity.courselist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.beizi.fusion.widget.ScrollClickView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.courselist.CourseDownLoadActivity;
import com.psychiatrygarden.activity.courselist.CourseVideoListActivity;
import com.psychiatrygarden.activity.courselist.adapter.CourseListAdapter;
import com.psychiatrygarden.activity.courselist.bean.CourseListChapterBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.courselist.utils.DataRepository;
import com.psychiatrygarden.activity.courselist.viewmodel.CourseChapterViewModel;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class CourseDoubleChapterFragment extends BaseFragment {
    private CourseListAdapter adapter;
    private ExpandableListView elv_tiku_question;
    private LinearLayout lineimg;
    private CourseChapterViewModel viewModel;
    private int tempPosition = 0;
    private int sign = -1;
    private List<CourseListChapterBean.DataBean> dataList = new ArrayList();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        this.dataList = list;
        putData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initViews$1(ExpandableListView expandableListView, View view, int i2, long j2) {
        if (this.dataList.get(i2).getChildren().size() != 0) {
            mViewControl(i2);
            return true;
        }
        if (this.dataList.get(i2).getTotal() == 0) {
            ToastUtil.shortToast(getActivity(), "此学科下无视频！");
            return true;
        }
        gotoModel(this.dataList.get(i2).getChapter_id() + "", "0", this.dataList.get(i2).getTitle(), "");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initViews$2(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        if (!"".equals("" + this.dataList.get(i2).getChildren().get(i3).getChapter_id())) {
            if (this.dataList.get(i2).getChildren().get(i3).getTotal() == 0) {
                ToastUtil.shortToast(getActivity(), "此章节下无视频！");
                return true;
            }
            gotoModel(this.dataList.get(i2).getChapter_id() + "", this.dataList.get(i2).getChildren().get(i3).getChapter_id() + "", this.dataList.get(i2).getTitle(), this.dataList.get(i2).getChildren().get(i3).getTitle());
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(int i2) {
        int i3 = this.tempPosition;
        if (i3 != i2) {
            this.elv_tiku_question.collapseGroup(i3);
            this.tempPosition = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putData$5(ObservableEmitter observableEmitter) throws Exception {
        String string = getArguments() != null ? getArguments().getString("vidteaching_id") : "";
        ArrayList arrayList = new ArrayList();
        List<VideoDownBean> videoDownLoadInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadInfo(string);
        if (videoDownLoadInfo != null && videoDownLoadInfo.size() > 0) {
            for (int i2 = 0; i2 < videoDownLoadInfo.size(); i2++) {
                for (int i3 = 0; i3 < this.dataList.size(); i3++) {
                    if ((this.dataList.get(i3).getChapter_id() + "").equals(videoDownLoadInfo.get(i2).getParent_id())) {
                        this.dataList.get(i3).setIsdown("1");
                        if (this.dataList.get(i3).getChildren() != null) {
                            for (int i4 = 0; i4 < this.dataList.get(i3).getChildren().size(); i4++) {
                                if ((this.dataList.get(i3).getChildren().get(i4).getChapter_id() + "").equals(videoDownLoadInfo.get(i2).getChapter_id())) {
                                    this.dataList.get(i3).getChildren().get(i4).setIsdown("1");
                                }
                            }
                        }
                    }
                }
            }
            for (int i5 = 0; i5 < this.dataList.size(); i5++) {
                if ("1".equals(this.dataList.get(i5).getIsdown())) {
                    if (this.dataList.get(i5).getChildren() != null) {
                        ArrayList<CourseListChapterBean.DataBean.ChildrenBean> arrayList2 = new ArrayList<>();
                        for (int i6 = 0; i6 < this.dataList.get(i5).getChildren().size(); i6++) {
                            if ("1".equals(this.dataList.get(i5).getChildren().get(i6).getIsdown())) {
                                arrayList2.add(this.dataList.get(i5).getChildren().get(i6));
                            }
                        }
                        Collections.sort(arrayList2);
                        this.dataList.get(i5).setChildren(arrayList2);
                    }
                    arrayList.add(this.dataList.get(i5));
                }
            }
        }
        Collections.sort(arrayList);
        observableEmitter.onNext(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putData$6(List list) throws Exception {
        if (list == null || list.size() <= 0) {
            this.lineimg.setVisibility(0);
        } else {
            this.lineimg.setVisibility(8);
        }
        this.adapter.setNewData(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$4(List list) {
        this.viewModel.setCcDataBean(list);
    }

    public static CourseDoubleChapterFragment newInstance() {
        Bundle bundle = new Bundle();
        CourseDoubleChapterFragment courseDoubleChapterFragment = new CourseDoubleChapterFragment();
        courseDoubleChapterFragment.setArguments(bundle);
        return courseDoubleChapterFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_coursed;
    }

    public void gotoModel(String parent_id, String chapter_id, String subtitle, String childtitle) {
        if (getActivity() instanceof CourseDownLoadActivity) {
            Intent intent = new Intent(getActivity(), (Class<?>) CourseDownLoadActivity.class);
            intent.putExtra("series", "0");
            intent.putExtra("vidteaching_id", getArguments().getString("vidteaching_id") + "");
            intent.putExtra("chapter_id", getArguments().getString("chapter_id"));
            intent.putExtra("parent_id", getArguments().getString("parent_id"));
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent(getActivity(), (Class<?>) CourseVideoListActivity.class);
        intent2.putExtra("series", "" + getArguments().getString("series"));
        intent2.putExtra("vidteaching_id", "" + getArguments().getString("vidteaching_id"));
        intent2.putExtra("type", "" + getArguments().getString("type"));
        intent2.putExtra("chapter_id", "" + chapter_id);
        intent2.putExtra("parent_id", "" + parent_id);
        intent2.putExtra("subtitle", "" + subtitle);
        intent2.putExtra("childtitle", "" + childtitle);
        startActivity(intent2);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.lineimg = (LinearLayout) holder.get(R.id.lineimg);
        this.elv_tiku_question = (ExpandableListView) holder.get(R.id.elv_tiku_question);
        CourseChapterViewModel courseChapterViewModel = (CourseChapterViewModel) ViewModelProviders.of(this).get(CourseChapterViewModel.class);
        this.viewModel = courseChapterViewModel;
        courseChapterViewModel.getChapterInfo().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.c1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11932a.lambda$initViews$0((List) obj);
            }
        });
        if (getActivity() instanceof CourseDownLoadActivity) {
            this.adapter = new CourseListAdapter(getActivity(), this.dataList, ScrollClickView.DIR_DOWN);
        } else {
            this.adapter = new CourseListAdapter(getActivity(), this.dataList, "" + getArguments().getString("type"));
        }
        this.elv_tiku_question.setAdapter(this.adapter);
        this.elv_tiku_question.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.d1
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public final boolean onGroupClick(ExpandableListView expandableListView, View view, int i2, long j2) {
                return this.f11936a.lambda$initViews$1(expandableListView, view, i2, j2);
            }
        });
        this.elv_tiku_question.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.e1
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f11941a.lambda$initViews$2(expandableListView, view, i2, i3, j2);
            }
        });
        this.elv_tiku_question.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.f1
            @Override // android.widget.ExpandableListView.OnGroupExpandListener
            public final void onGroupExpand(int i2) {
                this.f11947a.lambda$initViews$3(i2);
            }
        });
        setData();
    }

    public void mViewControl(int groupPosition) {
        int i2 = this.sign;
        if (i2 == -1) {
            this.elv_tiku_question.expandGroup(groupPosition);
            this.elv_tiku_question.setSelectedGroup(groupPosition);
            this.sign = groupPosition;
        } else if (i2 == groupPosition) {
            this.elv_tiku_question.collapseGroup(i2);
            this.sign = -1;
        } else {
            this.elv_tiku_question.collapseGroup(i2);
            this.elv_tiku_question.expandGroup(groupPosition);
            this.elv_tiku_question.setSelectedGroup(groupPosition);
            this.sign = groupPosition;
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.compositeDisposable.isDisposed()) {
            return;
        }
        this.compositeDisposable.dispose();
    }

    public void putData() {
        if (getActivity() instanceof CourseDownLoadActivity) {
            this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.fragment.g1
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    this.f11952a.lambda$putData$5(observableEmitter);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.fragment.h1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f11957c.lambda$putData$6((List) obj);
                }
            }));
            return;
        }
        List<CourseListChapterBean.DataBean> list = this.dataList;
        if (list == null || list.size() <= 0) {
            this.lineimg.setVisibility(0);
        } else {
            this.lineimg.setVisibility(8);
        }
        this.adapter.setNewData(this.dataList);
    }

    public void setData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (getArguments() != null) {
            getArguments().getString("vidteaching_id");
        }
        ajaxParams.put("vidteaching_id", "" + getArguments().getString("vidteaching_id"));
        ajaxParams.put("type", "" + getArguments().getString("type"));
        new DataRepository().getChapterInfo(ajaxParams).observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.i1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11963a.lambda$setData$4((List) obj);
            }
        });
    }
}
