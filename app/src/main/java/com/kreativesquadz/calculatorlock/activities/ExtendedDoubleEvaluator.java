package com.kreativesquadz.calculatorlock.activities;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Parameters;
import java.util.Iterator;


public class ExtendedDoubleEvaluator extends DoubleEvaluator {
    private static final Function ACOSD = new Function("acosd", 1);
    private static final Function ASIND = new Function("asind", 1);
    private static final Function ATAND = new Function("atand", 1);
    private static final Function CBRT = new Function("cbrt", 1);
    private static final Parameters PARAMS = DoubleEvaluator.getDefaultParameters();
    private static final Function SQRT = new Function("sqrt", 1);

    static {
        PARAMS.add(SQRT);
        PARAMS.add(CBRT);
        PARAMS.add(ASIND);
        PARAMS.add(ACOSD);
        PARAMS.add(ATAND);
    }

    public ExtendedDoubleEvaluator() {
        super(PARAMS);
    }

    @Override
    public Double evaluate(Function function, Iterator<Double> it, Object obj) {
        double atan;
        double degrees;
        if (function == SQRT) {
            degrees = Math.sqrt(it.next().doubleValue());
        } else if (function == CBRT) {
            degrees = Math.cbrt(it.next().doubleValue());
        } else {
            if (function == ASIND) {
                atan = Math.asin(it.next().doubleValue());
            } else if (function == ACOSD) {
                atan = Math.acos(it.next().doubleValue());
            } else if (function != ATAND) {
                return super.evaluate(function, it, obj);
            } else {
                atan = Math.atan(it.next().doubleValue());
            }
            degrees = Math.toDegrees(atan);
        }
        return Double.valueOf(degrees);
    }
}
