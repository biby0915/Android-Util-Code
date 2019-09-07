package com.zby.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.zby.util.constant.PermissionConstant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author ZhuBingYang
 * @date 2019-08-23
 */
public class LocationUtil {
    private static final String TAG = "LocationUtil";
    private static final int LOCATING_TIME_OUT_MILL = 30 * 1000;
    private Map<Long, OnLocationChangeListener> mLocationListeners = new HashMap<>();

    private int mLocatingTimeOut = LOCATING_TIME_OUT_MILL;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            for (Map.Entry<Long, OnLocationChangeListener> entry : mLocationListeners.entrySet()) {
                entry.getValue().onLocationChanged(location);
            }
            cancelLocation(AppUtil.getApplication());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            for (Map.Entry<Long, OnLocationChangeListener> entry : mLocationListeners.entrySet()) {
                entry.getValue().onStatusChanged(provider, status, extras);
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            for (Map.Entry<Long, OnLocationChangeListener> entry : mLocationListeners.entrySet()) {
                entry.getValue().onProviderEnabled(provider);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            for (Map.Entry<Long, OnLocationChangeListener> entry : mLocationListeners.entrySet()) {
                entry.getValue().onProviderDisabled(provider);
            }
        }
    };

    public LocationUtil getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * @param listener listener to receive location updates
     */
    @SuppressLint("MissingPermission")
    public void requestLocation(Context context, OnLocationChangeListener listener) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {

            //Request system relocation
            List<String> providers = locationManager.getProviders(true);
            String locationProvider = null;
            if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                //if Network provider
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                //if GPS provider
                locationProvider = LocationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.PASSIVE_PROVIDER)) {
                //if PASSIVE provider
                locationProvider = LocationManager.PASSIVE_PROVIDER;
            } else {
                Logger.D.log(TAG, "no location provider");
                listener.onLocationChanged(null);
                return;
            }

            Location location = locationManager.getLastKnownLocation(locationProvider);
            //Successfully retrieve the latest location information
            //return result
            if (location != null) {
                listener.onLocationChanged(location);
                return;
            }
            mLocationListeners.put(System.currentTimeMillis(), listener);
            ThreadTool.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkTimeOut();
                }
            }, mLocatingTimeOut);
            locationManager.requestLocationUpdates(locationProvider, 1, (float) 1.0, locationListener);
        } else {
            Logger.D.log(TAG, "requestLocation failed. Unable to obtain location manager.");
        }
    }

    private void checkTimeOut() {
        Iterator<Map.Entry<Long, OnLocationChangeListener>> iterator = mLocationListeners.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, OnLocationChangeListener> entry = iterator.next();
            if (entry.getKey() + mLocatingTimeOut <= System.currentTimeMillis()) {
                entry.getValue().onTimeOut();
                iterator.remove();
            }
        }

        if (mLocationListeners.isEmpty()) {
            cancelLocation(AppUtil.getApplication());
        }
    }

    public void removeLocationListener(OnLocationChangeListener listener) {
        mLocationListeners.values().remove(listener);
        // TODO: 2019-09-04 remove callback in message queue
        if (mLocationListeners.isEmpty()) {
            cancelLocation(AppUtil.getApplication());
        }
    }

    @SuppressLint("MissingPermission")
    public void cancelLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
        mLocationListeners.clear();
    }

    public boolean isLocationServiceEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * whether current application has location permissions
     */
    public boolean hasLocationPermission(Context context) {
        return PermissionUtil.hasAnyPermission(context, PermissionConstant.GROUP_LOCATION);
    }

    static class SingletonHolder {
        static final LocationUtil instance = new LocationUtil();
    }

    public abstract class OnLocationChangeListener implements LocationListener {
        public void onTimeOut() {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
