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
 * 使用solrJ来调用solr服务
 * @author km
 *
 */
public class SolrJ {
	
	 
	/**
	 * 使用solr向索引库添加一条数据
	 * @throws SolrServerException
	 * @throws IOException
	 */
	 @Test
	public void addIndex() throws SolrServerException, IOException{
	
		 //连接的Url
		 String urlString  = "http://localhost:8983/solr/mycore";
		 
		  //创建solr客户端
		 SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		 
		 //创建 SolrInputDocument对象
		// SolrInputDocument document = new SolrInputDocument();
		 
		 //第一种方式添加数据，(使用字段)
		    //添加字段
		    //document.addField("id", "9527");
		   //document.addField("name", "啦啦");
		   //document.addField("content", "solr是个好东西");
		  
		   
		 //添加document
		// solr.add(document);
		 
		 //第二种方式添加数据(使用对象集合)
		 List<Article> list = new ArrayList<Article>();
		 
	
		 for(int i=30;i<=60;i++){
			 
			 Article article = new Article();
			 article.setId(i);
			 article.setName("武淑静");
			 article.setTitle("是一个很有文艺范的名字");
			 article.setPrice("1354"+i);
			 article.setContent("亭亭玉立的女孩");
			 
			 list.add(article);
			 
		 }
		    
		     //添加对象集合
		    solr.addBeans(list);
		 
		 
		 
		 //第三种方式添加数据
//		    Article article = new Article();
//		    
//		     article.setId(26);
//		     article.setName("高大上");
//		     article.setTitle("白富美");
//		     article.setContent("高大上白富美");
//		    
//		      
//		     //添加对象
//		      solr.addBean(article);
		     //提交服务器
		    solr.commit();
           
		    
		    
		   
		   
		 
		 
	}
	 
	 
	  //删除索引
	   @Test
	  public void delIndexByID() throws SolrServerException, IOException{
		  
		   String urlString = "http://localhost:8983/solr/mycore";
		   
		   SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		   
		    solr.deleteById("26");
		    
		    solr.commit();
		   
		   
		    
	  }
	   
	   
	   @Test
	   /**
	    * 向索引库添加数据时，如果id和索引库id重复那么就会更新索引库的数据，可以用于更新数据库数据
	    * @throws IOException
	    * @throws SolrServerException
	    */
	  public  void updateIndex() throws IOException, SolrServerException{
		  String urlString = "http://localhost:8983/solr/mycore";
		   
		   SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		   
		      Article article = new Article();
		      
		       article.setId(1);
		       article.setName("张云");
		       article.setPrice("564");
		       article.setTitle("新名字");
		       article.setContent("好听的新名字");
		       
		       solr.addBean(article);
		       
		       solr.commit();
		   
		   
	  }
	   
	   
	   
	   
	    @Test
	    public void testFind() throws SolrServerException, IOException{
	    	 String urlString = "http://localhost:8983/solr/mycore";
			   
			 SolrClient solr = new HttpSolrClient.Builder(urlString).build(); 
			 
			   
			    //使用这个对象做查询
			    SolrQuery params  = new SolrQuery();  
			    params.setQuery("name:武淑静");
			    
			    //分页，默认是分页从0开始，每页显示10行
			    params.setStart(0);
			    params.setRows(30);
			    
			     //开启高亮
			     params.setHighlight(true);
			     //高亮的格式化
			      params.setHighlightSimplePre("<font color='red'>");
			      params.setHighlightSimplePost("</font>");
			      
			      //我需要高亮的字段进行高亮显示
			       //注意这里是hl.fl不是h1.f1;
			       params.setParam("hl.fl", "name");
			    QueryResponse queryResponse =solr.query(params);
			     //拿到数据集合,返回查询结果
			    List<SolrDocument> list =queryResponse.getResults();
			    
			    
			    //拿到所有高亮的结果
			    
               Map<String, Map<String, List<String>>> mapList = queryResponse.getHighlighting();
			    //循环打印数据集合
			    
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
