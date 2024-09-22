package wish.wishServer.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wish.wishServer.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
}
