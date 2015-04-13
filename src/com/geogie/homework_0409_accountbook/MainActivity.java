package com.geogie.homework_0409_accountbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.geogie.homework_0409_accountbook.helper.MySQLiteOpenHelper;
import com.geogie.homework_0409_accountbook.view.ArcChartView;
import com.geogie.homework_0409_accountbook.view.ArcChartView1;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import java.util.List;
import java.util.Map;
public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initView();

        initSQLite(this);
    }
    public void clickButton(View view){
        switch (view.getId()){
            case R.id.button_main_first:
                arcChartView_first.changeData(100,200);
                break;
            case R.id.button_main_second:
                arcChartView_first.changeData(100,400);
                break;
        }
    }
    //=======================================================================
    @ViewInject(R.id.arcChartView_first)
    private ArcChartView arcChartView_first;
    @ViewInject(R.id.arcChartView_three)
    private ArcChartView1 arcChartView_three;
    /**
     * 布局控件初始化
     */
    private void initView(){
        ViewUtils.inject(this);
    }
    private MySQLiteOpenHelper dbHelper;
    /**
     * 初始化数据库
     */
    public void initSQLite(Context context){
        dbHelper = new MySQLiteOpenHelper(context);
        //收入和支出的
        String insertSql = "insert into tb_simple (income  , outcome) values(?, ?)";
        boolean flag = dbHelper.execData(insertSql, new Object[]{"100", "300"});
        if(flag){
            List<Map<String, Object>> currentList = dbHelper.selectList("select * from tb_simple",null);
            Log.d("=========================>", currentList.toString());
            int income =Integer.parseInt(currentList.get(0).get("income").toString());
            int outcome = Integer.parseInt(currentList.get(0).get("outcome").toString());
            arcChartView_first.changeData(income, outcome);
        }
        //吃穿住行用的
        String insertSql1 = "insert into tb_detail(eat  , clothes, home, play, use) values(?, ?, ?, ?, ?)";
        boolean flag1 = dbHelper.execData(insertSql1, new Object[]{"100", "300", "200","50","100"});
        if(flag1){
            List<Map<String, Object>> currentList = dbHelper.selectList("select * from tb_detail",null);
            Log.d("=========================>", currentList.toString());
            int[] data = new int[5];
            data[0] =Integer.parseInt(currentList.get(0).get("eat").toString());
            data[1] = Integer.parseInt(currentList.get(0).get("clothes").toString());
            data[2] = Integer.parseInt(currentList.get(0).get("home").toString());
            data[3] = Integer.parseInt(currentList.get(0).get("clothes").toString());
            data[4] = Integer.parseInt(currentList.get(0).get("use").toString());
            arcChartView_three.changeData(data);
        }
    }
    //=======================================================================

}
