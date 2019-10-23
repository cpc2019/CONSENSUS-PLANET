package com.pingan.baselibs.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.pingan.baselibs.ApplicationManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * App工具类
 * <p>
 * Created by yelongfei490 on 16/8/10.
 */
public class AppUtils {

	private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
	private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

	//public static String getVersionName(Context context) {
	//	String versionName = null;
	//	try {
	//		versionName = context.getPackageManager()
	//			.getPackageInfo(context.getPackageName(),
	//				PackageManager.GET_ACTIVITIES).versionName;
	//	} catch (PackageManager.NameNotFoundException ignored) {
	//	}
	//	return versionName;
	//}

	public static String getVersionName(Context mContext) {
		String versionName = "";
		try {
			if (mContext == null) {
				Log.e("getVersionName", "context is null");
				return "";
			}
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			//Log.logExc(e);

		}
		return versionName;
	}

	public static int getVersionCode(Context mContext) {
		int versionCode = 0;
		try {
			if (mContext == null) {
				Log.e("getVersionCode", "context is null");
				return versionCode;
			}
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
			versionCode = pi.versionCode;
			return versionCode;
		} catch (Exception e) {
			//Log.logExc(e);
		}
		return versionCode;
	}
	//public static int getVersionCode(Context context) {
	//	int versionCode = 0;
	//	try {
	//		versionCode = context.getPackageManager()
	//			.getPackageInfo(context.getPackageName(),
	//				PackageManager.GET_ACTIVITIES).versionCode;
	//	} catch (PackageManager.NameNotFoundException ignored) {
	//	}
	//	return versionCode;
	//}

	public static String getAppName(Context context) {
		int labelRes = context.getApplicationInfo().labelRes;
		if (0 != labelRes) {
			return context.getString(labelRes);
		}
		return null;
	}

	/**
	 * 获取进程号对应的进程名
	 *
	 * @param pid 进程号
	 * @return 进程名
	 */
	public static String getProcessName(int pid) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
			String processName = reader.readLine();
			if (!TextUtils.isEmpty(processName)) {
				processName = processName.trim();
			}
			return processName;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	public static String packageId = null;

	public static String getManifestPlaceholder(String metaKey) {
		ApplicationInfo appInfo = null;
		if (packageId != null) return packageId;
		try {
			appInfo = ApplicationManager.getApplication()
				.getPackageManager()
				.getApplicationInfo(ApplicationManager.getApplication().getPackageName(),
					PackageManager.GET_META_DATA);
			packageId = appInfo.metaData.getString(metaKey);

			return packageId;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Integer statusHeight = null;

	public static int getStatusBarHeight(Context context) {
		if (statusHeight != null) {
			return statusHeight;
		}
		int resourceId =
			context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			statusHeight = context.getResources().getDimensionPixelSize(resourceId);
			return statusHeight;
		}
		return 0;
	}

	public static Point point = null;

	public static Point getDisPlaySize(Activity activity) {
		if (point != null) {
			return point;
		}
		Point point = new Point();
		activity.getWindow().getWindowManager().getDefaultDisplay().getSize(point);
		return point;
	}

	public static boolean isMainThread(Context context) {
		int pId = Process.myPid();
		String processName = null;
		ActivityManager activityManager =
			(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
			activityManager.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses) {
			if (processInfo.pid == pId) {
				processName = processInfo.processName;
				break;
			}
		}
		if (processName != null && processName.equals(context.getPackageName())) {
			return true;
		}

		return false;
	}

	public static boolean isApkDebugable(Context context) {
		try {
			ApplicationInfo info = context.getApplicationInfo();
			return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * 检查手机上是否安装了指定的软件
	 *
	 * @param context context
	 * @param pkgName 应用包名
	 * @return true:已安装；false：未安装
	 */
	public static boolean isPkgInstalled(Context context, String pkgName) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
		} catch (PackageManager.NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		return packageInfo != null;
	}

	//判断当前界面显示的是哪个Activity
	public static String getTopActivity(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		return cn.getClassName();
	}

	/**
	 * 启动App
	 */
	public static void onStartApp(Context mContext, String packageName) {
		PackageManager packageManager = mContext.getPackageManager();
		Intent intent = packageManager.getLaunchIntentForPackage(packageName);
		mContext.startActivity(intent);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static boolean isNotificationEnabled(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			///< 8.0手机以上
			if (((NotificationManager) context.getSystemService(
				Context.NOTIFICATION_SERVICE)).getImportance()
				== NotificationManager.IMPORTANCE_NONE) {
				return false;
			}
		}

		AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
		ApplicationInfo appInfo = context.getApplicationInfo();
		String pkg = context.getApplicationContext().getPackageName();
		int uid = appInfo.uid;

		Class appOpsClass = null;
		try {
			appOpsClass = Class.forName(AppOpsManager.class.getName());
			Method checkOpNoThrowMethod =
				appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
			Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

			int value = (Integer) opPostNotificationValue.get(Integer.class);
			return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg)
				== AppOpsManager.MODE_ALLOWED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 通知权限申请
	 */
	public static void requestNotify(Activity activity, int requestCode) {
		/**
		 * 跳到通知栏设置界面
		 * @param context
		 */
		Intent localIntent = new Intent();
		///< 直接跳转到应用通知设置的代码
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			localIntent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
			localIntent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.getPackageName());
		} else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
			&& android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
			localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
			localIntent.putExtra("app_package", activity.getPackageName());
			localIntent.putExtra("app_uid", activity.getApplicationInfo().uid);
		} else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
			localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			localIntent.addCategory(Intent.CATEGORY_DEFAULT);
			localIntent.setData(Uri.parse("package:" + activity.getPackageName()));
		} else {
			///< 4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
			localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (Build.VERSION.SDK_INT >= 9) {
				localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
				localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
			} else if (Build.VERSION.SDK_INT <= 8) {
				localIntent.setAction(Intent.ACTION_VIEW);
				localIntent.setClassName("com.android.settings",
					"com.android.setting.InstalledAppDetails");
				localIntent.putExtra("com.android.settings.ApplicationPkgName",
					activity.getPackageName());
			}
		}
		activity.startActivityForResult(localIntent, requestCode);
	}
}