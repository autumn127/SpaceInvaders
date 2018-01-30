package com.example.autumnlee1.spaceinvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

// This is the ‘‘game engine ’ ’.
public class SpaceView extends SurfaceView
        implements SurfaceHolder. Callback {
    public SpaceView(Context context) {
        super(context);
        // Notify the SurfaceHolder that you’d like to receive
        // SurfaceHolder callbacks .
        getHolder().addCallback(this);
        setFocusable(true);
        // Initialize game state variables. DON'T RENDER THE GAME YET
    }

    Space s;
    SpaceThread st;
    Cannonball b;
    Aliens a;
    Mysteryship m;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Construct game initial state
        // Launch animator thread.
        s = new Space(getWidth(), getHeight());
        b = new Cannonball(getWidth(),getHeight());
        a = new Aliens(getWidth(), getHeight());
        m = new Mysteryship(getWidth(),getHeight());
        st = new SpaceThread(this);
        st.start();
        a.makeAliens();
    }

    public void draw(Canvas c) {
            c.drawColor(Color.BLACK);
            s.draw(c); // draw player
            a.draw(c); // draw aliens
            b.drawcannon(c); // draw cannonfire
            m.draw(c); // draw mysteryship
            s.update(); // update player position
            b.updatecannon(); // update bullet position
            a.update(); // update aliens position
            m.update(); // update mystery ship position
            a.collision(); // check if alien and cannon collision occurred
            m.collision(); // check if mystery ship and cannon collision occurred
            a.gameover(); // check if aliens hit player
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height) {
        // Respond to surface changes , e.g. , aspect ration changes.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // The cleanest way to stop a thread is by interrupting it.
        // SpaceThread regularly checks its interrupt flag. st.interrupt();
        st.interrupt();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // Update game state in response to events:
        // touch -down, touch -up, and touch-move.
        int height = getHeight();
        int width = getWidth();
        float dx=0;
        float dy=height/2;
        float ux=0;
        float uy=0;
        float mx=0;
        float my=height/2;


        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: //pressing on screen
                dx = e.getX();
                dy = e.getY();
                break;
            case MotionEvent.ACTION_UP: //releasing on screen
                ux = e.getX();
                uy = e.getY();
                break;
            case MotionEvent.ACTION_MOVE: // dragging on screen
                mx = e.getX();
                my = e.getY();
                break;
        }

        if (dy < height/2 || my < height/2){ // if current and pressing is the upper half of screen
            b.shoot(); // shoot cannon
            s.move(dx,mx,ux); // CONTINUES TO UPDATE PLAYER POSITION, WILL ONLY STOP MOVING WHEN RELEASING SCREEN
        }
        else{
            s.move(dx, mx, ux); // Only updates player position
        }
        return true;
    }

}
