package kamilswojak.ellio;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;
import java.io.InputStream;

import kamilswojak.ellio.animation.Animation;
import kamilswojak.ellio.animation.Frame;

public class Assets {
    private static SoundPool soundPool;
    public static Bitmap welcome;

    public static Bitmap block;
    public static Bitmap cloud1;
    public static Bitmap cloud2;
    public static Bitmap duck;
    public static Bitmap grass;
    public static Bitmap jump;
    public static Bitmap run1;
    public static Bitmap run2;
    public static Bitmap run3;
    public static Bitmap run4;
    public static Bitmap run5;
    public static Bitmap scoreButton;
    public static Bitmap scoreButtonDown;
    public static Bitmap playButton;
    public static Bitmap playButtonDown;

    public static int hit;
    public static int onJump;

    public static Animation runAnimation;

    public static int skyBlue;

    private static MediaPlayer mediaPlayer;

    public static void load() {
        block = loadBitmap("block.png", false);
        cloud1 = loadBitmap("cloud1.png", false);
        cloud2 = loadBitmap("cloud2.png", false);
        duck = loadBitmap("duck.png", false);
        grass = loadBitmap("grass.png", false);
        jump = loadBitmap("jump.png", false);
        run1 = loadBitmap("run_anim1.png", false);
        run2 = loadBitmap("run_anim2.png", false);
        run3 = loadBitmap("run_anim3.png", false);
        run4 = loadBitmap("run_anim4.png", false);
        run5 = loadBitmap("run_anim5.png", false);
        scoreButton = loadBitmap("score_button.png", false);
        scoreButtonDown = loadBitmap("score_button_down.png", false);
        playButton = loadBitmap("start_button.png", false);
        playButtonDown = loadBitmap("start_button_down.png", false);
        welcome = loadBitmap("welcome.png", false);

        Frame f1 = new Frame(run1, .1f);
        Frame f2 = new Frame(run2, .1f);
        Frame f3 = new Frame(run3, .1f);
        Frame f4 = new Frame(run4, .1f);
        Frame f5 = new Frame(run5, .1f);

        runAnimation = new Animation(f1, f2, f3, f4, f5);

        skyBlue = Color.rgb(208, 244, 247);

    }

    private static Bitmap loadBitmap(String path, boolean transparency) {
        InputStream inputStream = null;

        try {
            inputStream = GameMainActivity.assetManager.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BitmapFactory.Options options = new BitmapFactory.Options();

        if (transparency) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        } else {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    private static Bitmap loadBitmap(String path, boolean transparency, boolean shouldSubsample) {
        InputStream inputStream = null;

        try {
            inputStream = GameMainActivity.assetManager.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BitmapFactory.Options options = new BitmapFactory.Options();

        if (shouldSubsample) {
            options.inSampleSize = 2;
        }

        if (transparency) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        } else {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        return BitmapFactory.decodeStream(inputStream, null, options);

    }

    private static int loadSound(String path) {
        int soundId = 0;
        if (soundPool == null) {
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
        }

        try {
            soundId = soundPool.load(GameMainActivity.assetManager.openFd(path), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return soundId;
    }

    public static void playSound(int soundId) {
        if (soundPool != null) {
            soundPool.play(soundId, 1, 1, 1, 0, 1);
        }
    }

    public static void playMusic(String filename, boolean looping) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        try {
            AssetFileDescriptor afd = GameMainActivity.assetManager.openFd(filename);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(looping);
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static void onPause() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }

        stopMusic();
    }


    public static void onResume() {
        hit = loadSound("hit.wav");
        onJump = loadSound("onjump.wav");
    }

}
