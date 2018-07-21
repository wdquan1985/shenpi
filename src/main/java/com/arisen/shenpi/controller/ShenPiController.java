package com.arisen.shenpi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.arisen.shenpi.model.ShenPi;
import com.arisen.shenpi.service.ShenPiService;

/**
 * Created by bruce on 18-07-12.
 */
@Api(value="/api/shenpi", tags="审批流程模块")
@Controller
@RequestMapping(value = "/api/shenpi")
public class ShenPiController {
	@Autowired
	@Qualifier("shenPiService")
    ShenPiService shenPiService;

	@ApiOperation(value="删除供应商信息", notes = "删除供应商信息")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
//		String scheduled = "Job is scheduled!!";
		int result = shenPiService.deleteById(id);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", result + "");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@ApiOperation(value="添加供应商信息", notes = "添加供应商信息")
	@ApiImplicitParam(name="shenpi", value="ShenPi", required = true, dataType = "ShenPi")
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@RequestBody ShenPi shenpi, HttpServletRequest req, HttpServletResponse response) {
    	try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Map<String, Object> map = new HashMap<String, Object>();
    	int result = shenPiService.save(shenpi);
		
		return new ResponseEntity<>(result + "",HttpStatus.OK);
    }
    
	@ApiOperation(value="得到所有", notes = "得到所有供应商信息")
    @RequestMapping(value = "/searchAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ShenPi>> search(HttpServletRequest req, HttpServletResponse response) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<ShenPi> shenpiList= shenPiService.findAll();
		
		return new ResponseEntity<>(shenpiList,HttpStatus.OK);
    }
}
