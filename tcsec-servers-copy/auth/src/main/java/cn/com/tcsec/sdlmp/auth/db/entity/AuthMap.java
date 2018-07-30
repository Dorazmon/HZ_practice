package cn.com.tcsec.sdlmp.auth.db.entity;

import java.util.ArrayList;
import java.util.List;

public class AuthMap {
	private List<AuthObject> parentList = null;

	public AuthMap() {
		super();
	}

	public AuthMap(Auth auth) {
		addParent(auth);
	}

	public void addParent(Auth auth) {
		if (parentList == null) {
			parentList = new ArrayList<AuthObject>();
		}
		AuthObject object = new AuthObject(auth);
		parentList.add(object);
	}

	public AuthObject getParent(String parentId) {
		if (parentList == null || parentId == null) {
			return null;
		}

		for (AuthObject object : parentList) {
			if (parentId.equals(object.getMenuId())) {
				return object;
			}
		}
		return null;
	}

	public List<AuthObject> getObject() {
		return parentList;
	}

	public class AuthObject {
		private String id;
		private String name;
		private String menuId;
		private String sort;
		private String url;
		private List<AuthObject> children = null;
		private List<AuthObject> parent = new ArrayList<AuthObject>(1);
		private String icon;

		public AuthObject(String id, String name, String menuId, String sort, String url, String icon) {
			super();
			this.id = id;
			this.name = name;
			this.menuId = menuId;
			this.sort = sort;
			this.url = url;
			this.icon = icon;
		}

		public AuthObject(Auth auth) {
			this(auth.getId(), auth.getName(), auth.getMenu_id(), auth.getSort(), auth.getUrl(), auth.getIcon());
		}

		public List<AuthObject> getChildren() {
			return children;
		}

		public AuthObject getChildren(String childId) {
			if (this.children == null || childId == null) {
				return null;
			}

			for (AuthObject object : this.children) {
				if (childId.equals(object.getMenuId())) {
					return object;
				}
			}
			return null;
		}

		public void setChildren(List<Auth> children) {
			if (children == null || children.isEmpty()) {
				return;
			}

			for (Auth auth : children) {
				this.children.add(new AuthObject(auth));
			}
		}

		public void addChildren(AuthObject children) {
			if (this.children == null) {
				this.children = new ArrayList<AuthObject>();
			}
			this.children.add(children);
		}

		public void addChildren(Auth children) {
			addChildren(new AuthObject(children));
		}

		public List<AuthObject> getParent() {
			return parent;
		}

		public void setParent(List<AuthObject> parent) {
			this.parent = parent;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMenuId() {
			return menuId;
		}

		public void setMenuId(String menuId) {
			this.menuId = menuId;
		}

		public String getSort() {
			return sort;
		}

		public void setSort(String sort) {
			this.sort = sort;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		@Override
		public String toString() {
			return "AuthObject [id=" + id + ", name=" + name + ", menuId=" + menuId + ", sort=" + sort + ", url=" + url
					+ ", children=" + children + ", parent=" + parent + ", icon=" + icon + "]";
		}

	}

}
