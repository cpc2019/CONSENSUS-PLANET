package com.pingan.baselibs.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import com.pingan.baselibs.ApplicationManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 设备信息收集工具
 * Created by lvyingwei134 on 16/8/30
 */
public class DeviceUtils {

	/**
	 * root状态 1:已root 2:未root
	 */
	private static int rootState = -1;

	private static final String UNKNOWN = "UNKNOWN";

	//获得WindowManager实例
	public static WindowManager getWindowManager(Context context) {
		return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}

	//获得TelephonyManager实例
	public static TelephonyManager getTelephonyManager(Context context) {
		TelephonyManager telephonyManager;
		if (checkPermission(context, Manifest.permission.READ_PHONE_STATE) && (null != (
			telephonyManager =
				(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)))) {
			return telephonyManager;
		}
		return null;
	}

	/**
	 * 获得ConnectivityManager实例
	 */
	public static ConnectivityManager getConnectivityManager(Context context) {
		ConnectivityManager connectivityManager;
		if (checkPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) && (null != (
			connectivityManager =
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)))) {
			return connectivityManager;
		}
		return null;
	}

	//获得WifiManager实例
	public static WifiManager getWifiManager(Context context) {
		WifiManager wifiManager;
		if (checkPermission(context, Manifest.permission.ACCESS_WIFI_STATE) && (null != (
			wifiManager = (WifiManager) context.getApplicationContext()
				.getSystemService(Context.WIFI_SERVICE)))) {
			return wifiManager;
		}
		return null;
	}

	private static WifiInfo getWifiInfo(Context context) {
		WifiManager wifiManager = getWifiManager(context);
		if (null != wifiManager) {
			return wifiManager.getConnectionInfo();
		}
		return null;
	}

	//=======================以下是获取设备信息的方法=======================

	/**
	 * 检查权限
	 */
	public static boolean checkPermission(Context context, String permission) {
		return context.getPackageManager().checkPermission(permission, context.getPackageName())
			== 0;
	}

	/**
	 * 获取手机号码
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager telephonyManager = getTelephonyManager(context);
		if (null != telephonyManager) {
			return telephonyManager.getLine1Number();
		}
		return null;
	}

	/**
	 * 获取IMEI
	 */
	public static String getIMEI(Context context) {
		TelephonyManager telephonyManager = getTelephonyManager(context);
		if (null != telephonyManager) {
			if (AndroidVersionUtils.hasO()) {
				return telephonyManager.getImei();
			} else {
				return telephonyManager.getDeviceId();
			}
		}
		return null;
	}

	public static String getAndroidId(Context context) {
		return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	}

	public static String getDeviceId(Context context) {
		String deviceId = getIMEI(context);
		if (TextUtils.isEmpty(deviceId)) {
			deviceId = getAndroidId(context);
			if (TextUtils.isEmpty(deviceId)) {
				deviceId = getDeviceMac(context);
			}
		}
		return deviceId;
	}

	/**
	 * 获取IMSI(标识手机卡,涵盖了运营商代码,双卡手机可能会有问题)
	 */
	public static String getIMSI(Context context) {
		TelephonyManager telephonyManager = getTelephonyManager(context);
		if (telephonyManager != null) return telephonyManager.getSubscriberId();
		return null;
	}

	/**
	 * 获取运营商名字
	 */
	public static String getSimOperatorName(Context context) {
		TelephonyManager telephonyManager = getTelephonyManager(context);
		if (telephonyManager != null) return telephonyManager.getSimOperatorName();
		return null;
	}

	/**
	 * 获取运营商
	 */
	public static String getSimOperator(Context context) {
		TelephonyManager telephonyManager = getTelephonyManager(context);
		if (telephonyManager != null) {
			return telephonyManager.getSimOperator();
		}
		return null;
	}

	/**
	 * 获取设备MCC
	 */
	public static String getMCC(Context context) {
		String simOperator = getSimOperator(context);
		if (!TextUtils.isEmpty(simOperator)) {
			if (simOperator.length() >= 3) {
				return simOperator.substring(0, 3);
			}
		}
		return null;
	}

	/**
	 * 获取设备MNC
	 */
	public static String getMNC(Context context) {
		String simOperator = getSimOperator(context);
		if (!TextUtils.isEmpty(simOperator)) {
			if (simOperator.length() > 3) {
				return simOperator.substring(3);
			}
		}
		return null;
	}

	/**
	 * 获取手机型号
	 */
	public static String getModel() {
		return Build.MODEL;
	}

	/**
	 * 获取手机厂商
	 */
	public static String getManufacturer() {
		return Build.MANUFACTURER;
	}

	//获取系统版本号
	public static String getOsVersion() {
		//        return String.format(Locale.getDefault(), "Android%s/%d", Build.VERSION.RELEASE, Build.VERSION.SDK_INT);
		return Build.VERSION.RELEASE;
	}

	//获得国家信息
	public static String getCountry() {
		return Locale.getDefault().getCountry();
	}

	//获得语言信息
	public static String getLanguage() {
		return Locale.getDefault().getLanguage();
	}

	//获得时区
	public static String getTimeZone() {
		return TimeZone.getDefault().getID();
	}

	//获得屏幕大小
	public static Point getScreenSize(Context context) {
		Point size = new Point();
		getWindowManager(context).getDefaultDisplay().getSize(size);
		return size;
	}

	//获得屏幕宽
	public static int getScreenWidth(Context context) {
		return getScreenSize(context).x;
	}

	//获得屏幕高
	public static int getScreenHeight(Context context) {
		return getScreenSize(context).y + (SmartBarUtils.isMeizu() ? 0
			: getNavigationBarHeight(context));
	}

	//判断电池是否在充电

	//获得剩余电量

	/**
	 * 获得memory总容量
	 */
	public static String getTotalMemory(Context context) {
		// memInfo.totalMem not supported in pre-Jelly Bean APIs.
		if (AndroidVersionUtils.hasJellyBean()) {
			ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
			ActivityManager am =
				(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			am.getMemoryInfo(memInfo);
			return fileSize(memInfo.totalMem);
		} else {
			try {
				FileReader fr = new FileReader("/proc/meminfo");
				BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
				return localBufferedReader.readLine();
			} catch (Exception ignored) {
			}
		}

		return null;
	}

	//获得memory可用容量
	public static String getFreeMemory() {
		String path = "/proc/meminfo";
		String data = "";
		try {
			FileReader fr = new FileReader(path);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			localBufferedReader.readLine();
			data = localBufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 判断是否处于调试状态
	 */
	public static int isDebugMode(Context context) {
		int debugMode = 0;
		try {
			if (AndroidVersionUtils.hasJellyBeanMR1()) {
				debugMode = Settings.Global.getInt(context.getContentResolver(),
					Settings.Global.ADB_ENABLED);
			} else {
				debugMode = Settings.Secure.getInt(context.getContentResolver(),
					Settings.Secure.ADB_ENABLED);
			}
		} catch (Settings.SettingNotFoundException ignored) {
		}

		return debugMode == 1 ? 1 : 2;
	}

	//获取内核启动时间

	//判断设备当前是否联网
	public static boolean isNetworkConnected() {
		ConnectivityManager manager = getConnectivityManager(ApplicationManager.getContext());
		NetworkInfo ni = null;
		if (manager != null) {
			ni = manager.getActiveNetworkInfo();
		}
		return ni != null && ni.isConnected();
	}

	///**
	// * 获取当前网络类型
	// *
	// * @param context
	// * @return
	// */
	//public static int getNetworkType(Context context) {
	//    return ConnectivityUtils.getNetworkClass(context);
	//}

	private static String loadFileAsString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}

	//获取Wifi的SSID地址
	public static String getWifiSSID(Context context) {
		WifiInfo wifiInfo = getWifiInfo(context);
		if (wifiInfo != null) {
			return wifiInfo.getSSID();
		}
		return null;
	}

	//获取Wifi的BSSID地址
	public static String getWifiBSSID(Context context) {
		WifiInfo wifiInfo = getWifiInfo(context);
		if (wifiInfo != null) {
			return wifiInfo.getBSSID();
		}
		return null;
	}

	//    //获取蜂窝数据的IP
	//    public static String getCellIpAddress() {
	//        try {
	//            for (Enumeration<NetworkInterface> en = NetworkInterface
	//                    .getNetworkInterfaces(); en.hasMoreElements();) {
	//
	//                NetworkInterface intf = en.nextElement();
	//
	//                for (Enumeration<InetAddress> enumIpAddr = intf
	//                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
	//
	//                    InetAddress inetAddress = enumIpAddr.nextElement();
	//
	//                    if (!inetAddress.isLoopbackAddress()
	//                            && inetAddress instanceof Inet4Address) {
	//
	//                        return inetAddress.getHostAddress();
	//                    }
	//                }
	//            }
	//        } catch (Exception e) {
	//            e.printStackTrace();
	//        }
	//        return null;
	//    }

	/**
	 * 获取系统启动时间
	 */
	public static long getStartTime() {
		return System.currentTimeMillis() - SystemClock.elapsedRealtime();
	}

	/**
	 * 获取电池状态(1:充电, 2:非充电)
	 */
	public static int getBatteryStatus(Context context) {
		Intent batteryStatus =
			context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		// 是否在充电
		int status =
			batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1) : 0;
		if (status == BatteryManager.BATTERY_STATUS_CHARGING
			|| status == BatteryManager.BATTERY_STATUS_FULL) {
			return 1;
		}
		return 2;
	}

	/**
	 * 获取电池电量
	 */
	public static int getBatteryLevel(Context context) {
		Intent batteryStatus =
			context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		if (batteryStatus == null) {
			return 100;
		}

		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		if (-1 == level || -1 == scale) {
			return 100;
		}

		return (int) (100 * (1.0 * level / scale));
	}

	/**
	 * 获取rom容量
	 */
	public static String getRomCapacity() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize;
		long totalBlocks;
		if (AndroidVersionUtils.hasJellyBeanMR2()) {
			blockSize = stat.getBlockSizeLong();
			totalBlocks = stat.getBlockCountLong();
		} else {
			blockSize = stat.getBlockSize();
			totalBlocks = stat.getBlockCount();
		}
		return fileSize(totalBlocks * blockSize);
	}

	/**
	 * 格式化容量
	 */
	private static String fileSize(long size) {
		String str = "";
		if (size >= 1024) {
			str = "KB";
			size /= 1024;
			if (size >= 1024) {
				str = "MB";
				size /= 1024;
				if (size >= 1024) {
					str = "GB";
					size /= 1024;
				}
			}
		}
		DecimalFormat formatter = new DecimalFormat();
		formatter.setGroupingSize(3);
		return String.format("%s%s", formatter.format(size), str);
	}

	/**
	 * 获取rom剩余容量
	 */
	public static String getAvailableRomCapacity() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize;
		long availableBlocks;
		if (AndroidVersionUtils.hasJellyBeanMR2()) {
			blockSize = stat.getBlockSizeLong();
			availableBlocks = stat.getAvailableBlocksLong();
		} else {
			blockSize = stat.getBlockSize();
			availableBlocks = stat.getAvailableBlocks();
		}

		return fileSize(blockSize * availableBlocks);
	}

	/**
	 * 获取root状态
	 */
	public static int getRootState() {
		if (-1 == rootState) {
			File f;
			final String kSuSearchPaths[] =
				{ "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/" };
			try {
				for (String kSuSearchPath : kSuSearchPaths) {
					f = new File(kSuSearchPath + "su");
					if (f.exists()) {
						rootState = 1;
					}
				}
			} catch (Exception ignored) {
			}
			rootState = 2;
		}

		return rootState;
	}

	//    /**
	//     * Get IP address from first non-localhost interface
	//     * @param ipv4  true=return ipv4, false=return ipv6
	//     * @return  address or empty string
	//     */
	//    public static String getIPAddress(boolean useIPv4) {
	//        try {
	//            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
	//            for (NetworkInterface intf : interfaces) {
	//                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
	//                for (InetAddress addr : addrs) {
	//                    if (!addr.isLoopbackAddress()) {
	//                        String sAddr = addr.getHostAddress().toUpperCase();
	//                        if(addr instanceof Inet4Address) {
	//                            if (useIPv4)
	//                                return sAddr;
	//                        } else {
	//                            if (!useIPv4) {
	//                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
	//                                return delim<0 ? sAddr : sAddr.substring(0, delim);
	//                            }
	//                        }
	//                    }
	//                }
	//            }
	//        } catch (Exception ex) { } // for now eat exceptions
	//        return "";
	//    }

	/**
	 * 获取路由器ip
	 */
	public static String getRouterIp(Context context) {
		WifiManager wifiManager = getWifiManager(context);
		if (wifiManager != null) {
			DhcpInfo dhcp = wifiManager.getDhcpInfo();
			if (dhcp != null) {
				//                return Formatter.formatIpAddress(dhcp.gateway);
				return intToIp(dhcp.gateway);
			}
		}
		return null;
	}

	/**
	 * 获取CPU型号
	 */
	public static String getCpuModel() {
		//        try {
		//            FileReader fr = new FileReader("/proc/cpuinfo");
		//            BufferedReader br = new BufferedReader(fr);
		//            String text = br.readLine();
		//            return text.split(":\\s+", 2)[1];
		//        } catch (Exception ignored) {
		//        }
		//        return null;
		return Build.HARDWARE;
	}

	/**
	 * 获取cpu频率
	 */
	public static String getCpuHz() {
		String result = "";
		ProcessBuilder cmd;
		try {
			String[] args = {
				"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"
			};
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (Exception ignored) {
		}
		return result.trim();
	}

	/**
	 * 获取cpu内核数
	 */
	public static int getNumberOfCPUCores() {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
			// Gingerbread doesn't support giving a single application access to both cores, but a
			// handful of devices (Atrix 4G and Droid X2 for example) were released with a dual-core
			// chipset and Gingerbread; that can let an app in the background run without impacting
			// the foreground application. But for our purposes, it makes them single core.
			return 1;
		}
		int cores = 1;
		try {
			cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
		} catch (Exception ignored) {
		}
		return cores;
	}

	private static final FileFilter CPU_FILTER = new FileFilter() {
		@Override public boolean accept(File pathname) {
			String path = pathname.getName();
			//regex is slow, so checking char by char.
			if (path.startsWith("cpu")) {
				for (int i = 3; i < path.length(); i++) {
					if (path.charAt(i) < '0' || path.charAt(i) > '9') {
						return false;
					}
				}
				return true;
			}
			return false;
		}
	};

	/**
	 * 获取sdcard容量
	 */
	public static String getSDCardMemory() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long bSize;
			long bCount;
			if (AndroidVersionUtils.hasJellyBeanMR2()) {
				bSize = sf.getBlockSizeLong();
				bCount = sf.getBlockCountLong();
			} else {
				bSize = sf.getBlockSize();
				bCount = sf.getBlockCount();
			}
			return fileSize(bSize * bCount);
		}
		return null;
	}

	/**
	 * 获取虚拟按键栏高度
	 */
	public static int getNavigationBarHeight(Context context) {
		int result = 0;
		if (hasNavBar(context)) {
			Resources res = context.getResources();
			int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
			if (resourceId > 0) {
				result = res.getDimensionPixelSize(resourceId);
			}
		}
		return result;
	}

	/**
	 * 检查是否存在虚拟按键栏
	 */
	private static boolean hasNavBar(Context context) {
		Resources res = context.getResources();
		int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
		if (resourceId != 0) {
			boolean hasNav = res.getBoolean(resourceId);
			// check override flag
			String sNavBarOverride = getNavBarOverride();
			if ("1".equals(sNavBarOverride)) {
				hasNav = false;
			} else if ("0".equals(sNavBarOverride)) {
				hasNav = true;
			}
			return hasNav;
		} else { // fallback
			return !ViewConfiguration.get(context).hasPermanentMenuKey();
		}
	}

	/**
	 * 判断虚拟按键栏是否重写
	 */
	private static String getNavBarOverride() {
		String sNavBarOverride = null;
		if (AndroidVersionUtils.hasKitKat()) {
			try {
				Class c = Class.forName("android.os.SystemProperties");
				Method m = c.getDeclaredMethod("get", String.class);
				m.setAccessible(true);
				sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
			} catch (Throwable e) {
			}
		}
		return sNavBarOverride;
	}

	/**
	 * 判断是否平板设备
	 *
	 * @return true:平板,false:手机
	 */
	public static boolean isTabletDevice(Context context) {
		return (context.getResources().getConfiguration().screenLayout
			& Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	private static NetworkInfo getNetworkInfo(Context context) {
		ConnectivityManager connectivityManager = getConnectivityManager(context);
		return connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
	}

	public static String getNetworkTypeName(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null && info.isConnected()) {
			switch (info.getType()) {
				case ConnectivityManager.TYPE_WIFI:
					return info.getTypeName();
				case ConnectivityManager.TYPE_MOBILE:
					return info.getSubtypeName();
			}
		}
		return UNKNOWN;
	}

	/**
	 * 获取网关
	 */
	public static String getGateway(Context context) {
		WifiManager wifiManager = getWifiManager(context);
		if (wifiManager != null) {
			DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
			return intToIp(dhcpInfo.gateway);
		}
		return null;
	}

	/**
	 * 转ip地址
	 */
	private static String intToIp(int paramInt) {
		return (paramInt & 0xFF)
			+ "."
			+ (0xFF & paramInt >> 8)
			+ "."
			+ (0xFF & paramInt >> 16)
			+ "."
			+ (0xFF & paramInt >> 24);
	}

	/**
	 * 获取IP地址(IPv4)
	 */
	public static String getIpAddress() {
		try {
			for (Enumeration<NetworkInterface> nilist = NetworkInterface.getNetworkInterfaces();
				nilist.hasMoreElements(); ) {
				NetworkInterface ni = nilist.nextElement();
				for (Enumeration<InetAddress> ialist = ni.getInetAddresses();
					ialist.hasMoreElements(); ) {
					InetAddress address = ialist.nextElement();
					if (!address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
						return address.getHostAddress();
					}
				}
			}
		} catch (SocketException ignored) {
		}
		return null;
	}

	/**
	 * Returns MAC address of the given interface name.
	 *
	 * @param interfaceName eth0, wlan0 or NULL=use first interface
	 * @return mac address or empty string
	 */
	private static String getMACAddress(String interfaceName) {
		try {
			List<NetworkInterface> interfaces =
				Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				if (interfaceName != null) {
					if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
				}
				byte[] mac = intf.getHardwareAddress();
				if (mac == null) return "";
				StringBuilder buf = new StringBuilder();
				for (int idx = 0; idx < mac.length; idx++)
					buf.append(String.format("%02X:", mac[idx]));
				if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
				return buf.toString();
			}
		} catch (Exception ignored) {
		} // for now eat exceptions
		return null;
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
	}

	/**
	 * 获取设备MAC地址
	 */
	public static String getDeviceMac(Context context) {
		String macSerial = null;
		//        // Linux命令方案
		//        try {
		//            Process pp = Runtime.getRuntime().exec(
		//                    "cat /sys/class/net/wlan0/address ");
		//            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
		//            LineNumberReader input = new LineNumberReader(ir);
		//            String str = "";
		//            for (; null != str;) {
		//                str = input.readLine();
		//                if (str != null) {
		//                    macSerial = str.trim();// 去空格
		//                    break;
		//                }
		//            }
		//            if (TextToolUtils.isEmpty(macSerial)) {
		//                macSerial = loadFileAsString("/sys/class/net/eth0/address")
		//                        .toUpperCase().substring(0, 17);
		//            }
		//        } catch (Exception e) {
		//            e.printStackTrace();
		//        }
		//
		//        if (TextToolUtils.isEmpty(macSerial)) {
		//            WifiInfo wifiInfo = getWifiInfo(context);
		//            if (wifiInfo != null) {
		//                macSerial = wifiInfo.getMacAddress();
		//            }
		//        }

		macSerial = getMACAddress("wlan0");
		if (TextUtils.isEmpty(macSerial)) {
			macSerial = getMACAddress("eth0");
		}
		if (TextUtils.isEmpty(macSerial)) {
			macSerial = getMACAddress(null);
		}
		return macSerial;
	}

	public static String getSerial(Context context) {
		String serial = null;
		if (AndroidVersionUtils.hasO()) {
			if (null != getTelephonyManager(context)) {
				serial = Build.getSerial();
			}
		} else {
			serial = Build.SERIAL;
		}
		return serial;
	}

	public static boolean isWifi(Context context) {
		ConnectivityManager connManager =
			(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // 获取网络服务
		if (null == connManager) { // 为空则认为无网络
			return false;
		}
		// 获取网络类型，如果为空，返回无网络
		NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
		if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
			return false;
		}
		// 判断是否为WIFI
		NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (null != wifiInfo) {
			NetworkInfo.State state = wifiInfo.getState();
			if (null != state) {
				if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
					return true;
				}
			}
		}
		return false;
	}

	public static String getBrand() {
		if (android.os.Build.BRAND == null) {
			return "";
		}
		return android.os.Build.BRAND;
	}

	public static String getMode() {
		if (Build.MODEL == null) {
			return "";
		}
		return Build.MODEL;
	}

	public static String getSysVersion() {
		if (android.os.Build.VERSION.RELEASE == null) {
			return "";
		}
		return android.os.Build.VERSION.RELEASE;
	}
}
