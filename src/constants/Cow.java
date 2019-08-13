package constants;

/**
 * Created by christianbartram on 2019-08-13.
 * <p>
 * http://github.com/cbartram
 */
public enum Cow {
    DEFAULT(5854), // TODO verify these as they might not be right
    MOVING(5851),
    DYING(5850),
    IN_COMBAT(5849);

    int animation;

    Cow(int animation) {
        this.animation = animation;
    }

    public int getAnimation() {
        return animation;
    }
}
