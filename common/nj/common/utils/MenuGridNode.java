package nj.common.utils;

import java.util.ArrayList;

public class MenuGridNode {

	private int id; 
	private int pid; 
	private String name; 
	private String href;
	private String operatecode; 
	private ArrayList<MenuGridNode> children = new ArrayList<MenuGridNode>(); 
	
	public MenuGridNode() {
		
	}
	
	public MenuGridNode(int id, int pid, String name, String href,String operatecode) {
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.href = href;
		this.operatecode = operatecode;
	}
	
	public void add(MenuGridNode node) {
		if(0 == node.pid) {
			this.children.add(node);
		}else if(node.pid == this.id) {
			this.children.add(node);
		} else {
			for(MenuGridNode temp_node:children) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getOperatecode() {
		return operatecode;
	}

	public void setOperatecode(String operatecode) {
		this.operatecode = operatecode;
	}

	public ArrayList<MenuGridNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<MenuGridNode> children) {
		this.children = children;
	}
	
}
