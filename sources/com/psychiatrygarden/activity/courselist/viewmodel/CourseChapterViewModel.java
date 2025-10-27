package com.psychiatrygarden.activity.courselist.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.activity.courselist.bean.CourseListChapterBean;
import com.psychiatrygarden.activity.courselist.bean.CourseVideoListBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.courselist.utils.DataRepository;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseChapterViewModel extends ViewModel {
    private MediatorLiveData<List<CourseVideoListBean.DataBean>> cDataBean = new MediatorLiveData<>();
    private MediatorLiveData<List<CourseListChapterBean.DataBean>> ccDataBean = new MediatorLiveData<>();
    private MediatorLiveData<List<CourseCalalogueBean.DataNewBean.DataBean>> courseCalalogueBean = new MediatorLiveData<>();
    private DataRepository mDataRepository = new DataRepository();

    public LiveData<List<CourseListChapterBean.DataBean>> getChapterInfo() {
        return this.ccDataBean;
    }

    public LiveData<List<CourseVideoListBean.DataBean>> getChapterVideoInfo() {
        return this.cDataBean;
    }

    public LiveData<List<CourseCalalogueBean.DataNewBean.DataBean>> getCourseCalalogueBean() {
        return this.courseCalalogueBean;
    }

    public LiveData<List<VideoDownBean>> getDownloadInfo(String cId) {
        return this.mDataRepository.getDownloadInfo(cId);
    }

    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
    }

    public void putData(VideoDownBean... videoDownBean) {
        this.mDataRepository.putVideoData(videoDownBean);
    }

    public void setCcDataBean(List<CourseListChapterBean.DataBean> ccDataBeans) {
        this.ccDataBean.postValue(ccDataBeans);
    }

    public void setCourseCalalogueBean(List<CourseCalalogueBean.DataNewBean.DataBean> courseCalalogueBeans) {
        this.courseCalalogueBean.postValue(courseCalalogueBeans);
    }

    public void setcDataBean(List<CourseVideoListBean.DataBean> cDataBeans) {
        this.cDataBean.postValue(cDataBeans);
    }

    public void updataStatusData(int mStatus, String[] courseId) {
        this.mDataRepository.updateData(mStatus, courseId);
    }

    public void updateVideoStatus(CourseVideoListBean.DataBean bean) {
        this.mDataRepository.updateVideo(bean);
    }
}
