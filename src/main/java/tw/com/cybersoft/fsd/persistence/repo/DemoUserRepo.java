package tw.com.cybersoft.fsd.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.cybersoft.fsd.persistence.entity.DemoUser;

public interface DemoUserRepo extends JpaRepository<DemoUser, String> {

	Optional<DemoUser> findByEmpNo(String empNo);
	
}
