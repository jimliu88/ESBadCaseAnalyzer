package showresult;

import java.util.*;
import java.io.*;

public class ShowResult {

	public void generateShowPage(String content) {
		try {

			// 模板路径
			String filePath = "./htmltemplate/template.html";
			// System.out.print(filePath);

			String templateContent = "";
			FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
			int lenght = fileinputstream.available();
			byte bytes[] = new byte[lenght];
			fileinputstream.read(bytes);
			fileinputstream.close();
			templateContent = new String(bytes);

			templateContent = templateContent.replaceAll("###content###", content);

			// 根据时间得文件名
			Calendar calendar = Calendar.getInstance();
			String fileame = String.valueOf("BadCaseAnalysis" + calendar.getTimeInMillis()) + ".html";
			fileame = "/" + fileame;// 生成的html文件保存路径。
			FileOutputStream fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流

			byte tag_bytes[] = templateContent.getBytes();
			fileoutputstream.write(tag_bytes);
			fileoutputstream.close();

		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}

	public static void main(String[] args) {

	}

}
