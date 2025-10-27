package com.ykb.ebook.weight;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.psychiatrygarden.activity.z3;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.BookCommentsAdapter;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookReview;

/* loaded from: classes8.dex */
public class SubFloorFactorys<T> {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$8() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$0(View view) {
        Toast.makeText(view.getContext(), "已认证", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildSubFloor$1(FloorCommentListenter floorCommentListenter, BookReview bookReview, TextView textView, Object obj, View view) {
        if (ViewExtensionsKt.doubleClick()) {
            floorCommentListenter.commListSupport(bookReview.isSupport() + "", bookReview.getId(), bookReview.getId());
            if (bookReview.isOpposition() == 1) {
                return;
            }
            String str = "0";
            if (bookReview.isSupport() == 1) {
                bookReview.setSupport(0);
                try {
                    String supportCount = bookReview.getSupportCount();
                    if (TextUtils.isEmpty(supportCount)) {
                        supportCount = "0";
                    }
                    if (supportCount.trim().equals("0")) {
                        bookReview.setSupportCount("0");
                    } else {
                        bookReview.setSupportCount((Integer.parseInt(supportCount) - 1) + "");
                    }
                    textView.setText(String.format("赞同(%s)", bookReview.getSupportCount()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                Toast_pop(textView, 0);
                bookReview.setSupport(1);
                try {
                    String supportCount2 = bookReview.getSupportCount();
                    if (!TextUtils.isEmpty(supportCount2)) {
                        str = supportCount2;
                    }
                    bookReview.setSupportCount((Integer.parseInt(str) + 1) + "");
                    textView.setText(String.format("已赞同(%s)", bookReview.getSupportCount()));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            if (obj instanceof BookCommentsAdapter) {
                ((BookCommentsAdapter) obj).notifyDataSetChanged();
            } else if (obj instanceof CommonAdapter) {
                ((CommonAdapter) obj).notifyDataSetChanged();
            } else if (obj instanceof BaseQuickAdapter) {
                ((BaseQuickAdapter) obj).notifyDataSetChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildSubFloor$2(FloorCommentListenter floorCommentListenter, BookReview bookReview, TextView textView, Object obj, View view) {
        if (ViewExtensionsKt.doubleClick()) {
            floorCommentListenter.commListOppose(bookReview.isOpposition() + "", bookReview.getId(), bookReview.getId());
            if (bookReview.isSupport() == 1) {
                return;
            }
            String str = "0";
            if (bookReview.isOpposition() == 1) {
                bookReview.setOpposition(0);
                try {
                    String oppositionCount = bookReview.getOppositionCount();
                    if (TextUtils.isEmpty(oppositionCount)) {
                        oppositionCount = "0";
                    }
                    if (oppositionCount.trim().equals("0")) {
                        bookReview.setOppositionCount("0");
                    } else {
                        bookReview.setOppositionCount((Integer.parseInt(oppositionCount) - 1) + "");
                    }
                    textView.setText(String.format("反对(%s)", bookReview.getOppositionCount()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                Toast_pop(textView, 1);
                bookReview.setOpposition(1);
                try {
                    String oppositionCount2 = bookReview.getOppositionCount();
                    if (!TextUtils.isEmpty(oppositionCount2)) {
                        str = oppositionCount2;
                    }
                    bookReview.setOppositionCount((Integer.parseInt(str) + 1) + "");
                    textView.setText(String.format("已反对(%s)", bookReview.getOppositionCount()));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            if (obj instanceof BookCommentsAdapter) {
                ((BookCommentsAdapter) obj).notifyDataSetChanged();
            } else if (obj instanceof CommonAdapter) {
                ((CommonAdapter) obj).notifyDataSetChanged();
            } else if (obj instanceof BaseQuickAdapter) {
                ((BaseQuickAdapter) obj).notifyDataSetChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$3(FloorCommentListenter floorCommentListenter, BookReview bookReview, View view) {
        if (ViewExtensionsKt.doubleClick()) {
            floorCommentListenter.commListReply(bookReview, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$4(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$5(View view, BookReview bookReview) {
        try {
            new XPopup.Builder(view.getContext()).asImageViewer(null, bookReview.getPicture(), new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$6(BookReview bookReview, View view) {
        if (ViewExtensionsKt.doubleClick()) {
            if (!"0".equals(bookReview.is_logout())) {
                Toast.makeText(view.getContext(), "该用户已注销", 0).show();
                return;
            }
            String userId = bookReview.getUserId();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("ebook://ykb_user_info/?user_id=" + userId));
            view.getContext().startActivity(intent);
        }
    }

    public void Toast_pop(View view, int i2) {
        ImageView imageView = new ImageView(view.getContext());
        if (i2 == 0) {
            imageView.setBackgroundResource(R.drawable.zan_animation);
        } else {
            imageView.setBackgroundResource(R.drawable.cai_animation);
        }
        PopupWindow popupWindow = new PopupWindow(imageView, -2, -2);
        if (i2 == 0) {
            popupWindow.setAnimationStyle(R.style.popshowzan);
        } else {
            popupWindow.setAnimationStyle(R.style.popshowcai);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.ykb.ebook.weight.z0
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                SubFloorFactorys.lambda$Toast_pop$8();
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        if (i2 == 0) {
            popupWindow.showAtLocation(view, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        } else {
            popupWindow.showAtLocation(view, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        }
        new Handler().postDelayed(new z3(popupWindow), 1000L);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0271  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View buildSubFloor(final com.ykb.ebook.model.BookReview r19, android.view.ViewGroup r20, final com.ykb.ebook.weight.FloorCommentListenter r21, boolean r22, final T r23, java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 809
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.weight.SubFloorFactorys.buildSubFloor(com.ykb.ebook.model.BookReview, android.view.ViewGroup, com.ykb.ebook.weight.FloorCommentListenter, boolean, java.lang.Object, java.lang.String):android.view.View");
    }

    public View buildSubHideFloor(BookReview bookReview, ViewGroup viewGroup) {
        View viewInflate = ((LayoutInflater) viewGroup.getContext().getSystemService("layout_inflater")).inflate(R.layout.floor_comment_item, (ViewGroup) null);
        RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.show_sub_floor_content);
        RelativeLayout relativeLayout2 = (RelativeLayout) viewInflate.findViewById(R.id.hide_sub_floor_content);
        relativeLayout.setVisibility(8);
        relativeLayout2.setVisibility(0);
        TextView textView = (TextView) viewInflate.findViewById(R.id.hide_text);
        if (ReadConfig.INSTANCE.getColorMode() != 2) {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_ebook_expand_arrow, 0, 0, 0);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_ebook_expand_arrow_night, 0, 0, 0);
        }
        viewInflate.findViewById(R.id.hide_pb).setVisibility(8);
        return viewInflate;
    }
}
