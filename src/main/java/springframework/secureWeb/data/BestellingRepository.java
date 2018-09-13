package springframework.secureWeb.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import springframework.secureWeb.domein.Bestelling;

public interface BestellingRepository extends CrudRepository<Bestelling, Long> {

	@Query(value = "SELECT * FROM bestelling WHERE klant_id = ?", nativeQuery = true)
	List<Bestelling> findBestellingByKlant(long klantId);

}
