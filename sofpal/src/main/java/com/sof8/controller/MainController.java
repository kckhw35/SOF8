package com.sof8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Product;
import com.sof8.service.ProductService;

@Controller
public class MainController {
	@Autowired
	ProductService pservice;
	
	@RequestMapping("/")
	public String main(Model model) {
		List<Product> oldlist = null;
		List<Product> newlist = null;
		String[] op_img = null;
		String[] np_img = null;
		String omain_img = null;
		String nmain_img = null;
		
		try {
			oldlist = pservice.selectold();
			for(Product p : oldlist) {
				omain_img = p.getP_img();
				op_img = omain_img.split(",");
				omain_img = op_img[0];
				p.setP_img(omain_img);
			}
			
			newlist = pservice.selectnew();
			for(Product p : newlist) {
				nmain_img = p.getP_img();
				np_img = nmain_img.split(",");
				nmain_img = np_img[0];
				p.setP_img(nmain_img);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("oldlist", oldlist);
		model.addAttribute("newlist", newlist);
		return "index";
	}
}
