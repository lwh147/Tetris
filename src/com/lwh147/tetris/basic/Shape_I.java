/*
 * ��Ϸ�������12*12����
 * ������������I��ͼ�εĸ��������������Ϣ��״̬
 * ���췽����
 * 		ֱ�Ӱ�����ɸ���״���ĸ������������Ϣ�����ݶ�shape���г�ʼ��
 * 		�涨I����״ֻ��������ת״̬����ʼ��states��ָ������״̬�µĸ����������������Ϣ
 */
package com.lwh147.tetris.basic;

import com.lwh147.tetris.main.Tetris_GUI;

public class Shape_I extends BasicShape {
	public Shape_I(){
		//����ɸ���״���ĸ������������Ϣ�����ݽ��г�ʼ��
		shape[0] = new BasicBlock(5, 1, Tetris_GUI.I);
		shape[1] = new BasicBlock(5, 0, Tetris_GUI.I);
		shape[2] = new BasicBlock(5,2, Tetris_GUI.I);
		shape[3] = new BasicBlock(5,3, Tetris_GUI.I);
		states = new State[2];											//�涨����״ֻ������״̬
		states[0] = new State(0, 0, 0, -1, 0, 1, 0, 2);			//��ʼ״̬
		states[1] = new State(0, 0, 1, 0, -1, 0, -2, 0);			//˳ʱ����תһ��״̬
	}
}
