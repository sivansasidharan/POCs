package com.ss.nitro.analytics.assetlc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;

import com.ss.nitro.analytics.assetlc.dao.EntityRepository;
import com.ss.nitro.analytics.assetlc.domain.CallOne;
import com.ss.nitro.analytics.assetlc.domain.Coordinates;
import com.ss.nitro.analytics.assetlc.domain.Entity;
import com.ss.nitro.analytics.assetlc.domain.ExpiredInfo;
import com.ss.nitro.analytics.assetlc.domain.LocationDetail;
import com.ss.nitro.analytics.assetlc.domain.MapCircle;
import com.ss.nitro.analytics.assetlc.domain.MapDetails;
import com.ss.nitro.analytics.assetlc.domain.MapLoad;
import com.ss.nitro.analytics.assetlc.domain.OnLoad;
import com.ss.nitro.analytics.assetlc.domain.PoDetails;
import com.ss.nitro.analytics.assetlc.domain.Sankey;
import com.ss.nitro.analytics.assetlc.domain.SubLocElement;
import com.ss.nitro.analytics.assetlc.dto.AssetDataDTO;
import com.ss.nitro.analytics.assetlc.dto.AssetDataSankeyNodes;
import com.ss.nitro.analytics.assetlc.dto.AssetDataVendorInfoDTO;
import com.ss.nitro.analytics.assetlc.dto.AssetDataYearInfoDTO;
import com.ss.nitro.analytics.assetlc.dto.AssetInfoDTO;
import com.ss.nitro.analytics.assetlc.dto.ExpiredAssetInfoDTO;

@Service("EntityService")
public class EntityServiceImpl implements EntityService {

	AssetDataDTO assetDto = null;
	List<AssetDataYearInfoDTO> assetInfoDtoList = null;
	List<AssetInfoDTO> assetInfoList = null;
	List<AssetDataVendorInfoDTO> assetInfoVendorDtoList = null;

	@Autowired
	private EntityRepository entityRepository;

	public AssetDataDTO popOnLoad() {
		assetDto = new AssetDataDTO();
		assetDto.setTotalAssetValue(entityRepository.popOnLoad()
				.getTotalAssetValue());
		assetDto.setTotalAssetCount(entityRepository.popOnLoad()
				.getTotalAssetCount());
		assetDto.setPoAmount(entityRepository.popOnLoad().getPoAmount());
		assetDto.setEntity(entityRepository.popOnLoad().getEntity());
		assetDto.setServiceLines(entityRepository.popOnLoad().getServiceLine());
		assetDto.setSubServiceLines(entityRepository.popOnLoad()
				.getSubServiceLine());
		assetDto.setRegion(entityRepository.popOnLoad().getRegion());
		assetDto.setCountry(entityRepository.popOnLoad().getCountry());
		assetDto.setCity(entityRepository.popOnLoad().getCity());
		assetDto.setLocation(entityRepository.popOnLoad().getLocation());
		List<CallOne> yearData = entityRepository
				.populateAssetDetailsYearPred(); //
		yearData.addAll(entityRepository.populateAssetDetailsYear());
		String[] yearAseetValues;
		// Collections.sort(yearData,Collections.reverseOrder());
		Map<String, String[]> year = new TreeMap<String, String[]>();
		for (CallOne callOne : yearData) {
			yearAseetValues = new String[3];
			yearAseetValues[0] = "AssetCount:"
					+ BigDecimal.valueOf(callOne.getTotalAssetCount())
							.toPlainString();
			yearAseetValues[1] = "AssetValue:"
					+ BigDecimal.valueOf(callOne.getTotalAssetValue())
							.toPlainString();
			yearAseetValues[2] = "POAmount:"
					+ BigDecimal.valueOf(callOne.getPoAmount()).toPlainString();
			year.put(callOne.getYear(), yearAseetValues);
		}

		assetDto.setYear(year);
		return assetDto;

	}

