package ai.zhuanzhi.test;

public class Test {
    public static int add(int a, int b) {
        int carrier;
        while (b != 0) {
            carrier = (a & b) << 1;
            a = a ^ b;
            b = carrier;
        }
        return a;
    }

    public static int subtraction(int a, int b) {
        return add(a, add(~b, 1));
    }

    public static int div(int a, int b) {
        int flag = (a > 0 && b > 0 || a < 0 && b < 0) ? 1 : -1;
        if (a < 0) a = add(~a, 1);
        if (b < 0) b = add(~b, 1);

        int quotient = 0;
        int remainder = 0;
//        while(a >= b){
//            quotient = add(quotient, 1);
//            a = subtraction(a, b);
//        }
        for (int i = 31; i >= 0; i--) {
            if ((a >> i) >= b) {
                quotient = add(quotient, 1 << i);
                a = subtraction(a, b << i);
            }
        }
        if (flag == -1) return add(~quotient, 1);
        return quotient;
    }

    public static int multi(int a, int b) {
        int flag = (a > 0 && b > 0 || a < 0 && b < 0) ? 1 : -1;
        if (a < 0) a = add(~a, 1);
        if (b < 0) b = add(~b, 1);
        int res = 0;
        while (b > 0) {
            if ((b & 1) > 0) {
                res = add(res, a);
            }
            a = a << 1;
            b = b >> 1;
        }
        if (flag == 1) {
            return res;
        }
        return add(~res, 1);
    }

    public static void main(String[] args) {
        System.out.println(multi(12, -11));
        System.out.println(div(12, 3));
    }
}
