package com.pingan.baselibs.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

public class TextToolUtils {

	/**
	 * 复制文本到剪贴板
	 *
	 * @param context 上下文
	 * @param content 复制到文本
	 */
	public static void copy(Context context, String content) {
		if(TextUtils.isEmpty(content)){
			return;
		}
		ClipboardManager clipboardManager =
			(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboardManager.setPrimaryClip(ClipData.newPlainText("Label", content));
	}
}
