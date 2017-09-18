package com.sample.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ss.nitro.analytics.assetlc.domain.Nodes;
import com.ss.nitro.analytics.assetlc.domain.OnLoad;
import com.ss.nitro.analytics.assetlc.domain.Sankey;
import com.ss.nitro.analytics.assetlc.domain.sankey.Brand;
import com.ss.nitro.analytics.assetlc.domain.sankey.DeviceName;
import com.ss.nitro.analytics.assetlc.domain.sankey.Source;
import com.ss.nitro.analytics.assetlc.domain.sankey.Value;
import com.ss.nitro.analytics.assetlc.domain.sankey.VendorName;
import com.ss.nitro.analytics.assetlc.service.EntityService;

public class OnLoadTest {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"entity.xml");
		@SuppressWarnings("unused")
		EntityService aEntityService = (EntityService) context
				.getBean("EntityService");
		List<Source> sample = new ArrayList<Source>();
		Source dept = allNewSankey("EYGBS", "CBS", "ESS", "EMEA", "India", ".*", ".*",
				"2012", "totalAssetValue", aEntityService);
		List<String> node = allNodes("EYGBS", "CBS", "ESS", "EMEA", "India", ".*", ".*",
				"2012", "totalAssetValue", aEntityService);
		sample.add(dept);
		List<Nodes> nodes = makeList(node);
		SankeyJson fromClass = new SankeyJson();
		fromClass.setLinks(sample);
		fromClass.setNodes(nodes);
		String jsonString = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = mapper.writeValueAsString(fromClass);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("- "+jsonString);
	}

	private static List<String> allNodes(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target,EntityService aEntityService) {

		OnLoad onload = aEntityService.popNodes(entity, sl, ssl, region,
				country, city, location, year);
		List<String> nodes = new ArrayList<String>();
		nodes.addAll(onload.getBrand());
		nodes.addAll(onload.getVendor());
		nodes.addAll(onload.getSubServiceLine());
		nodes.addAll(onload.getProduct());
		nodes.add(target);
		return nodes;
	}
	
	private static Source allNewSankey(String entity, String sl,
			String ssl, String region, String country, String city,
			String location, String year, String target,
			EntityService aEntityService) {

		List<Sankey> sank1 = aEntityService.popSank(entity, sl, ssl, region,
				country, city, location, year, target);

		List<Sankey> sank2 = aEntityService.popSank2(entity, sl, ssl, region,
				country, city, location, year, target);

		List<Sankey> sank3 = aEntityService.popSank3(entity, sl, ssl, region,
				country, city, location, year, target);

		List<Sankey> sank4 = aEntityService.popSank4(entity, sl, ssl, region,
				country, city, location, year, target);

		Value data1 = null;
		Value data2 = null;
		Value data3 = null;
		Value data4 = null;
		Brand brand = null;
		VendorName vendor = null;
		DeviceName device = null;
		Source dept = null;
		List<Brand> brandList = null;
		List<VendorName> vendorList = null;
		List<DeviceName> deviceList = null;
		List<VendorName> vendorListAll = null;
		List<DeviceName> deviceListAll = null;
		for (Sankey sankey : sank1) {
			dept = new Source();
			dept.setName(sankey.getSource());
			String filter1Target = sankey.getTarget();// totalassetvalue--count
			data1 = new Value();
			data1.setLevel(Integer.valueOf(sankey.getLevel()));
			data1.setValue1(sankey.getValue());
			data1.setValue2(sankey.getValue2());
			deviceListAll = new ArrayList<DeviceName>();
			for (Sankey sankey2 : sank2) {
				System.out.println("looping --" + sankey2.getTarget());
				if (sankey2.getSource().equalsIgnoreCase(filter1Target)) {
					System.out.println("Step 1 ");
					device = new DeviceName();
					device.setName(sankey2.getTarget());
					deviceList = new ArrayList<DeviceName>();
					data2 = new Value();
					data2.setLevel(Integer.valueOf(sankey2.getLevel()));
					data2.setValue1(sankey2.getValue());
					data2.setValue2(sankey2.getValue2());
					String filter2Target = sankey2.getTarget();
					vendorList = new ArrayList<VendorName>();

					for (Sankey sankey3 : sank3) {
						if (sankey3.getSource().equalsIgnoreCase(filter2Target)) {
							vendorListAll = new ArrayList<VendorName>();
							System.out.println("Step 2 ");
							vendor = new VendorName();
							vendor.setName(sankey3.getTarget());
							data3 = new Value();
							data3.setLevel(Integer.valueOf(sankey3.getLevel()));
							data3.setValue1(sankey3.getValue());
							data3.setValue2(sankey3.getValue2());
							String filter3Target = sankey3.getTarget();
							brandList = new ArrayList<Brand>();
							for (Sankey sankey4 : sank4) {
								if (sankey4.getSource().equalsIgnoreCase(
										filter3Target)) {
									System.out.println("Step 3 ");
									brand = new Brand();
									brand.setName(sankey4.getTarget());
									data4 = new Value();
									data4.setLevel(Integer.valueOf(sankey4
											.getLevel()));
									data4.setValue1(sankey4.getValue());
									data4.setValue2(sankey4.getValue2());
									brand.setValue(data4);
									brandList.add(brand);
								}
							}
							vendor.setValue(data3);
							vendor.setBrand(brandList);
							vendorList.add(vendor);
							vendorListAll.addAll(vendorList);
						}

					}
					device.setValue(data2);
					device.setVendorName(vendorListAll);
					deviceList.add(device);
					deviceListAll.addAll(deviceList);
				}

			}

		}
		dept.setValue(data1);
		dept.setDeviceName(deviceListAll);	
		return dept;
	}
	
	private static List<Nodes> makeList(List<String> list1) {
		int size = list1.size();
		List<Nodes> node = new ArrayList<Nodes>();
		// Nodes name = new Nodes();
		for (int i = 0; i < size; i++) {
			Nodes name = new Nodes();
			System.out.println("Name " + list1.get(i));
			name.setName(list1.get(i));
			System.out.println("Name " + name.getName());
			node.add(name);
			// nodes.get(i)
		}
		return node;
	}
	static class SankeyJson {
		private List<Source> links;
		private List<Nodes> nodes;
		public List<Source> getLinks() {
			return links;
		}
		public void setLinks(List<Source> links) {
			this.links = links;
		}
		public List<Nodes> getNodes() {
			return nodes;
		}
		public void setNodes(List<Nodes> nodes) {
			this.nodes = nodes;
		}
		
	}
}
