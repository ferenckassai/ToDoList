package ferenc.ferenc.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ferenc.ferenc.todolist.model.ToDoItem;

public class New_to_do extends AppCompatActivity {

    private Spinner fontossag;
    private List<String> fontossagRank;
    private ToDoItem tdi;
    private EditText etFeladat;
    static String stringDate;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_to_do);
        fontossag = findViewById(R.id.spFontossag);
        etFeladat = findViewById(R.id.etTask);

        intent = getIntent();

        tdi = (ToDoItem) intent.getSerializableExtra("tdi");


        fontossagRank = new ArrayList();

        fontossagRank.add("FONTOS");
        fontossagRank.add("KÖZEPESEN FONTOS");
        fontossagRank.add("NEM FONTOS");

        fontossag.setAdapter(new ArrayAdapter<String>(this, R.layout.fontossag_item, fontossagRank));

    }

    public void saveData(View view) {
        if (stringDate != null) {
            String fnt = fontossag.getSelectedItem().toString();
            tdi = new ToDoItem();

            switch (fnt) {
                case "FONTOS":
                    tdi.setImportance(1);
                    break;
                case "KÖZEPESEN FONTOS":
                    tdi.setImportance(2);
                    break;
                case "NEM FONTOS":
                    tdi.setImportance(3);
                    break;
            }

            tdi.setName(etFeladat.getText().toString());
            tdi.setDeadLine(stringDate);


            intent.putExtra("tdi", tdi);

            setResult(RESULT_OK, intent);

            finish();
        }else {

            Toast.makeText(this, "Nem volt dátum beállítva",Toast.LENGTH_LONG).show();
        }



    }

    public void megsem(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    // innen jön a picker

    public void showDatePickerDialog(View view) {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {


        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);


        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            i1++;

            stringDate = i + "-" + i1 + "-" + i2;

              }
    }
}

