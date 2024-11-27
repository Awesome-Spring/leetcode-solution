package com.shunwang.computing.monitor.api.model.request;

import com.shunwang.computing.common.model.PageRequest;
import lombok.Data;

/**
 * Created on 2024/11/12 13:50 
 *
 * @author jiang tao
 */
@Data
public class BMExceptionPageRequest extends PageRequest {
    private static final long serialVersionUID = -1996906617973537535L;


    private  Integer exceptionId;

}
