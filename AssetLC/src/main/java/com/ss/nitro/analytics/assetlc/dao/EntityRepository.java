package com.ss.nitro.analytics.assetlc.dao;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.ss.nitro.analytics.assetlc.domain.CallOne;
import com.ss.nitro.analytics.assetlc.domain.Entity;
import com.ss.nitro.analytics.assetlc.domain.ExpiredInfo;
import com.ss.nitro.analytics.assetlc.domain.LocationDetail;
import com.ss.nitro.analytics.assetlc.domain.OnLoad;
import com.ss.nitro.analytics.assetlc.domain.PoDetails;
import com.ss.nitro.analytics.assetlc.domain.Prediction;
import com.ss.nitro.analytics.assetlc.domain.Sankey;

public interface EntityRepository extends GraphRepository<Entity> {
	@Query("Match (n:PO)  return  sum(round(toFloat( n.UnitPrice))) as totalAssetValue, sum(round(toFloat( n.POUnit$))) as poamount,"
			+ "count(n) as totalAssetCount, collect(distinct n.Entity) as "
			+ "Entity,collect(distinct n.ServiceLine) as ServiceLine, "
			+ "collect(distinct n.SubServiceLine) as SubServiceLine, "
			+ "collect(distinct n.Region) as Region, "
			+ "collect(distinct n.Country) as Country, "
			+ "collect(distinct n.Location) as City, "
			+ "collect(distinct n.SubLocationName) as Location ")
	OnLoad popOnLoad();

	@Query("Match (n:PO)  return n.Year as year,  sum(round(toFloat( n.UnitPrice))) as "
			+ "totalAssetValue, count(n) as totalAssetCount,sum(round(toFloat( n.POUnit$))) as poamount order by year")
	List<CallOne> populateAssetDetailsYear();

