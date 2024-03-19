package com.inno.portpolio.toastGrid.entity;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.Data;

/**
* @author 연구개발 5팀 사원 김재성
* @since 2024. 03. 12.
* @version 1.0
* @see javax.servlet.http.HttpServlet 
* <pre>
* [[개정이력(Modification Information)]]
*    수정일            수정자               수정내용
* ------------     --------    ----------------------
* 2024.03.12.        김재성       최초작성
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/
@Data
public class PaginationEntity {
	
    boolean isCreated;

    boolean isUpdated;

    boolean isDeleted;

    private int limit;

    private int page;

    private int offset;
    
    
    
    @JsonGetter
    public int getOffset(){
        if(limit <= 0)
            limit = 10;
        if(page <= 0)
            page = 1;

        return (page - 1) * limit;
    }
}
