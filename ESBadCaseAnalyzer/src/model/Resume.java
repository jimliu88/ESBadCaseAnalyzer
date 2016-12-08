package model;

import java.util.List;

class Work {
	private String id;
	private String func_match;
	private String corp_func_num;
	private String corp_id;
	private List<String> function_tag;
	private String corp_match;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunc_match() {
		return func_match;
	}

	public void setFunc_match(String func_match) {
		this.func_match = func_match;
	}

	public String getCorp_func_num() {
		return corp_func_num;
	}

	public void setCorp_func_num(String corp_func_num) {
		this.corp_func_num = corp_func_num;
	}

	public String getCorp_id() {
		return corp_id;
	}

	public void setCorp_id(String corp_id) {
		this.corp_id = corp_id;
	}

	public List<String> getFunction_tag() {
		return function_tag;
	}

	public void setFunction_tag(List<String> function_tag) {
		this.function_tag = function_tag;
	}

	public String getCorp_match() {
		return corp_match;
	}

	public void setCorp_match(String corp_match) {
		this.corp_match = corp_match;
	}

}

class School {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

public class Resume {

	private String id;
	private float score;
	private List<Work> work;
	private List<String> function;
	private List<String> priority;
	private List<String> rank;
	private School school;
	private List<String> worked_corp_id;
	private List<String> corp_industry_tag;
	private List<String> phone_id;
	private List<String> work_experience;
	private List<String> corp_id;
	private List<String> corp_name;
	private List<String> education;
	private List<String> last_updated_at;
	private List<String> updated_at;
	private int updated_type;
	private List<String> applied_at;
	private List<String> degree_level;
	private List<String> education_type;
	private List<String> will;
	private List<String> avg_duty_month;
	private List<String> internal_corp_id;
	private List<String> worked_internal_corp_id;
	private List<String> show_src;
	private List<String> tobuser_id;
	private String[] title_feature;
	private List<String> title_feature_priority;
	private List<String> desc_feature;
	private List<String> desc_feature_priority;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public List<Work> getWork() {
		return work;
	}

	public void setWork(List<Work> work) {
		this.work = work;
	}

	public List<String> getFunction() {
		return function;
	}

	public void setFunction(List<String> function) {
		this.function = function;
	}

	public List<String> getPriority() {
		return priority;
	}

	public void setPriority(List<String> priority) {
		this.priority = priority;
	}

	public List<String> getRank() {
		return rank;
	}

	public void setRank(List<String> rank) {
		this.rank = rank;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public List<String> getWorked_corp_id() {
		return worked_corp_id;
	}

	public void setWorked_corp_id(List<String> worked_corp_id) {
		this.worked_corp_id = worked_corp_id;
	}

	public List<String> getCorp_industry_tag() {
		return corp_industry_tag;
	}

	public void setCorp_industry_tag(List<String> corp_industry_tag) {
		this.corp_industry_tag = corp_industry_tag;
	}

	public List<String> getPhone_id() {
		return phone_id;
	}

	public void setPhone_id(List<String> phone_id) {
		this.phone_id = phone_id;
	}

	public List<String> getWork_experience() {
		return work_experience;
	}

	public void setWork_experience(List<String> work_experience) {
		this.work_experience = work_experience;
	}

	public List<String> getCorp_id() {
		return corp_id;
	}

	public void setCorp_id(List<String> corp_id) {
		this.corp_id = corp_id;
	}

	public List<String> getCorp_name() {
		return corp_name;
	}

	public void setCorp_name(List<String> corp_name) {
		this.corp_name = corp_name;
	}

	public List<String> getEducation() {
		return education;
	}

	public void setEducation(List<String> education) {
		this.education = education;
	}

	public List<String> getLast_updated_at() {
		return last_updated_at;
	}

	public void setLast_updated_at(List<String> last_updated_at) {
		this.last_updated_at = last_updated_at;
	}

	public List<String> getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(List<String> updated_at) {
		this.updated_at = updated_at;
	}

	public int getUpdated_type() {
		return updated_type;
	}

	public void setUpdated_type(int updated_type) {
		this.updated_type = updated_type;
	}

	public List<String> getApplied_at() {
		return applied_at;
	}

	public void setApplied_at(List<String> applied_at) {
		this.applied_at = applied_at;
	}

	public List<String> getDegree_level() {
		return degree_level;
	}

	public void setDegree_level(List<String> degree_level) {
		this.degree_level = degree_level;
	}

	public List<String> getEducation_type() {
		return education_type;
	}

	public void setEducation_type(List<String> education_type) {
		this.education_type = education_type;
	}

	public List<String> getWill() {
		return will;
	}

	public void setWill(List<String> will) {
		this.will = will;
	}

	public List<String> getAvg_duty_month() {
		return avg_duty_month;
	}

	public void setAvg_duty_month(List<String> avg_duty_month) {
		this.avg_duty_month = avg_duty_month;
	}

	public List<String> getInternal_corp_id() {
		return internal_corp_id;
	}

	public void setInternal_corp_id(List<String> internal_corp_id) {
		this.internal_corp_id = internal_corp_id;
	}

	public List<String> getWorked_internal_corp_id() {
		return worked_internal_corp_id;
	}

	public void setWorked_internal_corp_id(List<String> worked_internal_corp_id) {
		this.worked_internal_corp_id = worked_internal_corp_id;
	}

	public List<String> getShow_src() {
		return show_src;
	}

	public void setShow_src(List<String> show_src) {
		this.show_src = show_src;
	}

	public List<String> getTobuser_id() {
		return tobuser_id;
	}

	public void setTobuser_id(List<String> tobuser_id) {
		this.tobuser_id = tobuser_id;
	}

	public String[] getTitle_feature() {
		return title_feature;
	}

	public void setTitle_feature(String[] title_feature) {
		this.title_feature = title_feature;
	}

	public List<String> getTitle_feature_priority() {
		return title_feature_priority;
	}

	public void setTitle_feature_priority(List<String> title_feature_priority) {
		this.title_feature_priority = title_feature_priority;
	}

	public List<String> getDesc_feature() {
		return desc_feature;
	}

	public void setDesc_feature(List<String> desc_feature) {
		this.desc_feature = desc_feature;
	}

	public List<String> getDesc_feature_priority() {
		return desc_feature_priority;
	}

	public void setDesc_feature_priority(List<String> desc_feature_priority) {
		this.desc_feature_priority = desc_feature_priority;
	}

}
