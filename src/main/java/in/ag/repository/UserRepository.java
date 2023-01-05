package in.ag.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ag.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable> {
		
	public User findByEmail(String email);
	
	public User findByEmailAndUserPwd(String email, String pwd);
}
