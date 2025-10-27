package h;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.umeng.analytics.pro.am;
import org.wrtca.util.ThreadUtils;

/* loaded from: classes8.dex */
public final class b {

    public static class a implements SensorEventListener {

        /* renamed from: f, reason: collision with root package name */
        public static final String f27019f = "RTCProximitySensor";

        /* renamed from: b, reason: collision with root package name */
        public final Runnable f27021b;

        /* renamed from: c, reason: collision with root package name */
        public final SensorManager f27022c;

        /* renamed from: a, reason: collision with root package name */
        public final ThreadUtils.ThreadChecker f27020a = new ThreadUtils.ThreadChecker();

        /* renamed from: d, reason: collision with root package name */
        public Sensor f27023d = null;

        /* renamed from: e, reason: collision with root package name */
        public boolean f27024e = false;

        public a(Context context, Runnable runnable) {
            c.h.a(f27019f, f27019f + b.a());
            this.f27021b = runnable;
            this.f27022c = (SensorManager) context.getSystemService(am.ac);
        }

        public static a a(Context context, Runnable runnable) {
            return new a(context, runnable);
        }

        public final void b() {
            if (this.f27023d == null) {
                return;
            }
            c.h.a(f27019f, "Proximity sensor: name=" + this.f27023d.getName() + ", vendor: " + this.f27023d.getVendor() + ", power: " + this.f27023d.getPower() + ", resolution: " + this.f27023d.getResolution() + ", max range: " + this.f27023d.getMaximumRange() + ", min delay: " + this.f27023d.getMinDelay() + ", type: " + this.f27023d.getStringType() + ", max delay: " + this.f27023d.getMaxDelay() + ", reporting mode: " + this.f27023d.getReportingMode() + ", isWakeUpSensor: " + this.f27023d.isWakeUpSensor());
        }

        public boolean c() {
            this.f27020a.checkIsOnValidThread();
            return this.f27024e;
        }

        public boolean d() {
            this.f27020a.checkIsOnValidThread();
            c.h.a(f27019f, "start" + b.a());
            if (!a()) {
                return false;
            }
            this.f27022c.registerListener(this, this.f27023d, 3);
            return true;
        }

        public void e() {
            this.f27020a.checkIsOnValidThread();
            c.h.a(f27019f, "stop" + b.a());
            Sensor sensor = this.f27023d;
            if (sensor == null) {
                return;
            }
            this.f27022c.unregisterListener(this, sensor);
        }

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i2) {
            this.f27020a.checkIsOnValidThread();
            b.a(sensor.getType() == 8);
            if (i2 == 0) {
                Log.e(f27019f, "The values returned by this sensor cannot be trusted");
            }
        }

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            this.f27020a.checkIsOnValidThread();
            b.a(sensorEvent.sensor.getType() == 8);
            if (sensorEvent.values[0] < this.f27023d.getMaximumRange()) {
                c.h.a(f27019f, "Proximity sensor => NEAR state");
                this.f27024e = true;
            } else {
                c.h.a(f27019f, "Proximity sensor => FAR state");
                this.f27024e = false;
            }
            Runnable runnable = this.f27021b;
            if (runnable != null) {
                runnable.run();
            }
            c.h.a(f27019f, "onSensorChanged" + b.a() + ": accuracy=" + sensorEvent.accuracy + ", timestamp=" + sensorEvent.timestamp + ", distance=" + sensorEvent.values[0]);
        }

        public final boolean a() {
            if (this.f27023d != null) {
                return true;
            }
            Sensor defaultSensor = this.f27022c.getDefaultSensor(8);
            this.f27023d = defaultSensor;
            if (defaultSensor == null) {
                return false;
            }
            b();
            return true;
        }
    }

    public static void a(boolean z2) {
        if (!z2) {
            throw new AssertionError("Expected condition to be true");
        }
    }

    public static String a() {
        return "@[name=" + Thread.currentThread().getName() + ", id=" + Thread.currentThread().getId() + StrPool.BRACKET_END;
    }

    public static void a(String str) {
        c.h.a(str, "Android SDK: " + Build.VERSION.SDK_INT + ", Release: " + Build.VERSION.RELEASE + ", Brand: " + Build.BRAND + ", Device: " + Build.DEVICE + ", Id: " + Build.ID + ", Hardware: " + Build.HARDWARE + ", Manufacturer: " + Build.MANUFACTURER + ", Model: " + Build.MODEL + ", Product: " + Build.PRODUCT);
    }
}
