package analyzer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.json.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.ResumeRelatedWithQuery;
import model.QueryResultSummary;
import model.Resume;
import model.SearchResult;
import searchsimulator.HttpRequestSimulator;
import util.FileUtil;

public class ESBadCaseAnalyzer {

	private final static int EXPECTEDRESULTCOUNT = 30; // 期望搜索结果数
	private final static String PRESEARCHCONDITION = "m=resume&keyword="; // 模拟搜索中，搜索关键字前缀
	private final static String POSTSEARCHCONDITION = "&sort=updated_at_day_desc,field_score&detail=1&count="; // 模拟搜索中，搜索关键字后缀
	private final static String MAXPOSTSEARCHCONDITION="&detail=1&count=10000";
	private final static float SIMILARITYTHRESHOLD = 0.0f; // 搜索query与简历title相似度阈值
	private final static float BADCASERATEWITHOUTTITLE = 1.00f; // 未匹配到相关简历时的badCase率，暂时指定为1
	private final static float BADCASERATETHRESHOLD = 0.60f; // BadCase 阈值
	

	/***
	 * 获得搜索结果的简历Resume ID和Title
	 * 
	 * @param jsonFile
	 */
	private SearchResult doJsonParse(String jsonFile) {

		if (jsonFile == null || jsonFile.isEmpty())
			return null;

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObeject = (JsonObject) jsonParser.parse(jsonFile);

		JsonObject response = jsonObeject.get("response").getAsJsonObject();
		String queryCondition = jsonObeject.get("request").getAsJsonObject().get("keyword").getAsString();
		int totalCount = response.get("results").getAsJsonObject().get("totalcount").getAsInt();

		SearchResult searchResult = new SearchResult();
		searchResult.setQueryCondition(queryCondition);
		searchResult.setTotalcount(totalCount);
		if (totalCount > 0) {
			JsonObject resumeList = response.get("results").getAsJsonObject().get("list").getAsJsonObject();
			Set<Entry<String, JsonElement>> set = resumeList.entrySet();
			String regex = "(?<=\").*?(?=\")";
			List<Resume> resumeLists = new ArrayList<Resume>();

			for (Entry<String, JsonElement> entry : set) {

				Resume resume = new Resume();

				resume.setId(entry.getKey());
				// System.out.println(resume.getId());
				JsonObject detailInfo = entry.getValue().getAsJsonObject();
				// 如果简历包含title，则添加到结果list中
				if (detailInfo.has("title_feature")) {

					String temp = detailInfo.get("title_feature").toString();
					// System.out.println(temp);
					resume.setTitle_feature(doRegularMatch(temp, regex));
					resume.setScore(detailInfo.get("score").getAsFloat());

				} else {
					resume.setTitle_feature(null); // 搜索结果简历中的不包含title_feature时
				}
				resumeLists.add(resume);
				
				//按照score对resume排序
				doObjectSort(resumeLists, "score");
				
			}
			searchResult.setList(resumeLists);
		}
		return searchResult;

	}

	/***
	 * 初步判断搜索结果中的简历与query关键字是否有关系，没有公共字符说明没有关系
	 * 
	 * @param query
	 *            搜索关键字
	 * @param title_feature
	 *            简历title关键字组合
	 */
	private float getRelevanceWithTitle(String query, String[] title_feature) {
		if (query == null || title_feature == null) {
			return 0; // 当简历title为空的时候，此时判断不显示，但实际情况应比较其他属性
		}

		int indexLength = query.length() > title_feature.length ? query.length() : title_feature.length;
		if (indexLength == 0)
			return 0;
		
		int baseCount = 0;
		float totalSimilarity = 0f;
		for (String title : title_feature) {
			// 跟单 "文员"
			baseCount++;
			CosineSimilarity cs = new CosineSimilarity(query, title);
			totalSimilarity += cs.sim();
		}

		totalSimilarity = (float) totalSimilarity / baseCount;
		return totalSimilarity;

	}

	/***
	 * 从json数据中获取某个Element""之间的字符串
	 * 
	 * @param source
	 *            要查找的源字符串
	 * @param regex
	 *            用来匹配的正则表达式
	 * @return 返回 匹配到的结果
	 */
	private String[] doRegularMatch(String source, String regex) {
		ArrayList<String> finds = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			String find = matcher.group(0);
			if (!find.equals(",")) {
				finds.add(matcher.group(0));
			}
		}

