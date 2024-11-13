import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;    

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener {
    class Block {
        int x, y;
        int height, width;
        Image img;
        boolean isAlive = true; //only used for aliens 
        boolean used = false; // only used for bullets
  
        public Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;
            this.img = img;
        }
    }
  
    //board
    int tileSize = 32;
    int rows = 16;
    int columns = 16;
    int boardWidth = tileSize * columns;
    int boardHeight = tileSize * rows;

    //Declaring all images
    Image alienImg;
    Image shipImg;
    Image alienMagentaImg;
    Image alienCyanImg;
    Image alienYellowImg;

    ArrayList<Image> alienImgArray;

    // ship
    int shipWidth = tileSize * 2;  //64px
    int shipHeight = tileSize;  //32px
    int shipX = tileSize * columns / 2 - tileSize;
    int shipY = boardHeight - tileSize * 2;
    int shipVelocityX = tileSize;  //ship moving speed
    Block ship;

    // aliens
    ArrayList<Block> alienArray;
    int alienWidth = tileSize * 2;
    int alienHeight = tileSize;
    int alienX = tileSize;
    int alienY = tileSize;

    int alienRows = 2;
    int alienColumns = 3;
    int alienCount = 0;  // number of aliens defeated
    int alienVelocityX = 1; // alien moving speed

    // bullets
    ArrayList<Block> bulletArray;
    int bulletWidth = tileSize / 8;
    int bulletHeight = tileSize / 2;
    int bulletsVelocityY = -10; // bullet moving speed

    int score = 0;
    boolean gameOver = false;

    Timer gameloop;

    SpaceInvaders() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // load images
        alienImg = new ImageIcon(getClass().getResource("Images/alien.png")).getImage();
        shipImg = new ImageIcon(getClass().getResource("Images/ship.png")).getImage();
        alienCyanImg = new ImageIcon(getClass().getResource("Images/alien_cyan.png")).getImage();
        alienYellowImg = new ImageIcon(getClass().getResource("Images/alien_yellow.png")).getImage();
        alienMagentaImg = new ImageIcon(getClass().getResource("Images/alien_magenta.png")).getImage();

        // add images to the arraylist
        alienImgArray = new ArrayList<Image>();

        alienImgArray.add(alienImg);
//        alienImgArray.add(shipImg);
        alienImgArray.add(alienCyanImg);
        alienImgArray.add(alienMagentaImg);
        alienImgArray.add(alienYellowImg);

        // ship block
        ship = new Block(shipX, shipY, shipWidth, shipHeight, shipImg);

        // alien arraylist
        alienArray = new ArrayList<Block>();

        // bullet arraylist
        bulletArray = new ArrayList<Block>();

        // game timer
        gameloop = new Timer(1000 / 60, this);
        createAliens();
        gameloop.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            ship.x = shipX;
            score = 0;
            alienVelocityX = 1;
            alienColumns = 3;
            alienRows = 2;
            alienArray.clear();
            bulletArray.clear();
            gameOver = false;
            createAliens();
            gameloop.start();

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && ship.x - shipVelocityX >= 0) {
            ship.x -= shipVelocityX; // mpve player ship to left
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && ship.x + ship.width + shipVelocityX <= boardWidth) {
            ship.x += shipVelocityX; // move player ship to right
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Block bullet = new Block(ship.x + shipWidth * 15 / 32, ship.y, bulletWidth, bulletHeight, null);
            bulletArray.add(bullet);
        }
    }

    public void draw(Graphics g) {
        // drawing the ship
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);

        // drawing the aliens
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.isAlive) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
        }

        // bullets
        g.setColor(Color.white);
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            if (!bullet.used) {
                g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
            }
        }

        // score draw
        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.PLAIN, 35));
        if (gameOver) {
            g.setColor(Color.yellow);
            g.drawString("Game Over: " + String.valueOf(score), 10, 35);
        } else {
            g.drawString(String.valueOf(score), 10, 35);
        }
    }

    public void move() {
        // aliens
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.isAlive) {
                alien.x += alienVelocityX;

                // check if aliens touches the border
                if (alien.x + alien.width >= boardWidth || alien.x <= 0) {
                    alienVelocityX *= -1;
                    alien.x += alienVelocityX * 2;

                    // move all aliens to one row down
                    for (int j = 0; j < alienArray.size(); j++) {
                        alienArray.get(j).y += alienHeight;
                    }
                }

                // game over
                if (alien.y >= ship.y) {
                    gameOver = true;
                }
            }
        }

        // bullets
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            bullet.y += bulletsVelocityY;

            // bullet collision with aliens
            for (int j = 0; j < alienArray.size(); j++) {
                Block alien = alienArray.get(j);
                if (!bullet.used && alien.isAlive && detectCollision(bullet, alien)) {
                    bullet.used = true;
                    alien.isAlive = false;
                    alienCount--;
                    score += 100;
                }
            }
        }

        // clear bullets
        while (bulletArray.size() > 0 && (bulletArray.get(0).used || bulletArray.get(0).y < 0)) {
            bulletArray.remove(0); // remove first element of arraylist
        }

        // next level
        if (alienCount == 0) {
            score += alienColumns * alienRows * 100;
            // increase the alien numbers in the next colum by 1 row
            alienColumns = Math.min(alienColumns + 1, columns / 2 - 2); // max column will be 16/2 - 2 = 6
            alienRows = Math.min(alienRows + 1, rows - 6);  // max rows will be 16 - 6 = 10
            alienArray.clear();
            bulletArray.clear();
            alienVelocityX = 1;
            createAliens();
        }
    }

    public boolean detectCollision(Block a, Block b) {
        return a.x < b.x + b.width &&   // a's tpo right corner doesnt reach b's top right crner
               a.x + a.width  > b.x &&  // a's top right corner passes b's top left corner
               a.y < b.y + b.height &&  // a's top left corner doesn't reach b's bottom left corner
               a.y + a.height > b.y;    // a's bottom left corner passes b's top left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) gameloop.stop();
    }

    private void createAliens() {
        Random random = new Random();

        for (int r = 0; r < alienRows; r++) {
            for (int c = 0; c < alienColumns; c++) {
                int randomImgIndex = random.nextInt(alienImgArray.size());

                Block alien = new Block(alienX + c * alienWidth,
                        alienY + r * alienHeight,
                        alienWidth, alienHeight,
                        alienImgArray.get(randomImgIndex)
                );
                alienArray.add(alien);
            }
        }
        alienCount = alienArray.size();
    }
}
