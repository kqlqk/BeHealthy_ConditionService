package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.UserCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConditionRepository extends JpaRepository<UserCondition, Long> {
    UserCondition findById(long id);

    UserCondition findByUserId(long id);

    boolean existsByUserId(long id);
}
