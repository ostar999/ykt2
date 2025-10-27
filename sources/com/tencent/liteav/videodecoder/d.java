package com.tencent.liteav.videodecoder;

import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private boolean f20144a = false;

    /* renamed from: b, reason: collision with root package name */
    private boolean f20145b = false;

    private void a(String str, byte[] bArr) {
        if (!this.f20144a || bArr == null) {
            return;
        }
        String str2 = "";
        for (int i2 = 0; i2 < bArr.length && i2 < 256; i2++) {
            String hexString = Integer.toHexString(bArr[i2] & 255);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            str2 = str2 + " " + hexString;
        }
        Log.d("[H264SPSModifier]", str + str2);
    }

    private byte[] b(byte[] bArr) {
        byte b3;
        byte[] bArr2 = new byte[bArr.length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr.length) {
            if (i2 < bArr.length - 3 && (b3 = bArr[i2]) == 0) {
                int i4 = i2 + 1;
                if (bArr[i4] == 0 && bArr[i2 + 2] == 3) {
                    int i5 = i2 + 3;
                    if (bArr[i5] <= 3) {
                        int i6 = i3 + 1;
                        bArr2[i3] = b3;
                        i3 = i6 + 1;
                        bArr2[i6] = bArr[i4];
                        i2 = i5;
                    }
                }
            }
            bArr2[i3] = bArr[i2];
            i2++;
            i3++;
        }
        if (i3 == bArr.length) {
            return null;
        }
        byte[] bArr3 = new byte[i3];
        System.arraycopy(bArr2, 0, bArr3, 0, i3);
        return bArr3;
    }

    private byte[] c(byte[] bArr) {
        byte b3;
        byte[] bArr2 = new byte[(bArr.length * 3) / 2];
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr.length) {
            if (i2 < bArr.length - 2 && (b3 = bArr[i2]) == 0) {
                int i4 = i2 + 1;
                if (bArr[i4] == 0) {
                    int i5 = i2 + 2;
                    if (bArr[i5] <= 3) {
                        int i6 = i3 + 1;
                        bArr2[i3] = b3;
                        int i7 = i6 + 1;
                        bArr2[i6] = bArr[i4];
                        bArr2[i7] = 3;
                        i3 = i7 + 1;
                        i2 = i5;
                    }
                }
            }
            bArr2[i3] = bArr[i2];
            i2++;
            i3++;
        }
        if (i3 == bArr.length) {
            return bArr;
        }
        byte[] bArr3 = new byte[i3];
        System.arraycopy(bArr2, 0, bArr3, 0, i3);
        return bArr3;
    }

    public byte[] a(byte[] bArr) throws IOException {
        boolean z2;
        a("origin sps : ", bArr);
        byte[] bArrB = b(bArr);
        if (bArrB != null) {
            a("deEmulationPrevention sps : ", bArrB);
            z2 = true;
            bArr = bArrB;
        } else {
            z2 = false;
        }
        byte[] bArrA = a(new ByteArrayInputStream(bArr));
        a("new sps : ", bArrA);
        if (bArrA == null || !z2) {
            return bArrA;
        }
        byte[] bArrC = c(bArrA);
        a("emulationPrevention sps : ", bArrC);
        return bArrC;
    }

    private boolean b(c cVar) throws IOException {
        if (cVar.d("VUI: aspect_ratio_info_present_flag") && ((int) cVar.a(8, "VUI: aspect_ratio")) == 255) {
            cVar.b(16, "VUI: sar_width");
            cVar.b(16, "VUI: sar_height");
        }
        if (cVar.d("VUI: overscan_info_present_flag")) {
            cVar.b(1, "VUI: overscan_appropriate_flag");
        }
        if (cVar.d("VUI: video_signal_type_present_flag")) {
            cVar.b(3, "VUI: video_format");
            cVar.b(1, "VUI: video_full_range_flag");
            if (cVar.d("VUI: colour_description_present_flag")) {
                cVar.b(8, "VUI: colour_primaries");
                cVar.b(8, "VUI: transfer_characteristics");
                cVar.b(8, "VUI: matrix_coefficients");
            }
        }
        if (cVar.d("VUI: chroma_loc_info_present_flag")) {
            cVar.b("VUI chroma_sample_loc_type_top_field");
            cVar.b("VUI chroma_sample_loc_type_bottom_field");
        }
        if (cVar.d("VUI: timing_info_present_flag")) {
            cVar.b(32, "VUI: num_units_in_tick");
            cVar.b(32, "VUI: time_scale");
            cVar.b(1, "VUI: fixed_frame_rate_flag");
        }
        boolean zD = cVar.d("VUI: nal_hrd_parameters_present_flag");
        if (zD) {
            a(cVar);
        }
        boolean zD2 = cVar.d("VUI: vcl_hrd_parameters_present_flag");
        if (zD2) {
            a(cVar);
        }
        if (zD || zD2) {
            cVar.b(1, "VUI: low_delay_hrd_flag");
        }
        cVar.b(1, "VUI: pic_struct_present_flag");
        if (cVar.e("VUI: bitstream_restriction_flag")) {
            if (this.f20144a) {
                Log.d("[H264SPSModifier]", "steve:VUI has bs restriction!!");
            }
            cVar.a(true, "VUI: set bitstream_restriction_flag");
            cVar.d("VUI: motion_vectors_over_pic_boundaries_flag");
            cVar.b("VUI max_bytes_per_pic_denom");
            cVar.b("VUI max_bits_per_mb_denom");
            cVar.b("VUI log2_max_mv_length_horizontal");
            cVar.b("VUI log2_max_mv_length_vertical");
            cVar.b("VUI num_reorder_frames");
            if (!this.f20145b) {
                TXCLog.w("[H264SPSModifier]", "decode: do not add max_dec_frame_buffering when it is " + cVar.c(false));
                this.f20145b = true;
            }
            return false;
        }
        if (this.f20144a) {
            Log.d("[H264SPSModifier]", "steve:VUI has NO bs restriction!!");
        }
        cVar.a(true, "VUI: set bitstream_restriction_flag");
        cVar.a(true, "VUI: motion_vectors_over_pic_boundaries_flag");
        cVar.c(0, "VUI: max_bytes_per_pic_denom");
        cVar.c(0, "VUI: max_bits_per_mb_denom");
        cVar.c(10, "VUI: log2_max_mv_length_horizontal");
        cVar.c(10, "VUI: log2_max_mv_length_vertical");
        cVar.c(0, "VUI: num_reorder_frames");
        cVar.c(1, "VUI: max_dec_frame_buffering");
        if (!this.f20145b) {
            TXCLog.w("[H264SPSModifier]", "decode: add max_dec_frame_buffering 1 when it is no exist");
            this.f20145b = true;
        }
        return true;
    }

    public byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        c cVar = new c(inputStream, byteArrayOutputStream);
        cVar.b(8, "NALU type");
        int iA = (int) cVar.a(8, "SPS: profile_idc");
        cVar.b(8, "SPS: constraint_set_0-3_flag and reserved_zero_4bits");
        cVar.a(8, "SPS: level_idc");
        cVar.b("SPS: seq_parameter_set_id");
        if (iA == 100 || iA == 110 || iA == 122 || iA == 144) {
            if (cVar.a("SPS: chroma_format_idc") == 3) {
                cVar.b(1, "SPS: residual_color_transform_flag");
            }
            cVar.b("SPS: bit_depth_luma_minus8");
            cVar.b("SPS: bit_depth_chroma_minus8");
            cVar.b(1, "SPS: qpprime_y_zero_transform_bypass_flag");
            if (cVar.d("SPS: seq_scaling_matrix_present_lag")) {
                for (int i2 = 0; i2 < 8; i2++) {
                    if (cVar.d("SPS: seqScalingListPresentFlag")) {
                        if (i2 < 6) {
                            cVar.c(16);
                        } else {
                            cVar.c(64);
                        }
                    }
                }
            }
        }
        cVar.b("SPS: log2_max_frame_num_minus4");
        int iA2 = cVar.a("SPS: pic_order_cnt_type");
        if (iA2 == 0) {
            cVar.b("SPS: log2_max_pic_order_cnt_lsb_minus4");
        } else if (iA2 == 1) {
            cVar.b(1, "SPS: delta_pic_order_always_zero_flag");
            cVar.b("SPS: offset_for_non_ref_pic");
            cVar.b("SPS: offset_for_top_to_bottom_field");
            int iA3 = cVar.a("SPS: num_ref_frames_in_pic_order_cnt_cycle");
            for (int i3 = 0; i3 < iA3; i3++) {
                cVar.b("SPS: offsetForRefFrame [" + i3 + StrPool.BRACKET_END);
            }
        }
        int iA4 = cVar.a("SPS: num_ref_frames");
        if (this.f20144a) {
            Log.d("[H264SPSModifier]", "SPS: num_ref_frames: " + iA4);
        }
        cVar.b(1, "SPS: gaps_in_frame_num_value_allowed_flag");
        cVar.b("SPS: pic_width_in_mbs_minus1");
        cVar.b("SPS: pic_height_in_map_units_minus1");
        if (!cVar.d("SPS: frame_mbs_only_flag")) {
            cVar.b(1, "SPS: mb_adaptive_frame_field_flag");
        }
        cVar.b(1, "SPS: direct_8x8_inference_flag");
        if (cVar.d("SPS: frame_cropping_flag")) {
            cVar.b("SPS: frame_crop_left_offset");
            cVar.b("SPS: frame_crop_right_offset");
            cVar.b("SPS: frame_crop_top_offset");
            cVar.b("SPS: frame_crop_bottom_offset");
        }
        if (cVar.e("SPS: vui_parameters_present_flag")) {
            if (this.f20144a) {
                Log.d("[H264SPSModifier]", "vui_parameters_present_flag exist!! modify max_dec_frame_buffering");
            }
            cVar.a(true, "VUI set 1: ");
            if (!b(cVar)) {
                return null;
            }
        } else {
            if (this.f20144a) {
                Log.d("[H264SPSModifier]", "vui_parameters_present_flag NOT exist!! add max_dec_frame_buffering");
            }
            cVar.a(true, "VUI set 1: ");
            cVar.a(false, "VUI: aspect_ratio_info_present_flag");
            cVar.a(false, "VUI: overscan_info_present_flag");
            cVar.a(false, "VUI: video_signal_type_present_flag");
            cVar.a(false, "VUI: chroma_loc_info_present_flag");
            cVar.a(false, "VUI: timing_info_present_flag");
            cVar.a(false, "VUI: nal_hrd_parameters_present_flag");
            cVar.a(false, "VUI: vcl_hrd_parameters_present_flag");
            cVar.a(false, "VUI: pic_struct_present_flag");
            cVar.a(true, "VUI: bitstream_restriction_flag");
            cVar.a(true, "VUI: motion_vectors_over_pic_boundaries_flag");
            cVar.c(0, "VUI: max_bytes_per_pic_denom");
            cVar.c(0, "VUI: max_bits_per_mb_denom");
            cVar.c(10, "VUI: log2_max_mv_length_horizontal");
            cVar.c(10, "VUI: log2_max_mv_length_vertical");
            cVar.c(0, "VUI: num_reorder_frames");
            cVar.c(1, "VUI: max_dec_frame_buffering");
            if (!this.f20145b) {
                TXCLog.w("[H264SPSModifier]", "decode: add max_dec_frame_buffering 1 when vui is no exist");
                this.f20145b = true;
            }
        }
        cVar.c();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (this.f20144a) {
            String str = "";
            for (byte b3 : byteArray) {
                String hexString = Integer.toHexString(b3 & 255);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                str = str + " " + hexString;
            }
            Log.d("[H264SPSModifier]", "new SPS:" + str);
        }
        return byteArray;
    }

    private void a(c cVar) throws IOException {
        int iA = cVar.a("SPS: cpb_cnt_minus1");
        cVar.b(4, "HRD: bit_rate_scale");
        cVar.b(4, "HRD: cpb_size_scale");
        for (int i2 = 0; i2 <= iA; i2++) {
            cVar.b("HRD: bit_rate_value_minus1");
            cVar.b("HRD: cpb_size_value_minus1");
            cVar.b(1, "HRD: cbr_flag");
        }
        cVar.b(5, "HRD: initial_cpb_removal_delay_length_minus1");
        cVar.b(5, "HRD: cpb_removal_delay_length_minus1");
        cVar.b(5, "HRD: dpb_output_delay_length_minus1");
        cVar.b(5, "HRD: time_offset_length");
    }
}
