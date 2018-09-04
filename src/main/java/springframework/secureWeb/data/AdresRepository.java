package springframework.secureWeb.data;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import springframework.secureWeb.domein.Adres;

/**
 *
 * @author FeniksBV
 */
public interface AdresRepository extends CrudRepository<Adres, Long>{
    
    @Query(value = "SELECT * FROM adres WHERE klant_id = ?", nativeQuery = true)
    List<Adres> findAdresByKlant( Long klantId );
}
