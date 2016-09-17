package kamilswojak.ellio.model;


import android.graphics.Rect;

import kamilswojak.ellio.Assets;
import kamilswojak.ellio.Painter;
import kamilswojak.ellio.util.RandomNumberGenerator;

public class Block extends GameObject {

    private float x, y;
    private int width, height;

    private Rect bounds;

    private boolean isVisible;

    public static final int UPPER_Y = 275;
    public static final int LOWER_Y = 355;


    public Block(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rect();
        isVisible = false;
    }

    public void update(float delta, float velX) {
        x += velX * delta;
        updateRects();
        if (x <= -50) {
            reset();
        }
    }

    private void updateRects() {
        bounds.set((int) x, (int) y, (int) x + width, (int) y + height);
    }

    private void reset() {
        isVisible = true;

        if (RandomNumberGenerator.getRandom(3) == 0) {
            y = UPPER_Y;
        } else {
            y = LOWER_Y;
        }

        x += 1000;
        updateRects();
    }

    public void onCollide(Player p) {
        isVisible = false;
        p.pushBack(30);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rect getBounds() {
        return bounds;
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void render(Painter p) {
        p.drawImage(Assets.block, ((int) x), ((int) y));
    }
}
