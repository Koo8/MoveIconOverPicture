package MainPackage;

import java.awt.event.KeyEvent;

public enum Direction {

    UP(KeyEvent.VK_UP, 0,-1),
    DOWN(KeyEvent.VK_DOWN, 0, 1),
    LEFT(KeyEvent.VK_LEFT, -1, 0),
    RIGHT(KeyEvent.VK_RIGHT, 1, 0);

    private int keycode;
    private int xDirection;
    private int yDirection;

    Direction(int keycode, int xDirection, int yDirection){
           this.keycode = keycode;
           this.xDirection = xDirection;
           this.yDirection = yDirection;
    }

    public int getKeycode() {
        return keycode;
    }

    public int getxDirection() {
        return xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }
}
