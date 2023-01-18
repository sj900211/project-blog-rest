package run.freshr.common.util;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static java.util.List.of;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.enumeration.ColumnType.BIGINT;
import static run.freshr.enumeration.ColumnType.BIT;
import static run.freshr.enumeration.ColumnType.BLOB;
import static run.freshr.enumeration.ColumnType.DATE;
import static run.freshr.enumeration.ColumnType.DATETIME;
import static run.freshr.enumeration.ColumnType.DECIMAL;
import static run.freshr.enumeration.ColumnType.DOUBLE;
import static run.freshr.enumeration.ColumnType.FLOAT;
import static run.freshr.enumeration.ColumnType.INT;
import static run.freshr.enumeration.ColumnType.SMALLINT;
import static run.freshr.enumeration.ColumnType.TIME;
import static run.freshr.enumeration.ColumnType.TINYINT;
import static run.freshr.enumeration.ColumnType.UNKNOWN;
import static run.freshr.enumeration.ColumnType.VARCHAR;

import com.querydsl.core.types.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.Column;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.snippet.Attributes;
import run.freshr.common.snippet.PopupFieldsSnippet;
import run.freshr.enumeration.ColumnType;

/**
 * 문서 편의 기능 정의.
 *
 * @author FreshR
 * @apiNote RestDocs 문서 편의 기능 정의
 * @since 2023. 1. 18. 오후 5:04:38
 */
@Slf4j
public class PrintUtil {

  /**
   * {@link ParameterDescriptor} 목록
   *
   * @apiNote {@link ParameterDescriptor} 목록
   * @since 2023. 1. 18. 오후 5:04:38
   */
  private final List<ParameterDescriptor> parameterList = new ArrayList<>();
  /**
   * {@link FieldDescriptor} 목록
   *
   * @apiNote {@link FieldDescriptor} 목록
   * @since 2023. 1. 18. 오후 5:04:39
   */
  private final List<FieldDescriptor> fieldList = new ArrayList<>();
  /**
   * {@link PopupFieldsSnippet} 목록
   *
   * @apiNote {@link PopupFieldsSnippet} 목록
   * @since 2023. 1. 18. 오후 5:04:39
   */
  private final List<PopupFieldsSnippet> popupList = new ArrayList<>();

  /**
   * 생성자.
   *
   * @apiNote 생성자
   * @author FreshR
   * @since 2023. 1. 18. 오후 5:04:38
   */
  public PrintUtil() {
  }

  /**
   * 생성자.
   *
   * @param builder builder
   * @apiNote {@link Builder} 생성자
   * @author FreshR
   * @since 2023. 1. 18. 오후 5:04:38
   */
  public PrintUtil(Builder builder) {
    log.info("PrintUtil.Constructor");

    this.parameterList.addAll(builder.parameterList);
    this.fieldList.addAll(builder.fieldList);
    this.popupList.addAll(builder.popupList);
  }

  /**
   * Builder.
   *
   * @return builder
   * @apiNote builder
   * @author FreshR
   * @since 2023. 1. 18. 오후 5:04:38
   */
  public static Builder builder() {
    log.info("PrintUtil.builder");

    return new Builder();
  }

  /**
   * {@link ParameterDescriptor} 목록 반환.
   *
   * @return parameter list
   * @apiNote {@link ParameterDescriptor} 목록 반환
   * @author FreshR
   * @since 2023. 1. 18. 오후 5:04:38
   */
  public List<ParameterDescriptor> getParameterList() {
    log.info("PrintUtil.getParameterList");

    return parameterList;
  }

  /**
   * {@link FieldDescriptor} 목록 반환
   *
   * @return field list
   * @apiNote {@link FieldDescriptor} 목록 반환
   * @author FreshR
   * @since 2023. 1. 18. 오후 5:04:38
   */
  public List<FieldDescriptor> getFieldList() {
    log.info("PrintUtil.getFieldList");

    return fieldList;
  }

  /**
   * {@link PopupFieldsSnippet} 목록 반환
   *
   * @return popup list
   * @apiNote {@link PopupFieldsSnippet} 목록 반환
   * @author FreshR
   * @since 2023. 1. 18. 오후 5:04:38
   */
  public List<PopupFieldsSnippet> getPopupList() {
    log.info("PrintUtil.getPopupList");

    return popupList;
  }

