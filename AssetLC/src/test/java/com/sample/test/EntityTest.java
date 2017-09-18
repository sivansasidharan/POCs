package com.sample.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ss.nitro.analytics.assetlc.domain.Nodes;
import com.ss.nitro.analytics.assetlc.domain.OnLoad;
import com.ss.nitro.analytics.assetlc.domain.Sankey;
import com.ss.nitro.analytics.assetlc.domain.SankeyJson;
import com.ss.nitro.analytics.assetlc.service.EntityService;

public class EntityTest {
	   private static Nodes name;
	private static List<Nodes> node;


	public static void main(String[] args) {
		   @SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("entity.xml");		
		    @SuppressWarnings("unused")
			EntityService service = (EntityService) context.getBean("EntityService");
		    
//		    Input - null
//		    List<CallOne> sample = service.popAssetDetails();
//		    returned values -list of totalAssetValue, totalAssetCount

		    
//		    Input - enitity, serviceline, subserviceline
//		    List<CallOne> sample2 = service.popAssetdetailsCond("EYGBS","Core Business Services","ESS");
//		    returned values -list of year, totalAssetValue, totalAssetCount

//		    Input - enitity, serviceline, subserviceline
//		    If one of the filters is not applied pass '.*' as the parameter like given below
//		    List<CallOne> sample3 = service.popAssetdetailsCond("EYGBS",".*",".*");
//		    returned values -list of year, totalAssetValue, totalAssetCount

		    
//			Region and country is set as 'all' in this query, EMEA and India are the only values available
//		    Input - enitity, serviceline, subserviceline, region, country, city, location
//		    List<CallOne> sample4 = service.popAssetdetailsCondRegion("EYGBS","Core Business Services","ESS",".*",".*",".*",".*");
//		    returned values -list of year, totalAssetValue, totalAssetCount

		    
//			Region and country is set as all in this query, EMEA and India are the only values available
//		    Input - enitity, serviceline, subserviceline, region, country, city, location, year
//		    List<CallOne> sample5 = service.popAssetdetailsCondYear("EYGBS","Core Business Services","ESS",".*",".*",".*",".*","2012");
//		    returned values -list of distinct sl, ssl, totalAssetValue, totalAssetCount
//
//		    List<CallOne> sample5 = service.popAssetdetailsCondYear("EYLLP","Core Business Services","TSS / IT",
//		    		"EMEA","India","Bangalore","RMZ Infinity","2014");

//		    Region and country in this call cannot be null
//		    Input - enitity, serviceline, subserviceline, region, country, city, location, year
//		    returned values -list of distinct  ssl, brand, vendor, product, year, totalAssetValue, totalAssetCount


//		    Input - ProductCategory, Qtr, ServiceLine, Attribute
//		    Prediction sample7 = service.popPrediction("Communication Device", "2016.*", "ESS", "Quantity" );
//		    returned value - integer
		    
		        
//		    System.out.println("total Asset Count= "  + sample5.get(0).getTotalAssetCount()); 
//		    System.out.println("total Asset Value= "  + sample5.get(0).getTotalAssetValue());
//		    System.out.println("Po Amount "  + sample5.get(0).getPoAmount());
//		    System.out.println("size= "  + sample5.size());
//		    System.out.println("SSL= "  + sample5.get(0).getSsl());
//		    System.out.println("SL= "  + sample5.get(0).getSl());
//		    System.out.println("total Asset Count= "  + sample5.get(0).getBrand());
//		    System.out.println("forecast = "  + sample7.getForecast());

		    
		    
		    
//		    List<Sankey> links = allSankey("EYGBS","Core Business Services","ESS","EMEA","India",".*",".*","2012", "totalAssetValue", service);
//		    OnLoad sample7 = service.popNodes("EYGBS","Core Business Services","ESS","EMEA","India",".*",".*","2012");
//		    List<String> nodes = allNodes("EYGBS","Core Business Services","ESS","EMEA","India",".*",".*","2012", service);
//		    System.out.println("Size "  + nodes.size());
//		    SankeyJson fromClass = new SankeyJson();
//		    fromClass.setLinks(links);
//		    fromClass.setNodes(nodes);
		    
		 String js=  makeJson("EYGBS","Core Business Services","ESS","EMEA","India",".*",".*","2012", "totalAssetValue", service);
		    System.out.println("Size "  + js);

//            List names = new ArrayList();
//		    System.out.println("Size "  + sample6.size());
//		    System.out.println("Size "  + sample7.getBrand());
//		    System.out.println("Size "  + sample7.getVendor());
//		    System.out.println("Size "  + sample7.getSubServiceLine());
//		    System.out.println("Size "  + sample7.getProduct());


//		    System.out.println("Size "  + sample6.size());
//		    System.out.println("Source "  + sample6.get(0).getSource);
//		    System.out.println("target "  + sample6.get(12).getTarget());
//		    System.out.println(" Value = "  + sample6.get(10).getValue());
//		    System.out.println("getPoamount "  + sample6.get(0).getPoamount());
		    
// #####################################################################################################################	   
//		     sample6 contains Links  as a list of sankey objests and 
//		     sample7 contains nodes as list of list ; to be converted to list
//		     To-Do ----- Using object mapper convert both this into json and join them to get the required json
// #####################################################################################################################		    
		    
	   }
	  
