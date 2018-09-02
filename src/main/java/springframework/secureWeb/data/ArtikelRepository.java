package springframework.secureWeb.data;

import org.springframework.data.repository.CrudRepository;
import springframework.secureWeb.domein.Artikel;

/**
 *
 * @author FeniksBV
 */
public interface ArtikelRepository extends CrudRepository<Artikel, Long>{
    
}
