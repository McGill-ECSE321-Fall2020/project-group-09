package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.Payment;

@Repository
public interface PaymentRepository extends CrudRepository <Payment, String>  {
public Payment findPaymentByPaymentId(String paymentId);

}
