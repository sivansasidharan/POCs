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
import com.ss.nitro.analytics.assetlc.domain.expired.ExAsset;
import com.ss.nitro.analytics.assetlc.domain.expired.ExBrand;
import com.ss.nitro.analytics.assetlc.domain.expired.ExDeviceName;
import com.ss.nitro.analytics.assetlc.domain.expired.ExNode;
import com.ss.nitro.analytics.assetlc.domain.expired.ExSankeyModJson;
import com.ss.nitro.analytics.assetlc.domain.expired.ExSource;
import com.ss.nitro.analytics.assetlc.domain.expired.ExValue;
import com.ss.nitro.analytics.assetlc.domain.expired.ExVendorName;
import com.ss.nitro.analytics.assetlc.service.EntityService;

public class OnLoadExpiryTest {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"entity.xml");
		@SuppressWarnings("unused")
		EntityService aEntityService = (EntityService) context
				.getBean("EntityService");
		List<ExSource> sample = new ArrayList<ExSource>();
		sample = expiredSankey(".*", "CBS", ".*", ".*", ".*", ".*",
				".*", "2015", "totalAssetValue", "", aEntityService);
		List<String> node = allNodes(".*", "CBS", ".*", ".*", ".*",
				".*", ".*", "2015", "totalAssetValue", aEntityService);
		List<ExNode> nodes = makeExList(node);
		ExSankeyModJson fromClass = new ExSankeyModJson();
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
		System.out.println("- " + jsonString);
	}

	private static List<ExNode> makeExList(List<String> list1) {
		int size = list1.size();
		List<ExNode> node = new ArrayList<ExNode>();
		// Nodes name = new Nodes();
		for (int i = 0; i < size; i++) {
			ExNode name = new ExNode();
			System.out.println("Name " + list1.get(i));
			name.setName(list1.get(i));
			System.out.println("Name " + name.getName());
			node.add(name);
			// nodes.get(i)
		}
		return node;
	}

	private static List<String> allNodes(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, EntityService aEntityService) {

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

	private static List<ExSource> expiredSankey(String entity, String sl,
			String ssl, String region, String country, String city,
			String location, String year, String target, String expType,
			EntityService aEntityService) {

		List<Sankey> sank1 = aEntityService.popExSank(entity, sl, ssl, region,
				country, city, location, year, target, expType);

		List<Sankey> sank2 = aEntityService.popExSank2(entity, sl, ssl, region,
				country, city, location, year, target, expType);

		List<Sankey> sank3 = aEntityService.popExSank3(entity, sl, ssl, region,
				country, city, location, year, target, expType);

		List<Sankey> sank4 = aEntityService.popExSank4(entity, sl, ssl, region,
				country, city, location, year, target, expType);

		List<Sankey> sank5 = aEntityService.popExSank5(entity, sl, ssl, region,
				country, city, location, year, target, expType);

		ExValue data1 = null;
		ExValue data2 = null;
		ExValue data3 = null;
		ExValue data4 = null;
		ExValue data5 = null;
		ExBrand brand = null;
		ExVendorName vendor = null;
		ExDeviceName device = null;
		ExSource dept = null;
		ExAsset asset = null;
		List<ExBrand> brandList = null;
		List<ExAsset> exAssetList = null;
		List<ExVendorName> vendorList = null;
		List<ExDeviceName> deviceList = null;
		List<ExBrand> brandListAll = null;
		List<ExVendorName> vendorListAll = null;
		List<ExDeviceName> deviceListAll = null;
		for (Sankey sankey : sank1) {
			dept = new ExSource();
			dept.setName(sankey.getSource());
			String filter1Target = sankey.getTarget();// totalassetvalue--count
			data1 = new ExValue();
			data1.setLevel(Integer.valueOf(sankey.getLevel()));
			data1.setValue1(sankey.getValue());
			data1.setValue2(sankey.getValue2());
			deviceListAll = new ArrayList<ExDeviceName>();
			for (Sankey sankey2 : sank2) {
				System.out.println("looping --" + sankey2.getTarget());
				if (sankey2.getSource().equalsIgnoreCase(filter1Target)) {
					System.out.println("Step 1 ");
					device = new ExDeviceName();
					device.setName(sankey2.getTarget());
					deviceList = new ArrayList<ExDeviceName>();
					data2 = new ExValue();
					data2.setLevel(Integer.valueOf(sankey2.getLevel()));
					data2.setValue1(sankey2.getValue());
					data2.setValue2(sankey2.getValue2());
					String filter2Target = sankey2.getTarget();
					vendorList = new ArrayList<ExVendorName>();

					for (Sankey sankey3 : sank3) {
						if (sankey3.getSource().equalsIgnoreCase(filter2Target)) {
							vendorListAll = new ArrayList<ExVendorName>();
							System.out.println("Step 2 ");
							vendor = new ExVendorName();
							vendor.setName(sankey3.getTarget());
							data3 = new ExValue();
							data3.setLevel(Integer.valueOf(sankey3.getLevel()));
							data3.setValue1(sankey3.getValue());
							data3.setValue2(sankey3.getValue2());
							String filter3Target = sankey3.getTarget();
							brandList = new ArrayList<ExBrand>();
							for (Sankey sankey4 : sank4) {
								if (sankey4.getSource().equalsIgnoreCase(
										filter3Target)) {
									brandListAll = new ArrayList<ExBrand>();
									System.out.println("Step 3 ");
									brand = new ExBrand();
									brand.setName(sankey4.getTarget());
									data4 = new ExValue();
									data4.setLevel(Integer.valueOf(sankey4
											.getLevel()));
									data4.setValue1(sankey4.getValue());
									data4.setValue2(sankey4.getValue2());
									String filter4Target = sankey4.getTarget();
									exAssetList = new ArrayList<ExAsset>();
									for (Sankey sankey5 : sank5) {
										if (sankey5
												.getSource()
												.equalsIgnoreCase(filter4Target)) {
											System.out.println("Step 4 ");
											asset = new ExAsset();
											asset.setName(sankey5.getTarget());
											data5 = new ExValue();
											data5.setLevel(Integer
													.valueOf(sankey5.getLevel()));
											data5.setValue1(sankey5.getValue());
											data5.setValue2(sankey5.getValue2());
											asset.setValue(data5);
											exAssetList.add(asset);
										}
									}
									brand.setValue(data4);
									brand.setAssetName(exAssetList);
									brandList.add(brand);
									brandListAll.addAll(brandList);
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
		List<ExSource> sourcevalue = new ArrayList<ExSource>();
		sourcevalue.add(dept);
		return sourcevalue;

	}
}
