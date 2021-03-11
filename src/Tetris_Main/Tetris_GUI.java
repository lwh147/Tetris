/*
 * ��������������Ϸͼ�ν��棨�̳���JPanel�ಢ��дpaint����ʵ�ֽ�����ƣ�
 * ��Ա������
 * 		������Ϸ���������һЩ����
 * 		��Ϸ��������Ҫ���Ƶ��ĸ���ҪԪ�أ���Ϸ���������������״����һ�����ɵ���״�Լ���Ϸ״̬
 * 		��Ϸ���õ�������ͼƬ��Դ
 * ������
 * 		paint����д�����paint������������Ϸ����
 * 		paintWall��������Ϸ����
 * 		paintBoundary��������Ϸ�����ָ�߽�
 * 		paintDropping�����������������״
 * 		paintNextDrop��������һ���������״
 * 		paintScore��������Ϸ�÷�����
 * 		paintGameState��������Ϸ״̬����
 * 		paintGameOperation��������Ϸ����˵��
 */
package Tetris_Main;

import Tetris_Basic.BasicBlock;
import Tetris_Basic.BasicShape;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tetris_GUI extends JPanel {
	//��Ա��������������������������������������������������������������������������������������������
	private static final long serialVersionUID = -8560764857881220975L;	
	//��Ϸ������ز���
	protected int BASICBLOCK_SIZE = 30;		//�涨��������Ĵ�С
	protected int NUMOFROW = 20;			//�涨��Ϸ���򷽿��������
	protected int NUMOFCOL = 12;				//�涨��Ϸ���򷽿��������
	protected int MAXIMUMROW = 22;		//�涨���������Ϸ���������
	protected int MAXIMUMCOL = 22;			//�涨���������Ϸ���������
	//��Ϸ���ĸ���Ҫ��Ԫ��
	protected BasicBlock[][] wall = new BasicBlock[NUMOFROW][NUMOFCOL];			//��Ϸ����
	protected BasicShape Shape_dropping = BasicShape.random_generate_shape();		//���ڵ������״
	protected BasicShape Shape_nextdrop = BasicShape.random_generate_shape();		//��һ����Ҫ�������״
	protected String[] showState = { "Pause...", "Playing...", "GameOver!",
	"Press 'C' to continue", "Press 'P' to pause", "Press 'Enter' to replay"};							//��Ϸ״̬��ʾ���
	//��ʾ����ͼƬ��Դ
	public static BufferedImage I;							//I��״���ͼƬ
	public static BufferedImage J;						//J��״���ͼƬ
	public static BufferedImage L;						//L��״���ͼƬ
	public static BufferedImage O;						//O��״���ͼƬ
	public static BufferedImage S;						//S��״���ͼƬ
	public static BufferedImage T;						//T��״���ͼƬ
	public static BufferedImage Z;						//Z��״���ͼƬ	
	public static BufferedImage icon;					//��Ϸ����ͼ��
	public static BufferedImage gameover;			//��Ϸ��������
	public static BufferedImage boundary;			//��Ϸ����ָ�߽�ͼƬ
	public static BufferedImage background;		//��Ϸ����ͼ
	//����ͼƬ��Դ����̬����飬���ڴ���ʱֱ�Ӽ��أ�
	static{
		try {
			I = ImageIO.read(Tetris_GUI.class.getResource("images/I.png"));
			J = ImageIO.read(Tetris_GUI.class.getResource("images/J.png"));
			L = ImageIO.read(Tetris_GUI.class.getResource("images/L.png"));
			O = ImageIO.read(Tetris_GUI.class.getResource("images/O.png"));
			S = ImageIO.read(Tetris_GUI.class.getResource("images/S.png"));
			T = ImageIO.read(Tetris_GUI.class.getResource("images/T.png"));
			Z = ImageIO.read(Tetris_GUI.class.getResource("images/Z.png"));
			icon = ImageIO.read(Tetris_GUI.class.getResource("images/icon.png"));
			boundary = ImageIO.read(Tetris_GUI.class.getResource("images/boundary.png"));
			gameover = ImageIO.read(Tetris_GUI.class.getResource("images/gameover.png"));
			background = ImageIO.read(Tetris_GUI.class.getResource("images/background.png"));
		} catch (IOException e) {
			e.printStackTrace();		//�п��ܼ���ʧ��
		}
	}
	
	//����������������������������������������������������������������������������������������������
	//������Ϸ���棬���ظ����paint����
	//�����е��õĲ���Ϥ�ĺ���˵��
	/*
	 * g�൱��Panel�ϵĻ��ʣ����������������Panel�ϵĽ������
	 * public abstract boolean drawImage(Image img,
     *                       								   int x,
     *                       								   int y,
     *                       								   ImageObserver observer)
     * Draws as much of the specified image as is currently available.
	 * Parameters:
	 *		img - the specified image to be drawn. This method does nothing if img is null.
	 *		x - the x coordinate.
	 *		y - the y coordinate.
	 *		observer - object to be notified as more of the image is converted.
	 * Returns:
	 *		false if the image pixels are still changing; true otherwise.
	 *-----------------------------------------------------------------------------------------------------------
	 * public abstract void translate(int x,
     *                    							 int y)
     * Translates the origin of the graphics context to the point (x, y) in the current coordinate system. 
     * Parameters:
     *		x - the x coordinate.
     *		y - the y coordinate.
	 */
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);			//������Ϸ����
		paintBoundary(g);				//������Ϸ�߽�
		g.translate(30, 30);				//�ƶ�����
		paintWall(g);						//������Ϸ����
		paintGameOperation(g);	//������Ϸ����˵��
		paintDropping(g);				//���Ƶ�ǰ�����������״
		paintNextDrop(g);				//������һ��Ҫ�������״
		paintScore(g);						//���ƼƷְ�
		paintGameState(g);			//������Ϸ״̬
	}
	//������Ϸ����
	private void paintWall(Graphics g){	
		for (int i = 0; i < NUMOFROW; i++) {			// ���ѭ����������
			for (int j = 0; j < NUMOFCOL; j++) {		// �ڲ�ѭ����������
				int x = j * BASICBLOCK_SIZE;				//�õ���ʵ����
				int y = i * BASICBLOCK_SIZE;
				BasicBlock bbs = wall[i][j];					//ȡ�ø÷��������
				if (bbs == null) {									//˵���÷���������û�з���
					g.setColor(Color.gray);
					g.drawRect(x, y, BASICBLOCK_SIZE, BASICBLOCK_SIZE);
				} else {
					g.drawImage(bbs.getBlock_image(), x, y, null);		//���Ƹ÷���
				}
			}
		}
	}
	//������Ϸ�߽�
	private void paintBoundary(Graphics g){
		for (int i=0; i<MAXIMUMCOL; i++){				//������Ϸ���±߽�
			int x = i*BASICBLOCK_SIZE;
			g.drawImage(boundary, x, 0, null);																		//�ϱ߽�
			g.drawImage(boundary, x, (MAXIMUMROW-1)*BASICBLOCK_SIZE, null);		//�±߽�
		}
		for (int i=0; i<MAXIMUMROW; i++){				//������Ϸ���ұ߽����Ϸ��״̬����ָ�߽�
			int y = i*BASICBLOCK_SIZE;
			g.drawImage(boundary, 0, y, null);				//��߽�
			g.drawImage(boundary, 13*BASICBLOCK_SIZE, y, null);		//��Ϸ��״̬����ָ�߽�
			g.drawImage(boundary, 21*BASICBLOCK_SIZE, y, null);		//�ұ߽�
		}
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 25));		//�����ַ�������
		g.setColor(Color.green);															//���û�����ɫ
		g.drawString("Next:", 14*BASICBLOCK_SIZE, 2*BASICBLOCK_SIZE-8);					//��ʾ�¸���״�����������ʾ
		for (int i=13; i<21; i++){															//����״̬���ָ�߽�
			int x = i*BASICBLOCK_SIZE;
			g.drawImage(boundary, x, 7*BASICBLOCK_SIZE, null);
			g.drawImage(boundary, x, 9*BASICBLOCK_SIZE, null);
			g.drawImage(boundary, x, 11*BASICBLOCK_SIZE, null);
			g.drawImage(boundary, x, 13*BASICBLOCK_SIZE, null);
			//g.drawImage(boundary, x, 14*BASICBLOCK_SIZE, null);
			//g.drawImage(boundary, x, 15*BASICBLOCK_SIZE, null);
			g.drawImage(boundary, x, 16*BASICBLOCK_SIZE, null);
			//g.drawImage(boundary, x, 18*BASICBLOCK_SIZE, null);
		}
	}
	//���Ƶ�ǰ�����������״
	private void paintDropping(Graphics g){
		BasicBlock[] bbsArr = Shape_dropping.getShape();		//��ȡ�������������״�ĸ���������Ϣ
		for (BasicBlock bbs:bbsArr){												//�����������з���
			int col = bbs.getX();														//��ȡ��ʵ������Ϣ
			int row = bbs.getY();
			int x = col*BASICBLOCK_SIZE;
			int y = row*BASICBLOCK_SIZE;
			g.drawImage(bbs.getBlock_image(), x, y, null);			//���л���
		}
	}
	//������һ��Ҫ�������״
	private void paintNextDrop(Graphics g){
		BasicBlock[] bbsArr = Shape_nextdrop.getShape();		//��ȡ�����һ����״�ĸ���������Ϣ
		for (BasicBlock bbs:bbsArr){												//�����������з���
			int col = bbs.getX();														//��ȡ��ʵ������Ϣ
			int row = bbs.getY();
			int x = (col+12)*BASICBLOCK_SIZE;
			int y = (row+1)*BASICBLOCK_SIZE;
			g.drawImage(bbs.getBlock_image(), x, y, null);			//���л���
		}
	}
	//���ƼƷְ�
	//�����е��õĲ���Ϥ�ĺ���˵��
	/*
	 * public abstract void setFont(Font font)
	 * Sets this graphics context's font to the specified font.
	 * Parameters:
	 * 		font - the font.
	 * ---------------------------------------------------------------------------
	 * public Font(String name,
     *    				  int style,
     * 					  int size)
     * Creates a new Font from the specified name, style and point size. 
     * Parameters:
     * 		name - the font name. 
     * 		style - the style constant for the Font 
     * 		size - the point size of the Font
	 */
	private void paintScore(Graphics g){
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 30));		//�����ַ�������
		g.setColor(Color.green);															//���û�����ɫ
		g.drawString("Scores:"+Tetris_Run.totalscore, 13*BASICBLOCK_SIZE, 8*BASICBLOCK_SIZE-5);			//���ƻ�õ��ܷ�
		g.drawString("Lines:"+Tetris_Run.totalline, 13*BASICBLOCK_SIZE, 10*BASICBLOCK_SIZE-5);				//����������������
		g.drawString("Level:"+(Tetris_Run.game_level+1), 13*BASICBLOCK_SIZE, 12*BASICBLOCK_SIZE-5);	//���Ƶ�ǰ��Ϸ�ȼ�
	}
	//������Ϸ״̬
	private void paintGameState(Graphics  g){								
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 22));		//�����ַ�������
		switch (Tetris_Run.game_state){												//����Ϸ״̬�����ж�
			case Tetris_Run.GAMEOVER:												//��Ϸ��������ʾ�������棬����״̬����ʾ
				g.drawImage(gameover, -7, 6*BASICBLOCK_SIZE, null);
				g.drawString("State:"+showState[Tetris_Run.GAMEOVER], 13*BASICBLOCK_SIZE, 14*BASICBLOCK_SIZE-5);
				g.drawString(showState[Tetris_Run.GAMEOVER+3], 13*BASICBLOCK_SIZE, 15*BASICBLOCK_SIZE-5);
				break;
			case Tetris_Run.PLAYING:			//��Ϸ���ڽ���
				g.drawString("State:"+showState[Tetris_Run.PLAYING], 13*BASICBLOCK_SIZE, 14*BASICBLOCK_SIZE-5);
				g.drawString(showState[Tetris_Run.PLAYING+3], 13*BASICBLOCK_SIZE, 15*BASICBLOCK_SIZE-5);
				break;
			case Tetris_Run.PAUSE:				//��Ϸ��ͣ
				g.drawString("State:"+showState[Tetris_Run.PAUSE], 13*BASICBLOCK_SIZE, 14*BASICBLOCK_SIZE-5);
				g.drawString(showState[Tetris_Run.PAUSE+3], 13*BASICBLOCK_SIZE, 15*BASICBLOCK_SIZE-5);
				break;
		}
	}
	//������Ϸ����˵��
	private void paintGameOperation(Graphics g){
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));					//�����ַ�������
		g.setColor(Color.green);																		//���û�����ɫ
		g.drawString("'Enter':Replay", 13*BASICBLOCK_SIZE, 17*BASICBLOCK_SIZE-12);						//�������¿�ʼ��Ϸ��ʾ
		g.drawString("'��':Rorate,'��':MoveDown", 13*BASICBLOCK_SIZE, 18*BASICBLOCK_SIZE-10);	//������Ϸ����˵��
		g.drawString("'��':MoveLeft,'��':Right", 13*BASICBLOCK_SIZE, 19*BASICBLOCK_SIZE-20);		//������Ϸ����˵��
		g.drawString("'+':LevelUp,'-':Down", 13*BASICBLOCK_SIZE, 20*BASICBLOCK_SIZE-30);			//������Ϸ����˵��
		g.drawString("'WhiteSpace':Drop", 13*BASICBLOCK_SIZE, 21*BASICBLOCK_SIZE-40);				//������Ϸ����˵��
	}
}
