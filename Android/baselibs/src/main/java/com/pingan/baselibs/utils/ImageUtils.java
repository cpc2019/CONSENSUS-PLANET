package com.pingan.baselibs.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtils {

	private static String DIRECTORY_NAME = "Durian video";

	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * 保存图片到本地相册
	 */
	@Deprecated public static void saveBitmap(Context context, Bitmap bitmap) {
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(), "temp_img");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".png";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		File filePath = new File(Environment.getExternalStorageDirectory().getPath(), "llws");
		String desPath = null;
		if (filePath.exists()) {
			try {
				filePath.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		desPath = new StringBuilder().append(filePath.getPath()).append(fileName).toString();
		ImageUtils.compressImage(file.getPath(), desPath, 720, 1080, 200);
		// 把文件插入到系统图库
		//try {
		//	MediaStore.Images.Media.insertImage(context.getContentResolver(),
		//		file.getAbsolutePath(), fileName, null);
		//} catch (FileNotFoundException e) {
		//	e.printStackTrace();
		//}
       /* // 通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + "/sdcard/namecard/")));*/
		scanPhoto(context, desPath);
	}

	/**
	 * @作者 jadon
	 * @创建日期 2019/4/27 18:20
	 * 刷新系统图片，以相册能看到保存的图片
	 */
	private static void scanPhoto(Context ctx, String imgFileName) {
		if (TextUtils.isEmpty(imgFileName)) return;
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File file = new File(imgFileName);
		Uri contentUri = Uri.fromFile(file);
		mediaScanIntent.setData(contentUri);
		ctx.sendBroadcast(mediaScanIntent);
	}

	public static Bitmap url2Bitmap(String imageUrl) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();

			BitmapFactory.Options options = new BitmapFactory.Options();

			//BitmapFactory.decodeStream(is, null, options);
			options.inJustDecodeBounds = true;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inSampleSize = 2;

			//options.inSampleSize = calculateInSampleSize(options, 720, 1080);
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeStream(is, null, options);
			Log.d("dd", "压缩后：图片占内存大小"
				+ bitmap.getByteCount() / 1024
				+ "MB / 宽度="
				+ bitmap.getWidth()
				+ "高度="
				+ bitmap.getHeight());
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	public static Bitmap urlToBitmap(String imageUrl) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();

			BitmapFactory.Options options = new BitmapFactory.Options();

			//BitmapFactory.decodeStream(is, null, options);
			//options.inJustDecodeBounds = true;
			//options.inPreferredConfig = Bitmap.Config.RGB_565;
			//options.inSampleSize = 2;

			//options.inSampleSize = calculateInSampleSize(options, 720, 1080);
			//options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeStream(is, null, options);
			Log.d("dd", "压缩后：图片占内存大小"
				+ bitmap.getByteCount() / 1024
				+ "MB / 宽度="
				+ bitmap.getWidth()
				+ "高度="
				+ bitmap.getHeight());
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	public static void urlToBitmap(Context context, String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();

			BitmapFactory.Options options = new BitmapFactory.Options();

			//BitmapFactory.decodeStream(is, null, options);
			//options.inJustDecodeBounds = true;
			//options.inPreferredConfig = Bitmap.Config.RGB_565;
			//options.inSampleSize = 2;

			//options.inSampleSize = calculateInSampleSize(options, 720, 1080);
			//options.inJustDecodeBounds = false;
			Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

			//图片保存到本地
			saveImageToGallery(context, bitmap);

			Log.d("dd", "压缩后：图片占内存大小"
				+ bitmap.getByteCount() / 1024
				+ "MB / 宽度="
				+ bitmap.getWidth()
				+ "高度="
				+ bitmap.getHeight());
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存图片
	 *
	 * @param imageUrl 图片地址
	 */
	public static void saveBitmap(Context context, String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();

			File srcPath = new File(Environment.getExternalStorageDirectory(), "temp_img");
			if (!srcPath.exists()) {
				srcPath.mkdirs();
			}
			String fileName = System.currentTimeMillis() + ".png";
			File srcFile = new File(srcPath, fileName);
			OutputStream outputStream = new FileOutputStream(srcFile);

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			outputStream.close();
			is.close();

			File descPath = new File(Environment.getExternalStorageDirectory(), "temp_img");
			if (!descPath.exists()) {
				descPath.mkdirs();
			}
			File desFile = new File(descPath, System.currentTimeMillis() + ".png");
			ImageUtils.compressImage(srcFile.getPath(), desFile, 720, 1080, 120);

			// 把文件插入到系统图库
			try {
				MediaStore.Images.Media.insertImage(context.getContentResolver(), desFile.getPath(),
					fileName, null);
				//扫描整个文件夹，这样文件多的话会很慢，且扫描过程不能访问
				//context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
				context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
					Uri.fromFile(new File(desFile.getPath()))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			srcFile.delete();
			desFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 保存文件到指定路径 */
	public static boolean saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片
		String storePath = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ DIRECTORY_NAME;
		File appDir = new File(storePath);
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			//通过io流的方式来压缩保存图片
			boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
			fos.flush();
			fos.close();
			if (null != bmp && !bmp.isRecycled()) {
				bmp.recycle();
			}
			//把文件插入到系统图库
			//MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

			//保存图片后发送广播通知更新数据库
			Uri uri = Uri.fromFile(file);
			context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
			if (isSuccess) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void compressImage(String srcPath, String desPath, int desWidth, int desHeight,
		int desSize) {
		compressImage(srcPath, new File(desPath), desWidth, desHeight, desSize);
	}

	/**
	 * 图片压缩
	 *
	 * @param srcPath 源图片路径
	 * @param desPath 压缩后的图片路径
	 * @param desWidth 压缩的目标宽度
	 * @param desHeight 压缩的目标高度
	 * @param desSize 压缩的目标大小（kb）
	 */
	public static void compressImage(String srcPath, File desPath, int desWidth, int desHeight,
		int desSize) {
		byte[] fileBytes = file2byte(srcPath);
		BitmapFactory.Options options = new BitmapFactory.Options();
		//只读取图片宽高，图片不写入内存
		options.inJustDecodeBounds = true;
		ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes);
		Bitmap desBitmap = BitmapFactory.decodeStream(bais, null, options);
		int inSampleSize;
		//按照长边来缩放
		if (options.outHeight > options.outWidth) {
			inSampleSize = options.outHeight / desHeight;
		} else {
			inSampleSize = options.outWidth / desWidth;
		}
		if (inSampleSize < 1) {
			inSampleSize = 1;
		}
		options.inSampleSize = inSampleSize;
		options.inJustDecodeBounds = false;
		bais = new ByteArrayInputStream(fileBytes);
		desBitmap = BitmapFactory.decodeStream(bais, null, options);
		int quality = 100;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//压缩尺寸
		desBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

		//压缩质量
		while (baos.toByteArray().length / 1024 > desSize && quality > 0) {
			baos.reset();
			desBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			quality -= 10;
		}
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(desPath);
			outputStream.write(baos.toByteArray());
			outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			desBitmap.recycle();
		}
	}

	public static byte[] file2byte(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
		int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 给图片添加文字到左上角
	 */
	public static Bitmap drawTextToBottom(Context context, Bitmap bitmap, String text, int size,
		int color, int paddingBottom) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setShadowLayer(10, 0, 0, Color.parseColor("#aa888888"));
		paint.setColor(color);
		paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size,
			context.getResources().getDisplayMetrics()));
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		return drawTextToBitmap(context, bitmap, text, paint, bounds, 10,
			bitmap.getHeight() - DisplayUtils.dp2px(context, paddingBottom));
	}

	/**
	 * 绘制文字到右下角
	 */
	public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text,
		int size, int color, int paddingRight, int paddingBottom) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setTextSize(DisplayUtils.dp2px(context, size));
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		return drawTextToBitmap(context, bitmap, text, paint, bounds,
			bitmap.getWidth() - bounds.width() - DisplayUtils.dp2px(context, paddingRight),
			bitmap.getHeight() - DisplayUtils.dp2px(context, paddingBottom));
	}

	//图片上绘制文字
	private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text, Paint paint,
		Rect bounds, int paddingLeft, int paddingTop) {
		android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

		paint.setDither(true); // 获取跟清晰的图像采样
		paint.setFilterBitmap(true);// 过滤一些
		if (bitmapConfig == null) {
			bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
		}
		bitmap = bitmap.copy(bitmapConfig, true);
		Canvas canvas = new Canvas(bitmap);

		canvas.drawText(text, paddingLeft, paddingTop, paint);
		return bitmap;
	}

	public static Bitmap onViewToBitmap(View view) {
		if (null == view) {
			return null;
		}

		int w = view.getWidth();
		int h = view.getHeight();

		Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bmp);

		c.drawColor(Color.WHITE);
		/** 如果不设置canvas画布为白色，则生成透明 */
		//view.layout(0, 0, w, h);
		view.draw(c);
		return bmp;
	}

	public static Bitmap onViewToBitmap(View view, int width, int Height) {
		if (null == view) {
			return null;
		}

		Bitmap bmp = Bitmap.createBitmap(width, Height, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bmp);

		c.drawColor(Color.WHITE);
		/** 如果不设置canvas画布为白色，则生成透明 */
		//view.layout(0, 0, w, h);
		view.draw(c);
		return bmp;
	}

	public static void onViewToImage(Context context, View view) {
		Bitmap bitmap = onViewToBitmap(view);
		if (null != bitmap) {
			saveBitmap(context, bitmap);
		}
	}

	public static String getExternalStroragePath(Context ctx) {
		Context var0 = ctx;
		int var1 = Build.VERSION.SDK_INT;
		if (var1 >= 12) {
			try {
				StorageManager var23 = (StorageManager)var0.getSystemService(Context.STORAGE_SERVICE);
				Method var24 = StorageManager.class.getMethod("getVolumeList");
				Method var4 = StorageManager.class.getMethod("getVolumeState", String.class);
				Object[] var5 = (Object[])((Object[])var24.invoke(var23));
				String var6 = null;
				String var7 = null;
				Boolean var8 = false;
				String var9 = "";
				String var10 = "";
				String var11 = "";
				String var12 = "";
				String var13 = null;
				Object[] var14 = var5;
				int var15 = var5.length;

				for(int var16 = 0; var16 < var15; ++var16) {
					Object var17 = var14[var16];
					Method var18 = var17.getClass().getMethod("getPath");
					Method var19 = var17.getClass().getMethod("isRemovable");
					var7 = (String)var18.invoke(var17);
					var6 = (String)var4.invoke(var23, var18.invoke(var17));
					var8 = (Boolean)var19.invoke(var17);
					if (!var7.toLowerCase().contains("private")) {
						if (var8) {
							if (null != var7 && null != var6 && var6.equals("mounted")) {
								if (var1 <= 18) {
									var13 = var7;
								} else {
									try {
										File[] var20 = var0.getExternalFilesDirs((String)null);
										if (var20 != null) {
											if (var20.length > 1) {
												var13 = var20[1].getAbsolutePath();
											} else {
												var13 = var7;
											}
										}
									} catch (Exception var21) {
										var13 = var7;
									}
								}
								break;
							}
						} else {
							var10 = var7;
							var12 = var6;
						}
					}
				}

				if (var1 <= 18) {
					if (null == var13 && null != var10 && null != var12 && var12.equals("mounted")) {
						var13 = var10;
					}

					return var13;
				}

				if (null != var10 && null != var12 && var12.equals("mounted")) {
					var13 = var10;
				}

				return var13;
			} catch (Throwable var22) {
				;
			}
		}

		File var2 = null;
		boolean var3 = Environment.getExternalStorageState().equals("mounted");
		if (var3) {
			var2 = Environment.getExternalStorageDirectory();
			return var2.toString();
		} else {
			return null;
		}
	}

}
