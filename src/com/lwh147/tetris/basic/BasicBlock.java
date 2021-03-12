/*
 * ����������Ϣ���õ�����Ļ����ϵ����ȡ��ͼ�������Ͻ�Ϊԭ�㣬����x�������ӣ�����y��������
 * ��������������Ϸ������״�Ļ������Ԫ�ء�������
 * ��Ա������
 * 		x��������
 * 		y��������
 * 		block_image����������ͼƬ
 * ������
 * 		get/set����
 * 		move_left������һ����λ
 * 		move_right������һ����λ
 * 		move_down������һ����λ
 */
package com.lwh147.tetris.basic;

import java.awt.image.BufferedImage;

public class BasicBlock {
	//��Ա����������������������������������������������������������������������������������������
	private int x; // һ������ĺ�����
	private int y; // һ�������������
	private BufferedImage block_image; // һ����������ͼƬ
	
	//��������������������������������������������������������������������������������������������
	// ���췽������
	// �޲ι��캯��
	public BasicBlock() {}
	// �вι��캯��
	public BasicBlock(int _x, int _y, BufferedImage image) {
		x = _x;
		y = _y;
		block_image = image;
	}
	// ����Ļ����ƶ���������
	// ����һ����λ
	public void move_right() {
		x++;
	}
	// ����һ����λ
	public void move_left() {
		x--;
	}
	// ����һ����λ
	public void move_down() {
		y++;
	}
	// ��ȡ�����ó�Ա������get/set����
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public BufferedImage getBlock_image() {
		return block_image;
	}
	public void setBlock_image(BufferedImage block_image) {
		this.block_image = block_image;
	}
}
