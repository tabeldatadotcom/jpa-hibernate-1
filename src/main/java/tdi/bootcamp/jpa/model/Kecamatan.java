package tdi.bootcamp.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kecamatan", schema = "public")
public class Kecamatan    {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(name = "id_kec", updatable = false, nullable = false)
	private int id;
	@Column(name = "nama_kec", length = 50)
	private String nama;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
   
}
