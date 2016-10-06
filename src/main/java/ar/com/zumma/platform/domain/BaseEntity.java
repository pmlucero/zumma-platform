package ar.com.zumma.platform.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseEntity implements IEntityObject, Serializable {

	private static final long serialVersionUID = 2475403734717798758L;

	protected int rcd_status;
	protected Timestamp createdTime;
	protected Timestamp lastUpdatedTime;

	public BaseEntity() {
		this.rcd_status = 1;
		this.createdTime = new Timestamp(System.currentTimeMillis());
		this.lastUpdatedTime = this.createdTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}