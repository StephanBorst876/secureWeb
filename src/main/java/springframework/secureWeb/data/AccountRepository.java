package springframework.secureWeb.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import springframework.secureWeb.domein.Account;

public interface AccountRepository extends CrudRepository<Account, Long>{

	 boolean existsByuserNaam(@Nullable String userNaam);
	 
	 Account findByuserNaam(@Nullable String userNaam);
	 
}
