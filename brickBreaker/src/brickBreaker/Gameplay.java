package brickBreaker;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;
//them thuoc tinh
public class Gameplay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private int score = 0; // diem bat dau la 0
	
	private int totalBricks = 32; // tong so gach
			
	private Timer timer; //lop hen gio
	private int delay = 8;
	
	private int playerX = 310; //khoang cach tu dương vien ben trai toi thanh do
	
	private int ballposX = 120; //vi tri bong ban dau
	private int ballposY = 350; //vi tri bong ban dau
	private int ballXdir = -1; //huong bong
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(4,8); //hang va cot gach la 3 va 7
		addKeyListener(this);
		setFocusable(true); //dat tieu diem
		setFocusTraversalKeysEnabled(false); //bat cac phim duyet tieu diem
		timer = new Timer(delay, this); //tao doi tuong cho bo dem thoi gian
		timer.start();
	}
	public void paint(Graphics g) {
		//nen
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 692, 592);
		
		//ve ban do
		map.draw((Graphics2D)g);
		
		//duong vien
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//diem
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score: "+score, 570, 30);
		
		//thanh do
		g.setColor(Color.black);
		g.fillRect(playerX, 550, 100, 8); //dai 100, rong 8
		
		//bong
		g.setColor(Color.gray);
		g.fillOval(ballposX, ballposY, 20, 20); //duong kinh bong 20
		
		//thắng
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You won, Score: "+ score, 200,300);
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230,350);
		}
		
		//thua, bong roi xuong
		if(ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: "+ score, 190,300);
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230,350);
		}
		g.dispose(); //huy tai nguyen da cap cho bo nho
	}
	//bong dap vao gach
	@Override 
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			//bong va thanh do giao nhau
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYdir = -ballYdir;
			}
		A:	for(int i=0; i<map.map.length;i++) {
				for(int j=0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--; //gach giam
							score +=5;
							//neu bong dap gach
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x+brickRect.width) {
								ballXdir = -ballXdir; //neu bong cham thi bong di chuyen huong nguoc lai
							}else {
								ballYdir = -ballYdir;
							}
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			//bong dap vao ben trai
			if (ballposX < 0) { 
				ballXdir = -ballXdir;
			}
			//bong dap len ben tren  
			if (ballposY < 0) {
				ballYdir = -ballYdir;
			}
			//bong dap vao ben phai
			if (ballposX > 670) { //690-20
				ballXdir = -ballXdir;
			}
		}
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {tang();}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {giam();}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX>=600) {
				playerX=600;
			}else {moveRight();}
		}
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX<10) {
				playerX=10;
			}else {moveLeft();}
		}
        // choi lai
        if(e.getKeyCode()== KeyEvent.VK_ENTER) {
        	if(!play) {
        		play=true;
        		ballposX = 120;
        		ballposY = 350;
        		ballXdir = -1;
        		ballYdir = -2;
        		playerX = 310;
        		score = 0;
        		totalBricks = 21;
        		map = new MapGenerator(3,7);
        		
        		repaint();
        	}
        }
	}
	public void moveRight() {
		play = true;
		playerX+=20;
	
	}
	public void moveLeft() {
		play = true;
		playerX-=20; 
	}
	public void tang() {
		timer = new Timer(delay, this); 
		timer.start();
		play = true;
		delay = 0;
	}
	public void giam() {
		timer = new Timer(delay, this); 
		timer.start();
		play = true;
	    delay= 16;
	}
}
