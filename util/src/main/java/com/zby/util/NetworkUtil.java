package com.zby.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author ZhuBingYang
 * @date 2019-08-14
 */
public class NetworkUtil {
    /**
     * return whether network is connected
     *
     * @return {@code true} connected <br/> {@code false} not connected
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                @SuppressLint("MissingPermission") NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * return whether network is connected
     *
     * @param context     the context
     * @param networkType Network Connection Type
     * @return @return {@code true} connected <br/> {@code false} not connected
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkConnected(Context context, int networkType) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = null;
            if (mConnectivityManager != null) {
                mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(networkType);
            }
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * return whether wifi is connected
     *
     * @return {@code true} connected <br/> {@code false} not connected
     */
    public static boolean isWifiConnected(Context context) {
        return isNetworkConnected(context, ConnectivityManager.TYPE_WIFI);
    }

    /**
     * return whether cellular data network is connected
     *
     * @return {@code true} connected <br/> {@code false} not connected
     */
    public static boolean isMobileConnected(Context context) {
        return isNetworkConnected(context, ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Reports the type of network to which the
     * info in this {@code NetworkInfo} pertains.
     *
     * @return one of {@link ConnectivityManager#TYPE_MOBILE}, {@link
     * ConnectivityManager#TYPE_WIFI}, {@link ConnectivityManager#TYPE_WIMAX}, {@link
     * ConnectivityManager#TYPE_ETHERNET},  {@link ConnectivityManager#TYPE_BLUETOOTH}, or other
     * types defined by {@link ConnectivityManager}.
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                @SuppressLint("MissingPermission") NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                    return mNetworkInfo.getType();
                }
            }
        }
        return -1;
    }

    /**
     * Returns the SSID of the current connection WIFI.
     * The 9.0 model must request the GPS permission and open the GPS to get the WIFI name correctly.
     * <p>
     * request permission {@code android.permission.ACCESS_NETWORK_STATE}
     */
    public static String getWifiSSID(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {

            WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            if (wm != null) {
                WifiInfo info = wm.getConnectionInfo();

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    return info.getSSID();
                } else {
                    return info.getSSID().replace("\"", "");
                }
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {

            ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                @SuppressLint("MissingPermission") NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo.isConnected()) {
                    if (networkInfo.getExtraInfo() != null) {
                        return networkInfo.getExtraInfo().replace("\"", "");
                    }
                }
            }
        }

        return "";
    }

    /**
     * Returns the BSSID of the current connection WIFI
     */
    public static String getWifiBSSID(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wm != null) {
            WifiInfo wi = wm.getConnectionInfo();
            return wi.getBSSID();
        }
        return "";
    }

    /**
     * Returns the IP address of the current network connection
     * if the connection is Wifi,android.permission.ACCESS_WIFI_STATE permission is required
     * if the connection is mobile,android.permission.INTERNET permission is required
     *
     * @return the ip address if successfully acquisition; otherwise, a string
     * equals to "" is returned
     */
    public static String getIpAddress(Context context) {
        int connectedType = getConnectedType(context);
        if (connectedType == -1) {
            return "";
        }

        if (connectedType == ConnectivityManager.TYPE_WIFI) {
            //requires android.permission.ACCESS_WIFI_STATE permission
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return ConvertUtil.int2ip(wifiInfo.getIpAddress());
        } else if (connectedType == ConnectivityManager.TYPE_MOBILE) {
            try {
                //requires android.permission.INTERNET permission
                if (NetworkInterface.getNetworkInterfaces() != null) {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface networkInterface = en.nextElement();
                        for (Enumeration<InetAddress> addresses = networkInterface.getInetAddresses(); addresses.hasMoreElements(); ) {
                            InetAddress inetAddress = addresses.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "";
    }
}
