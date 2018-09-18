package springframework.secureWeb.data;

import org.springframework.data.repository.CrudRepository;

import springframework.secureWeb.domein.Account;
import springframework.secureWeb.domein.Klant;

/**
 *
 * @author FeniksBV
 */
public interface KlantRepository extends CrudRepository<Klant, Long>{
     Klant	findKlantByAccount(Account account);
}