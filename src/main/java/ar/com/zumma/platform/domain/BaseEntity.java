package ar.com.zumma.platform.domain;

import java.io.Serializable;
import java.sql.Timestamp;

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

}