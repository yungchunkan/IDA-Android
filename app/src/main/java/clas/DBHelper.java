package clas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricky on 15/7/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager";
    private static final String TABLE_USER = "user";
    private static final String TABLE_FOLLOWED_REST = "followedRest";
    private static final String TABLE_FOLLOWED_DISCOUNT = "followedDiscount";

    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";


    private static final String KEY_DISCOUNT_ID = "discountId";
    private static final String KEY_REST_ID = "restId";

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + KEY_NAME + " TEXT PRIMARY KEY,"
            + KEY_PASSWORD + " TEXT" + ")";

    private static final String CREATE_FOLLOWED_REST_TABLE = "CREATE TABLE " + TABLE_FOLLOWED_REST + "("
            + KEY_NAME + " TEXT,"
            + KEY_REST_ID + " TEXT,"
            + "PRIMARY KEY (" + KEY_NAME + ", " + KEY_REST_ID + "))";

    private static final String CREATE_FOLLOWED_DISCOUNT_TABLE = "CREATE TABLE " + TABLE_FOLLOWED_DISCOUNT + "("
            + KEY_NAME + " TEXT,"
            + KEY_DISCOUNT_ID + " TEXT,"
            + "PRIMARY KEY (" + KEY_NAME + ", " + KEY_DISCOUNT_ID + "))";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FOLLOWED_REST_TABLE);
        db.execSQL(CREATE_FOLLOWED_DISCOUNT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLLOWED_REST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLLOWED_DISCOUNT);
        onCreate(db);
    }

    public void addUser(User user, String pw) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASSWORD, pw);

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public User loginUser(String name, String pw) {
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_NAME + " = ? AND " + KEY_PASSWORD + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name, pw});

        if (!cursor.moveToFirst()) {
            return null;
        }
        User user = new User(cursor.getString(0));

        cursor.close();
        db.close();

        return user;
    }

    public User getUser(String name) {
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_NAME + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name});

        if (!cursor.moveToFirst()) {
            return null;
        }
        User user = new User(cursor.getString(0));

        cursor.close();
        db.close();

        return user;
    }

    public void addFollowedRest(String user, String restId) {
        if (!checkFollowedRestExist(user, restId)) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, user);
            values.put(KEY_REST_ID, restId);

            db.insert(TABLE_FOLLOWED_REST, null, values);
            db.close();
        }
    }

    public List<String> getFollowedRestIdByUser(String user) {
        String selectQuery = "SELECT * FROM " + TABLE_FOLLOWED_REST + " WHERE " + KEY_NAME + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user});

        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }

    public boolean checkFollowedRestExist(String user, String restId) {
        String selectQuery = "SELECT * FROM " + TABLE_FOLLOWED_REST + " WHERE " + KEY_NAME + " = ? AND " + KEY_REST_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user, restId});

        boolean result = false;
        if (cursor.moveToFirst()) {
            result = true;
        }

        cursor.close();
        db.close();

        return result;
    }

    public boolean removeFollowedRest(String user, String restId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_FOLLOWED_REST, KEY_NAME + " = ? AND " + KEY_REST_ID + " = ?", new String[]{user, restId});
        db.close();

        return (i > 0);
    }

    public void addLikedDiscount(String user, String discountId) {
        if (!checkLikedDiscountExist(user, discountId)) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, user);
            values.put(KEY_DISCOUNT_ID, discountId);

            db.insert(TABLE_FOLLOWED_DISCOUNT, null, values);
            db.close();
        }
    }

    public List<String> getLikedDiscountByUser(String user) {
        String selectQuery = "SELECT * FROM " + TABLE_FOLLOWED_DISCOUNT + " WHERE " + KEY_NAME + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user});

        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }

    public boolean checkLikedDiscountExist(String user, String discountId) {
        String selectQuery = "SELECT * FROM " + TABLE_FOLLOWED_DISCOUNT + " WHERE " + KEY_NAME + " = ? AND " + KEY_DISCOUNT_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user, discountId});

        boolean result = false;
        if (cursor.moveToFirst()) {
            result = true;
        }

        cursor.close();
        db.close();

        return result;
    }

    public boolean removeLikedDiscount(String user, String discountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_FOLLOWED_DISCOUNT, KEY_NAME + " = ? AND " + KEY_DISCOUNT_ID + " = ?", new String[]{user, discountId});
        db.close();

        return (i > 0);
    }
}
