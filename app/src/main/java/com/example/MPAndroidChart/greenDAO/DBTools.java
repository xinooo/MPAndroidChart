package com.example.MPAndroidChart.greenDAO;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.util.Log;

import com.example.MPAndroidChart.greenDAO.gen.LedgerDao;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SuppressLint("SimpleDateFormat")
public class DBTools {
    //新增數據
    public static void save(DBService dbService, String Type, String Name, int Dollars, String PhotoPath){
//        String now = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        String now = new SimpleDateFormat("yyyy-MM-").format(new Date());
        int ii= new Random().nextInt(29)+1;
        String day = ii < 10? "0"+ii : ""+ii;
        Ledger ledger = new Ledger();
        ledger.setName(Name);
        ledger.setDollars(Dollars);
        ledger.setDate((now+day)+" 00:00:01");
        ledger.setPhoto("/storage/emulated/0/UploadImage/1604884152788.jpg");
        ledger.setType(Type);
        //返回的值為新增數據的id
        long i = dbService.getDaoSession().getLedgerDao().insert(ledger);
        Log.e("Save","flag："+i);
        Log.e("Save","Date："+now+day);
    }
    //查詢數據
    public static List<Ledger> query(DBService dbService){
        //查詢結果返回多條數據
        List<Ledger> ledger = dbService.getDaoSession().getLedgerDao().queryBuilder()
                .where(LedgerDao.Properties.Type.eq("工资"),
                        LedgerDao.Properties.Date.between("2020-11-01 12:02:51","2020-11-30 12:17:10"))
                .build().list();

        Log.e("Query","flag："+new Gson().toJson(ledger));
        return ledger;
    }

