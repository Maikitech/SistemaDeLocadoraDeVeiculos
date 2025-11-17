package com.mycompany.trabalhopoo2final.dao;

import com.mycompany.trabalhopoo2final.entity.Veiculo;
import com.mycompany.trabalhopoo2final.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class VeiculoDAO {

    public void salvar(Veiculo veiculo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(veiculo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void excluir(Veiculo veiculo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(veiculo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    /**
     * NOVO MÉTODO: Busca um Veiculo pelo seu ID.
     * Essencial para a função de "Editar".
     */
    public Veiculo buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Veiculo.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Veiculo> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Veiculo", Veiculo.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}