package com.itheima.lucene.test;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TestAnalyzer {

	 /**
	  * 分词测试器
	  * @param args
	  * @throws IOException
	  */
	public static void main(String[] args) throws IOException {
		
		 //单词分词器，会将每个字作为一个单词来进行处理
	    // Analyzer analyzer = new StandardAnalyzer();
		 //二分法分词器，会将两个字作为一个词进行处理
		 //Analyzer analyzer = new CJKAnalyzer();
		
		 //IKAnalyzer,庖丁分词器，可以用户自己扩展的分词器，包括停用词和扩展词
		
		IKAnalyzer analyzer = new IKAnalyzer();
		String text="lucene是传智播客的一个全文检索的高大上的工具包";
		
		
		testAnalzyer(analyzer, text);
	}
	
	
	
	public static void testAnalzyer(Analyzer analyzer,String text) throws IOException{
		
		System.out.println("当前使用的分词器：" + analyzer.getClass().getSimpleName());
		TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
	    tokenStream.addAttribute(CharTermAttribute.class);
	    tokenStream.reset();
	    while (tokenStream.incrementToken()) {
	       CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
	          System.out.println(new String(charTermAttribute.toString()));
	   }

		
		
	}

}
