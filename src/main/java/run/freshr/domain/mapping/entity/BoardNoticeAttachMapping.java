package run.freshr.domain.mapping.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.common.config.DefaultColumnConfig.ZERO;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.community.entity.BoardNotice;
import run.freshr.domain.mapping.embedded.BoardNoticeAttachMappingEmbeddedId;

@Slf4j
@Entity
@Table(name = "TB_MAP_BOARD_NOTICE_ATTACH")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "연관 관계 관리 > 공지사항 첨부 파일 관리")
public class BoardNoticeAttachMapping {

  @EmbeddedId
  private BoardNoticeAttachMappingEmbeddedId id;

  @ColumnDefault(ZERO)
  @Comment("정렬 순서")
  private Integer sort;

  @MapsId("noticeId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "notice_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_BOARD_NOTICE_ATTACH_BOARD"))
  @Comment("공지사항 일련 번호")
  private BoardNotice notice;

  @MapsId("attachId")
  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "attach_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_BOARD_NOTICE_ATTACH_ATTACH"))
  @Comment("파일 일련 번호")
  private Attach attach;

  private BoardNoticeAttachMapping(BoardNotice notice, Attach attach, Integer sort) {
    log.info("BoardNoticeAttachMapping.Constructor");

    this.id = BoardNoticeAttachMappingEmbeddedId.createId(notice.getId(), attach.getId());
    this.notice = notice;
    this.attach = attach;
    this.sort = sort;
  }

  public static BoardNoticeAttachMapping createEntity(BoardNotice notice, Attach attach,
      Integer sort) {
    log.info("BoardNoticeAttachMapping.createEntity");

    return new BoardNoticeAttachMapping(notice, attach, sort);
  }

}
