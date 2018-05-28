package cn.wt.poidemo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class ConfigUtil {
	public static Properties getProperties() {
		String path = Class.class.getClass().getResource("/").getPath() + "dbconfig.properties";
		System.out.println("Begin load database dbconfig.properties");
		try {
			System.out.println(path);
			FileInputStream fis = new FileInputStream(path);
			Properties p = new Properties();
			p.load(fis);
			System.out.println("Done!");
			return p;
		} catch (FileNotFoundException e) {
			System.err.println("该  \"" + path + " \" 路径下的文件没有找到!");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("该  \"" + path + " \" 路径中的文件读取失败!");
			e.printStackTrace();
		}
		return null;
	}

	public static Set<String> getSensitivewords() throws IOException {
		String path = Class.class.getClass().getResource("/").getPath() + "sensitivewords.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
		Set<String> sensitiveWordSet = new HashSet<String>();
		String lineTxt = null;
		while ((lineTxt = br.readLine()) != null) {
			sensitiveWordSet.add(lineTxt);
		}
		br.close();
		return sensitiveWordSet;
	}
	
	public static void main(String args[]) throws IOException {
		Set<String> sensitiveWordSet = getSensitivewords();
		sensitiveWordSet.forEach(s -> {
			System.out.println(s);
		});
	}
}
