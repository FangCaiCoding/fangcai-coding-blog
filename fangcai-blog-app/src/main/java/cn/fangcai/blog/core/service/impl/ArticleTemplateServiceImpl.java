package cn.fangcai.blog.core.service.impl;

import cn.fangcai.blog.core.model.entity.ArticleTemplate;
import cn.fangcai.blog.core.mapper.ArticleTemplateMapper;
import cn.fangcai.blog.core.service.IArticleTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章通用内容模板表 服务实现类
 * </p>
 *
 * @author MouFangCai
 * @since 2024-09-22
 */
@Service
public class ArticleTemplateServiceImpl extends ServiceImpl<ArticleTemplateMapper, ArticleTemplate> implements IArticleTemplateService {

}
