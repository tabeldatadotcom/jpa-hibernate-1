package tdi.bootcamp.jpa;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;

import tdi.bootcamp.jpa.model.Alamat;
import tdi.bootcamp.jpa.model.Departement;
import tdi.bootcamp.jpa.model.Employee;
import tdi.bootcamp.jpa.model.Kecamatan;
import tdi.bootcamp.jpa.model.Student;
import tdi.bootcamp.jpa.util.HibernateUtil;

public class MainApp {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		String result = getNativeQuery(session, "select version()");
		System.out.println(result);

		// Integer emp = simpanPegawai( session);
		// updatePegawai( session);
		// System.out.println( " hasil = "+emp);
		// deletePegawai( session);
		// updatePegawaiDua(session);
		Integer emp = simpanMurid(session);// simpanPegawai(session);
		List<Employee> listPeg = getListPegawai(session);// getListPegawaiDanDept( session);//
		for (Employee employee : listPeg) {
			System.out.println(employee.getName());
		}

		session.getTransaction().commit();
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
		Departement dept = new Departement();
		dept.setNamaDepartement("DIV IT");
		dept.setIdEntry("userdept");
		dept.setTglEntry(new Timestamp(System.currentTimeMillis()));
		Employee emp = new Employee();
		emp.setName("nama abcde dept ");
		emp.setAlamat(" JL Tes ABCDE DEPT");
		emp.setIdEntry("user1dept");
		emp.setTglEntry(new Timestamp(System.currentTimeMillis()));
		emp.setDepartement(dept);
		// ------------set
		/*
		 * Set simpanTask = new LinkedHashSet<Task>( ); Task task = new Task();
		 * task.setNamaTugas("tugas 1"); task.setIdEntry("entrytask1");
		 * task.setTglEntry(new Timestamp(System.currentTimeMillis()));
		 * simpanTask.add(task);
		 * 
		 * emp.setListTugas(simpanTask);
		 */

		// ------
		return (Integer) session.save(emp);

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

	private static List<Employee> getListPegawaiDanDept(Session session) {
		return session.createQuery("select p from Employee p JOIN FETCH p.departement ").getResultList();

	}
}
