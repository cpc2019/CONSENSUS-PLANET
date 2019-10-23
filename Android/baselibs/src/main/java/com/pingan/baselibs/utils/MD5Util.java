package com.pingan.baselibs.utils;

/**
 * Function: ${todo} ADD FUNCTION. <br/>
 * date: ${date} ${time} <br/>
 *
 * @author ${user}
 * @version ${enclosing_type}${tags}
 */

import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MS5
 */
public class MD5Util {
    private static final String TAG = MD5Util.class.getSimpleName();
    private static final int STREAM_BUFFER_LENGTH = 1024;

    /**
     * Gets digest.
     *
     * @param algorithm the algorithm
     * @return the digest
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static MessageDigest getDigest(final String algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm);
    }

    /**
     * Md 5 byte [ ].
     *
     * @param txt the txt
     * @return the byte [ ]
     */
    public static byte[] md5(String txt) {
        return md5(txt.getBytes());
    }

    /**
     * Md 5 str string.
     *
     * @param txt the txt
     * @return the string 16进制字符串
     */
    public static String md5Str(String txt) {
        if(TextUtils.isEmpty(txt)){
            return "";
        }
        byte[] buf = md5(txt);
        if (buf == null) {
            return "";
        }
        StringBuilder hex = new StringBuilder(buf.length * 2);
        for (byte b : buf) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     *
     * @param txt
     * @return  16进制字符串
     */
    public static String md5Str2(String txt) {
        byte[] buf = md5(txt);
        if (buf == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (int offset = 0; offset < buf.length; offset ++) {
            i = buf[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }

    /**
     * 对二进制进行md5处理
     *
     * @param content
     *
     * @return
     */
    public static String md5Str(byte[] content) {
        byte[] buf = md5(content);
        if (buf == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (int offset = 0; offset < buf.length; offset ++) {
            i = buf[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }

    /**
     * Md 5 byte [ ].
     *
     * @param bytes the bytes
     * @return the byte [ ]
     */
    public static byte[] md5(byte[] bytes) {
        try {
            MessageDigest digest = getDigest("MD5");
            digest.update(bytes);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Md 5 byte [ ].
     *
     * @param is the is
     * @return the byte [ ]
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws IOException              the io exception
     */
    public static byte[] md5(InputStream is) throws NoSuchAlgorithmException, IOException {
        return updateDigest(getDigest("MD5"), is).digest();
    }

    /**
     * Update digest message digest.
     *
     * @param digest the digest
     * @param data   the data
     * @return the message digest
     * @throws IOException the io exception
     */
    public static MessageDigest updateDigest(final MessageDigest digest, final InputStream data) throws
            IOException {
        final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }

        return digest;
    }

    /**
     * Md 5 hex 8 string.
     *
     * @param is the is
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws IOException              the io exception
     */
    public static String md5Hex8(InputStream is)
            throws NoSuchAlgorithmException, IOException {
        return byteArrayToHex(md5(is));
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };

        char[] resultCharArray = new char[byteArray.length / 2];

        int index = 0;
        for (int i = 0; i < byteArray.length; i += 4) {
            byte b = byteArray[i];
            resultCharArray[(index++)] = hexDigits[(b >>> 4 & 0xF)];
            resultCharArray[(index++)] = hexDigits[(b & 0xF)];
        }

        return new String(resultCharArray);
    }
}
