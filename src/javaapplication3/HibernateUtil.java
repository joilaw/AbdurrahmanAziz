package javaapplication3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Andaf RyzenJr
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    // Membangun SessionFactory
    private static SessionFactory buildSessionFactory() {
        try {
            // Membaca konfigurasi Hibernate dan membuat SessionFactory
            return new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ModelAkun.class).buildSessionFactory();
        } catch (Exception e) {
            // Jika terjadi kesalahan dalam konfigurasi, tampilkan pesan error
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    // Menutup SessionFactory jika aplikasi selesai digunakan
    public static void shutdown() {
        getSessionFactory().close();
    }
}
