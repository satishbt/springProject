package com.app.stock.data;

import org.springframework.data.repository.CrudRepository;
import com.app.stock.model.Users;

public interface UserRepository extends CrudRepository<Users, Long> {

    public Users findByEmail(String email);

}