    //查詢數據
    public static List<Ledger> query2(DBService dbService) {
        //查詢結果返回多條數據
        List<Ledger> ledger = new ArrayList<>();
        String[] strings = getRange().split(",");
//        String s = "SELECT * FROM LEDGER WHERE NAME = '一月' AND DATE BETWEEN '"+strings[0]+"' AND '"+strings[1]+"'";
        String s = "SELECT * FROM LEDGER WHERE NAME = '一月' AND DATE BETWEEN '"+strings[0]+"' AND '2020-11-30'";
        Cursor cursor = dbService.getDaoSession().getLedgerDao().getDatabase()
                .rawQuery(s, null);

        /*
        //金額加總
        Cursor cursor = dbService.getDaoSession().getLedgerDao().getDatabase()
                .rawQuery("SELECT SUM(DOLLARS) FROM LEDGER WHERE DATE BETWEEN '2020-11-03' AND '2020-11-04'", null);
        cursor.moveToFirst();
        Log.e("Query","flag："+cursor.getLong(0));*/

        while (cursor.moveToNext()) {
            //根據當前列名自獲取當前索引
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("NAME"));
            int dollars = cursor.getInt(cursor.getColumnIndex("DOLLARS"));
            String date = cursor.getString(cursor.getColumnIndex("DATE"));
            String type = cursor.getString(cursor.getColumnIndex("TYPE"));
            String photo = cursor.getString(cursor.getColumnIndex("PHOTO"));

            ledger.add(new Ledger(id,name,dollars,date,type,photo));

            Log.e("Query2", "id=" + id + "; name=" + name + "; dollars=" + dollars + "; date=" + date + "; type=" + type + "; photo=" + photo);
        }
        return ledger;
    }

    //查詢數據
    public static List<Ledger> getWeekData(DBService dbService) {
        //查詢結果返回多條數據
        List<Ledger> ledger = new ArrayList<>();
        String[] strings = getRange().split(",");
        //查詢本週數據
        for (int i = 0; i < 7; i++){
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(strings[0]));
                c.add(Calendar.DAY_OF_YEAR,i);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String date = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            String ss = "SELECT SUM(DOLLARS) FROM LEDGER WHERE DATE BETWEEN '" + date + " 00:00:00" + "' AND '"+ date + " 23:59:59'";
            Cursor cursor = dbService.getDaoSession().getLedgerDao().getDatabase()
                    .rawQuery(ss, null);
            cursor.moveToFirst();
            ledger.add(new Ledger(0L,"",(int) cursor.getLong(0),new SimpleDateFormat("MM/dd").format(c.getTime()),"",""));
        }
        return ledger;
    }

    //查詢數據
    public static List<Ledger> getMonthData(DBService dbService) {
        List<Ledger> ledger = new ArrayList<>();
        int month, count = 0;
        String firstDay = new SimpleDateFormat("yyyy-MM-").format(new Date())+"01";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        month = calendar.get(Calendar.MONTH);
        //查詢本月數據
        while (true){
            try {
                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(firstDay));
                calendar.add(Calendar.DAY_OF_YEAR,count);
                count++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(month != calendar.get(Calendar.MONTH)){
                return ledger;
            }
            String date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            String ss = "SELECT SUM(DOLLARS) FROM LEDGER WHERE DATE BETWEEN '" + date + " 00:00:01" + "' AND '"+ date + " 23:59:59'";
            Cursor cursor = dbService.getDaoSession().getLedgerDao().getDatabase()
                    .rawQuery(ss, null);
            cursor.moveToFirst();
            ledger.add(new Ledger(0L,"",(int) cursor.getLong(0),new SimpleDateFormat("MM/dd").format(calendar.getTime()),"",""));
        }
    }

    public static int getTodayWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Log.e("todayWeek","todayWeek:"+c.get(Calendar.DAY_OF_WEEK));
        return c.get(Calendar.DAY_OF_WEEK)-1;
    }

    public static String getRange(){
        String startDate = "", endDate = "";
        Calendar Start = Calendar.getInstance();
        Calendar End = Calendar.getInstance();
        Start.setTime(new Date());
        End.setTime(new Date());
        String now = new SimpleDateFormat("yyyy-MM-dd").format(Start.getTime());
        Log.e("todayWeek","today:"+now);
        String range = "";
        switch (getTodayWeek()){
            case 0://禮拜日
                Start.add(Calendar.DAY_OF_YEAR,-6);//日期加10天
                startDate = new SimpleDateFormat("yyyy-MM-dd").format(Start.getTime());
                endDate = new SimpleDateFormat("yyyy-MM-dd").format(End.getTime());
                break;
            case 1://一
                End.add(Calendar.DAY_OF_YEAR,6);//日期加10天
                startDate = new SimpleDateFormat("yyyy-MM-dd").format(Start.getTime());
                endDate = new SimpleDateFormat("yyyy-MM-dd").format(End.getTime());
                break;
            case 2://二
                Start.add(Calendar.DAY_OF_YEAR,-1);//日期加10天
                End.add(Calendar.DAY_OF_YEAR,5);//日期加10天
                startDate = new SimpleDateFormat("yyyy-MM-dd").format(Start.getTime());
                endDate = new SimpleDateFormat("yyyy-MM-dd").format(End.getTime());
                break;
            case 3://三
                Start.add(Calendar.DAY_OF_YEAR,-2);//日期加10天
                End.add(Calendar.DAY_OF_YEAR,4);//日期加10天
                startDate = new SimpleDateFormat("yyyy-MM-dd").format(Start.getTime());
                endDate = new SimpleDateFormat("yyyy-MM-dd").format(End.getTime());
                break;
            case 4://四
                Start.add(Calendar.DAY_OF_YEAR,-3);//日期加10天
                End.add(Calendar.DAY_OF_YEAR,3);//日期加10天
                startDate = new SimpleDateFormat("yyyy-MM-dd").format(Start.getTime());
                endDate = new SimpleDateFormat("yyyy-MM-dd").format(End.getTime());
                break;
            case 5://五
                Start.add(Calendar.DAY_OF_YEAR,-4);//日期加10天
                End.add(Calendar.DAY_OF_YEAR,2);//日期加10天
                startDate = new SimpleDateFormat("yyyy-MM-dd").format(Start.getTime());
                endDate = new SimpleDateFormat("yyyy-MM-dd").format(End.getTime());
                break;
            case 6://六
                Start.add(Calendar.DAY_OF_YEAR,-5);//日期加10天
                End.add(Calendar.DAY_OF_YEAR,1);//日期加10天
                startDate = new SimpleDateFormat("yyyy-MM-dd").format(Start.getTime());
                endDate = new SimpleDateFormat("yyyy-MM-dd").format(End.getTime());
                break;
        }
        range = startDate + "," + endDate;
        Log.e("todayWeek","range:"+range);
        return range;
    }
}
