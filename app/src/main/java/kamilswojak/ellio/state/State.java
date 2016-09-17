package kamilswojak.ellio.state;

import android.view.MotionEvent;

import kamilswojak.ellio.GameMainActivity;
import kamilswojak.ellio.Painter;


public abstract class State {

    public abstract void init();

    public abstract void update(float deltaMillis);

    public abstract void render(Painter p);

    public abstract boolean onTouch(MotionEvent event, int scaledX, int scaledY);

    public void setCurrentState(State state) {
        GameMainActivity.sGame.setCurrentState(state);
    }

    public void onResume() {
    }

    public void onPause() {
    }

}
