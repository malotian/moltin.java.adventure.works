/**
 */
package com.moltin.adventure.works;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

	private static final String ALGO = "AES";
	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
	private static ObjectMapper mapper = new ObjectMapper();

	public static final String MOLTIN_AW_HOME = System.getProperty("moltin.aw.home");

	private static String bytesToHex(final byte[] hash) {
		return DatatypeConverter.printHexBinary(hash);
	}

	public static String decryptText(final byte[] byteCipherText, final SecretKey secretKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		final Cipher aesCipher = Cipher.getInstance(ALGO);
		aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
		final byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
		return new String(bytePlainText);
	}

	public static String decryptText(final String cipherText)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		final String[] tokens = cipherText.split("@@");
		final byte[] encodedKey = Base64.decodeBase64(tokens[1]);
		return decryptText(DatatypeConverter.parseHexBinary(tokens[0]), new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES"));
	}

	public static byte[] encryptText(final String plainText, final SecretKey secretKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		final Cipher aesCipher = Cipher.getInstance(ALGO);
		aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return aesCipher.doFinal(plainText.getBytes());
	}

	public static SecretKey getSecretEncryptionKey() throws NoSuchAlgorithmException {
		final KeyGenerator generator = KeyGenerator.getInstance(ALGO);
		generator.init(128);
		return generator.generateKey();
	}

	public static String json(final Object input) {
		try {
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			mapper.configure(Feature.IGNORE_UNDEFINED, true);
			return mapper.writeValueAsString(input);
		} catch (final IOException e) {
			LOGGER.trace("error converting: {}", input);
		}
		return null;
	}

	public static String json(final Object input, final boolean formatted) {
		try {
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			mapper.configure(Feature.IGNORE_UNDEFINED, true);
			if (formatted) {
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input);
			}
			return mapper.writeValueAsString(input);
		} catch (final IOException e) {
			LOGGER.trace("error converting-x: {}", input);
		}
		return null;
	}

	public static void main(final String[] args) throws Exception {

		final Options options = new Options();
		final Option option = new Option("pt", "plain.text", true, "");
		option.setRequired(true);
		option.setArgName("text-to-be-encrypted");
		options.addOption(option);

		String plainText = null;
		try {

			final CommandLineParser parser = new DefaultParser();
			final CommandLine line = parser.parse(options, args);
			plainText = line.getOptionValue("pt");
		} catch (final Exception e) {
			LOGGER.error("exception: {}", ExceptionUtils.getStackTrace(e));
			final HelpFormatter formatter = new HelpFormatter();
			formatter.setWidth(150);
			formatter.printHelp(Util.class.getCanonicalName(), options);
			return;
		}

		final SecretKey secretKey = getSecretEncryptionKey();
		final String encodedKey = Base64.encodeBase64String(secretKey.getEncoded());
		final byte[] cipherText = encryptText(plainText, secretKey);
		final String encryptedText = bytesToHex(cipherText) + "@@" + Base64.encodeBase64String(secretKey.getEncoded());
		final String decryptedText = decryptText(encryptedText);
		LOGGER.debug("plain.text: {}", plainText);
		LOGGER.debug("generated.key(encoded): {}", encodedKey);
		LOGGER.info("encrypted.text: {}", encryptedText);
		LOGGER.debug("decrypted.text: {}", decryptedText);
	}

	public static JsonNode readJson(final String input) {
		try {
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			mapper.configure(Feature.IGNORE_UNDEFINED, true);
			return mapper.readValue(input, JsonNode.class);
		} catch (final IOException e) {
			LOGGER.trace("error reading jason: {}", input);
		}
		return null;
	}

}
