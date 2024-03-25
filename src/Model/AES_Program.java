package Model;

public class AES_Program {
	public static int[] w;
	public static int Nb = 4;
	public static int Nr = 0;
	private static int actual;
	public static int[] key_128bit = { 0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x97, 0x46, 0x53,
			0x7f, 0x3c, 0xa5 };
	public static int[] key_192bit = { 0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x97, 0x46, 0x53,
			0x7f, 0x3c, 0xa5, 0x76, 0x55, 0x45, 0x88, 0x32, 0x12, 0x7f, 0x42 };
	public static int[] key_256bit = { 0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x97, 0x46, 0x53,
			0x7f, 0x3c, 0xa5, 0x76, 0x55, 0x45, 0x88, 0x32, 0x12, 0x7f, 0x42, 0x54, 0x67, 0x23, 0x11, 0x34, 0x78, 0x90,
			0xab };

	public static String plainTextAES = "Hello, Everyone! I'm Duy!";

	// ================== CÁC HÀM MỞ RỘNG KHÓA KEY ================== \\
	public static int RotWord(int word) {
		return (word << 8) | ((word & 0xFF000000) >>> 24);
	}

	public static int SubWord(int word) {
		int subWord = 0;
		for (int i = 24; i >= 0; i -= 8) {
			int in = word << i >>> 24;
			subWord |= SBoxs.SBox[in] << (24 - i);
		}
		return subWord;
	}

	public static int invSubWord(int word) {
		int subWord = 0;
		for (int i = 0; i < 4; i++) {
			int in = (word >>> (8 * (3 - i))) & 0xFF;
			subWord |= SBoxs.InvSBox[in] << (8 * (3 - i));
		}
		return subWord;
	}

	public static int[] KeyExpansion(int[] key) {
		int Nk = key.length / 4; // Số lượng từ trong key
		Nr = Nk + 6; // Số lượng vòng lặp rounds
		int[] expandedKey = new int[(Nr + 1) * 4]; // Mảng lưu trữ các từ mở rộng
		w = new int[(Nr + 1) * Nb];
		int temp, i = 0;
		while (i < Nk) {
			w[i] = 0x00000000;
			w[i] |= key[4 * i] << 24;
			w[i] |= key[4 * i + 1] << 16;
			w[i] |= key[4 * i + 2] << 8;
			w[i] |= key[4 * i + 3];
			i++;
		}

		i = Nk;
		while (i < Nb * (Nr + 1)) { // 128bit: Nb = 4, Nr = 10
			temp = w[i - 1];
			if (i % Nk == 0) {
				temp = SubWord(RotWord(temp)) ^ (SBoxs.rCon[i / Nk] << 24);
			} else if (Nk > 6 && (i % Nk == 4)) {
				temp = SubWord(temp);
			} else {

			}
			w[i] = w[i - Nk] ^ temp;
			i++;
		}
		return w;
	}

