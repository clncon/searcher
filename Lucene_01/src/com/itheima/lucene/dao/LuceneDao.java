package com.itheima.lucene.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.itheima.lucene.domain.Article;
import com.itheima.lucene.util.AritcleUtils;
import com.itheima.lucene.util.LuceneUtil;

public class LuceneDao {
	
	
	 /**
	  * 向索引库添加一个article索引
	  * @param article
	  * @throws Exception
	  */
	  public void addIndex(Article article) throws Exception{
		  
		   //取得indexWriter
		   IndexWriter indexWriter = LuceneUtil.getIndexWriter();
		   
		   
		      
		      
		      //通过indexWriter写入索引库
		      indexWriter.addDocument(AritcleUtils.articleToDocument(article));
		      
		      
		      //关闭流
		       indexWriter.close();
		      
		   
	  }
	  
	   /**
	    * 根据指定的关键词(keywords)以及索引开始的地方(start),查询的记录数，来分页的查询索引库
	    * @param keywords
	    * @param start
	    * @param rows
	    * @return
	    * @throws Exception
	    */
	   public List<Article> searcherIndex(String keywords,int start,int rows) throws Exception{
		   
		      IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
		    
		      
		       //创建需要用于查询的字段数组
		        String[] fields = {"title","content"};
		        //创建查询用的类
		        QueryParser queryParser = new MultiFieldQueryParser(fields, LuceneUtil.getAnalyzer());
		        //查询符合关键字的数据
		       Query query = queryParser.parse(keywords);
		      //开始插叙
		      TopDocs topDocs = indexSearcher.search(query, start+rows);
		      
		      System.out.println("总记录数:"+topDocs.totalHits);
		       List<Article> list = new ArrayList<Article>();
		       //返回击中
		        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		          int result  = (start+rows)<(scoreDocs.length)?(start+rows):(scoreDocs.length);
		         //如果start+rowsVSscoreDocs.length的话，返回较小值
		        for(int i=start;i<result;i++){
		        	  
		        	  Article article = new Article();
		        	  int docID = scoreDocs[i].doc;
		        	  System.out.println("docuemntID======="+docID);
		        	 Document document=indexSearcher.doc(docID);
		        	  
		        	   //将document转换成Article对象
		        	   int id = Integer.parseInt(document.get("id"));
		        	    article.setId(id);
		        	    article.setTitle(document.get("title"));
		        	    article.setAuthor(document.get("author"));
		        	    article.setContent(document.get("content"));
		        	    article.setLink(document.get("link"));
		        	    
		        	    list.add(article);
		        	    
		        }
		        
		   return list;
	   }

	   
	   
	    /**
	     * 根据指定的fieldName,fiedValue删除索引
	     * @param fieldName
	     * @param fieldValue
	     * @throws Exception
	     */
	    public void delIndex(String fieldName,String fieldValue) throws Exception{
	    	
	    	  //使用字段名和字段值来创建词条
	    	  Term term  = new Term(fieldName,fieldValue);
	    	
	    	  IndexWriter indexWriter = LuceneUtil.getIndexWriter();
	    	  
	    	   //相当于delete from table where fieldName=fieldValue;
	    	   indexWriter.deleteDocuments(term);
	    	   indexWriter.close();
	    }
	    
	     /**
	      * 先更删除值，再更新值
	      * @param fieldName
	      * @param fieldValue
	     * @throws Exception 
	      */
	    public void updateIndex(String fieldName,String fieldValue,Article article) throws Exception{
	    	
	    	//给出查询的条件Term
	    	 //Term term  = new Term(fieldName,fieldValue);
	    	
	    	 
	    	  IndexWriter indexWriter = LuceneUtil.getIndexWriter();
	    	  
	    	   indexWriter.addDocument(AritcleUtils.articleToDocument(article));
	    	   indexWriter.close();
	    	  /**
	    	   *  1.设置需要更新的条件
	    	   *  2.取得需要更新的对象
	    	   * 
	    	   */
	     
	    	
	    }
}
