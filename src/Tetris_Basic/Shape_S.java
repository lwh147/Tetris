/*
 * ��Ϸ�������12*12����
 * ������������S��ͼ�εĸ��������������Ϣ��״̬
 * ���췽����
 * 		ֱ�Ӱ�����ɸ���״���ĸ������������Ϣ�����ݶ�shape���г�ʼ��
 * 		�涨S����״���ĸ���ת״̬����ʼ��states��ָ������״̬�µĸ����������������Ϣ
 */
package Tetris_Basic;

import Tetris_Main.Tetris_GUI;

public class Shape_S extends BasicShape {
	public Shape_S(){
		//����ɸ���״���ĸ������������Ϣ�����ݽ��г�ʼ��
		shape[0] = new BasicBlock(5, 1, Tetris_GUI.S);
		shape[1] = new BasicBlock(4, 1, Tetris_GUI.S);
		shape[2] = new BasicBlock(5, 0, Tetris_GUI.S);
		shape[3] = new BasicBlock(6, 0, Tetris_GUI.S);
		states = new State[4];										//�涨����״������״̬
		states[0] = new State(0, 0, -1, 0, 0, -1, 1, -1);		//��ʼ״̬
		states[1] = new State(0, 0, 0, -1, 1, 0, 1, 1);		//˳ʱ����תһ��״̬���Դ�����
		states[2] = new State(0, 0, 1, 0, 0, 1, -1, 1);
		states[3] = new State(0, 0, 0, 1, -1, 0, -1, -1);
	}
}