	// =========================== Done ========================= \\
	// ================== CÁC HÀM THỰC HIỆN AES ================== \\
	public int[][] SubBytes(int[][] state) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < Nb; j++) {
				state[i][j] = SubWord(state[i][j]) & 0xFF;
			}
		}
		return state;
	}

	public int[][] invSubBytes(int[][] state) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < Nb; j++) {
				state[i][j] = invSubWord(state[i][j]) & 0xFF;
			}
		}
		return state;
	}

	public int[][] ShiftRows(int[][] state) {
		int temp1, temp2, temp3, i;
		temp1 = state[1][0];
		for (i = 0; i < Nb - 1; i++) {
			state[1][i] = state[1][(i + 1) % Nb];
		}
		state[1][Nb - 1] = temp1;

		temp1 = state[2][0];
		temp2 = state[2][1];
		for (i = 0; i < Nb - 2; i++) {
			state[2][i] = state[2][(i + 2) % Nb];
		}
		state[2][Nb - 2] = temp1;
		state[2][Nb - 1] = temp2;

		temp1 = state[3][0];
		temp2 = state[3][1];
		temp3 = state[3][2];
		for (i = 0; i < Nb - 3; i++) {
			state[3][i] = state[3][(i + 3) % Nb];
		}
		state[3][Nb - 3] = temp1;
		state[3][Nb - 2] = temp2;
		state[3][Nb - 1] = temp3;
		return state;
	}

	public int[][] invShiftRows(int[][] state) {
		int temp1, temp2, temp3, i;
		// row 1;
		temp1 = state[1][Nb - 1];
		for (i = Nb - 1; i > 0; i--) {
			state[1][i] = state[1][(i - 1) % Nb];
		}
		state[1][0] = temp1;
		// row 2
		temp1 = state[2][Nb - 1];
		temp2 = state[2][Nb - 2];
		for (i = Nb - 1; i > 1; i--) {
			state[2][i] = state[2][(i - 2) % Nb];
		}
		state[2][1] = temp1;
		state[2][0] = temp2;
		// row 3
		temp1 = state[3][Nb - 3];
		temp2 = state[3][Nb - 2];
		temp3 = state[3][Nb - 1];
		for (i = Nb - 1; i > 2; i--) {
			state[3][i] = state[3][(i - 3) % Nb];
		}
		state[3][0] = temp1;
		state[3][1] = temp2;
		state[3][2] = temp3;
		return state;
	}

	public int xtime(int b) {
		return ((b << 1) ^ ((b & 0x80) != 0 ? 0x1b : 0x00)) & 0xFF;
	}

	public int mult(int a, int b) {
		int sum = 0;
		while (a != 0) {
			if ((a & 1) != 0) {
				sum = sum ^ b;
			}
			b = xtime(b);
			a = a >>> 1;
		}
		return sum;
	}

	public int[][] MixColumns(int[][] state) {
		int temp0, temp1, temp2, temp3;
		for (int c = 0; c < Nb; c++) {
			temp0 = mult(0x02, state[0][c]) ^ mult(0x03, state[1][c]) ^ state[2][c] ^ state[3][c];
			temp1 = state[0][c] ^ mult(0x02, state[1][c]) ^ mult(0x03, state[2][c]) ^ state[3][c];
			temp2 = state[0][c] ^ state[1][c] ^ mult(0x02, state[2][c]) ^ mult(0x03, state[3][c]);
			temp3 = mult(0x03, state[0][c]) ^ state[1][c] ^ state[2][c] ^ mult(0x02, state[3][c]);
			state[0][c] = temp0;
			state[1][c] = temp1;
			state[2][c] = temp2;
			state[3][c] = temp3;
		}
		return state;
	}

	public int[][] invMixColumnas(int[][] state) {
		int temp0, temp1, temp2, temp3;
		for (int c = 0; c < Nb; c++) {
			temp0 = mult(0x0e, state[0][c]) ^ mult(0x0b, state[1][c]) ^ mult(0x0d, state[2][c])
					^ mult(0x09, state[3][c]);
			temp1 = mult(0x09, state[0][c]) ^ mult(0x0e, state[1][c]) ^ mult(0x0b, state[2][c])
					^ mult(0x0d, state[3][c]);
			temp2 = mult(0x0d, state[0][c]) ^ mult(0x09, state[1][c]) ^ mult(0x0e, state[2][c])
					^ mult(0x0b, state[3][c]);
			temp3 = mult(0x0b, state[0][c]) ^ mult(0x0d, state[1][c]) ^ mult(0x09, state[2][c])
					^ mult(0x0e, state[3][c]);
			state[0][c] = temp0;
			state[1][c] = temp1;
			state[2][c] = temp2;
			state[3][c] = temp3;
		}
		return state;
	}

	public int[][] AddRoundKey(int[][] state, int turn) {
		for (int c = 0; c < Nb; c++) {
			for (int r = 0; r < 4; r++) {
				state[r][c] = state[r][c] ^ ((w[turn * Nb + c] << (r * 8)) >>> 24);
			}
		}
		return state;
	}

	// =========================== Done ========================= \\
	// ========================== Program ======================== \\
	public void encrypt128bit(int[][] plainText, int[][] encryptText) {
		int[] expandedKey = KeyExpansion(key_128bit);
		for (int i = 0; i < plainText.length; i++) {
			System.arraycopy(plainText[i], 0, encryptText[i], 0, plainText.length);
		}
		actual = 0;
		AddRoundKey(encryptText, actual);
		for (actual = 1; actual < Nr; actual++) {
			SubBytes(encryptText);
			ShiftRows(encryptText);
			MixColumns(encryptText);
			AddRoundKey(encryptText, actual);
		}
		SubBytes(encryptText);
		ShiftRows(encryptText);
		AddRoundKey(encryptText, actual);
	}

	public void decrypt128bit(int[][] encryptedText, int[][] plainText) {
		int[] expandedKey = KeyExpansion(key_128bit);
		for (int i = 0; i < encryptedText.length; i++) {
			System.arraycopy(encryptedText[i], 0, plainText[i], 0, encryptedText.length);
		}
		actual = Nr;
		AddRoundKey(plainText, actual);
		for (actual = Nr - 1; actual > 0; actual--) {
			invShiftRows(plainText);
			invSubBytes(plainText);
			AddRoundKey(plainText, actual);
			invMixColumnas(plainText);
		}
		invShiftRows(plainText);
		invSubBytes(plainText);
		AddRoundKey(plainText, actual);
	}

	public void encrypt192bit(int[][] plainText, int[][] encryptText) {
		int[] expandedKey = KeyExpansion(key_192bit);
		for (int i = 0; i < plainText.length; i++) {
			System.arraycopy(plainText[i], 0, encryptText[i], 0, plainText.length);
		}
		actual = 0;
		AddRoundKey(encryptText, actual);
		for (actual = 1; actual < Nr; actual++) {
			SubBytes(encryptText);
			ShiftRows(encryptText);
			MixColumns(encryptText);
			AddRoundKey(encryptText, actual);
		}
		SubBytes(encryptText);
		ShiftRows(encryptText);
		AddRoundKey(encryptText, actual);
	}

	public void decrypt192bit(int[][] encryptedText, int[][] plainText) {
		int[] expandedKey = KeyExpansion(key_192bit);
		for (int i = 0; i < encryptedText.length; i++) {
			System.arraycopy(encryptedText[i], 0, plainText[i], 0, encryptedText.length);
		}
		actual = Nr;
		AddRoundKey(plainText, actual);
		for (actual = Nr - 1; actual > 0; actual--) {
			invShiftRows(plainText);
			invSubBytes(plainText);
			AddRoundKey(plainText, actual);
			invMixColumnas(plainText);
		}
		invShiftRows(plainText);
		invSubBytes(plainText);
		AddRoundKey(plainText, actual);
	}

	public void encrypt256bit(int[][] plainText, int[][] encryptText) {
		int[] expandedKey = KeyExpansion(key_256bit);
		for (int i = 0; i < plainText.length; i++) {
			System.arraycopy(plainText[i], 0, encryptText[i], 0, plainText.length);
		}
		actual = 0;
		AddRoundKey(encryptText, actual);
		for (actual = 1; actual < Nr; actual++) {
			SubBytes(encryptText);
			ShiftRows(encryptText);
			MixColumns(encryptText);
			AddRoundKey(encryptText, actual);
		}
		SubBytes(encryptText);
		ShiftRows(encryptText);
		AddRoundKey(encryptText, actual);
	}

	public void decrypt256bit(int[][] encryptedText, int[][] plainText) {
		int[] expandedKey = KeyExpansion(key_256bit);
		for (int i = 0; i < encryptedText.length; i++) {
			System.arraycopy(encryptedText[i], 0, plainText[i], 0, encryptedText.length);
		}
		actual = Nr;
		AddRoundKey(plainText, actual);
		for (actual = Nr - 1; actual > 0; actual--) {
			invShiftRows(plainText);
			invSubBytes(plainText);
			AddRoundKey(plainText, actual);
			invMixColumnas(plainText);
		}
		invShiftRows(plainText);
		invSubBytes(plainText);
		AddRoundKey(plainText, actual);
	}
}
