package run.freshr.domain.common.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extension.entity.EntityPhysicalExtension;
import run.freshr.domain.mapping.entity.BlogHashtagMapping;
import run.freshr.domain.mapping.entity.PostHashtagMapping;

@Slf4j
@Entity
@Table(name = "TB_COMMON_HASHTAG")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "공통 관리 > 해시태그 관리")
public class Hashtag extends EntityPhysicalExtension {

  @Id
  @Comment("일련 번호")
  private String id;

  @OneToMany(fetch = LAZY, mappedBy = "hashtag")
  private List<BlogHashtagMapping> blogList;

  @OneToMany(fetch = LAZY, mappedBy = "hashtag")
  private List<PostHashtagMapping> postList;

  private Hashtag(String id) {
    log.info("Hashtag.Constructor");

    this.id = id;
  }

  public static Hashtag createEntity(String id) {
    log.info("Hashtag.createEntity");

    return new Hashtag(id);
  }

}
