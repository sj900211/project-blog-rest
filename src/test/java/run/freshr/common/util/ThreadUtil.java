package run.freshr.common.util;

/**
 * Thread 변수 관리.
 *
 * @author FreshR
 * @apiNote 테스트 통신에서 사용할 전역 변수 관리
 * @since 2023. 1. 13. 오전 11:24:08
 */
public class ThreadUtil {

  public static ThreadLocal<String> threadAccess = new ThreadLocal<>(); // ACCESS TOKEN
  public static ThreadLocal<String> threadRefresh = new ThreadLocal<>(); // REFRESH TOKEN
  public static ThreadLocal<String> threadPublicKey = new ThreadLocal<>(); // RSA PUBLIC KEY

}
