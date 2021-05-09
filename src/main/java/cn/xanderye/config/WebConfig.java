package cn.xanderye.config;

import cn.xanderye.filter.CorsFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author XanderYe
 * @date 2018-12-21
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * 过滤器
     * @param
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     * @author XanderYe
     * @date 2019/8/27
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new CorsFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }

    /**
     * 格式化json
     * @param converters
     * @return void
     * @author XanderYe
     * @date 2020-08-15
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //springboot 2.0后fastJson不生效，需增加以下代码
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);

        //自定义配置
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setSerializeFilters((ValueFilter) (o, s, source) -> {
            if (source == null) {
                return "";
            }
            if (source instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sdf.format(source);
            }
            return source;
        });
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }
}
