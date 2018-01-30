package com.example.autumnlee1.spaceinvaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.logging.Logger;

public class Aliens {
    int width;
    int height;
    float vx, vy, ax;
    int i, j;
    int alive;
    float x, y;
    double distance;
    int edgenumber;
    int right, left;
    static int collide;
    static int dead;
    Aliens aliens[][] = new Aliens[5][4];

    public Aliens(int width, int height) {
        right = 4;
        left = 0;
        this.width = width;
        this.height = height;
        vx = 5;
        vy = 18;
        x = width / 4;
        y = height / 4;
    }

    public void makeAliens() {
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 5; i++) { //array of Aliens with their properties
                aliens[i][j] = new Aliens(width, height);
                aliens[i][j].x = x;
                aliens[i][j].y = y;
                aliens[i][j].alive = 1;
                aliens[i][j].edgenumber = i; // column number
                x = x + 150;
            }
            x = width / 4;
            y = y + 150;
        }
    }

    void draw(Canvas c) {
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 5; i++) {
                if (aliens[i][j].alive != 0) { // only draws aliens if they are alive
                    Paint g = new Paint();
                    g.setColor(Color.GREEN);
                    c.drawCircle(aliens[i][j].x, aliens[i][j].y, 40, g);
                }
            }
        }
    }

    void update() {
        if (aliens[right][0].alive == 0 && aliens[right][1].alive == 0 && aliens[right][2].alive == 0 && aliens[right][3].alive == 0) {
            // checks if right most row is destroyed and therefore the next one after it is the one that hits the wall
            right = right - 1;
        }

        if (aliens[left][0].alive == 0 && aliens[left][1].alive == 0 && aliens[left][2].alive == 0 && aliens[left][3].alive == 0) {
            // checks if left most row is destroyed and therefore the next one after it is the one that hits the wall
            left = left + 1;
        }
        vy = 0;
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 5; i++) {
                if (aliens[i][j].x > width && aliens[i][j].edgenumber == right) {// checks when column hits the wall, and then changes direction and y position
                    vx = -5;  // speed alien travels at
                    ax = -ax; // for when rounds are complete, alien speed gets faster
                    vy = 8;
                }
                if (aliens[i][j].x < 0 && aliens[i][j].edgenumber == left) {
                    vx = 5;
                    ax = -ax;
                    vy = 8;
                }
            }
            for (j = 0; j < 4; j++) {
                for (i = 0; i < 5; i++) {
                    aliens[i][j].x = aliens[i][j].x + vx + ax; // updates x position
                    aliens[i][j].y = aliens[i][j].y + vy; // updates y position
                }
            }
        }
        boolean alldead = true;
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 5; i++) {
                if (aliens[i][j].alive != 0) {
                    alldead = false; // round is not over, some aliens still alive
                    break;
                }
            }
        }
        if (alldead) { // aliens are dead, make a new set of aliens that move faster
            x = width / 4;
            y = height / 4;
            right = 4;
            left = 0;
            ax = ax + 5;
            for (j = 0; j < 4; j++) {
                for (i = 0; i < 5; i++) {
                    makeAliens();

               }
            }
        }
    }

    boolean collision() {
        float cannonx = Cannonball.x;
        float cannony = Cannonball.y;
        int x1 = Math.round(cannonx); // make ints from x and yfloats
        int y1 = Math.round(cannony);
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 5; i++) {
                int x2 = Math.round(aliens[i][j].x);// make ints from x and yfloats
                int y2 = Math.round(aliens[i][j].y);
                if (x1 + 50 > x2 && x1 < x2 + 50 && y1 + 50 > y2 && y1 < y2 + 50 && aliens[i][j].alive == 1) {
                    //Rectangles are overlapping, close but not necessarily touching
                    distance = Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
                    if (distance < 50) {
                        //alien has collided with bullet and status is no longer alive
                        aliens[i][j].alive = 0;
                        collide = 1;
                        return true;
                    }
                }
            }
        }
        collide = 0; // no collision alien still alive
        return false;
    }

    void gameover() {
        float spacex = Space.x;
        float spacey = Space.y;
        int x1 = Math.round(spacex);
        int y1 = Math.round(spacey);
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 5; i++) {
                int x2 = Math.round(aliens[i][j].x);
                int y2 = Math.round(aliens[i][j].y);
                if (x1 + 100 > x2 && x1 < x2 + 100 && y1 + 100 > y2 && y1 < y2 + 100 && aliens[i][j].alive == 1) {
                    //Rectangles are overlapping, close but not necessarily touching
                    distance = Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
                    if (distance < 100) {
                        //aliens have collided with player, game is over
                        System.exit(0);  // game will quit and app will close when player loses
                        dead = 1;
                    }
                }
            }
        }
        dead = 0;
    }
}


