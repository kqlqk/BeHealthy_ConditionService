package integration.me.kqlqk.behealthy.condition_service.repository;

import annotations.RepositoryTest;
import me.kqlqk.behealthy.condition_service.model.KcalsInfo;
import me.kqlqk.behealthy.condition_service.repository.KcalsInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
public class KcalsInfoRepositoryTest {
    @Autowired
    private KcalsInfoRepository kcalsInfoRepository;

    @Test
    public void findById_shouldFindByIdOrReturnNull() {
        KcalsInfo kcalsInfo = kcalsInfoRepository.findById(1);

        assertThat(kcalsInfo).isNotNull();
        assertThat(kcalsInfo.getId()).isEqualTo(1);

        KcalsInfo nullKcalsInfo = kcalsInfoRepository.findById(99);
        assertThat(nullKcalsInfo).isNull();
    }
}
