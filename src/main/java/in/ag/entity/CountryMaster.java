package in.ag.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="COUNTRY_MASTER")
public class CountryMaster {
	
	@Id
	private Integer countryId;
	private String countryName;

}
