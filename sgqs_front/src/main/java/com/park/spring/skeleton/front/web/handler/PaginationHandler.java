package com.park.spring.skeleton.front.web.handler;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

/**
 * 一覧ページのページング処理を行う
 * @author park
 *
 * @param <T>
 */
public class PaginationHandler<T> {
	private final static Logger logger = LoggerFactory.getLogger(PaginationHandler.class);
	
	public static final int MAX_PAGE_ITEM_DISPLAY = 5;
	private Page<T> page;
	private List<PageItem> items;
	private int currentNumber;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PaginationHandler(Page<T> page, String url) {
		this.page = page;
		this.url = url;
		items = new ArrayList<PageItem>();
		
		currentNumber = page.getNumber(); //start from 0 to match page.page
		
		int start, size;
		if (page.getTotalPages() <= MAX_PAGE_ITEM_DISPLAY) {
			start = 0;
			size = page.getTotalPages();
		} else {
			if (currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY / 2) {
				start = 0;
				size = MAX_PAGE_ITEM_DISPLAY;
			} else if (currentNumber >= page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY / 2) {
				start = page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY;
				size = MAX_PAGE_ITEM_DISPLAY;
			} else {
				start = currentNumber - MAX_PAGE_ITEM_DISPLAY / 2;
				size = MAX_PAGE_ITEM_DISPLAY;
			}
		}

		for (int i = 0; i < size; i++) {
			items.add(new PageItem(start + i, (start + i) == currentNumber));
		}
		
		logger.debug("num:" + page.getNumber() + ", cNumb:" + currentNumber + ", size:" + size + ", start:" + start + "totPages:" + page.getTotalPages());
	}

	public List<PageItem> getItems() {
		return items;
	}

	public int getNumber() {
		return currentNumber;
	}

	public List<T> getContent() {
		return page.getContent();
	}

	public int getSize() {
		return page.getSize();
	}

	public int getTotalPages() {
		return page.getTotalPages();
	}

	public boolean isFirstPage() {
		return page.isFirst();
	}

	public boolean isLastPage() {
		return page.isLast();
	}

	public boolean isHasPreviousPage() {
		return page.hasPrevious();
	}

	public boolean isHasNextPage() {
		return page.hasNext();
	}

	public class PageItem {
		private int number;
		private boolean current;

		public PageItem(int number, boolean current) {
			this.number = number;
			this.current = current;
		}

		public int getNumber() {
			return this.number;
		}

		public boolean isCurrent() {
			return this.current;
		}
	}
}
