package kamilswojak.ellio.util;

import android.graphics.Bitmap;
import android.graphics.Rect;

import kamilswojak.ellio.Painter;

public class Button {
    private Rect bounds;
    private boolean buttonDown = false;
    private Bitmap buttonImage, buttonDownImage;

    public Button(int left, int top, int right, int bottom, Bitmap buttonImage, Bitmap buttonDownImage) {
        bounds = new Rect(left, top, right, bottom);
        this.buttonImage = buttonImage;
        this.buttonDownImage = buttonDownImage;
    }

    public void render(Painter p) {
        Bitmap currentBitmap = buttonDown ? buttonDownImage : buttonImage;
        p.drawImage(currentBitmap, bounds.left, bounds.top, bounds.width(), bounds.height());
    }

    public void onTouchDown(int touchX, int touchY) {
        buttonDown = bounds.contains(touchX, touchY);
    }

    public void cancel() {
        buttonDown = false;
    }

    public boolean isPressed(int touchX, int touchY) {
        return buttonDown && bounds.contains(touchX, touchY);
    }

}
