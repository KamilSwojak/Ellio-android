package kamilswojak.ellio.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import kamilswojak.ellio.GameMainActivity;
import kamilswojak.ellio.Painter;

public class GameOverState extends State {

    private String score;
    private String gameOverMessage = "GAME OVER";

    public GameOverState(int score) {
        this.score = score + "";
        if (score > GameMainActivity.getHighScore()) {
            GameMainActivity.setHighScore(score);
            gameOverMessage = "HIGH SCORE";
        }
    }

    @Override
    public void init() {
        GameMainActivity.sGame.saveScore(score);
    }

    @Override
    public void update(float deltaMillis) {

    }

    public void render(Painter p) {
        p.setColor(Color.rgb(255, 145, 0));
        p.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        p.setColor(Color.DKGRAY);
        p.setFont(Typeface.DEFAULT_BOLD, 50);
        p.drawString(gameOverMessage, 257, 175);
        p.drawString(score, 385, 250);
        p.drawString("Touch the screen.", 240, 350);
    }

    @Override
    public boolean onTouch(MotionEvent event, int scaledX, int scaledY) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            setCurrentState(new MenuState());
        }
        return true;
    }

}
