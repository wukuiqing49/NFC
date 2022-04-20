package com.wkq.nfc;

import java.math.BigInteger;

/**
 * @author wkq
 * @date 2022年04月20日 14:56
 * @des
 */

public class NFCUtil {

    public static String bytesToTenNum(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[1] = Character.toUpperCase(Character.forDigit(
                    (src[i] >>> 4) & 0x0F, 16));
            buffer[0] = Character.toUpperCase(Character.forDigit(src[i] & 0x0F,
                    16));
            stringBuilder.append(buffer);
        }
        stringBuilder.reverse();
        BigInteger bigi = new BigInteger(stringBuilder.toString(), 16);
        return bigi.toString();
    }

    public static String bytesToHex(byte[] src){
        StringBuffer sb = new StringBuffer();
        if (src == null || src.length <= 0) {
            return null;
        }
        String sTemp;
        for (int i = 0; i < src.length; i++) {
            sTemp = Integer.toHexString(0xFF & src[i]);
            if (sTemp.length() < 2){
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

}
