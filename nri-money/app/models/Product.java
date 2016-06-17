package models;

import com.avaje.ebean.*;

import javax.persistence.*;


@Entity
public class Product extends Model {
	
	@Id
	public Long id;
	
	@ManyToOne()
	public Institution institution;
	
	public String name;
	public static Finder<Long, Product> find = new Finder<Long,Product>(Product.class);

}
