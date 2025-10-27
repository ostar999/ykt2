package com.xiaomi.push;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import java.util.Set;

/* loaded from: classes6.dex */
public class eg extends ei {
    public eg(Context context, int i2) {
        super(context, i2);
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 6;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.Bluetooth;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        StringBuilder sb = new StringBuilder();
        try {
            Set<BluetoothDevice> bondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
            if (!ad.a(bondedDevices)) {
                int i2 = 0;
                for (BluetoothDevice bluetoothDevice : bondedDevices) {
                    if (i2 > 10) {
                        break;
                    }
                    if (sb.length() > 0) {
                        sb.append(com.alipay.sdk.util.h.f3376b);
                    }
                    sb.append(bluetoothDevice.getName());
                    sb.append(",");
                    sb.append(bluetoothDevice.getAddress());
                    sb.append(",");
                    sb.append(bluetoothDevice.getType());
                    i2++;
                }
            }
        } catch (Throwable unused) {
        }
        return sb.toString();
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public boolean mo337a() {
        return ((ei) this).f339a.getPackageManager().checkPermission("android.permission.BLUETOOTH", ((ei) this).f339a.getPackageName()) == 0;
    }
}
