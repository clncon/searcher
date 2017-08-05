package com.itheima.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.DefaultSolrParams;
import org.apache.solr.common.params.HighlightParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;

import com.itheima.bean.Article;


/**
 * ʹ��solrJ������solr����
 * @author km
 *
 */
public class SolrJ {
	
	 
	/**
	 * ʹ��solr�����������һ������
	 * @throws SolrServerException
	 * @throws IOException
	 */
	 @Test
	public void addIndex() throws SolrServerException, IOException{
	
		 //���ӵ�Url
		 String urlString  = "http://localhost:8983/solr/mycore";
		 
		  //����solr�ͻ���
		 SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		 
		 //���� SolrInputDocument����
		// SolrInputDocument document = new SolrInputDocument();
		 
		 //��һ�ַ�ʽ������ݣ�(ʹ���ֶ�)
		    //����ֶ�
		    //document.addField("id", "9527");
		   //document.addField("name", "����");
		   //document.addField("content", "solr�Ǹ��ö���");
		  
		   
		 //���document
		// solr.add(document);
		 
		 //�ڶ��ַ�ʽ�������(ʹ�ö��󼯺�)
		 List<Article> list = new ArrayList<Article>();
		 
	
		 for(int i=30;i<=60;i++){
			 
			 Article article = new Article();
			 article.setId(i);
			 article.setName("���羲");
			 article.setTitle("��һ���������շ�������");
			 article.setPrice("1354"+i);
			 article.setContent("ͤͤ������Ů��");
			 
			 list.add(article);
			 
		 }
		    
		     //��Ӷ��󼯺�
		    solr.addBeans(list);
		 
		 
		 
		 //�����ַ�ʽ�������
//		    Article article = new Article();
//		    
//		     article.setId(26);
//		     article.setName("�ߴ���");
//		     article.setTitle("�׸���");
//		     article.setContent("�ߴ��ϰ׸���");
//		    
//		      
//		     //��Ӷ���
//		      solr.addBean(article);
		     //�ύ������
		    solr.commit();
           
		    
		    
		   
		   
		 
		 
	}
	 
	 
	  //ɾ������
	   @Test
	  public void delIndexByID() throws SolrServerException, IOException{
		  
		   String urlString = "http://localhost:8983/solr/mycore";
		   
		   SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		   
		    solr.deleteById("26");
		    
		    solr.commit();
		   
		   
		    
	  }
	   
	   
	   @Test
	   /**
	    * ���������������ʱ�����id��������id�ظ���ô�ͻ��������������ݣ��������ڸ������ݿ�����
	    * @throws IOException
	    * @throws SolrServerException
	    */
	  public  void updateIndex() throws IOException, SolrServerException{
		  String urlString = "http://localhost:8983/solr/mycore";
		   
		   SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		   
		      Article article = new Article();
		      
		       article.setId(1);
		       article.setName("����");
		       article.setPrice("564");
		       article.setTitle("������");
		       article.setContent("������������");
		       
		       solr.addBean(article);
		       
		       solr.commit();
		   
		   
	  }
	   
	   
	   
	   
	    @Test
	    public void testFind() throws SolrServerException, IOException{
	    	 String urlString = "http://localhost:8983/solr/mycore";
			   
			 SolrClient solr = new HttpSolrClient.Builder(urlString).build(); 
			 
			   
			    //ʹ�������������ѯ
			    SolrQuery params  = new SolrQuery();  
			    params.setQuery("name:���羲");
			    
			    //��ҳ��Ĭ���Ƿ�ҳ��0��ʼ��ÿҳ��ʾ10��
			    params.setStart(0);
			    params.setRows(30);
			    
			     //��������
			     params.setHighlight(true);
			     //�����ĸ�ʽ��
			      params.setHighlightSimplePre("<font color='red'>");
			      params.setHighlightSimplePost("</font>");
			      
			      //����Ҫ�������ֶν��и�����ʾ
			       //ע��������hl.fl����h1.f1;
			       params.setParam("hl.fl", "name");
			    QueryResponse queryResponse =solr.query(params);
			     //�õ����ݼ���,���ز�ѯ���
			    List<SolrDocument> list =queryResponse.getResults();
			    
			    
			    //�õ����и����Ľ��
			    
               Map<String, Map<String, List<String>>> mapList = queryResponse.getHighlighting();
			    //ѭ����ӡ���ݼ���
			    
			    for(SolrDocument solrDocument : list){
			    	 Object id = solrDocument.get("id");
			    	 Object content = solrDocument.get("content");
			    	System.out.println(id.toString());
			    	System.out.println(content.toString());
			    	
			    	
			    	Map<String, List<String>> map = mapList.get(id);
			    	
			        List<String> fieldList = map.get("name");
			        
			        System.out.println(fieldList);
			    	
			    	
			    }
	    }

}
