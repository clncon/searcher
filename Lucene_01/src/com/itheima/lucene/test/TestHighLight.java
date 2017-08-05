package com.itheima.lucene.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import com.itheima.lucene.domain.Article;
import com.itheima.lucene.util.LuceneUtil;

public class TestHighLight {

	public static void main(String[] args) throws Exception {
		
		
		 testHighLight();

	}
	
	
	
	
	
	public static void testHighLight() throws Exception{
		 IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
		//������Ҫ���ڲ�ѯ���ֶ�����
        String[] fields = {"title","content"};
        //������ѯ�õ���
        QueryParser queryParser = new MultiFieldQueryParser(fields, LuceneUtil.getAnalyzer());
        //��ѯ���Ϲؼ��ֵ�����
       Query query = queryParser.parse("��");
       
       //��ʽ���������ַ���
          Formatter formatter  = new SimpleHTMLFormatter("<font color='red'>", "</font>");
          
          //query��������������������������ؼ���
           Scorer fragmentScorer = new QueryScorer(query);
           
           
        //����������Ѱ
           /**
            * 1.��Ҫ����ʲô��ɫ
            * 2.����Щ�ؼ��ʽ��и���
            */
         Highlighter highlighter  = new Highlighter(formatter, fragmentScorer);
      //��ʼ����
      TopDocs topDocs = indexSearcher.search(query,100);
      
      System.out.println("�ܼ�¼��:"+topDocs.totalHits);
       List<Article> list = new ArrayList<Article>();
       //���ػ���
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
          
         //���start+rowsVSscoreDocs.length�Ļ������ؽ�Сֵ
        for(ScoreDoc scoreDoc:scoreDocs){
        	  
        	  Article article = new Article();
        	  int docID = scoreDoc.doc;
        	  //System.out.println("docuemntID======="+docID);
        	 Document document=indexSearcher.doc(docID);
        	 String title = document.get("title");
        	 String content = document.get("content");
        	 System.out.println("û�и�����title:"+title);
        	 System.out.println("û�и�����content:"+content);
        	 
        	 
        	 //��ĳ���ı����и��������ظ����Ľ��
        	 String highTitle = highlighter.getBestFragment(LuceneUtil.getAnalyzer(), "title",title);
        	 String highContent= highlighter.getBestFragment(LuceneUtil.getAnalyzer(), "content",content);
        	  
        	  //��ӡ�������ַ���
        	 
        	  if(highTitle==null){
        		  highTitle=title;
        	  }
        	  if(highContent==null){
        		  highContent=content;
        	  }
        	  
        	  System.out.println("�������ַ��� title:"+highTitle);
        	  System.out.println("�������ַ���  content:"+highContent);
        	   
        	    
        }
        
	}

}
