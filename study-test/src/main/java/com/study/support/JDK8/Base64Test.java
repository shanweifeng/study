package com.study.support.JDK8;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 21:36 2018/3/12
 * @Modified By:
 */
public class Base64Test {
    public static void main(String[] args) {
        final String text = "Base64 finally in Java 8!";

        final String encoded = Base64
                .getEncoder()
                .encodeToString( text.getBytes( StandardCharsets.UTF_8 ) );
        System.out.println( encoded );

        final String decoded = new String(
                Base64.getDecoder().decode( encoded ),
                StandardCharsets.UTF_8 );
        System.out.println( decoded );
    }
}
