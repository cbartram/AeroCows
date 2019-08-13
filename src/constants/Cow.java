package constants;

/**
 * Created by christianbartram on 2019-08-13.
 * <p>
 * http://github.com/cbartram
 */
public enum Cow {
    IDLE(-1),
    MOVING(5854),
    DYING(5851),
    ATTACKING(5849); // TODO and 5850 is attacking

    int animation;

    Cow(int animation) {
        this.animation = animation;
    }

    public int getAnimation() {
        return animation;
    }
}
