package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileUtil {

	/***
	 * 按行读取
	 * 
	 * @param inputPath
	 * @return
	 */
	public ArrayList<String> readFile(String inputPath) {
		ArrayList<String> result = new ArrayList<String>();
		// TODO
		File file = new File(inputPath);
		// 流水式输入
		BufferedReader bReader = null;

		String lineContent = null;
		try {
			InputStreamReader inputFile = new InputStreamReader(new FileInputStream(file));
			bReader = new BufferedReader(inputFile);
			while ((lineContent = bReader.readLine()) != null) {
				// System.out.println(lineContent);
				result.add(lineContent);
			}
			bReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bReader.close();
			} catch (IOException e) {
				// TODO
			}
		}

		return result;
	}

	/***
	 * 
	 * @param outputPath
	 * @param encode
	 * @return
	 */
	public void writeFile(String outputPath, ArrayList<String> outputString) {
		// TODO
		File outputFile = new File(outputPath);
		if (outputFile.exists())
			outputFile.delete();
		BufferedWriter bWriter = null;
		int lines = outputString.size();
		try {
			bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile, false))); // append
			for (int index = 0; index < lines; index++) {
				if (index < lines - 1) {
					bWriter.write(outputString.get(index) + "\n");
				} else {
					bWriter.write(outputString.get(index));
				}
			}
		} catch (Exception e) {
			// TODO
		} finally {
			try {
				bWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		/*
		 * String file="./data/queryhistory.txt"; FileUtil fUtil=new FileUtil();
		 * fUtil.readFile(file); //System.out.println((fUtil.readFile(file)));
		 * 
		 * String outputFile="./data/queryAnlysisResult.txt";
		 * fUtil.writeFile(outputFile, fUtil.readFile(file));
		 */
		String str = "完美世界(北京)网络技术有限公司 游戏特效 1 男 3-3年 27-27岁";
		str = str.replaceAll("[（）(),，.。：;；！!‘’“”*\"\'\\pS]", " ");
		System.out.println(str);

	}
}
