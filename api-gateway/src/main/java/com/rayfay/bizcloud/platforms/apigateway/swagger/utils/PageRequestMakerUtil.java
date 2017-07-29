package com.rayfay.bizcloud.platforms.apigateway.swagger.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenfu on 2017/4/26.
 */
public class PageRequestMakerUtil {

	/**
	 * 生成排序对象
	 * 
	 * @param sorter
	 *            排序信息
	 * @return 排序对象
	 */
	public static Sort.Order makeOrder(BizCloudSorter sorter) {
		Sort.Order order = null;

		if (StringUtils.isNotEmpty(sorter.getSortField())) {
			order = new Sort.Order(sorter.getDirection(), sorter.getSortField());
		}

		return order;
	}

	/**
	 * 生成排序对象列表
	 * 
	 * @param sorter
	 *            排序信息列表
	 * @return 排序对象列表
	 */
	public static Sort makeOrder(List<BizCloudSorter> sorter) {
		Sort sort = null;
		List<Sort.Order> orderList = null;
		if (sorter != null && sorter.size() > 0) {
			orderList = new ArrayList<>();
			for (BizCloudSorter one : sorter) {
				Sort.Order order = makeOrder(one);
				if (order != null) {
					orderList.add(order);
				}
			}
		}

		if (orderList != null && orderList.size() > 0) {
			sort = new Sort(orderList);
		}

		return sort;
	}

	/**
	 * 生成带翻页信息排序对象
	 * 
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            每页件数
	 * @param sorter
	 *            排序信息列表
	 * @return 带翻页信息排序对象
	 */
	public static PageRequest makePageRequest(int pageNumber, int pageSize, List<BizCloudSorter> sorter) {
		PageRequest pageRequest;
		Sort sort = makeOrder(sorter);

		if (sort != null) {
			pageRequest = new PageRequest(pageNumber, pageSize, sort);
		} else {
			pageRequest = new PageRequest(pageNumber, pageSize);
		}

		return pageRequest;
	}
}
