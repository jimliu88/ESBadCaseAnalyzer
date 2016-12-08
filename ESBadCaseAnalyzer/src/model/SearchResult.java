package model;

import java.util.List;

public class SearchResult {
	private String queryCondition;
	private int totalcount;
	private String sort;
	private List<Resume> list;

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<Resume> getList() {
		return list;
	}

	public void setList(List<Resume> list) {
		this.list = list;
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	// facet
	// highlight
	// highlightPro
}
