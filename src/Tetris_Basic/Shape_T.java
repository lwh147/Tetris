/*
 * ��Ϸ�������12*12����
 * ������������T��ͼ�εĸ��������������Ϣ��״̬
 * ���췽����
 * 		ֱ�Ӱ�����ɸ���״���ĸ������������Ϣ�����ݶ�shape���г�ʼ��
 * 		�涨T����״���ĸ���ת״̬����ʼ��states��ָ������״̬�µĸ����������������Ϣ
 */
package Tetris_Basic;

import Tetris_Main.Tetris_GUI;

public class Shape_T extends BasicShape {
	public Shape_T(){
		//����ɸ���״���ĸ������������Ϣ�����ݽ��г�ʼ��
		shape[0] = new BasicBlock(5, 1, Tetris_GUI.T);
		shape[1] = new BasicBlock(4, 1, Tetris_GUI.T);
		shape[2] = new BasicBlock(5, 0, Tetris_GUI.T);
		shape[3] = new BasicBlock(6, 1, Tetris_GUI.T);
		states = new State[4];										//�涨����״������״̬
		states[0] = new State(0, 0, -1, 0, 0, -1, 1, 0);		//��ʼ״̬
		states[1] = new State(0, 0, 0, -1, 1, 0, 0, 1);		//˳ʱ����תһ��״̬���Դ�����
		states[2] = new State(0, 0, 1, 0, 0, 1, -1, 0);
		states[3] = new State(0, 0, 0, 1, -1, 0, 0, -1);
	}
}
