package ferenc.ferenc.todolist.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class ToDoItem implements Serializable{

    private String deadLine;
    private String name;
    private int id;
    private int importance;


    public ToDoItem() {
        this.id = -1;
    }


    public ToDoItem(String deadLine, String name, int importance) {
        this.deadLine = deadLine;
        this.name = name;
        this.importance=importance;
        this.id = -1;
    }

    public ToDoItem(String deadLine, String name, int id, int importance) {
        this.deadLine = deadLine;
        this.name = name;
        this.id = id;
        this.importance=importance;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}



