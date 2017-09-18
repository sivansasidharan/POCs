package com.ss.nitro.analytics.assetlc.controllers;

import java.io.FileReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.nitro.analytics.assetlc.domain.MapLoad;
import com.ss.nitro.analytics.assetlc.domain.OnLoad;
import com.ss.nitro.analytics.assetlc.domain.Sankey;
import com.ss.nitro.analytics.assetlc.domain.ServiceLineElement;
import com.ss.nitro.analytics.assetlc.domain.YearClass;
import com.ss.nitro.analytics.assetlc.domain.expired.ExAsset;
import com.ss.nitro.analytics.assetlc.domain.expired.ExBrand;
import com.ss.nitro.analytics.assetlc.domain.expired.ExDeviceName;
import com.ss.nitro.analytics.assetlc.domain.expired.ExSource;
import com.ss.nitro.analytics.assetlc.domain.expired.ExValue;
import com.ss.nitro.analytics.assetlc.domain.expired.ExVendorName;
import com.ss.nitro.analytics.assetlc.domain.sankey.Brand;
import com.ss.nitro.analytics.assetlc.domain.sankey.DeviceName;
import com.ss.nitro.analytics.assetlc.domain.sankey.Source;
import com.ss.nitro.analytics.assetlc.domain.sankey.Value;
import com.ss.nitro.analytics.assetlc.domain.sankey.VendorName;
import com.ss.nitro.analytics.assetlc.dto.AssetDataDTO;
import com.ss.nitro.analytics.assetlc.dto.AssetDataSankeyNodes;
import com.ss.nitro.analytics.assetlc.dto.AssetDataVendorInfoDTO;
import com.ss.nitro.analytics.assetlc.dto.AssetDataYearInfoDTO;
import com.ss.nitro.analytics.assetlc.dto.AssetInfoDTO;
import com.ss.nitro.analytics.assetlc.dto.ExpiredAssetInfoDTO;
import com.ss.nitro.analytics.assetlc.service.EntityService;
import com.ss.nitro.analytics.assetlc.utility.JsonUtilities;

@RestController
@RequestMapping("/assetlc")
@ComponentScan("com.ss.nitro.analytics.assetlc.service")
public class ServiceController {

	@Autowired
	private Neo4jTemplate template;

	@Autowired
	private EntityService aEntityService;

