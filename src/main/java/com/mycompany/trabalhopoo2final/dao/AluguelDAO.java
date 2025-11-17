package com.mycompany.trabalhopoo2final.dao;

import com.mycompany.trabalhopoo2final.entity.Aluguel;
import com.mycompany.trabalhopoo2final.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class AluguelDAO {

    public void salvar(Aluguel aluguel) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(aluguel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void excluir(Aluguel aluguel) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(aluguel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Aluguel> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL para trazer os aluguéis, otimizando a busca
            // (join fetch) para já trazer usuários e veículos
            return session.createQuery("from Aluguel a join fetch a.usuario join fetch a.veiculo", Aluguel.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}