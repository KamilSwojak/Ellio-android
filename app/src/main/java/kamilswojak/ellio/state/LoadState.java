package kamilswojak.ellio.state;

import android.util.Log;
import android.view.MotionEvent;

import kamilswojak.ellio.Assets;
import kamilswojak.ellio.Painter;

public class LoadState extends State {
    private static final String TAG = "LoadState";

    @Override
    public void init() {
        Assets.load();
        Log.d(TAG, "init: assets loaded.");
    }

    @Override
    public void update(float deltaMillis) {
        setCurrentState(new MenuState());
    }

    @Override
    public void render(Painter p) {

    }

    @Override
    public boolean onTouch(MotionEvent event, int scaledX, int scaledY) {
        return false;
    }
}
