/*
 * 所有坐标信息采用的是屏幕坐标系，即取绘图区域左上角为原点，向右x坐标增加，向下y坐标增加
 * 该类用来描述游戏七种形状的基本组成元素——方块
 * 成员变量：
 * 		x：横坐标
 * 		y：纵坐标
 * 		block_image：方块的填充图片
 * 方法：
 * 		get/set方法
 * 		move_left：左移一个单位
 * 		move_right：右移一个单位
 * 		move_down：下移一个单位
 */
package com.lwh147.tetris.basic;

import java.awt.image.BufferedImage;

public class BasicBlock {
	//成员变量——————————————————————————————————————————
	private int x; // 一个方块的横坐标
	private int y; // 一个方块的纵坐标
	private BufferedImage block_image; // 一个方块的填充图片
	
	//方法————————————————————————————————————————————
	// 构造方法定义
	// 无参构造函数
	public BasicBlock() {}
	// 有参构造函数
	public BasicBlock(int _x, int _y, BufferedImage image) {
		x = _x;
		y = _y;
		block_image = image;
	}
	// 方块的基本移动方法定义
	// 右移一个单位
	public void move_right() {
		x++;
	}
	// 左移一个单位
	public void move_left() {
		x--;
	}
	// 下移一个单位
	public void move_down() {
		y++;
	}
	// 获取和设置成员变量的get/set方法
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public BufferedImage getBlock_image() {
		return block_image;
	}
	public void setBlock_image(BufferedImage block_image) {
		this.block_image = block_image;
	}
}
