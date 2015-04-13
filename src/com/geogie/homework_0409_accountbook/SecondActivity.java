package com.geogie.homework_0409_accountbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.geogie.homework_0409_accountbook.helper.MySQLiteOpenHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with Inte[i] IDEA.
 * User: renchaojun[FR]
 * Date:@date ${date}
 * Email:1063658094@qq.com
 */
public class SecondActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();
        initSQL();

        totalList = new ArrayList<Map<String, Object>>();
        adapter = new SimpleAdapter(this, totalList, R.layout.item_listview_second,
                new String[]{"income", "outcome"}, new int[]{R.id.textView_item_income
        ,R.id.textView_item_outcome});

        listView_second_infolist.setAdapter(adapter);
        listView_second_infolist.setEmptyView(textView_second_emptyinfo);
        registerForContextMenu(listView_second_infolist);
        reloadListView();
    }
    //=====================================================

    //收入
    @ViewInject(R.id.editText_second_income)
    private EditText editText_second_income;
    //支出
    @ViewInject(R.id.editText_second_outcome)
    private EditText getEditText_second_outcome;
    @ViewInject(R.id.listView_second_infolist)
    private ListView listView_second_infolist;
    @ViewInject(R.id.textView_second_emptyinfo)
    private TextView textView_second_emptyinfo;
    private List<Map<String, Object>> totalList;
    private SimpleAdapter adapter;
    /**
     * 控件的初始化
     */
    private void initView(){
        ViewUtils.inject(this);
    }
    private MySQLiteOpenHelper dbHelper;
    private void initSQL(){
        dbHelper = new MySQLiteOpenHelper(this);
    }
    /**
     * 更新listview中的数据
     */
    private void reloadListView(){
        totalList.clear();
        List<Map<String, Object>> currentList = dbHelper.
                selectList("select * from tb_simple", null);
        totalList.addAll(currentList);
        adapter.notifyDataSetChanged();
    }
    public void clickButton(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.button_second_submit:
                String income = editText_second_income.getText().toString();
                String outcome = getEditText_second_outcome.getText().toString();
                if (TextUtils.isEmpty(income)||TextUtils.isEmpty(outcome)){
                    Toast.makeText(this, "信息不能为空", Toast.LENGTH_SHORT).show();;
                }else{
                    String insertSql = "insert into tb_simple (income, outcome) values(?, ?)";
                    boolean flag = dbHelper.execData(insertSql, new Object[]{income, outcome});
                    if(flag){
                        reloadListView();
                    }
                }
                break;
            case R.id.button_second_turn:

                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button_second_detail:
                intent.setClass(this, ThreeActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 长按屏幕弹出对话框menu删除，更新
     * @param menu
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String income = totalList.get(info.position).get("income").toString();
        menu.setHeaderTitle(income);
        menu.setHeaderIcon(R.drawable.ic_launcher);
        getMenuInflater().inflate(R.menu.contextmenu_listview, menu);
    }
    /**
     * 选择删除或者更新后，再显示是否确定的对话框
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final String id = totalList.get(info.position).get("_id").toString();
        switch (item.getItemId()){
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle("提示：");
                builder.setMessage("确认删除吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean flag = dbHelper.execData("delete from tb_simple where _id=?",
                                new Object[]{id});
                        if(flag){
                            reloadListView();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                break;
            case R.id.action_update:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setIcon(R.drawable.ic_launcher);
                builder1.setTitle("更新数据");
                View view = getLayoutInflater().inflate(R.layout.dialog_update, null);
                final EditText editText_dialog_income = (EditText) view.findViewById(R.id.editText_dialog_income);
                final EditText editText_dialog_outcome = (EditText) view.findViewById(R.id.editText_dialog_outcome);
                List<Map<String, Object>> list = dbHelper.selectList(
                        "select * from tb_simple where _id=?",
                new String[]{id});
                editText_dialog_income.setText(list.get(0).get("income").toString());
                editText_dialog_outcome.setText(list.get(0).get("outcome").toString());
                builder1.setView(view);
                builder1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String income = editText_dialog_income.getText().toString();
                        String outcome = editText_dialog_outcome.getText().toString();
                        boolean flag = dbHelper.execData("update tb_simple set income=? , outcome=? where _id=?",
                                new String[]{income, outcome, id});
                        if (flag){
                            reloadListView();
                        }
                    }
                });
                builder1.setNegativeButton("取消", null);
                builder1.show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}