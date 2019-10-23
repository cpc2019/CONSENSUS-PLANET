package com.pingan.baselibs.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.pingan.baselibs.R;

import static com.bumptech.glide.request.RequestOptions.signatureOf;

/**
 * CHENGC
 * 2018/9/23
 */
public class ImageLoadUtils {

	public static final String URL = "url";
	public static final String BINARY = "binary";

	/**
	 * 初始化 加载图片默认图和失败的图片
	 *
	 * @param defaultIds 默认显示的icon
	 * @param errorIds 加载失败的icon
	 */
	private static RequestOptions initGlideOptions(int defaultIds, int errorIds) {
		RequestOptions options = new RequestOptions();
		options.placeholder(defaultIds == 0 ? R.drawable.img_default : defaultIds)
			.error(errorIds == 0 ? R.drawable.img_default : errorIds)
			.fallback(errorIds == 0 ? R.drawable.img_default : errorIds);
		return options;
	}

	/**
	 * 初始化 加载图片默认图和失败的图片
	 *
	 * @param defaultIds 默认显示的icon
	 * @param errorIds 加载失败的icon
	 */
	private static RequestOptions initGlideOptions(int defaultIds, int errorIds, int width,
		int height) {
		RequestOptions options = new RequestOptions();
		options.override(width, height)
			.placeholder(defaultIds == 0 ? R.drawable.img_default : defaultIds)
			.error(errorIds == 0 ? R.drawable.img_default : errorIds)
			.fallback(errorIds == 0 ? R.drawable.img_default : errorIds);
		return options;
	}

	/**
	 * 初始化加载圆形图片
	 *
	 * @param defaultIds 默认显示的icon
	 * @param errorIds 加载失败的icon
	 */
	private static RequestOptions initGlideCircleOptions(int defaultIds, int errorIds) {
		RequestOptions options = new RequestOptions();
		options.placeholder(defaultIds == 0 ? R.mipmap.ic_default_avatar : defaultIds)
			.error(errorIds == 0 ? R.mipmap.ic_default_avatar : errorIds)
			.fallback(errorIds == 0 ? R.mipmap.ic_default_avatar : errorIds)
			.apply(RequestOptions.circleCropTransform());
		return options;
	}

	/**
	 * 加载圆形头像
	 *
	 * @param defaultIds 默认显示的icon
	 * @param errorIds 加载失败的icon
	 */
	private static RequestOptions initGlideCircleHeadOptions(int defaultIds, int errorIds) {
		RequestOptions options = new RequestOptions();
		options.placeholder(defaultIds == 0 ? R.mipmap.ic_default_avatar : defaultIds)
			.error(errorIds == 0 ? R.mipmap.ic_default_avatar : errorIds)
			.fallback(errorIds == 0 ? R.mipmap.ic_default_avatar : errorIds)
			.apply(RequestOptions.circleCropTransform());
		return options;
	}

	/**
	 * 加载图片
	 *
	 * @param mContext 上下文
	 */
	public static final void onImageView(Context mContext, ImageView imageView, String url,
		String type, int defaultIds, int errorIds) {
		if (URL.equals(type)) {
			Glide.with(mContext)
				.load(url)
				.apply(initGlideOptions(defaultIds, errorIds))
				.into(imageView);
		} else if (BINARY.equals(type)) {
			Glide.with(mContext)
				.load(null == stringToByte(url) ? "" : stringToByte(url))
				.apply(initGlideOptions(defaultIds, errorIds))
				.apply(signatureOf(new ObjectKey(url)))
				.into(imageView);
		}
	}

	/**
	 * 加载图片
	 *
	 * @param mContext 上下文
	 */
	public static final void onImageView(Context mContext, ImageView imageView, String url,
		String type, RequestOptions requestOptions) {
		if (URL.equals(type)) {
			Glide.with(mContext).load(url).apply(requestOptions).into(imageView);
		} else if (BINARY.equals(type)) {
			Glide.with(mContext)
				.load(null == stringToByte(url) ? "" : stringToByte(url))
				.apply(requestOptions)
				.apply(signatureOf(new ObjectKey(url)))
				.into(imageView);
		}
	}

	public static final void onImageView(Context mContext, ImageView imageView, String url,
		int defaultIds) {
		Glide.with(mContext)
			.load(null == stringToByte(url) ? "" : stringToByte(url))
			.apply(initGlideOptions(defaultIds, defaultIds, 480, 320))
			.apply(signatureOf(new ObjectKey(url)))
			.into(imageView);
	}

	public static final void onImageView(Context mContext, ImageView imageView, String url,
		int defaultIds, int errorIds) {
		onImageView(mContext, imageView, url, URL, defaultIds, errorIds);
	}

	public static final void onImageView(Context mContext, ImageView imageView, String url) {
		onImageView(mContext, imageView, url, URL, R.drawable.img_default, R.drawable.img_default);
	}

	public static final void onImageView(Context mContext, ImageView imageView, String url,
		RequestOptions requestOptions) {
		onImageView(mContext, imageView, url, URL, requestOptions);
	}

