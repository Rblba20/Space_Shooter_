package com.example.space_shooter_;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;



public class Player {

    private Bitmap mBitmap;

    private int mX;
    private int mY;
    private int mSpeed;
    private int mMaxX;
    private int mMinX;
    private int mMaxY;
    private int mMinY;
    private int mMargin = 16;
    private boolean mIsSteerLeft, mIsSteerRight;
    private float mSteerSpeed;
    private Rect mCollision;
    private ArrayList<Laser> mLasers;
    private SoundPlayer mSoundPlayer;
    private Context mContext;
    private int mScreenSizeX, mScreenSizeY;

    private float mTouchX;

    static final String PREFS_NAME = "game_settings";
    static final String CONTROL_TYPE_KEY = "control_type";
    private int controlType;

    private boolean mIsMovingLeft = false;
    private boolean mIsMovingRight = false;

    public Player(Context context, int screenSizeX, int screenSizeY, SoundPlayer soundPlayer) {
        mScreenSizeX = screenSizeX;

        mScreenSizeY = screenSizeY;
        mContext = context;

        mSpeed = 1;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship_1_blue);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * 3/5, mBitmap.getHeight() * 3/5, false);

        mMaxX = screenSizeX - mBitmap.getWidth();
        mMaxY = screenSizeY - mBitmap.getHeight();
        mMinX = 0;
        mMinY = 0;

        mX = screenSizeX/2 - mBitmap.getWidth()/2;
        mY = screenSizeY - mBitmap.getHeight() - mMargin;

        mLasers = new ArrayList<>();
        mSoundPlayer = soundPlayer;

        mCollision = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

 /*   public boolean onTouch(View v, MotionEvent event) {
        if (controlType == 2) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    if (event.getX() < mScreenSizeX / 2) {
                        mIsMovingLeft = true;
                        mIsMovingRight = false;
                    } else {
                        mIsMovingLeft = false;
                        mIsMovingRight = true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mIsMovingLeft = false;
                    mIsMovingRight = false;
                    break;
            }
        }
        return true;
    }
*/
/*    public void moveLeft() {
        mX -= 10*mSteerSpeed;
        if (mX < mMinX) {
            mX = mMinX;
        }
    }

    public void moveRight() {
        mX += 10*mSteerSpeed;
        if (mX > mMaxX) {
            mX = mMaxX;
        }
    }


    public void startMovingLeft() {
        mIsMovingLeft = true;
    }

    public void startMovingRight() {
        mIsMovingRight = true;
    }

    public void stopMoving() {
        mIsMovingLeft = false;
        mIsMovingRight = false;
    }*/

    public void updateTouchX(float touchX) {
        mTouchX = touchX;
    }

    public int getControlType(){
        SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        controlType = prefs.getInt(CONTROL_TYPE_KEY, 1);
        return controlType;
    }



    public float getTouchX() {
        return mTouchX;
    }

    public void setTouchX(float touchX) {
        mTouchX = touchX;
    }


    public void update(){
        controlType = getControlType();
        if (controlType == 1) {
            // Первый вариант управления
            if (mIsSteerLeft) {
                mX -= 10 * mSteerSpeed;
                if (mX < mMinX) {
                    mX = mMinX;
                }
            } else if (mIsSteerRight) {
                mX += 10 * mSteerSpeed;
                if (mX > mMaxX) {
                    mX = mMaxX;
                }
            }
        } else if (controlType == 2) {
            // Второй вариант управления через нажатие на правую и левую половины экрана
/*            if (getTouchX() > (float) mScreenSizeX / 2) {
                mX += 10 * mSteerSpeed;
                if (mX > mMaxX) {
                    mX = mMaxX;
                }
            } else {
                mX -= 10 * mSteerSpeed;
                if (mX < mMinX) {
                    mX = mMinX;
                }
            }*/
            if (mIsSteerLeft) {
                mX -= 10 * mSteerSpeed;
                if (mX < mMinX) {
                    mX = mMinX;
                }
            } else if (mIsSteerRight) {
                mX += 10 * mSteerSpeed;
                if (mX > mMaxX) {
                    mX = mMaxX;
                }
            }
        }

        mCollision.left = mX;
        mCollision.top = mY;
        mCollision.right = mX + mBitmap.getWidth();
        mCollision.bottom = mY + mBitmap.getHeight();

        for (Laser l : mLasers) {
            l.update();
        }

        boolean deleting = true;
        while (deleting) {
            if (mLasers.size() != 0) {
                if (mLasers.get(0).getY() < 0) {
                    mLasers.remove(0);
                }
            }

            if (mLasers.size() == 0 || mLasers.get(0).getY() >= 0) {
                deleting = false;
            }
        }
    }

    public ArrayList<Laser> getLasers() {
        return mLasers;
    }

    public void fire(){
        mLasers.add(new Laser(mContext, mScreenSizeX, mScreenSizeY, mX, mY, mBitmap, false));
        mSoundPlayer.playLaser();
    }

    public Rect getCollision() {
        return mCollision;
    }

    public void steerRight(float speed){
        mIsSteerLeft = false;
        mIsSteerRight = true;
        mSteerSpeed = Math.abs(speed);
    }

    public void steerLeft(float speed){
        mIsSteerRight = false;
        mIsSteerLeft = true;
        mSteerSpeed = Math.abs(speed);
    }

    public void stay(){
        mIsSteerLeft = false;
        mIsSteerRight = false;
        mSteerSpeed = 0;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getSpeed() {
        return mSpeed;
    }
}