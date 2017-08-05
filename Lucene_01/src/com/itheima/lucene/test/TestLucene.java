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
		    article.setAuthor("欧阳夏文某小文");
		    article.setContent("是这样的张总张总张总张总张总张总，妳在家里的电脑上按了CTRL+C，然后在公司的电脑上再按CTRL+V是肯定不行的。即使同一篇文章也不行。不不，多贵的电脑都不行。");
		    article.setLink("www.itheima.com");
		    article.setTitle("lucene是一个用于进行全文检索的工具");
		    luceneDao.addIndex(article);
		 }
		    
	 }
	  @Test
	  public void testIndexSeacher() throws Exception{
		  
		     List<Article> list = luceneDao.searcherIndex("张总",0,10);
		     
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
	    	 luceneDao.delIndex("title", "张");
	    }
	     
	      @Test
	     public void testUpdateIndex() throws Exception{
	    	  Article article = new Article();
	    	   article.setId(26);
	    	   article.setTitle("我是一名java程序员");
	    	   article.setAuthor("李四");
	    	   article.setContent("我是一名工作了很多年的程序员，一直是都是程序员");
	    	   article.setLink("www.itcast.com");
	    	  luceneDao.addIndex(article);
	     }

}
