package com.raj.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.ecommerce.DTO.ShippingDTO;
import com.raj.ecommerce.exception.OrderException;
import com.raj.ecommerce.exception.ShipperException;
import com.raj.ecommerce.exception.ShippingException;
import com.raj.ecommerce.model.Orders;
import com.raj.ecommerce.model.Shipper;
import com.raj.ecommerce.model.ShippingDetails;
import com.raj.ecommerce.repository.OrderRepository;
import com.raj.ecommerce.repository.ShipperRepository;
import com.raj.ecommerce.repository.ShippingRepository;
import com.raj.ecommerce.service.ShippingService;

@Service
public class ShippingServiceImpl implements ShippingService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ShipperRepository shipperRepo;

	@Autowired
	private ShippingRepository shippingRepo;

	/*@Override
	public ShippingDetails setShippingDetails(Integer orderId, Integer shipperId, ShippingDetails shippingObj)
			throws ShippingException {
		
		if(shippingObj==null) {
			throw new ShippingException("Shipping Details Can't be Null");
		}
				
		Orders existOrder = orderRepo.findById(orderId)
				.orElseThrow(()-> new OrderException("Order Not Found"));
		
		Shipper existShipper = shipperRepo.findById(shipperId)
				.orElseThrow(()-> new ShipperException("Shipper Id Not Found in the Database"));
		
		//save the ShippingDetails Entity First
		shippingObj.setOrder(existOrder); //Shipping to Order Object
		shippingObj.setShipper(existShipper); //Shipping to Shipper Object
		
		shippingObj = shippingRepo.save(shippingObj);
		
		existOrder.setShippingDetails(shippingObj);  //Order to Shipping
		orderRepo.save(existOrder);  //Save the Order
		
		return shippingObj;
		
	}*/

	@Override
	public ShippingDetails setShippingDetails(Integer orderId, Integer shipperId, ShippingDetails shippingObj)
			throws ShippingException {

		if (shippingObj == null) {
			throw new ShippingException("Shipping Details Can't be Null");
		}

		Orders existOrder = orderRepo.findById(orderId).orElseThrow(() -> new OrderException("Order Not Found"));

		if (existOrder.getShippingDetails() != null) {
			throw new ShippingException("Shipping Details Already Added");
		}

		Shipper existShipper = shipperRepo.findById(shipperId)
				.orElseThrow(() -> new ShipperException("Shipper Id Not Found"));

		// Mapping
		shippingObj.setOrder(existOrder);
		shippingObj.setShipper(existShipper);

		existShipper.getShippingDetails().add(shippingObj);

		// Save
		shippingObj = shippingRepo.save(shippingObj);

		existOrder.setShippingDetails(shippingObj);

		orderRepo.save(existOrder);
		shipperRepo.save(existShipper);

		return shippingObj;
	}

	@Override
	public ShippingDetails updateShippingDetails(Integer shippingId, ShippingDTO shippingDTO) throws ShippingException {

		ShippingDetails existShippingDetails = shippingRepo.findById(shippingId)
				.orElseThrow(() -> new ShippingException("No Shipping Details Found"));

		existShippingDetails.setAddress(shippingDTO.getAddress());
		existShippingDetails.setCity(shippingDTO.getCity());
		existShippingDetails.setCountry(shippingDTO.getCountry());
		existShippingDetails.setPostalCode(shippingDTO.getPostalCode());
		existShippingDetails.setState(shippingDTO.getState());

		return shippingRepo.save(existShippingDetails);
	}

	@Override
	public void deleteShippingDetails(Integer shippingId) throws ShippingException {

		ShippingDetails existShippingDetails = shippingRepo.findById(shippingId)
				.orElseThrow(() -> new ShippingException("No Shipping Details Found"));

		shippingRepo.delete(existShippingDetails);
	}
}
