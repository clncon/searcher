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
	  * �ִʲ�����
	  * @param args
	  * @throws IOException
	  */
	public static void main(String[] args) throws IOException {
		
		 //���ʷִ������Ὣÿ������Ϊһ�����������д���
	    // Analyzer analyzer = new StandardAnalyzer();
		 //���ַ��ִ������Ὣ��������Ϊһ���ʽ��д���
		 //Analyzer analyzer = new CJKAnalyzer();
		
		 //IKAnalyzer,�Ҷ��ִ����������û��Լ���չ�ķִ���������ͣ�ôʺ���չ��
		
		IKAnalyzer analyzer = new IKAnalyzer();
		String text="lucene�Ǵ��ǲ��͵�һ��ȫ�ļ����ĸߴ��ϵĹ��߰�";
		
		
		testAnalzyer(analyzer, text);
	}
	
	
	
	public static void testAnalzyer(Analyzer analyzer,String text) throws IOException{
		
		System.out.println("��ǰʹ�õķִ�����" + analyzer.getClass().getSimpleName());
		TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
	    tokenStream.addAttribute(CharTermAttribute.class);
	    tokenStream.reset();
	    while (tokenStream.incrementToken()) {
	       CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
	          System.out.println(new String(charTermAttribute.toString()));
	   }

		
		
	}

}
