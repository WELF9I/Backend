package backend.backend.repository;

import backend.backend.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findAll();
    Optional<ExpenseEntity> findById(Long id);
    ExpenseEntity save(ExpenseEntity entity);
    void deleteById(Long id);
    long count();
    boolean existsById(Long id);
}
