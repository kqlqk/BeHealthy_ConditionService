package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.KcalsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KcalsInfoRepository extends JpaRepository<KcalsInfo, Long> {
    KcalsInfo findById(long id);
}
