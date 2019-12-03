package com.example.t_otp.helpers;

import android.util.Base64;
import android.util.Log;

import com.example.t_otp.BuildConfig;
import com.example.t_otp.MainActivity;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CEncryption {
    private static final String TAG = "CEncryption";

    public static String encrypt(String plainText, boolean isPrivate){
        final Integer keyLife = BuildConfig.OTP_KEY_LIFE;
        final Integer ctime = ((int)(System.currentTimeMillis() / 1000));
        final Integer counter = ctime / keyLife;
        String cKey;

        if(isPrivate){
            cKey = MainActivity.OTP_PRIVATE_KEY;
        }else{
            cKey = BuildConfig.OTP_MAIN_KEY;
        }

        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(new SecretKeySpec(hexStringToByteArray(cKey), "HmacMD5"));
            mac.update(counter.toString().getBytes());

            byte [] key = mac.doFinal();

            IvParameterSpec iv = new IvParameterSpec(hexStringToByteArray(BuildConfig.OTP_INIT_VECTOR));
            SecretKeySpec skeySpec = new SecretKeySpec(key, BuildConfig.OTP_ALGORITHM);

            Cipher cipher = Cipher.getInstance(BuildConfig.OTP_CHIPER);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(plainText.getBytes());

            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static String decrypt(String cipher, boolean isPrivate){
        final Integer keyLife = BuildConfig.OTP_KEY_LIFE;
        Integer timeForce = BuildConfig.OTP_KEY_FORCE;
        final Integer factor = timeForce / 2;
        final Integer ctime = ((int)(System.currentTimeMillis() / 1000)) + factor;
        Integer counter = (ctime / keyLife) + factor;

        String cKey;
        String clearText;

        if(isPrivate){
            cKey = MainActivity.OTP_PRIVATE_KEY;
        }else{
            cKey = BuildConfig.OTP_MAIN_KEY;
        }

        while (timeForce-- > 0){
            clearText = tryDecrypt(cipher, cKey, counter);
            if(clearText != null){
                Log.d(TAG, "DECRYPT SUCCESS: " + clearText);
                return clearText;
            }

            counter--;
        }

        Log.d(TAG, "decrypt: FAILED DECRYPT");

        return null;
    }

    private static String tryDecrypt(String cipher, String tkey, final Integer counter){
        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(new SecretKeySpec(hexStringToByteArray(tkey), "HmacMD5"));

            mac.update(counter.toString().getBytes());
            byte [] key = mac.doFinal();
            IvParameterSpec iv = new IvParameterSpec(hexStringToByteArray(BuildConfig.OTP_INIT_VECTOR));
            SecretKeySpec skeySpec = new SecretKeySpec(key, BuildConfig.OTP_ALGORITHM);

            Cipher mcipher = Cipher.getInstance(BuildConfig.OTP_CHIPER);
            mcipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            return new String(mcipher.doFinal(Base64.decode(cipher, Base64.DEFAULT)));
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException | IllegalBlockSizeException ex){
            return null;
        }

        return null;
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