  // .______    __    __   __   __       _______   _______ .______
  // |   _  \  |  |  |  | |  | |  |     |       \ |   ____||   _  \
  // |  |_)  | |  |  |  | |  | |  |     |  .--.  ||  |__   |  |_)  |
  // |   _  <  |  |  |  | |  | |  |     |  |  |  ||   __|  |      /
  // |  |_)  | |  `--'  | |  | |  `----.|  '--'  ||  |____ |  |\  \----.
  // |______/   \______/  |__| |_______||_______/ |_______|| _| `._____|

  /**
   * Builder.
   *
   * @author FreshR
   * @apiNote Builder
   * @since 2023. 1. 18. 오후 5:04:38
   */
  public static class Builder {

    /**
     * {@link ParameterDescriptor} 목록
     *
     * @apiNote {@link ParameterDescriptor} 목록
     * @since 2023. 1. 18. 오후 5:04:38
     */
    private final List<ParameterDescriptor> parameterList = new ArrayList<>();
    /**
     * {@link FieldDescriptor} 목록
     *
     * @apiNote {@link FieldDescriptor} 목록
     * @since 2023. 1. 18. 오후 5:04:39
     */
    private final List<FieldDescriptor> fieldList = new ArrayList<>();
    /**
     * {@link PopupFieldsSnippet} 목록
     *
     * @apiNote {@link PopupFieldsSnippet} 목록
     * @since 2023. 1. 18. 오후 5:04:39
     */
    private final List<PopupFieldsSnippet> popupList = new ArrayList<>();
    /**
     * Prefix
     *
     * @apiNote parameter name & field path 의 prefix 설정<br>
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @since 2023. 1. 18. 오후 5:04:43
     */
    private String prefix = "";
    /**
     * Prefix description
     *
     * @apiNote description 의 prefix 설정<br>
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @since 2023. 1. 18. 오후 5:04:43
     */
    private String prefixDescription = "";
    /**
     * Prefix optional
     *
     * @apiNote 필수 항목 설정<br>
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @since 2023. 1. 18. 오후 5:04:43
     */
    private Boolean prefixOptional = false;

