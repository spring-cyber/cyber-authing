package com.cyber.authing.domain.response;

import lombok.Data;

/**
 * @Author: lijianming
 * @Description:
 * @Date: Created in 10:35 2023/7/14
 * @Modified By:
 */
@Data
public class CountStatus {

    //状态标识
    private Integer status;

    //状态数量
    private Long count;

}
