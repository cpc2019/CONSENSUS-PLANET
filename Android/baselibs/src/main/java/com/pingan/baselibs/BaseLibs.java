package com.pingan.baselibs;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.pingan.baselibs.utils.PicassoUtils;
import com.pingan.baselibs.utils.SDCardUtils;
import com.pingan.baselibs.utils.StethoUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import java.io.File;

public class BaseLibs {

	private static final String SDCARD_LOG_FILE_DIR = "log";
	public static boolean isDarkTheme;
	public static String timsStamp;
	public static boolean openServerTime = true;
	public static void init(Application app) {
		init(app, false);
	}

	public static void init(Application app, boolean isDark) {
		ApplicationManager.init(app);
		initXLog(app);
		//StethoUtils.init(app);
		initPicasso();
		//initLeakCanary();
		isDarkTheme = isDark;
	}

	private static void initXLog(Context context) {
		LogConfiguration config =
			new LogConfiguration.Builder().tag(context.getPackageName()).nt().st(1).b().build();
		Printer androidPrinter = new AndroidPrinter();
		Printer filePrinter = null;
		String appExternalRoot = SDCardUtils.getAppRoot(context);
		if (!TextUtils.isEmpty(appExternalRoot)) {
			File logFileDir = new File(appExternalRoot, SDCARD_LOG_FILE_DIR);
			if (logFileDir.exists() || logFileDir.mkdirs()) {
				filePrinter =
					new FilePrinter.Builder(logFileDir.getAbsolutePath()).fileNameGenerator(
						new DateFileNameGenerator()).build();
			}
		}
		if (filePrinter == null) {
			XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE, config, androidPrinter);
		} else {
			XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE, config, androidPrinter,
				filePrinter);
		}
	}

	public static RefWatcher refWatcher;

	/**
	 * 初始化LeakCanary
	 */
	private static void initLeakCanary() {
		if (LeakCanary.isInAnalyzerProcess(ApplicationManager.getApplication())) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return;
		}
		refWatcher = LeakCanary.install(ApplicationManager.getApplication());
	}

	private static void initPicasso() {
		PicassoUtils.init(ApplicationManager.getApplication(), android.R.color.darker_gray);
	}
}
