package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
public final class zzhn implements zzhk {
    private static final zzbq<Boolean> zzrg;
    private static final zzbq<Boolean> zzrh;
    private static final zzbq<Boolean> zzri;
    private static final zzbq<Boolean> zzrj;
    private static final zzbq<Boolean> zzrk;
    private static final zzbq<Boolean> zzrl;
    private static final zzbq<Boolean> zzrm;
    private static final zzbq<Boolean> zzrn;
    private static final zzbq<Boolean> zzro;
    private static final zzbq<Boolean> zzrp;
    private static final zzbq<Boolean> zzrq;

    static {
        zzbu zzbuVar = new zzbu(zzbn.zzl("com.google.android.gms.icing"));
        zzrg = zzbuVar.zza("block_action_upload_if_data_sharing_disabled", false);
        zzrh = zzbuVar.zza("drop_usage_reports_for_account_mismatch", false);
        zzri = zzbuVar.zza("enable_additional_type_for_email", true);
        zzrj = zzbuVar.zza("enable_client_grant_slice_permission", true);
        zzrk = zzbuVar.zza("enable_custom_action_url_generation", false);
        zzrl = zzbuVar.zza("enable_failure_response_for_apitask_exceptions", false);
        zzrm = zzbuVar.zza("enable_on_device_sharing_control_ui", false);
        zzrn = zzbuVar.zza("enable_safe_app_indexing_package_removal", false);
        zzro = zzbuVar.zza("enable_slice_authority_validation", false);
        zzrp = zzbuVar.zza("redirect_user_actions_from_persistent_to_main", false);
        zzrq = zzbuVar.zza("type_access_whitelist_enforce_platform_permissions", true);
    }

    @Override // com.google.android.gms.internal.icing.zzhk
    public final boolean zzeb() {
        return zzrj.get().booleanValue();
    }
}
