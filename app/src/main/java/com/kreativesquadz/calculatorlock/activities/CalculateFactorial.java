package com.kreativesquadz.calculatorlock.activities;


public class CalculateFactorial {
    public static final int MAX = 1000;
    private int[] res = new int[1000];
    private int res_size = 1;

    private int multiply(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int[] iArr = this.res;
            int i5 = i4 + (iArr[i3] * i);
            iArr[i3] = i5 % 10;
            i3++;
            i4 = i5 / 10;
        }
        while (i4 != 0) {
            this.res[i2] = i4 % 10;
            i4 /= 10;
            i2++;
        }
        return i2;
    }

    public int[] factorial(int i) {
        this.res[0] = 1;
        for (int i2 = 2; i2 <= i; i2++) {
            this.res_size = multiply(i2, this.res_size);
        }
        return this.res;
    }

    public int getRes() {
        return this.res_size;
    }
}
