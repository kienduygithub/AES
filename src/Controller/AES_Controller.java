package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Enumeration;

import javax.swing.AbstractButton;

import Model.AES_Program;
import View.AES_Views;

public class AES_Controller implements ActionListener {
	private AES_Views viewAES;
	private AES_Program aes;
	public int[][] encryptData;
	public int[][] decryptedData;
	public String encryptString;
	public String decryptString;

	public AES_Controller(AES_Views view) {
		this.viewAES = view;
		this.encryptData = new int[4][4];
		this.encryptString = "";
		this.decryptedData = new int[4][4];
		this.decryptString = "";
	}

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		this.aes = new AES_Program();
		Enumeration<AbstractButton> btnOptions = this.viewAES.btnGroup.getElements();
		if (command.equals("Mã hóa")) {
			while (btnOptions.hasMoreElements()) {
				AbstractButton b = btnOptions.nextElement();
				String option = b.getText();

				String result = "";

				if (b.isSelected()) {
					Instant time1 = Instant.now();

//					String plainText = this.aes.plainTextAES;
					String plainText = this.viewAES.jTextField_BanRoEn.getText();
					this.viewAES.jTextField_BanRoEn.setText(plainText);
//					byte[] plainByte = plainText.getBytes(StandardCharsets.UTF_8);
//					int[][] plainInput = this.initEmptyEncrypt(plainByte);

					if (option.equals("128 bit")) {
						for (int i = 0; i < plainText.length(); i += 16) {
							String partText = plainText.substring(i, Math.min(i + 16, plainText.length()));
							byte[] plainByte = partText.getBytes(StandardCharsets.UTF_8);
							int[][] plainInput = this.initEmptyEncrypt(plainByte);
							int[][] encryptedData = new int[4][4];
							aes.encrypt128bit(plainInput, encryptedData);
							StringBuilder encryptedHex = new StringBuilder();
							for (int j = 0; j < 4; j++) {
								for (int k = 0; k < 4; k++) {
									encryptedHex.append(String.format("%02x", encryptedData[k][j]));
								}
							}
							result += encryptedHex.toString();
						}
//						aes.encrypt128bit(plainInput, this.encryptData);
//						this.setEncryptString(this.convertIntToString(this.encryptData));
						this.setEncryptString(result);
						this.viewAES.jTextField_BanMaEn.setText(this.getEncryptString());
					} else if (option.equals("192 bit")) {
						for (int i = 0; i < plainText.length(); i += 16) {
							String partText = plainText.substring(i, Math.min(i + 16, plainText.length()));
							byte[] plainByte = partText.getBytes(StandardCharsets.UTF_8);
							int[][] plainInput = this.initEmptyEncrypt(plainByte);
							int[][] encryptedData = new int[4][4];
							aes.encrypt192bit(plainInput, encryptedData);
							StringBuilder encryptedHex = new StringBuilder();
							for (int j = 0; j < 4; j++) {
								for (int k = 0; k < 4; k++) {
									encryptedHex.append(String.format("%02x", encryptedData[k][j]));
								}
							}
							result += encryptedHex.toString();
						}
						this.setEncryptString(result);
						this.viewAES.jTextField_BanMaEn.setText(this.getEncryptString());
					} else if (option.equals("256 bit")) {
						for (int i = 0; i < plainText.length(); i += 16) {
							String partText = plainText.substring(i, Math.min(i + 16, plainText.length()));
							byte[] plainByte = partText.getBytes(StandardCharsets.UTF_8);
							int[][] plainInput = this.initEmptyEncrypt(plainByte);
							int[][] encryptedData = new int[4][4];
							aes.encrypt256bit(plainInput, encryptedData);
							StringBuilder encryptedHex = new StringBuilder();
							for (int j = 0; j < 4; j++) {
								for (int k = 0; k < 4; k++) {
									encryptedHex.append(String.format("%02x", encryptedData[k][j]));
								}
							}
							result += encryptedHex.toString();
						}
						this.setEncryptString(result);
						this.viewAES.jTextField_BanMaEn.setText(this.getEncryptString());
					}
					Instant time2 = Instant.now();
					Duration duration = Duration.between(time1, time2);
					this.viewAES.lbl_TimeEn.setText(duration.toMillis() / 1000.0 + "");
				}
			}
		} else if (command.equals("Giải mã")) {
			while (btnOptions.hasMoreElements()) {
				AbstractButton b = btnOptions.nextElement();
				String option = b.getText();

				this.decryptedData = new int[4][4];
				String decryptString = "";

				if (b.isSelected()) {
					Instant time1 = Instant.now();
					String encryptedString = this.getEncryptString();
					this.viewAES.jTextField_BanMaDe.setText(encryptedString);
					if (option.equals("128 bit")) {
						for (int i = 0; i < encryptedString.length(); i += 32) {
							String partDecrypt = encryptedString.substring(i,
									Math.min(i + 32, encryptedString.length()));

							// Chuyển đổi chuỗi hex đã chia thành mảng các byte
							byte[] byteData = new byte[16];
							for (int j = 0; j < 16; j++) {
								byteData[j] = (byte) ((Character.digit(partDecrypt.charAt(j * 2), 16) << 4)
										+ Character.digit(partDecrypt.charAt(j * 2 + 1), 16));
							}
							// Chuyển mảng byte thành ma trận intData
							int[][] intData = new int[4][4];
							int index = 0;
							for (int j = 0; j < 4; j++) {
								for (int k = 0; k < 4; k++) {
									intData[k][j] = (byteData[index++] & 0xFF);
								}
							}

							// Giải mã từng phần và hiển thị kết quả
							int[][] decryptedData = new int[4][4];
							aes.decrypt128bit(intData, decryptedData);

							// Chuyển ma trận intData thành chuỗi và hiển thị kết quả
							String decryptedString = convertDecryptedDataToString(decryptedData);

							decryptString += decryptedString.toString();
						}
						this.viewAES.jTextField_BanRoDe.setText(decryptString.trim());
//						int[][] encryptedData = this.encryptData;
//						aes.decrypt128bit(encryptedData, this.decryptedData);
//						String decryptedString = this.convertDecryptedDataToString(this.decryptedData);
//						this.viewAES.jTextField_BanRoDe.setText(decryptedString);
					} else if (option.equals("192 bit")) {
						for (int i = 0; i < encryptedString.length(); i += 32) {
							String partDecrypt = encryptedString.substring(i,
									Math.min(i + 32, encryptedString.length()));

							// Chuyển đổi chuỗi hex đã chia thành mảng các byte
							byte[] byteData = new byte[16];
							for (int j = 0; j < 16; j++) {
								byteData[j] = (byte) ((Character.digit(partDecrypt.charAt(j * 2), 16) << 4)
										+ Character.digit(partDecrypt.charAt(j * 2 + 1), 16));
							}
							// Chuyển mảng byte thành ma trận intData
							int[][] intData = new int[4][4];
							int index = 0;
							for (int j = 0; j < 4; j++) {
								for (int k = 0; k < 4; k++) {
									intData[k][j] = (byteData[index++] & 0xFF);
								}
							}

							// Giải mã từng phần và hiển thị kết quả
							int[][] decryptedData = new int[4][4];
							aes.decrypt192bit(intData, decryptedData);

							// Chuyển ma trận intData thành chuỗi và hiển thị kết quả
							String decryptedString = convertDecryptedDataToString(decryptedData);

							decryptString += decryptedString.toString();
						}
						this.viewAES.jTextField_BanRoDe.setText(decryptString.trim());
					} else if (option.equals("256 bit")) {
						for (int i = 0; i < encryptedString.length(); i += 32) {
							String partDecrypt = encryptedString.substring(i,
									Math.min(i + 32, encryptedString.length()));

							// Chuyển đổi chuỗi hex đã chia thành mảng các byte
							byte[] byteData = new byte[16];
							for (int j = 0; j < 16; j++) {
								byteData[j] = (byte) ((Character.digit(partDecrypt.charAt(j * 2), 16) << 4)
										+ Character.digit(partDecrypt.charAt(j * 2 + 1), 16));
							}
							// Chuyển mảng byte thành ma trận intData
							int[][] intData = new int[4][4];
							int index = 0;
							for (int j = 0; j < 4; j++) {
								for (int k = 0; k < 4; k++) {
									intData[k][j] = (byteData[index++] & 0xFF);
								}
							}

							// Giải mã từng phần và hiển thị kết quả
							int[][] decryptedData = new int[4][4];
							aes.decrypt256bit(intData, decryptedData);

							// Chuyển ma trận intData thành chuỗi và hiển thị kết quả
							String decryptedString = convertDecryptedDataToString(decryptedData);

							decryptString += decryptedString.toString();
						}
						this.viewAES.jTextField_BanRoDe.setText(decryptString.trim());
					}
					Instant time2 = Instant.now();
					Duration duration = Duration.between(time1, time2);
					this.viewAES.lbl_TimeDe.setText(duration.toMillis() / 1000.0 + "");
				}
			}
		}
	}

	public String convertIntToString(int[][] text) {
		StringBuilder encryptedHex = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				encryptedHex.append(String.format("%02x", text[j][i]));
			}
		}
		return encryptedHex.toString();
	}

	public int[][] initEmptyEncrypt(byte[] data) {
		int[][] plainInput = new int[4][4];
		int index = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				plainInput[j][i] = (data[index++] & 0xFF);
				if (index >= data.length)
					break;
			}
			if (index >= data.length)
				break;
		}
		return plainInput;
	}

	public String convertDecryptedDataToString(int[][] decryptedData) {
		StringBuilder decryptedText = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				decryptedText.append((char) decryptedData[j][i]);
			}
		}
		return decryptedText.toString();
	}
}