	@RequestMapping(value = "/testService", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String testService() {
		return "Yes Sir !!";
	}

	public Neo4jTemplate getTemplate() {
		return template;
	}

	public void setTemplate(Neo4jTemplate template) {
		this.template = template;
	}

	public EntityService getaEntityService() {
		return aEntityService;
	}

	public void setaEntityService(EntityService aEntityService) {
		this.aEntityService = aEntityService;
	}

	// pageload
	@RequestMapping(value = "/load", produces = "application/json")
	@ResponseBody
	@Transactional
	public String getAllData() throws Exception {
		System.out.println("----------------ON LOAD - Service------------");
		AssetDataDTO data = aEntityService.popOnLoad();
		JSONObject result = new JSONObject();
		result.put("AssetData", data);
		// to add expired asset info on current date
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
		String year = format.format(curDate);
		System.out.println("year - passed for info --" + year);
		ExpiredAssetInfoDTO dueExpiredAssetInfo = aEntityService
				.fetchDueReplacementExpiredAssetInfo(".*", ".*", ".*", ".*",
						".*", ".*", ".*", year, true);
		ExpiredAssetInfoDTO pendingExpiredAssetInfo = aEntityService
				.fetchPendingReplacementExpiredAssetInfo(".*", ".*", ".*",
						".*", ".*", ".*", ".*", year, true);
		ExpiredAssetInfoDTO upcomingExpiredAssetInfo = aEntityService
				.fetchUpcomingReplacementExpiredAssetInfo(".*", ".*", ".*",
						".*", ".*", ".*", ".*", year, true);
		result.put("dueExpiredAssetInfo", dueExpiredAssetInfo);
		result.put("pendingExpiredAssetInfo", pendingExpiredAssetInfo);
		result.put("upcomingExpiredAssetInfo", upcomingExpiredAssetInfo);
		// ends here
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		System.out.println("On Load - Data returned-- ->" + jsonString);
		return jsonString;
	}

	// pageload
	@RequestMapping(value = "/loadMap", produces = "application/json")
	@ResponseBody
	@Transactional
	public String loadMapDetails(@RequestParam("region") String region,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("location") String location) throws Exception {
		System.out.println("----------------LOAD MAP- Service------------");
		MapLoad data = aEntityService.popMapDetails(city, location, country,
				region);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(data);
		System.out.println("Load MAP - Data returned--- ->" + jsonString);
		return jsonString;
	}

	// pageload
	@RequestMapping(value = "/loadSankeyNodes", produces = "application/json")
	@ResponseBody
	@Transactional
	public String getAllSankeyNodes(@RequestParam("entity") String entity,
			@RequestParam("serviceLine") String serviceLine,
			@RequestParam("subserviceLine") String subserviceLine,
			@RequestParam("region") String region,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("location") String location,
			@RequestParam("year") String year,
			@RequestParam("brand") String brand,
			@RequestParam("vendor") String vendor,
			@RequestParam("product") String product) throws Exception {
		System.out
				.println("----------------LOAD Sankey Nodes - Service------------");
		AssetDataSankeyNodes data = aEntityService.popNodesUpdate(entity,
				serviceLine, subserviceLine, region, country, city, location,
				year, brand, vendor, product);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(data);
		System.out.println("Load Sankey Nodes - Data returned-- ->"
				+ jsonString);
		return jsonString;
	}

	// filters
	@RequestMapping(value = "/filter", produces = "application/json")
	@ResponseBody
	@Transactional
	public String getAssetInfoBasedOnFilters(
			@RequestParam("entity") String entity,
			@RequestParam("serviceLine") String serviceLine,
			@RequestParam("subserviceLine") String subserviceLine,
			@RequestParam("region") String region,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("location") String location,
			@RequestParam("year") String year, @RequestParam("pred") String pred)
			throws Exception {
		System.out.println("---------------FILTER Service ------------\n");
		System.out.println("-- Filter - params \n " + entity + "\n"
				+ serviceLine + "\n" + "\n" + subserviceLine + "\n" + "\n"
				+ region + "\n" + "\n" + country + "\n" + "\n" + city + "\n"
				+ "\n" + location + "\n" + "\n" + year + "\n");
		List<AssetDataYearInfoDTO> sample = aEntityService
				.popAssetdetailsByFilters(entity, serviceLine, subserviceLine,
						region, country, city, location, year, pred);
		List<AssetInfoDTO> yearAsset = aEntityService
				.fetchYearWiseDataForFilters(entity, serviceLine,
						subserviceLine, region, country, city, location, year,
						pred);
		JSONObject result = new JSONObject();
		result.put("AssetInfo", sample);
		result.put("Year", yearAsset);
		// to add expired asset info on yearly data
		String yearUpdate = null;
		boolean filter = false;
		if (year != null && (year.equalsIgnoreCase(".*"))) {
			Date curDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
			yearUpdate = format.format(curDate);
			filter = true;
		} else
			yearUpdate = year;
		System.out.println("year - passed for info --" + yearUpdate);
		ExpiredAssetInfoDTO dueExpiredAssetInfo = aEntityService
				.fetchDueReplacementExpiredAssetInfo(entity, serviceLine,
						subserviceLine, region, country, city, location,
						yearUpdate, filter);
		ExpiredAssetInfoDTO pendingExpiredAssetInfo = aEntityService
				.fetchPendingReplacementExpiredAssetInfo(entity, serviceLine,
						subserviceLine, region, country, city, location,
						yearUpdate, filter);
		ExpiredAssetInfoDTO upcomingExpiredAssetInfo = aEntityService
				.fetchUpcomingReplacementExpiredAssetInfo(entity, serviceLine,
						subserviceLine, region, country, city, location,
						yearUpdate, filter);
		result.put("dueExpiredAssetInfo", dueExpiredAssetInfo);
		result.put("pendingExpiredAssetInfo", pendingExpiredAssetInfo);
		result.put("upcomingExpiredAssetInfo", upcomingExpiredAssetInfo);
		// ends here
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		System.out
				.println("Filter service - Data returned---- ->" + jsonString);
		return jsonString;
	}

	// slide 5
	@RequestMapping(value = "/yearlyOld", produces = "application/json")
	@ResponseBody
	@Transactional
	public String getAllAssetInfoForYears(
			@RequestParam("entity") String entity,
			@RequestParam("serviceLine") String serviceLine,
			@RequestParam("subserviceLine") String subserviceLine,
			@RequestParam("region") String region,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("location") String location,
			@RequestParam("year") String year) throws Exception {

		System.out.println("--------------Yearly Old Service ------------\n");
		System.out.println("-- params " + entity + "\n" + serviceLine + "\n"
				+ "\n" + subserviceLine + "\n" + "\n" + region + "\n" + "\n"
				+ country + "\n" + "\n" + city + "\n" + "\n" + location + "\n"
				+ "\n" + year + "\n");
		List<AssetDataYearInfoDTO> sample = aEntityService
				.popAssetdetailsByFilters(entity, serviceLine, subserviceLine,
						region, country, city, location, year, "notPred");
		JSONObject result = new JSONObject();
		result.put("AssetInfo", sample);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		System.out.println("-Yearly Old Service data returned -- ->"
				+ JsonUtilities.formatJsonForAssetInfo(jsonString));
		return JsonUtilities.formatJsonForAssetInfo(jsonString);

	}

	// slide 5
	@RequestMapping(value = "/yearly", produces = "application/json")
	@ResponseBody
	@Transactional
	public String getAllAssetInfoForYears2(
			@RequestParam("entity") String entity,
			@RequestParam("serviceLine") String serviceLine,
			@RequestParam("subserviceLine") String subserviceLine,
			@RequestParam("region") String region,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("location") String location,
			@RequestParam("year") String year, @RequestParam("pred") String pred)
			throws Exception {

		System.out.println("--------------Yearly Service ------------\n");
		System.out.println("--yearly service  params - \n" + entity + "\n"
				+ serviceLine + "\n" + "\n" + subserviceLine + "\n" + "\n"
				+ region + "\n" + "\n" + country + "\n" + "\n" + city + "\n"
				+ "\n" + location + "\n" + "\n" + year + "\n");
		List<AssetDataYearInfoDTO> sample = aEntityService
				.popAssetdetailsByFilters(entity, serviceLine, subserviceLine,
						region, country, city, location, year, pred);

		Map<String, List<YearClass>> map = new HashMap<String, List<YearClass>>();
		Map<String, List<ServiceLineElement>> map2 = new HashMap<String, List<ServiceLineElement>>();
		Map<String, double[]> map3 = new HashMap<String, double[]>();

		for (AssetDataYearInfoDTO yearInfo : sample) {
			String key = yearInfo.getServiceLine();
			if (map.get(key) == null) {
				map.put(key, new ArrayList<YearClass>());
				map2.put(key, new ArrayList<ServiceLineElement>());
				map3.put(key, new double[3]);

				double[] list = { 0, 0, 0 };

				map3.put(key, list);

			}

			ServiceLineElement object = new ServiceLineElement();
			object.setDepartment(yearInfo.getSubServiceLine());
			object.setTotalAssetValue(Double.parseDouble(yearInfo
					.getTotalAssetValue().toPlainString()));
			object.setTotalAssetCount(Double.parseDouble(yearInfo
					.getTotalAssetCount().toPlainString()));
			object.setPoAmount(Double.parseDouble(yearInfo.getTotalPOAmount()
					.toPlainString()));
			map2.get(key).add(object);

			double[] list2 = map3.get(key);

			list2[0] = list2[0]
					+ Double.parseDouble(yearInfo.getTotalAssetValue()
							.toPlainString());
			list2[1] = list2[1]
					+ Double.parseDouble(yearInfo.getTotalAssetCount()
							.toPlainString());
			list2[2] = list2[2]
					+ Double.parseDouble(yearInfo.getTotalPOAmount()
							.toPlainString());
			map3.put(key, list2);
		}
		Set<String> keys = map.keySet();

		// Loop over String keys.
		for (String key : keys) {
			YearClass e = new YearClass();
			e.setInnerList(map2.get(key));
			double[] list3 = map3.get(key);
			e.setTotalAssetValue(new BigDecimal(list3[0]));
			e.setTotalAssetCount(new BigDecimal(list3[1]));
			e.setPoAmount(new BigDecimal(list3[2]));

			map.get(key).add(e);
		}

		// to add expired asset info on yearly data
		String yearUpdate = null;
		boolean filter = false;
		if (year != null && (year.equalsIgnoreCase(".*"))) {
			Date curDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
			yearUpdate = format.format(curDate);
			filter = true;
		} else
			yearUpdate = year;
		System.out.println("year - passed for info --" + yearUpdate);
		ExpiredAssetInfoDTO dueExpiredAssetInfo = aEntityService
				.fetchDueReplacementExpiredAssetInfo(entity, serviceLine,
						subserviceLine, region, country, city, location,
						yearUpdate, filter);
		ExpiredAssetInfoDTO pendingExpiredAssetInfo = aEntityService
				.fetchPendingReplacementExpiredAssetInfo(entity, serviceLine,
						subserviceLine, region, country, city, location,
						yearUpdate, filter);
		ExpiredAssetInfoDTO upcomingExpiredAssetInfo = aEntityService
				.fetchUpcomingReplacementExpiredAssetInfo(entity, serviceLine,
						subserviceLine, region, country, city, location,
						yearUpdate, filter);

		// ends here
		JSONObject result = new JSONObject();
		result.put("AssetInfo", map);
		// to add expired asset info on yearly data
		result.put("dueExpiredAssetInfo", dueExpiredAssetInfo);
		result.put("pendingExpiredAssetInfo", pendingExpiredAssetInfo);
		result.put("upcomingExpiredAssetInfo", upcomingExpiredAssetInfo);
		// ends here
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		System.out.println("Yearly Service data returned-- ->" + jsonString);
		return jsonString;

	}

	// Slide 9 // old
	@RequestMapping(value = "/detailold", produces = "application/json")
	@ResponseBody
	@Transactional
	public String getDetailAssetInfoOnFilters(
			@RequestParam("entity") String entity,
			@RequestParam("serviceLine") String serviceLine,
			@RequestParam("subserviceLine") String subserviceLine,
			@RequestParam("region") String region,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("location") String location,
			@RequestParam("year") String year) throws Exception {

		System.out.println("--------------detail old  Service ------------\n");
		System.out.println("-- detail old service params - \n " + entity + "\n"
				+ serviceLine + "\n" + "\n" + subserviceLine + "\n" + "\n"
				+ region + "\n" + "\n" + country + "\n" + "\n" + city + "\n"
				+ "\n" + location + "\n" + "\n" + year + "\n");

		List<AssetDataVendorInfoDTO> sample = aEntityService
				.popAssetdetailsCondLast(entity, serviceLine, subserviceLine,
						region, country, city, location, year);
		JSONObject result = new JSONObject();
		result.put("AssetInfo", sample);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		System.out
				.println("detail Old Service data returned-- ->" + jsonString);
		return jsonString;
	}

	// Slide 9 // new for Sankey
	@RequestMapping(value = "/detail", produces = "application/json")
	@ResponseBody
	@Transactional
	public String getDetailAssetInfoByFilters(
			@RequestParam("entity") String entity,
			@RequestParam("serviceLine") String serviceLine,
			@RequestParam("subserviceLine") String subserviceLine,
			@RequestParam("region") String region,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("location") String location,
			@RequestParam("year") String year,
			@RequestParam("target") String target) throws Exception {

		System.out.println("--------------detail Service ------------\n");
		System.out.println("-- detail Service params - \n" + entity + "\n"
				+ serviceLine + "\n" + "\n" + subserviceLine + "\n" + "\n"
				+ region + "\n" + "\n" + country + "\n" + "\n" + city + "\n"
				+ "\n" + location + "\n" + "\n" + year + "\n" + "\n" + target
				+ "\n");

		// target can be one of the following
		// :totalAssetValue,totalAssetCount,poamount

		// List<Sankey> links = allSankey(entity, serviceLine, subserviceLine,
		// region, country, city, location, year, target);
		List<Source> sourceValue = allNewSankey(entity, serviceLine,
				subserviceLine, region, country, city, location, year, target);
		List<String> node = allNodes(entity, serviceLine, subserviceLine,
				region, country, city, location, year, target);
		String returnJson = JsonUtilities.makeNewSankeyJson(sourceValue, node);
		System.out
				.println("--------------detail Service data returned------------\n"
						+ returnJson);
		return returnJson;

	}

	// Slide 9 // new for Sankey
	@RequestMapping(value = "/detailupdate", produces = "application/json")
	@ResponseBody
	@Transactional
	public String getDetailAssetInfoByFiltersUpdate(
			@RequestParam("entity") String entity,
			@RequestParam("serviceLine") String serviceLine,
			@RequestParam("subserviceLine") String subserviceLine,
			@RequestParam("region") String region,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("location") String location,
			@RequestParam("year") String year,
			@RequestParam("brand") String brand,
			@RequestParam("vendor") String vendor,
			@RequestParam("product") String product,
			@RequestParam("target") String target) throws Exception {

		System.out
				.println("--------------detail update Service ------------\n");
		System.out.println("-detail update Service  - params - \n" + entity
				+ "\n" + serviceLine + "\n" + "\n" + subserviceLine + "\n"
				+ "\n" + region + "\n" + "\n" + country + "\n" + "\n" + city
				+ "\n" + "\n" + location + "\n" + "\n" + year + "\n" + "\n"
				+ brand + "\n" + "\n" + vendor + "\n" + "\n" + product + "\n"
				+ "\n" + target + "\n");

		// target can be one of the following
		// :totalAssetValue,totalAssetCount,poamount

		List<Sankey> links = allSankeyUpdate(entity, serviceLine,
				subserviceLine, region, country, city, location, year, brand,
				vendor, product, target);
		List<String> node = allNodesUpdate(entity, serviceLine, subserviceLine,
				region, country, city, location, year, brand, vendor, product,
				target);
		String returnJson = JsonUtilities.makeSankeyJson(links, node);
		System.out
				.println("--------------detail update Service --data returned-----------\n"
						+ returnJson);
		return returnJson;

	}

	private List<Sankey> allSankey(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target) {

		List<Sankey> sank = aEntityService.popSank(entity, sl, ssl, region,
				country, city, location, year, target);
		sank.addAll(aEntityService.popSank2(entity, sl, ssl, region, country,
				city, location, year, target));
		sank.addAll(aEntityService.popSank3(entity, sl, ssl, region, country,
				city, location, year, target));
		sank.addAll(aEntityService.popSank4(entity, sl, ssl, region, country,
				city, location, year, target));
		return sank;
	}

	private List<Sankey> allSankeyUpdate(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target) {

		List<Sankey> sank = aEntityService.popSankUpdate(entity, sl, ssl,
				region, country, city, location, year, brand, vendor, product,
				target);
		sank.addAll(aEntityService.popSank2Update(entity, sl, ssl, region,
				country, city, location, year, brand, vendor, product, target));
		sank.addAll(aEntityService.popSank3Update(entity, sl, ssl, region,
				country, city, location, year, brand, vendor, product, target));
		sank.addAll(aEntityService.popSank4Update(entity, sl, ssl, region,
				country, city, location, year, brand, vendor, product, target));
		return sank;
	}

	private List<String> allNodes(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target) {

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

	private List<String> allNodesUpdate(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target) {

		OnLoad onload = aEntityService.popNodesUpdateDetail(entity, sl, ssl,
				region, country, city, location, year, brand, vendor, product);
		List<String> nodes = new ArrayList<String>();
		nodes.addAll(onload.getBrand());
		nodes.addAll(onload.getVendor());
		nodes.addAll(onload.getSubServiceLine());
		nodes.addAll(onload.getProduct());
		nodes.add(target);
		return nodes;
	}

	// filters- sankey page
	@RequestMapping(value = "/filtersankey", produces = "application/json")
	@ResponseBody
	@Transactional
	public String getAssetInfoBasedOnSankeyFilters(
			@RequestParam("entity") String entity,
			@RequestParam("serviceLine") String serviceLine,
			@RequestParam("subserviceLine") String subserviceLine,
			@RequestParam("region") String region,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("location") String location,
			@RequestParam("year") String year,
			@RequestParam("brand") String brand,
			@RequestParam("vendor") String vendor,
			@RequestParam("product") String product) throws Exception {

		System.out
				.println("--------------filtersankey - Invoked-----------------");
		List<AssetDataYearInfoDTO> sample = aEntityService
				.popAssetdetailsByFiltersankey(entity, serviceLine,
						subserviceLine, region, country, city, location, year,
						brand, vendor, product);
		List<AssetInfoDTO> yearAsset = aEntityService
				.fetchYearWiseDataForFiltersankey(entity, serviceLine,
						subserviceLine, region, country, city, location, year,
						brand, vendor, product);
		JSONObject result = new JSONObject();
		result.put("AssetInfo", sample);
		result.put("Year", yearAsset);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		System.out.println("Json returned for filter sankeys- ->" + jsonString);
		return jsonString;
	}

	private List<Source> allNewSankey(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target) {

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
		List<Source> sourcevalue = new ArrayList<Source>();
		sourcevalue.add(dept);
		return sourcevalue;
	}

	// detail expiry json
	@RequestMapping(value = "/detailexpiry", produces = "application/json")
	@ResponseBody
	@Transactional
	public String getExpiryDetailAssetInfoByFilters(
			@RequestParam("entity") String entity,
			@RequestParam("serviceLine") String serviceLine,
			@RequestParam("subserviceLine") String subserviceLine,
			@RequestParam("region") String region,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestParam("location") String location,
			@RequestParam("year") String year,
			@RequestParam("target") String target,
			@RequestParam("expType") String expType) throws Exception {

		System.out.println("----------------inside expiry service---------");
		List<ExSource> expiredData = expiredSankey(entity, serviceLine,
				subserviceLine, region, country, city, location, year, target,
				expType);

		List<String> node = allNodes(entity, serviceLine, subserviceLine,
				region, country, city, location, year, target);
		String returnJson = JsonUtilities.makeExpiredSankeyJson(expiredData,
				node);
		System.out.println("---detail expiry service data returned ---- "
				+ returnJson);
		return returnJson;
		// return getExpiredDummyListFromFile();

	}

	private String getExpiredDummyListFromFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		JSONParser parser = new JSONParser();
		String dummyList = null;
		try {
			Object obj = parser.parse(new FileReader(classLoader.getResource(
					"expiredList.json").getFile()));
			JSONArray jsonObject = (JSONArray) obj;
			System.out.println(" - >" + jsonObject.toJSONString());
			dummyList = jsonObject.toJSONString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dummyList;

	}

	private List<ExSource> expiredSankey(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, String expType) {

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
