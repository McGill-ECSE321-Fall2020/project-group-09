package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;

public interface PaymentRepository extends CrudRepository <Payment, String>  {
public Payment findPaymentById(String paymentId);

}
