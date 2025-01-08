/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javaapplication3.HibernateUtil;
import java.util.List;  // Tambahkan ini


/**
 *
 * @author Andaf RyzenJr
 */
public class ControllerAkun {
    ArrayList<ModelAkun> ArrayData;
    DefaultTableModel tabelModel;
    
    public ControllerAkun(){
        ArrayData = new ArrayList<ModelAkun>();
    }
    
    public void InsertData(int idAkun,String nama, String status, String tgl, int nominal) {
    Session session = HibernateUtil.getSessionFactory().openSession(); // Assuming you have a HibernateUtil class
    Transaction tx = session.beginTransaction();
    try {
        ModelAkun mnj = new ModelAkun(idAkun, nama, status, tgl, nominal);
        session.save(mnj);
        tx.commit();
    } catch (Exception e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
    } finally {
        session.close();
    }
}
    
   public static void UpdateData(int idAkun,String nama, String status, String tgl, int nominal) {
    Session session = HibernateUtil.getSessionFactory().openSession(); // Mengambil session Hibernate
    Transaction tx = session.beginTransaction(); // Memulai transaksi
    try {
        // Mencari objek berdasarkan kdBarang
        ModelAkun mAkun = (ModelAkun) session.get(ModelAkun.class, idAkun); // Cast the result to Mproduk
        
        if (mAkun != null) {
            // Update data produk
            mAkun.setNama(nama);
            mAkun.setStatus(status);
            mAkun.setTgl(tgl);
            mAkun.setNominal(nominal);

            // Simpan perubahan
            session.update(mAkun);
            tx.commit(); // Komit transaksi
        } else {
            System.out.println("Produk dengan kode barang " + idAkun + " tidak ditemukan.");
        }
    } catch (Exception e) {
        if (tx != null) tx.rollback(); // Rollback jika terjadi error
        e.printStackTrace();
    } finally {
        session.close(); // Menutup session
    }
}

public boolean deleteData(String idBarang) {
    Session session = HibernateUtil.getSessionFactory().openSession(); // Open Hibernate session
    Transaction tx = null;
    boolean isDeleted = false;

    try {
        tx = session.beginTransaction();
        
        // Retrieve the product object based on the kdBarang
        ModelAkun mAkun = (ModelAkun) session.get(ModelAkun.class, idBarang);
        
        if (mAkun != null) {
            // Delete the product
            session.delete(mAkun);
            tx.commit(); // Commit the transaction
            isDeleted = true;
        } else {
            System.out.println("Produk dengan kode barang " + idAkun + " tidak ditemukan.");
        }
    } catch (Exception e) {
        if (tx != null) tx.rollback(); // Rollback if error occurs
        e.printStackTrace();
    } finally {
        session.close(); // Close the session
    }

    return isDeleted;
}

  
public DefaultTableModel showData() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = null;
    List<ModelAkun> products = null;
    
    try {
        tx = session.beginTransaction();
        // Retrieve all products from the database
        products = session.createQuery("FROM akun").list(); // Fetch all rows from Mproduk
        tx.commit();
    } catch (Exception e) {
        if (tx != null) tx.rollback();  // Rollback transaction if error occurs
        e.printStackTrace();
    } finally {
        session.close();  // Close the session
    }

    // Define the column names for the table
    String[] columnNames = {"ID Barang", "Nama", "Status", "Jumlah Stok", "Tanggal"};
    
    // Create an Object array to hold the data
    Object[][] data = new Object[products.size()][5];

    // Fill the data array with values from the products list
    for (int i = 0; i < products.size(); i++) {
        ModelAkun product = products.get(i);
        data[i] = new Object[]{product.getIdAkun(), product.getNama(), product.getStatus(), product.getNominal(), product.getTgl()};
    }

    // Return a new DefaultTableModel with the data and column names
    return new DefaultTableModel(data, columnNames);
}



}
