package kai.kaiprivate.kai;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kai.kaiprivate.R;

public class KaiSpeech extends AppCompatActivity {

    private static final String TAG = "VoiceRecognition";
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    private TextView textView;
    private Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_speech);

        initWidget();

        // Check recognition activity existence
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
            button.setText("Recognizer not present");
        }
    }

    public void click(View view) {
        startVoiceRecognitionActivity();
    }

    private void initWidget()
    {
        textView = (TextView)findViewById(R.id.tv);
        button = (Button)findViewById(R.id.btn);
    }

    /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Display an hint to the user about what he should say.
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speak:");

        // Given an hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Specify how many results you want to receive. The results will be sorted
        // where the first result is the one with higher confidence.
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            StringBuilder stringBuilder = new StringBuilder();
            int Size = matches.size();
            for(int i=0;i<Size;++i)
            {
                stringBuilder.append(matches.get(i));
                stringBuilder.append("\n");
            }
            textView.setText(stringBuilder);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
