package com.geogie.homework_0409_accountbook.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.geogie.homework_0409_accountbook.R;

/**
 * Created with Inte[i] IDEA.
 * User: renchaojun[FR]
 * Date:@date ${date}
 * Email:1063658094@qq.com
 */
public class ArcChartView1 extends View{


    public ArcChartView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ArcChartView1(Context context) {
        this(context, null);
    }
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
    //========================================================
    String[] color= new String[]{"#FF0FF000","#FF0E6AB8","#FF0000FF","#FF046A32","#FFFF0000"};
    //五中数据吃穿住行用
    int[] data = new int[]{100,100,300,400,500};
    int total=1400;
    public void changeData(int[] data){
        this.data = data;
        total = 0;
        for (int i = 0; i < data.length; i++) {
            total+=data[i];
        }
    }
    //========================================================

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
        int tem=0;
        for (int i = 0; i < 5; i++) {
            arcPaint.setColor(Color.parseColor(color[i]));
            canvas.drawArc(arcRect, 360*tem/total, 360*data[i]/total, true, arcPaint);
            tem+=data[i];
        }
//        //arcPaint.setColor(Color.GREEN);#0F0
//        arcPaint.setColor(Color.parseColor("#FF0FF000"));
//        canvas.drawArc(arcRect, 0, 360*1/5, true, arcPaint);
//        //蓝色的半圆0e6ad8
//        arcPaint.setColor(Color.parseColor("#FF0E6AB8"));
//        canvas.drawArc(arcRect, 360*1/5, 360*1/5, true, arcPaint);
//
//        //arcPaint.setColor(Color.BLUE);
//        arcPaint.setColor(Color.parseColor("#FF0000FF"));
//        canvas.drawArc(arcRect, 360*2/5, 360*1/5, true, arcPaint);
//        //蓝色的半圆0e6ad8
//        arcPaint.setColor(Color.parseColor("#FF046A32"));
//        canvas.drawArc(arcRect, 360*3/5, 360*1/5, true, arcPaint);
//
//        //arcPaint.setColor(Color.RED);
//        arcPaint.setColor(Color.parseColor("#FFFF0000"));
//        canvas.drawArc(arcRect, 360*4/5, 360*1/5, true, arcPaint);

        arcPaint.setColor(Color.WHITE);
        canvas.drawCircle(60, 60, 20, arcPaint);

        //进行图片的绘制,左上角画一张图片，按照原始的尺寸
        //drawBitmap有很多调用方法，Bitmap,float left,float top,paint可以设置null
        //canvas.drawBitmap(bitmap, 130, 10, null);
        //进行放大缩小的操作变换，如果第二个参数指定了，就是切出一小块图像区域进行绘制
        canvas.drawBitmap(bitmap, new Rect(20, 30, 60, 60), new Rect(130,10, 250, 100), null);
    }
}
