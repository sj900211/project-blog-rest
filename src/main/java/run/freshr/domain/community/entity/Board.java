package run.freshr.domain.community.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extension.entity.EntityLogicalExtension;
import run.freshr.domain.community.enumeration.BoardType;

@Slf4j
@Entity
@Table(
    name = "TB_COMMUNITY_BOARD",
    indexes = {
        @Index(name = "IDX_COMMUNITY_BOARD_FLAG", columnList = "useFlag, deleteFlag"),
        @Index(name = "IDX_COMMUNITY_BOARD_TITLE", columnList = "title")
    }
)
@SequenceGenerator(
    name = "SEQUENCE_GENERATOR",
    sequenceName = "SEQ_COMMUNITY_BOARD"
)
@Getter
@DynamicInsert
@DynamicUpdate
@Inheritance(strategy = JOINED)
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "커뮤니티 관리 > 게시글 관리")
public class Board extends EntityLogicalExtension {

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("유형")
  protected BoardType type;

  @Column(
      nullable = false,
      length = 50
  )
  @Comment("제목")
  protected String title;

  @Lob
  @Column(nullable = false)
  @Comment("내용")
  protected String contents;

  public void remove() {
    this.deleteFlag = false;
    this.useFlag = true;
  }

}
