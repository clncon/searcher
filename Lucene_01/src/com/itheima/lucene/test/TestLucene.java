package com.itheima.lucene.test;

import java.util.List;

import org.junit.Test;

import com.itheima.lucene.dao.LuceneDao;
import com.itheima.lucene.domain.Article;
import com.itheima.lucene.util.LuceneUtil;

public class TestLucene {
	
	 LuceneDao luceneDao = new LuceneDao();
	
	 @Test
	 public void testAddIndex() throws Exception{
	  	 
		 for(int i=30;i<=30;i++){
		   Article article = new Article();
		    article.setId(i);
		    article.setAuthor("ŷ������ĳС��");
		    article.setContent("�������������������������������ܣ����ڼ���ĵ����ϰ���CTRL+C��Ȼ���ڹ�˾�ĵ������ٰ�CTRL+V�ǿ϶����еġ���ʹͬһƪ����Ҳ���С����������ĵ��Զ����С�");
		    article.setLink("www.itheima.com");
		    article.setTitle("lucene��һ�����ڽ���ȫ�ļ����Ĺ���");
		    luceneDao.addIndex(article);
		 }
		    
	 }
	  @Test
	  public void testIndexSeacher() throws Exception{
		  
		     List<Article> list = luceneDao.searcherIndex("����",0,10);
		     
		     for(Article article : list){
		    	 System.out.println(article.getId());
		    	 System.out.println(article.getAuthor());
		    	 System.out.println(article.getTitle());
		    	 System.out.println(article.getContent());
		    	 System.out.println(article.getLink());
		     }
	  }
	  
	   
	     @Test
	    public void testDelIndex() throws Exception{
	    	 luceneDao.delIndex("title", "��");
	    }
	     
	      @Test
	     public void testUpdateIndex() throws Exception{
	    	  Article article = new Article();
	    	   article.setId(26);
	    	   article.setTitle("����һ��java����Ա");
	    	   article.setAuthor("����");
	    	   article.setContent("����һ�������˺ܶ���ĳ���Ա��һֱ�Ƕ��ǳ���Ա");
	    	   article.setLink("www.itcast.com");
	    	  luceneDao.addIndex(article);
	     }

}
