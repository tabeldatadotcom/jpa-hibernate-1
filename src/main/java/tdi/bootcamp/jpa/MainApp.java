package tdi.bootcamp.jpa;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import tdi.bootcamp.jpa.model.Alamat;
import tdi.bootcamp.jpa.model.Departement;
import tdi.bootcamp.jpa.model.Employee;
import tdi.bootcamp.jpa.model.Kecamatan;
import tdi.bootcamp.jpa.model.Student;
import tdi.bootcamp.jpa.model.Task;
import tdi.bootcamp.jpa.util.HibernateUtil;

public class MainApp {
	
	private static List<Departement> getListDept(Session session) {
		return session.createQuery(" select p from Departement p JOIN FETCH p.employee ")
				.getResultList();

	}
	
	private static List<Employee> getListPegawaiDanDept(Session session) {
		return session.createQuery(" select p from Employee p JOIN FETCH p.departement ")
				.getResultList();

	}
	private static Set<Employee> getListPegawaiDanTask(Session session) {
		List<Employee> listData = 
				session.createQuery(" select p from Employee p JOIN FETCH p.listTugas where p.id = :id ")
				.setParameter("id", 42)
				.getResultList();
		return new HashSet<>(listData);

	}
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		

		String result = getNativeQuery(session, "select version()");
		System.out.println(result);

	/*	List<Departement> listdept =  getListDept( session);//
		for (Departement de : listdept) {
			System.out.println(de.getNamaDepartement()+"  emplyee "+de.getEmployee().getName());
		}*/
		
		//
		// Integer emp =  simpanPegawaiDept( session);
		// updatePegawai( session);
		// System.out.println( " hasil = "+emp);
		// deletePegawai( session);
		// updatePegawaiDua(session);
		//Integer emp = simpanMurid(session);// simpanPegawai(session);
		Set<Employee> listPegTask =  getListPegawaiDanTask( session);//
		for (Employee employee : listPegTask) {
			Set<Task> listTugas = employee.getListTugas();
			System.out.println(employee.getName()+"  id " +employee.getId());
			for (Task task : listTugas) {
				System.out.println( "  tugas "+task.getNamaTugas());
			}
		}
		
	/*	List<Employee> listPeg =  getListPegawaiDanDept( session);//
		for (Employee employee : listPeg) {
			System.out.println(employee.getName()+"  dept "+employee.getDepartement().getNamaDepartement());
		}
*/

		session.close();

		HibernateUtil.shutdown();
	}

	private static Integer simpanMurid(Session session) {
		Kecamatan kec = new Kecamatan();
		kec.setNama("antapani");
		Alamat alamat = new Alamat();
		alamat.setKecamatan(kec);
		alamat.setNama("jl XXXXXXX ");
		Student murid = new Student();
		murid.setNama("nama XXXXX");
		murid.setAlamat(alamat);
		return (Integer) session.save(murid);

	}

	private static Integer simpanPegawai(Session session) {
		Employee emp = new Employee();
		emp.setName("nama abcde 1");
		emp.setAlamat(" JL Tes ABCDE xx ");
		emp.setIdEntry("user1");
		emp.setTglEntry(new Timestamp(System.currentTimeMillis()));
		return (Integer) session.save(emp);

	}

	private static Integer simpanPegawaiDept(Session session) {
		session.beginTransaction();
		Departement dept = new Departement();
		dept.setNamaDepartement("DIV IT33");
		dept.setIdEntry("userdeptX33");
		dept.setTglEntry(new Timestamp(System.currentTimeMillis()));
		Employee emp = new Employee();
		emp.setName("nama abcde dept XX33");
		emp.setAlamat(" JL Tes ABCDE DEPTX33");
		emp.setIdEntry("user1deptX");
		emp.setTglEntry(new Timestamp(System.currentTimeMillis()));
		emp.setDepartement(dept);
		int hasil = (Integer)session.save(emp);
		// ------------set
		
		  
		   Task task = new Task();
		   task.setNamaTugas("tugas 23"); 
		   task.setIdEntry("entrytask23");
		   task.setEmployee(emp);
		   task.setTglEntry(new Timestamp(System.currentTimeMillis()));
		   session.save(task);
		  task = new Task();
		   task.setNamaTugas("tugas 33"); 
		   task.setIdEntry("entrytask33");
		   task.setEmployee(emp);
		   task.setTglEntry(new Timestamp(System.currentTimeMillis()));
		   session.save(task); 
		   session.getTransaction().commit();

		// ------
		return hasil;

	}

	private static void updatePegawai(Session session) {
		Employee emp = session.find(Employee.class, 2);
		emp.setName("nama abcde update");
		emp.setAlamat(" JL Tes ABCDE  update");
		emp.setIdEntry("user1");
		emp.setTglEntry(new Timestamp(System.currentTimeMillis()));
		session.update(emp);

	}

	private static int updatePegawaiDua(Session session) {
		return session.createQuery("update Employee set name = :nama where id = :id ")
				.setParameter("nama", "nama update hql ").setParameter("id", 1).executeUpdate();

	}

	private static void deletePegawai(Session session) {
		Employee emp = session.find(Employee.class, 2);
		session.delete(emp);

	}

	private static List<Employee> getListPegawai(Session session) {
		return session.createQuery("select p from Employee p ").getResultList();

	}

	private static String getNativeQuery(Session session, String sql) {
		return (String) session.createNativeQuery(sql).getSingleResult();

	}

	
}
