package wish.wishServer.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wish.wishServer.chatting.entity.ChattingEntity;

import java.util.List;

public interface ChattingRepository extends JpaRepository<ChattingEntity, String> {
    List<ChattingEntity> findByUsername(String username);
}
