package com.psychiatrygarden.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.psychiatrygarden.event.LocationEvent;
import com.psychiatrygarden.utils.LogUtils;
import de.greenrobot.event.EventBus;

/* loaded from: classes6.dex */
public class YkbLocationService extends Service {
    private YkbLocationListener locationListener;
    private LocationManager locationManager;

    public class YkbLocationListener implements LocationListener {
        public YkbLocationListener() {
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(@NonNull Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            LogUtils.d("onLocationChanged", "longitude=" + longitude + ",latitude=" + latitude);
            EventBus.getDefault().post(new LocationEvent(longitude, latitude));
            if (YkbLocationService.this.locationManager != null) {
                YkbLocationService.this.locationManager.removeUpdates(YkbLocationService.this.locationListener);
            }
            YkbLocationService.this.stopSelf();
        }

        @Override // android.location.LocationListener
        public void onProviderDisabled(@NonNull String provider) {
            LogUtils.d("onProviderDisabled", provider);
        }

        @Override // android.location.LocationListener
        public void onProviderEnabled(@NonNull String provider) {
            LogUtils.d("onProviderEnabled", provider);
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LogUtils.d("onStatusChanged", provider);
        }
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    @SuppressLint({"MissingPermission"})
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.locationManager = (LocationManager) getSystemService("location");
        this.locationListener = new YkbLocationListener();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(2);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(1);
        String bestProvider = this.locationManager.getBestProvider(criteria, false);
        if (bestProvider != null) {
            Location lastKnownLocation = this.locationManager.getLastKnownLocation(bestProvider);
            if (lastKnownLocation != null) {
                double longitude = lastKnownLocation.getLongitude();
                double latitude = lastKnownLocation.getLatitude();
                EventBus.getDefault().post(new LocationEvent(longitude, latitude));
                LogUtils.d("LocationChanged", "longitude=" + longitude + ",latitude=" + latitude);
                stopSelf();
            } else {
                this.locationManager.requestLocationUpdates(bestProvider, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS, 10.0f, this.locationListener);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
