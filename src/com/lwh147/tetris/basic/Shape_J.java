/*
 * ��Ϸ�������12*12����
 * ������������J��ͼ�εĸ��������������Ϣ��״̬
 * ���췽����
 * 		ֱ�Ӱ�����ɸ���״���ĸ������������Ϣ�����ݶ�shape���г�ʼ��
 * 		�涨I����״���ĸ���ת״̬����ʼ��states��ָ������״̬�µĸ����������������Ϣ
 */
package com.lwh147.tetris.basic;

import com.lwh147.tetris.main.Tetris_GUI;

public class Shape_J extends BasicShape {
	public Shape_J(){
		//����ɸ���״���ĸ������������Ϣ�����ݽ��г�ʼ��
		shape[0] = new BasicBlock(5, 1, Tetris_GUI.J);
		shape[1] = new BasicBlock(5, 0, Tetris_GUI.J);
		shape[2] = new BasicBlock(5, 2, Tetris_GUI.J);
		shape[3] = new BasicBlock(4, 2, Tetris_GUI.J);
		states = new State[4];										//�涨����״������״̬
		states[0] = new State(0, 0, 0, -1, 0, 1, -1, 1);		//��ʼ״̬
		states[1] = new State(0, 0, 1, 0, -1, 0, -1, -1);		//˳ʱ����תһ��״̬���Դ�����
		states[2] = new State(0, 0, 0, 1, 0, -1, 1, -1);
		states[3] = new State(0, 0, -1, 0, 1, 0, 1, 1);
	}
}
