import java.math.BigInteger;
import java.util.*;

class NumberTheory {
    public static int eulerTotient(int n) {
        int result = n;
        for (int p = 2; p * p <= n; p++) {
            if (n % p == 0) {
                while (n % p == 0) {
                    n /= p;
                }
                result -= result / p;
            }
        }
        if (n > 1) {
            result -= result / n;
        }
        return result;
    }

    public static long modExp(long a, long m, long n) {
        long result = 1;
        a %= n;
        while (m > 0) {
            if ((m & 1) == 1) {
                result = (result * a) % n;
            }
            a = (a * a) % n;
            m >>= 1;
        }
        return result;
    }




    public static long modInv(long a, long m) {
        long m0 = m, t, q;
        long x0 = 0, x1 = 1;
        if (m == 1) return 0;

        while (a > 1) {
            q = a / m;
            t = m;
            m = a % m;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }
        if (x1 < 0) x1 += m0;
        return x1;
    }

    public static long chineseRemainderTheorem(List<Congruence> congruences) {
        long M = 1;
        for (Congruence congruence : congruences) {
            M *= congruence.m;
        }

        long solution = 0;
        for (Congruence congruence : congruences) {
            long a_i = congruence.a;
            long M_i = M / congruence.m;
            long N_i = modInv(M_i, congruence.m);
            solution = (solution + a_i * M_i % M * N_i) % M;
        }
        return solution;
    }

    public static List<Long> getPrimeFactors(long x) {
        List<Long> factors = new ArrayList<>();
        for (long i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                factors.add(i);
                while (x % i == 0) x /= i;
            }
        }
        if (x > 1) factors.add(x);
        return factors;
    }

    public static boolean isPrimitiveRoot(int a, int n) {
        int phi = eulerTotient(n);
        List<Long> factors = getPrimeFactors(phi);

        for (long factor : factors) {
            if (modExp(a, phi / factor, n) == 1) return false;
        }
        return true;
    }
    static BigInteger modInverse(BigInteger a, BigInteger m) {
        return a.modInverse(m);
    }

    public static int discreteLog(int a, int b, int m) {
        a %= m;
        b %= m;
        int n = (int) Math.sqrt(m) + 1;

        long an = 1;
        for (int i = 0; i < n; ++i) {
            an = (an * a) % m;
        }

        Map<Integer, Integer> vals = new HashMap<>();
        for (int q = 0, cur = b; q <= n; ++q) {
            vals.put(cur, q);
            cur = (cur * a) % m;
        }

        for (int p = 1, cur = 1; p <= n; ++p) {
            cur = (int) ((cur * an) % m);
            if (vals.containsKey(cur)) {
                return n * p - vals.get(cur);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Chọn chế độ:");
            System.out.println("1: Thuật toán phần dư Trung Hoa (CRT)");
            System.out.println("2: Kiểm tra căn nguyên thủy");
            System.out.println("3: Tính hàm số Euler (φ)");
            System.out.println("4: Lũy thừa modulo");
            System.out.println("5: Nghịch đảo modulo");
            System.out.println("6: Giải logarit rời rạc");
            System.out.println("7: Các phép toán modulo đặc biệt");
            System.out.println("Nhập lựa chọn: ");
            int mode = scanner.nextInt();

            switch (mode) {
                case 1:
                    System.out.println("Nhập số hệ đồng dư (số phương trình trong hệ): ");
                    int h = scanner.nextInt();
                    List<Congruence> congruences = new ArrayList<>();
                    System.out.println("Nhập từng cặp số dư a và modulo m, mỗi cặp trên một dòng: ");
                    for (int i = 0; i < h; i++) {
                        System.out.print("Nhập a" + (i + 1) + ": ");
                        long a = scanner.nextLong();
                        System.out.print("Nhập m" + (i + 1) + ": ");
                        long m = scanner.nextLong();
                        congruences.add(new Congruence(a, m));
                    }
                    System.out.println("Nghiệm của hệ đồng dư là: " + chineseRemainderTheorem(congruences));
                    break;
                case 2:
                    System.out.print("Nhập số a và n: ");
                    int pa = scanner.nextInt();
                    int pn = scanner.nextInt();
                    if (isPrimitiveRoot(pa, pn)) {
                        System.out.println(pa + " là căn nguyên thủy của " + pn);
                    } else {
                        System.out.println(pa + " không phải là căn nguyên thủy của " + pn);
                    }
                    break;
                case 3:
                    System.out.print("Nhập n: ");
                    int n = scanner.nextInt();
                    System.out.println("Hàm Euler φ(" + n + ") = " + eulerTotient(n));
                    break;
                case 4:
                    System.out.print("Nhập a, m, n (tính a^m mod n): ");
                    long a = scanner.nextLong();
                    long m = scanner.nextLong();
                    long mod = scanner.nextLong();
                    System.out.println("Kết quả: " + modExp(a, m, mod));
                    break;
                case 5:
                    System.out.print("Nhập a, m (tìm a^(-1) mod m): ");
                    BigInteger invA = scanner.nextBigInteger();
                    BigInteger invM = scanner.nextBigInteger();
                    System.out.println("Nghịch đảo của " + invA + " mod " + invM + " là: " + modInverse(invA, invM));
                    break;
                case 6:
                    System.out.print("Nhập a, b, m (tìm x sao cho a^x ≡ b (mod m)): ");
                    int da = scanner.nextInt();
                    int db = scanner.nextInt();
                    int dm = scanner.nextInt();
                    int result = discreteLog(da, db, dm);
                    if (result == -1) System.out.println("Không tìm thấy x");
                    else System.out.println("x = " + result);
                    break;
                case 7:
                    System.out.println("Nhập a, x, b, y, n: ");
                    long a1 = scanner.nextLong(), x1 = scanner.nextLong(), b = scanner.nextLong(), y = scanner.nextLong(), n1 = scanner.nextLong();
                    System.out.println("A1 = " + (modExp(a1, x1, n1) + modExp(b, y, n1)) % n1);
                    System.out.println("A2 = " + (modExp(a1, x1, n1) - modExp(b, y, n1) + n1) % n1);
                    System.out.println("A3 = " + (modExp(a1, x1, n1) * modExp(b, y, n1)) % n1);
                    System.out.println("A4 = " + modInv(modExp(b, y, n1), n1));
                    System.out.println("A5 = " + (modExp(a1, x1, n1) * modInv(modExp(b, y, n1), n1)) % n1);
                    break;
                default:
                    System.out.println("Chế độ không hợp lệ!");
            }
        }
    }
}
