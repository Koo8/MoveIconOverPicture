package MainPackage;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class works for:
 * move icon to go across the image background
 *
 * NOTE: DONT draw when extends from JFrame, the paintComponents() is not used to be override.
 * draw when extends JPanel, its paintComponent() is for auto painting. Notice -> there is a
 * "s" for JFrame method
 *
 * loop through enum, use hashmap to capture "true" and "false" value of
 * 4 directions in the enum
 *
 * use keyBindings for actions
 */

public class DrawScreen extends JPanel {
    private BufferedImage backImage, icon;
    private Timer timer;
    File file;
    InputStream inputStream;
    ImageInputStream imageInputStream;
    URL backUrl, iconUrl;
    private int x, y, speed;
    private Map<Direction, Boolean> map = new HashMap<>();

    public DrawScreen() {
        x = 10;
        y = 10;
        speed = 10;
        // this case, sizes is defined by jFrame
        try {
            backUrl = new URL("https://rb.gy/iyealr");
            iconUrl = new URL("https://rb.gy/rai9i6");
            backImage = ImageIO.read(backUrl); // read from URL / inputStream / imageInputStream / File
            // System.out.println(backImage);
            icon = ImageIO.read(iconUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // set Hashmap for 4 directions, set its initial value to be false
        for (Direction d : Direction.values()) {
            map.put(d, false);
        }

        // keybinding  input_KeyStroke -> key -> Actionmap
        setKeyBinding();

        // update GUI constantly
        timer = new Timer(speed * 5, new ListenerForTimer());
        timer.setInitialDelay(0);
        timer.start();
    }

    private void setKeyBinding() {
        InputMap inputMap = getInputMap();//default
        ActionMap actionMap = getActionMap();

        // set keyStroke for inputmap, set key String for actionMap connection
        // loop through enum values--
        for (Direction direction : Direction.values()) {
            // for each direction enum, there are two inputmaps coresponding to two actions for the same input keycode
            KeyStroke pressed = KeyStroke.getKeyStroke(direction.getKeycode(), 0, false);
            KeyStroke released = KeyStroke.getKeyStroke(direction.getKeycode(), 0, true);
            inputMap.put(pressed, direction.toString() + "pressed");
            inputMap.put(released, direction.toString() + "released");
            actionMap.put(direction.toString() + "pressed", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // when pressed, make hashMap's value to be true
                    map.put(direction, true);
                }
            });
            actionMap.put(direction.toString() + "released", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    map.put(direction, false);
                }
            });
        }
    }

    private class ListenerForTimer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean moved = false;
            // loop through enum class, for each direction, check its corresponding hashmap value to be true or false
            for (Direction d : Direction.values()) {
                if (map.get(d)) {
                    x += speed * d.getxDirection();
                    y += speed * d.getyDirection(); // these are player's new position
                    moved = true;
                }
            }
            if (moved) {
                // only when moved, repaint the smaller icon area
                int paintX = x -2*speed;
                int paintY = y - 2*speed;
                int paintWidth = icon.getWidth() + 4* speed;
                int paintHeight = icon.getHeight() + 4 * speed;
                repaint(paintX, paintY, paintWidth, paintHeight);
            }

        }
    }

    @Override  // highlight: paintComponent() is for Jpanel, paintComponents() is for JFrame.
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2d = (Graphics2D) g;

        if (backImage != null)
            g2d.drawImage(backImage, x, y, null);
        if (icon != null)
            g2d.drawImage(icon, x, y, null);
    }

    public void createGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // jFrame needs to set size if JPanel didn't use LayoutManager, this time, pack() can't be used - minimize the screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) screen.getWidth() - 100, (int) screen.getHeight() - 50);

        frame.getContentPane().add(this);

        frame.setTitle("Move Icon Across Picture");
        // pack() will minimize the frame because jPanel didn't define the component sizse.
        //frame.pack(); // if no content is added, the size is minimal
        frame.setLocationRelativeTo(null); // if this line is above setSize, the location will be starting from the center point of the screen
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        DrawScreen drawScreen = new DrawScreen();
        drawScreen.createGUI();
    }
}
