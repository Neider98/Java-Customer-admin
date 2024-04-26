package com.customers.admin.services.Impl;

import com.customers.admin.models.entities.Customer;
import com.customers.admin.repositories.ICustomerRepository;
import com.customers.admin.services.ICustomerService;
import com.customers.admin.util.exceptions.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository userRepository;

    /**
     *
     * @param userRepository
     */
    @Autowired
    public CustomerServiceImpl(ICustomerRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Customer> findAllCustomer() {
        return userRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Customer> findCustomerById(Long id) {
        Optional<Customer> user = userRepository.findById(id);
        user.orElseThrow(() -> new IdNotFoundException("users"));
        return user;
    }

    /**
     *
     * @param customer
     * @return
     */
    @Override
    public Customer customerUser(Customer customer) {
        String sharedKey = deleteDomainEmail(customer.getEmail());
        customer.setSharedKey(sharedKey);
        return userRepository.save(customer);
    }

    /**
     *
     * @param customer
     * @return
     */
    @Override
    public Optional<Customer> updateCustomer(Customer customer) {
        Optional<Customer> userOptional = userRepository.findById(customer.getId());
        if (userOptional.isPresent()) {
            Customer updatedCustomer = userRepository.save(customer);
            return Optional.of(updatedCustomer);
        } else {
            return Optional.empty();
        }
    }

    /**
     *
     * @param customerId
     * @return
     */
    @Override
    public Optional<Customer> deleteCustomer(Long customerId) {
        Optional<Customer> userOptional = userRepository.findById(customerId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(customerId);
            return userOptional;
        } else {
            return Optional.empty();
        }
    }

    /**
     *
     * @param correo
     * @return
     */
    public static String deleteDomainEmail(String correo) {
        int indiceArroba = correo.indexOf('@');
        if (indiceArroba != -1) {
            return correo.substring(0, indiceArroba);
        } else {
            return correo;
        }
    }

}
