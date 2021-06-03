package jpabook.jpashop.repository;

import jpabook.jpashop.domains.TESTAUTOJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TESTAUTOJPARepository extends JpaRepository<TESTAUTOJPA, String>, JpaSpecificationExecutor<TESTAUTOJPA> {

}