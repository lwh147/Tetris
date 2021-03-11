/*
 * 该类用来绘制游戏图形界面（继承了JPanel类并重写paint方法实现界面绘制）
 * 成员变量：
 * 		控制游戏界面面积的一些变量
 * 		游戏界面中需要绘制的四个重要元素：游戏区域、正在下落的形状、下一个生成的形状以及游戏状态
 * 		游戏中用到的所有图片资源
 * 方法：
 * 		paint：重写父类的paint方法，绘制游戏界面
 * 		paintWall：绘制游戏区域
 * 		paintBoundary：绘制游戏背景分割边界
 * 		paintDropping：绘制正在下落的形状
 * 		paintNextDrop：绘制下一个掉落的形状
 * 		paintScore：绘制游戏得分区域
 * 		paintGameState：绘制游戏状态区域
 * 		paintGameOperation：绘制游戏操作说明
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
	//成员变量————————————————————————————————————————————
	private static final long serialVersionUID = -8560764857881220975L;	
	//游戏界面相关参数
	protected int BASICBLOCK_SIZE = 30;		//规定基本方块的大小
	protected int NUMOFROW = 20;			//规定游戏区域方块的总行数
	protected int NUMOFCOL = 12;				//规定游戏区域方块的总列数
	protected int MAXIMUMROW = 22;		//规定组成整个游戏界面的行数
	protected int MAXIMUMCOL = 22;			//规定组成整个游戏界面的列数
	//游戏中四个重要的元素
	protected BasicBlock[][] wall = new BasicBlock[NUMOFROW][NUMOFCOL];			//游戏区域
	protected BasicShape Shape_dropping = BasicShape.random_generate_shape();		//正在掉落的形状
	protected BasicShape Shape_nextdrop = BasicShape.random_generate_shape();		//下一个将要掉落的形状
	protected String[] showState = { "Pause...", "Playing...", "GameOver!",
	"Press 'C' to continue", "Press 'P' to pause", "Press 'Enter' to replay"};							//游戏状态提示语句
	//显示所需图片资源
	public static BufferedImage I;							//I形状填充图片
	public static BufferedImage J;						//J形状填充图片
	public static BufferedImage L;						//L形状填充图片
	public static BufferedImage O;						//O形状填充图片
	public static BufferedImage S;						//S形状填充图片
	public static BufferedImage T;						//T形状填充图片
	public static BufferedImage Z;						//Z形状填充图片	
	public static BufferedImage icon;					//游戏窗口图标
	public static BufferedImage gameover;			//游戏结束画面
	public static BufferedImage boundary;			//游戏区域分割边界图片
	public static BufferedImage background;		//游戏背景图
	//加载图片资源（静态代码块，类在创建时直接加载）
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
			e.printStackTrace();		//有可能加载失败
		}
	}
	
	//方法—————————————————————————————————————————————
	//绘制游戏界面，重载父类的paint方法
	//方法中调用的不熟悉的函数说明
	/*
	 * g相当于Panel上的画笔，操作画笔来完成在Panel上的界面绘制
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
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);			//绘制游戏背景
		paintBoundary(g);				//绘制游戏边界
		g.translate(30, 30);				//移动画笔
		paintWall(g);						//绘制游戏区域
		paintGameOperation(g);	//绘制游戏操作说明
		paintDropping(g);				//绘制当前正在下落的形状
		paintNextDrop(g);				//绘制下一个要下落的形状
		paintScore(g);						//绘制计分板
		paintGameState(g);			//绘制游戏状态
	}
	//绘制游戏区域
	private void paintWall(Graphics g){	
		for (int i = 0; i < NUMOFROW; i++) {			// 外层循环控制行数
			for (int j = 0; j < NUMOFCOL; j++) {		// 内层循环控制列数
				int x = j * BASICBLOCK_SIZE;				//得到真实坐标
				int y = i * BASICBLOCK_SIZE;
				BasicBlock bbs = wall[i][j];					//取得该方块的内容
				if (bbs == null) {									//说明该方块区域内没有方块
					g.setColor(Color.gray);
					g.drawRect(x, y, BASICBLOCK_SIZE, BASICBLOCK_SIZE);
				} else {
					g.drawImage(bbs.getBlock_image(), x, y, null);		//绘制该方块
				}
			}
		}
	}
	//绘制游戏边界
	private void paintBoundary(Graphics g){
		for (int i=0; i<MAXIMUMCOL; i++){				//绘制游戏上下边界
			int x = i*BASICBLOCK_SIZE;
			g.drawImage(boundary, x, 0, null);																		//上边界
			g.drawImage(boundary, x, (MAXIMUMROW-1)*BASICBLOCK_SIZE, null);		//下边界
		}
		for (int i=0; i<MAXIMUMROW; i++){				//绘制游戏左右边界和游戏、状态区域分割边界
			int y = i*BASICBLOCK_SIZE;
			g.drawImage(boundary, 0, y, null);				//左边界
			g.drawImage(boundary, 13*BASICBLOCK_SIZE, y, null);		//游戏、状态区域分割边界
			g.drawImage(boundary, 21*BASICBLOCK_SIZE, y, null);		//右边界
		}
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 25));		//设置字符串字体
		g.setColor(Color.green);															//设置画笔颜色
		g.drawString("Next:", 14*BASICBLOCK_SIZE, 2*BASICBLOCK_SIZE-8);					//显示下个形状区域的文字提示
		for (int i=13; i<21; i++){															//绘制状态区分割边界
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
	//绘制当前正在下落的形状
	private void paintDropping(Graphics g){
		BasicBlock[] bbsArr = Shape_dropping.getShape();		//获取组成正在下落形状的各个方块信息
		for (BasicBlock bbs:bbsArr){												//遍历绘制所有方块
			int col = bbs.getX();														//获取真实坐标信息
			int row = bbs.getY();
			int x = col*BASICBLOCK_SIZE;
			int y = row*BASICBLOCK_SIZE;
			g.drawImage(bbs.getBlock_image(), x, y, null);			//进行绘制
		}
	}
	//绘制下一个要下落的形状
	private void paintNextDrop(Graphics g){
		BasicBlock[] bbsArr = Shape_nextdrop.getShape();		//获取组成下一个形状的各个方块信息
		for (BasicBlock bbs:bbsArr){												//遍历绘制所有方块
			int col = bbs.getX();														//获取真实坐标信息
			int row = bbs.getY();
			int x = (col+12)*BASICBLOCK_SIZE;
			int y = (row+1)*BASICBLOCK_SIZE;
			g.drawImage(bbs.getBlock_image(), x, y, null);			//进行绘制
		}
	}
	//绘制计分板
	//方法中调用的不熟悉的函数说明
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
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 30));		//设置字符串字体
		g.setColor(Color.green);															//设置画笔颜色
		g.drawString("Scores:"+Tetris_Run.totalscore, 13*BASICBLOCK_SIZE, 8*BASICBLOCK_SIZE-5);			//绘制获得的总分
		g.drawString("Lines:"+Tetris_Run.totalline, 13*BASICBLOCK_SIZE, 10*BASICBLOCK_SIZE-5);				//绘制消除的总行数
		g.drawString("Level:"+(Tetris_Run.game_level+1), 13*BASICBLOCK_SIZE, 12*BASICBLOCK_SIZE-5);	//绘制当前游戏等级
	}
	//绘制游戏状态
	private void paintGameState(Graphics  g){								
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 22));		//设置字符串字体
		switch (Tetris_Run.game_state){												//对游戏状态进行判断
			case Tetris_Run.GAMEOVER:												//游戏结束则显示结束画面，并在状态栏提示
				g.drawImage(gameover, -7, 6*BASICBLOCK_SIZE, null);
				g.drawString("State:"+showState[Tetris_Run.GAMEOVER], 13*BASICBLOCK_SIZE, 14*BASICBLOCK_SIZE-5);
				g.drawString(showState[Tetris_Run.GAMEOVER+3], 13*BASICBLOCK_SIZE, 15*BASICBLOCK_SIZE-5);
				break;
			case Tetris_Run.PLAYING:			//游戏正在进行
				g.drawString("State:"+showState[Tetris_Run.PLAYING], 13*BASICBLOCK_SIZE, 14*BASICBLOCK_SIZE-5);
				g.drawString(showState[Tetris_Run.PLAYING+3], 13*BASICBLOCK_SIZE, 15*BASICBLOCK_SIZE-5);
				break;
			case Tetris_Run.PAUSE:				//游戏暂停
				g.drawString("State:"+showState[Tetris_Run.PAUSE], 13*BASICBLOCK_SIZE, 14*BASICBLOCK_SIZE-5);
				g.drawString(showState[Tetris_Run.PAUSE+3], 13*BASICBLOCK_SIZE, 15*BASICBLOCK_SIZE-5);
				break;
		}
	}
	//绘制游戏操作说明
	private void paintGameOperation(Graphics g){
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));					//设置字符串字体
		g.setColor(Color.green);																		//设置画笔颜色
		g.drawString("'Enter':Replay", 13*BASICBLOCK_SIZE, 17*BASICBLOCK_SIZE-12);						//绘制重新开始游戏提示
		g.drawString("'↑':Rorate,'↓':MoveDown", 13*BASICBLOCK_SIZE, 18*BASICBLOCK_SIZE-10);	//绘制游戏操作说明
		g.drawString("'←':MoveLeft,'→':Right", 13*BASICBLOCK_SIZE, 19*BASICBLOCK_SIZE-20);		//绘制游戏操作说明
		g.drawString("'+':LevelUp,'-':Down", 13*BASICBLOCK_SIZE, 20*BASICBLOCK_SIZE-30);			//绘制游戏操作说明
		g.drawString("'WhiteSpace':Drop", 13*BASICBLOCK_SIZE, 21*BASICBLOCK_SIZE-40);				//绘制游戏操作说明
	}
}
