import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.Math;
public class ScriptDemo {
 
    public static void main(String[] args) {
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        
        try {
            String expression = "Math.pow(2,10)";
            Object result = engine.eval(expression);
            System.out.println(expression+" = "+result);
        } catch(ScriptException se) {
            se.printStackTrace();
        }
    }
}
