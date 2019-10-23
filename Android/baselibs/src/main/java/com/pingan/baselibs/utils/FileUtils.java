package com.pingan.baselibs.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import com.elvishew.xlog.XLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	/*
	 * 根目录
	 */
	public static final String DIR_BASE = Environment.getExternalStorageDirectory() + "/LLWS";
	/*
	 * 声网日志路径
	 */
	public static final String AGORARTC_DIR = DIR_BASE + "/AgoraRtc";
	/*
	 * 系统崩溃的保存信息
	 */
	public static final String CRASH_DIR = DIR_BASE + "/Crash";

	/**
	 * 获取存储文件的根路径
	 *
	 * @param context
	 * @return
	 */
	public static File getFileRootLocation(Context context) {
		File fileLocation;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			fileLocation = Environment.getExternalStorageDirectory();
		} else {
			fileLocation = context.getFilesDir();
		}
		return fileLocation;
	}

	/**
	 * 获取sdcard状态
	 */
	public static boolean getSDCardState() {
		if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
			ToastUtils.toastMsg("SD卡不可用, 请检查内存卡...");
			return false;
		}
		return true;
	}

	/**
	 * 创建应用文件夹
	 */
	public static boolean createDir() {
		if (!getSDCardState()) {
			return false;
		}
		File dir = new File(AGORARTC_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		dir = new File(CRASH_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return true;
	}

	public static void assetsToImage(Context context, String assetName) {
		FileOutputStream fos = null;
		try {
			InputStream is = context.getAssets().open(assetName);

			File filePath =
				new File(Environment.getExternalStorageDirectory().getPath(), "temp_img");
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			String fileName = System.currentTimeMillis() + ".png";
			File desFile = new File(filePath, fileName);

			if (!desFile.exists()) {
				desFile.createNewFile();
			}

			byte[] data = new byte[1024];
			int read = 0;
			fos = new FileOutputStream(desFile);
			while ((read = is.read(data)) > -1) {
				fos.write(data, 0, read);
			}
			fos.flush();
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.fromFile(desFile);
			intent.setData(uri);
			context.sendBroadcast(intent);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据Uri获取图片的绝对路径
	 *
	 * @param context 上下文对象
	 * @param uri 图片的Uri
	 * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
	 */
	public static String getLocalPathFromUri(Context context, Uri uri) {
		int sdkVersion = Build.VERSION.SDK_INT;
		if (sdkVersion >= 19) { // api >= 19
			return getRealPathFromUriAboveApi19(context, uri);
		} else { // api < 19
			return getRealPathFromUriBelowAPI19(context, uri);
		}
	}

	/**
	 * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
	 *
	 * @param context 上下文对象
	 * @param uri 图片的Uri
	 * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
	 */
	private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
		return getDataColumn(context, uri, null, null);
	}

	/**
	 * 适配api19及以上,根据uri获取图片的绝对路径
	 *
	 * @param context 上下文对象
	 * @param uri 图片的Uri
	 * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
	 */
	@SuppressLint("NewApi") private static String getRealPathFromUriAboveApi19(Context context,
		Uri uri) {
		String filePath = null;
		if (DocumentsContract.isDocumentUri(context, uri)) {
			// 如果是document类型的 uri, 则通过document id来进行处理
			String documentId = DocumentsContract.getDocumentId(uri);
			if (isMediaDocument(uri)) { // MediaProvider
				// 使用':'分割
				String id = documentId.split(":")[1];

				String selection = MediaStore.Images.Media._ID + "=?";
				String[] selectionArgs = { id };
				filePath =
					getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection,
						selectionArgs);
			} else if (isDownloadsDocument(uri)) { // DownloadsProvider
				Uri contentUri =
					ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(documentId));
				filePath = getDataColumn(context, contentUri, null, null);
			}
		} else if ("content".equalsIgnoreCase(uri.getScheme())) {
			// 如果是 content 类型的 Uri
			filePath = getDataColumn(context, uri, null, null);
		} else if ("file".equals(uri.getScheme())) {
			// 如果是 file 类型的 Uri,直接获取图片对应的路径
			filePath = uri.getPath();
		}
		return filePath;
	}

	/**
	 * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
	 */
	private static String getDataColumn(Context context, Uri uri, String selection,
		String[] selectionArgs) {
		String path = null;

		String[] projection = new String[] { MediaStore.Images.Media.DATA };
		Cursor cursor = null;
		try {
			cursor =
				context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
				path = cursor.getString(columnIndex);
			}
		} catch (Exception e) {
			if (cursor != null) {
				cursor.close();
			}
		}
		return path;
	}

	/**
	 * @param uri the Uri to check
	 * @return Whether the Uri authority is MediaProvider
	 */
	private static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri the Uri to check
	 * @return Whether the Uri authority is DownloadsProvider
	 */
	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	//针对非系统影音资源文件夹
	public static void insertIntoMediaStore(Context context, boolean isVideo, File saveFile,
		long createTime) {
		ContentResolver mContentResolver = context.getContentResolver();
		if (createTime == 0) createTime = System.currentTimeMillis();
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.TITLE, saveFile.getName());
		values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.getName());
		//值一样，但是还是用常量区分对待
		values.put(isVideo ? MediaStore.Video.VideoColumns.DATE_TAKEN
			: MediaStore.Images.ImageColumns.DATE_TAKEN, createTime);
		values.put(MediaStore.MediaColumns.DATE_MODIFIED, System.currentTimeMillis());
		values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis());
		if (!isVideo) values.put(MediaStore.Images.ImageColumns.ORIENTATION, 0);
		values.put(MediaStore.MediaColumns.DATA, saveFile.getAbsolutePath());
		values.put(MediaStore.MediaColumns.SIZE, saveFile.length());
		values.put(MediaStore.MediaColumns.MIME_TYPE,
			isVideo ? getVideoMimeType(saveFile.getPath()) : "image/jpeg");
		//插入
		mContentResolver.insert(isVideo ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI
			: MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}

	// 获取video的mine_type,暂时只支持mp4,3gp
	private static String getVideoMimeType(String path) {
		String lowerPath = path.toLowerCase();
		if (lowerPath.endsWith("mp4") || lowerPath.endsWith("mpeg4")) {
			return "video/mp4";
		} else if (lowerPath.endsWith("3gp")) {
			return "video/3gp";
		}
		return "video/mp4";
	}

	public static String getPathFromUri(Context mContext, Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		CursorLoader loader = new CursorLoader(mContext, contentUri, proj, null, null, null);
		Cursor cursor = loader.loadInBackground();
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String string = cursor.getString(column_index);
		cursor.close();
		return string;
	}

	public static String getFilePathFromURI(Context context, Uri contentUri) {
		File rootDataDir = context.getFilesDir();
		String fileName = getFileName(contentUri);
		if (!TextUtils.isEmpty(fileName)) {
			File copyFile = new File(rootDataDir + File.separator + fileName);
			copyFile(context, contentUri, copyFile);
			return copyFile.getAbsolutePath();
		}
		return null;
	}

	public static String getFileName(Uri uri) {
		if (uri == null) return null;
		String fileName = null;
		String path = uri.getPath();
		int cut = path.lastIndexOf('/');
		if (cut != -1) {
			fileName = path.substring(cut + 1);
		}
		return fileName;
	}

	public static void copyFile(Context context, Uri srcUri, File dstFile) {
		try {
			InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
			if (inputStream == null) return;
			OutputStream outputStream = new FileOutputStream(dstFile);
			copyStream(inputStream, outputStream);
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int copyStream(InputStream input, OutputStream output)
		throws Exception, IOException {
		final int BUFFER_SIZE = 1024 * 2;
		byte[] buffer = new byte[BUFFER_SIZE];
		BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
		BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
		int count = 0, n = 0;
		try {
			while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
				out.write(buffer, 0, n);
				count += n;
			}
			out.flush();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		return count;
	}

	/**
	 * 读取assets文件数据
	 */
	public static String getFromAssets(Context context, String fileName) {
		String result = "";
		try {
			InputStreamReader inputReader =
				new InputStreamReader(context.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			while ((line = bufReader.readLine()) != null) result += line;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取文件夹下的所有子文件
	 */
	public static List<File> getFilesAll(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		if (files == null) {
			XLog.e("error", "空目录");
			return null;
		}
		List<File> s = new ArrayList<>();
		for (int i = 0; i < files.length; i++) {
			s.add(files[i]);
		}
		return s;
	}

	/**
	 * 删除文件
	 *
	 * @param file 文件
	 */
	public static boolean deleteFile(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		}
		return true;
	}
}