	/*
	 * public Map<String, String[]> fetchYearWiseDataForFilters(String entity,
	 * String sl, String ssl, String region, String country, String city, String
	 * location) {
	 * 
	 * List<CallOne> yearData = entityRepository
	 * .popAssetdetailsForYearsByFilters(entity, sl, ssl, region, country, city,
	 * location); String[] yearAseetValues; Map<String, String[]> filterYears =
	 * new HashMap<String, String[]>(); for (CallOne callOne : yearData) {
	 * yearAseetValues = new String[2]; yearAseetValues[0] =
	 * Integer.toString(callOne.getTotalAssetCount()); yearAseetValues[1] =
	 * Integer.toString(callOne.getTotalAssetValue());
	 * filterYears.put(callOne.getYear(), yearAseetValues); } return
	 * filterYears; }
	 */

	public List<AssetInfoDTO> fetchYearWiseDataForFilters(String entity,
			String sl, String ssl, String region, String country, String city,

			String location, String year, String pred) {
		assetInfoList = new ArrayList<AssetInfoDTO>();
		AssetInfoDTO data = null;
		List<CallOne> querData = entityRepository
				.popAssetdetailsForYearsByFilters(entity, sl, ssl, region,

				country, city, location, year);
		if (pred.equalsIgnoreCase("t")) {
			List<CallOne> predData = entityRepository

			.popAssetdetailsForYearsByFilters(sl, ssl, country, year);
			querData.addAll(predData);
		}
		for (CallOne callOne : querData) {
			data = new AssetInfoDTO();
			data.setTotalAssetCount(callOne.getTotalAssetCount());
			data.setTotalAssetValue(callOne.getTotalAssetValue());
			data.setPOAmount(callOne.getPoAmount());
			data.setYear(callOne.getYear());
			assetInfoList.add(data);
		}
		return assetInfoList;
	}

	public List<AssetDataYearInfoDTO> popAssetdetailsByFilters(String entity,
			String sl, String ssl, String region, String country, String city,
			String location, String year, String pred) {

		List<CallOne> details = entityRepository.popAssetdetailsCondYear(
				entity, sl, ssl, region, country, city, location, year);
		if (pred.equalsIgnoreCase("t")) {
			List<CallOne> details2 = entityRepository.popAssetdetailsCondYear(
					sl, ssl, country, year);
			details.addAll(details2);
		}
		assetInfoDtoList = new ArrayList<AssetDataYearInfoDTO>();
		AssetDataYearInfoDTO assetInfoDto = null;
		for (CallOne callOne : details) {
			assetInfoDto = new AssetDataYearInfoDTO();
			assetInfoDto.setServiceLine(callOne.getSl());
			assetInfoDto.setSubServiceLine(callOne.getSsl());
			assetInfoDto.setTotalAssetCount(callOne.getTotalAssetCount());
			assetInfoDto.setTotalAssetValue(callOne.getTotalAssetValue());
			assetInfoDto.setTotalPOAmount(callOne.getPoAmount());
			assetInfoDtoList.add(assetInfoDto);
		}
		return assetInfoDtoList;
	}

	public List<AssetInfoDTO> popAssetdetailsCondRegion(String entity,
			String sl, String ssl, String region, String country, String city,

			String location, String year) {
		assetInfoList = new ArrayList<AssetInfoDTO>();
		AssetInfoDTO data = null;
		List<CallOne> querData = entityRepository
				.popAssetdetailsForYearsByFilters(entity, sl, ssl, region,
						country, city, location, year);
		for (CallOne callOne : querData) {
			data = new AssetInfoDTO();
			data.setTotalAssetCount(callOne.getTotalAssetCount());
			data.setTotalAssetValue(callOne.getTotalAssetValue());
			data.setYear(callOne.getYear());
			assetInfoList.add(data);
		}
		return assetInfoList;

	}

	public List<AssetDataVendorInfoDTO> popAssetdetailsCondLast(String entity,
			String sl, String ssl, String region, String country, String city,
			String location, String year) {
		List<CallOne> details = entityRepository.popAssetdetailsCondAll(entity,
				sl, ssl, region, country, city, location, year);
		System.out.println(" - " + details.size());
		assetInfoVendorDtoList = new ArrayList<AssetDataVendorInfoDTO>();
		AssetDataVendorInfoDTO assetInfoDto = null;
		for (CallOne callOne : details) {
			assetInfoDto = new AssetDataVendorInfoDTO();
			assetInfoDto.setServiceLine(callOne.getSl());
			assetInfoDto.setSubServiceLine(callOne.getSsl());
			assetInfoDto.setTotalAssetCount(callOne.getTotalAssetCount());
			assetInfoDto.setTotalAssetValue(callOne.getTotalAssetValue());
			assetInfoDto.setPOAmount(callOne.getPoAmount());
			assetInfoDto.setBrand(callOne.getBrand());
			assetInfoDto.setProduct(callOne.getProduct());
			assetInfoDto.setVendor(callOne.getVendor());
			assetInfoVendorDtoList.add(assetInfoDto);
		}
		return assetInfoVendorDtoList;
	}

