package app.core.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.core.entites.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {

	// native sql - less recommended
	@Query(value = "select * from students where the_gender='F'", nativeQuery = true)
	List<Student> getFemales();

	// JPQL - a form of sql that is Entity oriented (not table oriented)
	@Query(value = "from Student where gender='M'")
	List<Student> getMales();

	@Query(value = "from Student where gender='M'")
	List<Student> getMales(Sort sort);
	
	@Query(value = "from Student where age > :age")
	List<Student> getOlderThan(int age);
	
	
//	Derived method = get their meaning by method name 
//	introducer: JPQL
	
	List<Student> findByName(String name);
	List<Student> findByActiveIsTrue();
	List<Student> findByActiveIsFalse ();
	
}
