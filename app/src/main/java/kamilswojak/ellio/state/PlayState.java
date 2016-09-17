package kamilswojak.ellio.state;


import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.ArrayList;

import kamilswojak.ellio.Assets;
import kamilswojak.ellio.GameMainActivity;
import kamilswojak.ellio.Painter;
import kamilswojak.ellio.model.Block;
import kamilswojak.ellio.model.Cloud;
import kamilswojak.ellio.model.Player;

public class PlayState extends State {
    private static final String TAG = "PlayState";

    private Player player;
    private ArrayList<Block> blocks;
    private ArrayList<Cloud> clouds;

    private int playerScore = 0;

    private static final int BLOCK_HEIGHT = 50;
    private static final int BLOCK_WIDTH = 20;
    private int blockSpeed = -200;

    public static final int PLAYER_WIDTH = 66;
    public static final int PLAYER_HEIGHT = 92;

    private boolean isPaused = false;
    private String pausedString = "Game paused. Tap to resume.";

    private float recentTouchY;

    @Override
    public void init() {
        System.out.println("PlayState.init");
        player = new Player(160, GameMainActivity.GAME_HEIGHT - 45 - PLAYER_WIDTH, PLAYER_WIDTH, PLAYER_HEIGHT);

        clouds = new ArrayList<>();
        Cloud cloud1 = new Cloud(100, 100, 1);
        Cloud cloud2 = new Cloud(500, 50, 2);
        Cloud cloud3 = new Cloud(750, 50, 2);
        Cloud cloud4 = new Cloud(300, 50, 1);

        clouds.add(cloud1);
        clouds.add(cloud2);
        clouds.add(cloud3);
        clouds.add(cloud4);

        blocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Block b = new Block(i * 200, GameMainActivity.GAME_HEIGHT - 95, BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks.add(b);
        }

//        Assets.playMusic("bgmusic.mp3", true);
    }

    @Override
    public void update(float deltaMillis) {
        if (isPaused) return;

        if (!player.isAlive()) {
            Assets.stopMusic();
            setCurrentState(new GameOverState(playerScore / 100));
        }
        playerScore += 1;

        if (playerScore % 500 == 0 && blockSpeed > -280) {
            blockSpeed -= 10;
        }

        Assets.runAnimation.update(deltaMillis);
        player.update(deltaMillis);

        for (Cloud cloud : clouds) {
            cloud.update(deltaMillis);
        }
        for (Block block : blocks) {
            block.update(deltaMillis, blockSpeed);

            if (block.isVisible()) {
                if (player.isDucked() && player.getDuckBounds().intersect(block.getBounds())) {
                    block.onCollide(player);
                } else if (!player.isDucked() && player.getRunningBounds().intersect(block.getBounds())) {
                    block.onCollide(player);
                }
            }
        }
    }

    public void render(Painter p) {
        p.setColor(Assets.skyBlue);
        p.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);

        player.render(p);

        for (Block block : blocks) {
            if (block.isVisible()) block.render(p);
        }

        for (Cloud cloud : clouds) {
            cloud.render(p);
        }
        p.drawImage(Assets.grass, 0, 405);
        renderSun(p);

        p.setColor(Color.GRAY);
        p.setFont(Typeface.SANS_SERIF, 25);
        p.drawString(playerScore / 100 + "", 20, 30);

        if (isPaused) {
            p.setColor(Color.rgb(153, 0, 0));
            p.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
            p.drawString(pausedString, 253, 240);
        }
    }

    private void renderSun(Painter p) {
        p.setColor(Color.rgb(255, 200, 0));
        p.fillOval(715, -85, 170, 170);
        p.setColor(Color.YELLOW);
        p.fillOval(725, -75, 150, 150);
    }

    @Override
    public boolean onTouch(MotionEvent event, int scaledX, int scaledY) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            recentTouchY = scaledY;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (scaledY - recentTouchY < -50) {
                player.jump();
            } else if (scaledY - recentTouchY > 50) {
                player.duck();
            }
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPaused = false;
        Assets.onResume();
//        Assets.playMusic("bgmusic.mp3", true);
    }

    @Override
    public void onPause() {
        super.onPause();
        isPaused = true;
        Assets.onPause();
    }
}
