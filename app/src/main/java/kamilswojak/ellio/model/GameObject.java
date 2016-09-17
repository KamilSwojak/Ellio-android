package kamilswojak.ellio.model;

import kamilswojak.ellio.Painter;

public abstract class GameObject implements Renderable {

    public abstract void render(Painter p);
}
