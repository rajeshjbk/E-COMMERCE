package com.raj.ecommerce.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.ecommerce.model.Shipper;
import com.raj.ecommerce.service.ShipperService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ecom/order-shippers")
@CrossOrigin("*")
public class ShipperController {
	
	@Autowired
	private ShipperService shipperService;
	
	@PostMapping("/add")
	public ResponseEntity<Shipper> saveShipper(@Valid @RequestBody Shipper shipperObj){
		
		Shipper shipperobj=shipperService.saveShipper(shipperObj);
		return ResponseEntity.ok(shipperobj);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Shipper> getShipperById(@PathVariable Integer id){
		Shipper shipperObj=shipperService.getShipperById(id);
		return ResponseEntity.ok(shipperObj);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteShippingDetails(@PathVariable Integer id) {
		
		shipperService.deleteShipperById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Shipper>> getAllShippers(){
		List<Shipper> shipperList=shipperService.getAllShippers();
		return ResponseEntity.ok(shipperList);
	}
}
