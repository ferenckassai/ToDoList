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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ferenc.ferenc.todolist.model.ToDoItem;

public class Modify extends AppCompatActivity {

    private Spinner fontossag;
    private List<String> fontossagRank;
    private ToDoItem tdi;
    private boolean delNotMod = false;
    private EditText etFeladat;
    static String stringDate;
    static String stringDateBe;
    private Intent intent;
    private int index;
    static boolean dataChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        fontossag = findViewById(R.id.spFontossag);
        etFeladat = findViewById(R.id.etTask);

        intent = getIntent();
        dataChanged = false;


        tdi = (ToDoItem) intent.getSerializableExtra("tdi");

        etFeladat.setText(tdi.getName());

        stringDateBe = tdi.getDeadLine();

        index = intent.getIntExtra("index", -1);


        fontossagRank = new ArrayList();

        fontossagRank.add("FONTOS");
        fontossagRank.add("KÖZEPESEN FONTOS");
        fontossagRank.add("NEM FONTOS");


        //kiválasztva legyen

        ArrayAdapter<String> arrayad = new ArrayAdapter<String>(this, R.layout.fontossag_item, fontossagRank);

        fontossag.setAdapter(arrayad);


        fontossag.setSelection(tdi.getImportance() - 1);

        etFeladat.setText(tdi.getName());


    }


    public void saveData(View view) {
        String fnt = fontossag.getSelectedItem().toString();

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

       if (dataChanged) {
           tdi.setDeadLine(stringDate);
       }

        Bundle extras = new Bundle();

        extras.putSerializable("tdi", tdi);
        extras.putSerializable("torles", delNotMod);
        extras.putSerializable("index", index);

        intent.putExtras(extras);

        setResult(RESULT_OK, intent);

        finish();

    }

    public void megsem(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void del(View view) {

        delNotMod = true;


        Bundle extras = new Bundle();

        extras.putSerializable("torles", delNotMod);
        extras.putSerializable("tdi", tdi);
        extras.putSerializable("index", index);

        intent.putExtras(extras);


        setResult(RESULT_OK, intent);

        finish();

    }


      // innen jön a picker

    public void showDatePickerDialog(View view) {

        DialogFragment newFragment = new Modify.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {


        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            String[] datumok = stringDateBe.split("-");


            int year = Integer.parseInt(datumok[0]);
            int month = Integer.parseInt(datumok[1]) - 1;
            int day = Integer.parseInt(datumok[2]);


            return new DatePickerDialog(getActivity(), this, year, month, day);

        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            i1++;

            stringDate = i + "-" + i1 + "-" + i2;
            dataChanged = true;
        }
    }
}

