package kamilswojak.ellio.util;


import android.view.MotionEvent;
import android.view.View;

import kamilswojak.ellio.GameMainActivity;
import kamilswojak.ellio.state.State;

public class InputHandler implements View.OnTouchListener {

    private State currentState;

    public void setCurrentState(State state) {
        currentState = state;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int scaledX = (int) (motionEvent.getX() / view.getWidth() * GameMainActivity.GAME_WIDTH);
        int scaledY = (int) (motionEvent.getY() / view.getHeight() * GameMainActivity.GAME_HEIGHT);
        return currentState.onTouch(motionEvent, scaledX, scaledY);
    }
}
