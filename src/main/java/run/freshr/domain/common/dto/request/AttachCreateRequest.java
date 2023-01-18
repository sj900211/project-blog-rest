package run.freshr.domain.common.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachCreateRequest {

  private String alt;

  private String title;

  private String directory;

  @NotEmpty
  private List<MultipartFile> files;

}
