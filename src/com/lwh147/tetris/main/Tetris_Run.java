/*
 * ��������������Ϸͼ�ν��沢ʵ�����û��Ľ���
 * ��Ա������
 * 		TP����Ϸ����
 * 		��־��Ϸ״̬�ĳ�Ա����
 * 		��Ϸ�м�¼�ͳ�Ա����
 * ������
 * 		Detect_isgameover���ж���Ϸ�Ƿ����
 * 		Detect_islinefull���ж�һ���Ƿ�����
 * 		Detect_candrop���ж���״�Ƿ��������
 * 		Detect_outofwall���ж���״�Ƿ����
 * 		Detect_iscoincide���ж���״�Ƿ������з����غ�
 * 		Action_inserttowall���䶨����״Ƕ��ǽ��
 * 		Action_destroyline������������һ��
 * 		Action_softdrop������һ��
 * 		Action_harddrop��ֱ�����
 *     Action_moceright������
 *     Action_moveleft������
 *     Action_rorate����ת
 *     Action_setkeylistener�����ð���������
 *     Action_run��������Ϸ���е���Ҫ�߼�
 */
package com.lwh147.tetris.main;

import com.lwh147.tetris.basic.BasicBlock;
import com.lwh147.tetris.basic.BasicShape;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tetris_Run {
	//��Ϸ������ڡ���������������������������������������������������������������������������������
	public static void main(String[] args){
		JFrame jf = new JFrame("Tetris");										//�½���Ϸ����
		jf.add(TP);																				//����Ϸ���໭����ӵ���Ϸ������
		jf.setVisible(true);																	//����������Ϊ�ɼ�
		jf.setSize(665, 688);																	//���ô��ڴ�С
		jf.setIconImage(Tetris_GUI.icon);											//���ô������Ͻ�ͼ��
		jf.setResizable(false);																//�̶����ڴ�С
		jf.setLocationRelativeTo(null);												//���ô��ھ���
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//���ô��ڹرղ���
		Action_run();																			//���ÿ�����Ϸ������Ҫ�߼��ķ�����ʼ��Ϸ
	}
	
	//��Ա������������������������������������������������������������������������������������������
	private static final Tetris_GUI TP = new Tetris_GUI();		//��Ϸ�������
	//��־��Ϸ״̬�ĳ�Ա����
	private static final int DEFAULTSPEED = 1000;					//�涨��ϷĬ���ٶ�
	private static final int EXP = 20;											//�涨��Ϸ�ȼ��Զ���������������
	private static final int[] score_pool = {0, 1, 2, 5, 10};			//�����أ��涨����0��1��2��3��4�����÷���
	protected static final int PLAYING = 1;								//��ʾ��Ϸ״̬Ϊ������Ϸ�ĳ���
	protected static final int PAUSE = 0;									//��ʾ��Ϸ��ͣ״̬�ĳ���
	protected static final int GAMEOVER = 2;							//��ʾ��Ϸ����״̬�ĳ���
	protected static final int MAXIMUMLEVEL = 9;				//�涨��Ϸ����ߵȼ�
	protected static final int MINIMUMLEVEL = 0;				//�涨��Ϸ����͵ȼ�
	//��Ϸ�м�¼�ͳ�Ա����
	protected static int totalscore = 0;		//�����ܷ�
	protected static int totalline = 0;		//������������
	protected static int game_state;			//��Ϸ״̬
	protected static int game_level;			//��Ϸ�ȼ�

	//��������������������������������������������������������������������������������������������
	//�����б��ܵ�һϵ�з���
	//�ж���Ϸ�Ƿ����
	public static boolean Detect_isgameover(){
		BasicBlock[] bbsArr = TP.Shape_nextdrop.getShape();		//��ȡ�����һ��������״�ķ�������
		for (BasicBlock bbs:bbsArr){													//����ÿ�����飬��鼴�����ɵ���״��ռ�е����з���λ������û���Ѿ����ڵķ���
			int row = bbs.getY();
			int col = bbs.getX();
			if (TP.wall[row][col] != null)												//���У���˵��ʣ��ռ䲻��������һ����״����Ϸ������������
				return true;
		}
		return false;																			//û�У�˵������������һ����״����Ϸ���������ؼ�
	}
	//�ж�һ���Ƿ�����
	public static boolean Detect_islinefull(int row){
		for (int col=0; col<TP.NUMOFCOL; col++){						//����ָ���е�ÿ�����飬����Ƿ��пյķ���
			 if (TP.wall[row][col] == null)											//���У���˵������δ�������ؼ�
				 return false;
		}
		return true;																			//û�м�⵽�շ��飬˵������������������
	}
	//�ж������������״�ܷ�����һ��
	public static boolean Detect_candrop(){	
		BasicBlock[] bbsArr = TP.Shape_dropping.getShape();		//��ȡ��������������״�ĸ����������Ϣ
		for (BasicBlock bbs:bbsArr){													//����ÿ�����飬�жϸ���״�Ƿ�������
			int row = bbs.getY();															//�õ���ǰ��������
			if (row == 19)																		//�жϵ�ǰ�����Ƿ��Ѿ����ײ�
				return false;																	//�����������䣬���ؼ�
			int col = bbs.getX();															//δ���ף���ȡ����
			if (TP.wall[row+1][col] != null)											//�жϵ�ǰ������һ�ж�Ӧ���Ƿ����з���
				return false;																	//�з����������䣬���ؼ�
		}
		return true;																			//���������������������
	}
	//�ж�����������״�Ƿ����
	public static boolean Detect_outofwall(){
		BasicBlock[] bbsArr = TP.Shape_dropping.getShape();		//��ȡ��������������״�ĸ����������Ϣ
		for (BasicBlock bbs:bbsArr){													//����ÿ������
			int row = bbs.getY();															//�õ���ǰ��������
			int col = bbs.getX();															//��ȡ����
			if (row>19 || row<0 || col<0 || col>11)								//�ж��Ƿ�Խ��
				return true;																	//Խ��
		}
		return false;																			//δԽ��
	}
	//�ж�������״�Ƿ������������غ�
	public static boolean Detect_iscoincide(){
		BasicBlock[] bbsArr = TP.Shape_dropping.getShape();		//��ȡ��������������״�ĸ����������Ϣ
		for (BasicBlock bbs:bbsArr){													//����ÿ������
			int row = bbs.getY();															//�õ���ǰ��������
			int col = bbs.getX();															//��ȡ����
			if (TP.wall[row][col] !=null)												//�жϸ������Ƿ��Ѿ����ڷ���
				return true;																	//�غ�
		}
		return false;																			//δ�غ�
	}
	//������·���
	//���������н�������
	//�����в���Ϥ�ĺ���˵��
	/*
	 * public static void arraycopy(Object src,
     *      									   int srcPos,
     *       									   Object dest,
     *       									   int destPos,
     *       									   int length)
     * Copies an array from the specified source array, beginning at the specified position, 
     * to the specified position of the destination array.
     * Parameters:
     * 		src - the source array.
     * 		srcPos - starting position in the source array.
     * 		dest - the destination array.
     * 		destPos - starting position in the destination data.
     * 		length - the number of array elements to be copied.
	 */
	public static void Action_destroyline(){
		int DestroyedLines = 0;														//��¼�������������
		for (int row=0; row<TP.NUMOFROW; row++){					//�Է���ǽ��ÿһ�н����ж�
			if (Detect_islinefull(row)){																//�ж��Ƿ���������
				DestroyedLines++;														//��������+1
				for (int i=row; i>0; i--){													//�����ѱ������е��Ϸ���ÿһ��
					try {
						System.arraycopy(TP.wall[i-1], 0, TP.wall[i], 0, TP.NUMOFCOL);		//����һ������һ��
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				TP.wall[0] = new BasicBlock[TP.NUMOFCOL];			//����һ�в��������ƺ��ڶ��������µ�һ��
			}
		}
		totalscore += score_pool[DestroyedLines];						//������һ�õ��ܷ���
		totalline += DestroyedLines;												//�������������������
		int temp=totalscore/EXP;				    									//���ݷ����Զ������Ϸ�ȼ�
		if (temp>game_level && temp<10)
			game_level = temp;
	}
	//�������������״Ƕ��ǽ��
	public static void Action_inserttowall(){	
		BasicBlock[] bbsArr = TP.Shape_dropping.getShape();		//��ȡ��������������״�ĸ����������Ϣ
		for (BasicBlock bbs:bbsArr){													//������������
			int row = bbs.getY();
			int col = bbs.getX();
			TP.wall[row][col] = bbs;														//����Ƕ��ǽ��
		}
		Action_destroyline();																			//ÿ������״Ƕ��ǽ�оͼ���Ƿ�Ҫ����
	}
	//һ��һ������
	public static void Action_softdrop(){	
		if (Detect_candrop()){																			//����������䣬����һ��
			TP.Shape_dropping.move_down();
		}
		else{																							//����������
			Action_inserttowall();																		//Ƕ��ǽ��
			if (!Detect_isgameover()){																//��Ϸ����
				TP.Shape_dropping = TP.Shape_nextdrop;
				TP.Shape_nextdrop = BasicShape.random_generate_shape();
			}
			else																						//��Ϸ����
				game_state = GAMEOVER;
		}
	}
	//ֱ�����䵽��
	public static void Action_harddrop(){
		while (Detect_candrop()){																	//����������һֱ����
			TP.Shape_dropping.move_down();
		}
		Action_inserttowall();																			//Ƕ��ǽ��
		if (!Detect_isgameover()){																	//��Ϸ����
			TP.Shape_dropping = TP.Shape_nextdrop;
			TP.Shape_nextdrop = BasicShape.random_generate_shape();
		}
		else																							//��Ϸ����
			game_state = GAMEOVER;
	}
	//����
	public static void Action_moveright(){
		TP.Shape_dropping.move_right();											//����
		if (Detect_outofwall() || Detect_iscoincide())													//�ж��ƶ��Ƿ����
			TP.Shape_dropping.move_left();										//�ع�����
	}
	//����
	public static void Action_moveleft(){
		TP.Shape_dropping.move_left();											//����
		if (Detect_outofwall() || Detect_iscoincide())													//�ж��ƶ��Ƿ����
			TP.Shape_dropping.move_right();										//�ع�����
	}
	//��ת
	public static void Action_rorate(){
		TP.Shape_dropping.rorate_cw();											//��ת
		if (Detect_outofwall() || Detect_iscoincide())													//�ж���ת�Ƿ����
			TP.Shape_dropping.rorate_ccw();										//�ع�����
	}
	//���ð���������
	public static void Action_setkeylistener(){
		KeyListener kl = new KeyAdapter() {									//��������������
			public void keyPressed (KeyEvent keye){						//���ذ����¼�
				int kc = keye.getKeyCode();											//��ȡ��ֵ
				switch (kc){
					case KeyEvent.VK_P:													//��ͣ��
						if (game_state == PLAYING)								//��ǰ��Ϸ״̬Ϊ������Ϸ������ͣ
							game_state = PAUSE;
						break;
					case KeyEvent.VK_C:													//������Ϸ��
						if (game_state == PAUSE)									//��ǰ��Ϸ״̬Ϊ��ͣ���ָܻ���Ϸ
							game_state = PLAYING;
						break;
					case KeyEvent.VK_ENTER:										//���¿�ʼ��Ϸ
						game_state = PLAYING;										//��������Ϸ״̬
						TP.wall = new BasicBlock[TP.NUMOFROW][TP.NUMOFCOL];		//�������ɷ���ǽ
						TP.Shape_dropping = BasicShape.random_generate_shape();		//������������������״
						TP.Shape_nextdrop = BasicShape.random_generate_shape();		//����������һ����״
						totalline = 0;				//��������������
						totalscore = 0;			//�������÷���
						game_level = 0;			//�ָ���ϷĬ�ϵȼ�
						break;
					case KeyEvent.VK_UP:						//��ת
						Action_rorate();
						break;
					case KeyEvent.VK_DOWN:				//��������
						Action_softdrop();
						break;
					case KeyEvent.VK_LEFT:					//����
						Action_moveleft();
						break;
					case KeyEvent.VK_RIGHT:				//����
						Action_moveright();
						break;
					case KeyEvent.VK_SPACE:				//ֱ�����
						Action_harddrop();
						break;
					case KeyEvent.VK_ADD:					//������Ϸ�ȼ�
						if (game_level < MAXIMUMLEVEL)
							game_level++;
						break;
					case KeyEvent.VK_SUBTRACT:		//��С��Ϸ�ȼ�
						if (game_level > MINIMUMLEVEL)
							game_level--;
						break;
					default:												//��������ʲô������
						break;
				}
				TP.repaint();											//ÿ������һ�ΰ����¼���Ҫ�Ի������������ػ�
			}
		};
		TP.addKeyListener(kl);			//Ϊ��ǰ��ͼ�������ð���������
		TP.requestFocus();					//������Ϸ�����ܲ������
	}
	//������Ϸ������Ҫ�߼��ķ���
	public static void Action_run(){												//��װ����Ϸ�߼�
		//��Ϸ����ǰ
		game_state = PLAYING;														//ֻ��Ϸ״̬Ϊ������Ϸ
		game_level = MINIMUMLEVEL;											//��Ϸ�ȼ�
		Action_setkeylistener();															//���ü�����
		//��Ϸ����ʱ
		while (true){
			try {
				Thread.sleep(DEFAULTSPEED-game_level*100);		//������Ϸ��������ٶȣ�����״�����ٶ�
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (game_state == PLAYING){
				if (Detect_candrop()){												//����������䣬��״Ĭ�ϵ���һ��
					Action_softdrop();
				}else{
					Action_inserttowall();											//������������䣬˵���Ѿ����ף�����Ƕ��ǽ��
					//������һ����״ǰ�ж���Ϸ�Ƿ����
					if (!Detect_isgameover()){									//��Ϸ����
						TP.Shape_dropping = TP.Shape_nextdrop;
						TP.Shape_nextdrop = BasicShape.random_generate_shape();
					}
					else															//��Ϸ����
						game_state = GAMEOVER;
				}
				TP.repaint();												//�ػ����
			}
		}
	}
}