	public Entity create(Entity profile) {
		return entityRepository.save(profile);
	}

	public void delete(Entity profile) {
		entityRepository.delete(profile);
	}

	public Entity findById(long id) {
		return entityRepository.findOne(id);
	}

	public Result<Entity> findAll() {
		return entityRepository.findAll();
	}

	public List<CallOne> popAssetDetails() {
		return entityRepository.populateAssetDetailsYear();
	}

	public List<CallOne> popAssetdetailsCond(String entity, String sl,
			String ssl) {
		return entityRepository.popAssetdetailsCond(entity, sl, ssl);
	}

	public List<Sankey> popSank(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target) {
		return entityRepository.popSank(entity, sl, ssl, region, country, city,
				location, year, target);
	}

	public List<Sankey> popSank2(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target) {
		return entityRepository.popSank2(entity, sl, ssl, region, country,
				city, location, year, target);
	}

	public List<Sankey> popSank3(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target) {
		return entityRepository.popSank3(entity, sl, ssl, region, country,
				city, location, year, target);
	}

	public List<Sankey> popSank4(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target) {
		return entityRepository.popSank4(entity, sl, ssl, region, country,
				city, location, year, target);
	}

	public OnLoad popNodes(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year) {
		return entityRepository.popNodes(entity, sl, ssl, region, country,
				city, location, year);
	}

	public List<Sankey> popSankUpdate(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target) {
		return entityRepository.popSankUpdate(entity, sl, ssl, region, country,
				city, location, year, brand, vendor, product, target);
	}

	public List<Sankey> popSank2Update(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target) {
		return entityRepository.popSank2Update(entity, sl, ssl, region,
				country, city, location, year, brand, vendor, product, target);
	}

	public List<Sankey> popSank3Update(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target) {
		return entityRepository.popSank3Update(entity, sl, ssl, region,
				country, city, location, year, brand, vendor, product, target);
	}

	public List<Sankey> popSank4Update(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target) {
		return entityRepository.popSank4Update(entity, sl, ssl, region,
				country, city, location, year, brand, vendor, product, target);
	}

	public AssetDataSankeyNodes popNodesUpdate(String entity, String sl,
			String ssl, String region, String country, String city,
			String location, String year, String brand, String vendor,
			String product) {
		OnLoad allNodes = entityRepository.popNodesUpdate(entity, sl, ssl,
				region, country, city, location, year, brand, vendor, product);
		AssetDataSankeyNodes nodes = new AssetDataSankeyNodes();
		nodes.setBrand(allNodes.getBrand());
		nodes.setProduct(allNodes.getProduct());
		nodes.setSubServiceLines(allNodes.getSubServiceLine());
		nodes.setVendor(allNodes.getVendor());
		return nodes;

	}

	public OnLoad popNodesUpdateDetail(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product) {
		return entityRepository.popNodesUpdate(entity, sl, ssl, region,
				country, city, location, year, brand, vendor, product);

	}

