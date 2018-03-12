package com.study.support.JDK8;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 21:30 2018/3/12
 * @Modified By:
 */
public class JavaScript {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName( "JavaScript" );

        double x = 12.5;
        engine.put("x",x);
        System.out.println( engine.getClass().getName() );
        System.out.println( "Result:" + engine.eval( "function f(x) { return 10*x; }; f(x) + x;" ) );
    }
}
