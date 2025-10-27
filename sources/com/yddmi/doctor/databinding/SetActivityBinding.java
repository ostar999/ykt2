package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.catchpig.mvvm.widget.DotView;
import com.catchpig.mvvm.widget.dsbridge.DWebView;
import com.noober.background.view.BLConstraintLayout;
import com.noober.background.view.BLEditText;
import com.noober.background.view.BLFrameLayout;
import com.noober.background.view.BLTextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yddmi.doctor.R;
import net.center.blurview.ShapeBlurView;

/* loaded from: classes6.dex */
public final class SetActivityBinding implements ViewBinding {

    @NonNull
    public final ConstraintLayout accountCl;

    @NonNull
    public final ImageView backImgv;

    @NonNull
    public final ImageView bankImgv;

    @NonNull
    public final TextView bankTipTv;

    @NonNull
    public final ShapeBlurView blurView;

    @NonNull
    public final EditText codeEt;

    @NonNull
    public final TextView codeTv;

    @NonNull
    public final ConstraintLayout detailsCl;

    @NonNull
    public final DotView dotV;

    @NonNull
    public final ImageView downImgv;

    @NonNull
    public final BLEditText et;

    @NonNull
    public final ConstraintLayout feedbackCl;

    @NonNull
    public final TextView feedbackTv;

    @NonNull
    public final BLFrameLayout fiveBfl;

    @NonNull
    public final RecyclerView fiveRv;

    @NonNull
    public final SmartRefreshLayout fiveSrl;

    @NonNull
    public final TextView fiveTv;

    @NonNull
    public final TextView fourTv;

    @NonNull
    public final BLTextView goSubmitTv;

    @NonNull
    public final TextView hiTv;

    @NonNull
    public final ConstraintLayout itemBankCl;

    @NonNull
    public final ConstraintLayout itemLogeoffCl;

    @NonNull
    public final ConstraintLayout itemPswCl;

    @NonNull
    public final ConstraintLayout itemWxCl;

    @NonNull
    public final ConstraintLayout leftCl;

    @NonNull
    public final TextView leftTv;

    @NonNull
    public final ImageView line2Imgv;

    @NonNull
    public final ImageView line3Imgv;

    @NonNull
    public final ImageView line4Imgv;

    @NonNull
    public final ImageView line5Imgv;

    @NonNull
    public final ImageView lineImgv;

    @NonNull
    public final ImageView logeoffImgv;

    @NonNull
    public final TextView logeoffTipTv;

    @NonNull
    public final ConstraintLayout logoffCl;

    @NonNull
    public final TextView logoutTv;

    @NonNull
    public final ImageView msg1ScrollImgv;

    @NonNull
    public final BLFrameLayout msgBfl;

    @NonNull
    public final ConstraintLayout msgCl;

    @NonNull
    public final ConstraintLayout msgDetailsCl;

    @NonNull
    public final FrameLayout msgFl;

    @NonNull
    public final ImageView msgHiImgv;

    @NonNull
    public final TextView msgHiTv;

    @NonNull
    public final ImageView msgImgv;

    @NonNull
    public final ImageView msgLineImgv;

    @NonNull
    public final RecyclerView msgRv;

    @NonNull
    public final ImageView msgScrol0lImgv;

    @NonNull
    public final ImageView msgScrollImgv;

    @NonNull
    public final ScrollView msgScrollV;

    @NonNull
    public final SmartRefreshLayout msgSrl;

    @NonNull
    public final ImageView msgTilteImgv;

    @NonNull
    public final TextView msgTitleTv;

    @NonNull
    public final ImageView msgUserImgv;

    @NonNull
    public final TextView oneTv;

    @NonNull
    public final BLConstraintLayout peopleCl;

    @NonNull
    public final EditText phoneEt;

    @NonNull
    public final TextView phoneTv;

    @NonNull
    public final BLConstraintLayout priacyCl;

    @NonNull
    public final EditText pswAgainEt;

    @NonNull
    public final TextView pswAgainTv;

    @NonNull
    public final ConstraintLayout pswCl;

    @NonNull
    public final EditText pswEt;

    @NonNull
    public final ImageView pswImgv;

    @NonNull
    public final TextView pswTipTv;

    @NonNull
    public final TextView pswTv;

    @NonNull
    public final ConstraintLayout rightCl;

