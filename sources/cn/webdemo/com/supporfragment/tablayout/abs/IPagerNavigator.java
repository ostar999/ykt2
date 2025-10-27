package cn.webdemo.com.supporfragment.tablayout.abs;

/* loaded from: classes.dex */
public interface IPagerNavigator {
    void notifyDataSetChanged();

    void onAttachToMagicIndicator();

    void onDetachFromMagicIndicator();

    void onPageScrollStateChanged(int i2);

    void onPageScrolled(int i2, float f2, int i3);

    void onPageSelected(int i2);
}
