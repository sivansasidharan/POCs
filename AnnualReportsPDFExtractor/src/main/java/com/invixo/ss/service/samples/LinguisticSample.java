package com.invixo.ss.service.samples;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class LinguisticSample {
	public static void main(String[] args) {
		HttpClient httpclient = HttpClients.createDefault();

		String json = "{\"language\" : \"en\", \"analyzerIds\" : [\"4fa79af1-f22c-408d-98bb-b7d7aeef7f04\", \"22a6b758-420f-4745-8a3c-46835a67c0d2\"],"
				+ "\"text\" : \"Cash flows from consolidated operations (a) 12,102 18,896 19,531 Dividends from equity accounted units 210 298 600 Cash flows from operations 12,312 19,194 20,131 Net interest paid (827) (981) (1,164) Dividends paid to holders of non-controlling interests in subsidiaries (310) (309) (191) Tax paid (1,792) (3,618) (3,698) Net cash generated from operating activities 9,383 14,286 15,078 Cash flows from investing activities Acquisitions of subsidiaries, joint ventures and associates 37 (3) – 4 Disposals of subsidiaries, joint ventures and associates 37 (38) 887 1,896 Purchases of property, plant and equipment and intangible assets 2 (4,685) (8,162) (13,001) Sales of financial assets 65 172 224 Purchases of financial assets (49) (24) (75) Net funding of equity accounted units 11 (117) (88) Other investing cash flows (b) 99 741 94 Net cash used in investing activities (4,600) (6,503) (10,946) Cash flows before financing activities 4,783 7,783 4,132 Cash flows from financing activities Equity dividends paid to owners of Rio Tinto 11 (4,076) (3,710) (3,322) Proceeds from additional borrowings 1,837 442 3,954 Repayment of borrowings (3,518) (3,476) (1,832) Proceeds from issue of equity to non-controlling interests 103 1,291 159 Own shares purchased from owners of Rio Tinto (2,028) – – Other financing cash flows 12 17 107 Net cash flows used in financing activities (7,670) (5,436) (934) Effects of exchange rates on cash and cash equivalents (159) (156) (261) Net (decrease)/increase in cash and cash equivalents (3,046) 2,191 2,937 Opening cash and cash equivalents less overdrafts 12,400 10,209 7,272 Closing cash and cash equivalents less overdrafts 21 9,354 12,400 10,209 (a) Cash flows from consolidated operations (Loss)/profit after tax for the year (1,719) 6,499 1,079 Adjustments for: – Taxation 993 3,053 2,426 – Finance items 4,702 3,008 4,407 – Share of profit after tax of equity accounted units (361) (625) (698) – Impairment charges net of reversals of investments in equity accounted units after tax – (589) 216 – Net (gains)/losses on disposal and consolidation of interests in businesses 37 (64) 563 (787) – Impairment charges net of reversals 6 2,791 1,062 7,315 – Depreciation and amortisation 4,645 4,860 4,791 – Provisions (including exchange differences on provisions) 26 726 712 1,449 Utilisation of provisions 26 (585) (973) (871) Utilisation of provision for post-retirement benefits 26 (230) (296) (635) Change in inventories 526 937 (330) Change in trade and other receivables 1,404 962 84 Change in trade and other payables (431) (380) 803 Other items (c) (295) 103 282 12,102 18,896 19,531 (b) Other investing cash flows in 2015 mainly relate to the disposal of property, plant and equipment across the Group. In 2014, this included the disposal of the Group’s St James’s Square properties. (c) Includes a cash outflow in 2015 of US$227 million (2014: inflow of US$66 million; 2013: outflow of US$267 million) mainly relating to derivative contracts transacted for operational purposes and not designated in a hedge relationship. The notes on pages 113 to 189 are an integral part of these consolidated financial statements.\" }";
		try {
			URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/linguistics/v1.0/analyze");

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", "de416cbf6281432f932baea2da8f65a5");

			// Request body
			StringEntity reqEntity = new StringEntity(json);
			request.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				System.out.println(EntityUtils.toString(entity));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}