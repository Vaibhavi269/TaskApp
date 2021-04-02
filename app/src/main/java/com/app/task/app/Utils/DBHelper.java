package com.app.task.app.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.task.app.Model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "tasks.db";
   public static final String TABLE_NAME = "task";
   public static final String COLUMN_ID = "id";
   public static final String COLUMN_TITLE = "title";
   public static final String COLUMN_PRIORITY = "priority";
   private HashMap hp;

   public DBHelper(Context context) {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
      db.execSQL(
         "create table task" +
         "(id integer primary key, title text,priority integer)"
      );
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
      db.execSQL("DROP TABLE IF EXISTS task");
      onCreate(db);
   }

   public boolean insertTask (String title, int priority) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("title", title);
      contentValues.put("priority", priority);
      db.insert(TABLE_NAME, null, contentValues);
      return true;
   }
   
   public Cursor getData(int id) {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from task where id="+id+"", null );
      return res;
   }
   
   public int numberOfRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
      return numRows;
   }
   
   public boolean updateTask (Integer id, String title, int priority) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("title", title);
      contentValues.put("priority", priority);
      db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
      return true;
   }

   public Integer deleteTask (String title) {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete("task",
      "title = ? ",
      new String[] { title });
   }
   
   public List<Task> getTask() {
      List<Task> array_list = new ArrayList<>();
      
      //hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from task", null );
      res.moveToFirst();
      
      while(res.isAfterLast() == false){
         array_list.add(new Task(res.getString(res.getColumnIndex(COLUMN_TITLE)),res.getInt(res.getColumnIndex(COLUMN_PRIORITY))));
         res.moveToNext();
      }
      return array_list;
   }
}