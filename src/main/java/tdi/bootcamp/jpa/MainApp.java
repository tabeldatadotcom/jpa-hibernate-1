package tdi.bootcamp.jpa;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;

import tdi.bootcamp.jpa.model.Employee;
import tdi.bootcamp.jpa.util.HibernateUtil;

public class MainApp {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
 
      
		String result = getNativeQuery(  session,  "select version()");
		System.out.println(result);

		//Integer emp = simpanPegawai(  session);
		//updatePegawai(  session);
		//System.out.println( " hasil = "+emp);
		//deletePegawai(  session);
		updatePegawaiDua(  session);
		List<Employee>  listPeg = getListPegawai(  session);
		for (Employee employee : listPeg) {
			System.out.println(employee.getName());
		}
		
		session.getTransaction().commit();
		session.close();

		HibernateUtil.shutdown();
	}
	
	private static Integer simpanPegawai(Session session){
		Employee emp = new Employee();
		emp.setName("nama abcde");
		emp.setAlamat(" JL Tes ABCDE ");
		emp.setIdEntry("user1");
		emp.setTglEntry(new Timestamp(System.currentTimeMillis()));
		return (Integer) session.save(emp);
		
	}
	
	private static void updatePegawai(Session session){
		Employee emp = session.find(Employee.class, 2)  ;
		emp.setName("nama abcde update");
		emp.setAlamat(" JL Tes ABCDE  update");
		emp.setIdEntry("user1");
		emp.setTglEntry(new Timestamp(System.currentTimeMillis()));
		    session.update(emp); 
		
	}
	
	private static int updatePegawaiDua(Session session){
		 return session.createQuery("update Employee set name = :nama where id = :id ")
		 .setParameter("nama", "nama update hql ") 
		 .setParameter("id", 1).executeUpdate() ;
		
	}
	
	private static void deletePegawai(Session session){
		Employee emp = session.find(Employee.class, 2)  ; 
		    session.delete( emp); 
		
	}
	
	
	private static List<Employee> getListPegawai(Session session){
		return session.createQuery("select p from Employee p ").getResultList();
		
	}
	private static String getNativeQuery(Session session,String sql){
		return (String) session.createNativeQuery(sql).getSingleResult();
		
	}
}