	public static final void onImageView(Context mContext, ImageView imageView, String url,
		String type) {
		if (TextUtils.isEmpty(type)) {
			onImageView(mContext, imageView, url, URL, R.drawable.img_default,
				R.drawable.img_default);
		} else {
			onImageView(mContext, imageView, url, type, R.drawable.img_default,
				R.drawable.img_default);
		}
	}

	public static final void onImageView(Context mContext, ImageView imageView, String url,
		String type, int defaultimg) {
		if (TextUtils.isEmpty(type)) {
			onImageView(mContext, imageView, url, URL, R.drawable.img_default,
				R.drawable.img_default);
		} else {
			onImageView(mContext, imageView, url, type,
				defaultimg > 0 ? defaultimg : R.drawable.img_default,
				defaultimg > 0 ? defaultimg : R.drawable.img_default);
		}
	}

	public static final void onImageViewCircle(Context mContext, ImageView imageView, String url) {
		onImageViewCircle(mContext, imageView, url, URL, R.mipmap.ic_default_avatar,
			R.mipmap.ic_default_avatar);
	}

	public static final void onImageViewCircle(Context mContext, ImageView imageView, String url,
		String type) {
		if (TextUtils.isEmpty(type)) {
			onImageViewCircle(mContext, imageView, url, URL, R.mipmap.ic_default_avatar,
				R.mipmap.ic_default_avatar);
		} else {
			onImageViewCircle(mContext, imageView, url, type, R.mipmap.ic_default_avatar,
				R.mipmap.ic_default_avatar);
		}
	}

	public static final void onImageViewCircle(Context mContext, ImageView imageView, String url,
		int defaultIds, int errorIds) {
		onImageViewCircle(mContext, imageView, url, URL, defaultIds, errorIds);
	}

	public static final void onImageViewCircle(Context mContext, ImageView imageView, String url,
		String type, int defaultIds, int errorIds) {
		if (URL.equals(type)) {
			if (null != mContext) {
				Glide.with(mContext)
					.load(url)
					.apply(initGlideCircleOptions(defaultIds, errorIds))
					.into(imageView);
			} else {
				imageView.setImageResource(defaultIds);
			}
		} else if (BINARY.equals(type)) {
			if (null != mContext) {
				Glide.with(mContext)
					.load(null == stringToByte(url) ? "" : stringToByte(url))
					.apply(initGlideCircleOptions(defaultIds, errorIds))
					.apply(signatureOf(new ObjectKey(url)))
					.into(imageView);
			} else {
				imageView.setImageResource(defaultIds);
			}
		}
	}

	public static final void onImageViewCircleHead(Context mContext, ImageView imageView,
		String url) {
		onImageViewCircleHead(mContext, imageView, url, URL, R.mipmap.ic_default_avatar,
			R.mipmap.ic_default_avatar);
	}

	public static final void onImageViewCircleHead(Context mContext, ImageView imageView,
		String url, String type) {
		if (TextUtils.isEmpty(type)) {
			onImageViewCircleHead(mContext, imageView, url, URL, R.mipmap.ic_default_avatar,
				R.mipmap.ic_default_avatar);
		} else {
			onImageViewCircleHead(mContext, imageView, url, type, R.mipmap.ic_default_avatar,
				R.mipmap.ic_default_avatar);
		}
	}

	public static final void onImageViewCircleHead(Context mContext, ImageView imageView,
		String url, int defaultIds, int errorIds) {
		onImageViewCircleHead(mContext, imageView, url, URL, defaultIds, defaultIds);
	}

	public static final void onImageViewCircleHead(Context mContext, ImageView imageView,
		String url, String type, int defaultIds, int errorIds) {
		if (URL.equals(type)) {
			if (null != mContext) {
				Glide.with(mContext)
					.load(url)
					.apply(initGlideCircleHeadOptions(defaultIds, errorIds))
					.into(imageView);
			} else {
				imageView.setImageResource(defaultIds);
			}
		} else if (BINARY.equals(type)) {
			if (null != mContext) {
				Glide.with(mContext)
					.load(null == stringToByte(url) ? "" : stringToByte(url))
					.apply(initGlideCircleHeadOptions(defaultIds, errorIds))
					.apply(signatureOf(new ObjectKey(url)))
					.into(imageView);
			} else {
				imageView.setImageResource(defaultIds);
			}
		}
	}

	public static final void loadCricleHeadImage(Context mContext, ImageView imageView,
		String url) {
		Glide.with(mContext)
			.load(url)
			.apply(
				initGlideCircleHeadOptions(R.mipmap.ic_default_avatar, R.mipmap.ic_default_avatar))
			.apply(signatureOf(new ObjectKey(url)))
			.into(imageView);
	}

	/**
	 * String 转 二进制
	 */
	public static byte[] stringToByte(String outString) {
		if (TextUtils.isEmpty(outString)) {
			return null;
		}
		byte[] bitmapArray;
		try {
			bitmapArray = Base64.decode(outString, Base64.DEFAULT);
		} catch (Exception e) {
			bitmapArray = new byte[] {};
		}

		return bitmapArray;
	}
}
