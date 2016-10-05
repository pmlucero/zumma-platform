package ar.com.zumma.platform.repositories.search;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Pageable;

public class SearchDTO {

	private String searchTerm;
	private SearchType searchType;
	private Pageable pageable;
	
	public SearchDTO() {
	}

	public SearchDTO(String term, SearchType type, Pageable pageable) {
		this.searchTerm = term;
		this.searchType = type;
		this.pageable = pageable;
	}
	
	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
	
	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
