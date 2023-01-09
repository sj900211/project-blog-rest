package run.freshr.service;

import java.util.List;
import java.util.Map;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.entity.Manager;
import run.freshr.domain.auth.entity.Sign;
import run.freshr.domain.auth.entity.Staff;
import run.freshr.domain.auth.enumeration.Privilege;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.mappers.EnumGetter;

public interface TestService {

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|

  Map<String, List<EnumGetter>> getEnumAll();

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  long createAttach(String filename, String path, Sign creator);

  //      ___      __    __  .___________. __    __
  //     /   \    |  |  |  | |           ||  |  |  |
  //    /  ^  \   |  |  |  | `---|  |----`|  |__|  |
  //   /  /_\  \  |  |  |  |     |  |     |   __   |
  //  /  _____  \ |  `--'  |     |  |     |  |  |  |
  // /__/     \__\ \______/      |__|     |__|  |__|

  void createRsa();

  void createAuth(Long id, Role role);

  void createAccess(String accessToken, Long id, Role role);

  AccessRedis getAccess(String id);

  void createRefresh(String refreshToken, String access);

  long createManager(Privilege privilege, String username, String name);

  Manager getManager(long id);

  long createStaff(Privilege privilege, String username, String name);

  Staff getStaff(long id);

  long createAccount(String username, String name);

  Account getAccount(long id);

  Sign getSign(long id);

}
