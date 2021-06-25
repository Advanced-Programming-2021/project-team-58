package Model;

import Controller.*;

abstract public class Effect {
    public abstract void activate(Game game);

    public abstract boolean isSuitableForActivate(Game game);
}
