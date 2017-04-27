package com.jingchujie.jdbctestinas;

import java.sql.Connection;
import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    //    private static final String REMOTE_IP = "192.168.1.7";
//    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/zw";
//    private static final String USER = "root";
//    private static final String PASSWORD = "root";
    private static final String REMOTE_IP = "localhost:33104";//这里是映射地址，可以随意写，不是服务器地址
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/mobile";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn;

    public void onConnSsh(View view) {

        new Thread() {
            public void run() {
                Log.e("============", "预备连接服务器");
                Util.go();
            }
        }.start();
    }

    public void onConn(View view) {

        new Thread() {
            public void run() {
                Log.e("============", "预备连接数据库");
                conn = Util.openConnection(URL, USER, PASSWORD);
            }
        }.start();
    }

    public void onInsert(View view) {
        new Thread() {
            public void run() {
                Log.e("============", "预备插入");
                String sql = "insert into users values(3, 'yinhongbo', 'yinhongbo')";
                Util.execSQL(conn, sql);
            }
        }.start();
    }

    public void onDelete(View view) {
        String sql = "delete from mytable where name='mark'";
        Util.execSQL(conn, sql);
    }

    public void onUpdate(View view) {
        String sql = "update mytable set name='lilei' where name='hanmeimei'";
        Util.execSQL(conn, sql);
    }

    public void onQuery(View view) {
        new Thread() {
            public void run() {
                Log.e("============", "预备查询");
                Util.query(conn, "select * from users");
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
