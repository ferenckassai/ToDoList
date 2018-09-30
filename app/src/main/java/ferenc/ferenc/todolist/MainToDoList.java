package ferenc.ferenc.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ferenc.ferenc.todolist.adapters.ToDoItemAdapter;
import ferenc.ferenc.todolist.model.ToDoItem;
import ferenc.ferenc.todolist.model.TodoItemDAO;

public class MainToDoList extends AppCompatActivity {

    private static final int RQC_NEW = 1;
    private static final int RQC_MOD = 2;
    private List<ToDoItem> tasks;
    private ListView lvTasks;
    private ToDoItemAdapter adapter;
    private TodoItemDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_to_do_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), New_to_do.class);

                startActivityForResult(intent, RQC_NEW);

            }
        });

        dao = new TodoItemDAO(this);

        tasks = dao.getAllItems();


        //     tasks = new ArrayList<>();

        //   tasks.add(new ToDoItem("2018-12-12", "nev", 1));
        // tasks.add(new ToDoItem("2018-12-12", "nev2", 2));
        // tasks.add(new ToDoItem("2018-12-12", "nev3", 3));

        lvTasks = findViewById(R.id.listToDo);


        adapter = new ToDoItemAdapter(this, R.layout.to_do_item, tasks);

        lvTasks.setAdapter(adapter);


        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ToDoItem selectedToDoItem = (ToDoItem) lvTasks.getItemAtPosition(i);

                Intent intent = new Intent(getApplicationContext(), Modify.class);


                Bundle extras = new Bundle();

                extras.putSerializable("tdi", selectedToDoItem);
                extras.putSerializable("index", i);


                intent.putExtras(extras);


                startActivityForResult(intent, RQC_MOD);


            }
        });

        registerForContextMenu(lvTasks);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.del_all) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Az adatbázis tartalmának törlése")
                    .setMessage("Biztos, hogy mindent törölsz?")
                    .setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

            builder.setPositiveButton("Biztos", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dao.deleteAllItems();
                    adapter.clear();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            ToDoItem tdi = new ToDoItem();

            switch (requestCode) {
                case RQC_NEW:
                    tdi = (ToDoItem) data.getSerializableExtra("tdi");
                    //   adapter.add(tdi);
                    tasks.add(tdi); //kell ez?
                    dao.saveItem(tdi);
                    adapter.notifyDataSetChanged();
                    break;
                case RQC_MOD:

                    Bundle extras = data.getExtras();
                    boolean isDel = (boolean) extras.getSerializable("torles");
                    tdi = (ToDoItem) extras.getSerializable("tdi");
                    int index = data.getIntExtra("index", -1);

                    if (isDel == true) {


                        if (index >= 0) {
                            dao.deleteItem(tdi);
                            tasks.remove(index);
                            adapter.notifyDataSetChanged();
                        } else {

                            Toast.makeText(this, "Nem tudott törölni",
                                    Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        tasks.set(index, tdi); //kell ez?
                        dao.saveItem(tdi);

                        lvTasks = findViewById(R.id.listToDo);


                        adapter = new ToDoItemAdapter(this, R.layout.to_do_item, tasks);

                        lvTasks.setAdapter(adapter);

                    //    adapter.notifyDataSetChanged();

                    }


                    break;
            }

        }
        //itt lehet az eredményeket kiszedni majd a save-ből

        super.onActivityResult(requestCode, resultCode, data);
    }
}