		String[] result = new String[finds.size()];
		for (int index = 0; index < finds.size(); index++) {
			result[index] = finds.get(index);
		}
		
		return result;
	}

	/***
	 * 作对象排序
	 * 
	 * @param object
	 * @param sortField
	 */
	@SuppressWarnings("unchecked")
	private <T> void doObjectSort(List<T> object, String sortField) {
		Comparator<Float> mycmpFirst = ComparableComparator.getInstance();
		mycmpFirst = ComparatorUtils.reversedComparator(mycmpFirst); // 逆序

		ArrayList<Object> sortFields = new ArrayList<Object>();
		sortFields.add(new BeanComparator(sortField, mycmpFirst));
		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort(object, multiSort);
	}

	/***
	 * 计算程序运行时间
	 * 
	 * @param start
	 * @param end
	 */
	private void getTimeDifference(Date start, Date end) {

		try {
			// 毫秒ms
			long diff = end.getTime() - start.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print("两个时间相差：");
			System.out.print(diffDays + " 天, ");
			System.out.print(diffHours + " 小时, ");
			System.out.print(diffMinutes + " 分钟, ");
			System.out.print(diffSeconds + " 秒.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * another way to compute badcase count
	 * 
	 * @param query
	 * @param title_feature
	 * @return
	 */
	private boolean hasCommonCharWithTitle(String query, String[] title_feature) {
		if (query == null || query.isEmpty())
			return false;
		if (title_feature == null || title_feature.length == 0)
			return false;

		HashMap<Character, Character> wordsInTitle = new HashMap<Character, Character>();
		char[] words = Arrays.toString(title_feature).toCharArray();
		for (char word : words) {
			if (!wordsInTitle.containsKey(word)) {
				wordsInTitle.put(word, word);
			}
		}
		for (char queryWord : query.toCharArray()) {
			if (wordsInTitle.containsKey(queryWord))
				return true;
		}

		return false;
	}

	/***
	 * badcase 分析
	 * 
	 * @param url
	 *            搜索url
	 * @param querys
	 *            搜索关键字数组， 可以设计为list 或array
	 * @param enode
	 *            输出编码格式，可以指定为gbk（操作系统默认格式）或utf-8
	 */
	public ArrayList<String> doESBadCaseAnalysis(String url, ArrayList<String> querys, String encode) {
		if (querys == null)
			return null;
		
		HttpRequestSimulator httpRS = new HttpRequestSimulator();
		List<QueryResultSummary> queryQuantityList = new ArrayList<QueryResultSummary>();
		ArrayList<String> analysisResultList = new ArrayList<String>();
		ArrayList<String> badCaseQueryList = new ArrayList<String>();
		DecimalFormat fnum = new DecimalFormat("##0.00");	
		int record = 0;

		for (String query : querys) {

			QueryResultSummary queryResultSummary = new QueryResultSummary();
			int badCaseCount = 0;
			List<ResumeRelatedWithQuery> resumeRelatedWithQueryList = new ArrayList<ResumeRelatedWithQuery>();

			try {
				System.out.println(
						"-----------------------------------------------------------------------------------------------");
				System.out.println("No." + (++record) + "\t模拟搜索关键字：" + query);
				String searchResultRaw = httpRS.doGet(url,
						PRESEARCHCONDITION + query.replaceAll(" ", "%20") +POSTSEARCHCONDITION+ EXPECTEDRESULTCOUNT);// 模拟搜索,应该改成多线程
				queryResultSummary.setKeyword(query);// 暂时用全部搜索条件

				queryResultSummary.setSearchResult(new JSONObject(searchResultRaw));
				System.out.println("搜索结果处理中......");
				SearchResult searchResultNew = doJsonParse(searchResultRaw);

				int resumeCountInResult = Math.min(searchResultNew.getTotalcount(), EXPECTEDRESULTCOUNT);				
				
				System.out.println("匹配到简历记录" + resumeCountInResult + "条");
				queryResultSummary.setTotalCount(searchResultNew.getTotalcount());

				if (resumeCountInResult > 0) {
					int topN=resumeCountInResult;
					System.out.println("搜索结果分析中.......");
			
						for (Resume resume : searchResultNew.getList()) {
							
								System.out.println(resume.getScore());
								
								float relevanceWithQuery = 0f;
								// resumeTitles.append(resume.getTitle_feature());
								System.out.println("正在分析简历:" + resume.getId() + "\t" + "简历标题描述："
										+ Arrays.toString(resume.getTitle_feature()));
								ResumeRelatedWithQuery queryMatcheResume = new ResumeRelatedWithQuery();
								String[] titlesDesc = resume.getTitle_feature();
								queryMatcheResume.setResumeTitleDesc(titlesDesc);

								query = query.replaceAll("[()（）,，.。：;；！!‘’“”*\"\'\\pS]", " ");// 去掉query关键词中的标点符号和其他特殊符号
								queryMatcheResume.setQuery(query);

								relevanceWithQuery = getRelevanceWithTitle(query, titlesDesc); // 计算query与简历title相关性
								queryMatcheResume.setRelevance(relevanceWithQuery);
								queryMatcheResume.setResumeID(resume.getId());
								queryMatcheResume.setScore(resume.getScore());
								
								if (relevanceWithQuery - SIMILARITYTHRESHOLD <= 0.00000001) {

									badCaseCount++;// 但简历title_feature为空的时候，也会算作badcase，
								}
								resumeRelatedWithQueryList.add(queryMatcheResume);
							queryResultSummary.setBadcaseRate((float) badCaseCount / resumeCountInResult);
							
						
					}
					

				} else {

					ResumeRelatedWithQuery queryMatcheResume = new ResumeRelatedWithQuery();
					queryMatcheResume.setQuery(query);
					queryMatcheResume.setRelevance(0f);// 这里暂时设置为0
					queryMatcheResume.setResumeID("未找到相关简历");
					queryMatcheResume.setResumeTitleDesc(null);
					queryMatcheResume.setScore(0f);
					resumeRelatedWithQueryList.add(queryMatcheResume);
					queryResultSummary.setBadcaseRate(BADCASERATEWITHOUTTITLE); // 无搜索结果返回时，暂时设定badcaserate为2.0
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("BadCase数目：" + badCaseCount);

			ESBadCaseAnalyzer esBCA = new ESBadCaseAnalyzer();
			esBCA.doObjectSort(resumeRelatedWithQueryList, "score");
			queryResultSummary.setQueryMatchResumes(resumeRelatedWithQueryList);
			queryQuantityList.add(queryResultSummary);
			esBCA.doObjectSort(queryQuantityList, "badcaseRate");

			System.out.println(
					"-----------------------------------------------------------------------------------------------");

		}

		for (QueryResultSummary qq : queryQuantityList) {
			float badCaseRate = qq.getBadcaseRate();
			int line = 0;
			for (ResumeRelatedWithQuery qmr : qq.getQueryMatchResumes()) {

				if (line == 0)
					analysisResultList.add("NotMatchRate: " + fnum.format(badCaseRate) + "\t" + qmr.toString());
				else
					analysisResultList.add("\t" + qmr.toString());
				line++;
			}

			if (badCaseRate > BADCASERATETHRESHOLD) {
				badCaseQueryList.add(qq.getKeyword());
			}
		}

		FileUtil saveBadCaseFile = new FileUtil();
		saveBadCaseFile.writeFile("./data/badCase.txt", badCaseQueryList);

		System.out.println(
				"-----------------------------------------------------------------------------------------------");

		return analysisResultList;
	}

	public static void main(String[] args) {
		ESBadCaseAnalyzer esbac = new ESBadCaseAnalyzer();
		FileUtil fileUtil = new FileUtil();
		
		// TODO Auto-generated method stub
		String keywordsList = "./data/queryhistory.txt";
		String analysisResultFilePath = "./data/queryAnlysisResult";
		String url = "http://127.0.0.1:6688/";
		
		Date start = new Date();

		ArrayList<String> queryList = fileUtil.readFile(keywordsList);
		Calendar calendar = Calendar.getInstance();
		String analysisResultFileName = String.valueOf(analysisResultFilePath + calendar.getTimeInMillis())
				+ ".txt";
		fileUtil.writeFile(analysisResultFileName, esbac.doESBadCaseAnalysis(url, queryList, args[0]));

		Date end = new Date();

		System.out.print("分析完成,耗时:");
		esbac.getTimeDifference(start, end);
	
	}

	

}
