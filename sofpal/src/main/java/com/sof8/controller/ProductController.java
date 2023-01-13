package com.sof8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
	@RequestMapping("/productall")
	public String product() {
		return "/product/productall";
	}
}
