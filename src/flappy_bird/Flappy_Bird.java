/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappy_bird;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Lenovo
 */
public class Flappy_Bird extends JPanel implements MouseListener{

	private static int height = 650;
	private static int width = 1100;
	private static JFrame frame;
	private static boolean active = false;
	private static List<Rectangle> columns_top = new ArrayList<Rectangle>();
	private static List<Rectangle> columns_bottom = new ArrayList<Rectangle>();
	private static boolean game_over = false;
	private static Rectangle bird;
	private static Timer timer;
        private static int score = 0;

        
         public Flappy_Bird() {
		this.addMouseListener(this);
		setDoubleBuffered(true);
	}
    
        public static void main(String[] args) {
        frame = new JFrame();
		frame.setContentPane(new Flappy_Bird());
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		create_column();
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(active && bird.y>0) {
					bird.y -= 40;
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		timer = new Timer(40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(active) {
					for(int i=0; i<columns_top.size();i++) {
						Rectangle r = columns_top.get(i);
                                                //kolonların geçiş hızı
						r.x -= 10; 
                                                //kolonların birbirine uzaklığı
						if(r.x == 720) {
							create_column();
						}
						if(r.x==-100) {
							columns_top.remove(r);
							score++;
						}
					}
					for(int i=0; i<columns_bottom.size();i++) {
						Rectangle r = columns_bottom.get(i);
						r.x -= 10;
						if(r.x==-100) {
							columns_top.remove(r);
						}
					}
                                        //kaybedildiğinde
					isGameOver();
					frame.repaint();
				}
			}
		});
                //zamanı başlatarak oyunu ve skoru başlat.
		timer.start();
	}
		
	@Override
    public void paintComponent(Graphics g) {
			super.paintComponent(g);
                        Image image = new ImageIcon("image/bg.png").getImage();
                        int baslangicX = 0;
                        int baslangicY = 0;
                        g.drawImage(image, baslangicX, baslangicY, null);
		if(!active) {
    			g.setFont(new Font("Arial", 1, 80));
			g.drawString("PLAY", width/2-80, height/2);
		}else if (active && !game_over){
                        g.setColor(new Color(0,128,0));
                        g.drawString("SCORE: " + score, width/80, height/2-300);
			for(Rectangle r: columns_top) {
				g.fillRect(r.x, r.y, r.width, r.height);
			}
			for(Rectangle r: columns_bottom) {
				g.fillRect(r.x, r.y, r.width, r.height);
			}
                        //kuşun yüksekliği küçükse 5 er 5 er arttır.
			if(bird.y<590) {
				bird.y += 5; 
			}
			g.setColor(Color.RED);
			g.fillRect(bird.x, bird.y, bird.width, bird.height);
		}else if(active && game_over) {
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 1, 80));
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER", width/2-220, height/2);
			g.drawString("SCORE: " + score, width/2-180, height/2+80);
		}
}

	public static void create_column() {
		Random rand = new Random();
		int h = 50 + rand.nextInt(300);
		int w = 100;
		int space = 200;
		Rectangle top = new Rectangle(width, 0, w, h);
		Rectangle bottom = new Rectangle(width, h+space, w, height-h-space);
		columns_top.add(top);
		columns_bottom.add(bottom);	
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		active = true;
		bird = new Rectangle(width/2, height/2, 20,20);
		repaint();
	}
	
	public static void isGameOver() {
		for(Rectangle r: columns_top) {
			if(r.contains(bird) || r.intersects(bird)) {
				game_over = true;
				timer.stop();
			}
		}
		for(Rectangle r: columns_bottom ) {
			if(r.contains(bird) || r.intersects(bird)) {
				game_over = true;
				timer.stop();
			}
		}
        }

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
