/*
 * 游戏区域采用12*12方格
 * 该类用来描述I型图形的各个方块的坐标信息及状态
 * 构造方法：
 * 		直接按照组成该形状的四个方块的坐标信息及内容对shape进行初始化
 * 		规定I型形状只有两个旋转状态，初始化states，指定两种状态下的各个方块相对坐标信息
 */
package com.lwh147.tetris.basic;

import com.lwh147.tetris.main.Tetris_GUI;

public class Shape_I extends BasicShape {
	public Shape_I(){
		//对组成该形状的四个方块的坐标信息及内容进行初始化
		shape[0] = new BasicBlock(5, 1, Tetris_GUI.I);
		shape[1] = new BasicBlock(5, 0, Tetris_GUI.I);
		shape[2] = new BasicBlock(5,2, Tetris_GUI.I);
		shape[3] = new BasicBlock(5,3, Tetris_GUI.I);
		states = new State[2];											//规定该形状只有两种状态
		states[0] = new State(0, 0, 0, -1, 0, 1, 0, 2);			//初始状态
		states[1] = new State(0, 0, 1, 0, -1, 0, -2, 0);			//顺时针旋转一次状态
	}
}
