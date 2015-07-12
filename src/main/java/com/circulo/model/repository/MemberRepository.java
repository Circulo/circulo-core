package com.circulo.model.repository;

import com.circulo.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 7/11/15.
 */
public interface MemberRepository extends MongoRepository<Member, String> {
}
