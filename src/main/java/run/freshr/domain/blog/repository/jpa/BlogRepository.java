package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long>, BlogRepositoryCustom {

}
