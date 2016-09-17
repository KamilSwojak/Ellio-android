package kamilswojak.ellio.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import kamilswojak.ellio.GameMainActivity;
import kamilswojak.ellio.Painter;

public class ScoreState extends State {

    private String highScore;

    @Override
    public void init() {

        highScore = GameMainActivity.getHighScore() + "";
    }

    @Override
    public void update(float deltaMillis) {

    }

    public void render(Painter p) {
        p.setColor(Color.rgb(53, 156, 253));
        p.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        p.setColor(Color.WHITE);
        p.setFont(Typeface.DEFAULT_BOLD, 50);
        p.drawString("THE ALL-TIME HIGH SCORE", 120, 175);
        p.setFont(Typeface.DEFAULT_BOLD, 75);
        p.drawString(highScore, 370, 260);
        p.setFont(Typeface.DEFAULT_BOLD, 50);
        p.drawString("Touch the screen.", 220, 350);
    }

    @Override
    public boolean onTouch(MotionEvent event, int scaledX, int scaledY) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            setCurrentState(new MenuState());
        }
        return true;
    }

}