    /**
     * 생성자.
     *
     * @apiNote 생성자
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder() {
      log.info("PrintUtil.Builder.Constructor");
    }

    /**
     * 객체 build.
     *
     * @return print util
     * @apiNote 객체 build
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public PrintUtil build() {
      log.info("PrintUtil.Builder.build");

      return new PrintUtil(this);
    }

    /**
     * Prefix.
     *
     * @param prefix prefix
     * @return builder
     * @apiNote parameter name & field path 의 prefix 설정<br>
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder prefix(String prefix) {
      log.info("PrintUtil.Builder.prefix");

      this.prefix = prefix;

      return this;
    }

    /**
     * Prefix description.
     *
     * @param prefixDescription prefix description
     * @return builder
     * @apiNote description 의 prefix 설정<br>
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder prefixDescription(String prefixDescription) {
      log.info("PrintUtil.Builder.prefixDescription");

      this.prefixDescription = prefixDescription;

      return this;
    }

    /**
     * Prefix optional.
     *
     * @return builder
     * @apiNote 필수 항목 설정<br>
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder prefixOptional() {
      log.info("PrintUtil.Builder.prefixOptional");

      return prefixOptional(true);
    }

    /**
     * Prefix optional.
     *
     * @param optional optional
     * @return builder
     * @apiNote 필수 항목 설정<br>
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder prefixOptional(Boolean optional) {
      log.info("PrintUtil.Builder.prefixOptional");

      this.prefixOptional = optional;

      return this;
    }

    /**
     * Clear prefix.
     *
     * @return builder
     * @apiNote parameter name & field path 의 prefix 설정 제거<br>
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder clearPrefix() {
      log.info("PrintUtil.Builder.clearPrefix");

      this.prefix = "";

      return this;
    }

    /**
     * Clear prefix description.
     *
     * @return builder
     * @apiNote description 의 prefix 설정 제거<br>
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder clearPrefixDescription() {
      log.info("PrintUtil.Builder.clearPrefixDescription");

      this.prefixDescription = "";

      return this;
    }

    /**
     * Clear optional.
     *
     * @return builder
     * @apiNote 필수 항목 설정 제거<br>
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder clearOptional() {
      log.info("PrintUtil.Builder.clearOptional");

      this.prefixOptional = false;

      return this;
    }

    /**
     * Clear.
     *
     * @return builder
     * @apiNote 모든 Prefix 설정 제거
     * 설정한 이후 다시 설정하기 전까지는 모든 항목에 적용된다.
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder clear() {
      log.info("PrintUtil.Builder.clear");

      this.prefix = "";
      this.prefixDescription = "";
      this.prefixOptional = false;

      return this;
    }

    // .______      ___      .______          ___      .___  ___.  _______ .___________. _______ .______
    // |   _  \    /   \     |   _  \        /   \     |   \/   | |   ____||           ||   ____||   _  \
    // |  |_)  |  /  ^  \    |  |_)  |      /  ^  \    |  \  /  | |  |__   `---|  |----`|  |__   |  |_)  |
    // |   ___/  /  /_\  \   |      /      /  /_\  \   |  |\/|  | |   __|      |  |     |   __|  |      /
    // |  |     /  _____  \  |  |\  \----./  _____  \  |  |  |  | |  |____     |  |     |  |____ |  |\  \----.
    // | _|    /__/     \__\ | _| `._____/__/     \__\ |__|  |__| |_______|    |__|     |_______|| _| `._____|

    /**
     * Parameter.
     *
     * @param paths paths
     * @return builder
     * @apiNote {@link Path} 정보로 {@link ParameterDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder parameter(Path<?>... paths) {
      log.info("PrintUtil.Builder.parameter");

      List.of(paths).forEach(this::parameter);

      return this;
    }

    /**
     * Parameter.
     *
     * @param path path
     * @return builder
     * @apiNote {@link Path} 정보로 {@link ParameterDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder parameter(Path<?> path) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(path, false, new Attributes.Attribute[0]);
    }

    /**
     * Parameter.
     *
     * @param path     path
     * @param optional optional
     * @return builder
     * @apiNote {@link Path} 정보로 {@link ParameterDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder parameter(Path<?> path, Boolean optional) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(path, optional, new Attributes.Attribute[0]);
    }

    /**
     * Parameter.
     *
     * @param path       path
     * @param attributes attributes
     * @return builder
     * @apiNote {@link Path} 정보로 {@link ParameterDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder parameter(Path<?> path, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(path, false, attributes);
    }

    /**
     * Parameter.
     *
     * @param path       path
     * @param optional   optional
     * @param attributes attributes
     * @return builder
     * @apiNote {@link Path} 정보로 {@link ParameterDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder parameter(Path<?> path, Boolean optional, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.parameter");

      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);

      return parameter(
          Optional.of(pathMap.get("name").toString()).orElse(""),
          Optional.of(pathMap.get("description").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Parameter.
     *
     * @param name        name
     * @param description description
     * @return builder
     * @apiNote {@link ParameterDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder parameter(String name, String description) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(name, description, false, new Attributes.Attribute[0]);
    }

    /**
     * Parameter.
     *
     * @param name        name
     * @param description description
     * @param optional    optional
     * @return builder
     * @apiNote {@link ParameterDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder parameter(String name, String description, Boolean optional) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(name, description, optional, new Attributes.Attribute[0]);
    }

    /**
     * Parameter.
     *
     * @param name        name
     * @param description description
     * @param attributes  attributes
     * @return builder
     * @apiNote {@link ParameterDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:39
     */
    public Builder parameter(String name, String description, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(name, description, false, attributes);
    }

