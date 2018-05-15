package com.haisenberg.f1st.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageUtils {

	// 公共方法初始化分页对象
	public static Pageable initPageable(Integer page, Integer limit, String sortOrder, String properties) {
		// 页面显示数据长度
		if (limit == null || limit <= 0) {
			limit = Constants.LIMIT;
		}
		// 当前页
		if (page == null || page <= 0) {
			page = Constants.PAGE;
		}
		Direction dir=null;
		if(Constants.SORT_ASC.equals(sortOrder)){
			dir=Sort.Direction.ASC;
		}else{
			dir=Sort.Direction.DESC;
		}
		return new PageRequest(page, limit, new Sort(dir,properties));
	}
}
