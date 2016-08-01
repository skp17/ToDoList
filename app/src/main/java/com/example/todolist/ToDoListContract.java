package com.example.todolist;

import android.provider.BaseColumns;

/**
 * Created by Peters on 2016-07-31.
 */
public class ToDoListContract {

    public void ToDoListContract() {} // Empty

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "ToDo_List";
        public static final String ITEM = "item";
        public static final String IS_CHECKED = "is_checked";
    }

}
