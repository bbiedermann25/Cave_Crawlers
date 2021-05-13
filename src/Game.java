import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Game extends JPanel{
    private PauseMenu pauseMenu = new PauseMenu(600, 800);
    private CharacterController controller = new CharacterController(KeyEvent.VK_RIGHT,
            KeyEvent.VK_LEFT,KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_P);
    private Character character = new Character(350,700, getSize().width, getSize().height,
            new Dimension(20, 28), controller, pauseMenu);
    private Object object;
    private Inventory inventory;
    private InventoryPanel inventoryPanel = new InventoryPanel();

    public Game(){
        setFocusable(true);
        setOpaque(false);
        setSize(800, 600);
        Timer timer = new Timer(16, new TimerListener());
        timer.start();
        controller = new CharacterController(KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT,KeyEvent.VK_UP,
                KeyEvent.VK_DOWN, KeyEvent.VK_P);
        addKeyListener(controller);
        character = new Character(350,350, getSize().width, getSize().height,
                new Dimension(20, 28), controller, pauseMenu);
        object = new Object(character.getX(), character.getY());
        object.spawn();

        add(inventoryPanel);

    }

    @Override
    public void paintComponent(Graphics g) {
        object.paint(g);
        character.paint(g);
        pauseMenu.paint(g);
    }

    private void update(){
        character.update();
        int x = Math.abs(character.getX() - object.x);
        int y = Math.abs(character.getY() - object.y);
        //System.out.println("x: " + (character.getX() - object.x) + " y: " + (character.getY() - object.y));

        //make object opaque
        if (x <= 40 && y <=40){
            object.playerNear = true;
            object.found = true;

            //player close enough to add object to inventory, make object transparent
            if(x <= 4 || y <= 4){
                object.playerNear = false;
                object.found = false;
                object.spawn();
                inventoryPanel.addCoin();
                inventoryPanel.coin.setText(String.valueOf(inventoryPanel.coinCount));
            }
        }

    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            update();
            repaint();
        }
    }
}
