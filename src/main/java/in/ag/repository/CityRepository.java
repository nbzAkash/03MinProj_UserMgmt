package in.ag.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ag.entity.CityMaster;


public interface CityRepository extends JpaRepository<CityMaster, Serializable> {
	
	public List<CityMaster> findByStateId(Integer stateId);

}
