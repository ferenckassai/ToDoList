package ferenc.ferenc.todolist.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDBHelper extends SQLiteOpenHelper {

    private static final String DB_name = "todolist.db";
    private static final int DB_version = 1;

    public ToDoDBHelper(Context context) {
        super(context, DB_name, null, DB_version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE todoitem (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, deadline TEXT, importance INTEGER)");


        sqLiteDatabase.execSQL("INSERT INTO todoitem (name,deadline,importance)" +
                " VALUES ('Proba', '2018-08-08', 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
