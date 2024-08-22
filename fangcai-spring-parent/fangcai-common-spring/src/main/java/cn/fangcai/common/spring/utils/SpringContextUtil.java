package cn.fangcai.common.spring.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author MouFangCai
 * @date 2024/8/22 23:09
 * @description
 */
public class SpringContextUtil implements ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }


    public void registerBean(final Class<?> clazz, final Object object) {
        if (Objects.nonNull(this.applicationContext)) {
            this.applicationContext.getBeanFactory().registerSingleton(this.generateBeanName(clazz), object);
        }
    }

    public void registerAliasByClazz(final Class<?> clazz, final String alias) {
        if (Objects.nonNull(this.applicationContext) && !StringUtils.hasText(alias)) {
            Object obj = this.applicationContext.getBean(clazz);

            String[] beanNames = this.applicationContext.getBeanNamesForType(clazz);
            if (beanNames.length > 0) {
                this.applicationContext.getBeanFactory().registerAlias(beanNames[0], alias);
            }
        }
    }

    public Map<String, Object> getBeansWithAnnotation(final Class<? extends Annotation> clazz) {
        Assert.notNull(clazz, "the clazz type can't be null!");
        Assert.notNull(applicationContext, "The spring context has not been initialized!");
        try {
            return this.applicationContext.getBeansWithAnnotation(clazz);
        } catch (BeansException e) {
            return new HashMap<>();
        }
    }

    public <T> T getBean(final Class<T> clazz) {
        Assert.notNull(clazz, "the clazz type can't be null!");
        Assert.notNull(applicationContext, "The spring context has not been initialized!");
        try {
            return this.applicationContext.getBean(clazz);
        } catch (BeansException e) {
            try {
                return this.getByName(clazz);
            } catch (BeansException ex) {
                return null;
            }
        }
    }

    public <T> T getBean(final String beanName, final Class<T> clazz) {
        Assert.notNull(clazz, "the clazz type can't be null!");
        Assert.notNull(applicationContext, "The spring context has not been initialized!");
        try {
            return this.applicationContext.getBean(this.generateBeanName(beanName), clazz);
        } catch (BeansException e) {
            return null;
        }
    }

    public <T> Map<String, T> getBeansOfType(final Class<T> clazz) {
        Assert.notNull(clazz, "the clazz type can't be null!");
        Assert.notNull(applicationContext, "The spring context has not been initialized!");
        try {
            return this.applicationContext.getBeansOfType(clazz);
        } catch (BeansException e) {
            return new HashMap<>();
        }
    }

    private <T> T getByName(final Class<T> type) {
        Assert.notNull(applicationContext, "The spring context has not been initialized!");
        return this.applicationContext.getBean(this.generateBeanName(type), type);
    }


    public <T> String generateBeanName(Class<T> type) {
        return this.generateBeanName(type.getSimpleName());
    }

    public String generateBeanName(String beanName) {
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    }
}
