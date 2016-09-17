package kamilswojak.ellio.model;

import android.graphics.Rect;

import kamilswojak.ellio.Assets;
import kamilswojak.ellio.Painter;

public class Player extends GameObject {

    private float x, y;
    private int width, height, yVelocity;

    private boolean isAlive;
    private boolean isDucked;

    private float duckDuration = .6f;
    private Rect runningBounds, duckBounds, ground;

    private static final int JUMP_VELOCITY = -600;
    private static final int ACCEL_GRAVITY = 1800;

    public Player(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        ground = new Rect(0, 405, 800, 405 + 45);
        runningBounds = new Rect();
        duckBounds = new Rect();
        isAlive = true;
        isDucked = false;
    }

    public void update(float delta) {
        if (duckDuration > 0 && isDucked) {
            duckDuration -= delta;
        } else {
            isDucked = false;
            duckDuration = .6f;
        }
        if (!isGrounded()) {
            yVelocity += ACCEL_GRAVITY * delta;
        } else {
            y = 406 - height;
            yVelocity = 0;
        }

        y += yVelocity * delta;
        updateRects();
    }

    private void updateRects() {
        int xInt = (int) x;
        int yInt = (int) y;
        runningBounds.set(xInt + 10, yInt, xInt + width - 20, yInt + height);
        duckBounds.set(xInt, yInt + 20, xInt + width, yInt + 20 + height - 20);
    }

    public void jump() {
        if (isGrounded()) {
            Assets.playSound(Assets.onJump);
            isDucked = false;
            duckDuration = .6f;
            y -= 10;
            yVelocity = JUMP_VELOCITY;
            updateRects();
        }
    }

    public void duck() {
        if (isGrounded()) isDucked = true;
    }

    public void pushBack(int dx) {
        Assets.playSound(Assets.hit);
        x -= dx;
        if (x < -width / 2) {
            isAlive = false;
        }
        runningBounds.set(((int) x), (int) y, (int) x + width, (int) y + height);

    }

    @Override
    public void render(Painter p) {
        int xInt = ((int) x);
        int yInt = ((int) y);

        if (isGrounded()) {
            if (isDucked) {
                p.drawImage(Assets.duck, xInt, yInt);
            } else {
                Assets.runAnimation.render(p, xInt, yInt, width, height);
            }
        } else {
            p.drawImage(Assets.jump, xInt, yInt);
        }
    }

    public boolean isGrounded() {
        return runningBounds.intersect(ground);
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

    public int getyVelocity() {
        return yVelocity;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isDucked() {
        return isDucked;
    }

    public float getDuckDuration() {
        return duckDuration;
    }

    public Rect getRunningBounds() {
        return runningBounds;
    }

    public Rect getDuckBounds() {
        return duckBounds;
    }

    public Rect getGround() {
        return ground;
    }
}
