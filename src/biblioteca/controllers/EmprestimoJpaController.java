/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.controllers;

import biblioteca.controllers.exceptions.NonexistentEntityException;
import biblioteca.entities.Emprestimo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import biblioteca.entities.Usuario;
import biblioteca.entities.Livro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vitor
 */
public class EmprestimoJpaController implements Serializable {

    public EmprestimoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Emprestimo emprestimo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario IDUsuario = emprestimo.getIDUsuario();
            if (IDUsuario != null) {
                IDUsuario = em.getReference(IDUsuario.getClass(), IDUsuario.getIDUsuario());
                emprestimo.setIDUsuario(IDUsuario);
            }
            Livro IDLivro = emprestimo.getIDLivro();
            if (IDLivro != null) {
                IDLivro = em.getReference(IDLivro.getClass(), IDLivro.getIDLivro());
                emprestimo.setIDLivro(IDLivro);
            }
            em.persist(emprestimo);
            if (IDUsuario != null) {
                IDUsuario.getEmprestimoList().add(emprestimo);
                IDUsuario = em.merge(IDUsuario);
            }
            if (IDLivro != null) {
                IDLivro.getEmprestimoList().add(emprestimo);
                IDLivro = em.merge(IDLivro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Emprestimo emprestimo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Emprestimo persistentEmprestimo = em.find(Emprestimo.class, emprestimo.getIDEmprestimo());
            Usuario IDUsuarioOld = persistentEmprestimo.getIDUsuario();
            Usuario IDUsuarioNew = emprestimo.getIDUsuario();
            Livro IDLivroOld = persistentEmprestimo.getIDLivro();
            Livro IDLivroNew = emprestimo.getIDLivro();
            if (IDUsuarioNew != null) {
                IDUsuarioNew = em.getReference(IDUsuarioNew.getClass(), IDUsuarioNew.getIDUsuario());
                emprestimo.setIDUsuario(IDUsuarioNew);
            }
            if (IDLivroNew != null) {
                IDLivroNew = em.getReference(IDLivroNew.getClass(), IDLivroNew.getIDLivro());
                emprestimo.setIDLivro(IDLivroNew);
            }
            emprestimo = em.merge(emprestimo);
            if (IDUsuarioOld != null && !IDUsuarioOld.equals(IDUsuarioNew)) {
                IDUsuarioOld.getEmprestimoList().remove(emprestimo);
                IDUsuarioOld = em.merge(IDUsuarioOld);
            }
            if (IDUsuarioNew != null && !IDUsuarioNew.equals(IDUsuarioOld)) {
                IDUsuarioNew.getEmprestimoList().add(emprestimo);
                IDUsuarioNew = em.merge(IDUsuarioNew);
            }
            if (IDLivroOld != null && !IDLivroOld.equals(IDLivroNew)) {
                IDLivroOld.getEmprestimoList().remove(emprestimo);
                IDLivroOld = em.merge(IDLivroOld);
            }
            if (IDLivroNew != null && !IDLivroNew.equals(IDLivroOld)) {
                IDLivroNew.getEmprestimoList().add(emprestimo);
                IDLivroNew = em.merge(IDLivroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = emprestimo.getIDEmprestimo();
                if (findEmprestimo(id) == null) {
                    throw new NonexistentEntityException("The emprestimo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Emprestimo emprestimo;
            try {
                emprestimo = em.getReference(Emprestimo.class, id);
                emprestimo.getIDEmprestimo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The emprestimo with id " + id + " no longer exists.", enfe);
            }
            Usuario IDUsuario = emprestimo.getIDUsuario();
            if (IDUsuario != null) {
                IDUsuario.getEmprestimoList().remove(emprestimo);
                IDUsuario = em.merge(IDUsuario);
            }
            Livro IDLivro = emprestimo.getIDLivro();
            if (IDLivro != null) {
                IDLivro.getEmprestimoList().remove(emprestimo);
                IDLivro = em.merge(IDLivro);
            }
            em.remove(emprestimo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Emprestimo> findEmprestimoEntities() {
        return findEmprestimoEntities(true, -1, -1);
    }

    public List<Emprestimo> findEmprestimoEntities(int maxResults, int firstResult) {
        return findEmprestimoEntities(false, maxResults, firstResult);
    }

    private List<Emprestimo> findEmprestimoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Emprestimo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Emprestimo findEmprestimo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Emprestimo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmprestimoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Emprestimo> rt = cq.from(Emprestimo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
