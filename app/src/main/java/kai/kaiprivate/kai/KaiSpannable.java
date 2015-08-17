package kai.kaiprivate.kai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import kai.kaiprivate.R;

public class KaiSpannable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_spannable);

        TextView t = (TextView) findViewById(R.id.tv);
        String _str = (String) t.getText();

        t.setText(_str, TextView.BufferType.SPANNABLE);

        Spannable s = (Spannable)t.getText();
        int start = 0;
        int end = 11;

        String sStr = "ll";
        start = _str.indexOf(sStr);
        end = start + sStr.length();

        s.setSpan(new ForegroundColorSpan(0xFFFF0000), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

}
