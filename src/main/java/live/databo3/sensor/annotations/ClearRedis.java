package live.databo3.sensor.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * clearRedis 의 포인트 컷 생성을 위한 어노테이션
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClearRedis {
}
