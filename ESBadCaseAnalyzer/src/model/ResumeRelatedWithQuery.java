package model;

import java.util.Arrays;

public class ResumeRelatedWithQuery {

	private String query;
	private String resumeID;
	private float relevance;
	private float score;
	private String[] resumeTitleDesc;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getResumeID() {
		return resumeID;
	}

	public void setResumeID(String resumeID) {
		this.resumeID = resumeID;
	}

	public float getRelevance() {
		return relevance;
	}

	public void setRelevance(float relevance) {
		this.relevance = relevance;
	}

	public String[] getResumeTitleDesc() {
		return resumeTitleDesc;
	}

	public void setResumeTitleDesc(String[] resumeTitleDesc) {
		this.resumeTitleDesc = resumeTitleDesc;
	}
	
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "QueryCondition: " + this.getQuery() + "\tResume Title Desc: "
				+ Arrays.toString(this.getResumeTitleDesc()) +"\tScore: " + this.getScore()+ "\tRelevance: " + this.getRelevance() + "\tResume ID："
				+ this.getResumeID();
	}

}
