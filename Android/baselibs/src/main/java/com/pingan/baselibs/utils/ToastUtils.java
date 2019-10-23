package com.pingan.baselibs.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.pingan.baselibs.ApplicationManager;
import com.pingan.baselibs.R;

public class ToastUtils {

	private static Context sCtx;
	private static Toast sToast;

	public static void toastMsg(String msg) {
		if (TextUtils.isEmpty(msg)) {
			return;
		}
		if (sToast != null) {
			sToast.cancel();
			sToast = null;
		}
		checkContext();
		View view = LayoutInflater.from(sCtx).inflate(R.layout.toast, null);
		TextView toastTv = view.findViewById(R.id.tv_toast);
		toastTv.setText(msg);
		sToast = new Toast(sCtx);
		sToast.setDuration(Toast.LENGTH_SHORT);
		//sToast = Toast.makeText(sCtx, msg, Toast.LENGTH_SHORT);
		sToast.setView(view);
		sToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		sToast.show();
	}

	public static void toastMsg(int msgId) {
		if (msgId == 0) {
			return;
		}
		checkContext();
		toastMsg(sCtx.getResources().getString(msgId));
	}

	private static void checkContext() {
		if (sCtx == null) {
			sCtx = ApplicationManager.getApplication();
		}
	}
}
