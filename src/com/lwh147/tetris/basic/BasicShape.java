/*
 * ��������������Ϸ��������״��һ����ɺͲ���
 * ��Ա������
 * 		shape��������״���ĸ����������
 * 		states����״��״̬�������飨�����ԱΪ�ڲ��ࣩ
 * 		rorate_times��������ת����
 * 		��class��State����¼��״�ڲ�ͬ��ת״̬�¸������������0�ŷ����������Ϣ���ڲ���
 * ������
 * 		get/set����
 * 		move_right����״����
 * 		move_left����״����
 * 		move_down����״����
 * 		rorate_cw����״˳ʱ����ת
 *		rorate_ccw����״��ʱ����ת
 *		random_generate_shape���������һ����״
 */
package com.lwh147.tetris.basic;


public class BasicShape {
	//��Ա������������������������������������������������������������������������������������������
	protected BasicBlock[] shape = new BasicBlock[4];// ���ĸ������������һ����״���±�0��ʾ��ת�᷽��
	protected State[] states;// ��������״�Ĳ�ͬ��ת״̬�¸������������0�ŷ���(��ת��)��������Ϣ
	/*
	 * һ����״�����������ת״̬������û��
	 * �±�0	����״�ĳ�ʼ״̬��Ϣ
	 * �±�1��˳ʱ����תһ�ε�״̬��Ϣ���Դ�����
	 */
	private int rorate_times=0;// ������ת����
	
	//����������������������������������������������������������������������������������������������
	// ���������״�ĸ�����Ϊ�����ơ����ơ����ơ���ת
	// ����
	public void move_right() { 	
		for (BasicBlock bbs : shape) { 							// ����ɸ���״��ÿ���������鶼����һ����λ
			bbs.move_right();
		}
	}
	// ����
	public void move_left() {
		for (BasicBlock bbs : shape) {
			bbs.move_left();
		}
	}
	// ����
	public void move_down() {
		for (BasicBlock bbs : shape) {
			bbs.move_down();
		}
	}
	//��ת
	// ˳ʱ����ת
	public void rorate_cw() {
		rorate_times++;												//��ʱӦ����ת����һ��״̬������+1
		State s=states[rorate_times%states.length];		//��Ϊ���ֻ������״̬�����Դ˴�����ȡ��ķ���ʵ��ѭ����ת
		//��ȡ��ת�᷽�飨��0�ŷ��飩��������Ϣ
		BasicBlock bbs=shape[0];
		int x0=bbs.getX();	
		int y0=bbs.getY();
		//������ת���ֱ����ɸ���״�������������������������
		shape[1].setX(x0+s.x1); shape[1].setY(y0+s.y1);
		shape[2].setX(x0+s.x2); shape[2].setY(y0+s.y2);
		shape[3].setX(x0+s.x3); shape[3].setY(y0+s.y3);
	}
	// ��ʱ����ת��һ��˳ʱ����ת������˴�����ͨ����ʱ����ת���ָ���
	public void rorate_ccw() {
		rorate_times--;													//˵���ϴ���ת����������ʱӦ����ת����һ��״̬������-1
		State s=states[rorate_times%states.length];		//��Ϊ���ֻ������״̬�����Դ˴�����ȡ��ķ���ʵ��ѭ����ת
		//��ȡ��ת�᷽�飨��0�ŷ��飩��������Ϣ
		BasicBlock bbs=shape[0];
		int x0=bbs.getX();	
		int y0=bbs.getY();
		//������ת���ֱ����ɸ���״�������������������������
		shape[1].setX(x0+s.x1); shape[1].setY(y0+s.y1);
		shape[2].setX(x0+s.x2); shape[2].setY(y0+s.y2);
		shape[3].setX(x0+s.x3); shape[3].setY(y0+s.y3);
	}
	//�������һ����״
	public static BasicShape random_generate_shape(){
		BasicShape bbs_random_generate = null;
		int random = (int) (Math.random() * 7);			//����0-6�е�һ��������������������������״
		switch (random) {
			case 0:
				bbs_random_generate = new Shape_I();
				break;
			case 1:
				bbs_random_generate = new Shape_J();
				break;
			case 2:
				bbs_random_generate = new Shape_L();
				break;
			case 3:
				bbs_random_generate = new Shape_O();
				break;
			case 4:
				bbs_random_generate = new Shape_S();
				break;
			case 5:
				bbs_random_generate = new Shape_T();
				break;
			case 6:
				bbs_random_generate = new Shape_Z();
				break;
		}
		return bbs_random_generate;
	}
	//��ȡ�����ó�Ա������get/set����
	public BasicBlock[] getShape() {
		return shape;
	}
	public void setShape(BasicBlock[] shape) {
		this.shape = shape;
	}
	public State[] getStates() {
		return states;
	}
	public void setStates(State[] states) {
		this.states = states;
	}
	/*
	 * �ڲ��࣬������״�Ĳ�ͬ״̬�¸������������0�ŷ���(��ת��)��������Ϣ
	 * ��Ա��
	 * 		0-3�ŷ�������������Ϣ
	 * ������
	 * 		get/set����
	 */
	public class State {
		// ��Ա����������������������������������������������������
		private int x0, y0;		//0�ŷ��飨��ת�ᣩ��������꣬��Ϊ��0��0��
		private int x1, y1;		//1�ŷ��������ת�ᣨ0�ŷ��飩������
		private int x2, y2;		//2�ŷ��������ת�ᣨ0�ŷ��飩������
		private int x3, y3;		//3�ŷ��������ת�ᣨ0�ŷ��飩������

		//��������������������������������������������������������
		// ���췽��
		// �޲ι��췽��
		public State() {}
		// �вι��췽��
		public State(int _x0, int _y0, 
							int _x1, int _y1, 
							int _x2, int _y2, 
							int _x3, int _y3) {
			x0 = _x0; y0 = _y0;
			x1 = _x1; y1 = _y1;
			x2 = _x2; y2 = _y2;
			x3 = _x3; y3 = _y3;
		}
		// ��ȡ�����ó�Ա������get/set����
		public int getX0() {
			return x0;
		}
		public void setX0(int x0) {
			this.x0 = x0;
		}
		public int getY0() {
			return y0;
		}
		public void setY0(int y0) {
			this.y0 = y0;
		}
		public int getX1() {
			return x1;
		}
		public void setX1(int x1) {
			this.x1 = x1;
		}
		public int getY1() {
			return y1;
		}
		public void setY1(int y1) {
			this.y1 = y1;
		}
		public int getX2() {
			return x2;
		}
		public void setX2(int x2) {
			this.x2 = x2;
		}
		public int getY2() {
			return y2;
		}
		public void setY2(int y2) {
			this.y2 = y2;
		}
		public int getX3() {
			return x3;
		}
		public void setX3(int x3) {
			this.x3 = x3;
		}
		public int getY3() {
			return y3;
		}
		public void setY3(int y3) {
			this.y3 = y3;
		}
	}
}