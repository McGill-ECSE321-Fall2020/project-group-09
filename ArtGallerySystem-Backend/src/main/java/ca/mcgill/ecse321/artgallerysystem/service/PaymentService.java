package ca.mcgill.ecse321.artgallerysystem.service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.PaymentException;
@Service
public class PaymentService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
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
		//if (paymentRepository.findPaymentByPaymentId(id)!=null) {
		//	throw new PaymentException ("payment already exist");
		//}
		Payment payment = new Payment ();
		payment.setIsSuccessful(success);
		payment.setPaymentId(id);
		payment.setPurchase(purchase);
		payment.setPaymentMethod(method);
		paymentRepository.save(payment);
		return payment;
	}
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
	@Transactional
	public List<Payment> getAllPayments(){
		return toList(paymentRepository.findAll());
		
	}
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
