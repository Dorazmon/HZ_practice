package cn.com.tcsec.jdk.exercise_6;

import java.util.ArrayList;
import java.util.List;

public class SkipNode {
	public int data;
	public List<SkipNode> nodes;
	public SkipNode(){
	  nodes=new ArrayList<>();	
	}

	public SkipNode(int data) {
		this.data = data;
		nodes = new ArrayList<>();
	}

	// public SkipNode(int level) {
	// for (int i = 0; i < level; i++) {
	// SkipNode skipNode = new SkipNode();
	// nodes = new ArrayList<>();
	// nodes.add(skipNode);
	// }

	// }
}
