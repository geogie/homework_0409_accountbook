package com.geogie.homework_0409_accountbook;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.geogie.homework_0409_accountbook.helper.MySQLiteOpenHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

/**
 * Created with Inte[i] IDEA.
 * User: renchaojun[FR]
 * Date:@date ${date}
 * Email:1063658094@qq.com
 */
public class ThreeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        ViewUtils.inject(this);
        dbHelper = new MySQLiteOpenHelper(this);
        adapter = new SimpleAdapter(this, totalList, R.layout.item_listview_three ,
                new String[]{"eat", "clothes", "home", "play", "use"},
                new int[]{R.id.textView_item_eat, R.id.textView_item_clothes, R.id.textView_item_home,
                R.id.textView_item_play, R.id.textView_item_use});
        listView_three_infolist.setAdapter(adapter);
        listView_three_infolist.setEmptyView(textView_three_emptyinfo);
        reloadListView();
    }
    //初始化控件
//    @ViewInject(R.id.editText_three_eat)
//    private EditText editText_three_eat;
//    @ViewInject(R.id.editText_three_clothes)
//    private EditText editText_three_clothes;
//    @ViewInject(R.id.editText_three_home)
//    private EditText editText_three_home;
//    @ViewInject(R.id.editText_three_play)
//    private EditText editText_three_play;
//    @ViewInject(R.id.editText_three_use)
//    private EditText editText_three_use;
    @ViewInject(R.id.listView_three_infolist)
    private ListView listView_three_infolist;
    @ViewInject(R.id.textView_three_emptyinfo)
    private TextView textView_three_emptyinfo;

    //声明变量
    private MySQLiteOpenHelper dbHelper;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> totalList;

    /**
     * 更新数据
     */
    private void reloadListView(){
        totalList.clear();
        List<Map<String, Object>> currentlist = dbHelper.selectList("select * from tb_detail ", null);
        Log.d("---------------->", currentlist.toString());
        totalList.addAll(currentlist);
        adapter.notifyDataSetChanged();
    }
}