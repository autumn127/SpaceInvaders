package com.example.autumnlee1.spaceinvaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.logging.Logger;

public class Space {
    int width;
    int height;
    static float x,y;
    float movex;

    public Space(int width, int height) {
        x=width/2;
        y= height *85/100;
        this.width=width;
        this.height=height;
    }

    void draw(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        c.drawCircle(x, y, 60, p);
    }

    void move(float dx, float mx, float ux) {
        if (ux < width && ux > 0) { // if screen released then no movement
            movex = 0;
        }
        if ((dx < width && dx > width/2) || (mx < width && mx > width/2)) {
            //move right if screen pressed or dragged on right side
            movex= 15;
        }

        if ((dx > 0 && dx < width/2) || (mx > 0 && mx < width/2)){
            // move left if screen press or dragged on left side
            movex= -15;
        }
    }

    void update() {
        x = x + movex; // move ship to whatever movex is set to
        if (x > width){
            x = width;
        }
        if (x < 0){
            x = 0;
        }
    }
}


