package com.inno.portpolio.paging.vo;

public class PageProcess {
	
	public PaginationVO getPagination(int page, int pageSize,PaginationVO vo,String tableName) {
		int totRecCnt = 0;
		
//		if(tableName.equals("CgvReview")) {
//			totRecCnt = movieDAO.getTotRecCnt(tableName,vo.getMovieTitle()); 
//		}
//		else if(tableName.equals("CGVTICKET")) {
//			if(vo.getSearchStr() == null) totRecCnt = adminDAO.getTotRecCnt(vo.getPart(),vo.getSearchStr(),tableName,0);
//			else totRecCnt = adminDAO.getTotRecCnt(vo.getPart(),vo.getSearchStr(),tableName,1);
//		}
//		else if(tableName.equals("CGVMEMBER")) {
//			
//		if(vo.getSearchStr() == null || vo.getPart().equals("")) {
//				totRecCnt = adminDAO.getTotRecCnt(vo.getPart(), vo.getSearchStr(),tableName, 0);
//			}
//			else {
//				totRecCnt = adminDAO.getTotRecCnt(vo.getPart(), vo.getSearchStr(),tableName, 1);
//			}
//		}
//		else if(tableName.equals("CGVREPORT")) {
//			if(vo.getSearchStr() == null || vo.getPart().equals("")) {
//				totRecCnt = adminDAO.getTotRecCnt(vo.getPart(), vo.getSearchStr(), tableName, 0);
//			}
//			else {
//				totRecCnt = adminDAO.getTotRecCnt(vo.getPart(), vo.getSearchStr(), tableName, 1); 
//			}
//		}
//		else if(tableName.equals("CGVCOUPON")) {
//			if(vo.getSearchStr() == null  || vo.getPart().equals("")) {
//				totRecCnt = adminDAO.getTotRecCnt(vo.getPart(), vo.getSearchStr(), tableName, 0);
//			}
//			else {
//				totRecCnt = adminDAO.getTotRecCnt(vo.getPart(), vo.getSearchStr(), tableName, 1); 
//			}
//		}
//		else if(tableName.equals("CGVBOARD")) {
//			if(vo.getSearchStr() == null  || vo.getPart().equals("")) {
//				totRecCnt = adminDAO.getTotRecCnt(vo.getPart(), vo.getSearchStr(), tableName, 0);
//			}
//			else {
//				totRecCnt = adminDAO.getTotRecCnt(vo.getPart(), vo.getSearchStr(), tableName, 1); 
//			}
//		}
			
		
		int totPage = (totRecCnt % pageSize)== 0 ? totRecCnt / pageSize : (totRecCnt / pageSize) + 1;
		int startIndexNo = (page - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		int blockSize = 3;
		int curBlock = (page - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		
		vo.setTotPage(totPage);
		vo.setStartIndexNo(startIndexNo);
		vo.setCurScrStartNo(curScrStartNo);
		vo.setBlockSize(blockSize);
		vo.setCurBlock(curBlock);
		vo.setLastBlock(lastBlock);
		vo.setPage(page);
		vo.setPageSize(pageSize);
		vo.setTotRecCnt(totRecCnt);
		
		return vo;
	}
}
