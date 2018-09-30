package ferenc.ferenc.todolist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ferenc.ferenc.todolist.model.ToDoDBHelper;

public class TodoItemDAO {

    private ToDoDBHelper helper;

    public TodoItemDAO(Context context) {

        helper = new ToDoDBHelper(context);

    }

    public List<ToDoItem> getAllItems() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM todoitem", null);
        cursor.moveToFirst();



        List<ToDoItem> listOfItems = new ArrayList<>();


        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String deadline = cursor.getString(cursor.getColumnIndex("deadline"));
            int importance = cursor.getInt(cursor.getColumnIndex("importance"));

            ToDoItem tdi = new ToDoItem(deadline, name, id, importance);

            listOfItems.add(tdi);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return listOfItems;

    }


    public void saveItem (ToDoItem tdi){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", tdi.getName());
        cv.put("deadline", tdi.getDeadLine());
        cv.put("importance",tdi.getImportance());

        if (tdi.getId() == -1) {//új elem

            long id = db.insert("todoitem", null, cv);
            tdi.setId((int) id);

        } else {//meglévő módosítása

            db.update("todoitem", cv, "_id=?", new String[]{tdi.getId() + ""});

        }
        db.close();

    }

    public void deleteItem (ToDoItem tdi){

        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("todoitem", "_id=?", new String[]{
                tdi.getId() + ""});
        db.close();

    }

    public void deleteAllItems (){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM todoitem");
        db.close();

    }




}
