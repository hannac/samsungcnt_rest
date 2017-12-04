package com.ryu.bigdata.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryu.bigdata.dto.requestDto.DailyRequestDto;
import com.ryu.bigdata.dto.requestDto.SkuRequestDto;
import com.ryu.bigdata.dto.requestDto.VectorConvertitsRequestDto;
import com.ryu.bigdata.dto.requestDto.VectorUpsertRequestDto;
import com.ryu.bigdata.dto.responseDto.CommonResult;
import com.ryu.bigdata.dto.responseDto.VectorConvertitsResponseDto;
import com.ryu.bigdata.exception.NoContentException;
import com.ryu.bigdata.mapper.ProductMapper;
import com.ryu.bigdata.mapper.VectorMapper;
import com.ryu.bigdata.server.ImgSearchServer;
import com.ryu.bigdata.service.DailyInfoService;
import com.ryu.bigdata.service.SkuService;
import com.ryu.bigdata.service.VectorService;
import com.ryu.bigdata.vo.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	private ProductMapper mapper;

	@Autowired
	private VectorMapper vectorMapper;

	// 2.1
	// /v1/competition/product/{0}/weekly/from/{1}/{2}/to/{3}/{4}
	@ApiOperation(value = "경쟁제품정보 – 주간정보(경쟁사 제품 상세정보)")
	@GetMapping(
			value="/v1/competition/product/{productId}/weekly/from/{startYear}/{startWeek}/to/{endYear}/{endWeek}"
			,produces = "application/json; charset=utf8")
	@ResponseBody
	public JSONObject competitionProduct(
			@PathVariable(value = "productId", required = true) String productId
			, @PathVariable(value = "startYear", required = true) int startYear
			, @PathVariable(value = "startWeek", required = true) int startWeek
			, @PathVariable(value = "endYear", required = true) int endYear
			, @PathVariable(value = "endWeek", required = true) int endWeek) throws NoContentException {

		CompetitionInfoSelet info =
				new CompetitionInfoSelet(productId, startYear, startWeek, endYear, endWeek);

		// 최종 리턴할 json 형식을 저장할 맵
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// json 에서 data 의 내용을 저장할 맵
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// resultMap 에 리턴할 내용 저장
		// 현재 result 는 목업이므로 그냥 이 코드 그대로 사용해도 될 것 같습니다.
		Result result = new Result();
		result.setCode("200");
		String success = "성공"; // <-- 추후 성공 실패 모듈 적용.

		result.setMessage(success);

		// 껍데기 만들기
		ProductWeeklyVoc ProductWeekly = new ProductWeeklyVoc();

		Product product = mapper.selectProductInfo(Long.parseLong(productId));

		// 조회되는 제품이 없으면 컨텐츠 없음 예외 발생 -> 핸들러에 의해 처리됨 203 응답 발생함
		if (product == null)
			throw new NoContentException();

		if(product != null)	{
			ProductWeekly.setProductId(product.getProductId());
			ProductWeekly.setProductCd(product.getProductCd());
			ProductWeekly.setAccSales(product.getAccSales());
			ProductWeekly.setBrandNm(product.getBrandNm());
			ProductWeekly.setReleaseDt(product.getReleaseDt());
			ProductWeekly.setRetailPrice(product.getRetailPrice());
			ProductWeekly.setEndOfLife(product.getEndOfLife());
			ProductWeekly.setProductNm(product.getProductNm());
			ProductWeekly.setItem(product.getItem());
			ProductWeekly.setColor(product.getColor());
			ProductWeekly.setSeason(product.getSeason());
			ProductWeekly.setSize(product.getSize());
			ProductWeekly.setFabric(product.getFabric());
			ProductWeekly.setImageUrl(product.getImageUrl());
			ProductWeekly.setCrawlUrl(product.getCrawlUrl());
			ProductWeekly.setOwnProductYn(product.getOwnProductYn());
			ProductWeekly.setSimillarScore(product.getSimillarScore());
		}

		initProductsDatabyIdx();

		// weeklySales 추가
		List<WeeklySales> weeklySales;// = new ArrayList<WeeklySales>();
		//weeklySales.add(new WeeklySales("2017_43", 13));

		WeeklySalesSearch weeklySalesSearch = new WeeklySalesSearch();
		weeklySalesSearch.setProductId(productId);
		weeklySalesSearch.setStartYear(startYear);
		weeklySalesSearch.setStartWeek(startWeek);
		weeklySalesSearch.setEndYear(endYear);
		weeklySalesSearch.setEndWeek(endWeek);

		weeklySales = mapper.selectWeeklyInfo(weeklySalesSearch);

		if(weeklySales.size() == 0 && ProductWeekly.getOwnProductYn() == "N")
		{
			weeklySales = new ArrayList<WeeklySales>();

			//for(int y=getWeekofYear(p.getReleaseDt()); y <= getWeekofYear(p.getEndOfLife()); y++)
			for(int y=getWeekofYear(ProductWeekly.getReleaseDt()); y <= getWeekofYear(getNow()); y++)
				weeklySales.add(new WeeklySales("2017_" + y, y));
		}

		ProductWeekly.setWeeklySales(weeklySales);

		// vocList 추가
		List<Voc> vocList = new ArrayList<Voc>();
		vocList.add(new Voc(3.4, "좋아요/배송늦음"));

		ProductWeekly.setVocList(vocList);

		// data 맵에 product 맵을 저장
		dataMap.put("product", ProductWeekly);

		// 최종적으로 리턴할 jsonObject 를 저장할 맵에 data, result(객체)을 저장
		jsonMap.put("data", dataMap);
		jsonMap.put("result", result);

		// 위에서 data, result 가 추가된 jsonMap 으로 리턴에 사용할  JSONObject 생성
		JSONObject json = new JSONObject(jsonMap);

		// 생성된 JSONObeject 를 json 형태의 문자열로 변경한 뒤 리턴
		return json;
	}

	// 2.2
	// /v1/competition/products/
	@ApiOperation(value = "경쟁제품정보 - 제품목록")
	@PostMapping(value = "/v1/competition/products/", produces = "application/json; charset=utf8")
	@ResponseBody
	public JSONObject competitionProductList(
			@ApiParam(name = "productId", value = "검색조건", required = true)
			@RequestBody ProductIds productId) throws NoContentException {
		// 최종 리턴할 json 형식을 저장할 맵
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// json 에서 data 의 내용을 저장할 맵
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// resultMap 에 리턴할 내용 저장
		// 현재 result 는 목업이므로 그냥 이 코드 그대로 사용해도 될 것 같습니다.
		Result result = new Result();
		result.setCode("200");
		String success = "성공"; // <-- 추후 성공 실패 모듈 적용.

		result.setMessage(success);

		// 리스트로 반환
		List<Product> products = new ArrayList<Product>();

		for (int z = 0; z < productId.getProductId().length; z++) {

			Product product = mapper.selectProductInfo(Long.parseLong(productId.getProductId()[z]));

			// 조회 결과가 null이 아닐때만 목록 추가
			if (product != null)
				products.add(product);
		}

		// 목록에 추가된 제품 정보가 없으면 컨텐츠 없음 예외 발생
		if (products.size() == 0) {
			throw new NoContentException();
		}

		// data 맵에 products 맵을 저장
		dataMap.put("products", products);

		// 최종적으로 리턴할 jsonObject 를 저장할 맵에 data, result(객체)을 저장
		jsonMap.put("data", dataMap);
		jsonMap.put("result", result);

		// 위에서 data, result 가 추가된 jsonMap 으로 리턴에 사용할  JSONObject 생성
		JSONObject json = new JSONObject(jsonMap);

		// 생성된 JSONObeject 를 리턴
		return json;
	}

	// 2.3
	// /v1/image/file
	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "영상검색 - 파일(유사이미지 검색)")
	@PostMapping(value = "/v1/image/file", produces = "application/json; charset=utf8")
	@ResponseBody
	public JSONObject imageProductList(
			@ApiParam(name = "ii", value = "검색조건", required = true)
			@RequestBody ImageInfo ii ) throws JsonParseException, JsonMappingException, IOException, NoContentException
	{
		// 최종 리턴할 json 형식을 저장할 맵
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// json 에서 data 의 내용을 저장할 맵
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// resultMap 에 리턴할 내용 저장
		// 현재 result 는 목업이므로 그냥 이 코드 그대로 사용해도 될 것 같습니다.
		Result result = new Result();
		result.setCode("200");
		String success = "성공"; // <-- 추후 성공 실패 모듈 적용.

		result.setMessage(success);

		JSONObject jsonForImage = null;

		try {
			ImgSearchServer imgServer = new ImgSearchServer();
			jsonForImage = imgServer.imageSearchByData(
					ii.getEncodingImage(), ii.getImageNm(), ii.getX(), ii.getY(),
					ii.getWidth(), ii.getHeight(), ii.getRetSize(), ii.getOwnYn());

			if (jsonForImage == null) {
				Map<String, Object> mainjsonMap = new HashMap<String, Object>();
				Map<String, Object> subjsonMap = new HashMap<String, Object>();
				subjsonMap.put("code", "401");
				subjsonMap.put("message", "imgServer data null");
				mainjsonMap.put("result", subjsonMap);
				return new JSONObject(mainjsonMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONArray ja = ((JSONArray)jsonForImage.get("search"));

		ObjectMapper objectMapper = new ObjectMapper();
		Search[] s = objectMapper.readValue(ja.toJSONString(), Search[].class);

		//s = getTempImageList(s); // 테스트를 위해

		// 리스트로 반환
		List<Product> products = new ArrayList<Product>();

		for (int z = 0; z < s.length; z++) {

			if(!s[z].getOurs().equals("True")) {
				// 경쟁사인 경우 이미지 이름으로 불러와서 사용
				Product p = mapper.selectProductInfoByImg(s[z].getImage_name());

				// 시연용
				if (p == null) {
					// DATA 가 없을 경우 목업을 채우고
					initProductsDatabyIdx();
					p = getProductsDatabyIdx(z); // 목업 데이터로 채우기
					p.setImageUrl(s[z].getS3url()); // 임시
					p.setOwnProductYn("N");
					p.setFileNm(s[z].getImage_name());

					// 저장하고
					mapper.insertProductDetailInfo(p);

					// 다시 불러오기
					p = mapper.selectProductInfoByImg(s[z].getImage_name());
				}

				p.setImageUrl(s[z].getS3url()); // 임시
				p.setSimillarScore(Float.parseFloat(s[z].getScore()));
				products.add(p);

			} else {
				// 자사인 경우 정보가 없으므로 이미지만 담아서 반환
				Product p = new Product();

				if (p != null) {
					p.setImageUrl(s[z].getS3url());
					p.setSimillarScore(Float.parseFloat(s[z].getScore()));
					p.setOwnProductYn("Y");
					p.setProductCd(s[z].getProduct_num().replaceAll(".jpg", "").replaceAll(".jpeg", ""));
					p.setFileNm(s[z].getImage_name());

					p = setTestData(p); // 시연용
					products.add(p);
				}
			}
		}

		// 목록에 추가된 제품 정보가 없으면 컨텐츠 없음 예외 발생
		if (products.size() == 0) {
			throw new NoContentException();
		}

		// data 맵에 products 맵을 저장
		dataMap.put("products", products);

		// 최종적으로 리턴할 jsonObject 를 저장할 맵에 data, result(객체)을 저장
		jsonMap.put("data", dataMap);
		jsonMap.put("result", result);

		// 위에서 data, result 가 추가된 jsonMap 으로 리턴에 사용할  JSONObject 생성
		JSONObject json = new JSONObject(jsonMap);

		return json;
	}

	// 2.4
	// /v1/image/file/detail
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "영상검색 - 파일 상세(유사이미지 검색)")
	@PostMapping(value = "/v1/image/file/detail", produces = "application/json; charset=utf8")
	@ResponseBody
	public JSONObject imageDetailProductList(
			@ApiParam(name = "ii", value = "검색조건", required = true)
			@RequestBody ImageInfo ii) throws JsonParseException, JsonMappingException, IOException, NoContentException
	{
		// 최종 리턴할 json 형식을 저장할 맵
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// json 에서 data 의 내용을 저장할 맵
		//Map<String, Object> dataMap = new HashMap<String, Object>();

		// resultMap 에 리턴할 내용 저장
		// 현재 result 는 목업이므로 그냥 이 코드 그대로 사용해도 될 것 같습니다.
		Result result = new Result();
		result.setCode("200");
		String success = "성공"; // <-- 추후 성공 실패 모듈 적용.

		result.setMessage(success);

		JSONObject jsonForImage = null;

		try {
			ImgSearchServer imgServer = new ImgSearchServer();
			jsonForImage = imgServer.imageSearchByData(
					ii.getEncodingImage(), ii.getImageNm(), ii.getX(), ii.getY(),
					ii.getWidth(), ii.getHeight(), ii.getRetSize(), ii.getOwnYn());

			if (jsonForImage == null) {
				Map<String, Object> mainjsonMap = new HashMap<String, Object>();
				Map<String, Object> subjsonMap = new HashMap<String, Object>();
				subjsonMap.put("code", "401");
				subjsonMap.put("message", "imgServer data null");
				mainjsonMap.put("result", subjsonMap);
				return new JSONObject(mainjsonMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}



		JSONArray ja = ((JSONArray)jsonForImage.get("search"));

		ObjectMapper objectMapper = new ObjectMapper();
		Search[] s = objectMapper.readValue(ja.toJSONString(), Search[].class);

		//s = getTempImageList(s); // 테스트를 위해

		// 리스트로 반환
		List<Product> products = new ArrayList<Product>();

		for (int z = 0; z < s.length; z++) {

			if(!s[z].getOurs().equals("True")) {
				// 경쟁사인 경우
				Product p = mapper.selectProductInfoByImg(s[z].getImage_name());

				// DB 에 DATA가 없을경우 새로 저장
				if (p == null) {
					// 목업을 채운다.
					initProductsDatabyIdx();
					p = getProductsDatabyIdx(z); // 목업 데이터로 채우기
					p.setImageUrl(s[z].getS3url()); // 임시
					p.setOwnProductYn("N");
					p.setFileNm(s[z].getImage_name());

					// 저장한다.
					mapper.insertProductDetailInfo(p);

					// 다시 불러온다.
					p = mapper.selectProductInfoByImg(s[z].getImage_name());
				}

				p.setImageUrl(s[z].getS3url()); // 임시
				p.setSimillarScore(Float.parseFloat(s[z].getScore()));
				products.add(p);


			} else {
				// 자사인 경우 정보가 없으므로 이미지만 담아서 반환
				Product p = new Product();

				if (p != null) {
					p.setImageUrl(s[z].getS3url());
					p.setSimillarScore(Float.parseFloat(s[z].getScore()));
					p.setOwnProductYn("Y");
					p.setProductCd(s[z].getProduct_num().replaceAll(".jpg", "").replaceAll(".jpeg", ""));
					p.setFileNm(s[z].getImage_name());

					p = setTestData(p); // 시연용
					products.add(p);
				}
			}
		}

		// 목록에 추가된 제품 정보가 없으면 컨텐츠 없음 예외 발생
		if (products.size() == 0) {
			throw new NoContentException();
		}

		// data 맵에 products 맵을 저장
		//dataMap.put("products", products);
		jsonForImage.put("products", products);
		jsonForImage.remove("search");

		// 최종적으로 리턴할 jsonObject 를 저장할 맵에 data, result(객체)을 저장
		jsonMap.put("data", jsonForImage);
		jsonMap.put("result", result);

		// 위에서 data, result 가 추가된 jsonMap 으로 리턴에 사용할  JSONObject 생성
		JSONObject json = new JSONObject(jsonMap);

		// 생성된 JSONObeject 를 json 형태의 문자열로 변경한 뒤 리턴
		return json;
	}

	// 2.5
	// /v1/image/url/
	@ApiOperation(value = "영상검색 - URL(유사이미지 검색)")
	@PostMapping(value = "/v1/image/url", produces = "application/json; charset=utf8")
	@ResponseBody
	public JSONObject similarImageSearch(
			@ApiParam(name = "iiu", value = "검색조건", required = true)
			@RequestBody ImageInfoUrl iiu) throws JsonParseException, JsonMappingException, IOException, NoContentException
	{
		// 최종 리턴할 json 형식을 저장할 맵
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// json 에서 data 의 내용을 저장할 맵
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// resultMap 에 리턴할 내용 저장
		// 현재 result 는 목업이므로 그냥 이 코드 그대로 사용해도 될 것 같습니다.
		Result result = new Result();
		String success = "성공"; // <-- 추후 성공 실패 모듈 적용.

		JSONObject jsonForImage = null;

		try {
			ImgSearchServer imgServer = new ImgSearchServer();
			jsonForImage = imgServer.imageSearchByUrl(
					iiu.getUrl(), iiu.getImageNm(), iiu.getX(), iiu.getY(),
					iiu.getWidth(), iiu.getHeight(), iiu.getRetSize(), iiu.getOwnYn());

			if (jsonForImage == null) {
				Map<String, Object> mainjsonMap = new HashMap<String, Object>();
				Map<String, Object> subjsonMap = new HashMap<String, Object>();
				subjsonMap.put("code", "401");
				subjsonMap.put("message", "imgServer data null");
				mainjsonMap.put("result", subjsonMap);
				return new JSONObject(mainjsonMap);
			}

		} catch (Exception e) {
			success = e.getMessage();
		}

		JSONArray ja = ((JSONArray)jsonForImage.get("search"));

		ObjectMapper objectMapper = new ObjectMapper();
		Search[] s = objectMapper.readValue(ja.toJSONString(), Search[].class);

		try {
			//s = getTempImageList(s); // 테스트를 위해
		} catch (Exception e) {
			success = e.getMessage();
		}

		// 리스트로 반환
		List<Product> products = new ArrayList<Product>();

		try {
			for (int z = 0; z < s.length; z++) {

				if(!s[z].getOurs().equals("True")) {
					// 경쟁사인경우
					Product p = mapper.selectProductInfoByImg(s[z].getImage_name());

					// DATA가 없으면 입력해 주시
					if (p == null) {
						// 목업 데이터 채우기
						initProductsDatabyIdx();
						p = getProductsDatabyIdx(z); // 목업 데이터로 채우기
						p.setImageUrl(s[z].getS3url()); // 임시
						p.setOwnProductYn("N");
						p.setFileNm(s[z].getImage_name());

						// DB 에 저장하기
						mapper.insertProductDetailInfo(p);

						// 다시 불러오기
						p = mapper.selectProductInfoByImg(s[z].getImage_name());
					}

					p.setImageUrl(s[z].getS3url()); // 임시
					p.setSimillarScore(Float.parseFloat(s[z].getScore()));
					products.add(p);
				} else {
					// 자사인 경우 정보가 없으므로 이미지만 담아서 반환
					Product p = new Product();

					if (p != null) {
						p.setImageUrl(s[z].getS3url());
						p.setSimillarScore(Float.parseFloat(s[z].getScore()));
						p.setOwnProductYn("Y");
						p.setProductCd(s[z].getProduct_num().replaceAll(".jpg", "").replaceAll(".jpeg", ""));
						p.setFileNm(s[z].getImage_name());

						p = setTestData(p); // 시연용
						products.add(p);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			success = e.getMessage();
		}

		// 목록에 추가된 제품 정보가 없으면 컨텐츠 없음 예외 발생
		if (products.size() == 0) {
			System.out.println("NoContent 전");
			throw new NoContentException();
		}

		// data 맵에 products 맵을 저장
		dataMap.put("products", products);

		// 최종적으로 리턴할 jsonObject 를 저장할 맵에 data, result(객체)을 저장
		jsonMap.put("data", dataMap);

		result.setCode("200");
		result.setMessage(success);

		jsonMap.put("result", result);

		// 위에서 data, result 가 추가된 jsonMap 으로 리턴에 사용할  JSONObject 생성
		JSONObject json = new JSONObject(jsonMap);

		// 생성된 JSONObeject 를 json 형태의 문자열로 변경한 뒤 리턴
		return json;
	}


	// 2.6
	// /v1/image/url/weekly
	@ApiOperation(value = "영상검색 - URL(유사이미지 검색:주간통계포함)")
	@PostMapping(value = "/v1/image/url/weekly", produces = "application/json; charset=utf8")
	@ResponseBody
	public JSONObject similarImageSearchWeekly(
			@ApiParam(name = "iiud", value = "검색조건", required = true)
			@RequestBody ImageInfoUrlDetail iiud) throws JsonParseException, JsonMappingException, IOException, NoContentException
	{
		// 최종 리턴할 json 형식을 저장할 맵
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// json 에서 data 의 내용을 저장할 맵
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// resultMap 에 리턴할 내용 저장
		// 현재 result 는 목업이므로 그냥 이 코드 그대로 사용해도 될 것 같습니다.
		Result result = new Result();
		result.setCode("200");
		String success = "성공"; // <-- 추후 성공 실패 모듈 적용.

		result.setMessage(success);

		JSONObject jsonForImage = null;

		try {
			ImgSearchServer imgServer = new ImgSearchServer();
			jsonForImage = imgServer.imageSearchByUrl(
					iiud.getUrl(), iiud.getImageNm(), iiud.getX(), iiud.getY(),
					iiud.getWidth(), iiud.getHeight(), iiud.getRetSize(), iiud.getOwnYn());

			if (jsonForImage == null) {
				Map<String, Object> mainjsonMap = new HashMap<String, Object>();
				Map<String, Object> subjsonMap = new HashMap<String, Object>();
				subjsonMap.put("code", "401");
				subjsonMap.put("message", "imgServer data null");
				mainjsonMap.put("result", subjsonMap);
				return new JSONObject(mainjsonMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONArray ja = ((JSONArray)jsonForImage.get("search"));

		ObjectMapper objectMapper = new ObjectMapper();
		Search[] s = objectMapper.readValue(ja.toJSONString(), Search[].class);

		//s = getTempImageList(s); // 테스트를 위해

		// 리스트로 반환
		List<ProductWeekly> products = new ArrayList<ProductWeekly>();

		for (int z = 0; z < s.length; z++) {
			Product p;

			if(!s[z].getOurs().equals("True")) {
				// 경쟁사일 경우
				p = mapper.selectProductInfoByImg(s[z].getImage_name());

				// DATA가 없으면 새로 입력해 준다
				if (p == null) {
					// 목업 채우기
					initProductsDatabyIdx();
					p = getProductsDatabyIdx(z); // 목업 데이터로 채우기
					p.setImageUrl(s[z].getS3url()); // 임시
					p.setOwnProductYn("N");
					p.setFileNm(s[z].getImage_name());

					// DB 에 저장하기
					mapper.insertProductDetailInfo(p);

					// 새로 가져오기
					p = mapper.selectProductInfoByImg(s[z].getImage_name());
				}

				p.setImageUrl(s[z].getS3url()); // 임시
				p.setSimillarScore(Float.parseFloat(s[z].getScore()));
			} else {
				// 자사인 경우 정보가 없으므로 이미지만 담아서 반환
				p = new Product();

				if (p != null) {
					p.setImageUrl(s[z].getS3url());
					p.setSimillarScore(Float.parseFloat(s[z].getScore()));
					p.setOwnProductYn("Y");
					p.setProductCd(s[z].getProduct_num().replaceAll(".jpg", "").replaceAll(".jpeg", ""));
					p.setFileNm(s[z].getImage_name());

					p = setTestData(p); // 시연용
				}
			}

			if (p != null) {

				// weeklySales 추가
				List<WeeklySales> weeklySalesList;// = new ArrayList<WeeklySales>();
				//weeklySalesList.add(new WeeklySales("2017_24", 0));

				WeeklySalesSearch weeklySalesSearch = new WeeklySalesSearch();
				weeklySalesSearch.setProductId(String.valueOf(p.getProductId()));
				weeklySalesSearch.setStartYear(Integer.valueOf(iiud.getStartYear()));
				weeklySalesSearch.setStartWeek(Integer.valueOf(iiud.getStartWeek()));
				weeklySalesSearch.setEndYear(Integer.valueOf(iiud.getEndYear()));
				weeklySalesSearch.setEndWeek(Integer.valueOf(iiud.getEndWeek()));

				weeklySalesList = mapper.selectWeeklyInfo(weeklySalesSearch);

				if(weeklySalesList.size() == 0 && p.getOwnProductYn() == "N")
				{
					weeklySalesList = new ArrayList<WeeklySales>();

					//for(int y=getWeekofYear(p.getReleaseDt()); y <= getWeekofYear(p.getEndOfLife()); y++)
					for(int y=getWeekofYear(p.getReleaseDt()); y <= getWeekofYear(getNow()); y++)
						weeklySalesList.add(new WeeklySales("2017_" + y, y));
				}

				ProductWeekly pw = new ProductWeekly();

				pw.setProductId(p.getProductId());
				pw.setProductCd(p.getProductCd());
				pw.setAccSales(p.getAccSales());
				pw.setBrandNm(p.getBrandNm());
				pw.setReleaseDt(p.getReleaseDt());
				pw.setRetailPrice(p.getRetailPrice());
				pw.setEndOfLife(p.getEndOfLife());
				pw.setProductNm(p.getProductNm());
				pw.setItem(p.getItem());
				pw.setColor(p.getColor());
				pw.setSeason(p.getSeason());
				pw.setSize(p.getSize());
				pw.setFabric(p.getFabric());
				pw.setImageUrl(p.getImageUrl());
				pw.setCrawlUrl(p.getCrawlUrl());
				pw.setOwnProductYn(p.getOwnProductYn());
				pw.setSimillarScore(p.getSimillarScore());
				pw.setWeeklySales(weeklySalesList);

				products.add(pw);
			}
		}

		// 목록에 추가된 제품이 없으면 컨텐츠 없음 예외 발생
		if (products.size() == 0) {
			throw new NoContentException();
		}

		// data 맵에 products 맵을 저장
		dataMap.put("products", products);

		// 최종적으로 리턴할 jsonObject 를 저장할 맵에 data, result(객체)을 저장
		jsonMap.put("data", dataMap);
		jsonMap.put("result", result);

		// 위에서 data, result 가 추가된 jsonMap 으로 리턴에 사용할  JSONObject 생성
		JSONObject json = new JSONObject(jsonMap);

		// 생성된 JSONObeject 를 json 형태의 문자열로 변경한 뒤 리턴
		return json;
	}


	// 2.7
	// /v1/image/url/detail
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "영상검색 - URL(상세 : 유사이미지 검색)")
	@PostMapping(value = "/v1/image/url/detail", produces = "application/json; charset=utf8")
	@ResponseBody
	public JSONObject similarImageSearchDetail(
			@ApiParam(name = "iiu", value = "검색조건", required = true)
			@RequestBody ImageInfoUrl iiu) throws JsonParseException, JsonMappingException, IOException, NoContentException
	{
		// 최종 리턴할 json 형식을 저장할 맵
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// json 에서 data 의 내용을 저장할 맵
		//Map<String, Object> dataMap = new HashMap<String, Object>();

		// resultMap 에 리턴할 내용 저장
		// 현재 result 는 목업이므로 그냥 이 코드 그대로 사용해도 될 것 같습니다.
		Result result = new Result();
		result.setCode("200");
		String success = "성공"; // <-- 추후 성공 실패 모듈 적용.

		result.setMessage(success);

		JSONObject jsonForImage = null;

		try {
			ImgSearchServer imgServer = new ImgSearchServer();
			jsonForImage = imgServer.imageSearchByUrl(
					iiu.getUrl(), iiu.getImageNm(), iiu.getX(), iiu.getY(),
					iiu.getWidth(), iiu.getHeight(), iiu.getRetSize(), iiu.getOwnYn());

			if (jsonForImage == null) {
				Map<String, Object> mainjsonMap = new HashMap<String, Object>();
				Map<String, Object> subjsonMap = new HashMap<String, Object>();
				subjsonMap.put("code", "401");
				subjsonMap.put("message", "imgServer data null");
				mainjsonMap.put("result", subjsonMap);
				return new JSONObject(mainjsonMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONArray ja = ((JSONArray)jsonForImage.get("search"));

		ObjectMapper objectMapper = new ObjectMapper();
		Search[] s = objectMapper.readValue(ja.toJSONString(), Search[].class);

		//s = getTempImageList(s); // 테스트를 위해

		// 리스트로 반환
		List<Product> products = new ArrayList<Product>();

		for (int z = 0; z < s.length; z++) {

			if(!s[z].getOurs().equals("True")) {
				Product p = mapper.selectProductInfoByImg(s[z].getImage_name());

				// DATA가 없으면
				if (p == null) {
					// 목업 채우기
					initProductsDatabyIdx();
					p = getProductsDatabyIdx(z); // 목업 데이터로 채우기
					p.setImageUrl(s[z].getS3url()); // 임시
					p.setOwnProductYn("N");
					p.setFileNm(s[z].getImage_name());

					// DB에 저장하기
					mapper.insertProductDetailInfo(p);

					// 다시 불러오기
					p = mapper.selectProductInfoByImg(s[z].getImage_name());
				}

				p.setImageUrl(s[z].getS3url()); // 임시
				p.setSimillarScore(Float.parseFloat(s[z].getScore()));
				products.add(p);
			} else {
				// 자사인 경우 정보가 없으므로 이미지만 담아서 반환
				Product p = new Product();

				if (p != null) {
					p.setImageUrl(s[z].getS3url());
					p.setSimillarScore(Float.parseFloat(s[z].getScore()));
					p.setOwnProductYn("Y");
					p.setProductCd(s[z].getProduct_num().replaceAll(".jpg", "").replaceAll(".jpeg", ""));
					p.setFileNm(s[z].getImage_name());

					p = setTestData(p); // 시연용
					products.add(p);
				}
			}
		}

		// 목록에 추가된 제품이 없으면 컨텐츠 없음 예외 발생
		if (products.size() == 0) {
			throw new NoContentException();
		}

		// data 맵에 products 맵을 저장
		//dataMap.put("products", products);
		jsonForImage.put("products", products);
		jsonForImage.remove("search");

		// 최종적으로 리턴할 jsonObject 를 저장할 맵에 data, result(객체)을 저장
		jsonMap.put("data", jsonForImage);
		jsonMap.put("result", result);

		// 위에서 data, result 가 추가된 jsonMap 으로 리턴에 사용할  JSONObject 생성
		JSONObject json = new JSONObject(jsonMap);

		// 생성된 JSONObeject 를 json 형태의 문자열로 변경한 뒤 리턴
		return json;
	}


	// =======================================================================================================================
	// 시연용
	public String getNow()
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

		String formatted = format1.format(cal.getTime());

		return formatted;
	}

	@SuppressWarnings("static-access")
	public int getWeekofYear(String startDate)
	{
		int result;

		Calendar c = Calendar.getInstance();

		if(startDate.length() == 8) {

			//System.out.println(startDate.substring(0, 4));
			//System.out.println(startDate.substring(4, 6));
			//System.out.println(startDate.substring(6, 8));
			startDate = startDate.substring(0, 4) + "-" + startDate.substring(4, 6) + "-" + startDate.substring(6, 8);
		}
		c.setTime(Date.valueOf(startDate));
		result = c.get(c.WEEK_OF_YEAR);

		return result;
	}


	public Product setTestData(Product product)
	{
		Product p = mapper.selectProductInfoByProductCD(product.getProductCd());

		if(p == null)
		{
			mapper.insertProductInfo(product);
			p = mapper.selectProductInfoByProductCD(product.getProductCd());
		}

		p.setSimillarScore(product.getSimillarScore());
		p.setImageUrl(product.getImageUrl());
		return p;
	}

	// 임시로 사용하는 함수
	public Search[] getTempImageList(Search[] s)
	{
		String[] temp = new String[20];
		int z = 0;

		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/87/100087987_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/56/100096056_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/60/100121560_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/46/100094646_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/33/100068933_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/39/100096339_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/30/100089330_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/35/100112635_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/10/100103610_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/34/100098334_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/44/100095944_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/67/100094267_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/97/100125097_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/63/100119263_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/27/100119627_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/17/100121317_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/61/100111261_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/85/100107285_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/66/100122766_00.jpg";
		temp[z++] = "https://s3.ap-northeast-2.amazonaws.com/sscnt/image/sites/department_ssg_com/origin/30/100117830_00.jpg";

		z = 0;

		for(int y=0; y<s.length; y++)
		{
			s[y].setImage_name(temp[z++]);
			if(z == 20) z = 0;
		}

		return s;
	}

	// 목업용
	List<Product> pmList;
	public void initProductsDatabyIdx()
	{
		String imgUrl = "";
		String crawlUrl = "";
		String hostUrl = "http://13.124.207.164/static/images/";
		Product pm = new Product();
		pmList = new ArrayList<Product>();

		int productId = 816303;
		int productCd = mapper.selectMaxID(); // 시연을 위해 MAX 값을 가져온다

		for(int z=0; z<10; z++)
		{
			imgUrl = hostUrl + "/MIXXO/1609038732_49.jpg";
			crawlUrl = "http://mixxo.elandmall.com/goods/initGoodsDetail.action?goods_no=1609038732";
			pm = new Product(productId--,"CJ" + productCd++,8,"MIXXO","2017-03-06",79900,"2017-03-18","하프 패치 야상점퍼 / (49)KHAKI","Outer","KHAKI","ALL","100",
					"면",imgUrl,crawlUrl,"N",(float) 0.9);
			pmList.add(pm);

			imgUrl = hostUrl + "/HM/0379457_73.jpeg";
			crawlUrl = "http://www2.hm.com/ko_kr/productpage.0379457007.html";
			pm = new Product(productId--,"CJ" + productCd++,0,"HM","2017-03-10",39000,"2017-09-18","카고 셔츠 / 73 다크 블루","가디건 & 풀오버","73 다크 블루","ALL","XS",
					"코튼",imgUrl,crawlUrl,"N",(float) 0.1);
			pmList.add(pm);

			imgUrl = hostUrl + "/ZARA/6895_066_카키.jpg";
			crawlUrl = "https://www.zara.com/kr/ko/woman/%EC%95%84%EC%9A%B0%ED%84%B0/%ED%8C%8C%EC%B9%B4/%EC%9E%90%EC%88%98-%EC%98%A4%EB%B2%84%EC%85%94%EC%B8%A0-c710516p4378515.html";
			pm = new Product(productId--,"CJ" + productCd++,32,"ZARA","2017-04-27",99000,"2017-06-19","자수 오버셔츠 / 카키","블레이저","카키","ALL","S (KR 55)",
					"면",imgUrl,crawlUrl,"N",(float) 0.5);
			pmList.add(pm);

			imgUrl = hostUrl + "/MIXXO/1702144672_49.jpg";
			crawlUrl = "http://mixxo.elandmall.com/goods/initGoodsDetail.action?goods_no=1702144672";
			pm = new Product(productId--,"CJ" + productCd++,14,"MIXXO","2017-03-06",89900,"2017-08-25","플라워 자수 야상점퍼 MIWJJ7321S / (49)KHAKI","Outer","KHAKI","ALL","M",
					"면",imgUrl,crawlUrl,"N",(float) 0.1);
			pmList.add(pm);

			imgUrl = hostUrl + "/HM/0446836_09.jpeg";
			crawlUrl = "http://www2.hm.com/ko_kr/productpage.0446836008.html";
			pm = new Product(productId--,"CJ" + productCd++,0,"HM","2017-03-10",29000,"2017-07-17","크롭트 플란넬 셔츠 / 09 블랙","가디건 & 풀오버","09 블랙","ALL","32",
					"코튼",imgUrl,crawlUrl,"N",(float) 0.6);
			pmList.add(pm);

			imgUrl = hostUrl + "/HM/0485808_19.jpeg";
			crawlUrl = "http://www2.hm.com/ko_kr/productpage.0485808001.html";
			pm = new Product(productId--,"CJ" + productCd++,0,"HM","2017-03-10",59000,"2017-06-19","유틸리티 재킷 / 19 카키 그린","베이직","19 카키 그린","ALL","32",
					"코튼",imgUrl,crawlUrl,"N",(float) 0.4);
			pmList.add(pm);

			imgUrl = hostUrl + "/MIXXO/1609022962_49.jpg";
			crawlUrl = "http://mixxo.elandmall.com/goods/initGoodsDetail.action?goods_no=1609022962";
			pm = new Product(productId--,"CJ" + productCd++,-4,"MIXXO","2017-03-06",79900,"2017-03-18","레터링패치 카키셔츠 / (49)KHAKI","Blouse/Shirts","KHAKI","ALL","100",
					"면",imgUrl,crawlUrl,"N",(float) 0.1);
			pmList.add(pm);

			imgUrl = hostUrl + "/HM/0510959_99.jpeg";
			crawlUrl = "http://www2.hm.com/ko_kr/productpage.0510959003.html";
			pm = new Product(productId--,"CJ" + productCd++,0,"HM","2017-03-10",59000,"2017-09-18","유틸리티 후드 재킷 / 99 다크 카키 그린","베이직","99 다크 카키 그린","ALL","32",
					"코튼",imgUrl,crawlUrl,"N",(float) 0.3);
			pmList.add(pm);

			imgUrl = hostUrl + "/ZARA/6985_401_카키.jpg";
			crawlUrl = "https://www.zara.com/kr/ko/man/%EC%9E%90%EC%BC%93/%EB%A1%B1-%EC%BD%94%ED%8A%BC-%ED%8C%8C%EC%B9%B4-c586542p4081997.html";
			pm = new Product(productId--,"CJ" + productCd++,65,"ZARA","2017-02-13",159000,"2017-03-30","롱 코튼 파카 / 카키","봄버자켓","카키","ALL","L (KR 100~105)",
					"면",imgUrl,crawlUrl,"N",(float) 0.4);
			pmList.add(pm);

			imgUrl = hostUrl + "/ZARA/6985_401_카키.jpg";
			crawlUrl = "http://www.lotte.com/goods/viewGoodsDetail.lotte?goods_no=451427774";
			pm = new Product(productId--,"CJ" + productCd++,65,"LOTTE","2017-02-13",159000,"2017-03-30","롱 코튼 파카 / 카키","봄버자켓","카키","ALL","L (KR 100~105)",
					"면",imgUrl,crawlUrl,"N",(float) 0.4);
			pmList.add(pm);

			imgUrl = hostUrl + "/MIXXO/1609038732_49.jpg";
			crawlUrl = "http://mixxo.elandmall.com/goods/initGoodsDetail.action?goods_no=1609038732";
			pm = new Product(productId--,"CJ" + productCd++,8,"MIXXO","2017-03-06",79900,"2017-03-18","하프 패치 야상점퍼 / (49)KHAKI","Outer","KHAKI","ALL","100",
					"면",imgUrl,crawlUrl,"N",(float) 0.9);
		}

		return;
	}

	// 저장해 놓은 DATA 가져오기
	public Product getProductsDatabyIdx(int idx)
	{
		Product pm = new Product();

		pm = pmList.get(idx);

		return pm;
	}

	// 목업용 찾기 리스트
	public List<Product> getProductsDataforDemo(ProductIds productId)
	{
		Product pm = new Product();
		List<Product> products = new ArrayList<Product>();

		for(int y=0; y<productId.getProductId().length; y++)
		{
			for(int z=0; z<pmList.size(); z++)
			{
				pm = pmList.get(z);
				if(pm.getProductId() == Long.parseLong(productId.getProductId()[y]))
				{
					products.add(pm);
				}
			}
		}

		return products;
	}

	// 목업용 찾기 단일
	public Product getProductsSingleDataforDemo(String productId)
	{
		Product pm = new Product();

		for(int z=0; z<pmList.size(); z++)
		{
			pm = pmList.get(z);
			if(pm.getProductId() == Long.parseLong(productId))
			{
				break;
			}
		}

		return pm;
	}

	// 목업용
	public List<Product> getProductsData(ProductIds productId)
	{
		String imgUrl = "";
		String crawlUrl = "";
		String hostUrl = "http://13.124.207.164/static/images/";
		List<Product> products = new ArrayList<Product>();
		Product pm;

		for(int z=0; z < productId.getProductId().length; z++)
		{

			if(productId.getProductId()[z].equals("52540"))
			{
				imgUrl = hostUrl + "/MIXXO/1609038732_49.jpg";
				crawlUrl = "http://mixxo.elandmall.com/goods/initGoodsDetail.action?goods_no=1609038732";
				pm = new Product(52540,"1609038732",8,"MIXXO","20170306",79900,"20170318","하프 패치 야상점퍼 / (49)KHAKI","Outer","KHAKI","ALL","100",
						"면",imgUrl,crawlUrl,"N",(float) 0.9);

				products.add(pm);
			}

			if(productId.getProductId()[z].equals("66918"))
			{
				imgUrl = hostUrl + "/HM/0379457_73.jpeg";
				crawlUrl = "http://www2.hm.com/ko_kr/productpage.0379457007.html";
				pm = new Product(66918,"0379457",0,"HM","20170310",39000,"20170918","카고 셔츠 / 73 다크 블루","가디건 & 풀오버","73 다크 블루","ALL","XS",
						"코튼",imgUrl,crawlUrl,"N",(float) 0.1);

				products.add(pm);
			}

			if(productId.getProductId()[z].equals("73350"))
			{
				imgUrl = hostUrl + "/ZARA/6895_066_카키.jpg";
				crawlUrl = "https://www.zara.com/kr/ko/woman/%EC%95%84%EC%9A%B0%ED%84%B0/%ED%8C%8C%EC%B9%B4/%EC%9E%90%EC%88%98-%EC%98%A4%EB%B2%84%EC%85%94%EC%B8%A0-c710516p4378515.html";
				pm = new Product(73350,"6895/066",32,"ZARA","20170427",99000,"20170619","자수 오버셔츠 / 카키","블레이저","카키","ALL","S (KR 55)",
						"면",imgUrl,crawlUrl,"N",(float) 0.5);

				products.add(pm);
			}

			if(productId.getProductId()[z].equals("52505"))
			{
				imgUrl = hostUrl + "/MIXXO/1702144672_49.jpg";
				crawlUrl = "http://mixxo.elandmall.com/goods/initGoodsDetail.action?goods_no=1702144672";
				pm = new Product(52505,"1702144672",14,"MIXXO","20170306",89900,"20170825","플라워 자수 야상점퍼 MIWJJ7321S / (49)KHAKI","Outer","KHAKI","ALL","M",
						"면",imgUrl,crawlUrl,"N",(float) 0.1);

				products.add(pm);
			}

			if(productId.getProductId()[z].equals("65083"))
			{
				imgUrl = hostUrl + "/HM/0446836_09.jpeg";
				crawlUrl = "http://www2.hm.com/ko_kr/productpage.0446836008.html";
				pm = new Product(65083,"0446836",0,"HM","20170310",29000,"20170717","크롭트 플란넬 셔츠 / 09 블랙","가디건 & 풀오버","09 블랙","ALL","32",
						"코튼",imgUrl,crawlUrl,"N",(float) 0.6);

				products.add(pm);
			}

			if(productId.getProductId()[z].equals("64759"))
			{
				imgUrl = hostUrl + "/HM/0485808_19.jpeg";
				crawlUrl = "http://www2.hm.com/ko_kr/productpage.0485808001.html";
				pm = new Product(64759,"0485808",0,"HM","20170310",59000,"20170619","유틸리티 재킷 / 19 카키 그린","베이직","19 카키 그린","ALL","32",
						"코튼",imgUrl,crawlUrl,"N",(float) 0.4);

				products.add(pm);
			}

			if(productId.getProductId()[z].equals("53356"))
			{
				imgUrl = hostUrl + "/MIXXO/1609022962_49.jpg";
				crawlUrl = "http://mixxo.elandmall.com/goods/initGoodsDetail.action?goods_no=1609022962";
				pm = new Product(53356,"1609022962",-4,"MIXXO","20170306",79900,"20170318","레터링패치 카키셔츠 / (49)KHAKI","Blouse/Shirts","KHAKI","ALL","100",
						"면",imgUrl,crawlUrl,"N",(float) 0.1);

				products.add(pm);
			}

			if(productId.getProductId()[z].equals("64768"))
			{
				imgUrl = hostUrl + "/HM/0510959_99.jpeg";
				crawlUrl = "http://www2.hm.com/ko_kr/productpage.0510959003.html";
				pm = new Product(64768,"0510959",0,"HM","20170310",59000,"20170918","유틸리티 후드 재킷 / 99 다크 카키 그린","베이직","99 다크 카키 그린","ALL","32",
						"코튼",imgUrl,crawlUrl,"N",(float) 0.3);

				products.add(pm);
			}

			if(productId.getProductId()[z].equals("28925"))
			{
				imgUrl = hostUrl + "/ZARA/6985_401_카키.jpg";
				crawlUrl = "https://www.zara.com/kr/ko/man/%EC%9E%90%EC%BC%93/%EB%A1%B1-%EC%BD%94%ED%8A%BC-%ED%8C%8C%EC%B9%B4-c586542p4081997.html";
				pm = new Product(28925,"6985/401",65,"ZARA","20170213",159000,"20170330","롱 코튼 파카 / 카키","봄버자켓","카키","ALL","L (KR 100~105)",
						"면",imgUrl,crawlUrl,"N",(float) 0.4);

				products.add(pm);
			}
			if(productId.getProductId()[z].equals("1144428"))
			{
				imgUrl = hostUrl + "/ZARA/6985_401_카키.jpg";
				crawlUrl = "http://www.lotte.com/goods/viewGoodsDetail.lotte?goods_no=451427774";
				pm = new Product(1144428,"451427774",65,"LOTTE","20170213",159000,"20170330","롱 코튼 파카 / 카키","봄버자켓","카키","ALL","L (KR 100~105)",
						"면",imgUrl,crawlUrl,"N",(float) 0.4);

				products.add(pm);
			}

		}

		return products;
	}

	// 목업용
	public List<Product> getProductsData()
	{
		String imgUrl = "";
		String crawlUrl = "";
		String hostUrl = "http://13.124.207.164/static/images/";
		List<Product> products = new ArrayList<Product>();
		Product pm;

		imgUrl = hostUrl + "/MIXXO/1609038732_49.jpg";
		crawlUrl = "http://mixxo.elandmall.com/goods/initGoodsDetail.action?goods_no=1609038732";
		pm = new Product(52540,"1609038732",8,"MIXXO","20170306",79900,"20170318","하프 패치 야상점퍼 / (49)KHAKI","Outer","KHAKI","ALL","100",
				"면",imgUrl,crawlUrl,"N",(float) 0.9);

		products.add(pm);

		imgUrl = hostUrl + "/HM/0379457_73.jpeg";
		crawlUrl = "http://www2.hm.com/ko_kr/productpage.0379457007.html";
		pm = new Product(66918,"0379457",0,"HM","20170310",39000,"20170918","카고 셔츠 / 73 다크 블루","가디건 & 풀오버","73 다크 블루","ALL","XS",
				"코튼",imgUrl,crawlUrl,"N",(float) 0.1);

		products.add(pm);

		imgUrl = hostUrl + "/ZARA/6895_066_카키.jpg";
		crawlUrl = "https://www.zara.com/kr/ko/woman/%EC%95%84%EC%9A%B0%ED%84%B0/%ED%8C%8C%EC%B9%B4/%EC%9E%90%EC%88%98-%EC%98%A4%EB%B2%84%EC%85%94%EC%B8%A0-c710516p4378515.html";
		pm = new Product(73350,"6895/066",32,"ZARA","20170427",99000,"20170619","자수 오버셔츠 / 카키","블레이저","카키","ALL","S (KR 55)",
				"면",imgUrl,crawlUrl,"N",(float) 0.5);

		products.add(pm);

		imgUrl = hostUrl + "/MIXXO/1702144672_49.jpg";
		crawlUrl = "http://mixxo.elandmall.com/goods/initGoodsDetail.action?goods_no=1702144672";
		pm = new Product(52505,"1702144672",14,"MIXXO","20170306",89900,"20170825","플라워 자수 야상점퍼 MIWJJ7321S / (49)KHAKI","Outer","KHAKI","ALL","M",
				"면",imgUrl,crawlUrl,"N",(float) 0.1);

		products.add(pm);

		imgUrl = hostUrl + "/HM/0446836_09.jpeg";
		crawlUrl = "http://www2.hm.com/ko_kr/productpage.0446836008.html";
		pm = new Product(65083,"0446836",0,"HM","20170310",29000,"20170717","크롭트 플란넬 셔츠 / 09 블랙","가디건 & 풀오버","09 블랙","ALL","32",
				"코튼",imgUrl,crawlUrl,"N",(float) 0.6);

		products.add(pm);

		imgUrl = hostUrl + "/HM/0485808_19.jpeg";
		crawlUrl = "http://www2.hm.com/ko_kr/productpage.0485808001.html";
		pm = new Product(64759,"0485808",0,"HM","20170310",59000,"20170619","유틸리티 재킷 / 19 카키 그린","베이직","19 카키 그린","ALL","32",
				"코튼",imgUrl,crawlUrl,"N",(float) 0.4);

		products.add(pm);

		imgUrl = hostUrl + "/MIXXO/1609022962_49.jpg";
		crawlUrl = "http://mixxo.elandmall.com/goods/initGoodsDetail.action?goods_no=1609022962";
		pm = new Product(53356,"1609022962",-4,"MIXXO","20170306",79900,"20170318","레터링패치 카키셔츠 / (49)KHAKI","Blouse/Shirts","KHAKI","ALL","100",
				"면",imgUrl,crawlUrl,"N",(float) 0.1);

		products.add(pm);

		imgUrl = hostUrl + "/HM/0510959_99.jpeg";
		crawlUrl = "http://www2.hm.com/ko_kr/productpage.0510959003.html";
		pm = new Product(64768,"0510959",0,"HM","20170310",59000,"20170918","유틸리티 후드 재킷 / 99 다크 카키 그린","베이직","99 다크 카키 그린","ALL","32",
				"코튼",imgUrl,crawlUrl,"N",(float) 0.3);

		products.add(pm);

		imgUrl = hostUrl + "/ZARA/6985_401_카키.jpg";
		crawlUrl = "https://www.zara.com/kr/ko/man/%EC%9E%90%EC%BC%93/%EB%A1%B1-%EC%BD%94%ED%8A%BC-%ED%8C%8C%EC%B9%B4-c586542p4081997.html";
		pm = new Product(28925,"6985/401",65,"ZARA","20170213",159000,"20170330","롱 코튼 파카 / 카키","봄버자켓","카키","ALL","L (KR 100~105)",
				"면",imgUrl,crawlUrl,"N",(float) 0.4);

		products.add(pm);

		return products;
	}
	// =======================================================================================================================


	// -------------------Retrieve All Users---------------------------------------------
	@ApiOperation(value = "이미지 검색(URL)")
	// @ApiImplicitParams({
	// 	@ApiImplicitParam(name = "vo", value = "검색조건 VO", required = true, dataType = "JSON", defaultValue = "")
	// })
	@PostMapping("/image/searchByUrl")
	public JSONObject imageSearchByUrl(
			@ApiParam(name = "vo", value = "검색조건", required = true)
			@RequestBody ImageSearch vo) {
		JSONObject json=null;
		try {
			ImgSearchServer imgServer=new ImgSearchServer();
			//json=imgServer.searchurl("http://newmedia.thehandsome.com/SY/1H/FW/SY1H8TTO519W_BK_P01.jpg",
			//		"SY1H8TTO519W_BK_P01.jpg", "0", "0", "684", "1032", "50");

			json=imgServer.imageSearchByUrl(vo.getImageUrl(),vo.getImageName(), vo.getX(), vo.getY(), vo.getWidth(), vo.getHeight(), vo.getRetsize(), vo.getOwnYn());


		}catch(Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	@ApiOperation(value = "이미지 검색(파일업로드)")
	@PostMapping("/image/searchByData")
	public JSONObject imageSearchByData(
			@ApiParam(name = "vo", value = "검색조건", required = true)
			@RequestBody ImageSearch vo) {
		JSONObject json=null;
		try {

			ImgSearchServer imgServer=new ImgSearchServer();


			json=imgServer.imageSearchByData(vo.getImage(),vo.getImageName(), vo.getX(), vo.getY(), vo.getWidth(), vo.getHeight(), vo.getRetsize(), vo.getOwnYn());

		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@ApiOperation(value = "이미지를 벡터로변환")
	@PostMapping("/image/imageToVector")
	public JSONObject imageToVector(
			@ApiParam(name = "vo", value = "검색조건", required = true)
			@RequestBody ImageSearch vo) {
		JSONObject json=null;
		try {
			ImgSearchServer imgServer=new ImgSearchServer();
			json=imgServer.imageToVector(vo.getImageUrl(),vo.getImageName(), vo.getX(), vo.getY(), vo.getWidth(), vo.getHeight(), vo.getRetsize());

		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@ApiOperation(value = "이미지 영역 인식(파일업로드)")
	@PostMapping("/image/detroi")
	public JSONObject detroi(
			@ApiParam(name = "vo", value = "검색조건", required = true)
			@RequestBody SimpleImage vo) {
		JSONObject json=null;
		try {

			ImgSearchServer imgServer=new ImgSearchServer();

			json=imgServer.detroi(vo.getImage(),vo.getImageName());

		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@Autowired
	VectorService vectorService;


	@ApiOperation(value = "벡터 변환 대상 이미지 검색")
	@GetMapping("/skuImg/vector")
	public Map vectorYnSearch(VectorConvertitsRequestDto vectorConvertitsRequestDto) {
		return vectorService.selectVectorNoList(vectorConvertitsRequestDto);
	}

	@ApiOperation(value = "벡터 저장")
	@PostMapping("/vector/upsert")
	public Map vectorUpsert(@RequestBody VectorUpsertRequestDto vectorUpsertRequestDto) {
		return vectorService.upsertVector(vectorUpsertRequestDto);
	}

	@Autowired
	SkuService skuService;

	@ApiOperation(value = "SKU")
	@PostMapping("/skuBase")
	public Map skuBaseInsert(@RequestBody SkuRequestDto skuRequestDto) {
		return skuService.insertSkuBase(skuRequestDto);
	}

	@Autowired
	DailyInfoService dailyInfoService;

	@ApiOperation(value = "일일정보, 일일인기검색어, 일일상품반응률, 상품평가 ")
	@PostMapping("/dailySearchRate")
	public Map dailyInsert(@RequestBody DailyRequestDto dailyRequestDto) {
		return dailyInfoService.insertDailyInfo(dailyRequestDto);
	}





}