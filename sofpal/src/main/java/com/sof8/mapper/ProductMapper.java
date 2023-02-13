package com.sof8.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Paging;
import com.sof8.dto.Product;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface ProductMapper extends Sof8Mapper<Integer, Product>{
	public int selectmaincat(Integer k) throws Exception;
	public int selectcategory(Product v) throws Exception;
	public int selectcnt(Integer k) throws Exception;
	public int updatecnt(Integer k) throws Exception;
	public List<Product> selectold() throws Exception;
	public List<Product> selectnew() throws Exception;
	public int selecttotal(Map<String, Object> map) throws Exception;
	public int selecttotaladmin(Map<String, Object> map) throws Exception;
	public List<Product> selectlist(Paging p) throws Exception;
	public List<Product> selectlistadmin(Paging p) throws Exception;
}
