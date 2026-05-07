package dev.axeldiego.realchatapi.repository;

import dev.axeldiego.realchatapi.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

