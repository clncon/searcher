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
	  * �����������һ��article����
	  * @param article
	  * @throws Exception
	  */
	  public void addIndex(Article article) throws Exception{
		  
		   //ȡ��indexWriter
		   IndexWriter indexWriter = LuceneUtil.getIndexWriter();
		   
		   
		      
		      
		      //ͨ��indexWriterд��������
		      indexWriter.addDocument(AritcleUtils.articleToDocument(article));
		      
		      
		      //�ر���
		       indexWriter.close();
		      
		   
	  }
	  
	   /**
	    * ����ָ���Ĺؼ���(keywords)�Լ�������ʼ�ĵط�(start),��ѯ�ļ�¼��������ҳ�Ĳ�ѯ������
	    * @param keywords
	    * @param start
	    * @param rows
	    * @return
	    * @throws Exception
	    */
	   public List<Article> searcherIndex(String keywords,int start,int rows) throws Exception{
		   
		      IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
		    
		      
		       //������Ҫ���ڲ�ѯ���ֶ�����
		        String[] fields = {"title","content"};
		        //������ѯ�õ���
		        QueryParser queryParser = new MultiFieldQueryParser(fields, LuceneUtil.getAnalyzer());
		        //��ѯ���Ϲؼ��ֵ�����
		       Query query = queryParser.parse(keywords);
		      //��ʼ����
		      TopDocs topDocs = indexSearcher.search(query, start+rows);
		      
		      System.out.println("�ܼ�¼��:"+topDocs.totalHits);
		       List<Article> list = new ArrayList<Article>();
		       //���ػ���
		        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		          int result  = (start+rows)<(scoreDocs.length)?(start+rows):(scoreDocs.length);
		         //���start+rowsVSscoreDocs.length�Ļ������ؽ�Сֵ
		        for(int i=start;i<result;i++){
		        	  
		        	  Article article = new Article();
		        	  int docID = scoreDocs[i].doc;
		        	  System.out.println("docuemntID======="+docID);
		        	 Document document=indexSearcher.doc(docID);
		        	  
		        	   //��documentת����Article����
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
	     * ����ָ����fieldName,fiedValueɾ������
	     * @param fieldName
	     * @param fieldValue
	     * @throws Exception
	     */
	    public void delIndex(String fieldName,String fieldValue) throws Exception{
	    	
	    	  //ʹ���ֶ������ֶ�ֵ����������
	    	  Term term  = new Term(fieldName,fieldValue);
	    	
	    	  IndexWriter indexWriter = LuceneUtil.getIndexWriter();
	    	  
	    	   //�൱��delete from table where fieldName=fieldValue;
	    	   indexWriter.deleteDocuments(term);
	    	   indexWriter.close();
	    }
	    
	     /**
	      * �ȸ�ɾ��ֵ���ٸ���ֵ
	      * @param fieldName
	      * @param fieldValue
	     * @throws Exception 
	      */
	    public void updateIndex(String fieldName,String fieldValue,Article article) throws Exception{
	    	
	    	//������ѯ������Term
	    	 //Term term  = new Term(fieldName,fieldValue);
	    	
	    	 
	    	  IndexWriter indexWriter = LuceneUtil.getIndexWriter();
	    	  
	    	   indexWriter.addDocument(AritcleUtils.articleToDocument(article));
	    	   indexWriter.close();
	    	  /**
	    	   *  1.������Ҫ���µ�����
	    	   *  2.ȡ����Ҫ���µĶ���
	    	   * 
	    	   */
	     
	    	
	    }
}