	public MapLoad popMapDetails(String city, String location, String country,
			String region) {

		List<LocationDetail> locList = entityRepository.popMapLocDetails(city,
				location, country, region);
		List<PoDetails> poList = entityRepository.popMapPoDetails(city,
				location, country, region);
		List<MapCircle> mapDetails = new ArrayList<MapCircle>();

		Map<String, String> map = new HashMap<String, String>();
		for (PoDetails po : poList) {
			map.put(po.getSubocation(), po.getPoamount());
		}

		for (LocationDetail loc : locList) {
			// String keyList = loc.getSublocation();
			MapDetails mapElement = new MapDetails();

			Coordinates cor = new Coordinates();
			cor.setX(loc.getX());
			cor.setY(loc.getY());
			mapElement.setCoordinates(cor);
			mapElement.setCountry(loc.getCountry());
			mapElement.setCity(loc.getLocation());
			mapElement.setRegion(loc.getRegion());
			mapElement.setState(loc.getState());

			List<SubLocElement> subLocList = new ArrayList<SubLocElement>();

			for (String key : loc.getSublocation()) {
				SubLocElement subLoc = new SubLocElement();
				subLoc.setLocation(key);
				subLoc.setPoAmount(map.get(key));
				subLocList.add(subLoc);
				mapElement.setLocations(subLocList);
			}
			MapCircle circle = new MapCircle();
			circle.setCircle(mapElement);
			mapDetails.add(circle);
		}

		MapLoad object = new MapLoad();
		object.setObjects(mapDetails);
		return object;
	}

	// last update for filter sankeys
	public List<AssetDataYearInfoDTO> popAssetdetailsByFiltersankey(
			String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String brand, String vendor, String product) {

		List<CallOne> details = entityRepository
				.popAssetdetailsCondYearsankeyfilter(entity, sl, ssl, region,
						country, city, location, year, brand, vendor, product);
		assetInfoDtoList = new ArrayList<AssetDataYearInfoDTO>();
		AssetDataYearInfoDTO assetInfoDto = null;
		for (CallOne callOne : details) {
			assetInfoDto = new AssetDataYearInfoDTO();
			assetInfoDto.setServiceLine(callOne.getSl());
			assetInfoDto.setSubServiceLine(callOne.getSsl());
			assetInfoDto.setTotalAssetCount(callOne.getTotalAssetCount());
			assetInfoDto.setTotalAssetValue(callOne.getTotalAssetValue());
			assetInfoDto.setTotalPOAmount(callOne.getPoAmount());
			assetInfoDtoList.add(assetInfoDto);
		}
		return assetInfoDtoList;
	}

	public List<AssetInfoDTO> fetchYearWiseDataForFiltersankey(String entity,
			String sl, String ssl, String region, String country, String city,

			String location, String year, String brand, String vendor,
			String product) {
		assetInfoList = new ArrayList<AssetInfoDTO>();
		AssetInfoDTO data = null;
		List<CallOne> querData = entityRepository
				.popAssetdetailsForYearsBysankeyFilters(entity, sl, ssl,
						region, country, city, location, year, brand, vendor,
						product);
		for (CallOne callOne : querData) {
			data = new AssetInfoDTO();
			data.setTotalAssetCount(callOne.getTotalAssetCount());
			data.setTotalAssetValue(callOne.getTotalAssetValue());
			data.setPOAmount(callOne.getPoAmount());
			data.setYear(callOne.getYear());
			assetInfoList.add(data);
		}
		return assetInfoList;
	}

	// expired asset info services

	public ExpiredAssetInfoDTO fetchPendingReplacementExpiredAssetInfo(
			String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			boolean current) {

		if (!current)
			if (year != null && !(year.equalsIgnoreCase(".*")))
				year = "01-01-" + year; // tmp fix for year data
		System.out.println("year - 13 - passed for info --" + year);
		ExpiredAssetInfoDTO assetDTO = new ExpiredAssetInfoDTO();
		ExpiredInfo data = entityRepository
				.fetchPendingReplacementExpiredAssetInfo(entity, sl, ssl,
						region, country, city, location, year);
		assetDTO.setTotalAssetCount(data.getTotalAssetCount());
		assetDTO.setTotalAssetValue(data.getTotalAssetValue());
		System.out.println("DB data returned --" + assetDTO);
		return assetDTO;
	}

	public ExpiredAssetInfoDTO fetchDueReplacementExpiredAssetInfo(
			String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			boolean current) {

		if (!current)
				year = "01-01-" + year; // tmp fix for year data

		System.out.println("year - 12 - passed for info --" + year);
		ExpiredAssetInfoDTO assetDTO = new ExpiredAssetInfoDTO();
		ExpiredInfo data = entityRepository
				.fetchDueReplacementExpiredAssetInfo(entity, sl, ssl, region,
						country, city, location, year);
		assetDTO.setTotalAssetCount(data.getTotalAssetCount());
		assetDTO.setTotalAssetValue(data.getTotalAssetValue());
		System.out.println("DB data returned --" + assetDTO);
		return assetDTO;
	}

