package springframework.secureWeb.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import springframework.secureWeb.domein.Bestelregel;

public interface BestelregelRepository extends CrudRepository<Bestelregel, Long> {
	
	@Query(value = "SELECT * FROM bestelregel WHERE bestelling_id = ?", nativeQuery = true)
	List <Bestelregel> findBestelregelByBestelling(long bestellingId);

}
