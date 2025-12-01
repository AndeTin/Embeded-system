package com.example.lab04;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Draw extends View {
    private Paint paint;
    private Bitmap bitmap;

    public Draw(Context context) {
        super(context);
        init();
    }

    private void init() {
        // 建立Paint物件，只需初始化一次
        paint = new Paint();
        
        // 預先載入資源圖形，避免在 onDraw 中重複解碼
        Resources res = this.getResources();
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.draw);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        paint.setStyle(Paint.Style.FILL);
        // 在整個Canvas物件的背景填滿色彩
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        
        // 畫圓
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        canvas.drawCircle(160, 160, 80, paint);
        
        // 畫長方形
        paint.setColor(Color.BLUE);
        canvas.drawRect(500, 120, 670, 220, paint);
        
        // 畫出資源圖形
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 550 ,600 , paint);
        }
        
        // 畫出文字內容
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(30);
        canvas.drawText("畫布!", 550, 180, paint);
        
        // 畫出旋轉的文字
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);
        String str = "旋轉的文字!";
        
        // 儲存當前畫布狀態
        canvas.save();
        // 旋轉繪出文字內容
        canvas.rotate(-45, 250, 250);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(str, 700, 700, paint);
        // 恢復畫布狀態，避免影響後續繪製
        canvas.restore();
    }
}

