package cn.shaikuba.mock.data.entity.base;

import lombok.Data;

import java.util.List;

@Data
public class Pageable<M> {

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * Page index
     */
    protected Integer pageNumber;
    /**
     * Records number one page
     */
    protected Integer pageSize;

    /**
     * Page number can be paged, derived from dataCount / pageSize
     */
    protected Integer totalPage;

    /**
     * data total count number.
     */
    protected Integer dataCount;

    private List<M> elements;

    public Pageable() {
        this.pageNumber = 0;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Pageable(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber <= 0 ? 1 : pageNumber;
        this.pageSize = pageSize <= 0 ? 1 : pageSize;
    }

    /**
     * Data count has been filtered on given conditions, and using this value to calculate the total page number.
     *
     * @param dataCount
     */
    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
        totalPage = dataCount % pageSize == 0 ? dataCount / pageSize : (dataCount / pageSize + 1);
    }

}
