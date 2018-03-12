package com.study.support.JDK8;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 19:47 2018/3/12
 * @Modified By:
 */
public class JDK8Character {
    public static void main(String[] args) throws Exception {
        Method method = JDK8Character.class.getMethod( "main", String[].class );
        for( final Parameter parameter: method.getParameters() ) {
            System.out.println( "Parameter: " + parameter.getName() );
        }
    }
}
