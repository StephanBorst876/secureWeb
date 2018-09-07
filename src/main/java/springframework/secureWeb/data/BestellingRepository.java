package springframework.secureWeb.data;

import org.springframework.data.repository.CrudRepository;

import springframework.secureWeb.domein.Bestelling;

public interface BestellingRepository extends CrudRepository<Bestelling, Long> {

}
