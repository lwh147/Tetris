/*
 * ��Ϸ�������12*12����
 * ������������O��ͼ�εĸ��������������Ϣ��״̬
 * ���췽����
 * 		ֱ�Ӱ�����ɸ���״���ĸ������������Ϣ�����ݶ�shape���г�ʼ��
 * 		�涨O����״ֻ��һ����ת״̬����ʼ��states��ָ��һ��״̬�µĸ����������������Ϣ
 */
package com.lwh147.tetris.basic;

import com.lwh147.tetris.main.Tetris_GUI;

public class Shape_O extends BasicShape {
	public Shape_O(){
		//����ɸ���״���ĸ������������Ϣ�����ݽ��г�ʼ��
		shape[0] = new BasicBlock(5, 0, Tetris_GUI.O);		
		shape[1] = new BasicBlock(6, 0, Tetris_GUI.O);
		shape[2] = new BasicBlock(6, 1, Tetris_GUI.O);
		shape[3] = new BasicBlock(5, 1, Tetris_GUI.O);
		states = new State[1];									//�涨����״ֻ��һ��״̬
		states[0] = new State(0, 0, 1, 0, 1, 1, 0, 1);		//��ʼ״̬
	}
}
