package by.epam.hotel.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Encoder {
	private static final Logger LOG = LogManager.getLogger(Encoder.class);
	public static String encodePassword(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff), 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			LOG.fatal("No Provider supports a MessageDigestSpi implementation for the specified algorithm: {}", e);
			throw new RuntimeException("No Provider supports a MessageDigestSpi implementation for the specified algorithm: {}", e);
		}

	}
	
	public static String generateEmailKey(String email) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			String salt = String.valueOf(new Random().nextInt(500)+1);
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(email.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff), 16));
			}
			return sb.toString().substring(0, 10);
		} catch (NoSuchAlgorithmException e) {
			LOG.fatal("No Provider supports a MessageDigestSpi implementation for the specified algorithm: {}", e);
			throw new RuntimeException("No Provider supports a MessageDigestSpi implementation for the specified algorithm: {}", e);
		}

	}
}
