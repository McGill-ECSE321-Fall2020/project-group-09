package ca.mcgill.ecse321.artgallerysystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.PaymentException;
@Service
/**
 * this class contains useful business methods to manipulate data in backend, used in controller 
 * @author amelia
 *
 */
public class PaymentService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
	/**
 	 * create a payment 
 	 * @param id: payment id 
 	 * @param method: payment method credit/debit
 	 * @param purchase: related purchase instance (since we are creating purchase before payment)
 	 * @param success: payment status 
 	 * @return
 	 */
	@Transactional
	public Payment createPayment (String id, PaymentMethod method, Purchase purchase, boolean success) {
		if (id == null|| id == "") {
			throw new PaymentException ("provide valid id");
		}
		if (method == null) {
			throw new PaymentException ("provide valid method");
		}
		if (purchase == null) {
			throw new PaymentException ("provide valid purchase");
		}
		Payment payment = new Payment ();
		payment.setIsSuccessful(success);
		payment.setPaymentId(id);
		payment.setPurchase(purchase);
		payment.setPaymentMethod(method);
		paymentRepository.save(payment);
		return payment;
	}
	/**
 	 * delete an existing payment by id 
 	 * @param id
 	 * @return old payment instance 
 	 */
	@Transactional
	public Payment deletePayment(String id) {
		if (id == null||id == "") {
			throw new PaymentException ("provide vaild id");
		}
		Payment payment = paymentRepository.findPaymentByPaymentId(id);
		if (payment == null) {
			throw new PaymentException ("not exist payment");
		}
		if(payment.isIsSuccessful()==true) {
			throw new PaymentException ("unable to delete");
		}
		Payment pay = null;
		paymentRepository.deleteById(id);
		return pay;
	}
	/**
 	 * get an existing payment by id 
 	 * @param id: payment id 
 	 * @return existing payment instance when succeed, throw exception otherwise 
 	 */
	@Transactional
	public Payment getPayment(String id) {
		if (id == null||id == "") {
			throw new PaymentException ("provide vaild id");
		}
		Payment payment = paymentRepository.findPaymentByPaymentId(id);
		if (payment == null) {
			throw new PaymentException ("not exist payment");
		}
		return payment;
	}
	/**
 	 * get list of all payments 
 	 * @return list of payment instances 
 	 */
	@Transactional
	public List<Payment> getAllPayments(){
		return toList(paymentRepository.findAll());
		
	}
	/**
 	 * update existing payment (get by id) with a new payment method 
 	 * @param id
 	 * @param newMethod: new payment method 
 	 * @return updated payment when succeed, throw exception otherwise 
 	 */
	@Transactional
	public Payment updatePaymentMethod(String id, PaymentMethod newMethod) {
		if (id == null||id == "") {
			throw new PaymentException ("provide vaild id");
		}
		Payment payment = paymentRepository.findPaymentByPaymentId(id);
		if (payment == null) {
			throw new PaymentException ("not exist payment");
		}
		if (payment.getPaymentMethod()== newMethod) {
			throw new PaymentException("give a new method");
		}
		payment.setPaymentMethod(newMethod);
		paymentRepository.save(payment);
		return payment;
	}
	
	private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}
