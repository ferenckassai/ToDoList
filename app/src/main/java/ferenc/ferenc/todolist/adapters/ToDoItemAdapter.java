package ferenc.ferenc.todolist.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ferenc.ferenc.todolist.R;
import ferenc.ferenc.todolist.model.ToDoItem;

import static android.widget.Toast.*;

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    private int resource;
    private Date date = new Date();



    public ToDoItemAdapter(@NonNull Context context, int resource, @NonNull List<ToDoItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
        }

        ToDoItem tdi = getItem(position);


        TextView tvTask = convertView.findViewById(R.id.tvTask);
        TextView tvDate = convertView.findViewById(R.id.tvDate);


        tvTask.setText("\u2022" + tdi.getName());
        tvDate.setText(tdi.getDeadLine());


        int importance = tdi.getImportance();

        if (importance == 1) {

            tvTask.setTextColor(Color.RED);

        }

        if (importance == 2) {

            tvTask.setTextColor(Color.parseColor("#ffa500"));
        }

        if (importance == 3) {

            tvTask.setTextColor(Color.parseColor("#a4c639"));

        }


        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            DateFormat df = sdf;

            Date deadLin = df.parse(tdi.getDeadLine());

            String nowTime = sdf.format(date.getTime());

            Date nowT = df.parse(nowTime);

            if (deadLin.getTime()<nowT.getTime()){
                tvTask.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.small_red_skull_hi, 0, 0, 0);
            }

         //   tvTask.getDrawableState()


        } catch (ParseException e) {
             e.printStackTrace();
        }


        return convertView;
    }
}
