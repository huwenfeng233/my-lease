package com.bighu.common.utils;

import java.util.Random;

public class VerifyCodeUtil {
    public static String getVerivfyCode(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
