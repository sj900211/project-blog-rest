package run.freshr.domain.community.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.common.config.DefaultColumnConfig.ZERO;
import static run.freshr.domain.community.enumeration.BoardType.NOTICE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.domain.auth.entity.Manager;
import run.freshr.domain.community.enumeration.BoardNoticeExpose;
import run.freshr.domain.mapping.entity.BoardNoticeAttachMapping;
import run.freshr.utils.XssUtil;

@Slf4j
@Entity
@Table(
    name = "TB_COMMUNITY_BOARD_NOTICE",
    indexes = {
        @Index(name = "IDX_COMMUNITY_BOARD_NOTICE_FIXED", columnList = "fixed"),
        @Index(name = "IDX_COMMUNITY_BOARD_NOTICE_EXPOSE", columnList = "expose")
    }
)
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "FK_COMMUNITY_BOARD_NOTICE"))
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "커뮤니티 관리 > 게시글 관리")
public class BoardNotice extends Board {

  @Column(nullable = false)
  @Comment("상단 고정 여부")
  private Boolean fixed;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("노출 유형")
  BoardNoticeExpose expose;

  @ColumnDefault(ZERO)
  @Comment("조회수")
  private Integer views;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "creatorId", nullable = false)
  @Comment("작성자")
  private Manager creator;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "updaterId", nullable = false)
  @Comment("마지막 수정자")
  private Manager updater;

  @OneToMany(fetch = LAZY, mappedBy = "notice")
  private List<BoardNoticeAttachMapping> attachList;

  private BoardNotice(String title, String contents, Boolean fixed, BoardNoticeExpose expose,
      Manager creator) {
    log.info("BoardNotice.Constructor");

    this.type = NOTICE;
    this.title = title;
    this.contents = XssUtil.xssBasicIgnoreImg(contents);
    this.fixed = fixed;
    this.expose = expose;
    this.creator = creator;
    this.updater = creator;
  }

  public static BoardNotice createEntity(String title, String contents, Boolean fixed,
      BoardNoticeExpose expose, Manager creator) {
    log.info("BoardNotice.createEntity");

    return new BoardNotice(title, contents, fixed, expose, creator);
  }

  public void updateEntity(String title, String contents, Boolean fixed, BoardNoticeExpose expose,
      Manager updater) {
    log.info("BoardNotice.updateEntity");

    this.title = title;
    this.contents = XssUtil.xssBasicIgnoreImg(contents);
    this.fixed = fixed;
    this.expose = expose;
    this.updater = updater;
  }

  public void addViews() {
    log.info("BoardNotice.addViews");

    this.views++;
  }

  public void removeEntity(Manager updater) {
    log.info("BoardNotice.removeEntity");

    this.updater = updater;

    remove();
  }

}
