package com.example.MPAndroidChart.greenDAO;

import android.content.Context;

import com.example.MPAndroidChart.greenDAO.gen.DaoMaster;
import com.example.MPAndroidChart.greenDAO.gen.DaoSession;

public class DBService {
    private static final String DB_NAME = "greedDaoDemo.db";
    private DaoSession daoSession;

    public void init(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());

        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }
}
