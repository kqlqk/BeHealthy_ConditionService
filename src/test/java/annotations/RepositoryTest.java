package annotations;

import me.kqlqk.behealthy.condition_service.ConditionServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

@SpringBootTest(classes = ConditionServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = "spring.profiles.active=test")
@Sql(value = {"/create_tables.sql", "/add_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public @interface RepositoryTest {
}
