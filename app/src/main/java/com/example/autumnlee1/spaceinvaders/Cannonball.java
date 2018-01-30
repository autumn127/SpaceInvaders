package com.example.autumnlee1.spaceinvaders;

/**
 * Created by autumnlee1 on 4/10/16.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;


import java.util.logging.Logger;

public class Cannonball{
    int width;
    int height;
    static float x,y;

    public Cannonball(int width, int height){
        this.width = width;
        this.height = height;
    }

    void drawcannon(Canvas c){
        if(Aliens.collide == 0 && Mysteryship.collide == 0){ // only CONTINUE to draw if no collision
            Paint d = new Paint();
            d.setColor(Color.BLUE);
            c.drawCircle(x, y, 10, d);
        }
        if(Aliens.collide == 1 || Mysteryship.collide == 1){ // collision occurred, set coordinates back to 0
            x = 0;
            y = 0;
        }
    }

    void shoot(){
        if (y < 0){ // new cannonball
            x = Space.x;
            y = height*85/100;
        }
    }

    void updatecannon(){
        y = y - 20;
    } // moves cannonball upwards
}
