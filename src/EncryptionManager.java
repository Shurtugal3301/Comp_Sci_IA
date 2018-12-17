import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.*;

public class EncryptionManager {

	private static Cipher AesCipher;

	private static final String DIR_NAME = ".dat";
	private static final String KEY_STORE_NAME = DIR_NAME + "/" + "edf4nf67c2";
	private static final String SECRET_KEY_ALIAS = "dbn34hgh67b";
	private static final String KEY_STORE_TYPE = "PKCS12";
	private static final String CIPHER_TYPE = "AES";
	private static final String PASSWORD = "s06vdbg2w";

	static final String SAVE_FILE_NAME = DIR_NAME + "/" + "g0hr73bbg";
	static final String PASSWORD_FILE_NAME = DIR_NAME + "/" + "sa9fsd2qm";

    private static FileInputStream fis;
    private static FileOutputStream fos;

    private static ObjectOutputStream objOut;
    private static ObjectInputStream objIn;

	private static boolean KeyStoreLoaded;

	private static KeyStore ks;
	private static SecretKey secKey;

	/**
	 * Initializes the encryptor for use
	 */
	public static void InitCrypt() {

		try {

			ks = KeyStore.getInstance(KEY_STORE_TYPE);
			AesCipher = Cipher.getInstance(CIPHER_TYPE);

		} catch (KeyStoreException | NoSuchAlgorithmException | NoSuchPaddingException e) {

            //e.printStackTrace();
		}

		LoadKeyStore(PASSWORD);

		secKey = GetKey(SECRET_KEY_ALIAS, PASSWORD);

		SaveKeyStore(PASSWORD);

	}

	// Gets the secret key from the key store
	private static SecretKey GetKey(String alias, String pswd) {

		try {

			if (KeyStoreLoaded) {

				if (ks.containsAlias(alias)) {

					char[] password = pswd.toCharArray();

					KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password);

					KeyStore.SecretKeyEntry skEntry;

					try {

						skEntry = (KeyStore.SecretKeyEntry) ks.getEntry(SECRET_KEY_ALIAS, protParam);
						return skEntry.getSecretKey();

					} catch (NoSuchAlgorithmException | UnrecoverableEntryException e) {

                        //e.printStackTrace();

					}

				} else {

					StoreKey(SECRET_KEY_ALIAS, PASSWORD, GenerateNewKey());
					return GetKey(SECRET_KEY_ALIAS, PASSWORD);

				}

			}

		} catch (KeyStoreException e) {

            //e.printStackTrace();

		}

		return null;

	}

	// Stores the current secret key in the key store
	private static void StoreKey(String alias, String pswd, SecretKey secretKey) {

		try {

			if (KeyStoreLoaded && !ks.containsAlias(alias)) {

				char[] password = pswd.toCharArray();

				KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password);

				javax.crypto.SecretKey mySecretKey = secretKey;
				KeyStore.SecretKeyEntry sckEntry = new KeyStore.SecretKeyEntry(mySecretKey);

				try {

					ks.setEntry(alias, sckEntry, protParam);

				} catch (KeyStoreException e) {

                    //e.printStackTrace();

				}

			}

		} catch (KeyStoreException e) {

            //e.printStackTrace();

		}

	}

	// Loads the key store from its save location
	private static void LoadKeyStore(String pswd) {

		boolean successful;

		char[] password = pswd.toCharArray();

		java.io.FileInputStream fis = null;

		try {

			fis = new java.io.FileInputStream(KEY_STORE_NAME);
			ks.load(fis, password);

			successful = true;

		} catch (FileNotFoundException e) {

            //e.printStackTrace();

			System.out.println("File not found. Creating new KeyStore file.");

			try {

				ks.load(fis, password);

				successful = true;

			} catch (NoSuchAlgorithmException | CertificateException | IOException f) {

                //f.printStackTrace();

				successful = false;

			} finally {

                if (fis != null) {

					try {

						fis.close();

					} catch (IOException g) {

                        //g.printStackTrace();

					}

				}

			}

		} catch (NoSuchAlgorithmException | CertificateException | IOException e) {

            //e.printStackTrace();

			successful = false;

		} finally {

			if (fis != null) {

				try {

					fis.close();

				} catch (IOException e) {

                    //e.printStackTrace();

				}

			}

		}

		KeyStoreLoaded = successful;

	}

	// Saves the key store to its save location
	private static void SaveKeyStore(String pswd) {

		if (!KeyStoreLoaded)
			return;

		char[] password = pswd.toCharArray();

		try {

			if (!new File(DIR_NAME).exists()) {

				File dir = new File(DIR_NAME);
				dir.mkdir();
				Files.setAttribute(dir.toPath(), "dos:hidden", true);

			}

            fos = new FileOutputStream(KEY_STORE_NAME);
			ks.store(fos, password);

		} catch (Exception e) {

            //e.printStackTrace();

		} finally {

			if (fos != null) {

				try {

					fos.close();

				} catch (IOException e) {

                    //e.printStackTrace();

				}

			}

		}

	}

	// Creates a new secret key
	private static SecretKey GenerateNewKey() {

		if (KeyStoreLoaded) {

			try {

				KeyGenerator KeyGen = KeyGenerator.getInstance("AES");
				KeyGen.init(128);

				return KeyGen.generateKey();

			} catch (Exception e) {

                //e.printStackTrace();
				return null;

			}

		} else {

			return null;

		}

	}

	/**
	 * Encrypts the String passed to it
	 *
     * @param object The object to be encrypted
     * @param fileName File to save the encrypted message
	 * @return Whether or not the encryption was successful
	 */
    public static boolean Encrypt(Object object, String fileName) {

        try {

            File file = new File(fileName + "_Temp");
            fos = new FileOutputStream(file);
            objOut = new ObjectOutputStream(fos);
            objOut.writeObject(object);

            objOut.flush();
            objOut.close();
            fos.close();

            byte[] byteText = Files.readAllBytes(Paths.get(fileName + "_Temp"));
            AesCipher.init(Cipher.ENCRYPT_MODE, secKey);
			byte[] byteCipherText = AesCipher.doFinal(byteText);
			Files.write(Paths.get(fileName), byteCipherText);

            file.delete();

			System.out.println("ENCRYPTION successful!");
			return true;

		} catch (Exception e) {

			System.out.println("ENCRYPTION failed!");
            //e.printStackTrace();
			return false;

		}
	}

	/**
	 * Decrypts the message from the file name passed to it
	 *
     * @param fileName Name of the file to decrypt
     * @return Decrypted object
	 */
    public static Object Decrypt(String fileName) {

		try {

			byte[] cipherText = Files.readAllBytes(Paths.get(fileName));
			AesCipher.init(Cipher.DECRYPT_MODE, secKey);
			byte[] bytePlainText = AesCipher.doFinal(cipherText);

            Files.write(Paths.get(fileName + "_Temp"), bytePlainText, StandardOpenOption.CREATE);

            File file = new File(fileName + "_Temp");
            fis = new FileInputStream(file);
            objIn = new ObjectInputStream(fis);

            Object obj = objIn.readObject();

            objIn.close();
            fis.close();

            file.delete();

			System.out.println("DECRYPTION successful!");
            return obj;

		} catch (Exception e) {

			System.out.println("DECRYPTION failed!");
            //e.printStackTrace();
            return null;

		}

	}

}