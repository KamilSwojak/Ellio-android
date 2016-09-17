package kamilswojak.ellio.state;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import kamilswojak.ellio.Assets;
import kamilswojak.ellio.Painter;
import kamilswojak.ellio.util.Button;

public class MenuState extends State {
    private static final String TAG = "MenuState";

    private Button playButton;
    private Button scoreButton;

    @Override
    public void init() {
        System.out.println("MenuState.init");
        playButton = new Button(316, 227, 484, 286, Assets.playButton, Assets.playButtonDown);
        scoreButton = new Button(316, 300, 484, 359, Assets.scoreButton, Assets.scoreButtonDown);
    }

    @Override
    public void update(float deltaMillis) {

    }

    @Override
    public void render(Painter p) {
        p.drawImage(Assets.welcome, 0, 0);

        playButton.render(p);
        scoreButton.render(p);
    }

    @Override
    public boolean onTouch(MotionEvent event, int x, int y) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(x, y);
            scoreButton.onTouchDown(x, y);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (playButton.isPressed(x, y)) {
                setCurrentState(new PlayState());
            } else if (scoreButton.isPressed(x, y)) {
                setCurrentState(new ScoreState());
            } else {
                playButton.cancel();
                scoreButton.cancel();
            }
        }
        return true;
    }
}
