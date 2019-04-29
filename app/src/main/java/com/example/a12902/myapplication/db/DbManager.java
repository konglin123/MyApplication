package com.example.a12902.myapplication.db;

import com.example.a12902.myapplication.app.MyApp;

import org.greenrobot.greendao.database.Database;

public class DbManager {

    private Database mDb;
    private AccessoryDbEntityDao accessoryDbEntityDao;

    private DbManager() {

    }

    private static class DbManagerHolder {
        private static DbManager INSTANCE = new DbManager();
    }

    public static DbManager getInstance() {
        return DbManagerHolder.INSTANCE;
    }

    //创建数据库
    public void init(String dbName) {
        initDao(dbName);
    }

    private void initDao(String dbName) {
        release();
        DbHelper dbHelper = new DbHelper(MyApp.getInstance(), dbName);
        mDb = dbHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(mDb);
        DaoSession daoSession = daoMaster.newSession();
        //清空所有数据表的缓存数据
        daoSession.clear();
        accessoryDbEntityDao = daoSession.getAccessoryDbEntityDao();
    }

    public AccessoryDbEntityDao getAccessoryDao() {
        return accessoryDbEntityDao;
    }

    private void release() {
        if (mDb != null) {
            mDb.close();
            mDb = null;
        }
        accessoryDbEntityDao = null;
    }

}
