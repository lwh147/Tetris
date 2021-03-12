/*
 * 游戏区域采用12*12方格
 * 该类用来描述O型图形的各个方块的坐标信息及状态
 * 构造方法：
 * 		直接按照组成该形状的四个方块的坐标信息及内容对shape进行初始化
 * 		规定O型形状只有一个旋转状态，初始化states，指定一种状态下的各个方块相对坐标信息
 */
package com.lwh147.tetris.basic;

import com.lwh147.tetris.main.Tetris_GUI;

public class Shape_O extends BasicShape {
	public Shape_O(){
		//对组成该形状的四个方块的坐标信息及内容进行初始化
		shape[0] = new BasicBlock(5, 0, Tetris_GUI.O);		
		shape[1] = new BasicBlock(6, 0, Tetris_GUI.O);
		shape[2] = new BasicBlock(6, 1, Tetris_GUI.O);
		shape[3] = new BasicBlock(5, 1, Tetris_GUI.O);
		states = new State[1];									//规定该形状只有一种状态
		states[0] = new State(0, 0, 1, 0, 1, 1, 0, 1);		//初始状态
	}
}
