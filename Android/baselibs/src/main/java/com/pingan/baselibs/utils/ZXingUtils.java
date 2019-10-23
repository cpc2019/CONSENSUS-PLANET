package com.pingan.baselibs.utils;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ZXingUtils {

	public static Bitmap createBitmap(String str) {
		Bitmap bitmap = null;
		BitMatrix result = null;
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		try {
			result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 400, 400);
			BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
			bitmap = barcodeEncoder.createBitmap(result);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException iae) {
			return null;
		}
		return bitmap;
	}

	public static String decodeBarcodeRGB(Bitmap barcode) {
		int width = barcode.getWidth();
		int height = barcode.getHeight();
		int[] data = new int[width * height];
		barcode.getPixels(data, 0, width, 0, 0, width, height);
		RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		Result result = null;
		try {
			result = reader.decode(bitmap1);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (ChecksumException e) {
			e.printStackTrace();
		} catch (FormatException e) {
			e.printStackTrace();
		}finally {
		}
		//barcode.recycle();
		return result == null ? "" : result.getText();
	}
}
