package tw.com.cybersoft.fsd.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import tw.com.cybersoft.fsd.persistence.entity.DemoRole;
import tw.com.cybersoft.fsd.persistence.entity.DemoUser;
import tw.com.cybersoft.fsd.persistence.entity.DemoUserRole;
import tw.com.cybersoft.fsd.persistence.repo.DemoUserRepo;

@Component
public class DemoUserDetailsService implements UserDetailsService {

	private DemoUserRepo repo;

	public DemoUserDetailsService(DemoUserRepo repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DemoUser user = repo.findByEmpNo(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
		String[] authorities = user.getCcafsUserRoles().stream().map(DemoUserRole::getCcafsRole)
				.map(DemoRole::getRoleCode).toArray(String[]::new);
		return User.builder().username(username).password("placeholder").authorities(authorities).build();
	}
	
}
