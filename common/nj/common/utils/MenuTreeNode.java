package nj.common.utils;

import java.util.ArrayList;
import java.util.Map;


public class MenuTreeNode {
	private int id; 
	private int pid; 
	private String text;
	private String state;
	private ArrayList<MenuTreeNode> children = new ArrayList<MenuTreeNode>();
	private ArrayList<Map> attributes = new ArrayList<Map>();
	
	public MenuTreeNode() {
		
	}
	
	public MenuTreeNode(int id, int pid, String text, String state) {
		this.id = id;
		this.pid = pid;
		this.text = text;
		this.state = state;
	}
	
	public void add(MenuTreeNode node) {
		if(0 == node.pid) {
			this.children.add(node);
		}else if(node.pid == this.id) {
			this.children.add(node);
		} else {
			for(MenuTreeNode temp_node:children) {
				temp_node.add(node);
			}
		}
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ArrayList<MenuTreeNode> getChildren() {
		return children;
	}

	public ArrayList<Map> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<Map> attributes) {
		this.attributes = attributes;
	}
	
}
