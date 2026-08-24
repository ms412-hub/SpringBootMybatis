package kopo.poly.config;

import kopo.poly.dto.WeatherDTO;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
public class CacheConfig {

    private static String round3(String s) {
        if (s == null || s.isBlank()) return "";
        return new BigDecimal(s).setScale(3, RoundingMode.HALF_UP).toPlainString();
    }

    @Bean
    public KeyGenerator latLonKeyGen() {
        return (target, method, params) -> {
            WeatherDTO d = (WeatherDTO) params[0];

            // 문자열 -> BigDecimal -> 소수점 3자리 반올림 -> 문자열
            String lat = round3(Optional.ofNullable(d.getLat()).orElse(""));
            String lon = round3(Optional.ofNullable(d.getLon()).orElse(""));

            return "v1:" + lat + "_" + lon + "_metric"; // 버전/단위 포함
        };
    }
    @Bean
    public KeyGenerator noticeList() {
        return (target, method, params) -> "v1:notice_list_metric";
    }


    @Bean
    public org.springframework.cache.CacheManager cacheManager() {

        CachingProvider provider =
                Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");


        CacheManager jcacheManager = provider.getCacheManager();


        MutableConfiguration<String, WeatherDTO> cfg =
                new MutableConfiguration<String, WeatherDTO>()
                        .setTypes(String.class, WeatherDTO.class)
                        .setStoreByValue(false) // by reference (성능 위주, 가변 객체 주의)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(
                                new Duration(TimeUnit.MINUTES, 10) // TTL = 10분
                        ));

        if (jcacheManager.getCache("weather", String.class, WeatherDTO.class) == null) {
            jcacheManager.createCache("weather", cfg);
        }
        // notice:list (List 타입으로 생성)
        MutableConfiguration<String, List> noticeListCfg =
                new MutableConfiguration<String, List>()
                        .setTypes(String.class, List.class)
                        .setStoreByValue(false) // 참조 저장: 반환 객체 변형 주의
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(
                                new Duration(TimeUnit.SECONDS, 60)));

        if (jcacheManager.getCache("notice:list", String.class, List.class) == null) {
            jcacheManager.createCache("notice:list", noticeListCfg);
        }

        return new JCacheCacheManager(jcacheManager);
    }
}