package cn.com.tcsec.jdk.exercise_6;

import java.util.Random;

public class SkipList {
	private SkipNode head;
	private int Maxlevel;
	private int length = 0;
	private Random random = new Random();

	public SkipList(int Maxlevel) {
		int i;
		this.Maxlevel = Maxlevel;
		head = new SkipNode();
	}

	// 添加一个节点
	public void addSkipList(int data) {
		int i;
		int newLevel = 0;
		int num = random.nextInt((int) (Math.pow(2, Maxlevel)));
		SkipNode newSkipNode = new SkipNode(data);
		SkipNode tem = head;
		SkipNode twoTem = head;
		SkipNode oldTem = head;
		System.out.println("插入一个新值" + data);
		System.out.println("产生的随机数" + num);
		for (i = 0; i < Maxlevel + 1; i++) {
			if (num < ((Math.pow(2, i)))) {
				newLevel = Maxlevel - i;
				break;
			}
		}
		System.out.println("新插入的节点在第" + newLevel + "层");

		// 第一次插入，插入的结点为头结点
		if (tem.nodes.size() == 0) {
			for (i = 0; i < newLevel + 1; i++) {
				tem.nodes.add(i, newSkipNode);
			}
			length++;
		} else {
			// 添加的节点不是头结点
			for (i = 0; i < newLevel + 1; i++) {
				while (twoTem.data < data) {
					oldTem = twoTem;
					twoTem = twoTem.nodes.get(i);
					if (twoTem.nodes.size() == 0) {
						if (data < twoTem.data) {
							oldTem.nodes.set(i, newSkipNode);
							newSkipNode.nodes.add(i, twoTem);
						} else {
							twoTem.nodes.add(i, newSkipNode);
							length++;
							break;
						}
					}
					if (twoTem.data > data) {
						oldTem.nodes.set(i, newSkipNode);
						newSkipNode.nodes.add(i, twoTem);
						length++;
						break;
					}
				}

			}
		}
	}

	// 查找所有的节点
	public void selectAll() {
		SkipNode tem = head;
		for (int i = 0; i < length; i++) {
			tem = tem.nodes.get(0);
			System.out.println(tem.data);

		}

	}

}
