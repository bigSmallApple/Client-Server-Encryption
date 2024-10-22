
package serverclientencryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encryptor {
    private static final String ALGORITHM = "AES";
    private static final byte[] keyBytes = "1234567890123456".getBytes(); // Example: Use a 16-byte key

    // Method to encrypt a message
    public static String encrypt(String plainText) throws Exception {
        SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes); // Convert to base64 to make it text-safe
    }

    // Method to decrypt a message
    public static String decrypt(String encryptedText) throws Exception {
        SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}
