package com.example.MPAndroidChart.greenDAO.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.MPAndroidChart.greenDAO.Ledger;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LEDGER".
*/
public class LedgerDao extends AbstractDao<Ledger, Long> {

    public static final String TABLENAME = "LEDGER";

    /**
     * Properties of entity Ledger.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Dollars = new Property(2, int.class, "dollars", false, "DOLLARS");
        public final static Property Date = new Property(3, String.class, "date", false, "DATE");
        public final static Property Type = new Property(4, String.class, "type", false, "TYPE");
        public final static Property Photo = new Property(5, String.class, "photo", false, "PHOTO");
    }


    public LedgerDao(DaoConfig config) {
        super(config);
    }
    
    public LedgerDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LEDGER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT NOT NULL ," + // 1: name
                "\"DOLLARS\" INTEGER NOT NULL ," + // 2: dollars
                "\"DATE\" TEXT," + // 3: date
                "\"TYPE\" TEXT," + // 4: type
                "\"PHOTO\" TEXT);"); // 5: photo
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LEDGER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Ledger entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindLong(3, entity.getDollars());
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(4, date);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
 
        String photo = entity.getPhoto();
        if (photo != null) {
            stmt.bindString(6, photo);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Ledger entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindLong(3, entity.getDollars());
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(4, date);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
 
        String photo = entity.getPhoto();
        if (photo != null) {
            stmt.bindString(6, photo);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Ledger readEntity(Cursor cursor, int offset) {
        Ledger entity = new Ledger( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.getInt(offset + 2), // dollars
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // date
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // type
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // photo
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Ledger entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setDollars(cursor.getInt(offset + 2));
        entity.setDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPhoto(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Ledger entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Ledger entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Ledger entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
