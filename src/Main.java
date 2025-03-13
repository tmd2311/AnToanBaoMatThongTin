import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static String caesar(String text, int shift) {
        StringBuilder cipherText = new StringBuilder();
        for (char c : text.toCharArray()) {
            char encryptedChar = (char) (((c - 'A' + shift) % 26) + 'A');
            cipherText.append(encryptedChar);
        }
        return cipherText.toString();
    }

    public static String caesarDecrypt(String text, int shift) {
        return caesar(text, 26 - shift);
    }

    public static String repeatKey(String text, String key) {
        StringBuilder extendedKey = new StringBuilder();
        int textLength = text.length();
        int keyLength = key.length();
        for (int i = 0; i < textLength; i++) {
            extendedKey.append(key.charAt(i % keyLength));
        }
        return extendedKey.toString();
    }

    public static String autoKey(String text, String key) {
        StringBuilder extendedKey = new StringBuilder(key);
        for (int i = key.length(); i < text.length(); i++) {
            extendedKey.append(text.charAt(i - key.length()));
        }
        return extendedKey.toString();
    }

    public static String vigenereEncrypt(String text, String key, boolean isAutoKey) {
        String extendedKey = isAutoKey ? autoKey(text, key) : repeatKey(text, key);
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char encryptedChar = (char) (((text.charAt(i) - 'A' + extendedKey.charAt(i) - 'A') % 26) + 'A');
            cipherText.append(encryptedChar);
        }
        return cipherText.toString();
    }

    public static String vigenereDecrypt(String text, String key, boolean isAutoKey) {
        String extendedKey = isAutoKey ? autoKey(text, key) : repeatKey(text, key);
        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char decryptedChar = (char) (((text.charAt(i) - extendedKey.charAt(i) + 26) % 26) + 'A');
            plainText.append(decryptedChar);
        }
        return plainText.toString();
    }

    public static String railFenceCipher(String message, int key) {
        if (key <= 1) return message;
        StringBuilder[] rails = new StringBuilder[key];
        for (int i = 0; i < key; i++) rails[i] = new StringBuilder();
        int currentRail = 0, direction = 1;
        for (char c : message.toCharArray()) {
            rails[currentRail].append(c);
            if (currentRail == 0) direction = 1;
            else if (currentRail == key - 1) direction = -1;
            currentRail += direction;
        }
        StringBuilder ciphertext = new StringBuilder();
        for (StringBuilder rail : rails) {
            ciphertext.append(rail);
        }
        return ciphertext.toString();
    }

    public static String railFenceDecrypt(String cipherText, int key) {
        if (key <= 1) return cipherText;
        char[] plainText = new char[cipherText.length()];
        int[] positions = new int[cipherText.length()];
        int currentRail = 0, direction = 1;

        for (int i = 0; i < cipherText.length(); i++) {
            positions[i] = currentRail;
            if (currentRail == 0) direction = 1;
            else if (currentRail == key - 1) direction = -1;
            currentRail += direction;
        }

        int index = 0;
        for (int rail = 0; rail < key; rail++) {
            for (int i = 0; i < cipherText.length(); i++) {
                if (positions[i] == rail) {
                    plainText[i] = cipherText.charAt(index++);
                }
            }
        }
        return new String(plainText);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Chon che do:");
            System.out.println("1. Ma hoa");
            System.out.println("2. Giai ma");
            System.out.print("Nhap lua chon: ");
            int mode = scanner.nextInt();

            System.out.println("Chon phuong phap:");
            System.out.println("1. Caesar");
            System.out.println("2. Vigenere co ban");
            System.out.println("3. Vigenere Auto-key");
            System.out.println("4. Thay the don");
            System.out.println("5. Rail Fence");
            System.out.print("Nhap lua chon: ");
            int method = scanner.nextInt();
            System.out.print("Nhap van ban: ");
            String text = scanner.next().toUpperCase();
            String result = "";

            boolean isEncrypt = (mode == 1);

            try {
                switch (method) {
                    case 1:
                        System.out.print("Nhap khoa k (so nguyen): ");
                        int k = scanner.nextInt();
                        result = isEncrypt ? caesar(text, k) : caesarDecrypt(text, k);
                        break;
                    case 2:
                        System.out.print("Nhap khoa (chuoi): ");
                        String key1 = scanner.next().toUpperCase();
                        result = isEncrypt ? vigenereEncrypt(text, key1, false) : vigenereDecrypt(text, key1, false);
                        break;
                    case 3:
                        System.out.print("Nhap khoa (chuoi): ");
                        String key2 = scanner.next().toUpperCase();
                        result = isEncrypt ? vigenereEncrypt(text, key2, true) : vigenereDecrypt(text, key2, true);
                        break;
                    case 5:
                        System.out.print("Nhap so hang (so nguyen): ");
                        int railKey = scanner.nextInt();
                        result = isEncrypt ? railFenceCipher(text, railKey) : railFenceDecrypt(text, railKey);
                        break;
                    default:
                        System.out.println("Che do khong hop le!");
                        return;
                }
                System.out.println("Ket qua: " + result);
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
            }
        }
    }
}
