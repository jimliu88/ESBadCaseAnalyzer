package model;

import java.util.List;

import org.json.JSONObject;

public class QueryResultSummary {

	private String keyword;
	private float badcaseRate;
	private int totalCount;
	private JSONObject searchResult;
	private List<ResumeRelatedWithQuery> queryMatchResumes;

	public JSONObject getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(JSONObject searchResult) {
		this.searchResult = searchResult;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public float getBadcaseRate() {
		return badcaseRate;
	}

	public void setBadcaseRate(float badcaseRate) {
		this.badcaseRate = badcaseRate;
	}

	public List<ResumeRelatedWithQuery> getQueryMatchResumes() {
		return queryMatchResumes;
	}

	public void setQueryMatchResumes(List<ResumeRelatedWithQuery> queryMatchResumes) {
		this.queryMatchResumes = queryMatchResumes;
	}

	@Override
	public String toString() {

		return "搜索条件:" + this.getKeyword() + ",BadCase率：" + this.getBadcaseRate() + ",搜索结果总数：" + this.getTotalCount()
				+ ",搜索结果集：" + this.getSearchResult();

	}
}