			public static List<Sankey> allSankey(String entity, String sl, String ssl,String region, String country, String city, String location,String year, String target,EntityService service){
//			target can be one of the following :totalAssetValue,totalAssetCount,poamount
			    List<Sankey> sank = service.popSank3(entity, sl, ssl, region, country, city, location, year, target);
			    sank.addAll( service.popSank4(entity, sl, ssl, region, country, city, location, year, target));
			    sank.addAll(service.popSank3(entity, sl, ssl, region, country, city, location, year, target));
			    sank.addAll(service.popSank4(entity, sl, ssl, region, country, city, location, year, target));
			    return sank;

			}
		   public static List<String> allNodes(String entity, String sl, String ssl,String region, String country, String city, String location,String year,EntityService service){
			   
			   OnLoad onload = service.popNodes(entity, sl, ssl, region, country, city, location, year);
			   List<String> nodes = new ArrayList<String>();
			   nodes.addAll(onload.getBrand());
			   nodes.addAll(onload.getVendor());
			   nodes.addAll(onload.getSubServiceLine());
			   nodes.addAll(onload.getProduct());
			   return nodes;
		   }
		   
			public static List<Nodes> makeList(List<String> list1){
				int size = list1.size();
				
				List<Nodes> node = new ArrayList<Nodes>() ;
//				Nodes name = new Nodes();

				for(int i = 0; i < size; i++){
					Nodes name = new Nodes();

					System.out.println("Name "  + list1.get(i));
					name.setName(list1.get(i));
					System.out.println("Name "  + name.getName());
					node.add(name);
//					nodes.get(i)
					
					}
				return node;
			}
				
			
		public static String makeJson(String entity, String sl, String ssl,String region, String country, String city, String location,String year, String target, EntityService service){
			List<Sankey> links = allSankey( entity,  sl,  ssl, region,  country,  city,  location, year,  target, service);
		    List<String> node = allNodes( entity,  sl,  ssl, region,  country,  city,  location, year, service);
		    
		    List<Nodes> nodes = makeList(node);
		    SankeyJson fromClass = new SankeyJson();
		    fromClass.setLinks(links);
		    fromClass.setNodes(nodes);
		    String jsonString=null;
		    ObjectMapper mapper = new ObjectMapper();
		      try
		      {
		         jsonString = mapper.writeValueAsString(fromClass);
		      } catch (JsonGenerationException e)
		      {
		         e.printStackTrace();
		      } catch (JsonMappingException e)
		      {
		         e.printStackTrace();
		      } catch (IOException e)
		      {
		         e.printStackTrace();
		      }
		      return jsonString;
		   }
		
	
}
