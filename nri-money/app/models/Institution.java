package models;

import com.avaje.ebean.*;

import javax.persistence.*;


@Entity
public class Institution extends Model{

	@Id
	public Long id;
	public String logoUrl;
	
	@Column(unique=true)
	public String name;
	public String instType;
	public String url;
	
	
	public static Finder<Long, Institution> find = new Finder<Long,Institution>(Institution.class);

}
