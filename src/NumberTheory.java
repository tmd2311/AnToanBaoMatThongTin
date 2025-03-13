import java.math.BigInteger;
import java.util.*;

class NumberTheory {
    static int eulerTotient(int n) {
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

    static BigInteger modExp(BigInteger a, BigInteger m, BigInteger n) {
        return a.modPow(m, n);
    }

    static BigInteger modInverse(BigInteger a, BigInteger m) {
        return a.modInverse(m);
    }

    static boolean isPrimitiveRoot(int a, int n) {
        if (BigInteger.valueOf(a).gcd(BigInteger.valueOf(n)).compareTo(BigInteger.ONE) != 0) {
            return false;
        }
        int phi = eulerTotient(n);
        List<Long> factors = getPrimeFactors(phi);

        for (long factor : factors) {
            BigInteger result = modExp(BigInteger.valueOf(a), BigInteger.valueOf(phi / factor), BigInteger.valueOf(n));
            if (result.equals(BigInteger.ONE))
                return false;
        }
        return true;
    }

    static List<Long> getPrimeFactors(long x) {
        List<Long> factors = new ArrayList<>();
        for (long i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                factors.add(i);
                while (x % i == 0)
                    x /= i;
            }
        }
        if (x > 1) {
            factors.add(x);
        }
        return factors;
    }

    static int discreteLog(int a, int b, int m) {
        a %= m;
        b %= m;
        int n = (int) Math.sqrt(m) + 1;

        int an = 1;
        for (int i = 0; i < n; ++i)
            an = (an * a) % m;

        Map<Integer, Integer> vals = new HashMap<>();
        for (int q = 0, cur = b; q <= n; ++q) {
            vals.put(cur, q);
            cur = (cur * a) % m;
        }

        for (int p = 1, cur = 1; p <= n; ++p) {
            cur = (cur * an) % m;
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
            System.out.println("1: Tính hàm số Euler (φ)");
            System.out.println("2: Lũy thừa modulo");
            System.out.println("3: Nghịch đảo modulo");
            System.out.println("4: Kiểm tra căn nguyên thủy");
            System.out.println("5: Giải logarit rời rạc");
            System.out.print("Nhập lựa chọn: ");

            int mode = scanner.nextInt();

            switch (mode) {
                case 1:
                    System.out.print("Nhập n: ");
                    int n = scanner.nextInt();
                    System.out.println("Hàm Euler φ(" + n + ") = " + eulerTotient(n));
                    break;
                case 2:
                    System.out.print("Nhập a, m, n (tính a^m mod n): ");
                    BigInteger a = scanner.nextBigInteger();
                    BigInteger m = scanner.nextBigInteger();
                    BigInteger mod = scanner.nextBigInteger();
                    System.out.println("Kết quả: " + modExp(a, m, mod));
                    break;
                case 3:
                    System.out.print("Nhập a, m (tìm a^(-1) mod m): ");
                    BigInteger invA = scanner.nextBigInteger();
                    BigInteger invM = scanner.nextBigInteger();
                    System.out.println("Nghịch đảo của " + invA + " mod " + invM + " là: " + modInverse(invA, invM));
                    break;
                case 4:
                    System.out.print("Nhập số a và n: ");
                    int pa = scanner.nextInt();
                    int pn = scanner.nextInt();
                    if (isPrimitiveRoot(pa, pn)) {
                        System.out.println(pa + " là căn nguyên thủy của " + pn);
                    } else {
                        System.out.println(pa + " không phải là căn nguyên thủy của " + pn);
                    }
                    break;
                case 5:
                    System.out.print("Nhập a, b, m (tìm x sao cho a^x ≡ b (mod m)): ");
                    int da = scanner.nextInt();
                    int db = scanner.nextInt();
                    int dm = scanner.nextInt();
                    int result = discreteLog(da, db, dm);
                    if (result == -1) System.out.println("Không tìm thấy x");
                    else System.out.println("x = " + result);
                    break;
                default:
                    System.out.println("Chế độ không hợp lệ!");
            }
        }
    }
}
