/*
 * 游戏区域采用12*12方格
 * 该类用来描述S型图形的各个方块的坐标信息及状态
 * 构造方法：
 * 		直接按照组成该形状的四个方块的坐标信息及内容对shape进行初始化
 * 		规定S型形状有四个旋转状态，初始化states，指定四种状态下的各个方块相对坐标信息
 */
package com.lwh147.tetris.basic;

import com.lwh147.tetris.main.Tetris_GUI;

public class Shape_S extends BasicShape {
	public Shape_S(){
		//对组成该形状的四个方块的坐标信息及内容进行初始化
		shape[0] = new BasicBlock(5, 1, Tetris_GUI.S);
		shape[1] = new BasicBlock(4, 1, Tetris_GUI.S);
		shape[2] = new BasicBlock(5, 0, Tetris_GUI.S);
		shape[3] = new BasicBlock(6, 0, Tetris_GUI.S);
		states = new State[4];										//规定该形状有四种状态
		states[0] = new State(0, 0, -1, 0, 0, -1, 1, -1);		//初始状态
		states[1] = new State(0, 0, 0, -1, 1, 0, 1, 1);		//顺时针旋转一次状态，以此类推
		states[2] = new State(0, 0, 1, 0, 0, 1, -1, 1);
		states[3] = new State(0, 0, 0, 1, -1, 0, -1, -1);
	}
}