    /**
     * Parameter.
     *
     * @param maps maps
     * @return builder
     * @apiNote {@link HashMap} 정보로 {@link ParameterDescriptor} 생성<br>
     * 여기서 사용되는 HashMap 데이터는 run.freshr:search-docs 의<br>
     * SearchClass & SearchComment Annotation 을 사용한 클래스가 재구성되어<br>
     * compile 시점에 만들어지는 class 의 정보
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder parameter(HashMap<?, ?>... maps) {
      log.info("PrintUtil.Builder.parameter");

      List.of(maps).forEach(this::parameter);

      return this;
    }

    /**
     * Parameter.
     *
     * @param map map
     * @return builder
     * @apiNote {@link HashMap} 정보로 {@link ParameterDescriptor} 생성<br>
     * 여기서 사용되는 HashMap 데이터는 run.freshr:search-docs 의<br>
     * SearchClass & SearchComment Annotation 을 사용한 클래스가 재구성되어<br>
     * compile 시점에 만들어지는 class 의 정보
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder parameter(HashMap<?, ?> map) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(map, false);
    }

    /**
     * Parameter.
     *
     * @param map      map
     * @param optional optional
     * @return builder
     * @apiNote {@link HashMap} 정보로 {@link ParameterDescriptor} 생성<br>
     * 여기서 사용되는 HashMap 데이터는 run.freshr:search-docs 의<br>
     * SearchClass & SearchComment Annotation 을 사용한 클래스가 재구성되어<br>
     * compile 시점에 만들어지는 class 의 정보
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder parameter(HashMap<?, ?> map, Boolean optional) {
      log.info("PrintUtil.Builder.parameter");

      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (!hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return parameter(
          Optional.of(map.get("name").toString()).orElse(""),
          Optional.of(map.get("comment").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Parameter.
     *
     * @param map         map
     * @param description description
     * @return builder
     * @apiNote {@link HashMap} 정보로 {@link ParameterDescriptor} 생성<br>
     * 여기서 사용되는 HashMap 데이터는 run.freshr:search-docs 의<br>
     * SearchClass & SearchComment Annotation 을 사용한 클래스가 재구성되어<br>
     * compile 시점에 만들어지는 class 의 정보
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder parameter(HashMap<?, ?> map, String description) {
      log.info("PrintUtil.Builder.parameter");

      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (!hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return parameter(
          Optional.of(map.get("name").toString()).orElse(""),
          description,
          prefixOptional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Parameter.
     *
     * @param name        name
     * @param description description
     * @param optional    optional
     * @param attributes  attributes
     * @return builder
     * @apiNote {@link ParameterDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder parameter(String name, String description, Boolean optional,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.parameter");

      return optional(
          parameterWithName((hasLength(prefix) ? prefix + "." : "") + name)
              .description(prefixDescription + " " + description)
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    /**
     * Link parameter.
     *
     * @param filename filename
     * @param paths    paths
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, Path<?>... paths) {
      log.info("PrintUtil.Builder.linkParameter");

      List.of(paths).forEach(this::parameter);

      return this;
    }

    /**
     * Link parameter.
     *
     * @param filename filename
     * @param path     path
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, Path<?> path) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(filename, path, false, new Attributes.Attribute[0]);
    }

    /**
     * Link parameter.
     *
     * @param filename filename
     * @param path     path
     * @param optional optional
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, Path<?> path, Boolean optional) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(filename, path, optional, new Attributes.Attribute[0]);
    }

    /**
     * Link parameter.
     *
     * @param filename   filename
     * @param path       path
     * @param attributes attributes
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, Path<?> path,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(filename, path, false, attributes);
    }

    /**
     * Link parameter.
     *
     * @param filename   filename
     * @param path       path
     * @param optional   optional
     * @param attributes attributes
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, Path<?> path, Boolean optional,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkParameter");

      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);

      return linkParameter(
          filename,
          Optional.of(pathMap.get("name").toString()).orElse(""),
          Optional.of(pathMap.get("description").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Link parameter.
     *
     * @param filename    filename
     * @param name        name
     * @param description description
     * @return builder
     * @apiNote 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, String name, String description) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(filename, name, description, false, new Attributes.Attribute[0]);
    }

    /**
     * Link parameter.
     *
     * @param filename    filename
     * @param name        name
     * @param description description
     * @param optional    optional
     * @return builder
     * @apiNote 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, String name, String description,
        Boolean optional) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(filename, name, description, optional, new Attributes.Attribute[0]);
    }

    /**
     * Link parameter.
     *
     * @param filename    filename
     * @param name        name
     * @param description description
     * @param attributes  attributes
     * @return builder
     * @apiNote 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, String name, String description,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(filename, name, description, false, attributes);
    }

    /**
     * Link parameter.
     *
     * @param filename filename
     * @param maps     maps
     * @return builder
     * @apiNote {@link HashMap} 정보로 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결<br>
     * 여기서 사용되는 HashMap 데이터는 run.freshr:search-docs 의<br>
     * SearchClass & SearchComment Annotation 을 사용한 클래스가 재구성되어<br>
     * compile 시점에 만들어지는 class 의 정보
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, HashMap<?, ?>... maps) {
      log.info("PrintUtil.Builder.linkParameter");

      List.of(maps).forEach(this::parameter);

      return this;
    }

    /**
     * Link parameter.
     *
     * @param filename filename
     * @param map      map
     * @return builder
     * @apiNote {@link HashMap} 정보로 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결<br>
     * 여기서 사용되는 HashMap 데이터는 run.freshr:search-docs 의<br>
     * SearchClass & SearchComment Annotation 을 사용한 클래스가 재구성되어<br>
     * compile 시점에 만들어지는 class 의 정보
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, HashMap<?, ?> map) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(filename, map, false);
    }

    /**
     * Link parameter.
     *
     * @param filename filename
     * @param map      map
     * @param optional optional
     * @return builder
     * @apiNote {@link HashMap} 정보로 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결<br>
     * 여기서 사용되는 HashMap 데이터는 run.freshr:search-docs 의<br>
     * SearchClass & SearchComment Annotation 을 사용한 클래스가 재구성되어<br>
     * compile 시점에 만들어지는 class 의 정보
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:40
     */
    public Builder linkParameter(String filename, HashMap<?, ?> map, Boolean optional) {
      log.info("PrintUtil.Builder.linkParameter");

      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return linkParameter(
          filename,
          Optional.of(map.get("name").toString()).orElse(""),
          Optional.of(map.get("comment").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Link parameter.
     *
     * @param filename    filename
     * @param map         map
     * @param description description
     * @return builder
     * @apiNote {@link HashMap} 정보로 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결<br>
     * 여기서 사용되는 HashMap 데이터는 run.freshr:search-docs 의<br>
     * SearchClass & SearchComment Annotation 을 사용한 클래스가 재구성되어<br>
     * compile 시점에 만들어지는 class 의 정보
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder linkParameter(String filename, HashMap<?, ?> map, String description) {
      log.info("PrintUtil.Builder.linkParameter");

      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return linkParameter(
          filename,
          Optional.of(map.get("name").toString()).orElse(""),
          description,
          prefixOptional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Link parameter.
     *
     * @param filename    filename
     * @param name        name
     * @param description description
     * @param optional    optional
     * @param attributes  attributes
     * @return builder
     * @apiNote 팝업 {@link ParameterDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder linkParameter(String filename, String name, String description,
        Boolean optional, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkParameter");

      return optional(
          parameterWithName((hasLength(prefix) ? prefix + "." : "") + name)
              .description("link:popup-" + filename + "[" + prefixDescription + " "
                  + description + ",role=\"popup\"]")
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    /**
     * Optional.
     *
     * @param parameterDescriptor parameter descriptor
     * @param optional            optional
     * @return builder
     * @apiNote {@link ParameterDescriptor} 필수 항목 여부 설정
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    private Builder optional(ParameterDescriptor parameterDescriptor, Boolean optional) {
      log.info("PrintUtil.Builder.optional");

      parameterList.add(optional ? parameterDescriptor.optional() : parameterDescriptor);

      return this;
    }

    //  _______  __   _______  __       _______
    // |   ____||  | |   ____||  |     |       \
    // |  |__   |  | |  |__   |  |     |  .--.  |
    // |   __|  |  | |   __|  |  |     |  |  |  |
    // |  |     |  | |  |____ |  `----.|  '--'  |
    // |__|     |__| |_______||_______||_______/

    /**
     * Field.
     *
     * @param paths paths
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?>... paths) {
      log.info("PrintUtil.Builder.field");

      of(paths).forEach(this::field);

      return this;
    }

    /**
     * Field.
     *
     * @param path path
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?> path) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, null, false);
    }

    /**
     * Field.
     *
     * @param path    path
     * @param comment comment
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?> path, String comment) {
      log.info("PrintUtil.Builder.field");

      return field(path, comment, null, false);
    }

    /**
     * Field.
     *
     * @param path path
     * @param type type
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?> path, JsonFieldType type) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, type, false);
    }

    /**
     * Field.
     *
     * @param path     path
     * @param optional optional
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?> path, Boolean optional) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, null, optional);
    }

    /**
     * Field.
     *
     * @param path     path
     * @param comment  comment
     * @param optional optional
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?> path, String comment, Boolean optional) {
      log.info("PrintUtil.Builder.field");

      return field(path, comment, null, optional);
    }

    /**
     * Field.
     *
     * @param path    path
     * @param comment comment
     * @param type    type
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?> path, String comment, JsonFieldType type) {
      log.info("PrintUtil.Builder.field");

      return field(path, comment, type, false);
    }

    /**
     * Field.
     *
     * @param path       path
     * @param attributes attributes
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?> path, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, null, false, attributes);
    }

    /**
     * Field.
     *
     * @param path     path
     * @param type     type
     * @param optional optional
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?> path, JsonFieldType type, Boolean optional) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, type, optional);
    }

    /**
     * Field.
     *
     * @param path       path
     * @param type       type
     * @param attributes attributes
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?> path, JsonFieldType type, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, type, false, attributes);
    }

    /**
     * Field.
     *
     * @param path       path
     * @param comment    comment
     * @param type       type
     * @param optional   optional
     * @param attributes attributes
     * @return builder
     * @apiNote {@link Path} 정보로 {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(Path<?> path, String comment, JsonFieldType type, Boolean optional,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.field");

      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);
      JsonFieldType jsonFieldType = isNull(type) ? (JsonFieldType) pathMap.get("type") : type;
      String description = ofNullable(comment).orElse(pathMap.get("description").toString());

      return field(
          Optional.of(pathMap.get("name").toString()).orElse(""),
          description,
          jsonFieldType,
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Field.
     *
     * @param name        name
     * @param description description
     * @param type        type
     * @return builder
     * @apiNote {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(String name, String description, JsonFieldType type) {
      log.info("PrintUtil.Builder.field");

      return field(name, description, type, false, new Attributes.Attribute[0]);
    }

    /**
     * Field.
     *
     * @param name        name
     * @param description description
     * @param type        type
     * @param optional    optional
     * @return builder
     * @apiNote {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(String name, String description, JsonFieldType type, Boolean optional) {
      log.info("PrintUtil.Builder.field");

      return field(name, description, type, optional, new Attributes.Attribute[0]);
    }

    /**
     * Field.
     *
     * @param name        name
     * @param description description
     * @param type        type
     * @param attributes  attributes
     * @return builder
     * @apiNote {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:41
     */
    public Builder field(String name, String description, JsonFieldType type,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.field");

      return field(name, description, type, false, attributes);
    }

    /**
     * Field.
     *
     * @param name        name
     * @param description description
     * @param type        type
     * @param optional    optional
     * @param attributes  attributes
     * @return builder
     * @apiNote {@link FieldDescriptor} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder field(String name, String description, JsonFieldType type, Boolean optional,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.field");

      return optional(
          fieldWithPath((hasLength(prefix) ? prefix + "." : "") + name)
              .type(type)
              .description(prefixDescription + " " + description)
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    /**
     * Link field.
     *
     * @param paths paths
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(Path<?>... paths) {
      log.info("PrintUtil.Builder.linkField");

      of(paths).forEach(this::linkField);

      return this;
    }

    /**
     * Link field.
     *
     * @param path path
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(Path<?> path) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(null, path, null, null, false);
    }

    /**
     * Link field.
     *
     * @param name name
     * @param path path
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String name, Path<?> path) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(name, path, null, null, false);
    }

    /**
     * Link field.
     *
     * @param name    name
     * @param path    path
     * @param comment comment
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String name, Path<?> path, String comment) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(name, path, comment, null, false);
    }

    /**
     * Link field.
     *
     * @param name name
     * @param path path
     * @param type type
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String name, Path<?> path, JsonFieldType type) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(name, path, null, type, false);
    }

    /**
     * Link field.
     *
     * @param name     name
     * @param path     path
     * @param optional optional
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String name, Path<?> path, Boolean optional) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(name, path, null, null, optional);
    }

    /**
     * Link field.
     *
     * @param name     name
     * @param path     path
     * @param comment  comment
     * @param optional optional
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String name, Path<?> path, String comment, Boolean optional) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(name, path, comment, null, optional);
    }

    /**
     * Link field.
     *
     * @param name    name
     * @param path    path
     * @param comment comment
     * @param type    type
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String name, Path<?> path, String comment, JsonFieldType type) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(name, path, comment, type, false);
    }

    /**
     * Link field.
     *
     * @param name       name
     * @param path       path
     * @param attributes attributes
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String name, Path<?> path, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(name, path, null, null, false, attributes);
    }

    /**
     * Link field.
     *
     * @param name     name
     * @param path     path
     * @param type     type
     * @param optional optional
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String name, Path<?> path, JsonFieldType type, Boolean optional) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(name, path, null, type, optional);
    }

    /**
     * Link field.
     *
     * @param name       name
     * @param path       path
     * @param type       type
     * @param attributes attributes
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String name, Path<?> path, JsonFieldType type,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(name, path, null, type, false, attributes);
    }

    /**
     * Link field.
     *
     * @param name       name
     * @param path       path
     * @param comment    comment
     * @param type       type
     * @param optional   optional
     * @param attributes attributes
     * @return builder
     * @apiNote {@link Path} 정보로 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String name, Path<?> path, String comment, JsonFieldType type,
        Boolean optional,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkField");

      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);
      JsonFieldType jsonFieldType = isNull(type) ? (JsonFieldType) pathMap.get("type") : type;
      String description = ofNullable(comment).orElse(pathMap.get("description").toString());

      return linkField(
          ofNullable(name).orElse(path.getType().getSimpleName()),
          Optional.of(pathMap.get("name").toString()).orElse(""),
          description,
          jsonFieldType,
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Link field.
     *
     * @param className   class name
     * @param name        name
     * @param description description
     * @param type        type
     * @param optional    optional
     * @param attributes  attributes
     * @return builder
     * @apiNote 팝업 {@link FieldDescriptor} 생성<br>
     * description 부분에 html a 요소로 링크를 생성해서 popup 링크를 연결
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder linkField(String className, String name, String description, JsonFieldType type,
        Boolean optional, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkField");

      return optional(
          fieldWithPath((hasLength(prefix) ? prefix + "." : "") + name)
              .type(type)
              .description(
                  "link:popup-" + UPPER_CAMEL.to(LOWER_HYPHEN, className) + "[" + prefixDescription
                      + " " + description + ",role=\"popup\"]")
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    /**
     * Optional.
     *
     * @param fieldDescriptor field descriptor
     * @param optional        optional
     * @return builder
     * @apiNote {@link FieldDescriptor} 필수 항목 여부 설정
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    private Builder optional(FieldDescriptor fieldDescriptor, Boolean optional) {
      log.info("PrintUtil.Builder.optional");

      fieldList.add(optional ? fieldDescriptor.optional() : fieldDescriptor);
      return this;
    }

    /**
     * Add field.
     *
     * @param fieldDescriptorList field descriptor list
     * @return builder
     * @apiNote {@link FieldDescriptor} 목록을 Builder 에 추가
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder addField(List<FieldDescriptor> fieldDescriptorList) {
      log.info("PrintUtil.Builder.addField");

      fieldList.addAll(fieldDescriptorList);
      return this;
    }

    /**
     * Popup.
     *
     * @param title               title
     * @param fieldDescriptorList field descriptor list
     * @return builder
     * @apiNote {@link FieldDescriptor} 정보로 {@link PopupFieldsSnippet} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:42
     */
    public Builder popup(String title, List<FieldDescriptor> fieldDescriptorList) {
      log.info("PrintUtil.Builder.popup");

      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath(title).withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    /**
     * Popup data.
     *
     * @param title               title
     * @param fieldDescriptorList field descriptor list
     * @return builder
     * @apiNote {@link FieldDescriptor} 정보로 {@link PopupFieldsSnippet} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:43
     */
    public Builder popupData(String title, List<FieldDescriptor> fieldDescriptorList) {
      log.info("PrintUtil.Builder.popupData");

      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath("data").withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    /**
     * Popup list.
     *
     * @param title               title
     * @param fieldDescriptorList field descriptor list
     * @return builder
     * @apiNote {@link FieldDescriptor} 정보로 {@link PopupFieldsSnippet} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:43
     */
    public Builder popupList(String title, List<FieldDescriptor> fieldDescriptorList) {
      log.info("PrintUtil.Builder.popupList");

      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath("list[]").withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    /**
     * Popup page.
     *
     * @param title               title
     * @param fieldDescriptorList field descriptor list
     * @return builder
     * @apiNote {@link FieldDescriptor} 정보로 {@link PopupFieldsSnippet} 생성
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:43
     */
    public Builder popupPage(String title, List<FieldDescriptor> fieldDescriptorList) {
      log.info("PrintUtil.Builder.popupPage");

      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath("page").withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    //   ______   ______   .___  ___. .___  ___.   ______   .__   __.
    //  /      | /  __  \  |   \/   | |   \/   |  /  __  \  |  \ |  |
    // |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |  |  | |   \|  |
    // |  |     |  |  |  | |  |\/|  | |  |\/|  | |  |  |  | |  . `  |
    // |  `----.|  `--'  | |  |  |  | |  |  |  | |  `--'  | |  |\   |
    //  \______| \______/  |__|  |__| |__|  |__|  \______/  |__| \__|

    /**
     * Path map.
     *
     * @param path path
     * @return hash map
     * @apiNote {@link Path} 정보를 {@link HashMap} 으로 변환
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:43
     */
    public HashMap<String, Object> pathMap(Path<?> path) {
      log.info("PrintUtil.Builder.pathMap");

      HashMap<String, Object> map = new HashMap<>();
      String qPath = path.toString();
      int qDotPoint = qPath.indexOf(".") + 1;
      String target = qPath.substring(0, qDotPoint);
      String name = qPath.replace(target, "").replace(")", "[]");
      ColumnType columnType = getColumnType(path.getType().getTypeName());
      String description = "";
      String size = columnType.getSize();
      String format = columnType.getFormat();
      JsonFieldType type = getJsonType(columnType);

      Comment comment = path.getAnnotatedElement().getAnnotation(Comment.class);
      Column column = path.getAnnotatedElement().getAnnotation(Column.class);

      if (!isNull(comment)) {
        description = comment.value();
      }

      if (!isNull(column) && path.getType().getTypeName().equals(String.class.getTypeName())) {
        size = Optional.of(column.length()).orElse(0) + " characters";
      }

      map.put("name", name); // 이름
      map.put("description", description); // 설명
      map.put("columnType", columnType); // 컬럼 유형
      map.put("size", size); // 제한 크기
      map.put("format", format); // 규칙
      map.put("type", type); // 유형

      return map;
    }

    /**
     * Attribute list.
     *
     * @param pathMap    path map
     * @param attributes attributes
     * @return list
     * @apiNote {@link HashMap} 정보를 기준으로 추가 속성을 설정
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:43
     */
    private List<Attributes.Attribute> attributeList(HashMap<String, Object> pathMap,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.attributeList");

      List<Attributes.Attribute> originList = List.of(attributes);
      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (pathMap.containsKey("size") && originList.stream()
          .noneMatch(attribute -> attribute.getKey().equals("size"))) {
        attributeList.add(key("size").value(pathMap.get("size")));
      }

      if (pathMap.containsKey("format") && originList.stream()
          .noneMatch(attribute -> attribute.getKey().equals("format"))) {
        attributeList.add(key("format").value(pathMap.get("format")));
      }

      return attributeList;
    }

    /**
     * 컬럼 유형 조회.
     *
     * @param type type
     * @return column type
     * @apiNote 데이터 유형으로 Database 데이터 유형 조회
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:43
     */
    private ColumnType getColumnType(String type) {
      log.info("PrintUtil.Builder.getColumnType");

      ColumnType columnType;

      switch (type) {
        case "java.lang.Float":
          columnType = FLOAT;
          break;

        case "java.lang.Double":
          columnType = DOUBLE;
          break;

        case "java.lang.BigDecimal":
          columnType = DECIMAL;
          break;

        case "java.lang.Byte":
          columnType = TINYINT;
          break;

        case "java.lang.Short":
          columnType = SMALLINT;
          break;

        case "java.lang.String":
          columnType = VARCHAR;
          break;

        case "java.lang.Long":
          columnType = BIGINT;
          break;

        case "java.lang.Integer":
          columnType = INT;
          break;

        case "java.lang.Boolean":
          columnType = BIT;
          break;

        case "java.time.LocalDate":
          columnType = DATE;
          break;

        case "java.time.LocalDateTime":
          columnType = DATETIME;
          break;

        case "java.time.LocalTime":
          columnType = TIME;
          break;

        case "byte[]":
          columnType = BLOB;
          break;

        default:
          columnType = UNKNOWN;
          break;
      }

      return columnType;
    }

    /**
     * Json 유형 조회.
     *
     * @param columnType column type
     * @return json type
     * @apiNote 데이터 유형으로 Json 데이터 유형 조회
     * @author FreshR
     * @since 2023. 1. 18. 오후 5:04:43
     */
    private JsonFieldType getJsonType(ColumnType columnType) {
      log.info("PrintUtil.Builder.getJsonType");

      JsonFieldType jsonFieldType;

      switch (columnType) {
        case TINYINT:
        case BIT:
          jsonFieldType = BOOLEAN;
          break;

        case FLOAT:
        case DOUBLE:
        case DECIMAL:
        case SMALLINT:
        case BIGINT:
        case INT:
          jsonFieldType = NUMBER;
          break;

        case DATE:
        case TIME:
        case DATETIME:
        case VARCHAR:
        case LONGTEXT:
        case BLOB:
        default:
          jsonFieldType = STRING;
          break;
      }

      return jsonFieldType;
    }
  }

}
