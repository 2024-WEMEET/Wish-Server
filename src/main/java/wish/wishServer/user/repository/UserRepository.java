package wish.wishServer.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wish.wishServer.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
