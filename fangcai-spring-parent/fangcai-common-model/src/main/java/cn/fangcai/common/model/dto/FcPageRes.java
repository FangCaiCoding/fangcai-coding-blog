package cn.fangcai.common.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:32
 * @description
 */
@Data
public class FcPageRes<T> {

    private Integer page;

    private Integer pageSize;

    private Long total;

    private List<T> records;

    public FcPageRes() {
    }

    public FcPageRes(PageReq pageBase) {
        this.page = pageBase.getPage();
        this.pageSize = pageBase.getPageSize();
        this.total = 0L;
    }

    public FcPageRes(Integer page, Integer pageSize, Long total, List<T> records) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
    }

    public FcPageRes<T> total(Long total) {
        this.total = total;
        return this;
    }

    public FcPageRes<T> records(List<T> records) {
        this.records = records;
        return this;
    }

}
