package ar.com.zumma.platform.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import ar.com.zumma.platform.config.WebConfig;

public abstract class AbstractBaseController {

	protected final Pageable getPageable(String page) {
		return new PageRequest((page == null || page.equalsIgnoreCase("") ? 0 : (Integer.parseInt(page) - 1)), WebConfig.PAGE_SIZE, new Sort(new Order(sortDirection(), getSortField())));
	}
	
	public abstract String getSortField();
	
	public Direction sortDirection() {
		return Direction.ASC;
	}
}
