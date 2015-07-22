package kai.kaiprivate.kai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import kai.kaiprivate.R;

public class KaiTextViewTimePicker4MMF extends Activity implements View.OnClickListener{

    TextView tv;
    final Integer defaultStartHour = 9;
    final Integer defaultCloseHour = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_text_view_time_picker4_mmf);

        tv = (TextView) findViewById(R.id.tv_time_picker);
        tv.setText(String.valueOf(defaultStartHour));
        tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int _id = v.getId();
//        Log.v("kai", "tough");
        switch(_id) {
            case R.id.tv_time_picker:
//                Log.v("kai", "time picker");

                //using TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.v("kai", hourOfDay + ":" + minute);
                        tv.setText(hourOfDay + ":" + minute);
                    }
                }, defaultStartHour, 0, true);

                timePickerDialog.show();

                // using AlertDialog
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        this);
//
//                // set title
//                alertDialogBuilder.setTitle("Pick Time");
//
//                // set dialog message
//                alertDialogBuilder
//                        .setMessage("Click yes to exit!")
//                        .setCancelable(false)
//                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                // if this button is clicked, show timepicker
//                                Log.v("kai", "ok");
//                                tv.setText("ok");
//                            }
//                        })
//                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                // if this button is clicked, just close
//                                // the dialog box and do nothing
//                                dialog.cancel();
//                            }
//                        });
//
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // show it
//                alertDialog.show();
                break;
            default:
                break;
        }
    }
}
