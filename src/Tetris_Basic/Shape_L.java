/*
 * ��Ϸ�������12*12����
 * ������������L��ͼ�εĸ��������������Ϣ��״̬
 * ���췽����
 * 		ֱ�Ӱ�����ɸ���״���ĸ������������Ϣ�����ݶ�shape���г�ʼ��
 * 		�涨L����״���ĸ���ת״̬����ʼ��states��ָ������״̬�µĸ����������������Ϣ
 */
package Tetris_Basic;

import Tetris_Main.Tetris_GUI;

public class Shape_L extends BasicShape {
	public Shape_L(){
		//����ɸ���״���ĸ������������Ϣ�����ݽ��г�ʼ��
		shape[0] = new BasicBlock(5, 1, Tetris_GUI.L);
		shape[1] = new BasicBlock(5, 0, Tetris_GUI.L);
		shape[2] = new BasicBlock(5, 2, Tetris_GUI.L);
		shape[3] = new BasicBlock(6, 2, Tetris_GUI.L);
		states = new State[4];										//�涨����״������״̬
		states[0] = new State(0, 0, 0, -1, 0, 1, 1, 1);		//��ʼ״̬
		states[1] = new State(0, 0, 1, 0, -1, 0, -1, 1);		//˳ʱ����תһ��״̬���Դ�����
		states[2] = new State(0, 0, 0, 1, 0, -1, -1, -1);
		states[3] = new State(0, 0, -1, 0, 1, 0, 1, -1);
	}
}
