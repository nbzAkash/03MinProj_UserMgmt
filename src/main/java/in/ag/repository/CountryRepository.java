package in.ag.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ag.entity.CountryMaster;

public interface CountryRepository extends JpaRepository<CountryMaster, Serializable> {

}
