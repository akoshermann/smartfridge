package hu.hermann.akos.smartfridge.smartfridge.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hu.hermann.akos.smartfridge.smartfridge.model.Item;

/**
 * Created by a.hermann on 2015. 12. 07..
 *
 * Class for handling the SQLite database.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    /*
     * Fields for db
     */
    private static final int VERSION = 4;
    private static final String DATABASE_NAME = "FRIDGE";
    private static final String TABLE_FRIDGE = "fridge_items";
    private static final String KEY_NAME = "name";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_PRICE = "price";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FRIDGE_ITEMS_TABLE = "CREATE TABLE " + TABLE_FRIDGE + "(" + KEY_NAME + " TEXT,"
                + KEY_AMOUNT + " INTEGER, " + KEY_PRICE + " FLOAT" + ")";
        db.execSQL(CREATE_FRIDGE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIDGE);
        onCreate(db);
    }

    /**
     * Adds a new item to the database
     * @param item to add
     */
    public void addItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, item.getName());
        contentValues.put(KEY_AMOUNT, item.getAmount());
        contentValues.put(KEY_PRICE, item.getPrice());

        database.insert(TABLE_FRIDGE, null, contentValues);
        database.close();
    }

    /**
     * Get the item with the matching name.
     * @param name
     * @return the item, if there's no item with that name null.
     */
    public Item getItem(String name){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_FRIDGE, new String[] {KEY_NAME, KEY_AMOUNT, KEY_PRICE}, KEY_NAME + "=?",
                new String[]{name}, null, null, null, null);
        if(cursor == null){
            return null;
        }else cursor.moveToFirst();

        Item item = new Item(cursor.getString(0), cursor.getInt(1), cursor.getDouble(2));
        return item;
    }

    /**
     * Gives back all of the items in the db.
     * @return items, empty list if there's none.
     */
    public List<Item> getAllItems(){
        List<Item> itemList = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_FRIDGE, null);
        if(cursor.moveToFirst()) {
            do{
                Item item = new Item(cursor.getString(0), Integer.valueOf(cursor.getString(1)), Double.valueOf(cursor.getString(2)));
                itemList.add(item);
            }while (cursor.moveToNext());
        }
        return itemList;
    }


    /**
     * Gives back the item count.
     * @return
     */
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FRIDGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    /**
     * Updates an item.
     * @param item to update
     * @return
     */
    public int updateItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_AMOUNT, item.getAmount());
        contentValues.put(KEY_PRICE, item.getPrice());

        return database.update(TABLE_FRIDGE, contentValues, KEY_NAME + "=?", new String[]{item.getName()});
    }

    /**
     * Deletes an item.
     * @param item to delete
     */
    public void deleteItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_FRIDGE, KEY_NAME + "=?", new String[]{item.getName()});
        database.close();
    }
}
