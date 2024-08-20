package fc.common.model;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:32
 * @description
 */
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
