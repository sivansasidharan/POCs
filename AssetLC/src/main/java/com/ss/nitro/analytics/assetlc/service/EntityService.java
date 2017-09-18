package com.ss.nitro.analytics.assetlc.service;

import java.util.List;

import org.springframework.data.neo4j.conversion.Result;

import com.ss.nitro.analytics.assetlc.domain.CallOne;
import com.ss.nitro.analytics.assetlc.domain.Entity;
import com.ss.nitro.analytics.assetlc.domain.MapLoad;
import com.ss.nitro.analytics.assetlc.domain.OnLoad;
import com.ss.nitro.analytics.assetlc.domain.Sankey;
import com.ss.nitro.analytics.assetlc.dto.AssetDataDTO;
import com.ss.nitro.analytics.assetlc.dto.AssetDataSankeyNodes;
import com.ss.nitro.analytics.assetlc.dto.AssetDataVendorInfoDTO;
import com.ss.nitro.analytics.assetlc.dto.AssetDataYearInfoDTO;
import com.ss.nitro.analytics.assetlc.dto.AssetInfoDTO;
import com.ss.nitro.analytics.assetlc.dto.ExpiredAssetInfoDTO;

public interface EntityService {

	Entity create(Entity entity);

	void delete(Entity entity);

	Entity findById(long id);

	Result<Entity> findAll();

	AssetDataDTO popOnLoad();

	List<CallOne> popAssetDetails();

	List<CallOne> popAssetdetailsCond(String entity, String sl, String ssl);

	List<AssetInfoDTO> popAssetdetailsCondRegion(String entity, String sl,
			String ssl, String region, String country, String city,

			String location, String year);

	List<AssetDataYearInfoDTO> popAssetdetailsByFilters(String entity,
			String sl, String ssl, String region, String country, String city,
			String location, String year, String pred);

	List<AssetInfoDTO> fetchYearWiseDataForFilters(String entity, String sl,
			String ssl, String region, String country, String city,

			String location, String year, String pred);

	List<AssetDataVendorInfoDTO> popAssetdetailsCondLast(String entity,
			String sl, String ssl, String region, String country, String city,
			String location, String year);

	List<Sankey> popSank(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String target);

	List<Sankey> popSank2(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String target);

	List<Sankey> popSank3(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String target);

	List<Sankey> popSank4(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String target);

	OnLoad popNodes(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year);

	List<Sankey> popSankUpdate(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target);

	List<Sankey> popSank2Update(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target);

	List<Sankey> popSank3Update(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target);

	List<Sankey> popSank4Update(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target);

	AssetDataSankeyNodes popNodesUpdate(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product);

	OnLoad popNodesUpdateDetail(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product);

	MapLoad popMapDetails(String city, String location, String country,
			String region);

	// last update for filter sankeys

	List<AssetDataYearInfoDTO> popAssetdetailsByFiltersankey(String entity,
			String sl, String ssl, String region, String country, String city,
			String location, String year, String brand, String vendor,
			String product);

	List<AssetInfoDTO> fetchYearWiseDataForFiltersankey(String entity,
			String sl, String ssl, String region, String country, String city,
			String location, String year, String brand, String vendor,
			String product);

	// expired asset info services

	ExpiredAssetInfoDTO fetchPendingReplacementExpiredAssetInfo(String entity,
			String sl, String ssl, String region, String country, String city,
			String location,String year,boolean current);

	ExpiredAssetInfoDTO fetchDueReplacementExpiredAssetInfo(String entity,
			String sl, String ssl, String region, String country, String city,
			String location,String year,
			boolean current);

	ExpiredAssetInfoDTO fetchUpcomingReplacementExpiredAssetInfo(String entity,
			String sl, String ssl, String region, String country, String city,
			String location,String year,
			boolean current);

	List<Sankey> popExSank(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String target, String expType);

	List<Sankey> popExSank2(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, String expType);

	List<Sankey> popExSank3(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, String expType);

	List<Sankey> popExSank4(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, String expType);

	List<Sankey> popExSank5(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target, String expType);

	// ends here - expired asset info services
}
