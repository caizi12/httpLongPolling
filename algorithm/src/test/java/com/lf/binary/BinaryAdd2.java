package com.lf.binary;

public class BinaryAdd2 {

    public static String addBinary(String a, String b) {
        int aLength = a.length();
        int bLength = b.length();
        int n = Math.max(aLength, bLength);
        int carry = 0;
        StringBuffer result = new StringBuffer(n);
        int i = 0;
        while (i < n) {
            if (i < aLength) {
                carry += (a.charAt(aLength - 1 - i)) - '0';
            }

            if (i < bLength) {
                carry += (b.charAt(bLength - 1 - i)) - '0';
            }

            result.append(carry % 2);
            carry = carry / 2;
            i++;
        }
        if (carry == 1) {
            result.append("1");
        }
        return result.reverse().toString();
    }

    public static void main(String[] args) {
        String aa = "1010";
        String bb = "1011";
        System.out.println(addBinary(aa, bb));
    }
}