	@Query("match(n:POP)  return n.Year as year ,Sum(toFloat(n.Amount_Forecast)) as totalAssetValue, Sum(toFloat(n.Quantity_Forecast)) "
			+ " as totalAssetCount, Sum(toFloat(n.PoAmount_Forecast)) as poamount order by year")
	List<CallOne> populateAssetDetailsYearPred();

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} "
			+ "return n.Year as year,  sum(round(toFloat( n.UnitPrice))) as "
			+ "totalAssetValue, count(n) as totalAssetCount,sum(round(toFloat( n.POUnit$))) as poamount")
	List<CallOne> popAssetdetailsCond(String entity, String sl, String ssl);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} "
			+ "AND  n.Region=~{3} AND n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} AND n.Year=~{7} return n.Year as year, "
			+ " sum(round(toFloat( n.UnitPrice))) as  totalAssetValue, count(n) as totalAssetCount,sum(round(toFloat( n.POUnit$)))"
			+ " as poamount order by year")
	List<CallOne> popAssetdetailsForYearsByFilters(String entity, String sl,
			String ssl, String region, String country, String city,
			String location, String year);

	@Query("match(n:POP) where n.ServiceLine=~{0} and n.SubServiceLine=~{1}  AND n.Country=~{2} and  n.Year=~{3} return n.Year as year , "
			+ "Sum(toFloat(n.Amount_Forecast))  as totalAssetValue, Sum(toFloat(n.Quantity_Forecast)) as "
			+ " totalAssetCount, Sum(toFloat(n.PoAmount_Forecast)) as poamount order by year")
	List<CallOne> popAssetdetailsForYearsByFilters(String sl, String ssl,
			String country, String year);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2}"
			+ " and n.Region =~{3} and n.Country=~{4}  AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7}   return distinct n.ServiceLine "
			+ "as sl, n.SubServiceLine as ssl,   sum(round(toFloat( n.UnitPrice))) as totalAssetValue, "
			+ "count(n) as totalAssetCount,sum(round(toFloat( n.POUnit$))) as poamount order by sl, ssl")
	List<CallOne> popAssetdetailsCondYear(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year);

	@Query("Match (n:POP) where n.ServiceLine=~{0} AND n.SubServiceLine=~{1} and n.Country =~{2}"
			+ "  and n.Year =~{3}  return distinct n.ServiceLine as sl, n.SubServiceLine as ssl,  "
			+ " Sum(toFloat(n.Amount_Forecast))  as totalAssetValue, Sum(toFloat(n.Quantity_Forecast)) as "
			+ " totalAssetCount, Sum(toFloat(n.PoAmount_Forecast)) as poamount order by sl, ssl")
	List<CallOne> popAssetdetailsCondYear(String sl, String ssl,
			String country, String year);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and "
			+ "n.Region =~{3} and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} return distinct n.ServiceLine as sl , "
			+ "n.SubServiceLine as ssl,  n.Brand as brand, n.VendorName_cleaned as vendor , n.ProductName_cleaned"
			+ " as product,sum(round(toFloat( n.UnitPrice))) as totalAssetValue, count(n) as totalAssetCount, sum(round(toFloat( n.POUnit$))) as poamount "
			+ "order by sl, ssl")
	List<CallOne> popAssetdetailsCondAll(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year);

	@Query("match(n:POP) where n.ProductCategory={0} and n.Qtr=~{1} and n.ServiceLine={2} and n.Attribute={3}"
			+ " return toInt(Sum(toFloat(n.Forecast)))	as forecast")
	Prediction popPrediction(String productCategory, String qtr, String sl,
			String attribute);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region "
			+ "=~{3}and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} with  "
			+ "n.SubServiceLine as source , {8} as target ,sum(round(toFloat( n.UnitPrice))) as "
			+ "totalAssetValue,count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount , '0.25' as value ,'0' as level "
			+ " return distinct source  , target, case {8} When 'poamount'  then poamount When 'totalAssetCount' "
			+ "then totalAssetCount ELSE totalAssetValue END as value2, value,level")
	List<Sankey> popSank(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String target);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region "
			+ "=~{3}and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} with  "
			+ "{8} as source , n.ProductName_cleaned as target ,sum(round(toFloat( n.UnitPrice))) as totalAssetValue, "
			+ "count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount, '0.25' as value , '1' as level return distinct source , target "
			+ " , case {8} When 'poamount'  then poamount When 'totalAssetCount' then totalAssetCount ELSE "
			+ "totalAssetValue END as value2, value,level")
	List<Sankey> popSank2(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String target);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} with  n.VendorName_cleaned as "
			+ "target,  n.ProductName_cleaned as source  ,sum(round(toFloat( n.UnitPrice))) as totalAssetValue,count(n) "
			+ "as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount, '0.25' as value , '2' as level return distinct source , target ,"
			+ " case {8} When 'poamount'  then poamount When 'totalAssetCount' then totalAssetCount "
			+ "ELSE totalAssetValue END as value2, value,level")
	List<Sankey> popSank3(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String target);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} with  "
			+ "n.VendorName_cleaned as source, n.Brand as target ,sum(round(toFloat( n.UnitPrice))) as "
			+ " totalAssetValue,count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount, "
			+ "'0.25' as value , '3' as level return distinct source , target  , case {8} When 'poamount'  then poamount When "
			+ " 'totalAssetCount' then totalAssetCount ELSE totalAssetValue END as value2, value,level")
	List<Sankey> popSank4(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String target);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and "
			+ " n.Region =~{3} and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} "
			+ " return collect(distinct n.SubServiceLine) as SubServiceLine, "
			+ " collect(distinct n.Brand) as brand, "
			+ " collect(distinct n.VendorName_cleaned) as vendor, "
			+ " collect(distinct n.ProductName_cleaned) as product ")
	OnLoad popNodes(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region "
			+ "=~{3}and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} and "
			+ " n.Brand=~{8} and n.VendorName_cleaned=~{9} and n.ProductName_cleaned=~ {10} with  "
			+ "n.SubServiceLine as source , {11} as target ,sum(round(toFloat( n.UnitPrice))) as "
			+ "totalAssetValue,count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount , '0.25' as value "
			+ "return distinct source  , target, case {11} When 'poamount'  then poamount When 'totalAssetCount' "
			+ "then totalAssetCount ELSE totalAssetValue END as value2, value")
	List<Sankey> popSankUpdate(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region "
			+ "=~{3}and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} and"
			+ " n.Brand=~{8} and n.VendorName_cleaned=~{9} and n.ProductName_cleaned=~ {10} with  "
			+ "{11} as source , n.ProductName_cleaned as target ,sum(round(toFloat( n.UnitPrice))) as totalAssetValue, "
			+ "count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount, '0.25' as value return distinct source , target "
			+ " , case {11} When 'poamount'  then poamount When 'totalAssetCount' then totalAssetCount ELSE "
			+ "totalAssetValue END as value2, value")
	List<Sankey> popSank2Update(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7}  and"
			+ " n.Brand=~{8} and n.VendorName_cleaned=~{9} and n.ProductName_cleaned=~ {10} with  "
			+ "n.VendorName_cleaned as source, n.Brand as target ,sum(round(toFloat( n.UnitPrice))) as "
			+ " totalAssetValue,count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount "
			+ ", '0.25' as value return distinct source , target  , case {11} When 'poamount'  then poamount When "
			+ " 'totalAssetCount' then totalAssetCount ELSE totalAssetValue END as value2, value")
	List<Sankey> popSank4Update(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} and "
			+ " n.Brand=~{8} and n.VendorName_cleaned=~{9} and n.ProductName_cleaned=~ {10} with  n.VendorName_cleaned as "
			+ " target,  n.ProductName_cleaned as source  ,sum(round(toFloat( n.UnitPrice))) as totalAssetValue,count(n) "
			+ "as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount , '0.25' as value return distinct source , target ,"
			+ " case {11} When 'poamount'  then poamount When 'totalAssetCount' then totalAssetCount "
			+ "ELSE totalAssetValue END as value2, value")
	List<Sankey> popSank3Update(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String brand, String vendor, String product,
			String target);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and "
			+ " n.Region =~{3} and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} and "
			+ " n.Brand=~{8} and n.VendorName_cleaned=~{9} and n.ProductName_cleaned=~ {10}"
			+ " return collect(distinct n.SubServiceLine) as SubServiceLine, "
			+ " collect(distinct n.Brand) as brand, "
			+ " collect(distinct n.VendorName_cleaned) as vendor, "
			+ " collect(distinct n.ProductName_cleaned) as product ")
	OnLoad popNodesUpdate(String entity, String sl, String ssl, String region,
			String country, String city, String location, String year,
			String brand, String vendor, String product);

	@Query("Match (n:Location) , (a:SubLocation) where n.Location=~{0}  and "
			+ " n.Country=~{2} and n.Region=~{3} and n-[:SubLocation]->(a) and a.Name=~{1} return n.Location as location, "
			+ " n.State as state, n.Country as country, n.Region as region , n.x as x,n.y as y, collect "
			+ "(distinct a.Name) as sublocation")
	List<LocationDetail> popMapLocDetails(String city, String location,
			String country, String region);

	@Query("Match (n:PO) where n.Location=~{0} and n.SubLocationName=~{1} and n.Country=~{2} and n.Region =~{3} "
			+ " return n.SubLocationName as sublocation, sum(round(toFloat( n.POUnit$))) as poamount")
	List<PoDetails> popMapPoDetails(String city, String location,
			String country, String region);

	// last update - for sankey filters

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2}"
			+ " and n.Region =~{3} and n.Country=~{4}  AND  n.Location=~{5} AND n.SubLocationName=~{6} and n.Year =~{7} and n.Brand=~{8} and n.VendorName_cleaned=~{9} and  n.ProductName_cleaned=~{10}"
			+ "  return distinct n.ServiceLine "
			+ "as sl, n.SubServiceLine as ssl,   sum(round(toFloat( n.UnitPrice))) as totalAssetValue, "
			+ "count(n) as totalAssetCount,sum(round(toFloat( n.POUnit$))) as poamount order by sl, ssl")
	List<CallOne> popAssetdetailsCondYearsankeyfilter(String entity, String sl,
			String ssl, String region, String country, String city,
			String location, String year, String brand, String vendor,
			String product);

	@Query("Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} "
			+ "AND  n.Region=~{3} AND n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} AND n.Year=~{7} and n.Brand=~{8} and n.VendorName_cleaned=~{9} and  n.ProductName_cleaned=~{10} "
			+ "return n.Year as year, "
			+ " sum(round(toFloat( n.UnitPrice))) as  totalAssetValue, count(n) as totalAssetCount,sum(round(toFloat( n.POUnit$)))"
			+ " as poamount order by year")
	List<CallOne> popAssetdetailsForYearsBysankeyFilters(String entity,
			String sl, String ssl, String region, String country, String city,
			String location, String year, String brand, String vendor,
			String product);

	@Query("WITH split({7}, '-') AS cd	MATCH (d:PO) where d.Entity  =~{0} AND  "
			+ "d.ServiceLine=~{1} AND d.SubServiceLine=~{2} and d.Region =~{3} and d.Country=~{4} "
			+ "AND  d.Location=~{5} AND d.SubLocationName=~{6} WITH cd, split(d.AssetExpiryDate, '-') "
			+ "AS dd, d WHERE (cd[2] > dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] > dd[0]) OR (cd[0] = dd[0] "
			+ "AND (cd[1] > dd[1])))) RETURN sum(round(toFloat( d.UnitPrice))) as totalAssetValue, count(d) as totalAssetCount")
	ExpiredInfo fetchPendingReplacementExpiredAssetInfo(String entity,
			String sl, String ssl, String region, String country, String city,
			String location, String year);

	@Query("WITH split({7}, '-') AS cd	MATCH (d:PO) where"
			+ " d.Entity  =~{0} AND  d.ServiceLine=~{1} AND d.SubServiceLine=~{2} and "
					+ "d.Region =~{3} and d.Country=~{4} AND  d.Location=~{5} AND "
							+ "d.SubLocationName=~{6} WITH cd, split(d.AssetExpiryDate, '-') AS dd, "
							+ "d WHERE (cd[2] = dd[2] AND (cd[1] = dd[1])) RETURN sum(round(toFloat( d.UnitPrice)))"
							+ " as totalAssetValue, count(d) as totalAssetCount")
	ExpiredInfo fetchDueReplacementExpiredAssetInfo(String entity, String sl,
			String ssl, String region, String country, String city,
			String location, String year);

	@Query("WITH split({7}, '-') AS cd	MATCH (d:PO) where d.Entity  =~{0} AND "
			+ " d.ServiceLine=~{1} AND d.SubServiceLine=~{2} and d.Region =~{3} and "
			+ "d.Country=~{4} AND  d.Location=~{5} AND d.SubLocationName=~{6} WITH cd, "
			+ "split(d.AssetExpiryDate, '-') AS dd, d WHERE  (cd[2] < dd[2]) OR  (cd[2] = dd[2] "
			+ "AND ((cd[0] < dd[0]) OR (cd[0] = dd[0] AND  (cd[1] < dd[1])))) RETURN sum(round(toFloat( d.UnitPrice))) "
			+ "as totalAssetValue, count(d) as totalAssetCount")
	ExpiredInfo fetchUpcomingReplacementExpiredAssetInfo(String entity,
			String sl, String ssl, String region, String country, String city,
			String location, String year);

	@Query(" WITH split({7}, '-') AS cd "
			+ "MATCH (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} "
			+ "WITH cd, split(n.AssetExpiryDate, '-') AS dd, n WHERE (cd[2] = dd[2] AND  (cd[1] = dd[1])) "
			+ "with n.SubServiceLine as source ,{8} as target ,sum(round(toFloat( n.UnitPrice))) as "
			+ "totalAssetValue,count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount , '0.25' as value  ,"
			+ "'0' as level  RETURN distinct source  , target, case {8} When 'poamount'  "
			+ "then poamount When 'totalAssetCount' then totalAssetCount ELSE totalAssetValue END as value2, value,level ")
	List<Sankey> popExSankDue(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query("WITH split({7}, '-') AS cd Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} and n.Country=~{4} AND  n.Location=~{5} "
			+ "AND n.SubLocationName=~{6} WITH cd, split(n.AssetExpiryDate, '-') AS dd, n WHERE (cd[2] = dd[2] AND (cd[1] = dd[1])) with {8} as source , n.ProductName_cleaned "
			+ "as target ,sum(round(toFloat( n.UnitPrice))) as totalAssetValue, "
			+ "count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount, '0.25' as value, '1' as level return distinct source , target  , "
			+ "case {8} When 'poamount'  then poamount When 'totalAssetCount' then totalAssetCount ELSE	totalAssetValue END as value2, value,level")
	List<Sankey> popExSank2Due(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query("WITH split({7}, '-') AS cd	Match (n:PO) where n.Entity  =~{0} "
			+ "AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} "
			+ "WITH cd, split(n.AssetExpiryDate, '-') AS dd, n WHERE (cd[2] = dd[2] "
			+ "AND (cd[1] = dd[1])) with n.VendorName_cleaned as target,  n.ProductName_cleaned as source  ,"
			+ "sum(round(toFloat( n.UnitPrice))) as totalAssetValue,count(n) as totalAssetCount , "
			+ "sum(round(toFloat( n.POUnit$))) as poamount, '0.25' as value, '2' as level return distinct source , "
			+ "target , case {8} When 'poamount'  then poamount When 'totalAssetCount' "
			+ "then totalAssetCount ELSE totalAssetValue END as value2, value,level")
	List<Sankey> popExSank3Due(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query(" WITH split({7}, '-') AS cd Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} "
			+ "AND n.SubServiceLine=~{2} and n.Region =~{3} and n.Country=~{4} AND  n.Location=~{5} AND "
			+ "n.SubLocationName=~{6} WITH cd, split(n.AssetExpiryDate, '-') AS dd, n WHERE (cd[2] = dd[2] AND "
			+ "(cd[1] = dd[1])) with n.VendorName_cleaned as source, n.Brand as target ,sum(round(toFloat( n.UnitPrice))) "
			+ "as totalAssetValue,count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount,"
			+ "'0.25' as value , '3' as level return distinct source , target  , case {8} When 'poamount'  "
			+ "then poamount When 'totalAssetCount' then totalAssetCount ELSE totalAssetValue END as value2, value,level")
	List<Sankey> popExSank4Due(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query("WITH split({7}, '-') AS cd	Match (n:PO) where n.Entity  =~{0} AND  "
			+ "n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} WITH cd, split(n.AssetExpiryDate, '-') "
			+ "AS dd, n WHERE (cd[2] = dd[2] AND (cd[1] = dd[1])) with n.asset_tag as target, "
			+ "n.Brand as source ,sum(round(toFloat( n.UnitPrice))) as	totalAssetValue,count(n) as totalAssetCount , "
			+ "sum(round(toFloat( n.POUnit$))) as poamount,	'0.25' as value , '4' as level "
			+ "return distinct source , target  , case {8} When 'poamount'  "
			+ "then poamount When 'totalAssetCount' then totalAssetCount "
			+ "ELSE totalAssetValue END as value2, value,level limit 8")
	List<Sankey> popExSank5Due(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query(" WITH split({7}, '-') AS cd "
			+ "MATCH (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} "
			+ "WITH cd, "
			+ "split(n.AssetExpiryDate, '-') AS dd, n WHERE (cd[2] > dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] > dd[0]) OR (cd[0] = dd[0] AND (cd[1] > dd[1])))) "
			+ "with n.SubServiceLine as source ,{8} as target ,sum(round(toFloat( n.UnitPrice))) as "
			+ "totalAssetValue,count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount , '0.25' as value  ,"
			+ "'0' as level  RETURN distinct source  , target, case {8} When 'poamount'  "
			+ "then poamount When 'totalAssetCount' then totalAssetCount ELSE totalAssetValue END as value2, value,level ")
	List<Sankey> popExSankPend(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query("WITH split({7}, '-') AS cd Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} and n.Country=~{4} AND  n.Location=~{5} "
			+ "AND n.SubLocationName=~{6} "
			+ "WITH cd, "
			+ "split(n.AssetExpiryDate, '-') AS dd, n WHERE (cd[2] > dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] > dd[0]) OR (cd[0] = dd[0] AND (cd[1] > dd[1])))) "
			+ "with {8} as source , n.ProductName_cleaned "
			+ "as target ,sum(round(toFloat( n.UnitPrice))) as totalAssetValue, "
			+ "count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount, '0.25' as value, '1' as level return distinct source , target  , "
			+ "case {8} When 'poamount'  then poamount When 'totalAssetCount' then totalAssetCount ELSE	totalAssetValue END as value2, value,level")
	List<Sankey> popExSank2Pend(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query("WITH split({7}, '-') AS cd	Match (n:PO) where n.Entity  =~{0} "
			+ "AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} "
			+ "WITH cd, "
			+ "split(n.AssetExpiryDate, '-') AS dd, n WHERE (cd[2] > dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] > dd[0]) OR (cd[0] = dd[0] AND (cd[1] > dd[1])))) "
			+ "with n.VendorName_cleaned as target,  n.ProductName_cleaned as source  ,"
			+ "sum(round(toFloat( n.UnitPrice))) as totalAssetValue,count(n) as totalAssetCount , "
			+ "sum(round(toFloat( n.POUnit$))) as poamount, '0.25' as value, '2' as level return distinct source , "
			+ "target , case {8} When 'poamount'  then poamount When 'totalAssetCount' "
			+ "then totalAssetCount ELSE totalAssetValue END as value2, value,level")
	List<Sankey> popExSank3Pend(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query(" WITH split({7}, '-') AS cd Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} "
			+ "AND n.SubServiceLine=~{2} and n.Region =~{3} and n.Country=~{4} AND  n.Location=~{5} AND "
			+ "n.SubLocationName=~{6} "
			+ "WITH cd, "
			+ "split(n.AssetExpiryDate, '-') AS dd, n WHERE (cd[2] > dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] > dd[0]) OR (cd[0] = dd[0] AND (cd[1] > dd[1])))) "
			+ "with n.VendorName_cleaned as source, n.Brand as target ,sum(round(toFloat( n.UnitPrice))) "
			+ "as totalAssetValue,count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount,"
			+ "'0.25' as value , '3' as level return distinct source , target  , case {8} When 'poamount'  "
			+ "then poamount When 'totalAssetCount' then totalAssetCount ELSE totalAssetValue END as value2, value,level")
	List<Sankey> popExSank4Pend(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query("WITH split({7}, '-') AS cd	Match (n:PO) where n.Entity  =~{0} AND  "
			+ "n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} "
			+ "WITH cd, "
			+ "split(n.AssetExpiryDate, '-') AS dd, n WHERE (cd[2] > dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] > dd[0]) OR (cd[0] = dd[0] AND (cd[1] > dd[1])))) "
			+ "with n.asset_tag as target, "
			+ "n.Brand as source ,sum(round(toFloat( n.UnitPrice))) as	totalAssetValue,count(n) as totalAssetCount , "
			+ "sum(round(toFloat( n.POUnit$))) as poamount,	'0.25' as value , '4' as level "
			+ "return distinct source , target  , case {8} When 'poamount'  "
			+ "then poamount When 'totalAssetCount' then totalAssetCount "
			+ "ELSE totalAssetValue END as value2, value,level  limit 8")
	List<Sankey> popExSank5Pend(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query(" WITH split({7}, '-') AS cd "
			+ "MATCH (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} "
			+ "WITH cd, "
			+ "split(n.AssetExpiryDate, '-') AS dd, n WHERE  (cd[2] < dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] < dd[0]) OR (cd[0] = dd[0] AND  (cd[1] < dd[1])))) "
			+ "with n.SubServiceLine as source ,{8} as target ,sum(round(toFloat( n.UnitPrice))) as "
			+ "totalAssetValue,count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount , '0.25' as value  ,"
			+ "'0' as level  RETURN distinct source  , target, case {8} When 'poamount'  "
			+ "then poamount When 'totalAssetCount' then totalAssetCount ELSE totalAssetValue END as value2, value,level ")
	List<Sankey> popExSankUpcom(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query("WITH split({7}, '-') AS cd Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} and n.Country=~{4} AND  n.Location=~{5} "
			+ "AND n.SubLocationName=~{6} "
			+ "WITH cd, "
			+ "split(n.AssetExpiryDate, '-') AS dd, n WHERE  (cd[2] < dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] < dd[0]) OR (cd[0] = dd[0] AND  (cd[1] < dd[1]))))  "
			+ "with {8} as source , n.ProductName_cleaned "
			+ "as target ,sum(round(toFloat( n.UnitPrice))) as totalAssetValue, "
			+ "count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount, '0.25' as value, '1' as level return distinct source , target  , "
			+ "case {8} When 'poamount'  then poamount When 'totalAssetCount' then totalAssetCount ELSE	totalAssetValue END as value2, value,level")
	List<Sankey> popExSank2Upcom(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query("WITH split({7}, '-') AS cd	Match (n:PO) where n.Entity  =~{0} "
			+ "AND  n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} "
			+ "WITH cd, "
			+ "split(n.AssetExpiryDate, '-') AS dd, n WHERE  (cd[2] < dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] < dd[0]) OR (cd[0] = dd[0] AND  (cd[1] < dd[1])))) "
			+ "with n.VendorName_cleaned as target,  n.ProductName_cleaned as source  ,"
			+ "sum(round(toFloat( n.UnitPrice))) as totalAssetValue,count(n) as totalAssetCount , "
			+ "sum(round(toFloat( n.POUnit$))) as poamount, '0.25' as value, '2' as level return distinct source , "
			+ "target , case {8} When 'poamount'  then poamount When 'totalAssetCount' "
			+ "then totalAssetCount ELSE totalAssetValue END as value2, value,level")
	List<Sankey> popExSank3Upcom(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query(" WITH split({7}, '-') AS cd Match (n:PO) where n.Entity  =~{0} AND  n.ServiceLine=~{1} "
			+ "AND n.SubServiceLine=~{2} and n.Region =~{3} and n.Country=~{4} AND  n.Location=~{5} AND "
			+ "n.SubLocationName=~{6} "
			+ "WITH cd, "
			+ "split(n.AssetExpiryDate, '-') AS dd, n WHERE  (cd[2] < dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] < dd[0]) OR (cd[0] = dd[0] AND  (cd[1] < dd[1])))) "
			+ "with n.VendorName_cleaned as source, n.Brand as target ,sum(round(toFloat( n.UnitPrice))) "
			+ "as totalAssetValue,count(n) as totalAssetCount , sum(round(toFloat( n.POUnit$))) as poamount,"
			+ "'0.25' as value , '3' as level return distinct source , target  , case {8} When 'poamount'  "
			+ "then poamount When 'totalAssetCount' then totalAssetCount ELSE totalAssetValue END as value2, value,level")
	List<Sankey> popExSank4Upcom(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

	@Query("WITH split({7}, '-') AS cd	Match (n:PO) where n.Entity  =~{0} AND  "
			+ "n.ServiceLine=~{1} AND n.SubServiceLine=~{2} and n.Region =~{3} "
			+ "and n.Country=~{4} AND  n.Location=~{5} AND n.SubLocationName=~{6} "
			+ "WITH cd, "
			+ "split(n.AssetExpiryDate, '-') AS dd, n WHERE  (cd[2] < dd[2]) OR  (cd[2] = dd[2] AND ((cd[0] < dd[0]) OR (cd[0] = dd[0] AND  (cd[1] < dd[1]))))  "
			+ "with n.asset_tag as target, "
			+ "n.Brand as source ,sum(round(toFloat( n.UnitPrice))) as	totalAssetValue,count(n) as totalAssetCount , "
			+ "sum(round(toFloat( n.POUnit$))) as poamount,	'0.25' as value , '4' as level "
			+ "return distinct source , target  , case {8} When 'poamount'  "
			+ "then poamount When 'totalAssetCount' then totalAssetCount "
			+ "ELSE totalAssetValue END as value2, value,level  limit 8")
	List<Sankey> popExSank5Upcom(String entity, String sl, String ssl,
			String region, String country, String city, String location,
			String year, String target);

}
