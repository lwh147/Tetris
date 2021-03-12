/*
 * 该类用来描述游戏中七种形状的一般组成和操作
 * 成员变量：
 * 		shape：描述形状的四个方块的数组
 * 		states：形状的状态描述数组（数组成员为内部类）
 * 		rorate_times：正常旋转次数
 * 		（class）State：记录形状在不同旋转状态下各个方块相对于0号方块的坐标信息的内部类
 * 方法：
 * 		get/set方法
 * 		move_right：形状右移
 * 		move_left：形状左移
 * 		move_down：形状下移
 * 		rorate_cw：形状顺时针旋转
 *		rorate_ccw：形状逆时针旋转
 *		random_generate_shape：随机生成一种形状
 */
package com.lwh147.tetris.basic;


public class BasicShape {
	//成员变量———————————————————————————————————————————
	protected BasicBlock[] shape = new BasicBlock[4];// 由四个基本方块组成一个形状，下标0表示旋转轴方块
	protected State[] states;// 描述该形状的不同旋转状态下各个方块相对于0号方块(旋转轴)的坐标信息
	/*
	 * 一种形状最多有四种旋转状态，最少没有
	 * 下标0	：形状的初始状态信息
	 * 下标1：顺时针旋转一次的状态信息，以此类推
	 */
	private int rorate_times=0;// 正常旋转次数
	
	//方法—————————————————————————————————————————————
	// 定义基本形状的各种行为：右移、左移、下移、旋转
	// 右移
	public void move_right() { 	
		for (BasicBlock bbs : shape) { 							// 将组成该形状的每个基本方块都右移一个单位
			bbs.move_right();
		}
	}
	// 左移
	public void move_left() {
		for (BasicBlock bbs : shape) {
			bbs.move_left();
		}
	}
	// 下移
	public void move_down() {
		for (BasicBlock bbs : shape) {
			bbs.move_down();
		}
	}
	//旋转
	// 顺时针旋转
	public void rorate_cw() {
		rorate_times++;												//此时应该旋转到下一个状态，次数+1
		State s=states[rorate_times%states.length];		//因为最多只有四种状态，所以此处采用取余的方法实现循环旋转
		//获取旋转轴方块（即0号方块）的坐标信息
		BasicBlock bbs=shape[0];
		int x0=bbs.getX();	
		int y0=bbs.getY();
		//进行旋转，分别对组成该形状的其余三个方块进行坐标设置
		shape[1].setX(x0+s.x1); shape[1].setY(y0+s.y1);
		shape[2].setX(x0+s.x2); shape[2].setY(y0+s.y2);
		shape[3].setX(x0+s.x3); shape[3].setY(y0+s.y3);
	}
	// 逆时针旋转（一旦顺时针旋转后出现了错误则通过逆时针旋转来恢复）
	public void rorate_ccw() {
		rorate_times--;													//说明上次旋转不正常，此时应该旋转到上一个状态，次数-1
		State s=states[rorate_times%states.length];		//因为最多只有四种状态，所以此处采用取余的方法实现循环旋转
		//获取旋转轴方块（即0号方块）的坐标信息
		BasicBlock bbs=shape[0];
		int x0=bbs.getX();	
		int y0=bbs.getY();
		//进行旋转，分别对组成该形状的其余三个方块进行坐标设置
		shape[1].setX(x0+s.x1); shape[1].setY(y0+s.y1);
		shape[2].setX(x0+s.x2); shape[2].setY(y0+s.y2);
		shape[3].setX(x0+s.x3); shape[3].setY(y0+s.y3);
	}
	//随机生成一个形状
	public static BasicShape random_generate_shape(){
		BasicShape bbs_random_generate = null;
		int random = (int) (Math.random() * 7);			//产生0-6中的一个随机数来控制随机生成哪种形状
		switch (random) {
			case 0:
				bbs_random_generate = new Shape_I();
				break;
			case 1:
				bbs_random_generate = new Shape_J();
				break;
			case 2:
				bbs_random_generate = new Shape_L();
				break;
			case 3:
				bbs_random_generate = new Shape_O();
				break;
			case 4:
				bbs_random_generate = new Shape_S();
				break;
			case 5:
				bbs_random_generate = new Shape_T();
				break;
			case 6:
				bbs_random_generate = new Shape_Z();
				break;
		}
		return bbs_random_generate;
	}
	//获取和设置成员变量的get/set方法
	public BasicBlock[] getShape() {
		return shape;
	}
	public void setShape(BasicBlock[] shape) {
		this.shape = shape;
	}
	public State[] getStates() {
		return states;
	}
	public void setStates(State[] states) {
		this.states = states;
	}
	/*
	 * 内部类，描述形状的不同状态下各个方块相对于0号方块(旋转轴)的坐标信息
	 * 成员：
	 * 		0-3号方块的相对坐标信息
	 * 方法：
	 * 		get/set方法
	 */
	public class State {
		// 成员变量————————————————————————
		private int x0, y0;		//0号方块（旋转轴）的相对坐标，横为（0，0）
		private int x1, y1;		//1号方块相对旋转轴（0号方块）的坐标
		private int x2, y2;		//2号方块相对旋转轴（0号方块）的坐标
		private int x3, y3;		//3号方块相对旋转轴（0号方块）的坐标

		//方法——————————————————————————
		// 构造方法
		// 无参构造方法
		public State() {}
		// 有参构造方法
		public State(int _x0, int _y0, 
							int _x1, int _y1, 
							int _x2, int _y2, 
							int _x3, int _y3) {
			x0 = _x0; y0 = _y0;
			x1 = _x1; y1 = _y1;
			x2 = _x2; y2 = _y2;
			x3 = _x3; y3 = _y3;
		}
		// 获取和设置成员变量的get/set方法
		public int getX0() {
			return x0;
		}
		public void setX0(int x0) {
			this.x0 = x0;
		}
		public int getY0() {
			return y0;
		}
		public void setY0(int y0) {
			this.y0 = y0;
		}
		public int getX1() {
			return x1;
		}
		public void setX1(int x1) {
			this.x1 = x1;
		}
		public int getY1() {
			return y1;
		}
		public void setY1(int y1) {
			this.y1 = y1;
		}
		public int getX2() {
			return x2;
		}
		public void setX2(int x2) {
			this.x2 = x2;
		}
		public int getY2() {
			return y2;
		}
		public void setY2(int y2) {
			this.y2 = y2;
		}
		public int getX3() {
			return x3;
		}
		public void setX3(int x3) {
			this.x3 = x3;
		}
		public int getY3() {
			return y3;
		}
		public void setY3(int y3) {
			this.y3 = y3;
		}
	}
}