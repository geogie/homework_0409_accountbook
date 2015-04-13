package com.geogie.homework_0409_accountbook.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.geogie.homework_0409_accountbook.R;
import com.geogie.homework_0409_accountbook.helper.MySQLiteOpenHelper;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created with Inte[i] IDEA.
 * User: renchaojun[FR]
 * Date:@date ${date}
 * Email:1063658094@qq.com
 */
public class ArcChartView extends View{

    public ArcChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public ArcChartView(Context context) {
        this(context, null);
    }
    //=========================================================================================
    //收入
    private int income=100;
    //支出
    private int outcome=100;
    private int total=200;
    /**
     * 收入支出数据改变更新函数
     * @param income
     * @param outcome
     */
    public void changeData(int income, int outcome){
        this.income=income;
        this.outcome = outcome;
        total=income+outcome;
        invalidate();
    }
    //======================================================================================
    private Paint arcPaint;
    private RectF arcRect;
    /**
     * 用于绘制用的
     */
    private Bitmap bitmap;
    /**
     * 初始化
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs){
        arcPaint = new Paint();
        arcPaint.setColor(Color.BLACK);
        //限制椭圆
        arcRect = new RectF(10, 10, 110, 110);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //清除内容
        canvas.drawColor(Color.WHITE);
        //画一个弧线
        //45到90度
        //弧线尺寸范围，弧线起始角度，所画角度数，是否连接中心点fase不连接，true形成饼图的效果,画笔
        //arcPaint.setStyle(Paint.Style.STROKE);
        //反锯齿
//        arcPaint.setAntiAlias(true);
//        canvas.drawArc(arcRect, 45, 45, true, arcPaint);

        //画绿色的半圆
        arcPaint.setColor(Color.GREEN);

        canvas.drawArc(arcRect, 0, 360 * income / total, true, arcPaint);
        //蓝色的半圆0e6ad8
        arcPaint.setColor(Color.parseColor("#FF0E6AB8"));
        canvas.drawArc(arcRect, 360*income/total, 360*outcome/total, true, arcPaint);
        //绘制小圆（中部白色填充）
        arcPaint.setColor(Color.WHITE);
        canvas.drawCircle(60, 60, 20, arcPaint);

        //进行图片的绘制,左上角画一张图片，按照原始的尺寸
        //drawBitmap有很多调用方法，Bitmap,float left,float top,paint可以设置null
        //canvas.drawBitmap(bitmap, 130, 10, null);
        //进行放大缩小的操作变换，如果第二个参数指定了，就是切出一小块图像区域进行绘制
        canvas.drawBitmap(bitmap, new Rect(20, 30, 60, 60), new Rect(130,10, 250, 100), null);
    }
}