    @NonNull
    public final TextView rightTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ScrollView scrollV;

    @NonNull
    public final BLTextView sendCodeTv;

    @NonNull
    public final ConstraintLayout submitCl;

    @NonNull
    public final TextView submitTv;

    @NonNull
    public final TextView textView3;

    @NonNull
    public final TextView textView4;

    @NonNull
    public final TextView threeTv;

    @NonNull
    public final ImageView tilteImgv;

    @NonNull
    public final TextView tipTv;

    @NonNull
    public final ConstraintLayout titleCl;

    @NonNull
    public final ImageView top1Imgv;

    @NonNull
    public final ImageView top2Imgv;

    @NonNull
    public final ImageView topImgv;

    @NonNull
    public final FrameLayout twoFl;

    @NonNull
    public final TextView twoTv;

    @NonNull
    public final TextView userTv;

    @NonNull
    public final View view;

    @NonNull
    public final ImageView web2Imgv;

    @NonNull
    public final DWebView web2V;

    @NonNull
    public final ImageView webImgv;

    @NonNull
    public final TextView webTitle2Tv;

    @NonNull
    public final TextView webTitleTv;

    @NonNull
    public final DWebView webV;

    @NonNull
    public final ImageView wxImgv;

    @NonNull
    public final TextView wxTip1Tv;

    @NonNull
    public final TextView wxTipTv;

    @NonNull
    public final ImageView x110Imgv;

    @NonNull
    public final ImageView x111Imgv;

    @NonNull
    public final ImageView x116Imgv;

