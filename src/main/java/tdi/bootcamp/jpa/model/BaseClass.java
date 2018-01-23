package tdi.bootcamp.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseClass implements  Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7080905251204670441L;
	@Column(name = "id_entry", length = 30)
	private String idEntry;
	@Column(name = "d_entry" )
	private Timestamp tglEntry;

	public String getIdEntry() {
		return idEntry;
	}

	public void setIdEntry(String idEntry) {
		this.idEntry = idEntry;
	}

	public Timestamp getTglEntry() {
		return tglEntry;
	}

	public void setTglEntry(Timestamp tglEntry) {
		this.tglEntry = tglEntry;
	}

}