	public ExpiredAssetInfoDTO fetchUpcomingReplacementExpiredAssetInfo(
			String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			boolean current) {
		if (!current)
				year = "01-01-" + year; // tmp fix for year data
		System.out.println("year - 11 - passed for info --" + year);
		ExpiredAssetInfoDTO assetDTO = new ExpiredAssetInfoDTO();
		ExpiredInfo data = entityRepository
				.fetchUpcomingReplacementExpiredAssetInfo(entity, sl, ssl,
						region, country, city, location, year);
		assetDTO.setTotalAssetCount(data.getTotalAssetCount());
		assetDTO.setTotalAssetValue(data.getTotalAssetValue());
		System.out.println("DB data returned --" + assetDTO);
		return assetDTO;
	}

	public List<Sankey> popExSank(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, String expType) {

		year = "01-01-" + year; // tmp fix for year data
		System.out.println("year - 1 - passed for info --" + year);

		if (expType.equalsIgnoreCase("duereplace"))
			return entityRepository.popExSankDue(entity, sl, ssl, region,
					country, city, location, year, target);
		else if (expType.equalsIgnoreCase("pendingreplace"))
			return entityRepository.popExSankPend(entity, sl, ssl, region,
					country, city, location, year, target);
		else
			return entityRepository.popExSankUpcom(entity, sl, ssl, region,
					country, city, location, year, target);

	}

	public List<Sankey> popExSank2(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, String expType) {
		year = "01-01-" + year; // tmp fix for year data
		System.out.println("year - 2 - passed for info --" + year);

		if (expType.equalsIgnoreCase("duereplace"))
			return entityRepository.popExSank2Due(entity, sl, ssl, region,
					country, city, location, year, target);
		else if (expType.equalsIgnoreCase("pendingreplace"))
			return entityRepository.popExSank2Pend(entity, sl, ssl, region,
					country, city, location, year, target);
		else
			return entityRepository.popExSank2Upcom(entity, sl, ssl, region,
					country, city, location, year, target);
	}

	public List<Sankey> popExSank3(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, String expType) {
		year = "01-01-" + year; // tmp fix for year data
		System.out.println("year - 3 - passed for info --" + year);

		if (expType.equalsIgnoreCase("duereplace"))
			return entityRepository.popExSank3Due(entity, sl, ssl, region,
					country, city, location, year, target);
		else if (expType.equalsIgnoreCase("pendingreplace"))
			return entityRepository.popExSank3Pend(entity, sl, ssl, region,
					country, city, location, year, target);
		else
			return entityRepository.popExSank3Upcom(entity, sl, ssl, region,
					country, city, location, year, target);
	}

	public List<Sankey> popExSank4(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, String expType) {

		year = "01-01-" + year; // tmp fix for year data
		System.out.println("year - 4 - passed for info --" + year);

		if (expType.equalsIgnoreCase("duereplace"))
			return entityRepository.popExSank4Due(entity, sl, ssl, region,
					country, city, location, year, target);
		else if (expType.equalsIgnoreCase("pendingreplace"))
			return entityRepository.popExSank4Pend(entity, sl, ssl, region,
					country, city, location, year, target);
		else
			return entityRepository.popExSank4Upcom(entity, sl, ssl, region,
					country, city, location, year, target);
	}

	public List<Sankey> popExSank5(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, String expType) {

		year = "01-01-" + year; // tmp fix for year data
		System.out.println("year - 5 - passed for info --" + year);

		if (expType.equalsIgnoreCase("duereplace"))
			return entityRepository.popExSank5Due(entity, sl, ssl, region,
					country, city, location, year, target);
		else if (expType.equalsIgnoreCase("pendingreplace"))
			return entityRepository.popExSank5Pend(entity, sl, ssl, region,
					country, city, location, year, target);
		else
			return entityRepository.popExSank5Upcom(entity, sl, ssl, region,
					country, city, location, year, target);
	}
	// ends here - expired asset info services
}
