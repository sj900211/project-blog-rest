package run.freshr.domain.common.dto.response;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.extension.response.ResponseExtension;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AttachResponse extends ResponseExtension<Long> {

  private String contentType;

  private String filename;

  private URL url;

  private Long size;

  private String alt;

  private String title;

}
