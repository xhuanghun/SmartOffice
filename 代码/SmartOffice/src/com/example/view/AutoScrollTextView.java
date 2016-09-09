package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

/*
 * 文字可以左右滚动
 * */

public class AutoScrollTextView extends TextView{
	/** 文字长度 */ 
    private float textLength = 0f; 
    /** 滚动条长度 */ 
    private float viewWidth = 0f; 
    /** 文本x轴 的坐标 */ 
    private float tx = 0f; 
    /** 文本Y轴的坐标 */ 
    private float ty = 0f; 
    /** 文本当前长度 */ 
    private float temp_tx1 = 0.0f; 
    /** 文本当前变换的长度 */ 
    private float temp_tx2 = 0x0f; 
    /** 文本滚动开关 */ 
    private boolean isStarting = false; 
    /** 画笔对象 */ 
    private Paint paint = null; 
    /** 显示的文字 */ 
    private String text = ""; 
   
    public AutoScrollTextView(Context context, AttributeSet attrs) { 
        super(context, attrs); 
    } 
   
    /** 
     * 初始化自动滚动条,每次改变文字内容时，都需要重新初始化一次 
     *  
     * @param windowManager 
     *            获取屏幕 
     * @param text 
     *            显示的内容 
     */ 
    public void initScrollTextView(WindowManager windowManager, String text) { 
       
        paint = this.getPaint(); 
        
        this.text = text; 
   
        textLength = paint.measureText(text);
        viewWidth = this.getWidth();
        if (viewWidth == 0) { 
            if (windowManager != null) { 
                // 获取当前屏幕的属性 
                Display display = windowManager.getDefaultDisplay(); 
                viewWidth = display.getWidth();
   
            } 
        } 
        tx = textLength; 
        temp_tx1 = viewWidth + textLength; 
        temp_tx2 = viewWidth + textLength * 2;
       
        ty = this.getTextSize() + this.getPaddingTop(); 
    } 
   
    /** 
     * 开始滚动 
     */ 
    public void starScroll() { 
        // 开始滚动 
        isStarting = true; 
        this.invalidate();// 刷新屏幕 
    } 
   
    /** 
     * 停止方法,停止滚动 
     */ 
    public void stopScroll() { 
        // 停止滚动 
        isStarting = false; 
        this.invalidate();// 刷新屏幕 
    } 
   
    @Override 
    protected void onDraw(Canvas canvas) { 
        if (isStarting) { 
            // A-Alpha透明度/R-Read红色/g-Green绿色/b-Blue蓝色 
            paint.setARGB(255, 200, 200, 200); 
            canvas.drawText(text, temp_tx1 - tx, ty, paint); 
            tx += 0.4; 
            // 当文字滚动到屏幕的最左边 
            if (tx >= temp_tx2) { 
                // 把文字设置到最右边开始 
                tx = temp_tx1 - viewWidth; 
            } 
            this.invalidate();// 刷新屏幕 
        } 
        super.onDraw(canvas); 
    } 
}
