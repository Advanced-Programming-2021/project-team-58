package Model;

import Controller.*;
import View.*;

abstract public class Effect {
    abstract public void activate(Game game);
    abstract public boolean isSuitableForActivate(Game game);
}