    private SetActivityBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ConstraintLayout constraintLayout2, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull TextView textView, @NonNull ShapeBlurView shapeBlurView, @NonNull EditText editText, @NonNull TextView textView2, @NonNull ConstraintLayout constraintLayout3, @NonNull DotView dotView, @NonNull ImageView imageView3, @NonNull BLEditText bLEditText, @NonNull ConstraintLayout constraintLayout4, @NonNull TextView textView3, @NonNull BLFrameLayout bLFrameLayout, @NonNull RecyclerView recyclerView, @NonNull SmartRefreshLayout smartRefreshLayout, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull BLTextView bLTextView, @NonNull TextView textView6, @NonNull ConstraintLayout constraintLayout5, @NonNull ConstraintLayout constraintLayout6, @NonNull ConstraintLayout constraintLayout7, @NonNull ConstraintLayout constraintLayout8, @NonNull ConstraintLayout constraintLayout9, @NonNull TextView textView7, @NonNull ImageView imageView4, @NonNull ImageView imageView5, @NonNull ImageView imageView6, @NonNull ImageView imageView7, @NonNull ImageView imageView8, @NonNull ImageView imageView9, @NonNull TextView textView8, @NonNull ConstraintLayout constraintLayout10, @NonNull TextView textView9, @NonNull ImageView imageView10, @NonNull BLFrameLayout bLFrameLayout2, @NonNull ConstraintLayout constraintLayout11, @NonNull ConstraintLayout constraintLayout12, @NonNull FrameLayout frameLayout, @NonNull ImageView imageView11, @NonNull TextView textView10, @NonNull ImageView imageView12, @NonNull ImageView imageView13, @NonNull RecyclerView recyclerView2, @NonNull ImageView imageView14, @NonNull ImageView imageView15, @NonNull ScrollView scrollView, @NonNull SmartRefreshLayout smartRefreshLayout2, @NonNull ImageView imageView16, @NonNull TextView textView11, @NonNull ImageView imageView17, @NonNull TextView textView12, @NonNull BLConstraintLayout bLConstraintLayout, @NonNull EditText editText2, @NonNull TextView textView13, @NonNull BLConstraintLayout bLConstraintLayout2, @NonNull EditText editText3, @NonNull TextView textView14, @NonNull ConstraintLayout constraintLayout13, @NonNull EditText editText4, @NonNull ImageView imageView18, @NonNull TextView textView15, @NonNull TextView textView16, @NonNull ConstraintLayout constraintLayout14, @NonNull TextView textView17, @NonNull ScrollView scrollView2, @NonNull BLTextView bLTextView2, @NonNull ConstraintLayout constraintLayout15, @NonNull TextView textView18, @NonNull TextView textView19, @NonNull TextView textView20, @NonNull TextView textView21, @NonNull ImageView imageView19, @NonNull TextView textView22, @NonNull ConstraintLayout constraintLayout16, @NonNull ImageView imageView20, @NonNull ImageView imageView21, @NonNull ImageView imageView22, @NonNull FrameLayout frameLayout2, @NonNull TextView textView23, @NonNull TextView textView24, @NonNull View view, @NonNull ImageView imageView23, @NonNull DWebView dWebView, @NonNull ImageView imageView24, @NonNull TextView textView25, @NonNull TextView textView26, @NonNull DWebView dWebView2, @NonNull ImageView imageView25, @NonNull TextView textView27, @NonNull TextView textView28, @NonNull ImageView imageView26, @NonNull ImageView imageView27, @NonNull ImageView imageView28) {
        this.rootView = constraintLayout;
        this.accountCl = constraintLayout2;
        this.backImgv = imageView;
        this.bankImgv = imageView2;
        this.bankTipTv = textView;
        this.blurView = shapeBlurView;
        this.codeEt = editText;
        this.codeTv = textView2;
        this.detailsCl = constraintLayout3;
        this.dotV = dotView;
        this.downImgv = imageView3;
        this.et = bLEditText;
        this.feedbackCl = constraintLayout4;
        this.feedbackTv = textView3;
        this.fiveBfl = bLFrameLayout;
        this.fiveRv = recyclerView;
        this.fiveSrl = smartRefreshLayout;
        this.fiveTv = textView4;
        this.fourTv = textView5;
        this.goSubmitTv = bLTextView;
        this.hiTv = textView6;
        this.itemBankCl = constraintLayout5;
        this.itemLogeoffCl = constraintLayout6;
        this.itemPswCl = constraintLayout7;
        this.itemWxCl = constraintLayout8;
        this.leftCl = constraintLayout9;
        this.leftTv = textView7;
        this.line2Imgv = imageView4;
        this.line3Imgv = imageView5;
        this.line4Imgv = imageView6;
        this.line5Imgv = imageView7;
        this.lineImgv = imageView8;
        this.logeoffImgv = imageView9;
        this.logeoffTipTv = textView8;
        this.logoffCl = constraintLayout10;
        this.logoutTv = textView9;
        this.msg1ScrollImgv = imageView10;
        this.msgBfl = bLFrameLayout2;
        this.msgCl = constraintLayout11;
        this.msgDetailsCl = constraintLayout12;
        this.msgFl = frameLayout;
        this.msgHiImgv = imageView11;
        this.msgHiTv = textView10;
        this.msgImgv = imageView12;
        this.msgLineImgv = imageView13;
        this.msgRv = recyclerView2;
        this.msgScrol0lImgv = imageView14;
        this.msgScrollImgv = imageView15;
        this.msgScrollV = scrollView;
        this.msgSrl = smartRefreshLayout2;
        this.msgTilteImgv = imageView16;
        this.msgTitleTv = textView11;
        this.msgUserImgv = imageView17;
        this.oneTv = textView12;
        this.peopleCl = bLConstraintLayout;
        this.phoneEt = editText2;
        this.phoneTv = textView13;
        this.priacyCl = bLConstraintLayout2;
        this.pswAgainEt = editText3;
        this.pswAgainTv = textView14;
        this.pswCl = constraintLayout13;
        this.pswEt = editText4;
        this.pswImgv = imageView18;
        this.pswTipTv = textView15;
        this.pswTv = textView16;
        this.rightCl = constraintLayout14;
        this.rightTv = textView17;
        this.scrollV = scrollView2;
        this.sendCodeTv = bLTextView2;
        this.submitCl = constraintLayout15;
        this.submitTv = textView18;
        this.textView3 = textView19;
        this.textView4 = textView20;
        this.threeTv = textView21;
        this.tilteImgv = imageView19;
        this.tipTv = textView22;
        this.titleCl = constraintLayout16;
        this.top1Imgv = imageView20;
        this.top2Imgv = imageView21;
        this.topImgv = imageView22;
        this.twoFl = frameLayout2;
        this.twoTv = textView23;
        this.userTv = textView24;
        this.view = view;
        this.web2Imgv = imageView23;
        this.web2V = dWebView;
        this.webImgv = imageView24;
        this.webTitle2Tv = textView25;
        this.webTitleTv = textView26;
        this.webV = dWebView2;
        this.wxImgv = imageView25;
        this.wxTip1Tv = textView27;
        this.wxTipTv = textView28;
        this.x110Imgv = imageView26;
        this.x111Imgv = imageView27;
        this.x116Imgv = imageView28;
    }

    @NonNull
    public static SetActivityBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.accountCl;
        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
        if (constraintLayout != null) {
            i2 = R.id.backImgv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.bankImgv;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView2 != null) {
                    i2 = R.id.bankTipTv;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView != null) {
                        i2 = R.id.blurView;
                        ShapeBlurView shapeBlurView = (ShapeBlurView) ViewBindings.findChildViewById(view, i2);
                        if (shapeBlurView != null) {
                            i2 = R.id.codeEt;
                            EditText editText = (EditText) ViewBindings.findChildViewById(view, i2);
                            if (editText != null) {
                                i2 = R.id.codeTv;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView2 != null) {
                                    i2 = R.id.detailsCl;
                                    ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                    if (constraintLayout2 != null) {
                                        i2 = R.id.dotV;
                                        DotView dotView = (DotView) ViewBindings.findChildViewById(view, i2);
                                        if (dotView != null) {
                                            i2 = R.id.downImgv;
                                            ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                            if (imageView3 != null) {
                                                i2 = R.id.et;
                                                BLEditText bLEditText = (BLEditText) ViewBindings.findChildViewById(view, i2);
                                                if (bLEditText != null) {
                                                    i2 = R.id.feedbackCl;
                                                    ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                    if (constraintLayout3 != null) {
                                                        i2 = R.id.feedbackTv;
                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView3 != null) {
                                                            i2 = R.id.fiveBfl;
                                                            BLFrameLayout bLFrameLayout = (BLFrameLayout) ViewBindings.findChildViewById(view, i2);
                                                            if (bLFrameLayout != null) {
                                                                i2 = R.id.fiveRv;
                                                                RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                                                if (recyclerView != null) {
                                                                    i2 = R.id.fiveSrl;
                                                                    SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) ViewBindings.findChildViewById(view, i2);
                                                                    if (smartRefreshLayout != null) {
                                                                        i2 = R.id.fiveTv;
                                                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (textView4 != null) {
                                                                            i2 = R.id.fourTv;
                                                                            TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (textView5 != null) {
                                                                                i2 = R.id.goSubmitTv;
                                                                                BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                                                if (bLTextView != null) {
                                                                                    i2 = R.id.hiTv;
                                                                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (textView6 != null) {
                                                                                        i2 = R.id.itemBankCl;
                                                                                        ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                        if (constraintLayout4 != null) {
                                                                                            i2 = R.id.itemLogeoffCl;
                                                                                            ConstraintLayout constraintLayout5 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                            if (constraintLayout5 != null) {
                                                                                                i2 = R.id.itemPswCl;
                                                                                                ConstraintLayout constraintLayout6 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                if (constraintLayout6 != null) {
                                                                                                    i2 = R.id.itemWxCl;
                                                                                                    ConstraintLayout constraintLayout7 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                    if (constraintLayout7 != null) {
                                                                                                        i2 = R.id.leftCl;
                                                                                                        ConstraintLayout constraintLayout8 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                        if (constraintLayout8 != null) {
                                                                                                            i2 = R.id.leftTv;
                                                                                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                            if (textView7 != null) {
                                                                                                                i2 = R.id.line2Imgv;
                                                                                                                ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                if (imageView4 != null) {
                                                                                                                    i2 = R.id.line3Imgv;
                                                                                                                    ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                    if (imageView5 != null) {
                                                                                                                        i2 = R.id.line4Imgv;
                                                                                                                        ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                        if (imageView6 != null) {
                                                                                                                            i2 = R.id.line5Imgv;
                                                                                                                            ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                            if (imageView7 != null) {
                                                                                                                                i2 = R.id.lineImgv;
                                                                                                                                ImageView imageView8 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                if (imageView8 != null) {
                                                                                                                                    i2 = R.id.logeoffImgv;
                                                                                                                                    ImageView imageView9 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                    if (imageView9 != null) {
                                                                                                                                        i2 = R.id.logeoffTipTv;
                                                                                                                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                        if (textView8 != null) {
                                                                                                                                            i2 = R.id.logoffCl;
                                                                                                                                            ConstraintLayout constraintLayout9 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                            if (constraintLayout9 != null) {
                                                                                                                                                i2 = R.id.logoutTv;
                                                                                                                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                if (textView9 != null) {
                                                                                                                                                    i2 = R.id.msg1ScrollImgv;
                                                                                                                                                    ImageView imageView10 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                    if (imageView10 != null) {
                                                                                                                                                        i2 = R.id.msgBfl;
                                                                                                                                                        BLFrameLayout bLFrameLayout2 = (BLFrameLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                        if (bLFrameLayout2 != null) {
                                                                                                                                                            i2 = R.id.msgCl;
                                                                                                                                                            ConstraintLayout constraintLayout10 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                            if (constraintLayout10 != null) {
                                                                                                                                                                i2 = R.id.msgDetailsCl;
                                                                                                                                                                ConstraintLayout constraintLayout11 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                if (constraintLayout11 != null) {
                                                                                                                                                                    i2 = R.id.msgFl;
                                                                                                                                                                    FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                    if (frameLayout != null) {
                                                                                                                                                                        i2 = R.id.msgHiImgv;
                                                                                                                                                                        ImageView imageView11 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                        if (imageView11 != null) {
                                                                                                                                                                            i2 = R.id.msgHiTv;
                                                                                                                                                                            TextView textView10 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                            if (textView10 != null) {
                                                                                                                                                                                i2 = R.id.msgImgv;
                                                                                                                                                                                ImageView imageView12 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                if (imageView12 != null) {
                                                                                                                                                                                    i2 = R.id.msgLineImgv;
                                                                                                                                                                                    ImageView imageView13 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                    if (imageView13 != null) {
                                                                                                                                                                                        i2 = R.id.msgRv;
                                                                                                                                                                                        RecyclerView recyclerView2 = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                        if (recyclerView2 != null) {
                                                                                                                                                                                            i2 = R.id.msgScrol0lImgv;
                                                                                                                                                                                            ImageView imageView14 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                            if (imageView14 != null) {
                                                                                                                                                                                                i2 = R.id.msgScrollImgv;
                                                                                                                                                                                                ImageView imageView15 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                if (imageView15 != null) {
                                                                                                                                                                                                    i2 = R.id.msgScrollV;
                                                                                                                                                                                                    ScrollView scrollView = (ScrollView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                    if (scrollView != null) {
                                                                                                                                                                                                        i2 = R.id.msgSrl;
                                                                                                                                                                                                        SmartRefreshLayout smartRefreshLayout2 = (SmartRefreshLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                        if (smartRefreshLayout2 != null) {
                                                                                                                                                                                                            i2 = R.id.msgTilteImgv;
                                                                                                                                                                                                            ImageView imageView16 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                            if (imageView16 != null) {
                                                                                                                                                                                                                i2 = R.id.msgTitleTv;
                                                                                                                                                                                                                TextView textView11 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                if (textView11 != null) {
                                                                                                                                                                                                                    i2 = R.id.msgUserImgv;
                                                                                                                                                                                                                    ImageView imageView17 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                    if (imageView17 != null) {
                                                                                                                                                                                                                        i2 = R.id.oneTv;
                                                                                                                                                                                                                        TextView textView12 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                        if (textView12 != null) {
                                                                                                                                                                                                                            i2 = R.id.peopleCl;
                                                                                                                                                                                                                            BLConstraintLayout bLConstraintLayout = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                            if (bLConstraintLayout != null) {
                                                                                                                                                                                                                                i2 = R.id.phoneEt;
                                                                                                                                                                                                                                EditText editText2 = (EditText) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                if (editText2 != null) {
                                                                                                                                                                                                                                    i2 = R.id.phoneTv;
                                                                                                                                                                                                                                    TextView textView13 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                    if (textView13 != null) {
                                                                                                                                                                                                                                        i2 = R.id.priacyCl;
                                                                                                                                                                                                                                        BLConstraintLayout bLConstraintLayout2 = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                        if (bLConstraintLayout2 != null) {
                                                                                                                                                                                                                                            i2 = R.id.pswAgainEt;
                                                                                                                                                                                                                                            EditText editText3 = (EditText) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                            if (editText3 != null) {
                                                                                                                                                                                                                                                i2 = R.id.pswAgainTv;
                                                                                                                                                                                                                                                TextView textView14 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                if (textView14 != null) {
                                                                                                                                                                                                                                                    i2 = R.id.pswCl;
                                                                                                                                                                                                                                                    ConstraintLayout constraintLayout12 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                    if (constraintLayout12 != null) {
                                                                                                                                                                                                                                                        i2 = R.id.pswEt;
                                                                                                                                                                                                                                                        EditText editText4 = (EditText) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                        if (editText4 != null) {
                                                                                                                                                                                                                                                            i2 = R.id.pswImgv;
                                                                                                                                                                                                                                                            ImageView imageView18 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                            if (imageView18 != null) {
                                                                                                                                                                                                                                                                i2 = R.id.pswTipTv;
                                                                                                                                                                                                                                                                TextView textView15 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                if (textView15 != null) {
                                                                                                                                                                                                                                                                    i2 = R.id.pswTv;
                                                                                                                                                                                                                                                                    TextView textView16 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                    if (textView16 != null) {
                                                                                                                                                                                                                                                                        i2 = R.id.rightCl;
                                                                                                                                                                                                                                                                        ConstraintLayout constraintLayout13 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                        if (constraintLayout13 != null) {
                                                                                                                                                                                                                                                                            i2 = R.id.rightTv;
                                                                                                                                                                                                                                                                            TextView textView17 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                            if (textView17 != null) {
                                                                                                                                                                                                                                                                                i2 = R.id.scrollV;
                                                                                                                                                                                                                                                                                ScrollView scrollView2 = (ScrollView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                if (scrollView2 != null) {
                                                                                                                                                                                                                                                                                    i2 = R.id.sendCodeTv;
                                                                                                                                                                                                                                                                                    BLTextView bLTextView2 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                    if (bLTextView2 != null) {
                                                                                                                                                                                                                                                                                        i2 = R.id.submitCl;
                                                                                                                                                                                                                                                                                        ConstraintLayout constraintLayout14 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                        if (constraintLayout14 != null) {
                                                                                                                                                                                                                                                                                            i2 = R.id.submitTv;
                                                                                                                                                                                                                                                                                            TextView textView18 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                            if (textView18 != null) {
                                                                                                                                                                                                                                                                                                i2 = R.id.textView3;
                                                                                                                                                                                                                                                                                                TextView textView19 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                if (textView19 != null) {
                                                                                                                                                                                                                                                                                                    i2 = R.id.textView4;
                                                                                                                                                                                                                                                                                                    TextView textView20 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                    if (textView20 != null) {
                                                                                                                                                                                                                                                                                                        i2 = R.id.threeTv;
                                                                                                                                                                                                                                                                                                        TextView textView21 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                        if (textView21 != null) {
                                                                                                                                                                                                                                                                                                            i2 = R.id.tilteImgv;
                                                                                                                                                                                                                                                                                                            ImageView imageView19 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                            if (imageView19 != null) {
                                                                                                                                                                                                                                                                                                                i2 = R.id.tipTv;
                                                                                                                                                                                                                                                                                                                TextView textView22 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                if (textView22 != null) {
                                                                                                                                                                                                                                                                                                                    i2 = R.id.titleCl;
                                                                                                                                                                                                                                                                                                                    ConstraintLayout constraintLayout15 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                    if (constraintLayout15 != null) {
                                                                                                                                                                                                                                                                                                                        i2 = R.id.top1Imgv;
                                                                                                                                                                                                                                                                                                                        ImageView imageView20 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                        if (imageView20 != null) {
                                                                                                                                                                                                                                                                                                                            i2 = R.id.top2Imgv;
                                                                                                                                                                                                                                                                                                                            ImageView imageView21 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                            if (imageView21 != null) {
                                                                                                                                                                                                                                                                                                                                i2 = R.id.topImgv;
                                                                                                                                                                                                                                                                                                                                ImageView imageView22 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                if (imageView22 != null) {
                                                                                                                                                                                                                                                                                                                                    i2 = R.id.twoFl;
                                                                                                                                                                                                                                                                                                                                    FrameLayout frameLayout2 = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                    if (frameLayout2 != null) {
                                                                                                                                                                                                                                                                                                                                        i2 = R.id.twoTv;
                                                                                                                                                                                                                                                                                                                                        TextView textView23 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                        if (textView23 != null) {
                                                                                                                                                                                                                                                                                                                                            i2 = R.id.userTv;
                                                                                                                                                                                                                                                                                                                                            TextView textView24 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                            if (textView24 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.view))) != null) {
                                                                                                                                                                                                                                                                                                                                                i2 = R.id.web2Imgv;
                                                                                                                                                                                                                                                                                                                                                ImageView imageView23 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                if (imageView23 != null) {
                                                                                                                                                                                                                                                                                                                                                    i2 = R.id.web2V;
                                                                                                                                                                                                                                                                                                                                                    DWebView dWebView = (DWebView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                    if (dWebView != null) {
                                                                                                                                                                                                                                                                                                                                                        i2 = R.id.webImgv;
                                                                                                                                                                                                                                                                                                                                                        ImageView imageView24 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                        if (imageView24 != null) {
                                                                                                                                                                                                                                                                                                                                                            i2 = R.id.webTitle2Tv;
                                                                                                                                                                                                                                                                                                                                                            TextView textView25 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                            if (textView25 != null) {
                                                                                                                                                                                                                                                                                                                                                                i2 = R.id.webTitleTv;
                                                                                                                                                                                                                                                                                                                                                                TextView textView26 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                                if (textView26 != null) {
                                                                                                                                                                                                                                                                                                                                                                    i2 = R.id.webV;
                                                                                                                                                                                                                                                                                                                                                                    DWebView dWebView2 = (DWebView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                                    if (dWebView2 != null) {
                                                                                                                                                                                                                                                                                                                                                                        i2 = R.id.wxImgv;
                                                                                                                                                                                                                                                                                                                                                                        ImageView imageView25 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                                        if (imageView25 != null) {
                                                                                                                                                                                                                                                                                                                                                                            i2 = R.id.wxTip1Tv;
                                                                                                                                                                                                                                                                                                                                                                            TextView textView27 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                                            if (textView27 != null) {
                                                                                                                                                                                                                                                                                                                                                                                i2 = R.id.wxTipTv;
                                                                                                                                                                                                                                                                                                                                                                                TextView textView28 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                                                if (textView28 != null) {
                                                                                                                                                                                                                                                                                                                                                                                    i2 = R.id.x110Imgv;
                                                                                                                                                                                                                                                                                                                                                                                    ImageView imageView26 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                                                    if (imageView26 != null) {
                                                                                                                                                                                                                                                                                                                                                                                        i2 = R.id.x111Imgv;
                                                                                                                                                                                                                                                                                                                                                                                        ImageView imageView27 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                                                        if (imageView27 != null) {
                                                                                                                                                                                                                                                                                                                                                                                            i2 = R.id.x116Imgv;
                                                                                                                                                                                                                                                                                                                                                                                            ImageView imageView28 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                                                                                                                                                                                                                                            if (imageView28 != null) {
                                                                                                                                                                                                                                                                                                                                                                                                return new SetActivityBinding((ConstraintLayout) view, constraintLayout, imageView, imageView2, textView, shapeBlurView, editText, textView2, constraintLayout2, dotView, imageView3, bLEditText, constraintLayout3, textView3, bLFrameLayout, recyclerView, smartRefreshLayout, textView4, textView5, bLTextView, textView6, constraintLayout4, constraintLayout5, constraintLayout6, constraintLayout7, constraintLayout8, textView7, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9, textView8, constraintLayout9, textView9, imageView10, bLFrameLayout2, constraintLayout10, constraintLayout11, frameLayout, imageView11, textView10, imageView12, imageView13, recyclerView2, imageView14, imageView15, scrollView, smartRefreshLayout2, imageView16, textView11, imageView17, textView12, bLConstraintLayout, editText2, textView13, bLConstraintLayout2, editText3, textView14, constraintLayout12, editText4, imageView18, textView15, textView16, constraintLayout13, textView17, scrollView2, bLTextView2, constraintLayout14, textView18, textView19, textView20, textView21, imageView19, textView22, constraintLayout15, imageView20, imageView21, imageView22, frameLayout2, textView23, textView24, viewFindChildViewById, imageView23, dWebView, imageView24, textView25, textView26, dWebView2, imageView25, textView27, textView28, imageView26, imageView27, imageView28);
                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                }
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                }
                                                                                                                                                                                                            }
                                                                                                                                                                                                        }
                                                                                                                                                                                                    }
                                                                                                                                                                                                }
                                                                                                                                                                                            }
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static SetActivityBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static SetActivityBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.set_activity, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public ConstraintLayout getRoot() {
        return this.rootView;
    }
}
