package com.example.a12902.myapplication.db;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

public class DbHelper extends DaoMaster.OpenHelper {

    private static final String TAG = "DbHelper";

    public DbHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.e(TAG, "onUpgrade: oldVersion:" + oldVersion + ",newVersion:" + newVersion);
        //第三个参数是哪些表修改了就要传进去那些
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, AccessoryDbEntityDao.class);
    }
}
