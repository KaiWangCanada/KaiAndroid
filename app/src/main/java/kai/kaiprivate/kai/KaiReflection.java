package kai.kaiprivate.kai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import kai.kaiprivate.R;

public class KaiReflection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_reflection);

        StringBuilder sb = new StringBuilder();

        sb.append("Java Code Geeks");
        System.out.println("Initial: " + sb);

        // retrieve the method named "append"
        Method appendMethod = null;
        try {
//            appendMethod = sb.getClass().getMethod("append", String.class);
            appendMethod = sb.getClass().getMethod(null, null);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // invoke the method with the specified argument
        try {
            appendMethod.invoke(sb, "Java Examples & Code Snippets");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("Final: " + sb);




    }

}
