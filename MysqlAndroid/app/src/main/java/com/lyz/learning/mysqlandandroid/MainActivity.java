package com.lyz.learning.mysqlandandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.SQLException;

public class MainActivity extends Activity {

    private static final String REMOTE_IP = "182.254.140.181:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/hzy";
    private static final String USER = "hzy777";
    private static final String PASSWORD = "hzy777";

    private Connection conn;
    private Button onConn;
    private Button onInsert;
    private Button onDelete;
    private Button onUpdate;
    private Button onQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onConn = (Button) findViewById(R.id.onConn);
        onInsert = (Button) findViewById(R.id.onInsert);
        onDelete = (Button) findViewById(R.id.onDelete);
        onUpdate = (Button) findViewById(R.id.onUpdate);
        onQuery = (Button) findViewById(R.id.onQuery);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
            } finally {
                conn = null;
            }
        }
    }

    public void onConn(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = Util.openConnection(URL, USER, PASSWORD);
                Log.i("onConn", "onConn");
            }
        }).start();
    }

    public void onInsert(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "insert into mytable values('xiaoming')";
                Util.execSQL(conn, sql);
                Log.i("onInsert", "onInsert");
            }
        }).start();
    }


    public void onDelete(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "delete from mytable where name='hanmeimei'";
                Util.execSQL(conn, sql);
                Log.i("onDelete", "onDelete");
            }
        }).start();
    }

    public void onUpdate(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "update mytable set name='lilei' where name='liyanzhen'";
                Util.execSQL(conn, sql);
                Log.i("onUpdate", "onUpdate");
            }
        }).start();
    }

    public void onQuery(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Util.query(conn, "select * from mytable");
                Log.i("onQuery", "onQuery");
            }
        }).start();
    }
}