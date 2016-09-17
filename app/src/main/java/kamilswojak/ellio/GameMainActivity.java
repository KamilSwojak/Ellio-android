package kamilswojak.ellio;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.WindowManager;

public class GameMainActivity extends Activity {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 450;
    public static GameView sGame;
    public static AssetManager assetManager;

    private static SharedPreferences preferences;
    private static final String KEY_HIGH_SCORE = "high-score";
    private static final String KEY_MUSIC_ON = "music-on";
    private static int highScore = 0;
    public static boolean musicOn = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getPreferences(Activity.MODE_PRIVATE);
        highScore = retrieveHighScore();
        assetManager = getAssets();
        sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
        setContentView(sGame);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Assets.onPause();
        sGame.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Assets.onResume();
        sGame.onResume();
    }

    public static void setHighScore(int score) {
        preferences.edit().putInt(KEY_HIGH_SCORE, score).apply();
    }

    public static int getHighScore() {
        return highScore;
    }

    private int retrieveHighScore() {
        return preferences.getInt(KEY_HIGH_SCORE, 0);
    }

    private boolean retrieveIsMusicOn() {
        return preferences.getBoolean(KEY_MUSIC_ON, true);
    }

}
