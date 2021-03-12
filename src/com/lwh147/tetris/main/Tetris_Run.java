/*
 * 该类用来更新游戏图形界面并实现与用户的交互
 * 成员变量：
 * 		TP：游戏界面
 * 		标志游戏状态的成员变量
 * 		游戏中记录型成员变量
 * 方法：
 * 		Detect_isgameover：判断游戏是否结束
 * 		Detect_islinefull：判断一行是否铺满
 * 		Detect_candrop：判断形状是否可以下落
 * 		Detect_outofwall：判断形状是否出界
 * 		Detect_iscoincide：判断形状是否与已有方块重合
 * 		Action_inserttowall：落定的形状嵌入墙中
 * 		Action_destroyline：消除铺满的一行
 * 		Action_softdrop：下落一格
 * 		Action_harddrop：直接落地
 *     Action_moceright：右移
 *     Action_moveleft：左移
 *     Action_rorate：旋转
 *     Action_setkeylistener：设置按键监听器
 *     Action_run：控制游戏运行的主要逻辑
 */
package com.lwh147.tetris.main;

import com.lwh147.tetris.basic.BasicBlock;
import com.lwh147.tetris.basic.BasicShape;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tetris_Run {
	//游戏程序入口—————————————————————————————————————————
	public static void main(String[] args){
		JFrame jf = new JFrame("Tetris");										//新建游戏窗口
		jf.add(TP);																				//将游戏主类画板添加到游戏窗口中
		jf.setVisible(true);																	//将窗口设置为可见
		jf.setSize(665, 688);																	//设置窗口大小
		jf.setIconImage(Tetris_GUI.icon);											//设置窗口左上角图标
		jf.setResizable(false);																//固定窗口大小
		jf.setLocationRelativeTo(null);												//设置窗口居中
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//设置窗口关闭操作
		Action_run();																			//调用控制游戏运行主要逻辑的方法开始游戏
	}
	
	//成员变量———————————————————————————————————————————
	private static final Tetris_GUI TP = new Tetris_GUI();		//游戏界面绘制
	//标志游戏状态的成员变量
	private static final int DEFAULTSPEED = 1000;					//规定游戏默认速度
	private static final int EXP = 20;											//规定游戏等级自动提升所需分数间隔
	private static final int[] score_pool = {0, 1, 2, 5, 10};			//分数池，规定消除0，1，2，3，4行所得分数
	protected static final int PLAYING = 1;								//表示游戏状态为正在游戏的常量
	protected static final int PAUSE = 0;									//表示游戏暂停状态的常量
	protected static final int GAMEOVER = 2;							//表示游戏结束状态的常量
	protected static final int MAXIMUMLEVEL = 9;				//规定游戏的最高等级
	protected static final int MINIMUMLEVEL = 0;				//规定游戏的最低等级
	//游戏中记录型成员变量
	protected static int totalscore = 0;		//所得总分
	protected static int totalline = 0;		//消除的总行数
	protected static int game_state;			//游戏状态
	protected static int game_level;			//游戏等级

	//方法————————————————————————————————————————————
	//具有判别功能的一系列方法
	//判断游戏是否结束
	public static boolean Detect_isgameover(){
		BasicBlock[] bbsArr = TP.Shape_nextdrop.getShape();		//获取组成下一个下落形状的方块数组
		for (BasicBlock bbs:bbsArr){													//遍历每个方块，检查即将生成的形状所占有的所有方块位置中有没有已经存在的方块
			int row = bbs.getY();
			int col = bbs.getX();
			if (TP.wall[row][col] != null)												//若有，则说明剩余空间不足以生成一个形状，游戏结束，返回真
				return true;
		}
		return false;																			//没有，说明可以生成下一个形状，游戏继续，返回假
	}
	//判断一行是否铺满
	public static boolean Detect_islinefull(int row){
		for (int col=0; col<TP.NUMOFCOL; col++){						//遍历指定行的每个方块，检查是否有空的方块
			 if (TP.wall[row][col] == null)											//若有，则说明改行未满，返回假
				 return false;
		}
		return true;																			//没有检测到空方块，说明改行已满，返回真
	}
	//判断正在下落的形状能否下落一格
	public static boolean Detect_candrop(){	
		BasicBlock[] bbsArr = TP.Shape_dropping.getShape();		//获取组成正在下落的形状的各个方块的信息
		for (BasicBlock bbs:bbsArr){													//遍历每个方块，判断该形状是否不能下落
			int row = bbs.getY();															//得到当前方块行数
			if (row == 19)																		//判断当前方块是否已经到底部
				return false;																	//是则不能再下落，返回假
			int col = bbs.getX();															//未到底，获取列数
			if (TP.wall[row+1][col] != null)											//判断当前方块下一行对应列是否已有方块
				return false;																	//有方块则不能下落，返回假
		}
		return true;																			//满足可下落条件，返回真
	}
	//判断正在下落形状是否出界
	public static boolean Detect_outofwall(){
		BasicBlock[] bbsArr = TP.Shape_dropping.getShape();		//获取组成正在下落的形状的各个方块的信息
		for (BasicBlock bbs:bbsArr){													//遍历每个方块
			int row = bbs.getY();															//得到当前方块行数
			int col = bbs.getX();															//获取列数
			if (row>19 || row<0 || col<0 || col>11)								//判断是否越界
				return true;																	//越界
		}
		return false;																			//未越界
	}
	//判断下落形状是否与其他方块重合
	public static boolean Detect_iscoincide(){
		BasicBlock[] bbsArr = TP.Shape_dropping.getShape();		//获取组成正在下落的形状的各个方块的信息
		for (BasicBlock bbs:bbsArr){													//遍历每个方块
			int row = bbs.getY();															//得到当前方块行数
			int col = bbs.getX();															//获取列数
			if (TP.wall[row][col] !=null)												//判断该坐标是否已经存在方块
				return true;																	//重合
		}
		return false;																			//未重合
	}
	//界面更新方法
	//对铺满的行进行消除
	//方法中不熟悉的函数说明
	/*
	 * public static void arraycopy(Object src,
     *      									   int srcPos,
     *       									   Object dest,
     *       									   int destPos,
     *       									   int length)
     * Copies an array from the specified source array, beginning at the specified position, 
     * to the specified position of the destination array.
     * Parameters:
     * 		src - the source array.
     * 		srcPos - starting position in the source array.
     * 		dest - the destination array.
     * 		destPos - starting position in the destination data.
     * 		length - the number of array elements to be copied.
	 */
	public static void Action_destroyline(){
		int DestroyedLines = 0;														//记录这次消除的行数
		for (int row=0; row<TP.NUMOFROW; row++){					//对方块墙的每一行进行判断
			if (Detect_islinefull(row)){																//判断是否铺满改行
				DestroyedLines++;														//消除行数+1
				for (int i=row; i>0; i--){													//遍历已被消除行的上方的每一行
					try {
						System.arraycopy(TP.wall[i-1], 0, TP.wall[i], 0, TP.NUMOFCOL);		//将上一行下移一行
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				TP.wall[0] = new BasicBlock[TP.NUMOFCOL];			//消除一行并整体下移后在顶部产生新的一行
			}
		}
		totalscore += score_pool[DestroyedLines];						//计算玩家获得的总分数
		totalline += DestroyedLines;												//计算玩家消除的总行数
		int temp=totalscore/EXP;				    									//根据分数自动提高游戏等级
		if (temp>game_level && temp<10)
			game_level = temp;
	}
	//将正在下落的形状嵌入墙中
	public static void Action_inserttowall(){	
		BasicBlock[] bbsArr = TP.Shape_dropping.getShape();		//获取组成正在下落的形状的各个方块的信息
		for (BasicBlock bbs:bbsArr){													//遍历各个方块
			int row = bbs.getY();
			int col = bbs.getX();
			TP.wall[row][col] = bbs;														//将其嵌入墙中
		}
		Action_destroyline();																			//每当有形状嵌入墙中就检查是否要消除
	}
	//一行一行下落
	public static void Action_softdrop(){	
		if (Detect_candrop()){																			//如果可以下落，下落一格
			TP.Shape_dropping.move_down();
		}
		else{																							//不可以下落
			Action_inserttowall();																		//嵌入墙中
			if (!Detect_isgameover()){																//游戏继续
				TP.Shape_dropping = TP.Shape_nextdrop;
				TP.Shape_nextdrop = BasicShape.random_generate_shape();
			}
			else																						//游戏结束
				game_state = GAMEOVER;
		}
	}
	//直接下落到底
	public static void Action_harddrop(){
		while (Detect_candrop()){																	//若能下落则一直下落
			TP.Shape_dropping.move_down();
		}
		Action_inserttowall();																			//嵌入墙中
		if (!Detect_isgameover()){																	//游戏继续
			TP.Shape_dropping = TP.Shape_nextdrop;
			TP.Shape_nextdrop = BasicShape.random_generate_shape();
		}
		else																							//游戏结束
			game_state = GAMEOVER;
	}
	//右移
	public static void Action_moveright(){
		TP.Shape_dropping.move_right();											//右移
		if (Detect_outofwall() || Detect_iscoincide())													//判断移动是否出错
			TP.Shape_dropping.move_left();										//回滚操作
	}
	//左移
	public static void Action_moveleft(){
		TP.Shape_dropping.move_left();											//左移
		if (Detect_outofwall() || Detect_iscoincide())													//判断移动是否出错
			TP.Shape_dropping.move_right();										//回滚操作
	}
	//旋转
	public static void Action_rorate(){
		TP.Shape_dropping.rorate_cw();											//旋转
		if (Detect_outofwall() || Detect_iscoincide())													//判断旋转是否出错
			TP.Shape_dropping.rorate_ccw();										//回滚操作
	}
	//设置按键监听器
	public static void Action_setkeylistener(){
		KeyListener kl = new KeyAdapter() {									//声明按键监听器
			public void keyPressed (KeyEvent keye){						//重载按键事件
				int kc = keye.getKeyCode();											//获取键值
				switch (kc){
					case KeyEvent.VK_P:													//暂停键
						if (game_state == PLAYING)								//当前游戏状态为正在游戏才能暂停
							game_state = PAUSE;
						break;
					case KeyEvent.VK_C:													//继续游戏键
						if (game_state == PAUSE)									//当前游戏状态为暂停才能恢复游戏
							game_state = PLAYING;
						break;
					case KeyEvent.VK_ENTER:										//重新开始游戏
						game_state = PLAYING;										//置正在游戏状态
						TP.wall = new BasicBlock[TP.NUMOFROW][TP.NUMOFCOL];		//重新生成方块墙
						TP.Shape_dropping = BasicShape.random_generate_shape();		//重新生成正在下落形状
						TP.Shape_nextdrop = BasicShape.random_generate_shape();		//重新生成下一个形状
						totalline = 0;				//清零所消除行数
						totalscore = 0;			//清零所得分数
						game_level = 0;			//恢复游戏默认等级
						break;
					case KeyEvent.VK_UP:						//旋转
						Action_rorate();
						break;
					case KeyEvent.VK_DOWN:				//缓慢下落
						Action_softdrop();
						break;
					case KeyEvent.VK_LEFT:					//左移
						Action_moveleft();
						break;
					case KeyEvent.VK_RIGHT:				//右移
						Action_moveright();
						break;
					case KeyEvent.VK_SPACE:				//直接落地
						Action_harddrop();
						break;
					case KeyEvent.VK_ADD:					//增加游戏等级
						if (game_level < MAXIMUMLEVEL)
							game_level++;
						break;
					case KeyEvent.VK_SUBTRACT:		//减小游戏等级
						if (game_level > MINIMUMLEVEL)
							game_level--;
						break;
					default:												//其他按键什么都不做
						break;
				}
				TP.repaint();											//每监听到一次按键事件都要对画面立即进行重绘
			}
		};
		TP.addKeyListener(kl);			//为当前绘图区域设置按键监听器
		TP.requestFocus();					//设置游戏界面能捕获键盘
	}
	//控制游戏运行主要逻辑的方法
	public static void Action_run(){												//封装了游戏逻辑
		//游戏运行前
		game_state = PLAYING;														//只游戏状态为正在游戏
		game_level = MINIMUMLEVEL;											//游戏等级
		Action_setkeylistener();															//设置监听器
		//游戏运行时
		while (true){
			try {
				Thread.sleep(DEFAULTSPEED-game_level*100);		//控制游戏界面更新速度，即形状下落速度
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (game_state == PLAYING){
				if (Detect_candrop()){												//如果可以下落，形状默认掉落一行
					Action_softdrop();
				}else{
					Action_inserttowall();											//如果不可以下落，说明已经到底，将其嵌入墙中
					//生成下一个形状前判断游戏是否结束
					if (!Detect_isgameover()){									//游戏继续
						TP.Shape_dropping = TP.Shape_nextdrop;
						TP.Shape_nextdrop = BasicShape.random_generate_shape();
					}
					else															//游戏结束
						game_state = GAMEOVER;
				}
				TP.repaint();												//重绘界面
			}
		}
	}
}
