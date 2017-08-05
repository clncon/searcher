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
		//创建需要用于查询的字段数组
        String[] fields = {"title","content"};
        //创建查询用的类
        QueryParser queryParser = new MultiFieldQueryParser(fields, LuceneUtil.getAnalyzer());
        //查询符合关键字的数据
       Query query = queryParser.parse("张");
       
       //格式化高亮的字符串
          Formatter formatter  = new SimpleHTMLFormatter("<font color='red'>", "</font>");
          
          //query里面的条件，条件里面有搜索关键词
           Scorer fragmentScorer = new QueryScorer(query);
           
           
        //构建高亮插寻
           /**
            * 1.需要高亮什么颜色
            * 2.将那些关键词进行高亮
            */
         Highlighter highlighter  = new Highlighter(formatter, fragmentScorer);
      //开始插叙
      TopDocs topDocs = indexSearcher.search(query,100);
      
      System.out.println("总记录数:"+topDocs.totalHits);
       List<Article> list = new ArrayList<Article>();
       //返回击中
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
          
         //如果start+rowsVSscoreDocs.length的话，返回较小值
        for(ScoreDoc scoreDoc:scoreDocs){
        	  
        	  Article article = new Article();
        	  int docID = scoreDoc.doc;
        	  //System.out.println("docuemntID======="+docID);
        	 Document document=indexSearcher.doc(docID);
        	 String title = document.get("title");
        	 String content = document.get("content");
        	 System.out.println("没有高亮的title:"+title);
        	 System.out.println("没有高亮的content:"+content);
        	 
        	 
        	 //将某段文本进行高亮，返回高亮的结果
        	 String highTitle = highlighter.getBestFragment(LuceneUtil.getAnalyzer(), "title",title);
        	 String highContent= highlighter.getBestFragment(LuceneUtil.getAnalyzer(), "content",content);
        	  
        	  //打印高亮后字符串
        	 
        	  if(highTitle==null){
        		  highTitle=title;
        	  }
        	  if(highContent==null){
        		  highContent=content;
        	  }
        	  
        	  System.out.println("高亮后字符串 title:"+highTitle);
        	  System.out.println("高亮后字符串  content:"+highContent);
        	   
        	    
        }
        
	}

}
