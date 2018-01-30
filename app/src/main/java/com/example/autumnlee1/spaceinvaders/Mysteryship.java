package com.example.autumnlee1.spaceinvaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import java.util.Random;

/**
 * Created by autumnlee1 on 4/14/16.
 */
public class Mysteryship {
    int width;
    int height;
    float x, y;
    float vx;
    int number1, number2;
    double distance;
    int alive;
    static int collide;

    public Mysteryship(int width, int height) {
        number1 = 10;// start with value other than 0
        number2 = 10;
        alive = 1;
        x = -1;
        y = height/7;
        this.width = width;
        this.height = height;
        vx = 7;
    }
    void draw(Canvas c) {
        if (number1 < 2 && number2 < 2 && alive != 0){ // only draw if not shot with cannon and random numbers are BOTH less than 1
            Paint d = new Paint();
            d.setColor(Color.YELLOW);
            c.drawCircle(x, y, 30, d);
        }
    }
    void update() {
        if (number1 < 2 && number2 < 2) { // number is 1,
            x = x + vx;
        }
        if (x < 0){ // initial phase, keep generating a random number until number equals 1
            Random mystery1 = new Random();
            number1 = 1 + mystery1.nextInt(15); // 2 sets of random numbers to generate less random numbers
            Random mystery2 = new Random();
            number2 = 1 + mystery2.nextInt(15);// decrease frequency of mystery spaceship flying
            alive = 1;
        }
        if (x > width && number1 < 2 && number2 < 2){ // reached the end after number = 1, generate space ship again under same conditions
            Random mystery1 = new Random();
            number1 = 1 + mystery1.nextInt(15);
            Random mystery2 = new Random();
            number2 = 1 + mystery2.nextInt(15);
            x= -1;
            alive = 1;
        }

    }
    boolean collision() {
        float cannonx = Cannonball.x;
        float cannony = Cannonball.y;
        int x1 = Math.round(cannonx);
        int y1 = Math.round(cannony);
        int x2 = Math.round(x);
        int y2 = Math.round(y);
        if (x1 + 40 > x2 && x1 < x2 + 40 && y1 + 40 > y2 && y1 < y2 + 40) {
            //Rectangles are overlapping, close but not necessarily touching
            distance = Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
            if (distance < 40) {
                //collided with cannon, alive status is 0
                alive = 0;
                collide = 1;

                return true;
            }
        }
        collide = 0; // no collision, continue moving
        return false;
    }
}
